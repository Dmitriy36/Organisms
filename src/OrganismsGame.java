import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;
import java.util.ArrayList;

public class OrganismsGame
{

    JFrame frame;
    DrawPanel drawPanel;



    public static int height=600;
    public static int width=600;

    public static int organismSize = 6;
    public static int rightBorder = width/3;

    public static int distance=10;
    public static int speed = 100;


    private int oneY = height/3;
    private int oneX = (width - rightBorder)/2;
    public static int margin = 6;
    public static ArrayList<Organism> organisms = new ArrayList<>();

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    public static void main(String... args)
    {

        new OrganismsGame().begin(height,width);
    }

    private void begin(int height, int width)    {
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));
        organisms.add(new Organism(organismSize, 150,150));

        frame = new JFrame("Organisms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(true);
        frame.setSize(width,height);
//        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, this.getWidth() - rightBorder, this.getHeight());

            g.setColor(Color.WHITE);
            g.fillRect(margin, margin, this.getWidth() - rightBorder - margin*2, this.getHeight() - margin*2);


            for (Organism current_organism : organisms) {
                random(current_organism);
            }
            for (Organism current_organism : organisms) {
                g.setColor(Color.BLACK);
                g.fillOval(current_organism.getPositionX(), current_organism.getPositionY(), current_organism.getSize(), current_organism.getSize());
            }

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.repaint();

        }

        public int getWorkingWidth() {
            return this.getWidth() - rightBorder - margin*2;
        }

        public int getWorkingHeight() {
            return this.getHeight() - margin*2;
        }

        private void random(Organism organism) // (Graphics g)
        {
            Random rand = new Random();

            if (organism.getPositionX() >= drawPanel.getWorkingWidth())
            {organism.setPositionX(organism.getPositionX() - distance);}
            else if (organism.getPositionX() <= margin)
            {organism.setPositionX(organism.getPositionX() + distance);}
            else if (organism.getPositionY() >= drawPanel.getWorkingHeight())
            {organism.setPositionY(organism.getPositionY() - distance);}
            else if (organism.getPositionY() <= margin)
            {organism.setPositionY(organism.getPositionY() + distance);}

            else {

                right = rand.nextBoolean();
                left = rand.nextBoolean();
                up = rand.nextBoolean();
                down = rand.nextBoolean();


                if (up) organism.setPositionY(organism.getPositionY() - distance);  // oneX -=speed;
                if (down) organism.setPositionY(organism.getPositionY() + distance); // oneY +=speed;
                if (left) organism.setPositionX(organism.getPositionX() - distance);// oneX -=speed;
                if (right) organism.setPositionX(organism.getPositionX() + distance);// oneX +=speed;
            }

        }

        private void bounce() {
            while (true) {
                if (oneX >= drawPanel.getWorkingWidth()) {
                    right = false;
                    left = true;
                }
                if (oneX <= margin) {
                    right = true;
                    left = false;
                }
                if (oneY >= drawPanel.getWorkingHeight()) {
                    up = true;
                    down = false;
                }
                if (oneY <= margin) {
                    up = false;
                    down = true;
                }
                if (up) oneY--;
                if (down) oneY++;
                if (left) oneX--;
                if (right) oneX++;
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                frame.repaint();
            }
        }
    }
}