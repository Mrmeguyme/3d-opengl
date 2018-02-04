package com.game3dapplication.item;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Inventory {
    
    private Map<String, Integer> inv = new HashMap<String, Integer>();
    
    public Inventory ()
    {
        load();
        if (!inv.containsKey("money")) {
            loadItems();
            save();
        }
    }
    
    private void loadItems()
    {
        inv.put("money", 50);
        inv.put("door-key", 0);
    }
    
    public void save ()
    {
        try {
            OutputStream os = new FileOutputStream("res/save/inventory.data");
            ObjectOutput oo = new ObjectOutputStream(os);
            oo.writeObject(inv);
            
            oo.close();
        } catch (IOException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void load ()
    {
        BufferedReader br;     
        try {
            br = new BufferedReader(new FileReader("res/save/inventory.data"));
            if (br.readLine() == null) {
                return;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            InputStream is = new FileInputStream("res/save/inventory.data");
            ObjectInput oi = new ObjectInputStream(is);
            try {
                inv = (HashMap<String, Integer>) oi.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add (String key, int amount)
    {
        inv.put(key, inv.get(key) + amount);
        System.out.println("Picked up " + amount + " " + key);
        save();
    }
    
    public int get (String key)
    {
        int a = inv.get(key);
        return a;
    }
    
    public Map<String, Integer> getMap ()
    {
        return inv;
    }
    
    public void rem (String key, int amount)
    {
        inv.put(key, inv.get(key) - amount);
        save();
    }
    
    public void del (String key)
    {
        inv.put(key, 0);
        save();
    }
}
