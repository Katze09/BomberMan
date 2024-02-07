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
    public static Map map;

    public GameStates()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        map = new Map();
        Array<Texture> textures = new Array<Texture>();
        textures.add(new Texture("Player1.png"));
        player = new Player(textures, 100, 100);
    }

    public void keyBoardDown(int keycode)
    {
        player.keyBoardDown(keycode);
    }

    public void keyBoardUp(int keycode)
    {
        player.keyBoardUp(keycode);
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        map.draw(batch);
        player.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {
        player.update(deltaTime);
    }

    @Override
    public void dispose()
    {
        player.dispose();
    }

}
