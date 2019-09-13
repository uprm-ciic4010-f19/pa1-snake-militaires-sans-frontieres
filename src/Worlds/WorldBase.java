package Worlds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;


/**
 * Created by AlexVR on 7/2/2018.
 */
public abstract class WorldBase {

    //How many pixels are from left to right
    //How many pixels are from top to bottom
    //Must be equal
    public int GridWidthHeightPixelCount;

    //automatically calculated, depends on previous input.
    //The size of each box, the size of each box will be GridPixelsize x GridPixelsize.
    public int GridPixelsize;

    public Player player;

    protected Handler handler;


    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;


    public Boolean[][] playerLocation;
    

    public LinkedList<Tail> body = new LinkedList<>();


    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;
        
        
    }
    public void tick(){



    }

    public void render(Graphics g){
 
        //changed from 800 to 1200
    	for (int i = 0; i <= 800; i = i + GridPixelsize) {
        	//changed to red
            g.setColor(Color.WHITE);
            Font snakeFont= new Font("Comic Sans", 30, 30);
            g.setFont(snakeFont);
            //Getting the variable from Player class with a getter, applying then to currScore and exporting to the frame as necessary
            
           
            
            double output = Player.getCurrScore();
            g.drawString("Score: " + output, 30, 30);
            
            //commented these outs, these eliminate the gridlines
            //g.drawLine(0, i, handler.getWidth() , i);
            //g.drawLine(i,0,i,handler.getHeight());
            

        }



    }

}
