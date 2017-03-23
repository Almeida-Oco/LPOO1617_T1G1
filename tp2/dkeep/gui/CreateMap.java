package dkeep.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

//import maze.gui.CreateOptions;

public class CreateMap extends JPanel implements MouseListener {
	
	

	private static final long serialVersionUID = 1L;
	private static char[][] map;
	private static int height;
	private static int width;
	private HashMap<Character,BufferedImage> char_to_img = new HashMap<Character,BufferedImage>();
	private static JPanel panel;
	private static JFrame f;
	

	CreateMap(int height, int width){
		this.addMouseListener(this);
		CreateMap.height=height;
		CreateMap.width=width;
		CreateMap.map = new char[height][width];

		for (int linha = 0; linha < height; linha++) {
			for (int coluna = 0; coluna < width; coluna++) {
				map[linha][coluna] = ' ';
			}
		}
				

		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
				if(i==0 || j==0 || j==width-1|| i==height-1){
					map[i][j] = 'X';
				}
				
			}
		}
		
		try {                
			this.char_to_img.put(new Character('G') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Guard.png")));
		} catch (IOException ex) {
			System.out.println("Error reading guard image!");
		}
		try {                
			this.char_to_img.put(new Character('O') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Ogre.png")));
		} catch (IOException ex) {
			System.out.println("Error reading ogre image!");
		}
		try {                
			this.char_to_img.put(new Character('H') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Hero.png")));
		} catch (IOException ex) {
			System.out.println("Error reading hero image!");
		}
		try {                
			this.char_to_img.put(new Character('X') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Wall.png")));
		} catch (IOException ex) {
			System.out.println("Error reading wall image!");
		}
		try {                
			this.char_to_img.put(new Character('*') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Club.png")));
		} catch (IOException ex) {
			System.out.println("Error reading club image!");
		}
		try {                
			this.char_to_img.put(new Character(' ') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Floor.png")));
		} catch (IOException ex) {
			System.out.println("Error reading floor image!");
		}
		try {                
			this.char_to_img.put(new Character('I') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Door.png")));
		} catch (IOException ex) {
			System.out.println("Error reading door image!");
		}
		try {                
			this.char_to_img.put(new Character('S') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Stair.png")));
		} catch (IOException ex) {
			System.out.println("Error reading stair image!");
		}
		try {                
			this.char_to_img.put(new Character('k') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Lever.png")));
		} catch (IOException ex) {
			System.out.println("Error reading lever image!");
		}

		}
	
	
		
	
	public static void Construct(int height,int wheight){
		f = new JFrame("Constroi o teu labirinto");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(620, 670));
		f.getContentPane().setLayout(new BorderLayout());
		JPanel panel2 = new CreateOptions();
		panel =new CreateMap(height,wheight);
		f.getContentPane().add(panel, BorderLayout.CENTER);
		f.getContentPane().add(panel2, BorderLayout.SOUTH);
		f.pack();
		f.setResizable(true);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setFocusable(true);
		f.requestFocus();
		}
	
	JFrame getFrame(){
		return f;
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int i_map = arg0.getX()/(getWidth()/width);
		int j_map = arg0.getY()/(getHeight()/height);
		System.out.println(i_map + "  " + j_map);
		
		
		
		
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
	
	
	

	public void paint(Graphics g){
		super.paint(g);

		System.out.println("Trying to paint Panel!");
		int x = 0, y = 0;
		//System.out.println(this.current_map);
		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
			g.drawImage( this.char_to_img.get( map[i][j]) , x , y ,(getWidth()/width),(getHeight()/height),null);
			x+=(getWidth()/width);
		}
			y+=(getHeight()/height);
			x=0;
			continue;
	}
	}

}
