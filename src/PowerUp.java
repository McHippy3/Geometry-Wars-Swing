import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PowerUp {
    int x, y;

    BufferedImage icon;

    public PowerUp(int width, int height){
        try{
            icon = ImageIO.read(new File("pics/powerUp.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        x = (int)(Math.random()*(width-100) + 100);
        y = (int)(Math.random()*(height-100) + 100);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPoint(){
        return new Point(x,y);
    }

    public Rectangle getRectangle(){
        return new Rectangle(x,y,100,100);
    }

    public BufferedImage getIcon() {
        return icon;
    }
}
