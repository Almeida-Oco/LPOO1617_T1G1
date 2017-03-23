package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class PrettyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int IMG_WIDTH = 50;
	private final int IMG_HEIGHT= 50;
	private HashMap<Character,BufferedImage> char_to_img = new HashMap<Character,BufferedImage>();
	private BufferedImage guard_image;
	private BufferedImage ogre_image;
	private BufferedImage hero_image;
	private BufferedImage floor_image;
	private BufferedImage wall_image;
	private BufferedImage club_image;
	private BufferedImage door_image;
	private BufferedImage stair_image;
	private BufferedImage lever_image;
	private String current_map;
	
	/**
	 * Create the panel.
	 */
	public PrettyPanel(){
		setBackground(Color.WHITE);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("KEY PRESSED!");
			}
		});
		System.out.print("CREATING PRETTY PANEL!\n");
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
	
	public PrettyPanel(String map) {
		this();
		this.current_map = map;
		this.setBounds(0, 0, this.calculateWidth(), this.calculateHeight() );
	}

	public int calculateWidth(){
		int max = -1, cont = 0;
		for (int i = 0 ; i < this.current_map.length() ; i++){
			if ( this.current_map.charAt(i) != '\n')
				cont++;
			else{
				max = (max > cont) ? max : cont;
				cont = 0;
			}
		}
		System.out.print("WIDTH = "+max);
		return max*this.IMG_WIDTH;
	}
	
	public int calculateHeight(){
		int cont = 0;
		for (int i = 0 ; i < this.current_map.length() ; i++)
			if ( this.current_map.charAt(i) == '\n')
				cont++;
		
		System.out.print("HEIGHT = "+cont);
		return (cont*this.IMG_HEIGHT);
		//TODO check if there is a multiplatform way of making the window perfect size
	}
	
	public void updateCurrentMap(String map){
		this.current_map = map;
	}
	

	public void paint(Graphics g){
		super.paint(g);
		int x = 0, y = 0;
		System.out.println(this.current_map);
		for(int i = 0 ; i < this.current_map.length() ; i++){
			if(this.current_map.charAt(i) == '\n'){
				y+=50;
				x=0;
				continue;
			}
			
			g.drawImage( this.char_to_img.get( this.current_map.charAt(i) ) , x , y ,null);
			x+=50;
		}
	}
}
