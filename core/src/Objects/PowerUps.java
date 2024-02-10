/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import Objects.Player;
import Objects.Object;
import States.Loader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class PowerUps extends Object
{

    private final int type;

    public PowerUps(float X, float Y, int type)
    {
        this.type = type;
        setSprites(new Array<>(new Texture[]
        {
            Loader.LoadTexture("PowerUp" + type)
        }));
        WIDTH = sprites.get(indexSprites).getWidth();
        HEIGHT = sprites.get(indexSprites).getHeight();
        hitBoxMultiplication = 0.1f;
        float X1HitBox = X + (WIDTH * hitBoxMultiplication);
        float X2HitBox = WIDTH - 2 * (WIDTH * hitBoxMultiplication); // Corregido
        float Y1HitBox = Y + (HEIGHT * hitBoxMultiplication);
        float Y2HitBox = HEIGHT - 2 * (HEIGHT * hitBoxMultiplication); // Corregido
        hitBox = new Rectangle(X1HitBox, Y1HitBox, X2HitBox, Y2HitBox);
        vector3Position = new Vector3(X, Y, 0);
        setX(X);
        setY(Y);
    }

    @Override
    public void update(float deltaTime)
    {

    }

    public void aplicateEffect(Player player)
    {
        switch (type)
        {
            case 1:
                player.increaseNumBombs();
                break;
        }
    }

}
