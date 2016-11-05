import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SlotController extends JComponent
{
//	private JTextField txtTokens, txtPrize;
//	private JButton btnPlay;
//	private JMenu slotMenu;
//	private JMenuItem mitemNewGame;
//        private JComboBox numberList;
	
	private ArrayList<ActionListener> listeners;
        private SkinMenu skins;
        private PlayButton Play;
        private NewGame reset;
	
	public SlotController(int width, int height)
	{
		//this.setLayout(new GridLayout(3, 2));
            listeners = new ArrayList<ActionListener>();
            this.setSize(width, height);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(Play = new PlayButton(this.getWidth(), this.getHeight()/2));
            skins = new SkinMenu(this.getWidth(), this.getHeight()/26);
            this.add(skins);
            this.add(new SlotMenu(this.getWidth(), this.getHeight()/26));
            //this.add(new IconMenu(this.getWidth(), this.getHeight()/26));
            this.add(reset = new NewGame(this.getWidth(), height - this.getHeight()/2 - this.getHeight()/25));
		
//		this.add(new JLabel("Tokens: "));
//		txtTokens = new JTextField();
//		this.add(txtTokens);
//		
//		this.add(new JLabel("Prize: "));
//		txtPrize = new JTextField();
//		this.add(txtPrize);

//		btnPlay = new JButton("Play");
//		btnPlay.addActionListener(new PlayListener());
//                btnPlay.setSize(this.getWidth(), this.getHeight());
//		this.add(btnPlay);
                
//		slotMenu = new JMenu("Save Game");		
//		mitemNewGame = new JMenuItem("New Game");
//		mitemNewGame.addActionListener(new ResetListener());
//		slotMenu.add(mitemNewGame);
                
                //numberList added on 11/5/2014 12:51
//                numberList = new JComboBox();
//                for(int i = 3; i <= 7; i++)
//                {
//                    numberList.addItem(i);
//                }
//                this.add(numberList);
//		
//		this.add(slotMenu);
//		
//		listeners = new ArrayList<ActionListener>();
	}
	
	public void addActionListener(ActionListener a)
	{
                listeners.add(a);
                skins.changeSkinMenuContent();
                changePlayButtonIcon();
                changeNewGameIcon();
                revalidate();
	}
	
	class PlayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
                    Thread Test = new Thread(new rotateSlots());
                    Test.start();
//			System.out.println("Play button clicked...");
//                        for(ActionListener a :listeners)
//                        {
//                            ((SlotMachine)a).runGame();
//                            a.actionPerformed(e);
//                        }    
//                        try
//                        {
//                            ((SlotMachine)listeners.get(0)).actionPerformed(e);
//                        }
//                        catch(Exception E)
//                        {
//                            System.out.println(E.getLocalizedMessage());
//                        }

			//SlotMachine sm = new SlotMachine();
			//ActionEvent newEvent = new ActionEvent(sm, ActionEvent.ACTION_FIRST, "update");
			//for( ActionListener a : listeners ) a.actionPerformed(newEvent);
		}
	}
	
	class ResetListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Reset chosen...");
                        
                        try
                        {
                            for(ActionListener a :listeners)
                            {
                                ((SlotMachine)a).newGame();
                                a.actionPerformed(e);
                            }    
                        }
                        catch(Exception E)
                        {
                            System.out.println(E.getLocalizedMessage());
                        }
			
			//SlotMachine sm = new SlotMachine();
			//ActionEvent newEvent = new ActionEvent(sm, ActionEvent.ACTION_FIRST, "newgame");
			//for( ActionListener a : listeners) a.actionPerformed(newEvent);
		}
	}
        
        class slotListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Resetting amount of slots....");
                try
                {
                    for(ActionListener a: listeners)
                    {
                        String input = (String)((JComboBox)e.getSource()).getSelectedItem();
                        int i = Integer.valueOf(input.substring(0, 1));
                        if(i == 0) i = ((SlotMachine)a).getSlotCount();
                            ((SlotMachine)a).changeSlots(i);
                        a.actionPerformed(e);
                    }
                }
                catch(Exception E)
                {
                    System.out.println(E.getLocalizedMessage());
                }
                 ((JComboBox)e.getSource()).setSelectedIndex(0);
            }
        }
        
        class PlayButton extends JPanel
        {
//            JButton btnPlay = new JButton("Play");
//            btnPlay.addActionListener(new PlayListener());
//            btnPlay.setSize(this.getWidth(), this.getHeight());
//            this.add(btnPlay);
            JButton btnPlay;
            
            public PlayButton(int width, int height)
            {
                this.setOpaque(false);
                this.setSize(width, height);
                //btnPlay = new JButton("Play");
                btnPlay = new JButton("");
                for(ActionListener a: listeners)
                {
                    Image play = ((SlotMachine)a).getSkinPlayImage();
                    play = play.getScaledInstance(btnPlay.getWidth() - 5, btnPlay.getHeight() - 100, Image.SCALE_SMOOTH);
                    btnPlay.setMinimumSize(new Dimension(this.getWidth() - 90, this.getHeight() - 100));
                    btnPlay.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
                    btnPlay.setPreferredSize(new Dimension(this.getWidth() - 5, this.getHeight() - 100));
                    btnPlay.setIcon(new ImageIcon(play));
                }
                //btnPlay.setFont(new Font("Brush Script MT", Font.BOLD, 30));
                btnPlay.setMinimumSize(new Dimension(this.getWidth() - 90, this.getHeight() - 100));
                btnPlay.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
                btnPlay.setPreferredSize(new Dimension(this.getWidth() - 5, this.getHeight() - 100));
                btnPlay.addActionListener(new PlayListener());
                btnPlay.setSize(this.getWidth(), this.getHeight());
                this.add(btnPlay);
            }
            
            public void changePlayIcon()
            {
                for(ActionListener a: listeners)
                {
                    Image play = ((SlotMachine)a).getSkinPlayImage();
                    play = play.getScaledInstance(btnPlay.getWidth(), btnPlay.getHeight(), Image.SCALE_SMOOTH);
                    System.out.println("Dimension is: " + btnPlay.getWidth() + " " + btnPlay.getHeight());
                    btnPlay.setIcon(new ImageIcon(play));
                    ImageIcon press = new ImageIcon(((SlotMachine)a).getSkinPressedPlayImage().getScaledInstance(btnPlay.getWidth(), btnPlay.getHeight(), Image.SCALE_SMOOTH));
                    btnPlay.setPressedIcon(press);
                    revalidate();
                }
            }
        }
        
        public void changePlayButtonIcon()
        {
            Play.changePlayIcon();
        }
        
        class SkinMenu extends JPanel
        {
            private JComboBox numberList;
            private skinButton button;
            
            public SkinMenu(int width, int height)
            {
                this.setOpaque(false);
                this.setSize(width - 5, height);
                numberList = new JComboBox();
                numberList.addActionListener(button = new skinButton());
                numberList.setPreferredSize(this.getSize());
                this.add(numberList);
            }
            
            public void changeSkinMenuContent()
            {
                numberList.removeActionListener(button);
                numberList.removeAllItems();
                ArrayList<String[]> temp = new ArrayList<String[]>();
                int totalSkin = 0;
                numberList.addItem("Skins");
                for(ActionListener a: listeners)
                {
                    temp.add(((SlotMachine)a).getListOfSkinName());
                    totalSkin += ((SlotMachine)a).getListOfSkinName().length;
                }
                String totalSkins[] = new String[totalSkin];
                int count =  0;
                for(String[] b: temp)
                {
                    for(String c: b)
                    {
                        numberList.addItem(c.toString());
                    }
                }
                numberList.addActionListener(button);
                revalidate();
                numberList.setSelectedIndex(0);
            }
        }
        
        class skinButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e) 
            {
                for(ActionListener a: listeners)
                {
                    ((SlotMachine)a).changeSkin((String)((JComboBox)(e.getSource())).getSelectedItem());
                    changePlayIcon();
                    changeNewGameIcon();
                }
                ((JComboBox)e.getSource()).setSelectedIndex(0);
            }
        }
        
        class SlotMenu extends JPanel
        {
            public SlotMenu(int width, int height)
            {
                this.setOpaque(false);
                this.setSize(width - 5, height);
                String list[] = new String[5];
                list[0] = "Slots";
                for(int i = 1; i < 5; i++)
                {
                    list[i] = (i+3) + " Slots";
                }
                JComboBox numberList = new JComboBox(list);
                numberList.addActionListener(new slotListener());
                numberList.setPreferredSize(this.getSize());
                numberList.setSelectedIndex(0);
                this.add(numberList);
            }
            
        }
        
