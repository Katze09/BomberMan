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

    public boolean[] direction;
    public boolean[] directionLeft;
    private boolean[] directionNext;
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
        delayAnimationMove = 0.1f;
        direction = new boolean[]{false, false, false,false};
        directionLeft = new boolean[]{true,true,true,true};
        directionNext = new boolean[4];
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


    public Player(Array<Texture> sprites, float X, float Y, int life)
    {
        super(sprites, X, Y);
        speed = 500;
        numBombs = 3;
        delayAnimationMove = 0.1f;
        direction = new boolean[]{false, false, false,false};
        directionLeft = new boolean[]{true,true,true,true};
        directionNext = new boolean[4];
        float X1HitBox = X + (WIDTH * hitBoxMultiplication);
        float X2HitBox = WIDTH - 2 * (WIDTH * hitBoxMultiplication); // Corregido
        float Y1HitBox = (Y + 25) + (HEIGHT * hitBoxMultiplication);
        float Y2HitBox = (HEIGHT - 25) - 2 * ((HEIGHT - 25) * hitBoxMultiplication); // Corregido
        hitBox = new Rectangle(X1HitBox, Y1HitBox, X2HitBox, Y2HitBox);
        previouslyX = X;
        previouslyY = Y;
        this.life = life;
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
    
    public void reduceLife()
    {
        life--;
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
            direction = new boolean[]{false,false,false,false};
            directionNext = new boolean[]{false,false,false,false};
            directionLeft = new boolean[]{true,true,true,true};
            //up = down = right = left = false;
            //upNext = downNext = rightNext = leftNext = false;
            //upKleft = downKleft = rightKleft = leftKleft = true;
            switch (keycode)
            {
                case Input.Keys.W:
                    if (GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y + 100)) == -1)
                    {
                        direction[0] = true;
                        indexSprites = 12;
                        contNumSpritesAnimation = 0;
                        moveTo = Y + 100;
                        directionLeft[0] = true;
                    }
                    break;
                case Input.Keys.S:
                    if (GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y - 100)) == -1)
                    {
                        direction[1] = true;
                        indexSprites = 4;
                        contNumSpritesAnimation = 0;
                        moveTo = Y - 100;
                        directionLeft[1] = true;
                    }
                    break;
                case Input.Keys.D:
                    if (GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X + 100), (int) Y) == -1)
                    {
                        direction[2] = true;
                        indexSprites = 8;
                        contNumSpritesAnimation = 0;
                        moveTo = X + 100;
                        directionLeft[2] = true;
                    }
                    break;
                case Input.Keys.A:
                    if (GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X - 100), (int) Y) == -1)
                    {
                        direction[3] = true;
                        moveTo = X - 100;
                        indexSprites = 16;
                        contNumSpritesAnimation = 0;
                        directionLeft[3] = true;
                    }
                    break;
            }
        } else
        {
            directionNext = new boolean[]{false,false,false,false};
            //upNext = downNext = rightNext = leftNext = false;
            switch (keycode)
            {
                case Input.Keys.W:
                    directionNext[0] = true;
                    break;
                case Input.Keys.S:
                    directionNext[1] = true;
                    break;
                case Input.Keys.D:
                    directionNext[2] = true;
                    break;
                case Input.Keys.A:
                    directionNext[3] = true;
                    break;
            }
        }
    }

    public void keyBoardUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
                directionLeft[0] = false;
                break;
            case Input.Keys.S:
                directionLeft[1] = false;
                break;
            case Input.Keys.D:
                directionLeft[2] = false;
                break;
            case Input.Keys.A:
                directionLeft[3] = false;
                break;
        }
    }

    @Override
    public void update(float deltaTime)
    {
        if (direction[0])
            upEvent(deltaTime);
        if (direction[1])
            downEvent(deltaTime);
        if (direction[2])
            rightEvent(deltaTime);
        if (direction[3])
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
            if (directionLeft[0] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y + 100)) == -1)
                moveTo = Y + 100;
            else
            {
                if (directionLeft[0])
                    directionLeft[0] = false;
                moveTo = 0;
                indexSprites = 3;
                if (directionNext[1] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    direction[1] = true;
                    indexSprites = 4;
                    contNumSpritesAnimation = 0;
                    directionLeft[1] = true;
                    moveTo = Y - 100;
                } else if (directionNext[2] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    direction[2] = true;
                    indexSprites = 8;
                    contNumSpritesAnimation = 0;
                    directionLeft[2] = true;
                    moveTo = X + 100;
                } else if (directionNext[3] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    direction[3] = true;
                    indexSprites = 16;
                    contNumSpritesAnimation = 0;
                    directionLeft[3] = true;
                    moveTo = X - 100;
                }
            }
            direction[0] = directionLeft[0];
        }
    }

    private void downEvent(float deltaTime)
    {
        if (Y > moveTo + sizeBehindBlock)
            setY(Y - (speed * deltaTime));
        else
        {
            setY(moveTo);
            if (directionLeft[1] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y - 100)) == -1)
                moveTo = Y - 100;
            else
            {
                if (directionLeft[1])
                    directionLeft[1] = false;
                moveTo = 0;
                indexSprites = 0;
                if (directionNext[0] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    direction[0] = true;
                    indexSprites = 12;
                    contNumSpritesAnimation = 0;
                    directionLeft[0] = true;
                    moveTo = Y + 100;
                } else if (directionNext[2] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    direction[2] = true;
                    indexSprites = 8;
                    contNumSpritesAnimation = 0;
                    directionLeft[2] = true;
                    moveTo = X + 100;
                } else if (directionNext[3] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    direction[3] = true;
                    indexSprites = 16;
                    contNumSpritesAnimation = 0;
                    directionLeft[3] = true;
                    moveTo = X - 100;
                }
            }
            direction[1] = directionLeft[1];
        }
    }

    private void rightEvent(float deltaTime)
    {
        if (X < moveTo - sizeBehindBlock)
            setX(X + (speed * deltaTime));
        else
        {
            setX(moveTo);
            if (directionLeft[2] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X + 100), (int) Y) == -1)
                moveTo = X + 100;
            else
            {
                if (directionLeft[2])
                    directionLeft[2] = false;
                indexSprites = 1;
                moveTo = 0;
                if (directionNext[0] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    direction[0] = true;
                    indexSprites = 12;
                    contNumSpritesAnimation = 0;
                    directionLeft[0] = true;
                    moveTo = Y + 100;
                } else if (directionNext[1] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    direction[1] = true;
                    indexSprites = 4;
                    contNumSpritesAnimation = 0;
                    directionLeft[1] = true;
                    moveTo = Y - 100;
                } else if (directionNext[3] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X - 100), (int) Y) == -1)
                {
                    direction[3] = true;
                    indexSprites = 16;
                    contNumSpritesAnimation = 0;
                    directionLeft[3] = true;
                    moveTo = X - 100;
                }
            }
            direction[2] = directionLeft[2];
        }
    }

    private void leftEvent(float deltaTime)
    {
        if (X > moveTo + sizeBehindBlock)
            setX(X - (speed * deltaTime));
        else
        {
            setX(moveTo);
            if (directionLeft[3] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X - 100), (int) Y) == -1)
                moveTo = X - 100;
            else
            {
                if (directionLeft[3])
                    directionLeft[3] = false;
                moveTo = 0;
                indexSprites = 2;
                if (directionNext[0] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y + 100)) == -1)
                {
                    direction[0] = true;
                    indexSprites = 12;
                    contNumSpritesAnimation = 0;
                    directionLeft[0] = true;
                    moveTo = Y + 100;
                } else if (directionNext[1] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y - 100)) == -1)
                {
                    direction[1] = true;
                    indexSprites = 4;
                    contNumSpritesAnimation = 0;
                    directionLeft[1] = true;
                    moveTo = Y - 100;
                } else if (directionNext[2] && GameStates.map.get(GameStates.indexMap).canMoveTo((int) (X + 100), (int) Y) == -1)
                {
                    direction[2] = true;
                    indexSprites = 8;
                    contNumSpritesAnimation = 0;
                    directionLeft[2] = true;
                    moveTo = X + 100;
                }
            }
            direction[3] = directionLeft[3];
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
