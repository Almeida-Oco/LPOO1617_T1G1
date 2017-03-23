package dkeep.logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import pair.Pair;
	
public abstract class Map implements java.io.Serializable{
	protected int width;
	protected int height;
	protected char[][] map;
	protected boolean lever = false;
	protected ArrayList< Pair<Pair<Integer,Integer> , String> > doors = new ArrayList< Pair< Pair<Integer,Integer> , String> >();
	protected ArrayList<GameCharacter> chars = new ArrayList<GameCharacter>();
	protected Pair<Integer,Integer> key;
	
	//TODO These arrays will most likely not be needed
	//private ArrayList<Character> game_chars = new ArrayList<Character>( Arrays.asList( 'H' , 'A' , 'K' , 'a' , 'O' , '1' , '2' , '*'  , 'g' , 'G' , 'D' , 'R'));
	//a -> Armed and has key , K -> has key and is not armed , A -> armed without keys, 1 -> Ogre stunned 1 turn to go, 2->Ogre stunned 2 turns to go
	// g -> Drunked guard sleeping, G -> Suspicious Guard , D -> Drunked guard awake , R -> RookieGuard
	//private ArrayList<Character> game_specials = new ArrayList<Character>( Arrays.asList( 'k' , 'l' , 'S', 'i'));
	//k -> Key , l -> Lever , S -> Stairs , i -> Door which key/lever opens
	
	/**
	 * @brief Constructor
	 * @param guards (-1, No guards to generate) , (0, Randomly select guard) , (1 , generate RookieGuard) , (2, generate DrunkenGuard) , (3, generate SuspiciousGuard) 
	 * @param ogres (-1, No ogre to generate) , (0, Randomly generate number of ogres) , (x , generate x ogres)
	 * @param map Map to use for the specified level
	 */
	public Map(int guards , int ogres, char[][] map){ // 1-Rookie, 2 - Drunken, 3-Suspicious 
		if(map != null){
			Random rand = new Random();
			this.map = map;
			this.height = map.length;
			this.width = map[0].length;
			Pair<Integer,Integer> map_size = new Pair<Integer,Integer>(this.width,this.height);
			if (guards != -1){ //MEANING NO GUARDS TO GENERATE
				if (guards == 0) //IF GUARD IS 0 THEN RANDOMLY SELECT GUARD
					guards = rand.nextInt(3)+1;
				this.chars.add( (1 == guards) ? new RookieGuard(map_size) : ( (2 == guards) ? new DrunkenGuard(map_size) : new SuspiciousGuard(map_size)));
			}

			if (ogres != -1){ // MEANING NO OGRE TO GENERATE
				if (ogres == 0) //IF OGRE IS 0 THEN RANDOMLY SELECT NUMBER OF OGRES
					ogres = rand.nextInt(5) + 1;
				for (int i = 0; i < ogres; i++)
					this.chars.add(new Ogre(new Pair<Integer,Integer>(rand.nextInt(8) + 1, rand.nextInt(8) + 1) , map_size , false));
			}
		}
	}

	/**
	 * @brief Checks if the positions passed are free
	 * @param l Array of positions to check 
	 * @return -1 if all positions are free, else number that is the position of the array which has the not free pposition
	 * @details Before it checks if the tile is free it checks if the coordinates are valid.
	 * 		 	A tile is considered free if it is either a ' ', 'S' or a 'k'. Anything else is considered occupied.
	 */
	public int isFree( ArrayList< Pair<Integer,Integer> > l){
		int i = 0;
		for (Pair<Integer,Integer> p : l){
			if (p.getFirst() > 0 && p.getFirst() < this.height && p.getSecond() > 0 && p.getSecond() < this.width ){
				if (this.map[p.getFirst().intValue()][p.getSecond().intValue()] == ' ' || 
						this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'S' || 
						this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'k' )
					i++;
				else
					return i;
			}
			else
				return i;
		}

		return -1;
	}
	
	/**
	 * @brief Opens the doors of the map
	 * @param keep_closed Whether to keep the doors closed or not
	 * @details If the map has a lever keep_closed should be false, if the map has a key which the hero picks up then
	 * 			keep_closed should be true
	 */
	public void openDoors( boolean keep_closed){
		//TODO add an argument that says which door of the array to open (key can work for multiple doors)
		if( !keep_closed){
			for (Pair< Pair<Integer,Integer> , String> pos : this.doors)
				this.map[ pos.getFirst().getFirst().intValue() ][ pos.getFirst().getSecond().intValue() ] = pos.getSecond().charAt(0);
		}
	}
	
	
	/**
	 * @brief Returns next map
	 * @param enemies How many enemies to generate
	 * @return Object of the next map class
	 */
	public abstract Map nextMap(int enemies);
	
	/**
	 * @brief Picks up key from map, to be implemented by child classes
	 * @return true if hero is supposed to keep the key, or false if it aint (AKA if its a lever)
	 */
	public boolean pickUpKey() {
		this.map[ this.key.getFirst() ][ this.key.getSecond() ] = (lever) ? this.map[ this.key.getFirst() ][ this.key.getSecond() ] : ' ';
		return !this.lever;
	}
	
	
	public ArrayList<Pair<Integer,Integer> > getDoors(){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		for (Pair<Pair<Integer,Integer> , String > p : this.doors )
			temp.add(p.getFirst());
		
		return temp;
	}
	
	
	/**
	 * @brief Gets the map
	 * @return map
	 */
	public char[][] getMap(){
		char[][] temp = new char[this.height][];
		int i = 0;
		for (char[] arr : this.map){
			temp[i] = (char[])arr.clone();
			i++;
		}
		return temp;
	}
	
	
	/**
	 * @brief Gets the key
	 * @return The position of the key
	 */
	public Pair<Integer,Integer> getKey(){
		return this.key;
	}
	

	/**
	 * @brief Gets the heigth of this map
	 * @return Heigth of the map
	 */
	public int getHeight(){
		return this.height;
	}
	
	
	/**
	 * @brief Gets the width of this map
	 * @return Width of the map
	 */
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * @brief Size of the map
	 * @return Pair with (width,height) of the current map
	 */

	public Pair<Integer,Integer> getSize(){
		return new Pair<Integer,Integer>(this.width,this.height);
	}
	
	
	/**
	 * @brief Gets all characters of the map
	 * @return Array with all map characters(including hero)
	 */
	public ArrayList<GameCharacter> getCharacters(){
		return (ArrayList<GameCharacter>)this.chars.clone();
	}
	
	
	/**
	 * @brief Gets a specific tile of the map
	 * @param p Tile to get
	 * @return What is in the specified tile
	 */
	public char getTile(Pair<Integer,Integer> p){
		return this.map[p.getFirst().intValue()][p.getSecond().intValue()];
	}
}