package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;

    public int moveCounter;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;

    }

    public void tick(){
        moveCounter++;
        if(moveCounter>=5) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
            direction="Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
            direction="Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
            direction="Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
            direction="Right";
        }

    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                    kill();
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    kill();
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        
        
        
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
        	//added the g.setColor(Color.red) below to see what happens. Nothing happens
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
            		// commented the following out: g.setColor(Color.WHITE);
            		//separated g.setcolor for player/apple
            
            	/*Ok, so I managed to complete one of the specs by seperating the following code:
            	  if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    //changed i*handler to i+handler. it made the snake move slowly horizontally
                	g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                            
                            into player Location and apple location, two if statements where
                            
            	  */
            		
            	if(playeLocation[i][j]){
            			
            			g.setColor(Color.GREEN);
                        //changed i*handler to i+handler. it made the snake move slowly horizontally
                    	g.fillRect((i*handler.getWorld().GridPixelsize),
                                (j*handler.getWorld().GridPixelsize),
                                handler.getWorld().GridPixelsize,
                                handler.getWorld().GridPixelsize);
            		}
            	
            	/*
            	 * This if makes it so when the snake moves below the second row, it changes colors to red 
            	 * Also, the "apple" is red if it appears below j = 2, in other
            	 *  words, below the second column
            	 * if (j>=2) {
            		g.setColor(Color.red);
            	}
            	*/
            		
                if(handler.getWorld().appleLocation[i][j]){
                	g.setColor(Color.red);
                    //changed i*handler to i+handler. it made the snake move slowly horizontally
                	g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                	
                	
                	
                }

            }
        }


    }
    
    public void Eat(){
        lenght++;
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                       
                       
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        //added +5 to x and y: makes a copy of a tail in a spot on the grid that does nothing, but
        //my crash the game if it happens in the lower rows
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    }

    public void kill(){
        //changed lenght to 5, may crash the game bt otherwise does nothing
    	lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

            	//changed from false to true : basically changes it to a drawing software
                handler.getWorld().playerLocation[i][j]= false;

            }
        }
    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
