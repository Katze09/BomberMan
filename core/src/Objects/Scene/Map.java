/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Scene;

import Objects.PowerUps;
import Objects.GameMethods;
import States.Loader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class Map implements GameMethods
{

    private Block BlockUnbreakable[];
    public Array<Block> BlockBreakable;
    public Array<PowerUps> PowerUps;
    public char[][] map;

    public Map()
    {
        map = new char[11][15];
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 15; j++)
                map[i][j] = '*';
        setUnbreakableBlocks();
        map[1][1] = map[1][2] = map[2][1] = map[9][1] = map[9][2] = map[8][1] = map[1][12] = map[1][13] = map[2][13] = map[9][12] = map[9][13] = map[8][13] = '!';
        setBreakableBlocks();

    }

    public int canMoveTo(int x, int y)
    {
        x = (int) (x * 0.01f);
        y = (int) (y * 0.01f);
        switch (map[y][x])
        {
            case '#':
                return 0;
            case '+':
                return 1;
        }
        return -1;
    }

    public boolean thereBombInLine(int x, int y, boolean direction)
    {
        x = (int) (x * 0.01);
        y = (int) (y * 0.01);
        if (direction)
        {
            for (int i = 1; i < 15; i++)
                if (map[y][i] == '^')
                    return true;
        } else
            for (int i = 1; i < 11; i++)
                if (map[i][x] == '^')
                    return true;
        return false;
    }

    public boolean thereBombNear(int x, int y, boolean direction, boolean backOrForward)
    {
        x = (int) (x * 0.01);
        y = (int) (y * 0.01);
        try
        {
            if (direction)
            {
                if (backOrForward)
                {
                    for (int i = x; i < x + 3; i++)
                        if (map[y][i] == '^')
                            return true;
                } else
                    for (int i = x; i > x - 3; i--)
                        if (map[y][i] == '^')
                            return true;
            } else if (backOrForward)
            {
                for (int i = y; i < y + 3; i++)
                    if (map[i][x] == '^')
                        return true;
            } else
                for (int i = y; i > y - 3; i--)
                    if (map[i][x] == '^')
                        return true;
        } catch (ArrayIndexOutOfBoundsException ex)
        {
            System.err.println("This error is normal and you shouldn't worry: " + ex.getMessage());
        }
        return false;
    }

    public boolean setCharacter(float X, float Y, char character)
    {
        int x = (int) (X * 0.01);
        int y = (int) (Y * 0.01);
        return setCharacter(x, y, character);
    }

    public boolean setCharacter(int X, int Y, char character)
    {
        try
        {
            map[Y][X] = character;
            return true;
        } catch (ArrayIndexOutOfBoundsException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        for (Block BlockUnbreakable : this.BlockUnbreakable)
            BlockUnbreakable.draw(batch);
        for (PowerUps PowerUp : this.PowerUps)
            PowerUp.draw(batch);
        for (Block Blockbreakable : this.BlockBreakable)
            Blockbreakable.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 10; i >= 0; i--)
        {
            for (int j = 0; j < 15; j++)
                System.out.print(map[i][j]);
            System.out.println("");
        }
        System.out.println("");
    }

    @Override
    public void dispose()
    {
    }

    private void setBreakableBlocks()
    {
        BlockBreakable = new Array<>();
        PowerUps = new Array<>();
        Texture blockTexture = Loader.LoadTexture("BlockBreakable");
        int numBlocks = Loader.getRandomNum(60, 80);
        int door = Loader.getRandomNum(0, numBlocks);
        int numPowerUps = Loader.getRandomNum(5, 20);
        int[] powerUps = new int[numPowerUps];
        for (int i = 0; i < numPowerUps; i++)
        {
            boolean isRepited = false;
            powerUps[i] = Loader.getRandomNum(0, numBlocks);
            for (int j = 0; j < i; j++)
                if (powerUps[i] == powerUps[j])
                {
                    isRepited = true;
                    break;
                }
            if (powerUps[i] == door || isRepited)
                i--;
        }
        for (int i = 0; i < numBlocks; i++)
        {
            int x = Loader.getRandomNum(1, 15);
            int y = Loader.getRandomNum(1, 11);
            while (map[y][x] == '#' || map[y][x] == '+' || map[y][x] == '!')
            {
                x = Loader.getRandomNum(1, 15);
                y = Loader.getRandomNum(1, 11);
            }
            float X = x * 100;
            float Y = y * 100;
            if (i == door)
                //BlockBreakable.add(new Block(blockTexture, X, Y, 0));
                PowerUps.add(new PowerUps(X, Y, 0));
            else
                for (int j = 0; j < powerUps.length; j++)
                    if (powerUps[j] == i)
                    {
                        //BlockBreakable.add(new Block(blockTexture, X, Y, Loader.getRandomNum(1, 5)));
                        PowerUps.add(new PowerUps(X, Y, Loader.getRandomNum(1, 5)));
                        break;
                    }
            BlockBreakable.add(new Block(blockTexture, X, Y));
            map[y][x] = '+';
        }
    }

    private void setUnbreakableBlocks()
    {
        BlockUnbreakable = new Block[72];
        int mapX = 0;
        int mapY = 0;
        Texture blockTexture = Loader.LoadTexture("BlockUnbreakable");
        int cont = 0;
        for (int j = 0; j < 2800; j += 1400)
        {
            for (int i = 0; i < 1100; i += 100)
            {
                BlockUnbreakable[cont] = new Block(blockTexture, j, i);
                map[mapY][mapX] = '#';
                mapY++;
                cont++;
            }
            mapY = 0;
            mapX = 14;
        }
        mapX = 1;
        mapY = 0;
        for (int j = 0; j < 2000; j += 1000)
        {
            for (int i = 100; i < 1400; i += 100)
            {
                BlockUnbreakable[cont] = new Block(blockTexture, i, j);
                map[mapY][mapX] = '#';
                mapX++;
                cont++;
            }
            mapX = 1;
            mapY = 10;
        }
        mapX = 2;
        mapY = 2;
        for (int i = 200; i < 1000; i += 200)
        {
            for (int j = 200; j < 1400; j += 200)
            {
                BlockUnbreakable[cont] = new Block(blockTexture, j, i);
                map[mapY][mapX] = '#';
                mapX += 2;
                cont++;
            }
            mapX = 2;
            mapY += 2;
        }
    }

}
