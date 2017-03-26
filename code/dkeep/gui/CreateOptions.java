package dkeep.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CreateOptions extends JPanel{
	private static final long serialVersionUID = 6930814056136819156L;
	static JComboBox<String> lista;
	private JButton play , back;
	
	/**
	 * Cria as opcoes do menu de criacao de um labirinto
	 */
	CreateOptions(){
		this.initPlay();
		this.initBack();
		this.initOptions();
		this.add(this.play);
		this.add(this.back);
		this.add(lista);
	}
	
	private void initPlay(){
		this.play = new JButton("PlayGame");
		this.play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(CreateMap.canStart())
					GameWindow.initializeCreatedMap();	
				else
					GameWindow.showError();
			}

		});
	}
	private void initBack(){
		this.back = new JButton("Back");
		this.back.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	GameWindow.backtoMenu();
	      }
	    });
	}
	private void initOptions(){
		lista = new JComboBox<String>();
		lista.addItem("Exit");
		lista.addItem("Wall");
		lista.addItem("Hero");
		lista.addItem("Ogre");
		lista.addItem("Key");
	}
	/**
	 * Retorna o que o utilizador selecionou da lista
	 * @return item selecionado
	 */
	public static String getSelecionado(){
		return (String)lista.getSelectedItem();
	}
}