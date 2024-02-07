/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Scene;

import Objects.GameMethods;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Katze
 */
public class Map implements GameMethods
{

    private Block BlockUnbreakable[];
    private char[][] map;

    public Map()
    {
        map = new char[11][15];
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 15; j++)
                map[i][j] = '*';
        setUnbreakableBlocks();
        

    }
    
    public boolean canMoveTo(int x, int y)
    {
        x = (int) (x * 0.01f);
        y = (int) (y * 0.01f);
        return (map[y][x] != '#');
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        for (Block BlockUnbreakable : this.BlockUnbreakable)
            BlockUnbreakable.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {
    }

    @Override
    public void dispose()
    {
    }

    private void setUnbreakableBlocks()
    {
        BlockUnbreakable = new Block[72];
        int mapX = 0;
        int mapY = 0;
        Texture blockTexture = new Texture("BlockUnbreakable.png");
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
