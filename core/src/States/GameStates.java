/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package States;

import Objects.PowerUps;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import Objects.*;
import Objects.Enemies.Enemy;
import Objects.Scene.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 *
 * @author Katze
 */
public class GameStates implements GameMethods
{

    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();

    public OrthographicCamera camera;
    private Player player;
    public static Array<Bomb> bombs;
    private final Array<Texture> spritesBombs;
    public static Array<Map> map;
    private final Array<Integer> worldWidth;
    //private final int worldHeight;
    private final BitmapFont text;
    private final BitmapFont textTitle;
    private int score;
    private float followPlayerMoveMap;
    public static int indexMap;
    public static boolean passLevel = false;
    public static float delayPassLevel;

    public GameStates()
    {
        map = new Array<>();
        worldWidth = new Array<>();
        indexMap = 0;
        for (int i = 0; i < 5; i++)
        {
            map.add(new Map(31, 13, 120, 15, i + 1));
            worldWidth.add(map.get(i).Xsize * 100);
        }
        //map = new Map(31, 13, 120, 15, 10);
        delayPassLevel = 0;
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        followPlayerMoveMap = score = 0;
        bombs = new Array<>();
        text = Loader.LoadFont("8-bit Arcade In", 100);
        text.setColor(Color.WHITE);
        textTitle = Loader.LoadFont("8-bit Arcade In", 200);
        textTitle.setColor(Color.WHITE);
        String[] fileNames = new String[10];
        fileNames[0] = "Bomb";
        spritesBombs = Loader.LoadArraysprites(fileNames, 1, "");

        fileNames[0] = "Player";
        player = new Player(Loader.LoadArraysprites(fileNames, 1, "Player"), 100, 100);

        //enemies = new Array<>();
        //enemies.add(new Enemy(spritesEnemy, 100, 900));
    }

    public void keyBoardDown(int keycode)
    {
        player.keyBoardDown(keycode);
        if (Input.Keys.E == keycode && player.canPutBomb())
        {
            if (player.moveTo > 0)
                if (player.direction[0] || player.direction[1])
                {
                    bombs.add(new Bomb(spritesBombs, player.getX(), player.moveTo, true, player.getSizeExplosion()));
                    map.get(indexMap).setCharacter(player.getX(), player.moveTo, '^');
                } else
                {
                    bombs.add(new Bomb(spritesBombs, player.moveTo, player.getY(), true, player.getSizeExplosion()));
                    map.get(indexMap).setCharacter(player.moveTo, player.getY(), '^');
                }
            else
            {
                bombs.add(new Bomb(spritesBombs, player.getX(), player.getY(), true, player.getSizeExplosion()));
                map.get(indexMap).setCharacter(player.getX(), player.getY(), '^');
            }
            player.reduceNumBombs();
        }else if(Input.Keys.SPACE == keycode && player.isDead())
            resetMap();
    }

    private void resetMap()
    {
        Map mapTemp = map.get(indexMap);
        map.set(indexMap, new Map(mapTemp.Xsize, mapTemp.Ysize, mapTemp.maxNumBreakableBlocks, mapTemp.maxNumPowerUp, mapTemp.numEnemies));
        String[] fileNames = new String[1];
        fileNames[0] = "Player";
        player = new Player(Loader.LoadArraysprites(fileNames, 1, "Player"), 100, 100, player.getLife());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        followPlayerMoveMap = 0;
        bombs = new Array<>();
        if(player.getLife() < 0)
            Main.start = false;
    }

    public void keyBoardUp(int keycode)
    {
        player.keyBoardUp(keycode);
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        map.get(indexMap).draw(batch);
        for (Enemy enemy : map.get(indexMap).enemies)
            enemy.draw(batch);
        if (!player.isDead())
            player.draw(batch);
        for (int i = 0; i < bombs.size; i++)
            bombs.get(i).draw(batch);
        text.draw(batch, "Score " + score, 10 + followPlayerMoveMap, 1360);
        text.draw(batch, "Lifes " + player.getLife(), 550 + followPlayerMoveMap, 1360);
        text.draw(batch, "Level  " + indexMap + 1, 1050 + followPlayerMoveMap, 1360);
        if (player.isDead())
            deadEvent(batch);
        if(passLevel)
            levelPassEvent(batch);
        batch.setProjectionMatrix(camera.combined);
    }

    private void deadEvent(SpriteBatch batch)
    {
        if(player.getLife() >= 0)
        {
            textTitle.draw(batch, "You died", 400 + followPlayerMoveMap, 800);
            text.draw(batch, "Press Scape To continue", 300 + followPlayerMoveMap, 700);
        }   else {
            textTitle.draw(batch, "You lose", 400 + followPlayerMoveMap, 800);
            text.draw(batch, "Press Scape To", 420 + followPlayerMoveMap, 700);
            text.draw(batch, " Go To The Menu", 410 + followPlayerMoveMap, 600);
        }
    }

