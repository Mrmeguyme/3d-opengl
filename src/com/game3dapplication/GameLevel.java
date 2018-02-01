package com.game3dapplication;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Josh
 */
public class GameLevel {
    private static BufferedImage mapFile;
    private String jsonFileName;
    private static float camX;
    private static float camZ;
//    private JSONObject jsonContents;
    private static FastRGB fastRGB;
    //Textures
    private static Texture dirt;
    private static Texture stoneWall;
    private static Texture grass;
    private static Texture wood;
    private static Texture leaves;
    private static Texture water;
    
    public GameLevel(BufferedImage mapFile, String jsonFileName)
    {
        this.mapFile = mapFile;
        this.jsonFileName = jsonFileName;
        /*
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("res/" + jsonFileName));
        } catch (IOException iOException) {
            Logger.getLogger(GameLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, iOException);
        } catch (ParseException parseException) {
            Logger.getLogger(GameLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, parseException);
        }
        jsonContents =  (JSONObject)obj;
        
        */
        
        loadTextures();
    }
    
    private static void loadTextures()
    {
        dirt = loadTexture("dirt");
        stoneWall = loadTexture("stoneWall");
        grass = loadTexture("grass");
        wood = loadTexture("wood");
        leaves = loadTexture("leaves");
        water = loadTexture("water");
    }
    
    private static Texture loadTexture(String key)
    {
        try {
            return TextureLoader.getTexture("png", new FileInputStream(new File("res/" + key + ".png")));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void load()
    {
        FastRGB fastRGB = new FastRGB(mapFile);
        for (int i = 0; i < mapFile.getHeight(); i++) {
            for (int j = 0; j < mapFile.getWidth(); j++) {
                Color c = new Color(fastRGB.getRGB(j, i));
                switch (c.getRed()) {
                    case 1: {
                        camX = j;
                        camZ = i;
                        break;
                    }
                }
            }
        }
    }
    
    public Collision render(float camX, float camZ, Collision collisionObj)
    {
        FastRGB fastRGB = new FastRGB(mapFile); 
        BoundsBox box;
        collisionObj.clearBoxes();
        for (int i = 0; i < mapFile.getHeight(); i+= 1) {
            for (int j = 0; j < mapFile.getWidth(); j+= 1) {
                if (Draw.checkDistance(-camX, 0, -camZ, j*2, 0, i*2) < 100) {
                    Color c = new Color(fastRGB.getRGB(j, i));
                    switch (c.getRed()) {
                        case 255: {
                            dirt.bind();
                            box = Draw.cube(j*2, -4, i*2, -camX, 0, -camZ, "dirt");
                            collisionObj.addBox(box);
                            break;
                        }
                        case 254: {
                            stoneWall.bind();
                            for (int k = -4; k < 2; k+= 2) {
                                box = Draw.cube(j*2, k, i*2, -camX, 0, -camZ, "stoneWall");
                                collisionObj.addBox(box);
                            }
                            break;
                        }
                        case 253: {
                            grass.bind();
                            box = Draw.cube(j*2, -4, i*2, -camX, 0, -camZ, "grass");
                            collisionObj.addBox(box);
                            break;
                        }
                        case 252: {
                            wood.bind();
                            for (int k = -4; k < 4; k+= 2) {
                                box = Draw.cube(j*2, k, i*2, -camX, 0, -camZ, "wood");
                                collisionObj.addBox(box);
                            }
                            leaves.bind();
                            box = Draw.cube(j*2, 4, i*2, -camX, 0, -camZ, "leaves");
                            collisionObj.addBox(box);
                            box = Draw.cube(j*2 + 2, 4, i*2, -camX, 0, -camZ, "leaves");
                            collisionObj.addBox(box);
                            box = Draw.cube(j*2, 6, i*2, -camX, 0, -camZ, "leaves");
                            collisionObj.addBox(box);
                            box = Draw.cube(j*2, 4, i*2 + 2, -camX, 0, -camZ, "leaves");
                            collisionObj.addBox(box);
                            box = Draw.cube(j*2, 4, i*2 - 2, -camX, 0, -camZ, "leaves");
                            collisionObj.addBox(box);
                            box = Draw.cube(j*2 - 2, 4, i*2, -camX, 0, -camZ, "leaves");
                            collisionObj.addBox(box);
                            break;
                        }
                        case 251: {
                            water.bind();
                            box = Draw.cube(j*2, -4, i*2, -camX, 0, -camZ, "water");
                            collisionObj.addBox(box);
                            break;
                        }
                        case 1: {
                            grass.bind();
                            box = Draw.cube(j*2, -4, i*2, -camX, 0, -camZ, "grass");
                            collisionObj.addBox(box);
                            break;
                        }
                    }
                }
            }
        }
        return collisionObj;
    }
    
    public float getCamX()
    {
        return camX;
    }
    
    public float getCamZ()
    {
        return camZ;
    }
}
