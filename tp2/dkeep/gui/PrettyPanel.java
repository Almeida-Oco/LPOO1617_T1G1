package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PrettyPanel extends JPanel {

	private BufferedImage guard_image;
	private BufferedImage ogre_image;
	private BufferedImage hero_image;
	private BufferedImage floor_image;
	private BufferedImage wall_image;
	private BufferedImage club_image;
	private char[][] current_map;
	
	/**
	 * Create the panel.
	 */
	public PrettyPanel(){
		try {                
	          this.guard_image = ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Guard.png"));
	       } catch (IOException ex) {
	            System.out.println("Error reading guard image!");
	       }
		try {                
	          this.ogre_image = ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Ogre.png"));
	       } catch (IOException ex) {
	            System.out.println("Error reading ogre image!");
	       }
		try {                
	          this.hero_image = ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Hero.png"));
	       } catch (IOException ex) {
	            System.out.println("Error reading hero image!");
	       }
		try {                
	          this.wall_image = ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Wall.png"));
	       } catch (IOException ex) {
	            System.out.println("Error reading wall image!");
	       }
		try {                
	          this.club_image = ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Club.png"));
	       } catch (IOException ex) {
	            System.out.println("Error reading club image!");
	       }
	}
	
	public PrettyPanel(char[][] map) {
		this();
		this.current_map = map;
	}

	/*
	 * //this.frame.getContentPane().add(this.frame2);
		this.frame2.setBounds(50,50,1024,780);
	 */
	
	public void updateCurrentMap(char[][] map){
		this.current_map = map;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int x = 0, y = 0;
		for(int i = 0 ; i < this.current_map.length ; i++){
			for(int j = 0 ; j < this.current_map[i].length ; j++){
				g.drawImage( (this.current_map[i][j] == 'X' ) ? this.wall_image :
						   ( (this.current_map[i][j] == ' ' ) ? this.floor_image:
						   ( (this.current_map[i][j] == 'G' ) ? this.guard_image:
						   ( (this.current_map[i][j] == 'O' ) ? this.ogre_image :
						   ( (this.current_map[i][j] == '*' ) ? this.club_image :
						   ( (this.current_map[i][j] == 'H' ) ? this.hero_image : null))))) , x , y ,null);
				x+=50;
						   
			}
			y+=50;
		}
	}
}
