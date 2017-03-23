package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import maze.gui.CreateOptions;

public class CreateMap implements MouseListener {
	
	private static JPanel panel;
	private static char[][] map;
	private static JFrame f;
	private static int height;
	private static int width;

	CreateMap(int height, int width){
		
		CreateMap.height=height;
		CreateMap.height=width;
		CreateMap.map = new char[height][width];
		String mapa="";
<<<<<<< HEAD
		for (int linha = 0; linha < height; linha++) {
			for (int coluna = 0; coluna < width; coluna++) {
				map[linha][coluna] = ' ';
				
=======
		for (int linha = 0; linha < size; linha++) {
			for (int coluna = 0; coluna < size; coluna++) {
				map[linha][coluna] = (0 == linha || 0 == coluna || (size-1) == linha || (size-1) == coluna) ? 'X' : ' ';
				mapa+=map[linha][coluna];
>>>>>>> origin/MapCreator
			}
			mapa+="\n";
		}
<<<<<<< HEAD

		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
				if(i==0 || j==0 || j==width-1|| i==height-1){
					map[i][j] = 'X';
				}
				
		}
=======
/*
		for (int i = 0; i < size; i++) {
			map[0][i] = 'X';
			map[i][0] = 'X';
			map[size - 1][i] = 'X';
			map[i][size - 1] = 'X';
>>>>>>> origin/MapCreator
		}
		
		for (int linha = 0; linha < height; linha++) {
			for (int coluna = 0; coluna < width; coluna++) {
				mapa+=map[linha][coluna];
				
			}
			mapa+="\n";
		}
*/
		
		f = new JFrame("Constroi o teu labirinto");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setPreferredSize(new Dimension(620, 670));
		f.getContentPane().setLayout(new BorderLayout());
		JPanel panel2 = new CreateOptions();
		panel =new PrettyPanel();
		((PrettyPanel) panel).updateCurrentMap(mapa);
		System.out.println(mapa);
		
		f.getContentPane().add(panel, BorderLayout.CENTER);
		f.getContentPane().add(panel2, BorderLayout.SOUTH);
		f.pack();
		f.setResizable(true);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setFocusable(true);
		f.requestFocus();
		
		
	}
	
	
	JFrame getframe(){
		return f;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
