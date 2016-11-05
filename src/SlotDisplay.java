import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SlotDisplay extends JComponent implements ActionListener
{
    private imagePanelOne one;
    private imagePanelTwo two;
    private informationPanel information;
    private int width;
    private int height;
    private boolean hasTwo;
    private Timer closeTimer;
    
    public SlotDisplay(SlotMachine machine, int width, int height)
    {
        this.setSize(this.width = 7*width/8, this.height = height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setPreferredSize(new Dimension(this.width, this.height));
        System.out.printf("%s, %s\n", 7*width/8, height);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        hasTwo = false;
        this.add(one = new imagePanelOne(machine, this.getWidth(), 2*this.getHeight()/5));
        if(machine.getResult().length > 4)
        {
            hasTwo = true;
            this.add(two = new imagePanelTwo(machine, this.getWidth(), 2*this.getHeight()/5));
        }
        this.add(information = new informationPanel(machine, this.getWidth(), this.getHeight()/5));
        repaint();
        validate();
        System.out.println(this.width);
        System.out.println(this.height);
//        for(int i = 0; i < machine.getResult().length; i++)
//        {
//            this.add(new imagePanel(machine, Width, Height));
//        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
//        this.remove(one);
//        if(hasTwo)
//        {
//            this.remove(two);
//        }
//        this.remove(information);
        this.removeAll();
        this.setSize(width, height);
        //System.out.println(e.getSource().getClass());
        this.add(one = new imagePanelOne((SlotMachine)e.getSource(), this.getWidth(), 2*this.getHeight()/5));
        if(((SlotMachine)e.getSource()).getResult().length > 4)
        {
            this.add(two = new imagePanelTwo((SlotMachine)e.getSource(), this.getWidth(), 2*this.getHeight()/5));
            hasTwo = true;
        }
        else
        {
            hasTwo = false;
        }
        this.add(information = new informationPanel((SlotMachine)e.getSource(), this.getWidth(), this.getHeight()/5));
        repaint();
        validate();
        revalidate();
        if(((SlotMachine)e.getSource()).getChangedStatus())
        {
            System.out.println("Stats: ");
            System.out.println(((SlotMachine)(e.getSource())).getPrize());
            System.out.println(((SlotMachine)e.getSource()).getTokens());
                try
                {
                    if(((SlotMachine)e.getSource()).getPrize() > 0)
                    {
                        //JOptionPane.showMessageDialog(null, "You have won", "Win Screen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageIO.read(this.getClass().getResource(((SlotMachine)e.getSource()).getWinImage())).getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                        showResult(new ImageIcon(ImageIO.read(this.getClass().getResource(((SlotMachine)e.getSource()).getWinImage()))), ("<html><center>You won " + (((SlotMachine)e.getSource()).getPrize()) + " tokens!<br>" + (((SlotMachine)e.getSource()).getWinMessage())));
                    }
                    else if(((SlotMachine)e.getSource()).getTokens() == 0)
                    {
                        showResult(new ImageIcon(ImageIO.read(this.getClass().getResource(((SlotMachine)e.getSource()).getLoseImage()))), ("<html><center>You don't have any tokens! Please restart the game to play! <br>" + ((SlotMachine)e.getSource()).getLoseMessage()));
                    }
                    else
                    {
                        System.out.println("Does it go here?");
                        //JOptionPane.showMessageDialog(null, "You didn't won", "Lose Screen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageIO.read(this.getClass().getResource(((SlotMachine)e.getSource()).getLoseImage())).getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                        showResult(new ImageIcon(ImageIO.read(this.getClass().getResource(((SlotMachine)e.getSource()).getLoseImage()))), ("<html><center>You didn't win any tokens! <br>" + ((SlotMachine)e.getSource()).getLoseMessage()));
                    }
                }
            catch(Exception E)
            {
                System.out.println(E.getLocalizedMessage());
            }
        }
//        this.add(one = new imagePanelOne((SlotMachine)e.getSource(), this.getWidth(), 2*this.getHeight()/5));
//        one.setLocation(0, 0);
//        if(((SlotMachine)e.getSource()).getResult().length > 4)
//        {
//           this.add(two = new imagePanelTwo((SlotMachine)e.getSource(), this.getWidth(), 2*this.getHeight()/5));
//        }
//        information.refreshInformation((SlotMachine)(e.getSource()));
//        this.add(information);
//        //this.add(new informationPanel((SlotMachine)e.getSource(), this.getWidth(), this.getHeight()/5));
//        this.repaint();
//        this.validate();
    }
    
    class imagePanelOne extends JPanel
    {
        public imagePanelOne(SlotMachine machine, int width, int height)
        {
            this.setBackground(new Color(0, 0, 0, 0));
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setSize(width, height);
            for(int i = 0; i < machine.getResult().length && i < 4; i++)
            {
                //System.out.println(machine.getResult()[i]);
                try
                {
                    //Image imageFileOne = ImageIO.read(this.getClass().getResource("35331817.png"));
                    //Image imageFileTwo = ImageIO.read(this.getClass().getResource("34324859.png"));
                    int division = 0;
                    if(machine.getResult().length > 4)
                    {
                        division = 4;
                    }
                    else
                    {
                        division = machine.getResult().length;
                    }
                    Image imageFile;
                    if(machine.getResult()[i]%2 == 0)
                        //imageFile = imageFileOne;
                        imageFile = machine.getIcon(machine.getResult()[i]);
                    else
                        imageFile = machine.getIcon(machine.getResult()[i]);
                        //imageFile = imageFileTwo;
                    JLabel image = new JLabel(new ImageIcon(imageFile.getScaledInstance(this.getWidth()/division, this.getWidth()/division, Image.SCALE_DEFAULT)));
                    this.add(image);
                    
                    //Test code
                    JPanel Test = new JPanel();
                    Test.setBackground(new Color(0,0,0,0));
                    //Test.setLayout(new BoxLayout(Test, BoxLayout.Y_AXIS));
                    Test.setLayout(new BorderLayout());
                    Test.add(image, BorderLayout.NORTH);
                    JButton stop = new JButton("Stop");
                    stop.addActionListener(new stopButton(i, machine));
                    stop.setSize(this.getWidth()/division, stop.getHeight());
                    Test.add(stop, BorderLayout.CENTER);
                    this.add(Test);
                    //End Test code
                    
                }
                catch(Exception e)
                {
                    System.out.println(e.getLocalizedMessage());

                }
            }
        }
    }
    
    class imagePanelTwo extends JPanel
    {
        public imagePanelTwo(SlotMachine machine, int width, int height)
        {
            this.setBackground(new Color(0, 0, 0, 0));
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setSize(width, height);
            for(int i = 4; i < machine.getResult().length; i++)
            {
                try
                {
                    //Image imageFileOne = ImageIO.read(this.getClass().getResource("35331817.png"));
                    //Image imageFileTwo = ImageIO.read(this.getClass().getResource("34324859.png"));
                    //JLabel image = new JLabel(new ImageIcon(imageFileOne.getScaledInstance(this.getWidth()/4, this.getWidth()/4, Image.SCALE_DEFAULT)));
                    //JLabel image2 = new JLabel(new ImageIcon(imageFileTwo.getScaledInstance(this.getWidth()/4, this.getWidth()/4, Image.SCALE_DEFAULT)));
                    //if(machine.getResult()[i] %2 == 0)
                    Image imageFile = machine.getIcon(machine.getResult()[i]);
                    {
                        JLabel image = new JLabel(new ImageIcon(imageFile.getScaledInstance(this.getWidth()/4, this.getWidth()/4, Image.SCALE_DEFAULT)));
                        //this.add(image);
                        
                        //Test code
                        JPanel Test = new JPanel();
                        Test.setBackground(new Color(0,0,0,0));
                        //Test.setLayout(new BoxLayout(Test, BoxLayout.Y_AXIS));
                        Test.setLayout(new BorderLayout());
                        Test.add(image, BorderLayout.NORTH);
                        JButton stop = new JButton("Stop");
                        stop.addActionListener(new stopButton(i, machine));
                        stop.setSize(image.getWidth(), stop.getHeight());
                        Test.add(stop, BorderLayout.SOUTH);
                        this.add(Test);
                        //End Test code
                    
                    }
                    //else
                    //{
                    //    this.add(image2);
                    //}
                }
                catch(Exception e)
                {
                    System.out.println(e.getLocalizedMessage());

                }
            }
        }
    }
    
    class informationPanel extends JPanel
    {
        private JTextField showTokens;
        private JTextField showLastPrize;
        
        public informationPanel(SlotMachine machine, int width, int height)
        {
            this.setBackground(new Color(255, 255, 255, 150));
            this.setSize(width, height);
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            
            JLabel tokens = new JLabel("<html><center>Tokens: ");
            tokens.setPreferredSize(new Dimension(this.getWidth()/4, height));
            tokens.setFont(new Font("Jokerman", Font.BOLD, 30));
            this.add(tokens);
            
            showTokens = new JTextField();
            showTokens.setText("" + machine.getTokens());
            showTokens.setFont(new Font("Brush Script MT", Font.BOLD, 80));
            showTokens.setHorizontalAlignment(JTextField.CENTER);
            showTokens.setMinimumSize(new Dimension(this.getWidth()/4, height/2));
            showTokens.setMaximumSize(new Dimension(this.getWidth()/4, this.getHeight()));
            showTokens.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
            showTokens.setFocusable(false);
            this.add(showTokens);
            
            JLabel lastPrize = new JLabel("<html><center>Last Rounds<br>Prize: ");
            lastPrize.setFont(new Font("Jokerman", Font.BOLD, 30));
            lastPrize.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
            this.add(lastPrize);
            
            showLastPrize = new JTextField();
            showLastPrize.setText("" + machine.getPrize());
            showLastPrize.setFont(new Font("Brush Script MT", Font.BOLD, 80));
            showLastPrize.setHorizontalAlignment(JTextField.CENTER);
            showLastPrize.setMinimumSize(new Dimension(this.getWidth()/4, height/2));
            showLastPrize.setMaximumSize(new Dimension(this.getWidth()/4, this.getHeight()));
            showLastPrize.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
            showLastPrize.setFocusable(false);
            this.add(showLastPrize);
        }
        
        public void refreshInformation(SlotMachine machine)
        {
            showTokens.setText("" + machine.getTokens());
            showLastPrize.setText("" + machine.getPrize());
            this.repaint();
            this.validate();
        }
    }
    
    public void showResult(ImageIcon image, String message)
    {
        JFrame result = new JFrame("Result");
        result.setSize(500, 650);
        result.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        result.setVisible(true);
        result.setResizable(false);
        result.setLocationRelativeTo(null);
        result.setEnabled(false);
        closeTimer = new Timer(1750, new closeScreen(result));
        closeTimer.start();
        
        backGroundPanel backGround = new backGroundPanel(result, image);
        backGround.setLayout(new BorderLayout());
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";
        JLabel show = new JLabel(html1 + (result.size().width - 150) + html2 + message);
        show.setFont(new Font("Jokerman", Font.ITALIC, 30));
        backGround.add(show, BorderLayout.NORTH);
        result.add(backGround);
        
        result.repaint();
        result.validate();
        result.revalidate();
    }
    
    public class backGroundPanel extends JPanel
    {
        private Image image;
        private int width;
        private int height;
        
        public backGroundPanel(JFrame window, ImageIcon imageName)
        {
            try
            {
                image = imageName.getImage();
                width = window.getWidth() - 5;
                height = window.getHeight() - 35;
                image = image.getScaledInstance(window.getWidth() - 5, window.getHeight() - 35, Image.SCALE_SMOOTH);
            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage());
            }
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
    
    public class closeScreen implements ActionListener
    {
        JFrame screen;
        public closeScreen(JFrame frameToClose)
        {
            this.screen = frameToClose;
        }
        
        public void actionPerformed(ActionEvent e) 
        {
            //screen.dispatchEvent(new WindowEvent(screen, WindowEvent.WINDOW_CLOSING));
            ((Timer)e.getSource()).stop();
            screen.dispose();
        }
    }
    
    public class stopButton implements ActionListener
    {
        private int slot;
        private SlotMachine machine;
        
        public stopButton(int position, SlotMachine temp)
        {
            slot = position;
            machine = temp;
        }
        public void actionPerformed(ActionEvent e) 
        {
            Thread test = new Thread(new spinWheel(slot, machine));
            test.run();
        }
    }
    
    public class spinWheel implements Runnable
    {
        private SlotMachine machine;
        private int slot;
        
        public spinWheel(int slot, SlotMachine machine)
        {
            this.slot = slot;
            this.machine = machine;
        }
        public void run()
        {
            machine.changeSpinStatus(slot);
        }
    }
}
