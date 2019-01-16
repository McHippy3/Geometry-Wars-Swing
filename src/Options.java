import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Options extends JPanel implements MouseListener {

    private Color[] colorArray = {Color.CYAN,Color.BLUE,Color.RED,Color.GREEN,Color.WHITE,Color.YELLOW,Color.ORANGE,Color.PINK,Color.MAGENTA};
    static Boolean deathPause = true;
    static Color laserColor = Color.CYAN;
    static int colorIndex;
    private boolean finished;
    private JLabel dpLabel, colorlabel, backLabel;

    public Options(int width, int height){

        setSize(width,height);
        setLayout(new GridLayout(4,1));
        setBackground(Color.black);

        //Title
        JLabel title = new JLabel("Geometry Wars",SwingConstants.CENTER);
        title.setFont(new Font(Font.MONOSPACED,Font.BOLD,height/10));
        title.setForeground(Color.WHITE);
        add(title);

        //Death pause
        String yesNo;
        if(deathPause)
            yesNo = "ON";
        else yesNo = "OFF";
        dpLabel = new JLabel("Pause After Being Hit   " + yesNo, SwingConstants.CENTER);
        dpLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        dpLabel.setForeground(Color.WHITE);
        add(dpLabel);
        dpLabel.addMouseListener(this);

        //Color
        colorlabel = new JLabel("Laser Color   " + getColorName(laserColor.toString()), SwingConstants.CENTER);
        colorlabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height/15));
        colorlabel.setForeground(Color.WHITE);
        add(colorlabel);
        colorlabel.addMouseListener(this);

        //Back
        backLabel = new JLabel("Back",SwingConstants.CENTER);
        backLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/15));
        backLabel.setForeground(Color.white);
        add(backLabel);
        backLabel.addMouseListener(this);
    }

    public void mouseEntered(MouseEvent me)
    {

        if(me.getSource() == dpLabel)
            dpLabel.setForeground(Color.YELLOW);
        if(me.getSource() == colorlabel)
            colorlabel.setForeground(Color.YELLOW);
        if(me.getSource() == backLabel)
            backLabel.setForeground(Color.YELLOW);

    }

    public void mouseExited(MouseEvent me)
    {
        if(me.getSource() == dpLabel)
            dpLabel.setForeground(Color.WHITE);
        if(me.getSource() == colorlabel)
            colorlabel.setForeground(Color.WHITE);
        if(me.getSource() == backLabel)
            backLabel.setForeground(Color.WHITE);

    }

    public void mouseClicked(MouseEvent me)
    {
        if(me.getSource() == dpLabel){
            if(deathPause)
                deathPause = false;
            else deathPause = true;
            String yesNo;
            if(deathPause)
                yesNo = "ON";
            else yesNo = "OFF";
            dpLabel.setText("Pause After Being Hit   "+ yesNo);
        }

        if(me.getSource() == colorlabel){
            colorIndex++;
            if(colorIndex == 9)
                colorIndex = 0;
            laserColor = colorArray[colorIndex];

            String colorName = laserColor.toString();

            colorName = getColorName(colorName);

            colorlabel.setText("Laser Color   "+colorName);
        }

        if(me.getSource() == backLabel){
            finished = true;
        }
    }

    public String getColorName(String colorName){
        switch(colorName){
            case "java.awt.Color[r=0,g=255,b=255]": colorName = "CYAN"; break;
            case "java.awt.Color[r=0,g=0,b=255]": colorName = "BLUE"; break;
            case "java.awt.Color[r=255,g=0,b=0]": colorName = "RED"; break;
            case "java.awt.Color[r=0,g=255,b=0]": colorName = "GREEN"; break;
            case "java.awt.Color[r=255,g=255,b=255]": colorName = "WHITE"; break;
            case "java.awt.Color[r=255,g=255,b=0]": colorName = "YELLOW"; break;
            case "java.awt.Color[r=255,g=200,b=0]": colorName = "ORANGE"; break;
            case "java.awt.Color[r=255,g=175,b=175]": colorName = "PINK"; break;
            case "java.awt.Color[r=255,g=0,b=255]": colorName = "MAGENTA"; break;
        }
        return colorName;
    }
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}

    public boolean isFinished() {
        return finished;
    }
}
