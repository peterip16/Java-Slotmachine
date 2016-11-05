import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class Main {
	JFrame myWindow;
	private model mod;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.mod = new model();
		main.initializeGame();
	}
	
	public void initializeGame()
	{
		int i, initWidth, initHeight;
		int btnBoxWidth, btnBoxHeight;
		int cntrBoxWidth, cntrBoxHeight;
		int reelBoxWidth, reelBoxHeight;
		int tknBoxWidth, tknBoxHeight;
		
		JPanel pnlButtons;
		JPanel pnlCenter;
		JPanel pnlReelsRow1, pnlReelsRow2;
		JPanel pnlTokens;
		
		int []reelResults;
		ArrayList<JPanel> pnlReels;
		ArrayList<ImageIcon> symbols;
		
		JButton btnPlay;
		JButton btnNewGame;
		JButton btnSkin;
		
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem miNewGame;
		JMenuItem miSkin;
		JMenuItem miQuit;
		
		JLabel lblImage; //used to place images on reel panels
		JLabel lblTokens;
		JLabel lblPrize;
		
		JTextField tfTokens;
		JTextField tfPrize;
		
		//*****************
		// Create JFrame
		//*****************
		initWidth = 800;
		initHeight = 600;
		
		myWindow = new JFrame("MODEL TEST");
		myWindow.setSize(initWidth, initHeight);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setLayout(new BorderLayout());
		myWindow.setMinimumSize(new Dimension(initWidth/2, initHeight/2));
		myWindow.setResizable(false);
		
		btnBoxHeight = myWindow.getHeight() - myWindow.getHeight()/8;
		btnBoxWidth = myWindow.getWidth()/8;
		
		cntrBoxHeight = myWindow.getHeight() - myWindow.getHeight()/8;
		cntrBoxWidth = myWindow.getWidth() - myWindow.getWidth()/8;
		
		reelBoxHeight = (cntrBoxHeight - cntrBoxHeight/8) / 2;
		reelBoxWidth = cntrBoxWidth;
		
		tknBoxHeight = cntrBoxHeight/8;
		tknBoxWidth = cntrBoxWidth;
		
		//*****************
		// Creates and Sets Panels
		//*****************
		// Vertical BoxLayout for Buttons
		pnlButtons = new JPanel();
		BoxLayout bxButtons = new BoxLayout(pnlButtons, BoxLayout.Y_AXIS);
		pnlButtons.setLayout(bxButtons);
		//pnlButtons.setBackground(Color.black);
		pnlButtons.setBorder(new EmptyBorder(5, 0, 5, 5));
		myWindow.add(pnlButtons, BorderLayout.EAST);
		
		// Vertical BoxLayout for Center Panel
		//   contains 3 FlowLayouts
		//    -reels row1, reels row2, tokens
		pnlCenter = new JPanel();
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		//pnlCenter.setBackground(Color.black);
		BoxLayout bxCenter = new BoxLayout(pnlCenter, BoxLayout.Y_AXIS);
		pnlCenter.setSize(cntrBoxWidth, cntrBoxHeight);
		pnlCenter.setLayout(bxCenter);
		myWindow.add(pnlCenter, BorderLayout.CENTER);
		
		// FlowLayout for Reels
		Dimension dimReelPanel = new Dimension(reelBoxWidth, reelBoxHeight/2);
		pnlReelsRow1 = new JPanel();
		pnlReelsRow1.setBorder(new LineBorder(Color.black));
		pnlReelsRow1.setPreferredSize(dimReelPanel);
		FlowLayout flowReels1 = new FlowLayout();
		pnlReelsRow1.setLayout(flowReels1);
		pnlCenter.add(pnlReelsRow1);
		
		// FlowLayout for Reels
		pnlReelsRow2 = new JPanel();
		pnlReelsRow2.setBorder(new LineBorder(Color.black));
		pnlReelsRow2.setPreferredSize(dimReelPanel);
		FlowLayout flowReels2 = new FlowLayout();
		pnlReelsRow2.setLayout(flowReels2);
		pnlCenter.add(pnlReelsRow2);
		
		pnlCenter.add(Box.createVerticalGlue());
		
		// FlowLayout for Tokens
		pnlTokens = new JPanel();
		pnlTokens.setBorder(new LineBorder(Color.black));
		pnlTokens.setMaximumSize(new Dimension(tknBoxWidth, tknBoxHeight/8));
		FlowLayout flowTkns = new FlowLayout(FlowLayout.LEFT, 30, 10);
		pnlTokens.setLayout(flowTkns);
		pnlCenter.add(pnlTokens);
		
		//*****************
		// Creates and sets buttons
		//*****************
		Dimension dimBtn = new Dimension(btnBoxWidth, btnBoxHeight/10);
		Image resizedHandle = new ImageIcon("./src/PlayHandleIcon.png").getImage().getScaledInstance(btnBoxWidth - 1, btnBoxHeight/2 - 1, Image.SCALE_SMOOTH);
		Image rszHandleHover = new ImageIcon("./src/PlayHandleIconHover.png").getImage().getScaledInstance(btnBoxWidth - 1, btnBoxHeight/2 - 1, Image.SCALE_SMOOTH);
		
		btnPlay = new JButton(new ImageIcon(resizedHandle));
		btnPlay.setRolloverIcon(new ImageIcon(rszHandleHover));
		btnPlay.setMinimumSize(new Dimension(btnBoxWidth, btnBoxHeight/2));
		btnPlay.setMaximumSize(new Dimension(btnBoxWidth, btnBoxHeight/2));
		btnPlay.setPreferredSize(new Dimension(btnBoxWidth, btnBoxHeight/2));
		btnPlay.addActionListener(new PlayListener());
		pnlButtons.add(btnPlay);
		
		pnlButtons.add(Box.createVerticalGlue());
		
		Image resizedNG = new ImageIcon("./src/NewGameIcon.png").getImage().getScaledInstance(btnBoxWidth - 1, btnBoxHeight/10 - 1, Image.SCALE_SMOOTH);
		Image rszNGHover = new ImageIcon("./src/NewGameIconHover.png").getImage().getScaledInstance(btnBoxWidth - 1, btnBoxHeight/10 - 1, Image.SCALE_SMOOTH);
		
		btnNewGame = new JButton(new ImageIcon(resizedNG));
		btnNewGame.setRolloverIcon(new ImageIcon(rszNGHover));
		btnNewGame.setMinimumSize(dimBtn);
		btnNewGame.setMaximumSize(dimBtn);
		btnNewGame.setPreferredSize(dimBtn);
		btnNewGame.addActionListener(new NewGameListener());
		pnlButtons.add(btnNewGame);
		
		//pnlButtons.add(Box.createVerticalGlue());
		
		Image resizedCS = new ImageIcon("./src/ChangeSkinIcon.png").getImage().getScaledInstance(btnBoxWidth - 1, btnBoxHeight/10 - 1, Image.SCALE_SMOOTH);
		Image rszCSHover = new ImageIcon("./src/ChangeSkinIconHover.png").getImage().getScaledInstance(btnBoxWidth - 1, btnBoxHeight/10 - 1, Image.SCALE_SMOOTH);
		
		btnSkin = new JButton(new ImageIcon(resizedCS));
		btnSkin.setRolloverIcon(new ImageIcon(rszCSHover));
		btnSkin.setMinimumSize(dimBtn);
		btnSkin.setMaximumSize(dimBtn);
		btnSkin.setPreferredSize(dimBtn);
		btnSkin.addActionListener(new SkinListener());
		pnlButtons.add(btnSkin);
		
		//*****************
		// Creates and sets Menu
		//*****************
		menuBar = new JMenuBar();
		myWindow.add(menuBar, BorderLayout.NORTH);
		
		menu = new JMenu("Options");
		menuBar.add(menu);
		
		miNewGame = new JMenuItem("New Game");
		miNewGame.addActionListener(new NewGameListener());
		menu.add(miNewGame);
		
		miSkin = new JMenuItem("Change Skin");
		miSkin.addActionListener(new SkinListener());
		menu.add(miSkin);
		
		miQuit = new JMenuItem("Quit Game");
		miQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				myWindow.setVisible(false);
				myWindow.dispose();
				System.exit(0);
			}
		});
		menu.add(miQuit);
		
		//*****************
		// Creates and sets labels/textfields
		//*****************
		lblTokens = new JLabel("Tokens:");
		pnlTokens.add(lblTokens);
		tfTokens = new JTextField();
		tfTokens.setEditable(false);
		pnlTokens.add(tfTokens);
		
		lblPrize = new JLabel("Prize:");
		pnlTokens.add(lblPrize);
		tfPrize = new JTextField();
		tfPrize.setEditable(false);
		pnlTokens.add(tfPrize);
		
		//*****************
		// Creates and Sets Reels
		//*****************
		Dimension dimReel = new Dimension(reelBoxWidth/4, reelBoxHeight);
		JLabel lblTemp;
		JPanel pnlTemp;
		reelResults = mod.getReelValues();
		symbols = mod.getSymbols();
		pnlReels = new ArrayList<JPanel>();
		
		
		for(int rl : reelResults)
		{
			lblTemp = new JLabel(symbols.get(rl));
			pnlTemp = new JPanel();
			pnlTemp.setMinimumSize(dimReel);
			pnlTemp.setMaximumSize(dimReel);
			pnlTemp.setPreferredSize(dimReel);
			pnlTemp.add(lblTemp);
			pnlTemp.setBorder(new LineBorder(Color.red));
			pnlReels.add(pnlTemp);
		}
		
		i = 0;
		for(JPanel pnlRl : pnlReels)
		{			
			if(i++ < 4)
				pnlReelsRow1.add(pnlRl);
			else
				pnlReelsRow2.add(pnlRl);
		}
		
		myWindow.setVisible(true);
	}
	
	public class PlayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("PLAY executed");
			mod.runGame();
			
		}
	}
	
	public class NewGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Restarting Game");
			mod.newGame();
		}
	}
	
	public class SkinListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Chose to Change Skin!");
			JOptionPane.showMessageDialog(myWindow, "Skin");
		}
	}
}
