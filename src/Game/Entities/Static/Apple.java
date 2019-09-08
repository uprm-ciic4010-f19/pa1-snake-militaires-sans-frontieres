package Game.Entities.Static;

import Game.Entities.Dynamic.Player;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;

    //quality control
private static boolean qualityControl=true;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    public static void appleState(boolean goodorbad) {
     qualityControl= goodorbad;
    }
    public static boolean isGood() {
    	return qualityControl;	
    	}
}

