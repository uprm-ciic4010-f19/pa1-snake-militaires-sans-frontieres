package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

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
    
    //added variable counterMod
    public static int counterMod;
    /*Here I will add a public variable that will be a counter for the movement of the snake for the isGood()*/
    public int pacer;
    

    public int getPacer() {
		return pacer;
	}


	public void setPacer(int pacer) {
		this.pacer = pacer;
	}

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
        //added variable counterMod
        counterMod = 0;

    }
   

    public void tick(){
        moveCounter++;
        //added variable n that will change the speed at which the snake moves and if that changes n with the 
        if(moveCounter>=5) {
            checkCollisionAndMove();
            //added to = 1 from =0, added counter mod which keeps track of the players tick speed up to 5
            moveCounter=counterMod;
        }
        
        //added the following which allows you to add a tail on pressing the n key
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	handler.getWorld().body.addFirst(new Tail(xCoord, yCoord,handler));
        }
        
        //added the following to increase and decrease speed with the +- keys on the numpad (+ on numpad, - on keyboard)
       
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
        	counterMod--;
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
        	counterMod++;
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
        
      //added to check if player crashes against itself wip
        //if (handler.getWorld().playerLocation[xCoord][yCoord] == handler.getWorld().body.){
        	//kill();}

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
                    yCoord++;
                }
                pacer++;
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
            //added the following to speed up when the player eats by adding 1 to moveCounter
            counterMod++;
            
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
            
            
        }
        //pacer counter in console
        System.out.println(pacer);

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
            		g.setColor(Color.yellow);
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
    	
    	//add here the state change for isGood()?
    	//here we reset the pacer so that when eating it goes back to red
    	Apple.appleState(true);
    	pacer=0;
    	
    	
        //Juan, thinking of adding the score change here
    	
    	currScore=Math.sqrt((2*currScore)+1);
    	//La idea es cambiar aqui e imprimir en el render
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
        //might crash the game if it happens in the lower rows
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        
        
    }

    public void kill(){
        //changed length to 5, may crash the game but otherwise does nothing, changed it back to 0
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
