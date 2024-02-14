/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package States;

import Objects.PowerUps;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import Objects.*;
import Objects.Enemies.Enemy;
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
    public static Array<Bomb> bombs;
    private final Array<Texture> spritesBombs;
    private final Array<Texture> spritesEnemy;
    private Array<Enemy> enemies;
    public static Map map;

    public GameStates()
    {
        map = new Map();
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        //new FitViewport(worldWidth, worldHeight, camera);
        camera.update();
        bombs = new Array<>();
        String[] fileNames = new String[10];
        fileNames[0] = "Bomb";
        spritesBombs = Loader.LoadArraysprites(fileNames, 1);
        fileNames[0] = "Enemy";
        spritesEnemy = Loader.LoadArraysprites(fileNames, 1);
        fileNames[0] = "Player";
        player = new Player(Loader.LoadArraysprites(fileNames, 1), 100, 100);
        enemies = new Array<>();
        enemies.add(new Enemy(spritesEnemy, 100, 900));
    }

    public void keyBoardDown(int keycode)
    {
        player.keyBoardDown(keycode);
        if (Input.Keys.E == keycode && player.canPutBomb())
        {
            if (player.moveTo > 0)
                if (player.up || player.down)
                {
                    bombs.add(new Bomb(spritesBombs, player.getX(), player.moveTo, true, player.getSizeExplosion()));
                    map.setCharacter(player.getX(), player.moveTo, '^');
                } else
                {
                    bombs.add(new Bomb(spritesBombs, player.moveTo, player.getY(), true, player.getSizeExplosion()));
                    map.setCharacter(player.moveTo, player.getY(), '^');
                }
            else
            {
                bombs.add(new Bomb(spritesBombs, player.getX(), player.getY(), true, player.getSizeExplosion()));
                map.setCharacter(player.getX(), player.getY(), '^');
            }
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
        for (int i = 0; i < enemies.size; i++)
            enemies.get(i).draw(batch);
        if (!player.isDead())
            player.draw(batch);
        for (int i = 0; i < bombs.size; i++)
            bombs.get(i).draw(batch);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void update(float deltaTime)
    {
        if (!player.isDead())
            player.update(deltaTime);

        for (int i = 0; i < enemies.size; i++)
        {
            enemies.get(i).update(deltaTime);
            enemies.get(i).createBomb(bombs, spritesBombs);
        }

        for (int i = 0; i < bombs.size; i++)
        {
            Bomb bomb = bombs.get(i);
            bomb.update(deltaTime);
            checkCollisionsWithOtherBombs(i);
            checkCollisionWithBlockBreakables(bomb);
            checkCollisionWithPlayer(bomb);
            if (bomb.isDead())
            {
                map.setCharacter(bomb.getX(), bomb.getY(), '*');
                bombs.removeIndex(i);
                if (bomb.isFromPlayer)
                    player.increaseNumBombs();
                else
                    enemies.get(0).putBomb = false;
            }
        }

        for (int i = 0; i < map.PowerUps.size; i++)
            if (checkCollsionWithPowerUps(map.PowerUps.get(i)))
                map.PowerUps.removeIndex(i);
        moveCamera(deltaTime);
    }

    private void moveCamera(float deltaTime)
    {
        float X = player.getX();

        // Verifica si la posición actual de la cámara es menor que la posición del jugador
        if ((camera.position.x + 300) < X && camera.position.x <= 2250)
            camera.translate((player.getSpeed() * deltaTime), 0);
        if ((camera.position.x - 400) > X && camera.position.x >= 750)
            camera.translate(-1 * (player.getSpeed() * deltaTime), 0);
        camera.position.x = (camera.position.x < 750) ? 750 : camera.position.x;
        camera.position.x = (camera.position.x > 2250) ? 2250 : camera.position.x;
        camera.update();
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
                    map.setCharacter(blockBreakable.getIndexX(), blockBreakable.getIndexY(), '*');
                    //map.map[blockBreakable.getIndexY()][blockBreakable.getIndexX()] = '*';
                    map.BlockBreakable.removeValue(blockBreakable, true);
                }
    }

    private void checkCollisionWithPlayer(Bomb bomb)
    {
        for (Explosion explosion : bomb.explosion)
            if (explosion.checkCollsision(player))
                player.setDead(true);
    }

    private boolean checkCollsionWithPowerUps(PowerUps PowerUp)
    {
        if (PowerUp.checkCollsision(player))
        {
            PowerUp.aplicateEffect(player);
            return true;
        }
        return false;
    }

    @Override
    public void dispose()
    {
        player.dispose();
        for (Bomb bomb : bombs)
            bomb.dispose();
    }

}
