/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Enemies;

import Objects.Bomb;
import Objects.Object;
import States.GameStates;
import static States.GameStates.map;
import States.Loader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class Enemy extends Object
{

    protected boolean isMoving;
    protected boolean[] direction;
    protected float moveTo;
    public boolean putBomb;
    protected int[][] runAwayBomb;

    public Enemy(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        putBomb = isMoving = false;
        direction = new boolean[]
        {
            false, false, false, false
        };
        runAwayBomb = new int[10][4];
        moveTo = 0;
        speed = 500;
        float X1HitBox = X + (WIDTH * hitBoxMultiplication);
        float X2HitBox = WIDTH - 2 * (WIDTH * hitBoxMultiplication); // Corregido
        float Y1HitBox = (Y + 25) + (HEIGHT * hitBoxMultiplication);
        float Y2HitBox = (HEIGHT - 25) - 2 * ((HEIGHT - 25) * hitBoxMultiplication); // Corregido
        hitBox = new Rectangle(X1HitBox, Y1HitBox, X2HitBox, Y2HitBox);
    }

    @Override
    public void animationBase()
    {

    }

    protected void moveTo()
    {
        if (!isMoving && Loader.getRandomNum(0, 10) == 5)
        {
            int attemps = 0;
            boolean move = false;
            int direcion = -1;
            while (!move && attemps <= 4)
            {
                if (!putBomb)
                    direcion = Loader.getRandomNum(0, 7);
                else
                    direcion++;
                switch (direcion)
                {
                    case 0:
                        if ((map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y + 100)) == -1 && !map.get(GameStates.indexMap).thereBombInLine((int) X, (int) (Y + 100), true)
                                && !map.get(GameStates.indexMap).thereBombNear((int) X, (int) (Y + 100), false, true))/* || map.thereBombNear((int) X, (int) (Y + 100), false, false)*/)
                        {
                            move = isMoving = this.direction[0] = true;
                            moveTo = Y + 100;
                        }
                        break;
                    case 1:
                        if ((map.get(GameStates.indexMap).canMoveTo((int) X, (int) (Y - 100)) == -1 && !map.get(GameStates.indexMap).thereBombInLine((int) X, (int) (Y - 100), true)
                                && !map.get(GameStates.indexMap).thereBombNear((int) X, (int) (Y - 100), false, false))/* || map.thereBombNear((int) X, (int) (Y - 100), false, true)*/)
                        {
                            move = isMoving = this.direction[1] = true;
                            moveTo = Y - 100;
                        }
                        break;
                    case 2:
                        if ((map.get(GameStates.indexMap).canMoveTo((int) (X + 100), (int) Y) == -1 && !map.get(GameStates.indexMap).thereBombInLine((int) (X + 100), (int) Y, false)
                                && !map.get(GameStates.indexMap).thereBombNear((int) (X + 100), (int) (Y), true, true))/* || map.thereBombNear((int) (X + 100), (int) (Y), true, false)*/)
                        {
                            move = isMoving = this.direction[2] = true;
                            moveTo = X + 100;
                        }
                        break;
                    case 3:
                        if ((map.get(GameStates.indexMap).canMoveTo((int) (X - 100), (int) Y) == -1 && !map.get(GameStates.indexMap).thereBombInLine((int) (X - 100), (int) Y, false)
                                && !map.get(GameStates.indexMap).thereBombNear((int) (X - 100), (int) (Y), true, false))/* || map.thereBombNear((int) (X - 100), (int) (Y), true, true)*/)
                        {
                            move = isMoving = this.direction[3] = true;
                            moveTo = X - 100;
                        }
                        break;
                }

                attemps++;
            }
        }
    }

    protected void move(float deltaTime)
    {
        if (isMoving)
        {
            if (direction[0])
                if (Y < moveTo)
                    setY(Y + (speed * deltaTime));
                else
                {
                    setY(moveTo);
                    moveTo = 0;
                    direction[0] = isMoving = false;
                }
            if (direction[1])
                if (Y > moveTo)
                    setY(Y - (speed * deltaTime));
                else
                {
                    setY(moveTo);
                    moveTo = 0;
                    direction[1] = isMoving = false;
                }
            if (direction[2])
                if (X < moveTo)
                    setX(X + (speed * deltaTime));
                else
                {
                    setX(moveTo);
                    moveTo = 0;
                    direction[2] = isMoving = false;
                }
            if (direction[3])
                if (X > moveTo)
                    setX(X - (speed * deltaTime));
                else
                {
                    setX(moveTo);
                    moveTo = 0;
                    direction[3] = isMoving = false;
                }
        }
    }

    public void createBomb(Array<Bomb> bombs, Array<Texture> spriteBomb, int indexEnemy)
    {
        if (Loader.getRandomNum(0, 150) == 5 && !putBomb)
        {
            if (moveTo > 0)
                if (direction[0] || direction[1])
                {
                    bombs.add(new Bomb(spriteBomb, X, moveTo, false, 2, indexEnemy));
                    GameStates.map.get(GameStates.indexMap).setCharacter(X, moveTo, '^');
                } else
                {
                    bombs.add(new Bomb(spriteBomb, moveTo, Y, false, 2, indexEnemy));
                    GameStates.map.get(GameStates.indexMap).setCharacter(moveTo, Y, '^');
                }
            else
            {
                bombs.add(new Bomb(spriteBomb, X, Y, false, 2, indexEnemy));
                GameStates.map.get(GameStates.indexMap).setCharacter(X, Y, '^');
            }
            putBomb = true;

        }
    }

    @Override
    public void update(float deltaTime)
    {
        moveTo();
        move(deltaTime);
    }

    @Override
    public void setY(float Y)
    {
        this.Y = Y;
        vector3Position.y = Y;
        hitBox.setY((Y + 25) - ((HEIGHT - 25) * hitBoxMultiplication));
    }
}
