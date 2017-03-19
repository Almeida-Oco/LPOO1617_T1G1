package dkeep.logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import pair.Pair;
	
public abstract class Map {
	protected int width;
	protected int height;
	protected int MAP_SIZE;
	protected char[][] map;
	protected ArrayList< Pair<Pair<Integer,Integer> , String> > doors = new ArrayList< Pair< Pair<Integer,Integer> , String> >();
	//private   HashMap<Character, GameCharacter> char_to_character;
	protected ArrayList<GameCharacter> chars = new ArrayList<GameCharacter>();
	protected Pair<Integer,Integer> key;
	
	/**
	 * @brief Constructor
	 * @param guards (-1, No guards to generate) , (0, Randomly select guard) , (1 , generate RookieGuard) , (2, generate DrunkenGuard) , (3, generate SuspiciousGuard) 
	 * @param ogres (-1, No ogre to generate) , (0, Randomly generate number of ogres) , (x , generate x ogres)
	 * @param map Map to use for the specified level.
	 */ //TODO Change this constructor to accept maps from varying size AKA != 10
	public Map(int guards , int ogres, char[][] map){ // 1-Rookie, 2 - Drunken, 3-Suspicious 
		Random rand = new Random();
		this.map = map;
		this.MAP_SIZE = map.length;
		if (guards != -1){ //MEANING NO GUARDS TO GENERATE
			if (guards == 0) //IF GUARD IS 0 THEN RANDOMLY SELECT GUARD
				guards = rand.nextInt(3)+1;
			this.chars.add( (1 == guards) ? new RookieGuard() : ( (2 == guards) ? new DrunkenGuard() : new SuspiciousGuard()));
		}
		
		if (ogres != -1){ // MEANING NO OGRE TO GENERATE
			if (ogres == 0) //IF OGRE IS 0 THEN RANDOMLY SELECT NUMBER OF OGRES
				ogres = rand.nextInt(5) + 1;
			for (int i = 0; i < ogres; i++)
				this.chars.add(new Ogre(rand.nextInt(8) + 1, rand.nextInt(8) + 1, this.map.length , false));
		}
	};
	
	/**
	 * @brief Checks if the positions passed are free
	 * @param l Array of positions to check 
	 * @return -1 if all positions are free, else number that is the position of the array which has the not free pposition
	 * @details Before it checks if the tile is free it checks if the coordinates are valid.
	 * 		 	A tile is considered free if it is either a ' ', 'S' or a 'k'. Anything else is considered occupied.
	 */
	public int isFree( ArrayList< Pair<Integer,Integer> > l){
		//TODO change to width and height
		int i = 0;
		for (Pair<Integer,Integer> p : l){
			if (p.getFirst() >= 0 && p.getFirst() < this.map.length && p.getSecond() >= 0 && p.getSecond() < this.map.length ){
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
		if( !keep_closed){
			for (Pair< Pair<Integer,Integer> , String> pos : this.doors)
				this.map[ pos.getFirst().getFirst().intValue() ][ pos.getFirst().getSecond().intValue() ] = pos.getSecond().charAt(0);
		}
	}
	
	public abstract Map nextMap(int enemies);
	
	/**
	 * @brief Picks up key from map, to be implemented by child classes
	 * @return true if hero is supposed to keep the key, or false if it aint (AKA if its a lever)
	 */
	public abstract boolean pickUpKey();
	
	/*
	public void parseMap( char[][] map ){
		this.map = new char[map.length][];
		for(int i = 0 ; i < map.length ; i++){
			for(int j = 0 ; j < map[i].length ; j++){
				this.map[i][j] = map[i][j];
			}
		}
	}
	*/
	
	
	/**
	 * @brief Gets the map
	 * @return map
	 */
	public char[][] getMap(){
		char[][] temp = new char[this.MAP_SIZE][];
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
	 * TODO Change this to width and height
	 * @brief Gets the map size
	 * @return var MAP_SIZE
	 */
	public int getMapSize(){
		return this.MAP_SIZE;
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