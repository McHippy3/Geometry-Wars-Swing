import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnemySquare {
    private int x, y, width, height, health, moveSpeed;
    private BufferedImage icon, damagedIcon;
    private Boolean special;

    public BufferedImage getDamagedIcon() {
        return damagedIcon;
    }

    public EnemySquare(int x, int y, int w, int h, int health, Boolean isSpecial, int moveSpeed){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.health = health;
        special = isSpecial;
        try{
            if(isSpecial)
                icon = ImageIO.read(new File("pics/special_square.png"));
            else icon = ImageIO.read(new File("pics/square.png"));
            if(isSpecial)
                damagedIcon = ImageIO.read(new File("pics/damagedSpecial_square.png"));
            else damagedIcon = ImageIO.read(new File("pics/damagedSquare.png"));
        }
        catch(IOException e){
            System.out.println("Square icon not found");
        }
        this.moveSpeed = moveSpeed;
    }

    public Boolean isSpecial(){
        return special;
    }

    public Rectangle getRectangle(){
        return new Rectangle(x,y, width, height);
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

    public int getHealth() {
        return health;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public BufferedImage getIcon() {
        return icon;
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

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }
}
