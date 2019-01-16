import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameOverScreen extends JPanel implements MouseListener {
    private JLabel start, credits,exit;
    private int width, height;
    private volatile Boolean finished;



    public GameOverScreen(int width, int height, int score, int highScore)
    {
        finished = false;
        this.width = width;
        this.height = height;
        setSize(width,height);
        setLayout(new GridLayout(4,1));
        setBackground(Color.black);

        //Title
        JLabel title = new JLabel("Game Over",SwingConstants.CENTER);
        title.setFont(new Font(Font.MONOSPACED,Font.BOLD,height/10));
        title.setForeground(Color.WHITE);
        add(title);

        //Score
        start = new JLabel("Score: " + score, SwingConstants.CENTER);
        start.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        start.setForeground(Color.WHITE);
        add(start);
        start.addMouseListener(this);

        //High Score
        start = new JLabel("High Score: " + highScore , SwingConstants.CENTER);
        start.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        start.setForeground(Color.WHITE);
        add(start);
        start.addMouseListener(this);

        //Exit
        exit = new JLabel("Main Menu",SwingConstants.CENTER);
        exit.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        exit.setForeground(Color.white);
        add(exit);
        exit.addMouseListener(this);
    }

    public void mouseEntered(MouseEvent me)
    {
        if(me.getSource() == exit)
            exit.setForeground(Color.YELLOW);

    }

    public void mouseExited(MouseEvent me)
    {
        if(me.getSource() == exit)
            exit.setForeground(Color.WHITE);
    }

    public void mouseClicked(MouseEvent me)
    {
        if(me.getSource() == exit){
            finished = true;
        }
    }

    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}


    Boolean isFinished(){
        return finished;
    }
}
