/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import States.GameStates;
import States.Loader;
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
    private int life;
    public float previouslyX;
    public float previouslyY;
    private int numBombs;
    private int sizeExplosion;
    private float delayAnimationMove;
    public float delayPowerUp;
    public int sizeBehindBlock = 10;

    public Player(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        speed = 500;
        numBombs = 3;
        up = down = right = left = false;
        upKleft = downKleft = rightKleft = leftKleft = true;
        delayAnimationMove = 0.1f;
        float X1HitBox = X + (WIDTH * hitBoxMultiplication);
        float X2HitBox = WIDTH - 2 * (WIDTH * hitBoxMultiplication); // Corregido
        float Y1HitBox = (Y + 25) + (HEIGHT * hitBoxMultiplication);
        float Y2HitBox = (HEIGHT - 25) - 2 * ((HEIGHT - 25) * hitBoxMultiplication); // Corregido
        hitBox = new Rectangle(X1HitBox, Y1HitBox, X2HitBox, Y2HitBox);
        previouslyX = X;
        previouslyY = Y;
        life = 3;
        sizeExplosion = 1;
    }

    public int getNumBombs()
    {
        return numBombs;
    }

    public void reduceNumBombs()
    {
        numBombs--;
    }

    public void increaseNumBombs()
    {
        numBombs++;
    }

    public int getSizeExplosion()
    {
        return sizeExplosion;
    }

    public void increaseSizeExplosion()
    {
        sizeExplosion++;
    }

    public int getLife()
    {
        return life;
    }

    public void reduceSizeExplosion()
    {
        sizeExplosion--;
    }

    public boolean canPutBomb()
    {
        return numBombs > 0;
    }

    private int contNumSpritesAnimation = 0;

    @Override
    public void animationBase()
    {
        if (moveTo != 0)
        {
            if (delayAnimationMove <= 0)
            {
                indexSprites++;
                contNumSpritesAnimation++;
                delayAnimationMove = 0.1f;
            }
            if (contNumSpritesAnimation == 4)
            {
                contNumSpritesAnimation = 0;
                indexSprites = indexSprites - 4;
            }
        }
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
                        indexSprites = 12;
                        contNumSpritesAnimation = 0;
                        moveTo = Y + 100;
                        upKleft = true;
                    }
                    break;
                case Input.Keys.S:
                    if (GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                    {
                        down = true;
                        indexSprites = 4;
                        contNumSpritesAnimation = 0;
                        moveTo = Y - 100;
                        downKleft = true;
                    }
                    break;
                case Input.Keys.D:
                    if (GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                    {
                        right = true;
                        indexSprites = 8;
                        contNumSpritesAnimation = 0;
                        moveTo = X + 100;
                        rightKleft = true;
                    }
                    break;
                case Input.Keys.A:
                    if (GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                    {
                        left = true;
                        moveTo = X - 100;
                        indexSprites = 16;
                        contNumSpritesAnimation = 0;
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
        if (delayAnimationMove > 0)
            delayAnimationMove -= deltaTime;
        if (delayPowerUp > 0)
            delayPowerUp -= deltaTime;
        else
        {
            speed = 500;
            sizeBehindBlock = 10;
        }
        animationBase();
    }

    private void upEvent(float deltaTime)
    {
        if (Y < moveTo - sizeBehindBlock)
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
                indexSprites = 3;
                if (downNext && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    down = true;
                    indexSprites = 4;
                    contNumSpritesAnimation = 0;
                    downKleft = true;
                    moveTo = Y - 100;
                } else if (rightNext && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    right = true;
                    indexSprites = 8;
                    contNumSpritesAnimation = 0;
                    rightKleft = true;
                    moveTo = X + 100;
                } else if (leftNext && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    left = true;
                    indexSprites = 16;
                    contNumSpritesAnimation = 0;
                    leftKleft = true;
                    moveTo = X - 100;
                }
            }
            up = upKleft;
        }
    }

    private void downEvent(float deltaTime)
    {
        if (Y > moveTo + sizeBehindBlock)
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
                indexSprites = 0;
                if (upNext && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    up = true;
                    indexSprites = 12;
                    contNumSpritesAnimation = 0;
                    upKleft = true;
                    moveTo = Y + 100;
                } else if (rightNext && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    right = true;
                    indexSprites = 8;
                    contNumSpritesAnimation = 0;
                    rightKleft = true;
                    moveTo = X + 100;
                } else if (leftNext && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    left = true;
                    indexSprites = 16;
                    contNumSpritesAnimation = 0;
                    leftKleft = true;
                    moveTo = X - 100;
                }
            }
            down = downKleft;
        }
    }

    private void rightEvent(float deltaTime)
    {
        if (X < moveTo - sizeBehindBlock)
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
                indexSprites = 1;
                moveTo = 0;
                if (upNext && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    up = true;
                    indexSprites = 12;
                    contNumSpritesAnimation = 0;
                    upKleft = true;
                    moveTo = Y + 100;
                } else if (downNext && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    down = true;
                    indexSprites = 4;
                    contNumSpritesAnimation = 0;
                    downKleft = true;
                    moveTo = Y - 100;
                } else if (leftNext && GameStates.map.canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    left = true;
                    indexSprites = 16;
                    contNumSpritesAnimation = 0;
                    leftKleft = true;
                    moveTo = X - 100;
                }
            }
            right = rightKleft;
        }
    }

    private void leftEvent(float deltaTime)
    {
        if (X > moveTo + sizeBehindBlock)
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
                indexSprites = 2;
                if (upNext && GameStates.map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    up = true;
                    indexSprites = 12;
                    contNumSpritesAnimation = 0;
                    upKleft = true;
                    moveTo = Y + 100;
                } else if (downNext && GameStates.map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    down = true;
                    indexSprites = 4;
                    contNumSpritesAnimation = 0;
                    downKleft = true;
                    moveTo = Y - 100;
                } else if (rightNext && GameStates.map.canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    right = true;
                    indexSprites = 8;
                    contNumSpritesAnimation = 0;
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
        hitBox.setY((Y + 25) - ((HEIGHT - 25) * hitBoxMultiplication));
    }

}
