import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;



public class model implements ActionListener
{
	int tokens;
	int prize;
	int numSymbs, numReels, numTokens; //initial values loaded from file
	int []reels;
	int []symbolFreq;
	String theme;
	ArrayList<ImageIcon> symbols;
	ArrayList<ActionListener> guiListeners;
	
	public model(){
		// Loads Game & Theme Settings
		theme = "default";
		try{loadSkin();}catch(IOException e){System.out.println("File Not Found");}
		reels = new int[numReels];
		symbolFreq = new int[numSymbs];//after loadTheme
		symbols = new ArrayList<ImageIcon>();
		loadImages();
		
		// Creates Game
		newGame();
		guiListeners = new ArrayList<ActionListener>();
	}
	
	public void runGame()
	{
		if(tokens > 0){
			tokens--;
			randomize();
			checkSymbols();
			prize = calculateSymbols();
			tokens += prize;
			System.out.println("Tokens: " + tokens + "\nPrize: " + prize);
		}
		else
			System.out.println("No Tokens");
	}
	
	//***************************************
	// Data Access
	//***************************************
	public String getTokens()
	{
		int temp = this.tokens;
		return "" + temp;
	}
	
	public String getPrize()
	{
		int temp = this.prize;
		return "" + temp;
	}
	
	public ArrayList<ImageIcon> getSymbols()
	{
		ArrayList<ImageIcon> temp = this.symbols;
		return temp;
	}
	
	public int[] getReelValues()
	{
		int []temp = this.reels;
		return temp;
	}
	
	//***************************************
	// Observer ActionListener Commands
	//***************************************
	public void addActionListener(ActionListener a)
	{
		guiListeners.add(a);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if(command.compareTo("play") > 0)
			runGame();
		else if(command.compareTo("reset") > 0)
			newGame();
		else if(command.compareTo("newskin") > 0)
			//new skin
		
		for(ActionListener listener : this.guiListeners)
		{
			listener.actionPerformed(e);
		}
	}
	
	//***************************************
	// Play Game Steps
	//***************************************
	
	private void randomize()
	{
		Random rand = new Random();
		
		for(int i = 0; i < reels.length; i++)
		{
			reels[i] = rand.nextInt(numSymbs);
		}
	}
	
	private void checkSymbols()
        {
		Arrays.fill(symbolFreq, 0);
		
		for(int sym: reels)
                {
			symbolFreq[sym]++;
		}
	}
	
	private int calculateSymbols()
	{
		int ind = 0;
		int sum = 0;
		
		for(int val: symbolFreq)
		{
			if(val > 2){
				sum += val * ind;
				System.out.println("Freq: " + val + "Sum: " + sum);
			}
			ind++;
		}
		
		return sum;
	}

	//***************************************
	// Game Creation / Skin Change
	//***************************************
	public void newGame()
	{
		// sets token values to '0'
		tokens = numTokens;
		prize = 0;
		
		// sets result frequency array to '0's
		Arrays.fill(symbolFreq, 0);
		
		// resets all reels to default '0'-valued symbol
		Arrays.fill(reels, 0);
	}
	
	private void loadSkin() throws IOException
	{
		String currToken;
		
		try 
		{
			Scanner scanner = new Scanner(new File("./src/" + theme + ".txt"));
			
			while(scanner.hasNext())
			{
				currToken = scanner.next();
				if(currToken.equals("reels:"))
				{
					numReels = scanner.nextInt();
				}
				else if(currToken.equals("symbols:"))
				{
					numSymbs = scanner.nextInt();
				}
				else if(currToken.equals("tokens:"))
				{
					numTokens = scanner.nextInt();
				}
			}
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("NOT FOUND");
		}
	}

	private void loadImages() //throws IOException
	{
		for(int i = 0; i < numSymbs; i++)
		{
			symbols.add(new ImageIcon("./src/" + theme + "/" + theme + i + ".png"));
		}
	}

}