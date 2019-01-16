import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Credits extends JPanel implements MouseListener, ActionListener {
    private JLabel creditedName, title;
    private int width, height, duration;
    private volatile Boolean isFinished;
    private Timer timer;



    public Credits(int width, int height)
    {
        this.width = width;
        this.height = height;
        setSize(width,height);
        setLayout(null);
        setBackground(Color.black);
        addMouseListener(this);

        isFinished = false;

        //Title
        title = new JLabel("Lead Designer/Creator",SwingConstants.CENTER);
        title.setFont(new Font(Font.MONOSPACED,Font.BOLD,height/20));
        title.setForeground(Color.WHITE);
        title.setSize(width,height/18);
        title.setLocation(0,height);
        add(title);

        creditedName = new JLabel("David Wu", SwingConstants.CENTER);
        creditedName.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/25));
        creditedName.setForeground(Color.WHITE);
        creditedName.setSize(width,height/20);
        creditedName.setLocation(0,height+150);
        add(creditedName);

        //timer
        timer = new Timer(10,this);
        timer.setInitialDelay(150);
        timer.start();
    }

    public void mouseEntered(MouseEvent me)
    {

    }

    public void mouseExited(MouseEvent me)
    {

    }

    public void mouseClicked(MouseEvent me)
    {

    }

    public void mousePressed(MouseEvent me){
        isFinished = true;
    }

    public void mouseReleased(MouseEvent me){}

    public void actionPerformed(ActionEvent e) {
        int y = title.getY();
        title.setLocation(0, y-1);

        int y2 = creditedName.getY();
        creditedName.setLocation(creditedName.getX(),y2-1);

        if(y2 == 0)
            isFinished = true;

        repaint();
    }

    public Boolean isFinished() {
        return isFinished;
    }

}
