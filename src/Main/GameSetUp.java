package Main;

import Display.DisplayScreen;
import Game.GameStates.GameOverState;
import Game.GameStates.GameState;
import Game.GameStates.MenuState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Input.KeyManager;
import Input.MouseManager;
import Resources.Images;
import sun.applet.Main;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by AlexVR on 7/1/2018.
 */

public class GameSetUp implements Runnable {
    private DisplayScreen display;
    private int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;



    //Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    //Handler
    private Handler handler;

    //States
    public State gameState;
    public State menuState;
    public State pauseState;
    public State gameOverState;

    //Res.music, changed from private to public
    public InputStream audioFile;
    public AudioInputStream audioStream;
    public AudioFormat format;
    public DataLine.Info info;
    public Clip audioClip;

    private BufferedImage loading;
   //added variable to count the amount of times tick is called
    public int gameOverTickCounter = 0;
    public int menuTickCounter = 0;
    
    
    //added var to check the state
    //public State stateChecker;
    //public String statePrinter;
    

    public GameSetUp(String title, int width, int height){

        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

    }

    private void init(){
        display = new DisplayScreen(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Images img = new Images();


        handler = new Handler(this);

        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        pauseState = new PauseState(handler);
        
        //added this game state
        gameOverState = new GameOverState(handler);

        //changed from menuState to another state, changes in what state the player starts
        State.setState(menuState);
        menuTickCounter = 1;
        
        //tried commenting this out and putting it in tick()
       try {
      	
    	   	audioFile = getClass().getResourceAsStream("/music/Metal gear solid Alert Theme.wav");
          	audioStream = AudioSystem.getAudioInputStream(audioFile);
          	format = audioStream.getFormat(); 
        	info = new DataLine.Info(Clip.class, format);
        	audioClip = (Clip) AudioSystem.getLine(info);
        	audioClip.open(audioStream);
        	audioClip.loop(Clip.LOOP_CONTINUOUSLY);
         
         
        
     } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
     } catch (IOException e) {
          e.printStackTrace();
      } catch (LineUnavailableException e) {
           e.printStackTrace();}      

    }
    
    

    public void reStart(){
        gameState = new GameState(handler);
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        //this runs the run method in this  class
        thread = new Thread(this);
        thread.start();
    }

    public void run(){

        //initiallizes everything in order to run without breaking
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            //makes sure the games runs smoothly at 60 FPS
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                //re-renders and ticks the game around 60 times per second
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
        }
        

        stop();

    }

    private void tick(){
    	
        //checks for key types and manages them
        keyManager.tick();

        //game states are the menus
        if(State.getState() != null)
            State.getState().tick();
        //added to play sound upon death and resume music upon restart
        if (State.getState() == menuState && menuTickCounter < 1) {
        	try {
        		menuTickCounter++;
        	   	audioFile = getClass().getResourceAsStream("/music/SnakeFall.wav");
              	audioStream = AudioSystem.getAudioInputStream(audioFile);
              	format = audioStream.getFormat(); 
            	info = new DataLine.Info(Clip.class, format);
            	audioClip = (Clip) AudioSystem.getLine(info);
            	audioClip.open(audioStream);
            	audioClip.loop(Clip.LOOP_CONTINUOUSLY);
             
             
            
         } catch (UnsupportedAudioFileException e) {
             e.printStackTrace();
         } catch (IOException e) {
              e.printStackTrace();
          } catch (LineUnavailableException e) {
               e.printStackTrace();
           }
        	
        }
        
        
        if (State.getState() == gameOverState && gameOverTickCounter < 1){
        	
        	gameOverTickCounter++;
        	try {
              	audioClip.stop();
        	   	audioFile = getClass().getResourceAsStream("/music/SnakeSFX.wav");
              	audioStream = AudioSystem.getAudioInputStream(audioFile);
              	format = audioStream.getFormat(); 
            	info = new DataLine.Info(Clip.class, format);
            	audioClip = (Clip) AudioSystem.getLine(info);
            	audioClip.open(audioStream);
            	audioClip.loop(0);
             
             
            
         } catch (UnsupportedAudioFileException e) {
             e.printStackTrace();
         } catch (IOException e) {
              e.printStackTrace();
          } catch (LineUnavailableException e) {
               e.printStackTrace();
           }
        }
        
        
       
        
    }
    
        	
    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, width, height);

        //Draw Here!

        g.drawImage(loading ,0,0,width,height,null);
        if(State.getState() != null)
            State.getState().render(g);


        //End Drawing!
        bs.show();
        g.dispose();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KeyManager getKeyManager(){
        return keyManager;
    }

    public MouseManager getMouseManager(){
        return mouseManager;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
    
}

