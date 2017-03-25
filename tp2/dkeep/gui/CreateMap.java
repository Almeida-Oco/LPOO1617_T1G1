package dkeep.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dkeep.logic.GameCharacter;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;


//import maze.gui.CreateOptions;

public class CreateMap extends JPanel implements MouseListener{
	
	

	private static final long serialVersionUID = 1L;
	private char[][] map;
	private char[][] definitive_map;
	private static int height;
	private static int width;
	private HashMap<Character,BufferedImage> char_to_img = new HashMap<Character,BufferedImage>();
	private static CreateMap panel;
	private static JFrame f;
	private int ogres_number;
	private Hero hero;
	private ArrayList <GameCharacter> ogres;
	Pair<Integer,Integer> key;
	Pair< Pair<Integer,Integer> ,String> door;
	

	CreateMap(int height, int width){
		ogres = new ArrayList<GameCharacter>();
		this.addMouseListener(this);
		CreateMap.height=height;
		CreateMap.width=width;
		ogres_number=0;
		map = new char[height][width];
		definitive_map = new char[height][width];

		for (int linha = 0; linha < height; linha++) {
			for (int coluna = 0; coluna < width; coluna++) {
				map[linha][coluna] = ' ';
				definitive_map[linha][coluna] = ' ';
			}
		}
				

		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
				if(i==0 || j==0 || j==width-1|| i==height-1){
					map[i][j] = 'X';
					definitive_map[i][j]  = 'X';
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
			this.char_to_img.put(new Character('A') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Hero_armed.png")));
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
	
	
	static public CreateMap getpanel(){
		return panel;
		}
	
	public JFrame getframe(){
		return f;
	}
	
	public void putLimits(){
		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
				if(i==0 || j==0 || j==width-1|| i==height-1){
					if(definitive_map[i][j]==' ')
					definitive_map[i][j]  = 'X';
				}
				
			}
		}
	}
	
	
	Hero getHero(){
		return this.hero;
	}
	
	ArrayList <GameCharacter> getOgres(){
		return ogres;
	}
	
	Pair<Integer,Integer> getKey(){
		return key;
	}
	
	Pair< Pair<Integer,Integer> ,String> getDoor(){
		return door;
	}
	
	char[][] getMap(){
		return definitive_map;
	}
	

	
	
	
		
	
	public static void Construct(int height, int width){
		f = new JFrame("Constroi o teu labirinto");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(width*50+width/2,height*50+65));   //(620, 670));
		f.getContentPane().setLayout(new BorderLayout());
		JPanel panel2 = new CreateOptions();
		panel = new CreateMap(height,width);
		f.getContentPane().add(panel , BorderLayout.CENTER);
		f.getContentPane().add(panel2, BorderLayout.SOUTH);
		f.pack();
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setFocusable(true);
		f.requestFocus();
	}
	
	
	
	
	
	JFrame getFrame(){
		return f;
	}
	
	Boolean contaisComp(char c){
		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
				if (map[i][j]==c)
					return true;
			}
			}
		return false;
	}
	
	void removesComp(char c){
		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
				if (map[i][j]==c){
					map[i][j]=' ';
					definitive_map[i][j]=' ';
				}
			}
			}
	}
	
	void removeOgre(int i, int j){
		for(int h=0; h <ogres.size();h++){
			if(ogres.get(h).getPos().get(0).getFirst() ==i && ogres.get(h).getPos().get(0).getSecond()==j ){
				ogres.remove(h);
			}
		}
	}
	
	
	

	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int j_map = arg0.getX()/50;  ///(getWidth()/width);
		int i_map = arg0.getY()/50;  ///(getHeight()/height);
		System.out.println(i_map + "  " + j_map);
		System.out.println(CreateOptions.getSelecionado());
		
		
		if(CreateOptions.getSelecionado()=="Hero"){
			if(map[i_map][j_map]!='A'){
			if(contaisComp('A'))
				removesComp('A');
				map[i_map][j_map]='A';
				definitive_map[i_map][j_map]=' ';
				hero= new Hero(new Pair<Integer,Integer>(i_map,j_map) , new Pair<Integer,Integer>(height,width) );
				hero.setArmed(true);
			}
			else
				map[i_map][j_map]=' ';
			repaint();
		}
		
		else if(CreateOptions.getSelecionado()=="Ogre"){
			if(map[i_map][j_map]!='O'){
			if(ogres_number<5){
				map[i_map][j_map]='O';
				definitive_map[i_map][j_map]=' ';
			ogres_number++;
			Ogre o=new Ogre(new Pair<Integer,Integer>(i_map,j_map) ,new Pair<Integer,Integer>(height,width));
			ogres.add(o);
			}
			}
			else{
				map[i_map][j_map]=' ';
				ogres_number--;
				removeOgre(i_map,j_map);
			}
			repaint();
		}
		
		else if(CreateOptions.getSelecionado()=="Wall"){
			if(map[i_map][j_map]!='X'){
				map[i_map][j_map]='X';
				definitive_map[i_map][j_map]='X';
			}
			else{
				map[i_map][j_map]=' ';
				definitive_map[i_map][j_map]=' ';
			}
			repaint();
		}
		
		else if(CreateOptions.getSelecionado()=="Exit"){
			if(map[i_map][j_map]!='I'){
			if(contaisComp('I'))
				removesComp('I');
				map[i_map][j_map]='I';
				definitive_map[i_map][j_map]='I';
				door=new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(i_map) ,new Integer(j_map)) , "S");
			}
			else{
				map[i_map][j_map]=' ';
				definitive_map[i_map][j_map]=' ';
			}
			repaint();
		}
		else if(CreateOptions.getSelecionado()=="Key"){
			if(map[i_map][j_map]!='k'){
			if(contaisComp('k'))
				removesComp('k');
				map[i_map][j_map]='k';
				definitive_map[i_map][j_map]='k';
				key=new Pair<Integer,Integer>(i_map,j_map);
			}
			else{
				map[i_map][j_map]=' ';
				definitive_map[i_map][j_map]=' ';
			}
			repaint();
		}
		
		
		}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	
	

	public void paint(Graphics g){
		super.paint(g);

		System.out.println("Trying to paint Panel!");
		int x = 0, y = 0;
		//System.out.println(this.current_map);
		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
			g.drawImage( this.char_to_img.get( map[i][j]) , x , y , 50 , 50 , null); //(getWidth()/width),(getHeight()/height),null);
			g.drawRect(x, y, 50, 50);
			x+= 50; //(getWidth()/width);
		}
			y+= 50;//(getHeight()/height);
			x=0;
			continue;
	}
	}

}
