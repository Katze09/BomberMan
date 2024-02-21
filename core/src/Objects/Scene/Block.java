/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects.Scene;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Katze
 */
public class Block extends Obstacle
{

    private final int indexX;
    private final int indexY;
    private final int score;

    public Block(Texture sprite, float X, float Y)
    {
        super(sprite, X, Y);
        this.indexX = (int) (X * 0.01);
        this.indexY = (int) (Y * 0.01);
        score = 5;
    }

    @Override
    public void update(float deltaTime)
    {

    }

    public int getIndexX()
    {
        return indexX;
    }

    public int getIndexY()
    {
        return indexY;
    }
    
    public int getScore()
    {
        return score;
    }

    @Override
    public void animationBase()
    {
    }

}
