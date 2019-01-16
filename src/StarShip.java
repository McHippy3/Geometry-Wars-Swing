import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StarShip {
    private int x, y, width, height;
    private BufferedImage icon;

    public StarShip(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        try{
            icon = ImageIO.read(new File("pics/starship.png"));
        }
        catch(IOException e){
            System.out.println("Starship icon not found");
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public Rectangle getRectangle(){
        return new Rectangle(x + (x/20),y + (y/20), (width * 9)/10, (height * 9)/10);
    }
}
