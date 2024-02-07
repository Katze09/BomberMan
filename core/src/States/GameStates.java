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
import com.badlogic.gdx.utils.viewport.FitViewport;

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
    //private Array<Soldier> soldiers;

    public GameStates()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        Array<Texture> textures = new Array<Texture>();
        textures.add(new Texture("Player1.png"));
        player = new Player(textures, 400, 400);
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
        player.draw(batch);
        /*for (Soldier soldier : soldiers)
            soldier.draw(batch);
        batch.setProjectionMatrix(camera.combined);*/
    }

    @Override
    public void update(float deltaTime)
    {
        player.update(deltaTime);
        /*camera.update();
        for (Soldier soldier : soldiers)
            soldier.update(deltaTime);*/
    }

    @Override
    public void dispose()
    {
        player.dispose();
        /*for (Soldier soldier : soldiers)
            soldier.dispose();
        soldiers.clear();*/
    }

}
