package Game.GameStates;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

/**
 * Created by AlexVR on 7/1/2018.
 */

//added this new game state class for game over
public class GameOverState extends State {

    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        
        
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        uiManager.addObjects(new UIImageButton(56, (460+(64+16))+(64+16), 200, 150, Images.Restart, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().menuTickCounter = 0;
            
            State.setState(handler.getGame().menuState);
            handler.getGame().gameOverTickCounter = 0;
            handler.getGame().audioClip.stop();
            
            for (;count<=30;count++) {
            	if (count == 0) {
            		State.setState(handler.getGame().menuState);
            	}
            }
            
        })); 
    
        uiManager.addObjects(new UIImageButton(550, (460+(64+16))+(64+16), 200, 150, Images.Exit, () -> {
//            handler.getMouseManager().setUimanager(null);
//            handler.getGame().menuTickCounter = 0;
//            State.setState(handler.getGame().menuState);
//            handler.getGame().gameOverTickCounter = 0;
//            handler.getGame().audioClip.stop();
//            handler.getGame().menuTickCounter = 0;
//            System.out.println("is in " + handler.getGame().menuTickCounter);
        	//added exit on pressing the button
        	System.exit(1);
            for (;count<=30;count++) {
            	if (count == 0) {
            		State.setState(handler.getGame().menuState);
            	}
            }
            
        }));
    	

        //added death sound or tried to
    
}

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        //added to reset tick COunter upon entry into state
        //handler.getGame().tickCounter = 0;
}

    @Override
    public void render(Graphics g) {
    	
    	//added the Game Over Screen below
        g.drawImage(Images.GameOverScreen,0,0,800,800,null);
        g.drawImage(Images.GameOverText,200,420,400,200,null);
        uiManager.Render(g);

    }
    
}
