/*
* Programmer: Afonin, Anthony
* Chemeketa Community College
* Created: Friday, April 14, 2017 3:42:44 PM
* Assignment: Week 3, Threaded Dice - Part 2
* File Name: DiceSimulator.java
 */
/**
 * This application rolls two die on individual threads,
 * then counts and displays the number of time each face is shown.
 */
package threadeddice;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * A dice simulator that rolls two die and counts face values.
 */
public class DiceSimulator extends JFrame 
{
    // JLabel and JTextField to display number of 1s
    private JLabel side1JLabel;
    private JTextField output1JTextField;

    // JLabel and JTextField to display number of 2s
    private JLabel side2JLabel;
    private JTextField output2JTextField;

    // JLabel and JTextField to display number of 3s
    private JLabel side3JLabel;
    private JTextField output3JTextField;

    // JLabel and JTextField to display number of 4s
    private JLabel side4JLabel;
    private JTextField output4JTextField;

    // JLabel and JTextField to display number of 5s
    private JLabel side5JLabel;
    private JTextField output5JTextField;

    // JLabel and JTextField to display number of 6s
    private JLabel side6JLabel;
    private JTextField output6JTextField;

    // JLabel and JTextField to display number of doubles
    private JLabel dblsJLabel;
    private JTextField dblsJTextField;

    // JLabel and JTextField to display number of 7s
    private JLabel sevensJLabel;
    private JTextField sevensJTextField;

    // JLabel and JTextField to display number of 11s
    private JLabel elevensJLabel;
    private JTextField elevensJTextField;

    // JLabels to display dice
    private JLabel die1JLabel;
    private JLabel die2JLabel;

    // JButton to roll dice
    private JButton rollJButton;
    private JButton stopJButton;
    private JButton resetJButton;

    // dice face constants
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;

    // constants for 7, 11, and interval count
    private final int SEVEN = 7;
    private final int ELEVEN = 11;
    private final int COUNT = 1;

    // file name and directory constants
    private final String FILE_PREFIX = "images/die";
    private final String FILE_SUFFIX = ".png";

    // screen dimension
    private final int WIDTH = 325;
    private final int HEIGHT = 360;

    // variable that stops threads
    private volatile boolean running;

    // create a new die object
    Die die1 = new Die();
    Die die2 = new Die();

    // declare new thread for each die object
    Thread dieThread1, dieThread2;

    // private variables for roll speed and value counts
    private int rollSpeed, count1, count2, count3, count4, count5, count6,
            countDoubles, countSevens, countElevens;

    // declare variables for each die face value and sum of both die.
    private int die1Value, die2Value, sumOfDie;

    /**
     * no-argument constructor
     */
    public DiceSimulator()
    {
        createUserInterface();
    }

