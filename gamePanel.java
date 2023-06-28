import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;

public class gamePanel extends JPanel implements ActionListener {
    static final int Screen_Width=600;
    static final int Screen_Height=600;
    static final int Unit_Size=25;
    static final int Game_units=(Screen_Height*Screen_Width)/Unit_Size;
    static final int Delay=100;
    final int []x=new int[Game_units];
    final int []y=new int[Game_units];
    int Body_Parts=6;
    int Apples_Eaten;
    int appleX;
    int appleY;
    char direction='R';
    Boolean running =false;
    Timer timer;
    Random random;

    gamePanel() {
        random=new Random();
        this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(Delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
//            for (int j = 0; j < Screen_Height / Unit_Size; j++) {
//                g.drawLine(j * Unit_Size, 0, j * Unit_Size, Screen_Height);
//                g.drawLine(0, j * Unit_Size, Screen_Width, j * Unit_Size);
//            }
            //g.setColor(Color.green);
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillOval(appleX, appleY, Unit_Size, Unit_Size);

            for (int i = 0; i < Body_Parts; i++) {
                if (i == 0) {
                    g.setColor(Color.yellow);
                    g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
                } else
                    g.setColor(Color.white);
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score"+Apples_Eaten,(Screen_Width-metrics.stringWidth("Score"+Apples_Eaten))/2,g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }
    public void newApple(){
    appleX=random.nextInt((int)(Screen_Width/Unit_Size))*Unit_Size;
        appleY=random.nextInt((int)(Screen_Height/Unit_Size))*Unit_Size;
    }
    public void move(){
        for (int i=Body_Parts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - Unit_Size;
            case 'D' -> y[0] = y[0] + Unit_Size;
            case 'L' -> x[0] = x[0] - Unit_Size;
            case 'R' -> x[0] = x[0] + Unit_Size;
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            Body_Parts++;
            Apples_Eaten++;
            newApple();
        }

    }
    public void checkCollision(){
        //check if the head collides with body
        for(int i=Body_Parts;i>0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //check if the head collide with left border
        if(x[0]<0){
            running=false;
        }
        //check if the head collide with right border
        if(x[0]>Screen_Width){
            running=false;
        }
        //check if the head collide with up border
        if(y[0]<0){
            running=false;
        }
        //check if the head collide with down border
        if(y[0]>Screen_Height){
            running=false;
        }
        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics g){
        //this for Score after game end
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score"+Apples_Eaten,(Screen_Width-metrics1.stringWidth("Score"+Apples_Eaten))/2,g.getFont().getSize());
        //this is for game_over
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("game over",(Screen_Width-metrics2.stringWidth("game over"))/2,Screen_Height/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();;
             checkCollision();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
