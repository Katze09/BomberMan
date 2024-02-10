/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Random;

/**
 *
 * @author Katze
 */
public class Loader
{

    public static Array<Texture> LoadArraysprites(String[] fileName, int cantTextures)
    {
        Array<Texture> sprites = new Array<>();
        int cont = 1;
        boolean textureLoaded = true;
        for (int i = 0; i < cantTextures; i++)
            while (textureLoaded)
            {
                String filePath = fileName[i] + cont + ".png";
                try
                {
                    sprites.add(new Texture(filePath));
                    cont++;
                } catch (GdxRuntimeException e)
                {
                    System.out.println("No se pudo cargar la textura: " + filePath);
                    textureLoaded = false;
                }
            }
        return sprites;
    }

    public static Texture LoadTexture(String fileName)
    {
        return new Texture(fileName + ".png");
    }

    public static int getRandomNum(int i, int j)
    {
        return new Random().nextInt(i, j);
    }
}
