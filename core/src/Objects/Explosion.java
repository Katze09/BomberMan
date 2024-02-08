/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import Objects.Scene.Obstacle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class Explosion extends Object
{

    private float animationExplosion;
    private int rotate;

    public Explosion(Array<Texture> sprites, float X, float Y, int rotate)
    {
        super(sprites, X, Y);
        this.rotate = rotate;
        animationExplosion = 0.1f;
    }

    public Explosion(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        rotate = 0;
        animationExplosion = 0.1f;
    }

    @Override
    public void update(float deltaTime)
    {
        if (indexSprites < 6)
            if (animationExplosion > 0)
                animationExplosion -= deltaTime;
            else
            {
                indexSprites++;
                animationExplosion = 0.1f;
                if (indexSprites == 3)
                    animationExplosion = 0.5f;
            }
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        if (rotate == 0)
            batch.draw(sprites.get(indexSprites), X, Y);
        else
        {
            Sprite sprite = new Sprite(sprites.get(indexSprites));
            sprite.setPosition(X, Y);
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(rotate);
            sprite.draw(batch);
        }
    }

}
