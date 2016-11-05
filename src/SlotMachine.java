import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SlotMachine extends Thread implements ActionListener
{
	private int tokens;
	private int prize;
        private int slotCount; //Added 11/5/2014 12:33
	private int[] slotValues;
	private int[] symbolFreq;
        private skinPack currentSkin;
        private boolean changed;
        private boolean[] slotsSpinning;
        private Object backGround;
        private File directory;
        private ArrayList<skinPack> skinList;
	private ArrayList<ActionListener> guiListeners;
	
	public SlotMachine()
        {
		tokens = 10;
		prize = 0;
                slotCount = 4;
		slotValues = new int[slotCount];
                for(int i = 0; i < slotCount; i++)
                {
                    slotValues[i] = 0;
                }
                symbolFreq = new int[12];
                skinList = new ArrayList<skinPack>();
                if(readSkinFile(new File("Skins.txt")) == 0)
                {
                    readSkinFile(new File("./src/Skins.txt"));
                }
                currentSkin = skinList.get(0);
                changeSlots(skinList.get(0).getDefaultSkinSlotSize());
                guiListeners = new ArrayList<ActionListener>();
                changed = false;
                
                //Test Code
                slotsSpinning = new boolean[skinList.get(0).getDefaultSkinSlotSize()];
                Arrays.fill(slotsSpinning, true);
	}
	
	public void runGame()
	{
		if(tokens > 0)
                {
			tokens--;
                        Arrays.fill(slotsSpinning, true);
			randomize();
			checkSymbols();
			prize = calculateSymbols();
			tokens += prize;
                        changed = true;
		}
		else
			System.out.println("No Tokens");
	}
	
	public void newGame()
	{
		tokens = 10;
		prize = 0;
                
                //Edited 11/5/2014 12:34
		slotValues = new int[slotCount];
                for(int i = 0 ; i < slotCount; i++)
                {
                    slotValues[i] = 0;
                }
                
                Arrays.fill(slotsSpinning, true);
	}
        
        private void randomize()
	{
            boolean spinning = true;
            while(spinning)
            {
                System.out.println(spinning);
                Random rand = new Random();
                for(int i = 0; i < slotValues.length; i++)
                {
    //                    slotValues[i] = rand.nextInt(currentSkin.iconCount());
    //                    System.out.println("Random Integer Generated: " + slotValues[i]);

                    //Test Code

                    if(slotsSpinning[i])
                    {
                        slotValues[i] = rand.nextInt(currentSkin.iconCount());
                        System.out.println("Random Integer Generated: " + slotValues[i]);
                    }
                }
                spinning = false;
                for(boolean value: slotsSpinning)
                {
                    if(value)
                        spinning = true;
                } 
                actionPerformed(null);
                System.out.println("It gets here?");
                try {
                    Thread.sleep(350);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SlotMachine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}
	
	public int getTokens()
	{
		int temp = this.tokens;
		return temp;
	}
        
        public void changeSlots(int slotCount)
        {
            int[] temp = new int[slotCount];
            if(this.slotCount >= slotCount)
            {
                for(int i = 0; i < slotCount; i++)
                {
                    System.out.println(i);
                    temp[i] = slotValues[i]; 
                }
            }
            else
            {
                Arrays.fill(temp, 0);
                for(int i = 0; i < this.slotCount; i++)
                    temp[i] = slotValues[i];
            }
            slotValues = temp;
            this.slotCount = slotCount;
            slotsSpinning = new boolean[this.slotCount];
            Arrays.fill(slotsSpinning, true);
        }
        
        public int getSlotCount()
        {
            return slotCount;
        }
        
        public int[] getResult()
        {
            return slotValues;
        }
        
        public int getPrize()
        {
            return prize;
        }
	
//	public ArrayList<Graphics2D> getSymbols()
//	{
//		ArrayList<Graphics2D> temp = this.symbols;
//		return temp;
//	}
	
	public int[] getSlotValues()
	{
		int []temp = this.slotValues;
		return temp;
	}
        
        public void addActionListener(ActionListener a)
        {
            guiListeners.add(a);
        }
	
	public void actionPerformed(ActionEvent e)
	{
            System.out.println(this.getSlotCount());
            for(int i = 0; i < slotCount; i++)
            {
                System.out.println(slotValues[i]);
            }
                ActionEvent passOn = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
		for(ActionListener listener : guiListeners)
		{
			listener.actionPerformed(passOn);
		}
                changed = false;
	}
	
	private void checkSymbols()
        {
            symbolFreq = new int[12];
            Arrays.fill(symbolFreq, 0);
            for(int sym: slotValues)
            {
                    symbolFreq[sym]++;
            }
	}
	
	private int calculateSymbols()
	{
            int sum = 0;
            for(int i = 0; i < symbolFreq.length; i++)
            {
                if(symbolFreq[i] > 2)
                {
                        sum += i * symbolFreq[i];
                }
            }
            return sum;
	}
        
        //public int readSkinFile(String fileName)
        public int readSkinFile(File filename)
        {
            int noError = 0;
            //File file = new File(fileName);
            directory = filename;
            try
            {
                Scanner scanner = new Scanner(filename);
                while(scanner.hasNext())
                {
                    skinPack temp = new skinPack(scanner);
                    boolean found = false;
                    for(int i = 0; i < skinList.size(); i++)
                    {
                        if(skinList.get(i).getSkinName().equals(temp.getSkinName()))
                        {
                            found = true;
                        }
                    }
                    if(!found)
                        skinList.add(temp);
                }
                scanner.close();
                noError = 1;
            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage());
            }
            finally
            {
                return noError;
            }
        }
        
        private class skinPack
        {
            private ArrayList<Image> icons;
            private int defaultSkinSlotSize;
            private String skinName;
            private String backGround;
            private String winImage;
            private String winMessage;
            private String loseImage;
            private String loseMessage;
            private Image playImage;
            private Image pressedPlayImage;
            private Image resetImage;
            private Image pressedResetImage;
            
            
            public skinPack(Scanner scanner)
            {
                try
                {
                    icons = new ArrayList<Image>();
                    skinName = scanner.nextLine();
                    defaultSkinSlotSize = Integer.parseInt(scanner.nextLine());
                    changeSlots(defaultSkinSlotSize);
                    slotsSpinning = new boolean[defaultSkinSlotSize];
                    Arrays.fill(slotsSpinning, true);
                    System.out.println(slotCount);
                    backGround = scanner.nextLine();
                    winImage = scanner.nextLine();
                    winMessage = scanner.nextLine();
                    loseImage = scanner.nextLine();
                    loseMessage = scanner.nextLine();
                    playImage = ImageIO.read(this.getClass().getResource(scanner.nextLine()));
                    pressedPlayImage = ImageIO.read(this.getClass().getResource(scanner.nextLine()));
                    resetImage = ImageIO.read(this.getClass().getResource(scanner.nextLine()));
                    pressedResetImage = ImageIO.read(this.getClass().getResource(scanner.nextLine()));
                }
                catch(Exception e)
                {
                    JOptionPane.showConfirmDialog(null, "Warning: Something is wrong with the text file.", "Warning", JOptionPane.PLAIN_MESSAGE);
                }
                
                String temp;
                while(scanner.hasNext() && !(temp = scanner.nextLine()).equals("End"))
                {
                    try
                    {
                        System.out.println(temp);
                        icons.add(ImageIO.read(this.getClass().getResource(temp)));
                        System.out.println("Image has been added successfully");
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getLocalizedMessage());
                        JOptionPane.showConfirmDialog(null, "Warning: Program was not able to add an image", "Warning", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
            public String toString()
            {
                return skinName;
            }
            public int iconCount()
            {
                return icons.size();
            }
            public Image getImage(int i)
            {
                return icons.get(i);
            }
            
            public Image getPlayImage()
            {
                return playImage;
            }
            
            public Image getPressedPlayImage()
            {
                return pressedPlayImage;
            }
            
            public Image getResetImage()
            {
                return resetImage;
            }
            
            public Image getPressedResetImage()
            {
                return pressedResetImage;
            }
            
            public int getDefaultSkinSlotSize()
            {
                return defaultSkinSlotSize;
            }
            
            public String getSkinName()
            {
                return skinName;
            }
        }
        
        public Image getIcon(int index)
        {
            int i;
            for(i = 0; !skinList.get(i).toString().equals(currentSkin.toString()); i++);
            return skinList.get(i).getImage(index);
        }
        
        public void changeSkin(String newSkin)
        {
            for (int i = 0; i < skinList.size(); i++)
            {
                if(skinList.get(i).skinName.equals(newSkin))
                {
                    currentSkin = skinList.get(i);
                }
            }
            for(int i = 0; i < slotCount; i++)
            {
                slotValues[i] = 0;
            }
            try
            {
                for (int i = 0; i < skinList.size(); i++)
                {
                    if(skinList.get(i).skinName.equals(newSkin))
                    {
                        changeSlots(skinList.get(i).getDefaultSkinSlotSize());
                    }
                }
                ((Play.backGroundPanel)backGround).changeBackGround(currentSkin.backGround);
                slotsSpinning = new boolean[slotCount];
                Arrays.fill(slotsSpinning, true);
            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage());
            }
            actionPerformed(null);
        }
        
        public void addSkinToJMenu(JMenu menu)
        {
            for(int i = 0; i < skinList.size(); i++)
            {
                JMenuItem temp = new JMenuItem(skinList.get(i).skinName);
                temp.addActionListener(new skinButton(this, skinList.get(i).skinName));
                menu.add(temp);
            }
        }
        
//        public ArrayList<String> getListOfSkinName()
//        {
//            ArrayList<String> temp = new ArrayList<String>();
//            for(int i = 0; i < skinList.size(); i++)
//            {
//                temp.add(skinList.get(i).skinName);
//            }
//            return temp;
//        }
        
        public class skinButton implements ActionListener
        {
            private String newSkin;
            private SlotMachine machine;
            
            public skinButton(SlotMachine machine, String changeSkin)
            {
                newSkin = changeSkin;
                this.machine = machine;
            }

            public void actionPerformed(ActionEvent e) 
            {
                changeSkin(newSkin);
                machine.actionPerformed(e);
            }
        }
        
        public boolean getChangedStatus()
        {
            return changed;
        }
        
        public String getBackground()
        {
            return currentSkin.backGround;
        }
        
        public String getWinImage()
        {
            return currentSkin.winImage;
        }
        
        public String getLoseImage()
        {
            return currentSkin.loseImage;
        }
        
        public void storeBackGround(Object backGround)
        {
            this.backGround = backGround;
        }
        
        public String[] getListOfSkinName()
        {
            String list[] = new String[skinList.size()];
            for(int i = 0; i < skinList.size(); i++)
            {
                list[i] = skinList.get(i).toString();
            }
            return list;
        }
        
        public void chooseFileToLoad()
        {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            chooser.setCurrentDirectory(new File(this.getClass().getResource("").getPath()));
            chooser.setDialogTitle("Open Skins");
            chooser.setCurrentDirectory(directory);
            chooser.setFileFilter(filter);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = chooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                int currentCount = this.getListOfSkinName().length;
                File select = chooser.getSelectedFile();
                readSkinFile(select);
                try
                {
                    Scanner scanner = new Scanner(select);
                    //String firstSkinInFile = scanner.next();
                    //currentSkin = skinList.get(skinList.size() - 1);
                    changeSkin(scanner.nextLine());
                }
                catch(Exception e)
                {
                    System.out.println(e.getLocalizedMessage());
                }
                actionPerformed(null);
                System.out.println(currentSkin.toString());
            }
        }
        
        public String getWinMessage()
        {
            return currentSkin.winMessage;
        }
        
        public String getLoseMessage()
        {
            return currentSkin.loseMessage;
        }
        
        public String currentSkinName()
        {
            return currentSkin.toString();
        }
        
        public Image getSkinPlayImage()
        {
            Image temp = currentSkin.getPlayImage();
            return temp;
        }
        
        public Image getSkinPressedPlayImage()
        {
            Image temp = currentSkin.getPressedPlayImage();
            return temp;
        }
        
        public Image getSkinResetImage()
        {
            Image temp = currentSkin.getResetImage();
            return temp;
        }
        
        public Image getSkinPressedResetImage()
        {
            Image temp = currentSkin.getPressedResetImage();
            return temp;
        }
        
        //Test Code
        
        public void changeSpinStatus(int slots)
        {
            slotsSpinning[slots] = false;
        }
        
        
        //Added 11/5/2014 12:33
//        public void changeSlots(int slotCount)
//        {
//            this.slotCount = slotCount;
//        }
//        
//        public int getSlotCount()
//        {
//            return slotCount;
//        }
//        
//        public int[] getResult()
//        {
//            return slotValues;
//        }
//        
//        public int getPrize()
//        {
//            return prize;
//        }
}