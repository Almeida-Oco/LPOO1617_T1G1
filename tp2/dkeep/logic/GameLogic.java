package dkeep.logic;
import pair.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class GameLogic {
	private static final HashMap<Character,Integer> DIR = new HashMap<Character,Integer>();
	static{
		DIR.put(new Character('w') , 4);
		DIR.put(new Character('a') , 2);
		DIR.put(new Character('s') , 3);
		DIR.put(new Character('d') , 1);
	}
	
	private Map map;
	private ArrayList<GameCharacter> villains = new ArrayList<GameCharacter>();
	private Hero hero;
	private Pair<Integer,Integer> key;

	
	/**
	 * @brief Constructor
	 * @param level Current Level
	 * @param ogre How many ogres to generate (see Map constructor for specifics)
	 * @param guard Which guard to generate (see Map constructor for specifics)
	 * @details If game_map is null default first level will be used, if game_map is not null then it will use its map
	 * 			and its characters, making guard variable irrelevant
	 */
	public GameLogic(Map game_map, int guard) {
		this.map = (game_map == null) ? new DungeonMap(guard,-1) : game_map; //if null assume New Game
		for (GameCharacter ch : this.map.getCharacters() ) //get all characters from the map
			if (ch instanceof Hero)
				this.hero = (Hero)ch;
			else
				this.villains.add(ch);
		
		this.key = this.map.getKey();
	}

	/**
	 * @brief Moves all villains
	 * @detail Before moving the villains it checks overlap and if the map is free in that spot.
	 * 		   if one of those conditions fail, it will recalculate the position that failed to meet that criteria
	 */
	public void moveAllVillains() {
		ArrayList< Pair<Integer,Integer> > pos = new ArrayList<Pair<Integer,Integer> >();
		for (GameCharacter ch : this.villains){
			int change = 0;
			do{
 				pos = ch.moveCharacter(pos, change);
			} while ( ( (change = this.checkOverlap(pos)) != -1) || ( (change = this.map.isFree(pos)) != -1 ) );
			ch.setPos(pos);
			if(ch instanceof Ogre)
				checkStuns((Ogre)ch);
		}
	}
	
	/**
	 * @brief Moves hero in given direction
	 * @param direction Direction to move hero
	 * @return Next level GameLogic object, or this object otherwise
	 */
	public boolean moveHero(char direction) {
		ArrayList< Pair<Integer,Integer> > temp = null;
		Integer dir;
		if ( (dir = GameLogic.DIR.get(new Character(direction))) != null ) //translate char to int
			temp = this.hero.moveCharacter(temp,dir);
		else
			return false;

		if (checkTriggers(temp.get(0))){//IF hero is supposed to go to next level then return true
			this.hero.setPos(temp);
			return true;
		}
		
		if (this.map.isFree(temp) == -1) {
			for (GameCharacter ch : getVillains() )
				if ( temp.equals(ch.getPos()) ) //If hero tried to jump on top of something just ignore it
					return false;
			this.hero.setPos(temp);
		}

		return false;
	}

	/**
	 * @brief Checks if hero got to the final stairs
	 * @return true if hero is in final stairs
	 */
	public boolean wonGame() {
		for ( Pair<Integer,Integer> p : this.hero.getPos() )
			if (this.map.getTile( p )== 'S' && this.map.nextMap(0) == null)
				return true;
				
		return false;
	}

	/**
	 * @brief Checks if its game over
	 * @return True if it is game over, false otherwise
	 * @details First gathers all villains game over positions then checks to see if it is in an adjacent square of any
	 */
	public boolean isGameOver() { 
		for (GameCharacter ch : getVillains())
			for (Pair<Integer,Integer> pos : ch.getGameOverPos())
				if (inAdjSquares(this.hero.getPos().get(0) , pos))
					return true;

		return false;
	}

	
	
	/**	
	 * @brief Checks if p1 is in adjacent square of p2
	 * @param p Square to check
	 * @return True if hero is in adjacent, false if not
	 */
	private boolean inAdjSquares(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2) {
		if (p2.getFirst().intValue() != -1 && p2.getSecond().intValue() != -1)
			if ((p1.getFirst().intValue() == p2.getFirst().intValue() - 1 && p1.getSecond().intValue() == p2.getSecond().intValue()) || 
				(p1.getFirst().intValue() == p2.getFirst().intValue() + 1 && p1.getSecond().intValue() == p2.getSecond().intValue()) ||
				(p1.getFirst().intValue() == p2.getFirst().intValue() && p1.getSecond().intValue() == p2.getSecond().intValue() - 1) ||
				(p1.getFirst().intValue() == p2.getFirst().intValue() && p1.getSecond().intValue() == p2.getSecond().intValue() + 1) ||
				(p1.getFirst().intValue() == p2.getFirst().intValue() && p1.getSecond().intValue() == p2.getSecond().intValue()))
				return true;

		return false;
	}
	
	/**
	 * @brief Checks if positions passed overlap with another character
	 * @param l Array of Positions to check overlap
	 * @return Position of array with overlap or -1 if no position overlaps
	 */
	private int checkOverlap( ArrayList< Pair<Integer,Integer> > l){
		int i = 0;
		boolean found_same = false;
		for ( Pair<Integer,Integer> p_l : l){
			for (GameCharacter ch : this.villains ){
				for (Pair<Integer,Integer> p_ch : ch.getPos() )
					if (p_ch.equals(p_l))
						if (!found_same)
							found_same = true;
						else
							return i;
							
			}
			i++;
		}
		
		return -1;
	}
	
	/**
	 * @brief Checks if hero triggered something
	 * @param p Position of hero
	 * @return True if he triggered next level, false otherwise
	 */
	private boolean checkTriggers( Pair<Integer,Integer> p) { 
		if (p.equals(this.key)){
			boolean b = this.map.pickUpKey();
			this.map.openDoors( b );
			this.hero.setKey( b );
			hero.setRepresentation("K");
		}
		else if (this.map.getTile(p) == 'I' && this.hero.hasKey()) {
			p.setSecond(p.getSecond().intValue()+1); // stop hero from going inside stairs at first attempt
			this.map.openDoors( false );
		} else if (this.map.getTile(p) == 'S') //Next Level
			return true;
		else if (!this.hero.hasKey())
			this.hero.setRepresentation("H");

		return false;
	}
	
	/**
	 * @brief Checks if the hero stunned an Ogre
	 */
	private void checkStuns(Ogre ch){
		if (inAdjSquares(ch.getPos().get(0) , this.hero.getPos().get(0) ) )
			ch.stunOgre();
		else
			ch.roundPassed();

	}
	
	/**
	 * @brief Returns the next level to go	
	 * @param enemies How many enemies of the next level to generate
	 * @return If current game map is DungeonMap it returns an object of ArenaMap, if current is ArenaMap it returns 
	 * 		   an object of DungeonMap (because of GameLogic(Map,int) specifics), !DO NOT USE TO CHECK GAME OVER!
	 */
	public GameLogic getNextLevel(int enemies){
		Random rand = new Random();
		return new GameLogic(this.map.nextMap(enemies) , 0); //number is irrelevant
	}
	
	/**
	 * @brief Gets all Villains in a single container
	 * @return Array with all villains
	 */
	public ArrayList<GameCharacter> getVillains(){
		return (ArrayList<GameCharacter>)this.villains.clone();
	}
	
	/**
	 * @brief Gets all Characters in a single container
	 * @return Array with all Characters
	 */
	public ArrayList<GameCharacter> getAllCharacters() {
		ArrayList<GameCharacter> temp = (ArrayList<GameCharacter>)this.villains.clone();
		temp.add(0, this.hero);
		return temp;
	}
	
	/**
	 * @brief Gets current game Map
	 * @return Current Game Map
	 */
	public Map getMap() {
		return this.map;
	}
	
	/**
	 * @brief Gets Hero
	 * @return Hero
	 */
	public Hero getHero() {
		return this.hero;
	}

}