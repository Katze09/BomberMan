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
        if (Input.Keys.E == keycode)
            if (player.moveTo > 0)
                if (player.up || player.down)
                    bombs.add(new Bomb(spritesBombs, player.getX(), player.moveTo));
                else
                    bombs.add(new Bomb(spritesBombs, player.moveTo, player.getY()));
            else
                bombs.add(new Bomb(spritesBombs, player.getX(), player.getY()));
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
        player.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {
        player.update(deltaTime);
        for (int i = 0; i < bombs.size; i++)
            bombs.get(i).update(deltaTime);
    }

    @Override
    public void dispose()
    {
        //player.dispose();
    }

}
