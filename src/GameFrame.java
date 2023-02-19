import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    JFrame frame;
    GamePanel panel;
    GameFrame(){
        panel = new GamePanel();
        this.setBackground(Color.black);
        this.add(panel);
        this.setTitle("PongGame");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
