/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import States.GameStates;
import States.Loader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Katze
 */
public class Bomb extends Object
{

    public float delayExplode;
    public final Array<Explosion> explosion;
    private final Array<Texture> explosionSpriteBase;
    private final Array<Texture> explosionSpriteMid;
    private final Array<Texture> explosionSpriteFinal;
    private final boolean[] directionExplo;
    public boolean isExplode;
    int contEx;

    public Bomb(Array<Texture> sprites, float X, float Y)
    {
        super(sprites, X, Y);
        explosion = new Array<>();
        contEx = 0;
        String[] nameFile = new String[10];
        nameFile[0] = "ExplosionBase";
        explosionSpriteBase = Loader.LoadArraysprites(nameFile, 7);
        nameFile[0] = "ExplosionMid";
        explosionSpriteMid = Loader.LoadArraysprites(nameFile, 7);
        nameFile[0] = "ExplosionFinal";
        explosionSpriteFinal = Loader.LoadArraysprites(nameFile, 7);
        delayExplode = 2f;
        directionExplo = new boolean[4];
        isExplode = false;
    }

    @Override
    public void update(float deltaTime)
    {
        if (delayExplode > 0)
            delayExplode -= deltaTime;
        else
        {
            switch (contEx)
            {
                case 0:
                    isExplode = true;
                    explosion.add(new Explosion(explosionSpriteBase, X, Y));
                    delayExplode = 0.01f;
                    break;
                case 1:
                    if (directionExplo[0] = GameStates.map.canMoveTo((int) (X - 100), (int) Y) != 0)
                        explosion.add(new Explosion(explosionSpriteMid, X - 100, Y));
                    if (directionExplo[1] = GameStates.map.canMoveTo((int) (X + 100), (int) Y) != 0)
                        explosion.add(new Explosion(explosionSpriteMid, X + 100, Y));
                    if (directionExplo[2] = GameStates.map.canMoveTo((int) X, (int) (Y + 100)) != 0)
                        explosion.add(new Explosion(explosionSpriteMid, X, (Y + 100), 90));
                    if (directionExplo[3] = GameStates.map.canMoveTo((int) X, (int) (Y - 100)) != 0)
                        explosion.add(new Explosion(explosionSpriteMid, X, (Y - 100), 90));
                    delayExplode = 0.01f;
                    break;
                case 2:
                    if (directionExplo[0] && GameStates.map.canMoveTo((int) (X - 200), (int) Y) != 0)
                        explosion.add(new Explosion(explosionSpriteFinal, X - 200, Y, 180));
                    if (directionExplo[1] && GameStates.map.canMoveTo((int) (X + 200), (int) Y) != 0)
                        explosion.add(new Explosion(explosionSpriteFinal, X + 200, Y));
                    if (directionExplo[2] && GameStates.map.canMoveTo((int) X, (int) (Y + 200)) != 0)
                        explosion.add(new Explosion(explosionSpriteFinal, X, (Y + 200), 90));
                    if (directionExplo[3] && GameStates.map.canMoveTo((int) X, (int) (Y - 200)) != 0)
                        explosion.add(new Explosion(explosionSpriteFinal, X, (Y - 200), -90));
                    delayExplode = 0.5f;
                    break;
                default:
                    dead = true;
                    for (int i = 0; i < explosion.size; i++)
                        if (!explosion.get(i).isDead())
                        {
                            dead = false;
                            break;
                        }
                    break;
            }
            contEx++;
        }
        for (int i = 0; i < explosion.size; i++)
            explosion.get(i).update(deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        if (delayExplode > 0)
            batch.draw(sprites.get(indexSprites), X, Y);
        for (int i = 0; i < explosion.size; i++)
            explosion.get(i).draw(batch);
    }

    public float getDelayExplode()
    {
        return delayExplode;
    }

}
