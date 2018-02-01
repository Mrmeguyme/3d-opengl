package com.game3dapplication;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

/**
 *
 * @author Josh
 */
public class Camera
{
    //Transform
    private float x;
    private float y;
    private float z;
    private float rx;
    private float ry;
    private float rz;

    private float fov;
    private float ar;
    private float ncp;
    private float fcp;

    public Camera(float fov, float ar, float ncp, float fcp, float x, float z)
    {
        this.x = x;
        y = 0;
        this.z = z;

        rx = 0;
        ry = 0;
        rz = 0;

        this.fov = fov;
        this.ar = ar;
        this.ncp = ncp;
        this.fcp = fcp;
        
        initProjection();
    }
    
    private void initProjection()
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, ar, ncp, fcp);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0.3f, 0.3f, 1.0f, 1);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void useView()
    {
        glRotatef(rx, 1, 0, 0);
        glRotatef(ry, 0, 1, 0);
        glRotatef(rz, 0, 0, 1);
        
        glTranslatef(x, y, z);
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getZ()
    {
        return z;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public float getRX()
    {
        return rx;
    }

    public float getRY()
    {
        return ry;
    }

    public float getRZ()
    {
        return rz;
    }

    public void setRX(float rx)
    {
        this.rx = rx;
    }

    public void setRY(float ry)
    {
        this.ry = ry;
    }

    public void setRZ(float rz)
    {
        this.rz = rz;
    }

    public void move(float amt, float dir)
    {
        this.z += amt * Math.sin(Math.toRadians(ry + 90 * dir));
        this.x += amt * Math.cos(Math.toRadians(ry + 90 * dir));
    }

    public void rotateY(float amt)
    {
        ry += amt;
    }

    public void rotateX(float amt)
    {
        this.rx -= amt;
    }
    
    public void beginCrouch()
    {
        this.y += 0.1f;
    }
    
    public void endCrouch()
    {
        this.y -= 0.1f;
    }
    
    public void beginJump()
    {
        this.y -= 0.2;
    }
    
    public void endJump()
    {
        this.y += 0.19f;
    }
}
