import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;

public class Organisms
{

    JFrame frame;
    DrawPanel drawPanel;



    public static int height=300;
    public static int width=300;

    public static int organismSize = 16;
    public static int rightBorder = width/3;

    private int oneY = height/3;
    private int oneX = (width - rightBorder)/2;

    public static int margin = 16;


    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    public static void main(String... args)
    {

        new Organisms().begin(height,width);
    }

    private void begin(int height, int width)
    {
        frame = new JFrame("Organisms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(true);
        frame.setSize(width,height);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
//        bounce();
        random();
    }

    class DrawPanel extends JPanel
    {
//        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g)
        {
//            g.setColor(Color.BLACK);
//            g.fillRect(0,0, this.getWidth() + 200, this.getHeight());
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, this.getWidth() - rightBorder, this.getHeight());
//            g.fillRect(0,0,width,height);

            g.setColor(Color.WHITE);
            g.fillRect(margin, margin, this.getWidth() - rightBorder - margin-margin , this.getHeight()-margin-margin);


//            g.fillRect(3,3,width-margin,height-margin);

//            g.setColor(Color.WHITE);
//            g.fillRect(3, 3, this.getWidth() - 190, this.getHeight()-5);
            g.setColor(Color.BLACK);
            g.fillOval(oneX, oneY, organismSize, organismSize);

        }

        public int getWorkingWidth()
        {
            return this.getWidth() - rightBorder - margin-margin;
        }

        public int getWorkingHeight()
        {
            return this.getHeight() - margin-margin;
        }

    }

    private void random()
    {
        Random rand = new Random();
        int speed = 10;

        while(true)
        {
            if (oneX >= drawPanel.getWorkingWidth())
            {
                oneX -=speed;

            } else if (oneX <= margin)
            {
                oneX +=speed;
            } else if (oneY >= drawPanel.getWorkingHeight())
            {
                oneY -=speed;
            } else if (oneY <= margin)
            {
                oneY +=speed;
            }

            else {

                right = rand.nextBoolean();
                left = rand.nextBoolean();
                up = rand.nextBoolean();
                down = rand.nextBoolean();


            if (up) oneY -=speed;
            if (down) oneY +=speed;
            if (left) oneX -=speed;
            if (right) oneX +=speed;
            }

            try
            {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            frame.repaint();

        }
    }

    private void bounce()
    {
        while (true)
        {
            if (oneX >= drawPanel.getWorkingWidth())
            {
                right = false;
                left = true;
            }
            if (oneX <= margin)
            {
                right = true;
                left = false;
            }
            if (oneY >= drawPanel.getWorkingHeight())
            {
                up = true;
                down = false;
            }
            if (oneY <= margin)
            {
                up = false;
                down = true;
            }
            if (up) oneY--;
            if (down) oneY++;
            if (left) oneX--;
            if (right) oneX++;
            try
            {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            frame.repaint();
        }
    }
}