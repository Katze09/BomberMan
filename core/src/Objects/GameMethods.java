/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Katze
 */
public interface GameMethods
{
    public void draw(SpriteBatch batch);
    public void update(float deltaTime);
    public void dispose();
}
