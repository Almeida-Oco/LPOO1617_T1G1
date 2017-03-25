package dkeep.gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.TextArea;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import dkeep.cli.UserInput;
import dkeep.logic.GameLogic;
import dkeep.logic.GameCharacter;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow {
	
	private JFrame frame;
	private PrettyPanel imgs_panel;
	private JButton up_b,down_b,left_b,right_b;
	private UserInput input;
	private Container temp;
	protected GameLogic game;
	private int ogres;
	private int guard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					//PrettyWindow window2 = new PrettyWindow();
					window.frame.setVisible(true);
					//window.frame2.setVisible(true);
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
	
	public void proccessKey(char ch){
		if (ch != '\n' && !game.isGameOver() && !this.game.wonGame()){	
			boolean changed_lvl = this.game.moveHero(ch);
			if(changed_lvl && !this.game.wonGame())
				this.game = this.game.getNextLevel(this.ogres);
			if (!this.game.isGameOver())
				this.game.moveAllVillains();
			if (game.wonGame() || game.isGameOver()){
				disableButtons();
				this.imgs_panel.gameOver(game.isGameOver());
			}	
			this.imgs_panel.updateCurrentMap( this.input.getPrintableMap(this.game.getMap().getMap() , this.game.getAllCharacters() , false , false));
			this.frame.repaint();
		}
	}
	
	private char translateKey(KeyEvent e){
		return 	   ( ( (e.getKeyCode() == KeyEvent.VK_W) || (e.getKeyCode() == KeyEvent.VK_UP) ) ? 'w' : 
			( ( (e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_LEFT) ) ? 'a' : 
				( ( (e.getKeyCode() == KeyEvent.VK_S) || (e.getKeyCode() == KeyEvent.VK_DOWN) ) ? 's' : 
					( ( (e.getKeyCode() == KeyEvent.VK_D) || (e.getKeyCode() == KeyEvent.VK_RIGHT) ) ? 'd' : '\n'))));
	}

	public void disableButtons(){
		this.down_b.setEnabled(false);
		this.up_b.setEnabled(false);
		this.left_b.setEnabled(false);
		this.right_b.setEnabled(false);
	}

	public void chooseGuard(){

		Object[] possibilities = {"Novice", "Drunk", "Suspicious"};
		String s = (String)JOptionPane.showInputDialog(
				frame,
				"Choose the type of Guard: \n",
				"Game Options",
				JOptionPane.PLAIN_MESSAGE,
				null,
				possibilities,
				"Guard");
		if(s=="Novice")
			this.guard=0;
		else if(s== "Drunk")
			this.guard=1;
		else if(s=="Suspicious")
			this.guard=2;
	}

	public void chooseOgre(){
		JTextField field1 = new JTextField();
		Boolean exit=true;
		Object[] message = {
				"Number of Ogres (1-5):", field1,
		};
		while(true){
			exit=true;
			JOptionPane.showConfirmDialog(frame, message, "Ogre Options", JOptionPane.PLAIN_MESSAGE);
			String value1 = field1.getText();
			try{ 
				this.ogres=Integer.parseInt(value1);
			}
			catch (NumberFormatException n){
				exit=false;
				if(value1.length() == 0)
					this.ogres = 0;
				else{
					JOptionPane.showMessageDialog(frame, "It's supoesed to be 1-5 ogres");
					disableButtons();
					//return;

				}

			}
			if(ogres<0 ||ogres>5){
				JOptionPane.showMessageDialog(frame, "It's supoesed to be 1-5 ogres");
				exit=false;
			}

			if(exit)
				return;
		}
	}
	
	public void createNewGame(GameLogic game){
		this.frame.getContentPane().setLayout(new BorderLayout());
		enableButtons();
		this.game=game;
		this.imgs_panel = new PrettyPanel( UserInput.getPrintableMap( this.game.getMap().getMap() , this.game.getAllCharacters(), false , false) );
		initializeImgPanelListeners();
		this.temp = this.frame.getContentPane();
		this.frame.setVisible(true);
		this.frame.getContentPane().removeAll();
		this.frame.setBounds(100, 100, 500 , 500);
		this.frame.getContentPane().add(this.imgs_panel);
		this.frame.repaint();
		this.imgs_panel.requestFocus();
		System.out.println("FINISHED SETTING UP!");
	}
	
	


	public void newGame(){
		chooseGuard();
		chooseOgre();
		this.input = new UserInput();
		this.game = new GameLogic(null,guard+1);

		enableButtons();
		if(this.ogres != 0)
		createNewGame(game);

		//console_area.setText(input.getPrintableMap(game,false,true));
		//console_area.requestFocus();


		//Save current Frame and clear it to show only map

		//this.frame.requestFocus();


	}

	public void enableButtons(){
		this.down_b.setEnabled(true);
		this.up_b.setEnabled(true);
		this.left_b.setEnabled(true);
		this.right_b.setEnabled(true);
	}
	
	public void proccessButton(char pressed){
		proccessKey(pressed);
	}
	
	
	
	private void initializeImgPanelListeners(){
		this.imgs_panel.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				System.out.println("KEY PRESSED!");
				proccessKey(translateKey(e));
			}
		});
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		frame.setBounds(100, 100, 755, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				proccessKey(translateKey(e));
			
			}
		});
		
		
		//---- BEGIN BUTTONS ----
		this.up_b = new JButton("Up");
		up_b.setBounds(596, 225, 69, 29);
		up_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('w');
			}
		});
		up_b.setEnabled(false);
		up_b.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		this.left_b = new JButton("Left");
		left_b.setBounds(528, 265, 69, 29);
		left_b.setEnabled(false);
		left_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('a');
			}
		});
		left_b.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		this.right_b = new JButton("Right");
		right_b.setBounds(674, 265, 69, 29);
		right_b.setEnabled(false);
		right_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('d');
			}
		});
		right_b.setFont(new Font("Tahoma", Font.PLAIN, 12));
	
		
		this.down_b = new JButton("Down");
		down_b.setBounds(596, 305, 69, 29);
		down_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('s');
			}
		});
		down_b.setEnabled(false);
		down_b.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		
		JButton new_game = new JButton("New Game");
		new_game.setBounds(245, 484, 142, 46);
		new_game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				newGame();
			}
		});
		
		this.frame.getContentPane().setLayout(new BorderLayout());
		this.frame.getContentPane().add(new_game,BorderLayout.EAST);
		//this.frame.getContentPane().add(this.down_b);

		JButton btnMapCreation = new JButton("Map Creation");
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
				    
				   
				    //CreationFrame cf= new CreationFrame(height,width);
				    frame.setVisible(false);
				    CreateMap.Construct(width,height);
					}

		});
		btnMapCreation.setBounds(66, 501, 142, 29);
		frame.getContentPane().add(btnMapCreation,BorderLayout.SOUTH);
		
		Initial_Backgorund initial_Backgorund = new Initial_Backgorund();
		initial_Backgorund.setBounds(36, 54, 524, 328);
		frame.getContentPane().add(initial_Backgorund);
		
		JButton exit_b = new JButton("Exit");
		initial_Backgorund.add(exit_b);
		exit_b.setBounds(596, 452, 69, 25);
		exit_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		//initial_Backgorund.setLayout(new BorderLayout(0, 0));

		this.frame.repaint();
	}
}