    /**
     * Create and position GUI components; register event handlers
     */
    private void createUserInterface() 
    {
        // get content pane for attaching GUI components
        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components
        contentPane.setLayout(null);

        // set up side1JLabel
        side1JLabel = new JLabel();
        side1JLabel.setBounds(16, 16, 48, 23);
        side1JLabel.setText("Side 1:");
        contentPane.add(side1JLabel);

        // set up output1JTextField
        output1JTextField = new JTextField();
        output1JTextField.setBounds(72, 16, 56, 23);
        output1JTextField.setText("0");
        output1JTextField.setEditable(false);
        output1JTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(output1JTextField);

        // set up side2JLabel
        side2JLabel = new JLabel();
        side2JLabel.setBounds(16, 48, 48, 23);
        side2JLabel.setText("Side 2:");
        contentPane.add(side2JLabel);

        // set up output2JTextField
        output2JTextField = new JTextField();
        output2JTextField.setBounds(72, 48, 56, 23);
        output2JTextField.setText("0");
        output2JTextField.setEditable(false);
        output2JTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(output2JTextField);

        // set up side3JLabel
        side3JLabel = new JLabel();
        side3JLabel.setBounds(16, 80, 48, 23);
        side3JLabel.setText("Side 3:");
        contentPane.add(side3JLabel);

        // set up output3JTextField
        output3JTextField = new JTextField();
        output3JTextField.setBounds(72, 80, 56, 23);
        output3JTextField.setText("0");
        output3JTextField.setEditable(false);
        output3JTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(output3JTextField);

        // set up side4JLabel
        side4JLabel = new JLabel();
        side4JLabel.setBounds(16, 112, 48, 23);
        side4JLabel.setText("Side 4:");
        contentPane.add(side4JLabel);

        // set up output4JTextField
        output4JTextField = new JTextField();
        output4JTextField.setBounds(72, 111, 56, 23);
        output4JTextField.setText("0");
        output4JTextField.setEditable(false);
        output4JTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(output4JTextField);

        // set up side5JLabel
        side5JLabel = new JLabel();
        side5JLabel.setBounds(16, 144, 48, 23);
        side5JLabel.setText("Side 5:");
        contentPane.add(side5JLabel);

        // set up output5JTextField
        output5JTextField = new JTextField();
        output5JTextField.setBounds(72, 144, 56, 23);
        output5JTextField.setText("0");
        output5JTextField.setEditable(false);
        output5JTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(output5JTextField);

        // set up side6JLabel
        side6JLabel = new JLabel();
        side6JLabel.setBounds(16, 176, 48, 23);
        side6JLabel.setText("Side 6:");
        contentPane.add(side6JLabel);

        // set up output6JTextField
        output6JTextField = new JTextField();
        output6JTextField.setBounds(72, 176, 56, 23);
        output6JTextField.setText("0");
        output6JTextField.setEditable(false);
        output6JTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(output6JTextField);

        // set up doubleJLabel
        dblsJLabel = new JLabel();
        dblsJLabel.setBounds(16, 208, 48, 23);
        dblsJLabel.setText("Double:");
        contentPane.add(dblsJLabel);

        // set up doubleJTextField
        dblsJTextField = new JTextField();
        dblsJTextField.setBounds(72, 208, 56, 23);
        dblsJTextField.setText("0");
        dblsJTextField.setEditable(false);
        dblsJTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(dblsJTextField);

        // set up sevenJLabel
        sevensJLabel = new JLabel();
        sevensJLabel.setBounds(16, 240, 48, 23);
        sevensJLabel.setText("Seven:");
        contentPane.add(sevensJLabel);

        // set up sevenJTextField
        sevensJTextField = new JTextField();
        sevensJTextField.setBounds(72, 240, 56, 23);
        sevensJTextField.setText("0");
        sevensJTextField.setEditable(false);
        sevensJTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(sevensJTextField);

        // set up elevenJLabel
        elevensJLabel = new JLabel();
        elevensJLabel.setBounds(16, 272, 48, 23);
        elevensJLabel.setText("Eleven:");
        contentPane.add(elevensJLabel);

        // set up elevenJTextField
        elevensJTextField = new JTextField();
        elevensJTextField.setBounds(72, 272, 56, 23);
        elevensJTextField.setText("0");
        elevensJTextField.setEditable(false);
        elevensJTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(elevensJTextField);

        // set up die1JLabel
        die1JLabel = new JLabel("");
        die1JLabel.setBounds(152, 32, 72, 64);
        contentPane.add(die1JLabel);

        // set up die2JLabel
        die2JLabel = new JLabel("");
        die2JLabel.setBounds(208, 96, 72, 64);
        contentPane.add(die2JLabel);

        // set up rollJButton
        rollJButton = new JButton();
        rollJButton.setBounds(150, 190, 136, 24);
        rollJButton.setText("Roll");
        contentPane.add(rollJButton);

        rollJButton.addActionListener(
            new ActionListener() 
            {
                /**
                 * Anonymous inner class. 
                 * Event handler called when rollJButton is clicked.
                 *
                 * @param event Event handler is called.
                 */
                public void actionPerformed(ActionEvent event) 
                {
                    rollJButtonActionPerformed(event);
                }
            }
        );

        // set up stopJButton
        stopJButton = new JButton();
        stopJButton.setBounds(150, 220, 136, 24);
        stopJButton.setText("Stop");
        contentPane.add(stopJButton);

        stopJButton.addActionListener(
            new ActionListener() 
            {
                /**
                 * Anonymous inner class. 
                 * Event handler called when stopJButton is clicked.
                 *
                 * @param event Event handler is called.
                 */
                public void actionPerformed(ActionEvent event) 
                {
                    stopJButtonActionPerformed(event);
                }
            }
        );

        // set up resetJButton
        resetJButton = new JButton();
        resetJButton.setBounds(150, 270, 136, 24);
        resetJButton.setText("Reset");
        contentPane.add(resetJButton);

        resetJButton.addActionListener(
            new ActionListener() 
            {
                /**
                 * Anonymous inner class. 
                 * Event handler called when resetJButton is clicked.
                 *
                 * @param event Event handler is called.
                 */
                public void actionPerformed(ActionEvent event)
                {
                    resetJButtonActionPerformed(event);
                }
            }
        );

        // set properties of application's window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Dice Simulator");             // set title bar string
        int x = (screen.width - WIDTH) / 2;
        int y = (screen.height - HEIGHT) / 2;
        setBounds(x, y, WIDTH, HEIGHT);
        setSize(WIDTH, HEIGHT);			// set window size
        setVisible(true);       		// display window            
    }

