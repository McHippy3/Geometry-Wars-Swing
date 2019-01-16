import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class StartScreen extends JPanel implements MouseListener {
    private JLabel start, credits,exit, options;
    private int width, height;
    private volatile Boolean startGame, viewCredits,exitGame, openOptions;



    public StartScreen(int width, int height)
    {
        openOptions = false;
        startGame = false;
        viewCredits = false;
        exitGame = false;
        this.width = width;
        this.height = height;
        setSize(width,height);
        setLayout(new GridLayout(5,1));
        setBackground(Color.black);

        //Title
        JLabel title = new JLabel("Geometry Wars",SwingConstants.CENTER);
        title.setFont(new Font(Font.MONOSPACED,Font.BOLD,height/10));
        title.setForeground(Color.WHITE);
        add(title);

        //Start
        start = new JLabel("Start", SwingConstants.CENTER);
        start.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        start.setForeground(Color.WHITE);
        add(start);
        start.addMouseListener(this);

        //Options
        options = new JLabel("Options", SwingConstants.CENTER);
        options.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height/15));
        options.setForeground(Color.WHITE);
        add(options);
        options.addMouseListener(this);

        //Credits
        credits = new JLabel("Credits", SwingConstants.CENTER);
        credits.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        credits.setForeground(Color.WHITE);
        add(credits);
        credits.addMouseListener(this);

        //Exit
        exit = new JLabel("Exit",SwingConstants.CENTER);
        exit.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        exit.setForeground(Color.white);
        add(exit);
        exit.addMouseListener(this);
    }

    public void mouseEntered(MouseEvent me)
    {

        if(me.getSource() == start)
            start.setForeground(Color.YELLOW);
        if(me.getSource() == options)
            options.setForeground(Color.YELLOW);
        if(me.getSource() == credits)
            credits.setForeground(Color.YELLOW);
        if(me.getSource() == exit)
            exit.setForeground(Color.YELLOW);

    }

    public void mouseExited(MouseEvent me)
    {
        if(me.getSource() == start)
            start.setForeground(Color.WHITE);
        if(me.getSource() == options)
            options.setForeground(Color.WHITE);
        if(me.getSource() == credits)
            credits.setForeground(Color.WHITE);
        if(me.getSource() == exit)
            exit.setForeground(Color.WHITE);
    }

    public void mouseClicked(MouseEvent me)
    {
        if(me.getSource() == start){
            startGame = true;
        }

        if(me.getSource() == options) {
            openOptions = true;
        }

        if(me.getSource() == credits){
            viewCredits = true;
        }

        if(me.getSource() == exit){
            exitGame = true;
        }
    }

    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}


    Boolean getStartGame() {
        return startGame;
    }

    Boolean getViewCredits() {
        return viewCredits;
    }

    Boolean getExitGame(){
        return exitGame;
    }

    Boolean getOpenOptions(){return openOptions;}
}
