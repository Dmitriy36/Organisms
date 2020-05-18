//import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.BoxLayout;
import java.util.Random;
import java.util.ArrayList;

public class OrganismsGame extends JFrame implements ActionListener
{

    JFrame frame;
    DrawPanel drawPanel;

    public static int height=1000;
    public static int width=1000;


    public static int numberOfOrganisms = 1000;
    public static int organismSize = 10;
    public static int rightBorder = width/4;

    public static int distance=3;
    public static int speed = 50;


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

    private void begin(int height, int width) {
        Random rand = new Random();

        for(int i = 0; i < numberOfOrganisms; i++) {
            organisms.add(new Organism(organismSize, rand.nextInt(width - rightBorder - margin * 2 - 100), rand.nextInt(height - -margin * 2)));
        }

        frame = new JFrame("Organisms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        drawPanel = new DrawPanel();

      frame.getContentPane().add(BorderLayout.CENTER, drawPanel);


        frame.setResizable(true);
        frame.setSize(width,height);
//        frame.setLocationByPlatform(true);

        JPanel buttonPanel = new JPanel(new GridBagLayout());


        JButton b1 = new JButton("Test1");
        b1.addActionListener(this);
        JButton b2 = new JButton("Test2");
        b2.addActionListener(this);
        JButton b3 = new JButton("Test3");
        b3.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10,0,10,0);
        c.weightx=10;
        c.gridx=0;
        c.gridy=1;
        buttonPanel.add(b1,c);
        c.gridx=0;
        c.gridy=2;
        buttonPanel.add(b2,c);
        c.gridx=0;
        c.gridy=3;
        buttonPanel.add(b3,c);

        frame.add(buttonPanel, BorderLayout.EAST);

//        frame.getContentPane().add(BorderLayout.EAST, b1);
        frame.setVisible(true);
    }

    public void actionPerformed( ActionEvent evt)
    {
        String choice = evt.getActionCommand();

        if(choice.equals("Test1")) {
            System.out.println("Test1 button works.");
        }
        if(choice.equals("Test2")) {
            System.out.println("Test2 button works.");
        }
        if(choice.equals("Test3")) {
            System.out.println("Test3 button works.");
        }

    }


    class DrawPanel extends JPanel implements ActionListener {
        public void paintComponent(Graphics g) {

            g.setColor(Color.GREEN);
            g.fillRect(0, 0, this.getWidth() - rightBorder, this.getHeight());

            g.setColor(Color.WHITE);
            g.fillRect(margin, margin, this.getWidth() - rightBorder - margin*2, this.getHeight() - margin*2);


//            JPanel buttonPanel = new JPanel();
//            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
//
//
//            JButton b1 = new JButton("Test");
//            b1.setLayout(new FlowLayout(FlowLayout.RIGHT));
//            b1.addActionListener(this);
//            buttonPanel.add(b1);
//            this.add(buttonPanel, BoxLayout.X_AXIS);

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

        public void actionPerformed( ActionEvent evt)
        {
            getContentPane().setBackground( Color.blue );     // Change the Frame's background
            repaint();  // ask the system to paint the screen.
            System.out.println("button works.");
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
            else if (organism.getPositionX() <= margin ) // + organismSize )
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


                if (up) organism.setPositionY(organism.getPositionY() - distance);  // oneX -=speed;
                if (down) organism.setPositionY(organism.getPositionY() + distance); // oneY +=speed;
                if (left) organism.setPositionX(organism.getPositionX() - distance);// oneX -=speed;
                if (right) organism.setPositionX(organism.getPositionX() + distance);// oneX +=speed;
            }
        }
    }

    class Button extends JFrame implements ActionListener
    {
        JButton b1;
        Button(String title) {
            super(title);
            setLayout(new FlowLayout());

            // Construct a new button
            b1 = new JButton("Test");

            // Register the button object as the listener for JButton
            b1.addActionListener(this);
            add(b1); // Add the button to JFrame
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        public void actionPerformed(ActionEvent evt)
        {
            System.out.println("test");
        }


    }

}