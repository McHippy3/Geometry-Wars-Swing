import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class GamePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

    private Timer timer, enemyTimer, fireTimer, levelUpTimer;
    private AffineTransform affineTransform;
    private AffineTransformOp affineTransformOp;
    private BufferedImage bombIcon;
    private Scanner sc;
    private StarShip player;
    private int screenWidth, screenHeight, score, highScore, lives, bombWH, bombX, bombY, mouseX, mouseY;
    private volatile boolean finished, spawn, firing;
    private JLabel scoreLabel,highScoreLabel, levelLabel;
    static int bombsRemaining, level;
    static Boolean move_LEFT, move_RIGHT, move_UP, move_DOWN, bombActivated;
    private ArrayList<EnemySquare> enemyList;
    private ArrayList<Laser> laserList;
    private ArrayList<PowerUp> powerUps;
    //Options Variables
    Color laserColor = Options.laserColor;
    Boolean deathPause = Options.deathPause;

    public GamePanel(int width,int height){
        screenWidth = width;
        screenHeight = height;
        finished = false;
        score = 0;
        lives = 3;
        bombWH = 0;
        firing = false;
        affineTransform = new AffineTransform();
        level = 1;

        //static variables
        bombsRemaining = 2;
        move_LEFT = false;
        move_RIGHT = false;
        move_UP = false;
        move_DOWN = false;
        bombActivated = false;

        //Lists
        enemyList = new ArrayList<>();
        laserList = new ArrayList<>();
        powerUps = new ArrayList<>();

        //Scanner
        try {
            sc = new Scanner(new File("scores.dat"));
            bombIcon = ImageIO.read(new File("pics/bomb.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        highScore = sc.nextInt();
        sc.close();

        //Player
        player = new StarShip(screenWidth/2-50,screenHeight/2-50,100,100);

        //JLabels
        highScoreLabel = new JLabel("High Score: "+highScore);
        highScoreLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/25));
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setSize(width/4,height/20);
        highScoreLabel.setLocation(10, 80);
        add(highScoreLabel);

        scoreLabel = new JLabel("Score: "+score);
        scoreLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/25));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setSize(width/4,height/25);
        scoreLabel.setLocation(10,10);
        add(scoreLabel);

        levelLabel = new JLabel("Level "+level, SwingConstants.CENTER);
        levelLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,height/25));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setSize(width,height/25);
        levelLabel.setLocation(0,(screenHeight/22)*20);
        add(levelLabel);

        setLayout(null);
        setSize(width,height);
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        repaint();

        //Timers
        timer = new Timer(15,this);
        timer.start();

        enemyTimer = new Timer(3000,this);
        enemyTimer.setInitialDelay(5000);
        enemyTimer.start();

        fireTimer = new Timer(100,this);
        fireTimer.start();

        levelUpTimer = new Timer(30000,this);
        levelUpTimer.start();

    }

    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0,0,screenWidth,screenHeight);

        g2D.setColor(Color.WHITE);
        rotateShip(g2D);

        //Game Mechanics: score
        if(score>highScore) {
            highScore = score;
            highScoreLabel.setText("High Score: "+highScore);
        }
        scoreLabel.setText("Score: "+score);

        //Game Mechanics: lives counter
        g2D.drawImage(player.getIcon(),(screenWidth/22)*19,(screenHeight/22)*19, 65,65,null);
        g2D.setFont(new Font(Font.MONOSPACED,Font.PLAIN,screenHeight/25));
        g2D.drawString("X"+" "+ lives, (screenWidth/22)*20,(screenHeight/22)*20);

        //Game Mechanics: Level
        levelLabel.setText("Level "+ level);

        //Game Mechanics: Enemies
        if(spawn)
            spawnEnemies();
        for(EnemySquare eS: enemyList)
            g2D.drawImage(eS.getIcon(), eS.getX(), eS.getY(), eS.getWidth(), eS.getHeight(), null);

        //Game Mechanics: Bomb
        g2D.drawImage(bombIcon,screenWidth/22,(screenHeight/22)*19,65,65,null);
        g2D.drawString("X "+ bombsRemaining, (screenWidth/22)*2,(screenHeight/22)*20);
        if(bombActivated) {
                bombX = player.getX() - bombWH / 2 + player.getWidth()/2;
                bombY = player.getY() - bombWH / 2 + player.getHeight()/2;
            g2D.drawOval(bombX, bombY, bombWH, bombWH);
        }

        //Game Mechanics: Getting Hit
        for(EnemySquare eS: enemyList)
            if(rectInter(player.getRectangle(),eS.getRectangle()) && !bombActivated) {
            if(deathPause) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
                bombActivated = true;
                lives--;
            }

        //Draw Lasers
        g2D.setColor(laserColor);
        for(Point laser: laserList)
            g2D.fillRect((int) laser.getX(),(int) laser.getY(),10,10);

        //Game Mechanics: Lasers hitting enemies
        for(EnemySquare eS: enemyList)
            for(int i = 0; i < laserList.size();i++)
                if(eS.getRectangle().contains(laserList.get(i))) {
                    eS.setHealth(eS.getHealth() - 1);
                    g2D.drawImage(eS.getDamagedIcon(),eS.getX(),eS.getY(),eS.getWidth(),eS.getHeight(),null);
                    laserList.remove(i);
                    if(i > 0)
                        i--;
                }
        for(int e = 0; e < enemyList.size(); e++)
            if(enemyList.get(e).getHealth() <= 0) {
                enemyList.remove(e);
                score += level * 10;
                e--;
            }

        //Game Mechanics: Power Up
        for(int i = 0; i < powerUps.size(); i++) {
            g2D.drawImage(powerUps.get(i).getIcon(),powerUps.get(i).getX(),powerUps.get(i).getY(),100,100,null);
            if (rectInter(player.getRectangle(),powerUps.get(i).getRectangle())) {
                powerUp();
                powerUps.remove(i);
            }
        }
        //Movement
        animate();
        //Game Mechanics: Game Over
        if(lives <= 0)
            gameOver();
    }

    public void rotateShip(Graphics2D g2D) {

        //cannot divide by zero
        double degree;
        if(mouseX-player.getX()+player.getWidth()/2 == 0)
            degree = 0;
        else degree = Math.abs(Math.toDegrees(Math.atan((mouseY - player.getY()+player.getHeight()/2) / (mouseX - player.getX()+player.getWidth()/2))));

        //default, facing top
        if (degree >= 45 && mouseY > player.getY()+player.getHeight()/2) {
            g2D.drawImage(player.getIcon(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);
        }
        //facing bottom
        else if (degree >= 45 && mouseY < player.getY() + player.getHeight()/2) {
            affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(180), player.getWidth() / 2, player.getHeight() / 2);
            affineTransform.scale(0.30, 0.38);
            affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
            g2D.drawImage(affineTransformOp.filter(player.getIcon(), null), player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);
        }
        //facing left
        else if (degree < 45 && mouseX < player.getX()+player.getWidth()/2){
            affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(90), player.getWidth()/2,player.getHeight()/2);
            affineTransform.scale(0.30,0.38);
            affineTransformOp = new AffineTransformOp(affineTransform,AffineTransformOp.TYPE_BILINEAR);
            g2D.drawImage(affineTransformOp.filter(player.getIcon(),null),player.getX(),player.getY(),player.getWidth(),player.getHeight(),null);
        }
        //facing right
        else if (degree < 45 && mouseX > player.getX()+player.getWidth()/2){
            affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(-90), player.getWidth()/2,player.getHeight()/2);
            affineTransform.scale(0.30,0.38);
            affineTransformOp = new AffineTransformOp(affineTransform,AffineTransformOp.TYPE_BILINEAR);
            g2D.drawImage(affineTransformOp.filter(player.getIcon(),null),player.getX(),player.getY(),player.getWidth(),player.getHeight(),null);
        }
    }

    //Event Handlers + Listeners
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == timer)
            repaint();
        if (e.getSource() == enemyTimer)
            spawn = true;
        if (e.getSource() == fireTimer)
            if(firing)
                fireLaser();
        if (e.getSource() == levelUpTimer)
            levelUp();
    }

    public void mouseEntered(MouseEvent me){}

    public void mouseExited(MouseEvent me){}

    public void mouseClicked(MouseEvent me) {}

    public void mousePressed(MouseEvent me){
        firing = true;
    }

    public void mouseReleased(MouseEvent me){
        firing = false;
    }

    public void mouseMoved(MouseEvent me){
        mouseX = me.getX();
        mouseY = me.getY();
    }

    public void mouseDragged(MouseEvent me){
        mouseX = me.getX();
        mouseY = me.getY();
    }

    //Animations
    private void animate()
    {
        //Player
        if(player.getX() >= 0)
            if(move_LEFT)
                player.setX(player.getX()-10);

        if(player.getX() <= screenWidth-player.getWidth())
            if(move_RIGHT)
                player.setX(player.getX()+10);

        if(player.getY() >= 0)
            if(move_UP)
                player.setY(player.getY()-10);

        if(player.getY() <= screenHeight-player.getHeight())
            if(move_DOWN)
                player.setY(player.getY()+10);

        //Enemy
        for(EnemySquare eS: enemyList){
            if(player.getX() > eS.getX())
                eS.setX(eS.getX()+eS.getMoveSpeed());
            else if(player.getX() < eS.getX())
                eS.setX(eS.getX() - eS.getMoveSpeed());
            if(player.getY() > eS.getY())
                eS.setY(eS.getY()+eS.getMoveSpeed());
            else if(player.getY() < eS.getY())
                eS.setY(eS.getY() - eS.getMoveSpeed());
        }

        //Bomb
        if(bombActivated)
        {
            bombWH += 24;

            for(int i = 0; i < enemyList.size(); i++){
                //(x-h)^2 + (y-k)^2 < r^2
                if(Math.sqrt(((enemyList.get(i).getX()-player.getX()) * (enemyList.get(i).getX()-player.getX()) +(enemyList.get(i).getY() - player.getY())*(enemyList.get(i).getY() - player.getY()))) < bombWH/2) {
                    enemyList.remove(i);
                    score += level * 10;
                    i--;
                }
            }

            if(bombWH >= screenWidth) {
                bombActivated = false;
                bombX = 0;
                bombY = 0;
                bombWH = 0;
            }
        }

        //laser
        for(Laser l: laserList){
            l.setLocation(l.getX()+l.getMoveX(),l.getY()+l.getMoveY());
        }
    }

    //Game
    public void spawnEnemies(){
        for(int i = 0; i < 5; i++) {

            //random coordinate generation
            int y;
            int x = (int)((Math.random() * (screenWidth + 200))-100);

            if(x > 0 && x < screenWidth){
                if(Math.round(Math.random()) == 0)
                    y = -100;
                else y = screenHeight + 100;
            }
            else y = (int) (Math.random() * screenHeight);

            //add enemies
            if ((int) (Math.random() * 10) <= level) {
                enemyList.add(new EnemySquare(x,y,100,100,level,true, 5));
            }
            else enemyList.add(new EnemySquare(x,y,100,100,1,false, 3));
        }
        spawn = false;
    }

    public void levelUp(){
        EnemySquare boss = new EnemySquare(-100,-100,300,300,level * 30,false,2);
        enemyList.add(boss);
        score += 100;
        level++;

        //Create new Power Up
        powerUps.add(new PowerUp(screenWidth,screenHeight));
    }

    public Boolean rectInter(Rectangle r, Rectangle r2){
        return Math.sqrt((r.getX()-r2.getX())*(r.getX()-r2.getX()) + (r.getY()-r2.getY())*(r.getY()-r2.getY()))  < Math.sqrt((r.getX()-r.getX()+r.getWidth())*(r.getX()-r.getX()+r.getWidth()) + (r.getY()-r.getY()+r.getHeight())*(r.getY()-r.getY()+r.getHeight()));
    }

    public void powerUp(){
        fireTimer.setDelay((int)(fireTimer.getDelay()* 0.75));
    }

    private void gameOver(){
        if(score >= highScore){
            try{
                PrintWriter fileOut = new PrintWriter(new FileWriter("scores.dat"));
                fileOut.print(score);
                fileOut.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if(deathPause) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        finished = true;
    }

    boolean isFinished(){
        return finished;
    }

    int getScore(){
        return score;
    }

    int getHighScore(){
        return highScore;
    }

    private void fireLaser(){
        //fire shot
        Laser temp = new Laser(player.getX() + player.getWidth()/2,player.getY() + player.getHeight()/2, mouseX, mouseY, player.getX(), player.getY());
        laserList.add(temp);
    }
}