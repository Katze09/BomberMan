package States;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter implements InputProcessor
{

    private GameStates gameStates;
    private SpriteBatch batch;

    @Override
    public void create()
    {
        gameStates = new GameStates();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render()
    {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        gameStates.update(deltaTime);
        gameStates.draw(batch);
        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        gameStates.dispose();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        gameStates.keyBoardDown(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        gameStates.keyBoardUp(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
//        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
//        gameStates.camera.unproject(worldCoordinates);
//        gameStates.click(worldCoordinates.x, worldCoordinates.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }
}
