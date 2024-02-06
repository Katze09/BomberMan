/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.RemoteSender;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class Player extends Object
{

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;
    private float delayMove;

    public Player(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        delayMove = 0f;
        up = down = right = left = false;
    }

    public void keyBoardDown(int keycode)
    {
        if (delayMove <= 0)
            switch (keycode)
            {
                case Input.Keys.W:
                    up = true;
                    break;
                case Input.Keys.S:
                    down = true;
                    break;
                case Input.Keys.D:
                    right = true;
                    break;
                case Input.Keys.A:
                    left = true;
                    break;
            }
    }

    public void keyBoardUp(int keycode)
    {
        if (delayMove <= 0)
            switch (keycode)
            {
                case Input.Keys.W:
                    up = false;
                    break;
                case Input.Keys.S:
                    down = false;
                    break;
                case Input.Keys.D:
                    right = false;
                    break;
                case Input.Keys.A:
                    left = false;
                    break;
            }
    }

    @Override
    public void update(float deltaTime)
    {
        if(up)
            setY(Y + (speed * deltaTime));
        else if(down)
            setY(Y - (speed * deltaTime));
        System.out.println("X " + X);
        System.out.println("Y " + Y);
    }

}
