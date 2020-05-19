import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.Random;
import java.util.ArrayList;


import static java.lang.Math.abs;

public class OrganismsGame extends JFrame implements ActionListener
{

    JFrame frame;
    DrawPanel drawPanel;

    public static int height=1000;
    public static int width=1000;

    public static int infectionRadius = 5;


    public static int numberOfOrganisms = 1000;
    public static int organismSize = 10;
    public static int rightBorder = 0; //width/200;

    public static int distance=5;
    public static int speed = 75;


//    private int oneY = height/3;
//    private int oneX = (width - rightBorder)/2;
    public static int margin = 5;
    public static ArrayList<Organism> organisms = new ArrayList<>();
    public static int sidePanelWidth = 200;
    public JPanel buttonPanel = new JPanel();

    public int infectedCounter = 0;

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    public static void main(String... args)
    {
        new OrganismsGame().begin(height,width);
    }

    private void begin(int height, int width) {
        Random rand = new Random();

        for(int i = 0; i < numberOfOrganisms; i++) {
            organisms.add(new Organism(i, organismSize, rand.nextInt(width-sidePanelWidth-margin-organismSize), rand.nextInt(height-margin-organismSize)));
        }

        frame = new JFrame("Organisms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.RED);


        drawPanel = new DrawPanel();

      frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(true);
        frame.setSize(width,height);

        JButton b1 = new JButton("Infect one");
        b1.addActionListener(this);
        JButton b2 = new JButton("Cure all");
        b2.addActionListener(this);
        JButton b3 = new JButton("Cure one");
        b3.addActionListener(this);

        buttonPanel.setLayout(new GridLayout(10,1, 0, 10));

        buttonPanel.setPreferredSize(new Dimension(sidePanelWidth,1000));

        buttonPanel.add(b1);
        buttonPanel.add(b3);
        buttonPanel.add(b2);

        Border border = BorderFactory.createLineBorder(Color.BLUE,1);

        JLabel label1 = new JLabel("Infected: " + "\n" + String.valueOf(infectedCounter), SwingConstants.CENTER);
        label1.setBorder(border);
        label1.setOpaque(true);
        label1.setBackground(Color.YELLOW);
        buttonPanel.add(label1);
        buttonPanel.setBackground(Color.GRAY);

        frame.add(buttonPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void infect(ArrayList<Organism> organisms) {
        Random rand = new Random();
        int infected = rand.nextInt(organisms.size());
        organisms.get(infected).infect();
    }

    public void cureAll(ArrayList<Organism> organisms) {
        for(Organism current_organism: organisms) {
            if(current_organism.infectCheck() == true) {
                current_organism.cure();
            }
        }
    }

    public void cureOne(ArrayList<Organism> organisms) {
        for(Organism current_organism: organisms) {
            if(current_organism.infectCheck() == true) {
                current_organism.cure();
                break;
            }
        }
    }

    public void actionPerformed( ActionEvent evt)
    {
        String choice = evt.getActionCommand();

        if(choice.equals("Infect one")) {
            infect(organisms);
        }
        if(choice.equals("Cure all")) {
            cureAll(organisms);
        }
        if(choice.equals("Cure one")) {
            cureOne(organisms);
        }

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

            drawPanel.getBackground();
            for (Organism current_organism : organisms) {

                proximityInfection(organisms, current_organism);

                if(current_organism.infectCheck() == true) {
                    g.setColor(Color.RED);
                } else g.setColor(Color.BLUE);
                g.fillOval(current_organism.getPositionX(), current_organism.getPositionY(), current_organism.getSize(), current_organism.getSize());
                g.setFont(new Font("Lucida Sans", Font.PLAIN, 9));
                g.drawString(current_organism.getID(), current_organism.getPositionX(), current_organism.getPositionY());
            }

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.repaint();

        }

        public void proximityInfection(ArrayList<Organism> organisms, Organism organism1) {
            for(Organism current_organism: organisms) {
                if (current_organism.infectCheck() == true && abs(organism1.getPositionX() - current_organism.getPositionX()) <= infectionRadius && abs(organism1.getPositionY() - current_organism.getPositionY()) <= infectionRadius)  { //&& abs(organism1.getPositionY() - current_organism.getPositionY()) <= infectionRadius
                    organism1.infect();
                    infectedCounter++;
                }
            }
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

            if (organism.getPositionX() >= drawPanel.getWorkingWidth() - margin) // - organismSize)
            {organism.setPositionX(organism.getPositionX() - distance);}
            else if (organism.getPositionX() <= margin) // + organismSize )
            {organism.setPositionX(organism.getPositionX() + distance);}
            else if (organism.getPositionY() >= drawPanel.getWorkingHeight() - margin) // - organismSize)
            {organism.setPositionY(organism.getPositionY() - distance);}
            else if (organism.getPositionY() <= margin) // + organismSize)
            {organism.setPositionY(organism.getPositionY() + distance);}

            else {

                right = rand.nextBoolean();
                left = rand.nextBoolean();
                up = rand.nextBoolean();
                down = rand.nextBoolean();


                if (up) organism.setPositionY(organism.getPositionY() - rand.nextInt(distance));  // oneX -=speed;
                if (down) organism.setPositionY(organism.getPositionY() + rand.nextInt(distance)); // oneY +=speed;
                if (left) organism.setPositionX(organism.getPositionX() - rand.nextInt(distance));// oneX -=speed;
                if (right) organism.setPositionX(organism.getPositionX() + rand.nextInt(distance));// oneX +=speed;
            }
        }
    }
}