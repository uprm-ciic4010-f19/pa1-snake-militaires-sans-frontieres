package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;
import sun.applet.Main;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Tail {
    public int x,y;
    public Tail(int x, int y,Handler handler){
        this.x=x;
        this.y=y;
        handler.getWorld().playerLocation[x][y]=true;
      
        //adding a color to the tail of the snake 
        
        
    }
        
        
        //adding a color to the tail of the snake 
       
}

    


