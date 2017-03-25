package dkeep.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class PlayButtons extends JPanel {
	private static final long serialVersionUID = 1L;

	PlayButtons(){
		JButton Up = new JButton("Up");
		Up.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('w');
				GameWindow.focus();
			}

		});
		
		JButton Left = new JButton("Left");
		Left.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('a');
				GameWindow.focus();
			}

		});
		
		JButton Down = new JButton("Down");
		Down.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('s');
				GameWindow.focus();
			}

		});
		
		JButton Right = new JButton("Right");
		Right.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('d');
				GameWindow.focus();
			}

		});

		
		
		JButton voltar = new JButton("Voltar");
		voltar.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	GameWindow.backtoMenu();
	      }
	    });
		
		JButton save = new JButton("Save Game");
		save.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	GameWindow.save();
	    	GameWindow.focus();
	      }
	    });
		
		
		
		this.add(Up);
		this.add(Left);
		this.add(Down);
		this.add(Right);
		this.add(save);
		this.add(voltar);
		
	}
}
