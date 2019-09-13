package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Random;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Game.GameStates.State;

import Game.Entities.Static.Apple;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;
//applied getter and setter
    static double currScore=0;
	
    
public static double getCurrScore() {
		return currScore;
	}


	public void setCurrScore(double currScore) {
		this.currScore = currScore;
	}

	//trying to get the output to make sense
	public int moveCounter;
    

    //added variable counterMod and tickLimit
    public int counterMod1;
    //public double tickLimit;

    //added variable counterMod, changed it to counterMod1 cause it was a conflicting variable in git staging
    public int counterMod;
    /*Here I will add a public variable that will be a counter for the movement of the snake for the isGood()*/
    public int pacer;



	public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        //moveCounter = 0;
        //change to "Down from Right", changes the starting direction of the snake
        direction= "Right";
        justAte = false;
        lenght= 1;
        //added variable counterMod and tickLimit
        counterMod1 = 0;
        //tickLimit = 5;
        
        
    }
   

    public void tick(){
        moveCounter++;
        //added variable n that will change the speed at which the snake moves and if that changes n with the 
        if(moveCounter>=5) {
            checkCollisionAndMove();
            //added to = 1 from =0, added counter mod which keeps track of the players tick speed up to 5
            moveCounter=counterMod1;
            
        }
        
        //added suicide button
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
        	State.setState(handler.getGame().gameOverState);
        	
        }
        //added the following which allows you to add a tail on pressing the n key
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	handler.getWorld().body.addLast(new Tail(xCoord, yCoord,handler));
        }
        
        //added the following to increase and decrease speed with the +- keys on the numpad (+ on numpad, - on keyboard)
       
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
        	counterMod1--;
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
        	counterMod1++;
        	//added to try it out
