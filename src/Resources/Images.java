package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
    //renamed from Btitle to Restart
    public static BufferedImage[] Restart;
    public static BufferedImage[] Options;
    public static ImageIcon icon;
    //added this new buffered image for the game over screen
    public static BufferedImage GameOverScreen;
    public static BufferedImage GameOverText;
    public static BufferedImage[] Exit;
    
    

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        Restart = new BufferedImage[2];
        Options = new BufferedImage[2];
        Exit = new BufferedImage[2];

        try {
        	//added the new statement for the game over screen, (change to sheet file)
        	GameOverScreen = ImageIO.read(getClass().getResourceAsStream("/Buttons/Codec-forSnakeJavaProject1.png"));
        	
            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Snake Title V.3.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            Restart[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeRestartBtn.png"));
            Restart[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeRestartBtnP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeOptions.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeOptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeStart V1 Normal.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeStart V1 Pressed.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeStart V1 Clicked.png"));//clickbut
            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));
            //added game over text
            GameOverText = ImageIO.read(getClass().getResourceAsStream("/Sheets/GameOverText.png"));
            Exit[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeExit.png"));
            Exit[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeExitP.png"));

        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
