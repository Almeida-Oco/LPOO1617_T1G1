package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Initial_Backgorund extends JPanel {
	private BufferedImage background;
	private static final long serialVersionUID = 1L;
	
    Initial_Backgorund(){
		try{
			background = ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/initial.png"));
		}
		catch (IOException ex) {
			System.out.println("Error reading background image!");
	}
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // limpa fundo ...
	
			g.drawImage(background, 0, 0, getWidth(),getHeight(),
					null);
	
	
	
	


	}
}
