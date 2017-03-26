package dkeep.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class PlayButtons extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton up , left , down , right , back , save;
	
	PlayButtons(){
		this.initButtons();
		this.add(this.up);
		this.add(this.left);
		this.add(this.down);
		this.add(this.right);
		this.add(this.save);
		this.add(this.back);
		
	}
	
	private void initButtons(){
		this.initUpButton();
		this.initLeftButton();
		this.initRightButton();
		this.initDownButton();
		this.initBackButton();
		this.initSaveButton();
	}
	
	private void initUpButton(){
		this.up = new JButton("Up");
		this.up.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('w');
				GameWindow.focus();
			}

		});
	}
	private void initLeftButton(){
		this.left = new JButton("Left");
		this.left.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('a');
				GameWindow.focus();
			}

		});
	}
	private void initRightButton(){
		this.right = new JButton("Right");
		this.right.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('d');
				GameWindow.focus();
			}

		});
	}
	private void initDownButton(){
		this.down = new JButton("Down");
		this.down.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.proccessKey('s');
				GameWindow.focus();
			}

		});
	}
	private void initBackButton(){
		this.back = new JButton("Back");
		back.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	GameWindow.backtoMenu();
	      }
	    });
	}
	private void initSaveButton(){
		this.save = new JButton("Save Game");
		this.save.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	GameWindow.save();
	    	GameWindow.focus();
	      }
	    });
	}
}
