import java.awt.*;

public class Laser extends Point {
    private int moveX, moveY;
    public Laser(int x,int y, int mouseX, int mouseY, int plaX, int plaY){
        super(x,y);

        //calculating movement of laser
        int d = (int)Math.sqrt((mouseX-plaX)*(mouseX-plaX) + (mouseY-plaY)*(mouseY-plaY));
        moveX = (int)((25.0/d) * (mouseX-plaX));
        moveY = (int)((25.0/d) * (mouseY-plaY));
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }
}
