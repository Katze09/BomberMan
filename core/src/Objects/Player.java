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
    private boolean upKleft;
    private boolean downKleft;
    private boolean rightKleft;
    private boolean leftKleft;
    private boolean upNext;
    private boolean downNext;
    private boolean rightNext;
    private boolean leftNext;
    private float moveTo;

    public Player(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        speed = 500;
        up = down = right = left = false;
        upKleft = downKleft = rightKleft = leftKleft = true;
    }

    public void keyBoardDown(int keycode)
    {
        if (moveTo <= 0)
        {
            up = down = right = left = false;
            upNext = downNext = rightNext = leftNext = false;
            upKleft = downKleft = rightKleft = leftKleft = true;
            switch (keycode)
            {
                case Input.Keys.W:
                    up = true;
                    moveTo = Y + 100;
                    upKleft = true;
                    break;
                case Input.Keys.S:
                    down = true;
                    moveTo = Y - 100;
                    downKleft = true;
                    break;
                case Input.Keys.D:
                    right = true;
                    moveTo = X + 100;
                    rightKleft = true;
                    break;
                case Input.Keys.A:
                    left = true;
                    moveTo = X - 100;
                    leftKleft = true;
                    break;
            }
        }else
        {
            upNext = downNext = rightNext = leftNext = false;
            switch (keycode)
            {
                case Input.Keys.W:
                    upNext = true;
                    break;
                case Input.Keys.S:
                    downNext = true;
                    break;
                case Input.Keys.D:
                    rightNext = true;
                    break;
                case Input.Keys.A:
                    leftNext = true;
                    break;
            }
        }
    }

    public void keyBoardUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
                upKleft = false;
                break;
            case Input.Keys.S:
                downKleft = false;
                break;
            case Input.Keys.D:
                rightKleft = false;
                break;
            case Input.Keys.A:
                leftKleft = false;
                break;
        }
    }

    @Override
    public void update(float deltaTime)
    {
        if (up)
            if (Y < moveTo)
                setY(Y + (speed * deltaTime));
            else
            {
                if (upKleft)
                    moveTo = Y + 100;
                else
                {
                    setY(moveTo);
                    moveTo = 0;
                    if(downNext)
                    {
                        down = true;
                        moveTo = Y - 100;
                    }else if(rightNext)
                    {
                        right = true;
                        moveTo = X + 100;
                    }else if(leftNext)
                    {
                        left = true;
                        moveTo = X - 100;
                    }
                }
                up = upKleft;
            }
        if (down)
            if (Y > moveTo)
                setY(Y - (speed * deltaTime));
            else
            {
                if (downKleft)
                    moveTo = Y - 100;
                else
                {
                    setY(moveTo);
                    moveTo = 0;
                    if(upNext)
                    {
                        up = true;
                        moveTo = Y + 100;
                    }else if(rightNext)
                    {
                        right = true;
                        moveTo = X + 100;
                    }else if(leftNext)
                    {
                        left = true;
                        moveTo = X - 100;
                    }
                }
                down = downKleft;
            }
        if (right)
            if (X < moveTo)
                setX(X + (speed * deltaTime));
            else
            {
                if (rightKleft)
                    moveTo = X + 100;
                else
                {
                    setX(moveTo);
                    moveTo = 0;
                    if(upNext)
                    {
                        up = true;
                        moveTo = Y + 100;
                    }else if(downNext)
                    {
                        down = true;
                        moveTo = Y - 100;
                    }else if(leftNext)
                    {
                        left = true;
                        moveTo = X - 100;
                    }
                }
                right = rightKleft;
            }
        if (left)
            if (X > moveTo)
                setX(X - (speed * deltaTime));
            else
            {
                if (leftKleft)
                    moveTo = X - 100;
                else
                {
                    setX(moveTo);
                    moveTo = 0;
                    if(upNext)
                    {
                        up = true;
                        moveTo = Y + 100;
                    }else if(downNext)
                    {
                        down = true;
                        moveTo = Y - 100;
                    }else if(rightNext)
                    {
                        right = true;
                        moveTo = X + 100;
                    }
                }
                left = leftKleft;
            }
        System.out.println("X " + X);
        System.out.println("Y " + Y);
    }

}