//        	tickLimit += 0.5;
//        	System.out.println("1. Tick: " + tickLimit);
//        	System.out.println("2. Counter: " + counterMod);
//        	System.out.println("3. Move: " + moveCounter);
        }
        

        //added pause state when pressing escape
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);
        }
        //adding here the thing that removes tail
        //SIDENOTE IF M IS PRESSED DURING ONLY ONE TAIL GAME CRASHES
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_M)) {
        	if (lenght == 1) {
        		
        	}
        	lenght--;
        	handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
        	handler.getWorld().body.removeLast();
        	
        }
        
       
      //added to prevent backtracking
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
            if(direction != "Down")
            direction="Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
        	if(direction != "Up")
        	direction="Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
        	if(direction != "Right")
        	direction="Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
        	if(direction != "Left")
        	direction="Right";
        }
        //This restarts the game. Got this from the MenuState.java codeline 29
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
        	handler.getGame().reStart();
        }
        
    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
      
        switch (direction){
            case "Left":
                if(xCoord==0){
                	//removed the kill() when the snake crashed against the edge, replaced it with getting teleported
                    //kill();
                	xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }
                else{
                	//added this if statement to check if player hits itself
                	if (handler.getWorld().playerLocation[xCoord-1][yCoord] == true ) {
                		kill();
                		
                	}
                    xCoord--;
                }
                //pacer variable
                pacer++;
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    //kill();
                	//removed the kill() when the snake crashed against the edge, replaced it with getting teleported
                	xCoord = 0;
                }else{
                	//added if to check if player collides with itself, causes a wird graphics like lag when back tracking
                	if (handler.getWorld().playerLocation[xCoord+1][yCoord] == true ) {
                		kill();
                	}
                    xCoord++;
                }
                //pacer variable
                pacer++;
                break;
             
            case "Up":
                if(yCoord==0){
                	//removed the kill() when the snake crashed against the edge, replaced it with getting teleported
                	//kill();
                    yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                	//added if to check if player collides with itself
                	if (handler.getWorld().playerLocation[xCoord][yCoord-1] == true ) {
                		kill();
                	}
                    yCoord--;
                }
                //pacer variable
                pacer++;
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	//removed the kill() when the snake crashed against the edge, replaced it with getting teleported
                	//kill();
                	yCoord = 0;
                }else{
                	//added if to check if player collides with itself
                	if (handler.getWorld().playerLocation[xCoord][yCoord+1] == true ) {
                		kill();
                	}
                    yCoord++;
                }
                pacer++;
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
            //added the following to speed up when the player eats by adding 1 to moveCounter
            counterMod1++;
            //tickLimit = (counterMod+3);
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
            
            
        }
        //pacer counter in console
        //System.out.println(pacer);

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        
        
        
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
            		// commented the following out: g.setColor(Color.WHITE);
            		
            		//separated g.setcolor for player/apple
            
            		/*OK, so I managed to complete one of the specs by separating the following code:
            		 
            	  	if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    	//changed i*handler to i+handler. it made the snake move slowly horizontally
                		g.fillRect((i*handler.getWorld().GridPixelsize),
                    	(j*handler.getWorld().GridPixelsize),
                    	handler.getWorld().GridPixelsize,
                    	handler.getWorld().GridPixelsize);
                    
                    into playerLocation and appleLocation, (two if statements)*/
            		
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

            	if(pacer==handler.getWorld().GridWidthHeightPixelCount) {
            		Apple.appleState(false);
            	}
            	if(Apple.isGood()) {
            		g.setColor(Color.red);
            	}
            	else {
            		Color wine=new Color(88,11,28);
            		g.setColor(wine);
            }
            		
                if(handler.getWorld().appleLocation[i][j]){
                	
                	
                	
                	
                	
                	/* 
                	 *Juan. I am almost certain here is the change for good apple to bad apple aesthetic
                	 *My guess is the Apple.java class needs the isGood() property and then use a getter over here
                	 *if the getter determines the state of the apple to be good based on the info given
                	 *then we can use a g.setColor to make it visually a bad apple
                	 *Where would the initial getter be? Apple.java?*/
                	
                	
                	
                	
                	
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
    	
    	/*(((((((((()//add here the state change for isGood()?
    	//here we reset the pacer so that when eating it goes back to red
   
    	//add here the actual negative effect instead of just color change
    	
    	//sale NaN si el score baja de 0. Changing value to avoid that
    	
        //Juan, thinking of adding the score change here)))))))))
         * 
         * 
         * 
         * THE PREVIOUS COMMENTS HERE ARE FROM THE OLD BRANCHES. KEPT TO KNOW THE EFFECT
         * */        
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        
        //aqui que se añade cola si la manzana es buena
        if(Apple.isGood()) {
        	//cambiando las cosas dentro de esto para solo llamar el if apple is good una vez
        	lenght++;
        	currScore+= Math.sqrt((2*currScore)+1);
        	
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
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        }
        //aqui seria si la manzana no es buena
     
       
        //added +5 to x and y: makes a copy of a tail in a spot on the grid that does nothing, but
        //might crash the game if it happens in the lower rows
    
           else {
        	   //added to game over when game over when length is one
        	   if (lenght == 1) {
        		   kill();
        		   
        	   } else {
        	   
        	   //else automaticamente considera si el apple es bad
        	lenght--;
        	//reduce speed
        	/*
        	 
        	//this below code should reduce speed but it doesn't. Why?
        	counterMod--;
        	*/
        	//continuation
        	currScore-= Math.sqrt((2*currScore)+1);
        	handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
        	handler.getWorld().body.removeLast();
        	//aqui asegura que no baje a NaN, AKA a glitch, y que se quede en 0.
        	if(currScore<0) {
    		currScore=0;
    	}
        }
        //reset to natural apple state
     	Apple.appleState(true);
    	pacer=0;
           }
    }

    public void kill(){
        //changed length to 5, may crash the game but otherwise does nothing, changed it back to 0
    	lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
            	//changed from false to true : basically changes it to a drawing software
                handler.getWorld().playerLocation[i][j]= false;
                //added to test if killing sends to menu state
                State.setState(handler.getGame().gameOverState);
                
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