//        class IconMenu extends JPanel
//        {
//            public IconMenu(int width, int height)
//            {
//                this.setOpaque(false);
//                this.setSize(width - 5, height);
//                JComboBox iconList = new JComboBox();
//                iconList.setPreferredSize(this.getSize());
//                this.add(iconList);
//            }
//        }
        
        class NewGame extends JPanel
        {
            private JButton btnNewGame;
            
            public NewGame(int width, int height)
            {
                this.setOpaque(false);
                this.setSize(width, height);
                btnNewGame = new JButton("");
                for(ActionListener a: listeners)
                {
                    Image reset = ((SlotMachine)a).getSkinPlayImage();
                    btnNewGame.setIcon(new ImageIcon(reset));
                }
                //JButton btnNewGame = new JButton("<html><center>New<br>Game</html>");
                //btnNewGame.setFont(new Font("Jokerman", Font.PLAIN, 25));
                btnNewGame.setMinimumSize(new Dimension(this.getWidth() - 90, this.getHeight() - 100));
                btnNewGame.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
                btnNewGame.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() - 100));
                btnNewGame.setSize(this.getWidth(), this.getHeight());
                btnNewGame.addActionListener(new ResetListener());
                this.add(btnNewGame);
            }
            
            public void changeResetIcon()
            {
                for(ActionListener a: listeners)
                {
                    Image play = ((SlotMachine)a).getSkinResetImage();
                    play = play.getScaledInstance(btnNewGame.getWidth(), btnNewGame.getHeight(), Image.SCALE_SMOOTH);
                    btnNewGame.setIcon(new ImageIcon(play));
                    ImageIcon press = new ImageIcon(((SlotMachine)a).getSkinPressedResetImage().getScaledInstance(btnNewGame.getWidth(), btnNewGame.getHeight(), Image.SCALE_SMOOTH));
                    btnNewGame.setPressedIcon(press);
                    revalidate();
                }
            }
        }
        
        public void refreshSkinMenu()
        {
            skins.changeSkinMenuContent();
        }
        
        public void changePlayIcon()
        {
            Play.changePlayIcon();
        }
        
        public void changeNewGameIcon()
        {
            reset.changeResetIcon();
        }
        
        public class rotateSlots implements Runnable
        {
            public void run()
            {
                System.out.println("Play button clicked...");
                        for(ActionListener a :listeners)
                        {
                            ((SlotMachine)a).runGame();
                            a.actionPerformed(null);
                        } 
            }
        }
        
//        class slotButton implements ActionListener
//        {
//            private SlotMachine machine;
//            private int slotCount;
//
//            public slotButton(SlotMachine machine, int count)
//            {
//                this.machine = machine;
//                this.slotCount = count;
//            }
//
//            public void actionPerformed(ActionEvent e) 
//            {
//                machine.changeSlots(slotCount);
//                machine.actionPerformed(e);
//            }
//        }
}
