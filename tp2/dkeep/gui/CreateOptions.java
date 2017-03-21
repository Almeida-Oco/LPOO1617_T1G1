package dkeep.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


public class CreateOptions extends JPanel{
	private static final long serialVersionUID = 6930814056136819156L;
	static JComboBox<String> lista;
	
	/**
	 * Cria as opções do menu de criação de um labirinto
	 */
	CreateOptions(){
		JButton salvar = new JButton("Salvar");
		/*salvar.addActionListener(new ActionListener()
	    {
	      
	    });*/
		
		
		
		JButton voltar = new JButton("Voltar");
		voltar.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	 /*JPanel myPanel = InGameGUI.getInstance().getPanel();
	     	 CreateOptions.this.setVisible(false);
	     	 myPanel.setFocusable(true);
	     	 myPanel.requestFocus();
	     	 CreateMaze.getFrame().dispose();*/
	      }
	    });
		
		lista = new JComboBox<String>();
		lista.addItem("Saida");
		lista.addItem("Parede");
		lista.addItem("Herói");
		lista.addItem("Ogre");
		lista.addItem("Guarda");
		this.add(salvar);
		this.add(voltar);
		this.add(lista);
	}
	
	/**
	 * Retorna o que o utilizador selecionou da lista
	 * @return item selecionado
	 */
	public static String getSelecionado(){
		return (String)lista.getSelectedItem();
	}
}