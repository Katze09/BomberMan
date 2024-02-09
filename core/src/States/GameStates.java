/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import Objects.*;
import Objects.Scene.*;
import com.badlogic.gdx.Input;

/**
 *
 * @author Katze
 */
public class GameStates implements GameMethods
{

    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();

    public OrthographicCamera camera;
    private Player player;
    private Array<Bomb> bombs;
    private Array<Texture> spritesBombs;
    public static Map map;

    public GameStates()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        map = new Map();
        bombs = new Array<>();
        String[] fileNames = new String[10];
        fileNames[0] = "Bomb";
        spritesBombs = Loader.LoadArraysprites(fileNames, 1);
        fileNames[0] = "Player";
        player = new Player(Loader.LoadArraysprites(fileNames, 1), 100, 100);
    }

    public void keyBoardDown(int keycode)
    {
        player.keyBoardDown(keycode);
        if (Input.Keys.E == keycode && player.canPutBomb())
        {
            if (player.moveTo > 0)
                if (player.up || player.down)
                    bombs.add(new Bomb(spritesBombs, player.getX(), player.moveTo));
                else
                    bombs.add(new Bomb(spritesBombs, player.moveTo, player.getY()));
            else
                bombs.add(new Bomb(spritesBombs, player.getX(), player.getY()));
            player.reduceNumBombs();
        }
    }

    public void keyBoardUp(int keycode)
    {
        player.keyBoardUp(keycode);
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        map.draw(batch);
        for (int i = 0; i < bombs.size; i++)
            bombs.get(i).draw(batch);
        if (!player.isDead())
            player.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {
        if (!player.isDead())
            player.update(deltaTime);

        for (int i = 0; i < bombs.size; i++)
        {
            Bomb bomb = bombs.get(i);
            bomb.update(deltaTime);
            checkCollisionsWithOtherBombs(i);
            checkCollisionWithBlockBreakables(bomb);
            checkCollisionWithPlayer(bomb);
            if (bomb.isDead())
            {
                bombs.removeIndex(i);
                player.increaseNumBombs();
            }
        }
    }

    private void checkCollisionsWithOtherBombs(int currentBombIndex)
    {
        Bomb currentBomb = bombs.get(currentBombIndex);
        if (currentBomb.isExplode)
            for (int otherBombIndex = 0; otherBombIndex < bombs.size; otherBombIndex++)
                if (currentBombIndex != otherBombIndex)
                    for (Explosion explosion : currentBomb.explosion)
                        if (explosion.checkCollsision(bombs.get(otherBombIndex)))
                        {
                            Bomb otherBomb = bombs.get(otherBombIndex);
                            if (!otherBomb.isExplode)
                                otherBomb.delayExplode = 0;
                        }
    }

    private void checkCollisionWithBlockBreakables(Bomb bomb)
    {
        for (Explosion explosion : bomb.explosion)
            for (Block blockBreakable : map.BlockBreakable)
                if (explosion.checkCollsision(blockBreakable))
                {
                    map.map[blockBreakable.getIndexY()][blockBreakable.getIndexX()] = '*';
                    map.BlockBreakable.removeValue(blockBreakable, true);
                }
    }

    private void checkCollisionWithPlayer(Bomb bomb)
    {
        for (Explosion explosion : bomb.explosion)
            if (explosion.checkCollsision(player))
                player.setDead(true);
    }

    @Override
    public void dispose()
    {
        player.dispose();
        for (Bomb bomb : bombs)
            bomb.dispose();
    }

}
