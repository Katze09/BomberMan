/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import Objects.Object;

/**
 *
 * @author Katze
 */
public abstract class Obstacle extends Object
{

    public Obstacle(Texture sprite, float X, float Y)
    {
        super();
        Array<Texture> spriteA = new Array<Texture>();
        spriteA.add(sprite);
        hitBoxMultiplication = 1f;
        setSprites(spriteA);
        WIDTH = sprite.getWidth();
        HEIGHT = sprite.getHeight();
        setX(X);
        setY(Y);
    }
}
