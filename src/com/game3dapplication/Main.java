package com.game3dapplication;

import com.game3dapplication.util.BoundsBox;
import static org.lwjgl.opengl.GL11.*;

import com.game3dapplication.item.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.Sys;


/**
 *
 * @author Mrmeguyme
 */
public class Main
{    
    public static void main(String[] args)
    {
        initDisplay();
        initMouse();
        gameLoop();
        cleanUp();
    }
    
    public static void gameLoop()
    {
        float speed;
        
        File f = new File("res/level/joshgameworld.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameLevel myLevel = new GameLevel(img, "THISDOESNTEXISTYET.json");
        myLevel.load();
        
        Camera cam = new Camera(80, (float)Display.getWidth() / (float)Display.getHeight(), 0.3f, 1000, 0, 0);
        cam.move(myLevel.getCamZ() * -2, 1);
        cam.move(myLevel.getCamX() * -2, 0);
        cam.useView();
        
        Inventory inv = new Inventory();
        
        boolean justMoved = false;
        boolean crouching = false;
        boolean jumping = false;
        boolean jumpReq = false;
        boolean isJumping = false;
        
        int countCrouch = 0;
        int countJump = 0;
        
        Collision collisionObj = new Collision();
        collisionObj = myLevel.render(cam.getX(), cam.getZ(), collisionObj);
        
        float playerPrevX = 0;
        float playerPrevY = 0;
        float playerPrevZ = 0;
        
        //Game Running
        while (!Display.isCloseRequested())
        {            
            boolean isJumping2 = isJumping;
            boolean forward = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
            boolean backward = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
            boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
            boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
            boolean isCrouched = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
            isJumping = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
                    
            float mouseX = Mouse.getDX();
            float mouseY = Mouse.getDY();
            
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                Display.destroy();
                break;
            }
                
            //Sprinting
            speed = 0.09f;
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
                speed = speed * 2;
            
            //Crouching
            if (isCrouched && !crouching) {
                cam.beginCrouch();
                countCrouch++;
                if (countCrouch >= 10) { 
                    crouching = true;
                    countCrouch = 0;
                }
            } else if (!isCrouched && crouching) {
                cam.endCrouch();
                countCrouch++;
                if (countCrouch >= 10) {
                    crouching = false;
                    countCrouch = 0;
                }
            } else if (cam.getY() != 0 && !isCrouched && !jumpReq && !jumping) {
                cam.setY(0);
            }
            if (isJumping && isJumping2 != isJumping)
                jumpReq = true;
                
            if (jumpReq && !jumping) {
                cam.beginJump();
                countJump++;
                if (countJump >= 10) {
                    jumping = true;
                    jumpReq = false;
                    countJump = 0;
                }
            }
            
            if (jumping) {
                countJump++;
                if (countJump >= 3)
                    cam.endJump();
                if (countJump >= 7) {
                    countJump = 0;
                    jumping = false;
                }
            }
            
            if (jumping || jumpReq)
                speed = speed * 1.1f;
            
            if (isCrouched)
                speed = speed / 2f;
            
            if (forward)
                cam.move(speed, 1);
            if (backward)
                cam.move(-speed, 1);
            if (left)
                cam.move(speed, 0);
            if (right)
                cam.move(-speed, 0);
            
            if (justMoved == false) {
                if (mouseX > 0)
                    cam.rotateY(0.1f * Math.round(mouseX));
                if (mouseX < 0)
                    cam.rotateY(-0.1f * -Math.round(mouseX));
                if (mouseY > 0)
                    cam.rotateX(0.1f * Math.round(mouseY));
                if (mouseY < 0)
                    cam.rotateX(-0.1f * -Math.round(mouseY));
            } else {
                justMoved = false;
            }
            
            if (cam.getRX() > 85) {
                cam.setRX(85);
            }
            if (cam.getRX() < -80) {
                cam.setRX(-80);
            }
            
            if (!Mouse.isInsideWindow()) {
                Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
                justMoved = true;
            }
            
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            
            int CollisionInt = collisionObj.getIntCollision(new BoundsBox(-cam.getX(), cam.getY(), -cam.getZ(), 1f, "player"));
            
            switch (CollisionInt) {
                case 1: {
                    cam.setX(playerPrevX);
                    playerPrevZ = cam.getZ();
                    break;
                }
                
                case 2: {
                    cam.setZ(playerPrevZ);
                    playerPrevX = cam.getX();
                    break;
                }
                
                case 3: {
                    cam.setX(playerPrevX);
                    cam.setZ(playerPrevZ);
                    break;
                }
                
                default: {
                    playerPrevX = cam.getX();
                    playerPrevY = cam.getY();
                    playerPrevZ = cam.getZ();
                    break;
                }
            }
            
            cam.useView();
            collisionObj = myLevel.render(cam.getX(), cam.getZ(), collisionObj);
            Display.sync(60);
            Display.update();
        }
    }
    
    public static void cleanUp()
    {
        Mouse.destroy();
        Display.destroy();
    }
    
    public static void initDisplay()
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(1280, 720));
            Display.create();
            Display.setVSyncEnabled(false);
        }
        catch (LWJGLException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Using LWJGL Version: " + Sys.getVersion());
    }

    private static void initMouse() {
        try {
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Mouse.setGrabbed(true);
    }
}