    private void levelPassEvent(SpriteBatch batch)
    {
        textTitle.draw(batch, "Level Pass " + indexMap + 1, 200 + followPlayerMoveMap, 800);
        if(delayPassLevel <= 2.5)
            text.draw(batch, "Passing Level " + indexMap + 2, 450 + followPlayerMoveMap, 700);
        if (delayPassLevel < 0)
        {
            passLevel = false;
            indexMap++;
            String[] fileNames = new String[1];
            fileNames[0] = "Player";
            player = new Player(Loader.LoadArraysprites(fileNames, 1, "Player"), 100, 100, player.getLife());
            camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
            followPlayerMoveMap = 0;
            bombs = new Array<>();
        }
    }

    @Override
    public void update(float deltaTime)
    {
        if(!passLevel) {
            if (!player.isDead())
                player.update(deltaTime);

            for (int i = 0; i < map.get(indexMap).enemies.size; i++) {
                map.get(indexMap).enemies.get(i).update(deltaTime);
                map.get(indexMap).enemies.get(i).createBomb(bombs, spritesBombs, i);
            }

            for (int i = 0; i < bombs.size; i++) {
                Bomb bomb = bombs.get(i);
                bomb.update(deltaTime);
                checkCollisionsWithOtherBombs(i);
                checkCollisionWithBlockBreakables(bomb);
                checkCollisionWithPlayer(bomb);
                checkCollisionWithEnemy(bomb);
                if (bomb.isDead()) {
                    map.get(indexMap).setCharacter(bomb.getX(), bomb.getY(), '*');
                    bombs.removeIndex(i);
                    if (bomb.isFromPlayer)
                        player.increaseNumBombs();
                    else
                        try {
                            map.get(indexMap).enemies.get(bomb.getIndexEnemy()).putBomb = false;
                        } catch (IndexOutOfBoundsException ex) {
                            System.err.println(ex.getMessage());
                        }
                }
            }

            for (int i = 0; i < map.get(indexMap).PowerUps.size; i++)
                if (checkCollsionWithPowerUps(map.get(indexMap).PowerUps.get(i)))
                    map.get(indexMap).PowerUps.removeIndex(i);
        }
        if(delayPassLevel > 0)
            delayPassLevel -= deltaTime;
        moveCamera(deltaTime);
    }

    private void moveCamera(float deltaTime)
    {
        float X = player.getX();

        if ((camera.position.x + 300) < X && camera.position.x <= (worldWidth.get(indexMap) - 750))
        {
            camera.translate((player.getSpeed() * deltaTime), 0);
            if (camera.position.x > 750 && camera.position.x < (worldWidth.get(indexMap) - 750))
                followPlayerMoveMap += player.getSpeed() * deltaTime;
        }
        if ((camera.position.x - 400) > X && camera.position.x >= 750)
        {
            camera.translate(-1 * (player.getSpeed() * deltaTime), 0);
            if (camera.position.x > 750)
                followPlayerMoveMap -= player.getSpeed() * deltaTime;
        }
        camera.position.x = (camera.position.x < 750) ? 750 : camera.position.x;
        camera.position.x = (camera.position.x > (worldWidth.get(indexMap) - 750)) ? (worldWidth.get(indexMap) - 750)
                : camera.position.x;
        camera.update();
    }

    private void checkCollisionsWithOtherBombs(int currentBombIndex)
    {
        Bomb currentBomb = bombs.get(currentBombIndex);
        if (currentBomb.isExplode)
            for (int otherBombIndex = 0; otherBombIndex < bombs.size; otherBombIndex++)
                if (currentBombIndex != otherBombIndex)
                    for (Explosion explosion : currentBomb.explosion)
                        if (explosion.checkCollsision(bombs.get(otherBombIndex)))
                        {
                            Bomb otherBomb = bombs.get(otherBombIndex);
                            if (!otherBomb.isExplode)
                                otherBomb.delayExplode = 0;
                        }
    }

    private void checkCollisionWithBlockBreakables(Bomb bomb)
    {
        for (Explosion explosion : bomb.explosion)
            for (Block blockBreakable : map.get(indexMap).BlockBreakable)
                if (explosion.checkCollsision(blockBreakable))
                {
                    map.get(indexMap).setCharacter(blockBreakable.getIndexX(), blockBreakable.getIndexY(), '*');
                    //map.map[blockBreakable.getIndexY()][blockBreakable.getIndexX()] = '*';
                    if (bomb.isFromPlayer)
                        score += blockBreakable.getScore();
                    map.get(indexMap).BlockBreakable.removeValue(blockBreakable, true);
                }
    }

    private void checkCollisionWithPlayer(Bomb bomb)
    {
        for (Explosion explosion : bomb.explosion)
            if (explosion.checkCollsision(player))
            {
                if(!player.isDead())
                    player.reduceLife();
                player.setDead(true);
            }
    }

    private void checkCollisionWithEnemy(Bomb bomb)
    {
        for (Explosion explosion : bomb.explosion)
            for (Enemy enemy : map.get(indexMap).enemies)
                if (explosion.checkCollsision(enemy))
                    map.get(indexMap).enemies.removeValue(enemy, false);
    }

    private boolean checkCollsionWithPowerUps(PowerUps PowerUp)
    {
        if (PowerUp.checkCollsision(player))
        {
            PowerUp.aplicateEffect(player);
            return true;
        }
        return false;
    }

    @Override
    public void dispose()
    {
        player.dispose();
        for (Bomb bomb : bombs)
            bomb.dispose();
    }

}
