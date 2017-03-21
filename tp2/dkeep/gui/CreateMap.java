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
	private static int size;

	CreateMap(int size){
		
		CreateMap.size=size;
		CreateMap.map = new char[size][size];
		String mapa="";
		for (int linha = 0; linha < size; linha++) {
			for (int coluna = 0; coluna < size; coluna++) {
				map[linha][coluna] = ' ';
				
			}
		}

		for (int i = 0; i < size; i++) {
			map[0][i] = 'X';
			map[i][0] = 'X';
			map[size - 1][i] = 'X';
			map[i][size - 1] = 'X';
		}
		
		for (int linha = 0; linha < size; linha++) {
			for (int coluna = 0; coluna < size; coluna++) {
				mapa+=map[linha][coluna];
				
			}
			mapa+="\n";
		}
		
		
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
