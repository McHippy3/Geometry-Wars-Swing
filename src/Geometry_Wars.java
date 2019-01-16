import javax.swing.*;

public class Geometry_Wars{
    private JFrame f;
    private StartScreen ss;
    private GamePanel gamePanel;

    public Geometry_Wars(){
        f = new JFrame("Geometry Wars");
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setSize(800,600);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startScreen();
    }

    public void startScreen(){
        ss = new StartScreen(f.getWidth(),f.getHeight());
        f.add(ss);
        f.validate();

        while(!ss.getStartGame() && !ss.getViewCredits() && !ss.getExitGame() && !ss.getOpenOptions()){
            try{
                wait();
            }
            catch(Exception e){}
        }
        if(ss.getStartGame())
            startGame();
        else if(ss.getViewCredits())
            viewCredits();
        else if(ss.getOpenOptions())
            openOptions();
        else if(ss.getExitGame())
            System.exit(0);
    }

    public static void main(String[]args){
        new Geometry_Wars();
    }

    private void viewCredits(){
        f.remove(ss);
        Credits c = new Credits(f.getWidth(),f.getHeight());
        f.add(c);
        f.validate();
        while(!c.isFinished()){
            try{
                wait();
            }
            catch(Exception e){}
        }

        f.remove(c);
        f.validate();

        startScreen();
    }

    void openOptions(){

        f.remove(ss);
        Options o = new Options(f.getWidth(),f.getHeight());
        f.add(o);
        f.validate();
        while(!o.isFinished()) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        f.remove(o);
        f.validate();
        startScreen();
    }

    private void startGame(){
        f.remove(ss);

        gamePanel = new GamePanel(f.getWidth(),f.getHeight());

        GWKeyListener kl = new GWKeyListener();
        f.addKeyListener(kl);

        f.add(gamePanel);

        f.validate();

        while(!gamePanel.isFinished()) {
            try{
                wait();
            }
            catch(Exception e){}
        }

        f.removeKeyListener(kl);
        int s = gamePanel.getScore();
        int hs = gamePanel.getHighScore();
        f.remove(gamePanel);
        GameOverScreen gOS = new GameOverScreen(f.getWidth(),f.getHeight(),s,hs);
        f.add(gOS);
        f.validate();
        while(!gOS.isFinished()){
            try{
                wait();
            }
            catch (Exception e){}
        }
        f.remove(gOS);
        startScreen();
    }
}
