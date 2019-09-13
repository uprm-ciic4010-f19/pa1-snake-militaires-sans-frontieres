package Main;


/**
 * Created by AlexVR on 7/1/2018.
 */

public class Launch {

    public static void main(String[] args) {
    	//changed from 800 to 600
        GameSetUp game = new GameSetUp("Snake", 800, 800);
        game.start();
        
    }
}
