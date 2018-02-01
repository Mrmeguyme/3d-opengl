/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game3dapplication;

/**
 *
 * @author Josh
 */
public class BoundsBox {
    private float x;
    private float y;
    private float z;
    private float radius;
    private String type;
    
    public BoundsBox(float x, float y, float z, float radius, String type)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.radius = radius;
        this.type = type;
    }
    
    public boolean isCollided(BoundsBox box)
    {
        boolean condition = false;
        float distance = Draw.checkDistance(box.getX(), box.getY(), box.getZ(), x, y, z);
        if (distance > 3)
            return false;
        
        if (pythag3d(box.getX(), box.getY(), box.getZ(), x, y, z) < radius + box.getRadius()) {
            condition = true;
        }
        
        return condition;
    }
    
    public boolean isPlayerVerticallyCollided(BoundsBox box)
    {
        boolean condition = false;
        float distance = Draw.checkDistance(box.getX(), box.getY(), box.getZ(), x, y, z);
        if (distance > 5)
            return false;
        
        float checkFloat = box.getY() - y;
        if (checkFloat < 0)
            checkFloat *= -1;
        
        if (checkFloat < 4 && pythag(x - box.getX(), z - box.getZ()) < 1) {
            condition = true;
        }
        return condition;
    }
    
    private float pythag (float a, float b)
    {
        double asquared = a * a;
        double bsquared = b * b;
        
        double csquared = asquared + bsquared;
        float c = (float)Math.sqrt(csquared);
        return c;
    }
    
    private float pythag3d (float x, float y, float z, float x2, float y2, float z2)
    {
        float c = pythag(x - x2, z - z2);
        float d = pythag(c, y - y2);
        
        return d;
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
    
    public float getMinX()
    {
        return x - radius;
    }
    
    public float getMaxX()
    {
        return x + radius;
    }
    
    public float getMinY()
    {
        return y - radius;
    }
    
    public float getMaxY()
    {
        return y + radius;
    }
    
    public float getMinZ()
    {
        return z - radius;
    }
    
    public float getMaxZ()
    {
        return z + radius;
    }
    
    public float getRadius()
    {
        return radius;
    }
    
    public String getType()
    {
        return type;
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
    
    public void setRadius(float radius)
    {
        this.radius = radius;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
