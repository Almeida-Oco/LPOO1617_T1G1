package dkeep.gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JTextField;
import dkeep.cli.UserInput;
import dkeep.logic.GameLogic;
import dkeep.logic.GenericMap;
import dkeep.logic.Pair;
import dkeep.logic.GameCharacter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GameWindow {
	
	private static JFrame frame;
	private static JFrame frame2;
	private static JFrame frame3;	
	private static PrettyPanel imgs_panel;
	private JButton btnMapCreation,new_game;
	private Initial_Backgorund init_back;
	private JButton load_game;
	protected static GameLogic game;
	private static int ogres;
	private static int guard;
	private static CreateMap create_panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public GameWindow() {
		initialize();
	}
	
	public static void proccessKey(char ch){
		boolean gm_over = game.isGameOver() , gm_won = game.wonGame(), changed_lvl = false;
		
		
		if (ch != '\n' && !gm_over && !gm_won){	
			if( ( changed_lvl =game.moveHero(ch) ) && !(gm_won = game.wonGame()) )
				game = game.getNextLevel(ogres);
			if ( !(gm_over = game.isGameOver() ) )
				game.moveAllVillains();
			gm_won=game.wonGame();
			 gm_over=game.isGameOver();
			if (gm_won ||gm_over ){
				imgs_panel.gameOver(gm_over);
				imgs_panel.gameWon(gm_won);
			}	
			
			imgs_panel.updateCurrentMap( UserInput.getPrintableMap(game.getMap().getMap() , game.getAllCharacters() , false , false));
			frame3.repaint();
		}
		
	}
	
	private char translateKey(KeyEvent e){
		return ( ( (e.getKeyCode() == KeyEvent.VK_W) || (e.getKeyCode() == KeyEvent.VK_UP) ) ? 'w' : 
			   ( ( (e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_LEFT) ) ? 'a' : 
			   ( ( (e.getKeyCode() == KeyEvent.VK_S) || (e.getKeyCode() == KeyEvent.VK_DOWN) ) ? 's' : 
			   ( ( (e.getKeyCode() == KeyEvent.VK_D) || (e.getKeyCode() == KeyEvent.VK_RIGHT) ) ? 'd' : '\n'))));
	}

	public void chooseGuard(){
		Object[] possibilities = {"Novice", "Drunk", "Suspicious"};
		String s = (String)JOptionPane.showInputDialog( frame, "Choose the type of Guard: \n",
				"Game Options",JOptionPane.PLAIN_MESSAGE,null,possibilities,"Guard");
		
		if(s=="Novice")
			guard=0;
		else if(s== "Drunk")
			guard=1;
		else if(s=="Suspicious")
			guard=2;
	}

	public void chooseOgre(){
		JTextField field1 = new JTextField();
		Boolean exit=false;
		Object[] message = { "Number of Ogres (1-5):", field1, };
		while(!exit){
			exit=true;
			JOptionPane.showConfirmDialog(frame, message, "Ogre Options", JOptionPane.PLAIN_MESSAGE);
			String value1 = field1.getText();
			try{ 
				ogres=Integer.parseInt(value1);
			}
			catch (NumberFormatException n){
				exit=false;
				if(value1.length() == 0)
					ogres = 0;
				else
					JOptionPane.showMessageDialog(frame, "It's supposed to be 1-5 ogres");
			}
			if(ogres<0 ||ogres>5){
				JOptionPane.showMessageDialog(frame, "It's supposed to be 1-5 ogres");
				exit=false;
			}
		}
	}
	
	public void createNewGame(){
		frame3.requestFocus();
		frame.setVisible(false);
		frame2.setVisible(false);
		frame3.getContentPane().setLayout(new BorderLayout());
		imgs_panel = new PrettyPanel( UserInput.getPrintableMap( game.getMap().getMap() , game.getAllCharacters(), false , false) );
		initializeImgPanelListeners();
		frame3.setVisible(true);
		frame3.setBounds(100, 100 ,PrettyPanel.DEFAULT_IMG_SIZE*imgs_panel.getMapWidth() , PrettyPanel.DEFAULT_IMG_SIZE*imgs_panel.getMapHeight());
		frame3.getContentPane().add(imgs_panel,BorderLayout.CENTER);
		PlayButtons pb= new PlayButtons();
		frame3.getContentPane().add(pb,BorderLayout.SOUTH);
		frame3.revalidate();
		frame3.repaint();
		imgs_panel.requestFocus();
	}

	public void newGame(){
		chooseGuard();
		chooseOgre();
		game = new GameLogic(null,guard+1);

		if(ogres != 0)
		createNewGame();
	}
	
	public void proccessButton(char pressed){
		proccessKey(pressed);
	}
	
	
	private void initializeImgPanelListeners(){
		imgs_panel.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				proccessKey(translateKey(e));
			}
		});
	}
	
	
	public static void focus(){
		imgs_panel.requestFocus();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame2 = new JFrame();
		frame3 = new JFrame();
		this.initializeFrame();
		this.initializeNewGameButton();
		this.initializeCreateMapButton();
		this.initializeLoadGameButton();
		this.initializeInitBack();
		frame.repaint();
	}

	private void initializeFrame(){
		frame.setBounds(100, 100, 755, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				proccessKey(translateKey(e));
			
			}
		});
		frame.getContentPane().setLayout(new BorderLayout());
	}

	private void initializeNewGameButton(){
		new_game = new JButton("New Game");
		new_game.setBounds(245, 484, 142, 46);
		new_game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				newGame();
			}
		});
	}
	
	private void initializeLoadGameButton(){
		load_game= new JButton("Load Game");
		load_game.setBounds(245, 484, 142, 46);
		load_game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				game=load();
				if (game!=null)
				createNewGame();
			}
		});
	}
	
	private void initializeCreateMapButton(){
		btnMapCreation = new JButton("Map Creation");
		btnMapCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();
				Object[] message = {
						"Choose the grid height:", field1,
						"Choose the grid width:", field2,
				};

			int option =JOptionPane.showConfirmDialog(frame, message, "Choose the dimensions", JOptionPane.OK_CANCEL_OPTION);
			if (option != JOptionPane.OK_OPTION)
				return;
				    String value1 = field1.getText();
				    String value2 = field2.getText();
				    int height=0;
				    int width=0;
				    try{ 
						height=Integer.parseInt(value1);
					}
					catch (NumberFormatException n){
							JOptionPane.showMessageDialog(frame, "It's supoesed to be a number");
							return;
					}
				    try{ 
						width=Integer.parseInt(value2);
					}
					catch (NumberFormatException n){
							JOptionPane.showMessageDialog(frame, "It's supoesed to be a number");
							return;
						
						}
				    frame.setVisible(false);
				    frame2 = new JFrame("Build your map");
					frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame2.setPreferredSize(new Dimension(width*50+width/2,height*50+65)); 
					frame2.getContentPane().setLayout(new BorderLayout());
					JPanel panel2 = new CreateOptions();
					CreateMap panel = new CreateMap(height,width);
					frame2.getContentPane().add(panel , BorderLayout.CENTER);
					frame2.getContentPane().add(panel2, BorderLayout.SOUTH);
					frame2.pack();
					frame2.setResizable(false);
					frame2.setLocationRelativeTo(null);
					frame2.setVisible(true);
					frame2.setFocusable(true);
					frame2.requestFocus();
					create_panel=panel;
					}

		});
		btnMapCreation.setBounds(66, 501, 142, 29);
	}

	private void initializeInitBack(){
		init_back = new Initial_Backgorund();
		init_back.setBounds(36, 54, 524, 328);
		init_back.add(new_game);
		init_back.add(load_game);
		init_back.add(btnMapCreation);
		JButton exit_b = new JButton("Exit");
		init_back.add(exit_b);
		exit_b.setBounds(596, 452, 69, 25);
		exit_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(init_back);
	}
	
	public static void initializeCreatedMap(){
		frame2.setVisible(false);
		GenericMap gm=new GenericMap(create_panel.getMap());
		ArrayList <GameCharacter> characters=create_panel.getOgres();
		characters.add(create_panel.getHero());
		gm.setCharacters(characters);
		gm.setKey(create_panel.getKey(), false);
		ArrayList<Pair<Pair<Integer,Integer> , String > > doors = new ArrayList<Pair<Pair<Integer,Integer> , String> >();
		doors.add(create_panel.getDoor());
		gm.setDoors(doors);
		game = new GameLogic(gm,0);
		GameWindow n= new GameWindow();
		n.createNewGame();
	}

	

	public static void save(){
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(System.getProperty("user.dir")+"/save.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(game);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in "+System.getProperty("user.dir")+"/save.ser");
	      }catch(IOException i) {
	    	  System.out.println("ERROR! "+i.getMessage());
	    	  System.out.println("CAUSE : "+i.getCause());
	      }
	}
	
	private GameLogic load(){
		GameLogic e;
		try {
	         FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir")+"/save.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         e = (GameLogic) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         JOptionPane.showMessageDialog(frame, "There is no save yet");
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         return null;
	      }
		return e;
	}

	public static void backtoMenu() {
		frame2.setVisible(false);
		frame3.getContentPane().removeAll();
		frame3.setVisible(false);
		frame.setVisible(true);
	}

	public static void showError() {
		JOptionPane.showMessageDialog(frame2, "It must be at least a hero, a key and a exit");
	}

}