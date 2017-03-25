package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

/**
 * Base class of a game map, if a new map is to be added it must inherit from this class
 * @author Joao Almeida , Jose Pedro Machado
 */
public abstract class Map implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Width of the map
	 */
	protected int width;
	/**
	 * Height of the map
	 */
	protected int height;
	/**
	 * Map
	 */
	protected char[][] map;
	/**
	 * Whether the key is a lever or a key
	 */
	protected boolean lever = false;
	/**
	 * Position of doors and representation once open
	 */
	protected ArrayList< Pair<Pair<Integer,Integer> , String> > doors = new ArrayList< Pair< Pair<Integer,Integer> , String> >();
	/**
	 * Initial GameCharacters of the map
	 */
	protected ArrayList<GameCharacter> chars = new ArrayList<GameCharacter>();
	/**
	 * Position of the key
	 */
	protected Pair<Integer,Integer> key;
	
	/**
	 * Constructor
	 * @param guards -1 - No Guards , 0 - RandomGuard , 1 - RookieGuard , 2 - DrunkenGuard , 3 - SuspiciousGuard 
	 * @param ogres  -1 - No Ogres , 0 - Generate [1,5] Ogres , 1 - Generate 1 Ogre, 2 - Generate 2 Ogres ...
	 * @param map Map to use for the specified level
	 */
	public Map(int guards , int ogres, char[][] map){ // 1-Rookie, 2 - Drunken, 3-Suspicious 
		if(map != null){
			Random rand = new Random();
			this.map = map; this.height = map.length; this.width = map[0].length;
			Pair<Integer,Integer> map_size = new Pair<Integer,Integer>(this.width,this.height);
			if (guards != -1)
				this.chars.add( this.generateGuard(guards, map_size));

			if (ogres != -1)
				this.chars.addAll( this.generateNOgres(ogres, map_size));
		}
	}

	/**
	 * Checks if the positions passed are free
	 * @param l Array of positions to check 
	 * @return -1 if all positions are free, else number that is the position of the array which has the not free pposition
	 * 			Before it checks if the tile is free it checks if the coordinates are valid.
	 * 		 	A tile is considered free if it is either a ' ', 'S' or a 'k'. Anything else is considered occupied.
	 */
	public int isFree( ArrayList< Pair<Integer,Integer> > l){
		int i = 0;
		for (Pair<Integer,Integer> p : l){
			if (p.getFirst() >= 0 && p.getFirst() < this.height && p.getSecond() >= 0 && p.getSecond() < this.width ){
				if (this.map[p.getFirst().intValue()][p.getSecond().intValue()] == ' ' || this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'S' || this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'k' )
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
	 * Opens the doors of the map
	 * @param keep_closed Whether to keep the doors closed or not
	 * 			If the map has a lever keep_closed should be false, if the map has a key which the hero picks up then
	 * 			keep_closed should be true
	 */
	public void openDoors( boolean keep_closed){
		if( !keep_closed){
			for (Pair< Pair<Integer,Integer> , String> pos : this.doors)
				this.map[ pos.getFirst().getFirst().intValue() ][ pos.getFirst().getSecond().intValue() ] = pos.getSecond().charAt(0);
		}
	}
	
	
	/**
	 * Returns next map
	 * @param enemies How many enemies to generate
	 * @return Object of the next map class, null if last level
	 */
	public abstract Map nextMap(int enemies);
	
	/**
	 * Picks up key from map, to be implemented by child classes
	 * @return true if hero is supposed to keep the key, or false if it aint (AKA if its a lever)
	 */
	public boolean pickUpKey() {
		this.map[ this.key.getFirst() ][ this.key.getSecond() ] = (lever) ? this.map[ this.key.getFirst() ][ this.key.getSecond() ] : ' ';
		return !this.lever;
	}
	
	/**
	 * Gets the map doors (only those openable)
	 * @return Array with position of openable doors
	 */
	public ArrayList<Pair<Integer,Integer> > getDoors(){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		for (Pair<Pair<Integer,Integer> , String > p : this.doors )
			temp.add(p.getFirst());
		
		return temp;
	}
	
	
	/**
	 * Gets the map
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
	 * Gets parameter lever
	 * @return lever
	 */
	public boolean getLever(){
		return this.lever;
	}
	
	/**
	 * Gets the key 
	 * @return The position of the key
	 */
	public Pair<Integer,Integer> getKey(){
		return this.key;
	}
	

	/**
	 * Gets the heigth of this map
	 * @return Heigth of the map
	 */
	public int getHeight(){
		return this.height;
	}
	
	
	/**
	 * Gets the width of this map
	 * @return Width of the map
	 */
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Size of the map
	 * @return Pair with (width,height) of the current map
	 */
	public Pair<Integer,Integer> getSize(){
		return new Pair<Integer,Integer>(this.width,this.height);
	}
	
	
	/**
	 * Gets all characters of the map
	 * @return Array with all map characters(including hero)
	 */
	public ArrayList<GameCharacter> getCharacters(){
		return (ArrayList<GameCharacter>)this.chars.clone();
	}
	
	
	/**
	 * Gets a specific tile of the map
	 * @param p Tile to get
	 * @return What is in the specified tile
	 */
	public char getTile(Pair<Integer,Integer> p){
		return this.map[p.getFirst().intValue()][p.getSecond().intValue()];
	}
	
	/**
	 * Generate specified Guard 
	 * @param typeof Type of Guard (see Map constructor)
	 * @param map_size Size of the map
	 * @return Guard generated
	 */
	private GameCharacter generateGuard( int typeof , Pair<Integer,Integer> map_size ){
		Random rand = new Random();
		if (typeof == 0) //IF GUARD IS 0 THEN RANDOMLY SELECT GUARD
			typeof = rand.nextInt(3)+1;
		return ( (1 == typeof) ? new RookieGuard(map_size) : ( (2 == typeof) ? new DrunkenGuard(map_size) : new SuspiciousGuard(map_size)));
	}
	
	/**
	 * Generates specified Ogres
	 * @param how_many How many ogres to generate (see Map constructor)
	 * @param map_size Size of the map
	 * @return Array with all Ogres generated
	 */
	private ArrayList<GameCharacter> generateNOgres( int how_many , Pair<Integer,Integer> map_size){
		Random rand = new Random();
		ArrayList<GameCharacter> temp = new ArrayList<GameCharacter>();
		if (how_many == 0) //IF OGRE IS 0 THEN RANDOMLY SELECT NUMBER OF OGRES
			how_many = rand.nextInt(5) + 1;
		for (int i = 0; i < how_many; i++)
			temp.add(new Ogre(new Pair<Integer,Integer>(rand.nextInt(8) + 1, rand.nextInt(8) + 1) , map_size ));

		return temp;
	}
}