import javax.swing.*;
import java.awt.*;

public class gameFrame extends JFrame {
    public gameFrame(){
        this.add(new gamePanel());
        this.setTitle("snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
