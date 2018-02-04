package com.game3dapplication;

import com.game3dapplication.util.BoundsBox;
import java.util.ArrayList;

/**
 *
 * @author Mrmeguyme
 */
public class Collision
{
    private ArrayList<BoundsBox> boxes;

    public Collision()
    {
        boxes = new ArrayList<>();
    }
    
    public void addBox(BoundsBox box)
    {
        boxes.add(box);
    }
    
    public void clearBoxes()
    {
        boxes.clear();
    }
    
    public boolean getCollision(BoundsBox box)
    {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isCollided(box))
                
                return true;
        }
        
        return false;
    }
    
    public int getIntCollision(BoundsBox box)
    {
        int value = 0;
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isCollided(box)) {
                float smallestValueX;
                float smallestValueZ;
                
                smallestValueX = getSmallest(boxes.get(i).getMinX() - box.getMaxX(), boxes.get(i).getMaxX() - box.getMinX());
                smallestValueZ = getSmallest(boxes.get(i).getMaxZ() - box.getMinZ(), boxes.get(i).getMinZ() - box.getMaxZ());
                
                if (smallestValueX < smallestValueZ) {
                    if (value == 2)
                        return 3;
                    else
                        value = 1;
                } else if (smallestValueX > smallestValueZ) {
                    if (value == 1)
                        return 3;
                    else
                        value = 2;
                }
                else if (smallestValueX == smallestValueZ) {
                    return 3;
                }
            }
        }
        
        return value;
    }
    
    public boolean isPlayerVerticallyColliding(BoundsBox box)
    {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isPlayerVerticallyCollided(box))
                return true;
        }
        
        return false;
    }
    
    public float setAboveCurBlock(float x, float z)
    {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getX() == x && boxes.get(i).getZ() == z) {
                return boxes.get(i).getY() + 4;
            }
        }
        return 0;
    }
    
    private float getSmallest(float a, float b) 
    {
        if (a < 0)
            a *= -1;
        if (b < 0)
            b *= -1;
        
        if (a < b)
            return a;
        else
            return b;
    }
}
