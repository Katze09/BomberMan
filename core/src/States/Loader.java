/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Random;

/**
 *
 * @author Katze
 */
public class Loader
{

    public static Array<Texture> LoadArraysprites(String[] fileName, int cantTextures, String folder)
    {
        Array<Texture> sprites = new Array<>();
        int cont = 1;
        boolean textureLoaded = true;
        for (int i = 0; i < cantTextures; i++)
            while (textureLoaded)
            {
                String filePath = (!folder.equals("")) ? (folder + "/" + fileName[i] + cont + ".png")
                        : (fileName[i] + cont + ".png");
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

    public static Texture LoadTexture(String fileName, String folder)
    {
        return (folder.equals("")) ? new Texture(fileName + ".png") : new Texture(folder + "/" + fileName + ".png");
    }

    public static BitmapFont LoadFont(String fileName, int size)
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/" + fileName + ".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size; // Tamano de la fuente
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // Importante: liberar recursos despu�s de generar la fuente
        return font;
    }

    public static int getRandomNum(int i, int j)
    {
        return new Random().nextInt(i, j);
    }
}
