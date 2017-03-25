package dkeep.gui;



import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import dkeep.logic.GameCharacter;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;


public class CreateMap extends JPanel implements MouseListener{
	
	

	private static final long serialVersionUID = 1L;
	private char[][] map;
	private char[][] definitive_map;
	private static int height;
	private static int width;
	private HashMap<Character,BufferedImage> char_to_img = new HashMap<Character,BufferedImage>();
	private int ogres_number;
	private Hero hero;
	private ArrayList <GameCharacter> ogres;
	private static Boolean key_b;
	private static Boolean door_b;
	private static Boolean hero_b;
	Pair<Integer,Integer> key;
	Pair< Pair<Integer,Integer> ,String> door;
	

	CreateMap(int height, int width){
		key_b=false;
		door_b=false;
		hero_b=false;
		ogres = new ArrayList<GameCharacter>();
		this.addMouseListener(this);
		CreateMap.height=height; CreateMap.width=width;
		ogres_number=0;
		map = new char[height][width];
		definitive_map = new char[height][width];

		for (int linha = 0; linha < height; linha++)
			for (int coluna = 0; coluna < width; coluna++) 
				definitive_map[linha][coluna] = (map[linha][coluna] = (linha == 0 || coluna == 0 || 
																linha == height-1 || coluna == width-1) ? 'X' : ' ');
		this.loadAllImages();
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
	
	
	
	boolean contaisComp(char c){
		for (int i = 0; i < height; i++) 
			for(int j=0; j< width;j++)
				if (map[i][j]==c)
					return true;
		
		return false;
	}
	
	void removesComp(char c){
		for (int i = 0; i < height; i++) 
			for(int j=0; j< width;j++)
				if (map[i][j]==c){
					map[i][j]=' ';
					definitive_map[i][j]=' ';
				}
	}
	
	void removeOgre(int i, int j){
		for(int h=0; h <ogres.size();h++)
			if(ogres.get(h).getPos().get(0).getFirst() ==i && ogres.get(h).getPos().get(0).getSecond()==j )
				ogres.remove(h);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int j_map = arg0.getX()/50 , i_map = arg0.getY()/50; 
		System.out.println(i_map + "  " + j_map);
		System.out.println(CreateOptions.getSelecionado());
		if(CreateOptions.getSelecionado()=="Hero")
			this.insertHero(i_map, j_map);
		else if(CreateOptions.getSelecionado()=="Ogre")
			this.insertOgre(i_map, j_map);
		else if(CreateOptions.getSelecionado()=="Wall")
			this.insertWall(i_map, j_map);
		else if(CreateOptions.getSelecionado()=="Exit")
			this.insertExit(i_map, j_map);
		else if(CreateOptions.getSelecionado()=="Key")
			this.insertKey(i_map, j_map);
		}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	private void insertHero( int x , int y){
		if(map[x][y]!='A'){
			if(contaisComp('A'))
				removesComp('A');
				map[x][y]='A';
				definitive_map[x][y]=' ';
				hero_b=true;
				hero= new Hero(new Pair<Integer,Integer>(x,y) , new Pair<Integer,Integer>(width,height) );
				hero.setArmed(true);
			}
			else{
				map[x][y]=' ';
				hero_b=false;
			}
			repaint();
	}
	
	private void insertOgre( int x , int y){
		if(map[x][y]!='O'){
			if(ogres_number<5){
				map[x][y]='O';
				definitive_map[x][y]=' ';
			ogres_number++;
			Ogre o=new Ogre(new Pair<Integer,Integer>(x,y) ,new Pair<Integer,Integer>(width,height));
			ogres.add(o);
			}
			}
			else{
				map[x][y]=' ';
				ogres_number--;
				removeOgre(x,y);
			}
			repaint();
	}
	
	private void insertWall( int x , int y){
		if(map[x][y]!='X'){
			map[x][y]='X';
			definitive_map[x][y]='X';
		}
		else{
			map[x][y]=' ';
			definitive_map[x][y]=' ';
		}
		repaint();
	}
	
	private void insertExit( int x , int y){
		if(map[x][y]!='I'){
			if(contaisComp('I'))
				removesComp('I');
				map[x][y]='I';
				definitive_map[x][y]='I';
				door_b=true;
				door=new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(x) ,new Integer(y)) , "S");
			}
			else{
				map[x][y]=' ';
				definitive_map[x][y]=' ';
				door_b=false;
			}
			repaint();
	}
	
	private void insertKey( int x , int y){
		if(map[x][y]!='k'){
			if(contaisComp('k'))
				removesComp('k');
				map[x][y]='k';
				definitive_map[x][y]='k';
				key_b=true;
				key=new Pair<Integer,Integer>(x,y);
			}
			else{
				map[x][y]=' ';
				definitive_map[x][y]=' ';
				key_b=false;
			}
			repaint();
	}
	
	public static Boolean canStart(){
		return (key_b &&hero_b && door_b);
	}
	
	public void paint(Graphics g){
		super.paint(g);

		System.out.println("Trying to paint Panel!");
		int x = 0, y = 0;
		for (int i = 0; i < height; i++) {
			for(int j=0; j< width;j++){
			g.drawImage( this.char_to_img.get( map[i][j]) , x , y , 50 , 50 , null);
			g.drawRect(x, y, 50, 50);
			x+= 50;
		}
			y+= 50;
			x=0;
			continue;
	}
	}
	
	private void loadAllImages(){
		this.loadGuardImage();
		this.loadOgreImage();
		this.loadHeroImage();
		this.loadWallImage();
		this.loadClubImage();
		this.loadFloorImage();
		this.loadDoorImage();
		this.loadStairImage();
		this.loadLeverImag();
	}
	private void loadGuardImage(){
		try {                
			this.char_to_img.put(new Character('G') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Guard.png")));
		} catch (IOException ex) {
			System.out.println("Error reading guard image!");
		}
	}
	private void loadOgreImage(){
		try {                
			this.char_to_img.put(new Character('O') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Ogre.png")));
		} catch (IOException ex) {
			System.out.println("Error reading ogre image!");
		}
	}
	private void loadHeroImage(){
		try {                
			this.char_to_img.put(new Character('A') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Hero_armed.png")));
		} catch (IOException ex) {
			System.out.println("Error reading hero image!");
		}
	}
	private void loadWallImage(){
		try {                
			this.char_to_img.put(new Character('X') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Wall.png")));
		} catch (IOException ex) {
			System.out.println("Error reading wall image!");
		}
	}
	private void loadClubImage(){
		try {                
			this.char_to_img.put(new Character('*') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Club.png")));
		} catch (IOException ex) {
			System.out.println("Error reading club image!");
		}
	}
	private void loadFloorImage(){
		try {                
			this.char_to_img.put(new Character(' ') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Floor.png")));
		} catch (IOException ex) {
			System.out.println("Error reading floor image!");
		}
	}
	private void loadDoorImage(){
		try {                
			this.char_to_img.put(new Character('I') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Door.png")));
		} catch (IOException ex) {
			System.out.println("Error reading door image!");
		}
	}
	private void loadStairImage(){
		try {                
			this.char_to_img.put(new Character('S') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Stair.png")));
		} catch (IOException ex) {
			System.out.println("Error reading stair image!");
		}
	}
	private void loadLeverImag(){
		try {                
			this.char_to_img.put(new Character('k') ,ImageIO.read(new File(System.getProperty("user.dir")+"/imgs/Lever.png")));
		} catch (IOException ex) {
			System.out.println("Error reading lever image!");
		}
	}
}
