package dkeep.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import dkeep.logic.GameCharacter;
import dkeep.logic.GameLogic;
import dkeep.logic.GenericMap;
import dkeep.logic.Pair;

public class CreateOptions extends JPanel{
	private static final long serialVersionUID = 6930814056136819156L;
	static JComboBox<String> lista;
	
	/**
	 * Cria as opcoes do menu de criacao de um labirinto
	 */
	CreateOptions(){
		JButton salvar = new JButton("PlayGame");
		salvar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CreateMap cm= CreateMap.getpanel();
				cm.setVisible(false);
				cm.putLimits();
				cm.getframe().setVisible(false);
				GenericMap gm=new GenericMap(cm.getMap());

				ArrayList <GameCharacter> characters=cm.getOgres();
				characters.add(cm.getHero());
				gm.setCharacters(characters);
				gm.setKey(cm.getKey(), false);
				ArrayList<Pair<Pair<Integer,Integer> , String > > doors = new ArrayList<Pair<Pair<Integer,Integer> , String> >();
				doors.add(cm.getDoor());
				gm.setDoors(doors);
				GameLogic game = new GameLogic(gm,0);
				//String mp=UserInput.getPrintableMap(game, false , false);
				GameWindow n= new GameWindow();

				n.createNewGame(game);
			}

		});

		
		
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
		lista.addItem("Exit");
		lista.addItem("Wall");
		lista.addItem("Hero");
		lista.addItem("Ogre");
		lista.addItem("Key");
		
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