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

    public Random rand = new Random();
    public static int height=1000;
    public static int width=1000;

    public int infectionRadius = 10;


    public int numberOfOrganisms = 1000;
    public int organismSize = 10;
    public int rightBorder = 0; //width/200;

    public int distance=5;
    public int speed = 75;


//    private int oneY = height/3;
//    private int oneX = (width - rightBorder)/2;
    public int margin = 5;
    public ArrayList<Organism> organisms = new ArrayList<>();
    public ArrayList<Organism> infectedOrganisms = new ArrayList<>();
    public int sidePanelWidth = 200;
    public JPanel buttonPanel = new JPanel();

    public JLabel label1;
    public JLabel label2;
    public JLabel label3;
    public JLabel label4;

    public int infectedManually = 0;
    public int infectedProximity = 0;

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    public static void main(String... args)
    {
        new OrganismsGame().begin(height,width);
    }

    private void begin(int height, int width) {
//        Random rand = new Random();

        for(int i = 0; i < numberOfOrganisms; i++) {
            organisms.add(new Organism(i, organismSize, rand.nextInt(width-sidePanelWidth-margin-organismSize), rand.nextInt(height-margin-organismSize)));
        }

        frame = new JFrame("Organisms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.RED);


        drawPanel = new DrawPanel();

      frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        frame.setSize(width,height);

        JButton b1 = new JButton("Infect one");
        b1.addActionListener(this);
        JButton b2 = new JButton("Cure all");
        b2.addActionListener(this);
        JButton b3 = new JButton("Cure one");
        b3.addActionListener(this);

        buttonPanel.setLayout(new GridLayout(10,1, 0, 10));

        buttonPanel.setPreferredSize(new Dimension(sidePanelWidth,1000));

        Border border = BorderFactory.createLineBorder(Color.BLUE,1);

        b1.setBorder(border);
        b2.setBorder(border);
        b3.setBorder(border);

        buttonPanel.add(b1);
        buttonPanel.add(b3);
        buttonPanel.add(b2);

        label2 = new JLabel("Infected manually: " + "\n" + infectedManually, SwingConstants.CENTER);
        label2.setBorder(border);
        label2.setOpaque(true);
        label2.setBackground(Color.YELLOW);
        buttonPanel.add(label2);

        label1 = new JLabel("Infected by proximity: " + "\n" + infectedProximity, SwingConstants.CENTER);
        label1.setBorder(border);
        label1.setOpaque(true);
        label1.setBackground(Color.YELLOW);
        buttonPanel.add(label1);

        label3 = new JLabel("Total infected: " + "\n" + infectedProximity + infectedManually, SwingConstants.CENTER);
        label3.setBorder(border);
        label3.setOpaque(true);
        label3.setBackground(Color.YELLOW);
        buttonPanel.add(label3);

        buttonPanel.setBackground(Color.GRAY);

        frame.add(buttonPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void proximityInfection(ArrayList<Organism> organisms, Organism organismChecked) {
        for(Organism current_organism: organisms) {
            if (current_organism.infectCheck()
                    && abs(organismChecked.getPositionX() - current_organism.getPositionX()) <= infectionRadius
                    && abs(organismChecked.getPositionY() - current_organism.getPositionY()) <= infectionRadius
            )
            {
                organismChecked.infect();
                if(!infectedOrganisms.contains(organismChecked)) {infectedOrganisms.add(organismChecked);}
                System.out.println(infectedOrganisms.size());
            }
        }
    }

    public void infectRandom(ArrayList<Organism> organisms) {
        int infected = rand.nextInt(organisms.size());
        organisms.get(infected).infect();
        infectedManually++;
        infectedOrganisms.add(organisms.get(infected));

    }

    public void cureAll(ArrayList<Organism> organisms) {
        for(Organism current_organism: organisms) {
            if(current_organism.infectCheck()) {
                current_organism.cure();
            }
            infectedOrganisms.clear();
            infectedManually = 0;
        }
    }

    public void cureOne(ArrayList<Organism> organisms) {
        for(Organism current_organism: organisms) {
            if(current_organism.infectCheck()) {
                current_organism.cure();
                infectedOrganisms.remove(current_organism);
                break;
            }
        }
    }

    public void actionPerformed( ActionEvent evt)
    {
        String choice = evt.getActionCommand();

        if(choice.equals("Infect one")) {
            infectRandom(organisms);
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
            g.fillRect(margin, margin, this.getWidth() - rightBorder - margin * 2, this.getHeight() - margin * 2);

            for (Organism current_organism : organisms) {
                random(current_organism);
                proximityInfection(organisms, current_organism);
            }

            drawPanel.getBackground();
            for (Organism current_organism : organisms) {

                if (current_organism.infectCheck()) {
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



            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    infectedProximity = infectedOrganisms.size();
                    label1.setText("Infected by proximity: " +infectedProximity);
                    label2.setText("Infected manually: " +infectedManually);
                    label3.setText("Infected total: " + (infectedManually + infectedProximity));
                }
            });

            buttonPanel.repaint();
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
//            Random rand = new Random();

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