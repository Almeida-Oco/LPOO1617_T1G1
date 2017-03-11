package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import dkeep.cli.UserInput;
import dkeep.logic.GameLogic;
import dkeep.logic.Character;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class GameWindow {
	
	private JFrame frame;
	private JTextField OgreNumber;
	private JComboBox Guards;
	private JTextArea ConsoleArea;
	private JLabel StatusLabel,LabelNofOgres,LabelGuardPers;
	private JButton UpButton,DownButton,LeftButton,RightButton;
	private UserInput input;
	protected GameLogic game;

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

	public void disableButtons(){
		this.DownButton.setEnabled(false);
		this.UpButton.setEnabled(false);
		this.LeftButton.setEnabled(false);
		this.RightButton.setEnabled(false);
	}
	
	public void newGame(){
		int ogres = 0,guards = this.Guards.getSelectedIndex();
		try{ 
			ogres=Integer.parseInt(OgreNumber.getText());
		}
		catch (NumberFormatException n){
			StatusLabel.setText("Ogre number NaN!");
			disableButtons();
			return;
		}	
		
		this.input = new UserInput(ogres+1,guards+1);
		this.game = new GameLogic(0,ogres+1,guards+1);
		
		enableButtons();
		StatusLabel.setText("You can play now.");
		ConsoleArea.setText(input.printGame(game,game.getLevel(),false));
	}
	
	public void enableButtons(){
		this.DownButton.setEnabled(true);
		this.UpButton.setEnabled(true);
		this.LeftButton.setEnabled(true);
		this.RightButton.setEnabled(true);
	}
	
	public void proccessButton(char pressed){
		this.game = game.moveHero(pressed);
		this.game.moveAllVillains();	
		//System.out.println( this.input.printGame(this.game , this.game.getLevel() , true));
		this.ConsoleArea.setText(input.printGame(game,game.getLevel(),false));
		if (game.wonGame() || game.isGameOver()){
			disableButtons();
			this.StatusLabel.setText( (game.wonGame()) ? "YOU WIN!" : "YOU LOSE!" );
			
		}	
		//debug();
	}
	
 	private void debug(){
		for (Character ch : this.game.getAllCharacters() )
			System.out.println( ch.getClass()+" POS = "+ch.getPos());
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 755, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ConsoleArea = new JTextArea();
		ConsoleArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		ConsoleArea.setEditable(false);
		
		this.StatusLabel = new JLabel("You can start a New Game!");
		
		//---- BEGIN BUTTONS ----
		this.UpButton = new JButton("Up");
		UpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('w');
			}
		});
		UpButton.setEnabled(false);
		UpButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		this.LeftButton = new JButton("Left");
		LeftButton.setEnabled(false);
		LeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('a');
			}
		});
		LeftButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		this.RightButton = new JButton("Right");
		RightButton.setEnabled(false);
		RightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('d');
			}
		});
		RightButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		this.DownButton = new JButton("Down");
		DownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proccessButton('s');
			}
		});
		DownButton.setEnabled(false);
		DownButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		// ---- END BUTTONS ----
		
		this.LabelNofOgres = new JLabel("Number of Ogres");
		
		OgreNumber = new JTextField();
		OgreNumber.setColumns(10);
		
		this.LabelGuardPers = new JLabel("Guard Personality");
		
		this.Guards = new JComboBox();
		Guards.setModel(new DefaultComboBoxModel(new String[] {"Novice", "Drunk", "Suspicious"}));
		
		
		JButton NewGame = new JButton("New Game");
		NewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				newGame();
			}
		});
		
		JButton ExitButton = new JButton("Exit");
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(25)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(LabelGuardPers)
								.addComponent(LabelNofOgres))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(Guards, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 347, Short.MAX_VALUE)
									.addComponent(NewGame, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
									.addGap(55))
								.addComponent(OgreNumber, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(StatusLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(ConsoleArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(LeftButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
									.addGap(77)
									.addComponent(RightButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(68)
									.addComponent(UpButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(68)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(ExitButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(DownButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(48)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(LabelNofOgres)
								.addComponent(OgreNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(LabelGuardPers)
								.addComponent(Guards, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(72)
							.addComponent(NewGame)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(ConsoleArea, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(116)
							.addComponent(UpButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(RightButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(LeftButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addComponent(DownButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(ExitButton)))
					.addGap(18)
					.addComponent(StatusLabel)
					.addGap(34))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
