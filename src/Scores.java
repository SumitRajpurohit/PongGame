import java.awt.*;

public class Scores extends Rectangle {
    int width;
    int height;
    int player1;
    int player2;
    Scores(int width,int height)
    {
        this.width = width;
        this.height = height;

    }
    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.PLAIN,60));
        g.drawString(String.valueOf(player1) , width/2-60,50);
        g.drawString(String.valueOf(player2),width/2+20,50);
    }
}
