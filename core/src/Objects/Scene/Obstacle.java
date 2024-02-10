/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import Objects.Object;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Katze
 */
public abstract class Obstacle extends Object
{

    public Obstacle(Texture sprite, float X, float Y)
    {
        super(new Array<>(new Texture[]{sprite}), X, Y);
        hitBox = new Rectangle(X, Y, WIDTH, HEIGHT);
    }
}
