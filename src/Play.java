import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Play extends JComponent
{
    public static void main(String[] args)
    {
        try
        {
            JFrame myWindow = new JFrame("Slot Machine");
            myWindow.setSize(900, 600);
            myWindow.setLayout(new BoxLayout(myWindow.getContentPane(), BoxLayout.X_AXIS));
            //myWindow.setLayout(new BorderLayout());
            myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myWindow.setVisible(true);
            myWindow.setResizable(false);
            myWindow.setLocationRelativeTo(null);
            myWindow.setState(1);

            SlotMachine machine = new SlotMachine();

            backGroundPanel backGround = new backGroundPanel(myWindow, machine.getBackground());
            myWindow.add(backGround);
            machine.storeBackGround(backGround);

            JMenuBar menuBar = new JMenuBar();
            JMenu main = new JMenu("Menu");

            JMenuItem newGame = new JMenuItem("New Game");
            newGameButton button = new newGameButton(machine);
            newGame.addActionListener(button);
            //JMenuItem save = new JMenuItem("Save Game");
            //JMenuItem load = new JMenuItem("Load Game");
            main.add(newGame);
            //main.add(load);
            //main.add(save);

            JMenu slotAmount = new JMenu("Change Amount of Slot");
            JMenuItem four = new JMenuItem("4");
            slotButton fourButton = new slotButton(machine, Integer.parseInt(four.getText()));
            four.addActionListener(fourButton);
            JMenuItem five = new JMenuItem("5");
            slotButton fiveButton = new slotButton(machine, Integer.parseInt(five.getText()));
            five.addActionListener(fiveButton);
            JMenuItem six = new JMenuItem("6");
            slotButton sixButton = new slotButton(machine, Integer.parseInt(six.getText()));
            six.addActionListener(sixButton);
            JMenuItem seven = new JMenuItem("7");
            slotButton sevenButton = new slotButton(machine, Integer.parseInt(seven.getText()));
            seven.addActionListener(sevenButton);
            slotAmount.add(four);
            slotAmount.add(five);
            slotAmount.add(six);
            slotAmount.add(seven);
            main.add(slotAmount);

            JMenu skin = new JMenu("Change Into Registered Skins");
            //machine.addSkinToJMenu(skin);
//            for(String a: machine.getListOfSkinName())
//            {
//                JMenuItem temp = new JMenuItem(a);
//                temp.addActionListener(new skinButton(machine, a));
//                skin.add(temp);
//            }
            main.add(skin);

            JMenuItem fileChooser = new JMenuItem("Use New Skin");
            //fileChooser.addActionListener(new fileChooserButton(myWindow, machine));
            main.add(fileChooser);


            JMenuItem quit = new JMenuItem("Quit Game");
            exitGameButton exit = new exitGameButton(myWindow);
            quit.addActionListener(exit);
            main.add(quit);

            main.setVisible(true);
            menuBar.add(main);

            myWindow.setJMenuBar(menuBar);

            SlotDisplay slots = new SlotDisplay(machine, 900, 600);
            slots.setOpaque(false);
            machine.addActionListener(slots);
            //myWindow.add(slots);
            backGround.add(slots);

            SlotController control = new SlotController(100, 600);
            control.addActionListener(machine);
            control.setSize(slots.getSize());
            control.setOpaque(false);
            //myWindow.add(control);
            backGround.add(control);
            for(String a: machine.getListOfSkinName())
            {
                JMenuItem temp = new JMenuItem(a);
                temp.addActionListener(new skinButton(machine, a, control));
                skin.add(temp);
            }
            
            fileChooser.addActionListener(new fileChooserButton(myWindow, machine, control, skin));
            
            myWindow.repaint();
            myWindow.validate();
            myWindow.revalidate();
            
            control.changePlayButtonIcon();
            control.changeNewGameIcon();
            
            myWindow.setState(0);
        }
        catch(Exception e)
        {
            JOptionPane.showConfirmDialog(null, "Warning: Something went wrong with the program.\n", "Warning", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    static class slotButton implements ActionListener
    {
        private SlotMachine machine;
        private int slotCount;
        
        public slotButton(SlotMachine machine, int count)
        {
            this.machine = machine;
            this.slotCount = count;
        }
        
        public void actionPerformed(ActionEvent e) 
        {
            machine.changeSlots(slotCount);
            machine.actionPerformed(e);
        }
    }
    
    static class newGameButton implements ActionListener
    {
        private SlotMachine machine;
        
        public newGameButton(SlotMachine machine)
        {
            this.machine = machine;
        }
        public void actionPerformed(ActionEvent e) 
        {
            machine.newGame();
            machine.actionPerformed(e);
        }
    }
    
    static class exitGameButton implements ActionListener
    {
        private JFrame window; 
        public exitGameButton(JFrame myWindow)
        {
            window = myWindow;
        }
        public void actionPerformed(ActionEvent e) 
        {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    static class backGroundPanel extends JPanel
    {
        private Image image;
        private int width;
        private int height;
        
        public backGroundPanel(JFrame window, String imageName)
        {
            try
            {
                image = ImageIO.read(this.getClass().getResource(imageName));
                this.image = this.image.getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_DEFAULT);
                this.width = window.getWidth();
                this.height = window.getHeight();
                this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
        
        public void changeBackGround(String imageName)
        {
            try
            {
                this.image = ImageIO.read(this.getClass().getResource(imageName));
                this.image = this.image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage());
            }
            repaint();
            validate();
            revalidate();
            System.out.println("Image has been changed.");
        }
    }
    static class fileChooserButton extends Component implements ActionListener
    {
        private SlotMachine machine;
        private JFileChooser chooser;
        private JFrame frame;
        private SlotController control;
        private JMenu skin;
        
        public fileChooserButton(JFrame myWindow, SlotMachine machine, SlotController control, JMenu skin)
        {
            this.frame = myWindow;
            this.machine = machine;
            this.control = control;
            this.skin = skin;
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            chooser.addChoosableFileFilter(filter);
        }
        public void actionPerformed(ActionEvent e) 
        {
            int beforeSize = machine.getListOfSkinName().length;
            machine.chooseFileToLoad();
            control.changePlayButtonIcon();
            control.changeNewGameIcon();
            control.refreshSkinMenu();
            if(beforeSize != machine.getListOfSkinName().length)
            {
                skin.removeAll();
                for(String a: machine.getListOfSkinName())
                {
                    JMenuItem temp = new JMenuItem(a);
                    temp.addActionListener(new skinButton(machine, a, control));
                    skin.add(temp);
                }
            }
        }
    }
    
    static class skinButton implements ActionListener
    {
        private String newSkin;
        private SlotMachine machine;
        private SlotController control;

        public skinButton(SlotMachine machine, String changeSkin, SlotController control)
        {
            newSkin = changeSkin;
            this.machine = machine;
            this.control = control;
        }

        public void actionPerformed(ActionEvent e) 
        {
            machine.changeSkin(newSkin);
            machine.actionPerformed(e);
            control.changePlayIcon();
            control.changeNewGameIcon();
        }
    }
}
