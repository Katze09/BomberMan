/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public abstract class Object implements GameMethods
{

    protected Array<Texture> sprites;
    protected int WIDTH;
    protected int HEIGHT;
    protected float X;
    protected float Y;
    protected Rectangle hitBox;
    protected float hitBoxMultiplication;
    protected int indexSprites;
    protected boolean dead;
    protected int speed;
    protected Vector3 vector3Position;

    public Object()
    {
    }

    public Object(Array<Texture> sprites, float X, float Y)
    {
        this.sprites = sprites;
        indexSprites = 0;
        WIDTH = sprites.get(indexSprites).getWidth();
        HEIGHT = sprites.get(indexSprites).getHeight();
        this.X = X;
        this.Y = Y;
        speed = 100;
        dead = false;
        hitBoxMultiplication = 0.1f;
        float X1HitBox = X + (WIDTH * hitBoxMultiplication);
        float X2HitBox = WIDTH - 2 * (WIDTH * hitBoxMultiplication); // Corregido
        float Y1HitBox = Y + (HEIGHT * hitBoxMultiplication);
        float Y2HitBox = HEIGHT - 2 * (HEIGHT * hitBoxMultiplication); // Corregido
        hitBox = new Rectangle(X1HitBox, Y1HitBox, X2HitBox, Y2HitBox);
        //System.out.println(hitBo.xtoString());
        vector3Position = new Vector3(X, Y, 0);
    }

    public boolean checkCollsision(Object object)
    {
        return (hitBox.overlaps(object.getHitBox()));
    }

    public Array<Texture> getSprites()
    {
        return sprites;
    }

    public void setSprites(Array<Texture> sprites)
    {
        this.sprites = sprites;
    }

    public Texture getSprite(int index)
    {
        return sprites.get(index);
    }

    public void setSprite(Texture sprite, int index)
    {
        sprites.set(index, sprite);
    }

    public void addSprite(Texture sprite)
    {
        sprites.add(sprite);
    }

    public float getX()
    {
        return X;
    }

    public float getX2()
    {
        return X + WIDTH;
    }

    public void setX(float X)
    {
        this.X = X;
        vector3Position.x = X;
        hitBox.setX(X + (WIDTH * hitBoxMultiplication));
    }

    public float getY()
    {
        return Y;
    }

    public float getY2()
    {
        return Y + HEIGHT;
    }

    public void setY(float Y)
    {
        this.Y = Y;
        vector3Position.y = Y;
        hitBox.setY(Y + (HEIGHT * hitBoxMultiplication));
    }

    public Vector3 getVector3Position()
    {
        return vector3Position;
    }

    public int getWIDTH()
    {
        return WIDTH;
    }

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public boolean isDead()
    {
        return dead;
    }

    public void setDead(boolean dead)
    {
        this.dead = dead;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public Rectangle getHitBox()
    {
        return hitBox;
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        batch.draw(sprites.get(indexSprites), X, Y);
    }

    @Override
    public abstract void update(float deltaTime);

    @Override
    public void dispose()
    {
        sprites.get(indexSprites).dispose();
    }
}
