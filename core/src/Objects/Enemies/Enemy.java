/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Enemies;

import Objects.Bomb;
import Objects.Object;
import static States.GameStates.map;
import States.Loader;
import com.badlogic.gdx.graphics.Texture;
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

    public Enemy(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        isMoving = false;
        direction = new boolean[]
        {
            false, false, false, false
        };
        moveTo = 0;
    }

    protected void moveTo()
    {
        if (!isMoving && Loader.getRandomNum(0, 10) == 5)
        {
            boolean move = false;
            while (!move)
            {
                int direcion = Loader.getRandomNum(0, 4);
                switch (direcion)
                {
                    case 0:
                        if (map.canMoveTo((int) X, (int) (Y + 100)) == -1)
                        {
                            move = isMoving = this.direction[0] = true;
                            moveTo = Y + 100;
                        }
                        break;
                    case 1:
                        if (map.canMoveTo((int) X, (int) (Y - 100)) == -1)
                        {
                            move = isMoving = this.direction[1] = true;
                            moveTo = Y - 100;
                        }
                        break;
                    case 2:
                        if (map.canMoveTo((int) (X + 100), (int) Y) == -1)
                        {
                            move = isMoving = this.direction[2] = true;
                            moveTo = X + 100;
                        }
                        break;
                    case 3:
                        if (map.canMoveTo((int) (X - 100), (int) Y) == -1)
                        {
                            move = isMoving = this.direction[3] = true;
                            moveTo = X - 100;
                        }
                        break;
                }
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
                    direction[0] = isMoving = false;
                }
            if (direction[1])
                if (Y > moveTo)
                    setY(Y - (speed * deltaTime));
                else
                {
                    setY(moveTo);
                    direction[1] = isMoving = false;
                }
            if (direction[2])
                if (X < moveTo)
                    setX(X + (speed * deltaTime));
                else
                {
                    setX(moveTo);
                    direction[2] = isMoving = false;
                }
            if (direction[3])
                if (X > moveTo)
                    setX(X - (speed * deltaTime));
                else
                {
                    setX(moveTo);
                    direction[3] = isMoving = false;
                }
        }
    }

    public void createBomb(Array<Bomb> bombs, Array<Texture> spriteBomb)
    {
        if (Loader.getRandomNum(0, 180) == 5)
            if (moveTo > 0)
                if (direction[0] || direction[1])
                    bombs.add(new Bomb(spriteBomb, X, moveTo, false));
                else
                    bombs.add(new Bomb(spriteBomb, moveTo, Y, false));
            else
                bombs.add(new Bomb(spriteBomb, X, Y, false));
    }

    @Override
    public void update(float deltaTime)
    {
        moveTo();
        move(deltaTime);
    }
}
