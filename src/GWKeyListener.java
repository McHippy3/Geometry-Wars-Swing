import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GWKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == 87 || ke.getKeyCode() == KeyEvent.VK_UP){
            GamePanel.move_UP = true;
        }

        if(ke.getKeyCode() == 65 || ke.getKeyCode() == KeyEvent.VK_LEFT){
            GamePanel.move_LEFT = true;
        }

        if(ke.getKeyCode() == 83 || ke.getKeyCode() == KeyEvent.VK_DOWN){
            GamePanel.move_DOWN = true;
        }

        if(ke.getKeyCode() == 68 || ke.getKeyCode() == KeyEvent.VK_RIGHT){
            GamePanel.move_RIGHT = true;
        }

        if(ke.getKeyCode() == KeyEvent.VK_SPACE && GamePanel.bombsRemaining != 0){
            if(!GamePanel.bombActivated) {
                GamePanel.bombActivated = true;
                GamePanel.bombsRemaining--;
            }
        }
    }

    public void keyReleased(KeyEvent ke){
        if(ke.getKeyCode() == 87 || ke.getKeyCode() == KeyEvent.VK_UP){
            GamePanel.move_UP = false;
        }

        if(ke.getKeyCode() == 65 || ke.getKeyCode() == KeyEvent.VK_LEFT){
            GamePanel.move_LEFT = false;
        }

        if(ke.getKeyCode() == 83 || ke.getKeyCode() == KeyEvent.VK_DOWN){
            GamePanel.move_DOWN = false;
        }

        if(ke.getKeyCode() == 68 || ke.getKeyCode() == KeyEvent.VK_RIGHT){
            GamePanel.move_RIGHT = false;
        }
    }
}
