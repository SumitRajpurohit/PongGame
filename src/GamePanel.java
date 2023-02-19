import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH , GAME_HEIGHT);

    static final int PADDLE_HEIGHT = 100;
    static final int PADDLE_WIDTH = 25;
    static final int ball_diameter = 20;
    Image image;

    Graphics graphics;

    Paddles paddle1;
    Paddles paddle2;

    Ball ball;

    Thread gameThread;

    Scores score  = new Scores(GAME_WIDTH,GAME_HEIGHT);


    GamePanel()
    {
        newPaddles();
        newBall();
        this.setFocusable(true);
        this.addKeyListener(new ActionListener() );

        this.setPreferredSize(SCREEN_SIZE);
        gameThread =  new Thread(this);
        gameThread.start();

    }

    private void newBall()
    {
        Random random = new Random();
        ball = new Ball(GAME_WIDTH/2-ball_diameter/2,random.nextInt(GAME_HEIGHT-ball_diameter) , ball_diameter,ball_diameter);


    }
    private void newPaddles()
    {
        paddle1 = new Paddles(0,GAME_HEIGHT/2 - PADDLE_HEIGHT/2 , PADDLE_WIDTH, PADDLE_HEIGHT , 1);
        paddle2 = new Paddles(GAME_WIDTH-PADDLE_WIDTH ,GAME_HEIGHT/2 - PADDLE_HEIGHT/2 ,  PADDLE_WIDTH, PADDLE_HEIGHT , 2  );


    }

    @Override
    public void paint(Graphics g)
    {
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
//        super.paint(g);
    }

    private void draw(Graphics g)
    {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);

    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTickes = 60.0;
        double ns  = 1000000000/amountOfTickes;
        double delta = 0;
        while (true)
        {
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime = now;
            if(delta>=1)
            {
                move();
                repaint();
                checkCollsion();
                delta--;
            }
        }

    }

    private void checkCollsion() {
        if(ball.y<=0)
        {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y>=GAME_HEIGHT-ball_diameter)
        {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.intersects(paddle1))
        {
            ball.xVelocity = -ball.xVelocity;
            ball.xVelocity++;
            if (ball.yVelocity > 0)
            {
                ball.yVelocity++;
            }
            else
            {
                ball.yVelocity--;
            }
            ball.setxDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2))
        {
            ball.xVelocity = -ball.xVelocity;
            ball.xVelocity++;
            if (ball.yVelocity > 0)
            {
                ball.yVelocity++;
            } else
            {
                ball.yVelocity--;
            }
            ball.setxDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(paddle1.y<=0)
        {
            paddle1.y=0;
        }
        if(paddle1.y>=GAME_HEIGHT-PADDLE_HEIGHT)
        {
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        if(paddle2.y<=0)
        {
            paddle2.y=0;
        }
        if(paddle2.y>=GAME_HEIGHT-PADDLE_HEIGHT)
        {
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        if(ball.x>=GAME_WIDTH - ball_diameter)
        {
            newPaddles();
            newBall();
            score.player1++;
        }
        if (ball.x<=0)
        {
            newPaddles();
            newBall();
            score.player2++;
        }
    }

    private void move()
    {
        paddle1.move();
        paddle2.move();
        ball.move();

    }
    public class ActionListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }

}
