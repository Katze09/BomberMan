package Menu;

import Objects.GameMethods;
import Objects.Object;
import Objects.Scene.Map;
import States.Loader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Menu implements GameMethods
{

    private Button play;
    private BitmapFont titles;
    public Camera camera;

    public Menu()
    {
        titles = Loader.LoadFont("8-bit Arcade In", 220);
        titles.setColor(Color.WHITE);
        play  = new Button(300, 800, "Start Game");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    public boolean clickMenu(float x, float y)
    {
        return play.checkClick(x,y);
    }

    public void hover(float x, float y)
    {
        play.hover(x,y);
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        titles.draw(batch, "Bomberman" , 250, 1100);
        play.draw(batch);
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void dispose() {}

    private class Button implements GameMethods
    {

        private float X;
        private float Y;
        private float WIDTH;
        private float HEIGHT;
        private BitmapFont font;
        private final String text;
        private final Rectangle hitBox;
        public  Button(int X, int Y, String text)
        {
            this.X = X;
            this.Y = Y;
            this.text = text;
            font = Loader.LoadFont("8-bit Arcade In", 180);
            font.setColor(Color.WHITE);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(font, text);
            WIDTH = layout.width;
            HEIGHT = layout.height;
            hitBox = new Rectangle(X, (Y-HEIGHT + 20), WIDTH, HEIGHT + 20);
        }

        @Override
        public void draw(SpriteBatch batch)
        {
            font.draw(batch,text,X,Y);
        }

        public boolean checkClick(float x, float y)
        {
            return hitBox.contains(x, y);
        }

        public void hover(float x, float y)
        {
            if(checkClick(x,y))
                font.setColor(Color.RED);
            else
                font.setColor(Color.WHITE);
        }

        public float getX()
        {
            return X;
        }

        public void setX(float x)
        {
            X = x;
            hitBox.setX(X);
        }

        public float getY()
        {
            return Y;
        }

        public void setY(float y)
        {
            Y = y;
            hitBox.setY(Y);
        }

        @Override
        public void update(float deltaTime) {}

        @Override
        public void dispose() {}
    }
}