    /**
     * Starts or resumes thread for each die.
     *
     * @param event Event handler is called when rollJButton is clicked.
     */
    private void rollJButtonActionPerformed(ActionEvent event)
    {

        /**
         * Inner Thread class for die1
         */
        Runnable die1Thread = new Runnable() 
        {
            /**
             * Rolls and displays roll animation for die1
             */
            public void run()
            {

                while (running)
                {
                    // Set rollSpeed and roll die
                    rollSpeed = ((int) (Math.random() * 200) + 15);
                    die1Value = die1.roll();

                    //display the dice images
                    displayDie(die1JLabel, die1Value);

                    // Displays die at different speed every thread interval
                    try 
                    {
                        Thread.sleep(rollSpeed);
                    } 
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(
                                DiceSimulator.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }
        };

        /**
         * Inner Thread class for die2
         */
        Runnable die2Thread = new Runnable() 
        {
            /**
             * Rolls and displays roll animation for die2
             */
            public void run() 
            {

                while (running) 
                {
                    // Set rollSpeed and roll die
                    rollSpeed = ((int) (Math.random() * 300) + 25);
                    die2Value = die2.roll();

                    //display the dice images 
                    displayDie(die2JLabel, die2Value);

                    // Displays die at different speed every thread interval
                    try 
                    {
                        Thread.sleep(rollSpeed);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(
                                DiceSimulator.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }
        };

        // Change the running boolean to true for threads
        running = true;

        // Creates new thread for each die
        dieThread1 = new Thread(die1Thread);
        dieThread2 = new Thread(die2Thread);

        // Starts each thread
        dieThread1.start();
        dieThread2.start();

    }

    /**
     * Stop dice; display frequency of each side and special rolls
     *
     * @param event Event handler is called when stopJButton is clicked.
     */
    private void stopJButtonActionPerformed(ActionEvent event)
    {
        // Change running boolean of threads to false
        running = false;
        
        // Add the shown values and specials of the die 
        displayFrequency(die1Value);
        displayFrequency(die2Value);
        displaySpecials(die1Value, die2Value);
    }

    /**
     * Pause each die thread, reset count values and JLabel/JTextFields.
     *
     * @param event Event handler is called when resetJButton is clicked.
     */
    private void resetJButtonActionPerformed(ActionEvent event) 
    {
        // Change running boolean of threads to false
        running = false;

        // clear point icrons
        die1JLabel.setIcon(null);
        die2JLabel.setIcon(null);

        // set count values to 0
        count1 = 0;
        count2 = 0;
        count3 = 0;
        count4 = 0;
        count5 = 0;
        count6 = 0;
        countDoubles = 0;
        countSevens = 0;
        countElevens = 0;

        // reset text fields
        output1JTextField.setText(String.valueOf(count1));
        output2JTextField.setText(String.valueOf(count2));
        output3JTextField.setText(String.valueOf(count3));
        output4JTextField.setText(String.valueOf(count4));
        output5JTextField.setText(String.valueOf(count5));
        output6JTextField.setText(String.valueOf(count6));
        dblsJTextField.setText(String.valueOf(countDoubles));
        sevensJTextField.setText(String.valueOf(countSevens));
        elevensJTextField.setText(String.valueOf(countElevens));
    }

    /**
     * Displays the die image of corresponding rolled face value.
     *
     * @param picDieJLabel The JLabel of either die1 or die2.
     * @param face The rolled die face value.
     */
    private void displayDie(JLabel picDieJLabel, int face) 
    {
        ImageIcon image = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                getClass().getResource(FILE_PREFIX + face + FILE_SUFFIX))
        );

        //display die images in picDieJLabel
        picDieJLabel.setIcon(image);
    }

    /**
     * Count and display the current frequency of each rolled face value.
     * Additional verbose description.
     *
     * @param face The rolled die face value.
     */
    private void displayFrequency(int face) 
    {
        switch (face) 
        {
            case 1:
                count1 += COUNT;
                output1JTextField.setText(String.valueOf(count1));
                break;
            case 2:
                count2 += COUNT;
                output2JTextField.setText(String.valueOf(count2));
                break;
            case 3:
                count3 += COUNT;
                output3JTextField.setText(String.valueOf(count3));
                break;
            case 4:
                count4 += COUNT;
                output4JTextField.setText(String.valueOf(count4));
                break;
            case 5:
                count5 += COUNT;
                output5JTextField.setText(String.valueOf(count5));
                break;
            case 6:
                count6 += COUNT;
                output6JTextField.setText(String.valueOf(count6));
                break;
            default:
                break;
        }
    }

    /**
     * Count and display the frequency of rolled doubles, sevens, and elevens.
     *
     * @param face1 The rolled face value of die1.
     * @param face2 The rolled face value of die2.
     */
    public void displaySpecials(int face1, int face2) 
    {
        sumOfDie = face1 + face2;

        if (face1 == face2)
        {
            countDoubles += COUNT;
            dblsJTextField.setText(String.valueOf(countDoubles));
        }

        if (sumOfDie == SEVEN)
        {
            countSevens += COUNT;
            sevensJTextField.setText(String.valueOf(countSevens));
        } else if (sumOfDie == ELEVEN)
        {
            countElevens += COUNT;
            elevensJTextField.setText(String.valueOf(countElevens));
        }
    }

    /**
     * Main method creates new DiceSimulator.
     */
    public static void main(String[] args)
    {
        DiceSimulator application = new DiceSimulator();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
