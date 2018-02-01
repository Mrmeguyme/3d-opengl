package com.game3dapplication;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

/**
 *
 * @author Josh
 */
public class Draw
{
    public static float checkDistance(float x, float y, float z, float playerX, float playerY, float playerZ)
    {
        float distanceX;
        float distanceY;
        float distanceZ;
        
        //Checking if the player is in a distance worth rendering
        if (x > 0) {
            distanceX = x - playerX;
            
        } else {
            distanceX = x + playerX;
        }
        
        if (distanceX < 0)
            distanceX = distanceX * -1;
        
        if (y > 0) {
            distanceY = y - playerY;
            
        } else {
            distanceY = y + playerY;
        }
        if (distanceY < 0)
            distanceY = distanceY * -1;
        
        if (z > 0) {
            distanceZ = z - playerZ;
            
        } else {
            distanceZ = z + playerZ;
        }
        if (distanceZ < 0)
            distanceZ = distanceZ * -1;
        
        
        return distanceX + distanceY + distanceZ;
    }
    
    public static BoundsBox cube(float x, float y, float z, float playerX, float playerY, float playerZ, String type)
    {
        float totalDistance = checkDistance(x, y, z, playerX, playerY, playerZ);
        
        if (totalDistance < 100) {
            glPushMatrix();
            {
                glTranslatef(x, y, z);
                glBegin(GL_QUADS);
                    {


                        //Front Face - Checked
                        glTexCoord2f(0, 1); glVertex3f(1, 1, 1);
                        glTexCoord2f(0, 0); glVertex3f(1, -1, 1);
                        glTexCoord2f(1, 0); glVertex3f(-1, -1, 1);
                        glTexCoord2f(1, 1); glVertex3f(-1, 1, 1);

                        //Back Face - Checked
                        glTexCoord2f(0, 0); glVertex3f(-1, -1, -1);
                        glTexCoord2f(0, 1); glVertex3f(-1, 1, -1);
                        glTexCoord2f(1, 1); glVertex3f(1, 1, -1);
                        glTexCoord2f(1, 0); glVertex3f(1, -1, -1);

                        //Bottom Face - Checked
                        glTexCoord2f(1, 1); glVertex3f(-1, -1, -1);
                        glTexCoord2f(0, 1); glVertex3f(-1, -1, 1);
                        glTexCoord2f(0, 0); glVertex3f(-1, 1, 1);
                        glTexCoord2f(1, 0); glVertex3f(-1, 1, -1);

                        //Top Face - Checked
                        glTexCoord2f(1, 1); glVertex3f(1, -1, -1);
                        glTexCoord2f(0, 1); glVertex3f(1, -1, 1);
                        glTexCoord2f(0, 0); glVertex3f(1, 1, 1);
                        glTexCoord2f(1, 0); glVertex3f(1, 1, -1);

                        //Left Face
                        glTexCoord2f(0, 0); glVertex3f(1, -1, -1);
                        glTexCoord2f(0, 1); glVertex3f(-1, -1, -1);
                        glTexCoord2f(1, 1); glVertex3f(-1, -1, 1);
                        glTexCoord2f(1, 0); glVertex3f(1, -1, 1);

                        //Right Face
                        glTexCoord2f(1, 0); glVertex3f(-1, 1, 1);
                        glTexCoord2f(1, 1); glVertex3f(1, 1, 1);
                        glTexCoord2f(0, 0); glVertex3f(1, 1, -1);
                        glTexCoord2f(0, 1); glVertex3f(-1, 1, -1);
                        
                    }
                glEnd();
            }
            glPopMatrix();
        }
        return new BoundsBox(x, y, z, 1, type);
    }
}
