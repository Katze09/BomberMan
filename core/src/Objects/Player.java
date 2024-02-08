/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import States.GameStates;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.RemoteSender;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class Player extends Object
{

    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    private boolean upKleft;
    private boolean downKleft;
    private boolean rightKleft;
    private boolean leftKleft;
    private boolean upNext;
    private boolean downNext;
    private boolean rightNext;
    private boolean leftNext;
    public float moveTo;
    public float previouslyX;
    public float previouslyY;

    public Player(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        speed = 500;
        up = down = right = left = false;
        upKleft = downKleft = rightKleft = leftKleft = true;
        float X1HitBox = X + (WIDTH * hitBoxMultiplication);
        float X2HitBox = WIDTH - 2 * (WIDTH * hitBoxMultiplication); // Corregido
        float Y1HitBox = (Y + 25) - (HEIGHT * hitBoxMultiplication);
        float Y2HitBox = HEIGHT - 2 * (HEIGHT * hitBoxMultiplication); // Corregido
        hitBox = new Rectangle(X1HitBox, Y1HitBox, X2HitBox, Y2HitBox);
        previouslyX = X;
        previouslyY = Y;
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
                    if (GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                    {
                        up = true;
                        moveTo = Y + 100;
                        upKleft = true;
                    }
                    break;
                case Input.Keys.S:
                    if (GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                    {
                        down = true;
                        moveTo = Y - 100;
                        downKleft = true;
                    }
                    break;
                case Input.Keys.D:
                    if (GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                    {
                        right = true;
                        moveTo = X + 100;
                        rightKleft = true;
                    }
                    break;
                case Input.Keys.A:
                    if (GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                    {
                        left = true;
                        moveTo = X - 100;
                        leftKleft = true;
                    }
                    break;
            }
        } else
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
            upEvent(deltaTime);
        if (down)
            downEvent(deltaTime);
        if (right)
            rightEvent(deltaTime);
        if (left)
            leftEvent(deltaTime);
    }

    private void upEvent(float deltaTime)
    {
        if (Y < moveTo)
            setY(Y + (speed * deltaTime));
        else
        {
            setY(moveTo);
            if (upKleft && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                moveTo = Y + 100;
            else
            {
                if (upKleft)
                    upKleft = false;
                moveTo = 0;
                if (downNext && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    down = true;
                    downKleft = true;
                    moveTo = Y - 100;
                } else if (rightNext && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    right = true;
                    rightKleft = true;
                    moveTo = X + 100;
                } else if (leftNext && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    left = true;
                    leftKleft = true;
                    moveTo = X - 100;
                }
            }
            up = upKleft;
        }
    }

    private void downEvent(float deltaTime)
    {
        if (Y > moveTo)
            setY(Y - (speed * deltaTime));
        else
        {
            setY(moveTo);
            if (downKleft && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                moveTo = Y - 100;
            else
            {
                if (downKleft)
                    downKleft = false;
                moveTo = 0;
                if (upNext && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    up = true;
                    upKleft = true;
                    moveTo = Y + 100;
                } else if (rightNext && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    right = true;
                    rightKleft = true;
                    moveTo = X + 100;
                } else if (leftNext && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    left = true;
                    leftKleft = true;
                    moveTo = X - 100;
                }
            }
            down = downKleft;
        }
    }

    private void rightEvent(float deltaTime)
    {
        if (X < moveTo)
            setX(X + (speed * deltaTime));
        else
        {
            setX(moveTo);
            if (rightKleft && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                moveTo = X + 100;
            else
            {
                if (rightKleft)
                    rightKleft = false;
                moveTo = 0;
                if (upNext && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    up = true;
                    upKleft = true;
                    moveTo = Y + 100;
                } else if (downNext && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    down = true;
                    downKleft = true;
                    moveTo = Y - 100;
                } else if (leftNext && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    left = true;
                    leftKleft = true;
                    moveTo = X - 100;
                }
            }
            right = rightKleft;
        }
    }

    private void leftEvent(float deltaTime)
    {
        if (X > moveTo)
            setX(X - (speed * deltaTime));
        else
        {
            setX(moveTo);
            if (leftKleft && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                moveTo = X - 100;
            else
            {
                if (leftKleft)
                    leftKleft = false;
                moveTo = 0;
                if (upNext && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    up = true;
                    upKleft = true;
                    moveTo = Y + 100;
                } else if (downNext && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    down = true;
                    downKleft = true;
                    moveTo = Y - 100;
                } else if (rightNext && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    right = true;
                    rightKleft = true;
                    moveTo = X + 100;
                }
            }
            left = leftKleft;
        }
    }

    @Override
    public void setY(float Y)
    {
        this.Y = Y;
        vector3Position.y = Y;
        hitBox.setY((Y + 25) - (HEIGHT * hitBoxMultiplication));
    }

}
