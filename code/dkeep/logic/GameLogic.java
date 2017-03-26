package dkeep.logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Brain of the game, all Logic is here
 * @author Joao Almeida , Jose Pedro Machado
 */
public class GameLogic implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Translates between wasd and correspondent int code
	 */
	private static final HashMap<Character,Integer> DIR = new HashMap<Character,Integer>();
	static{
		DIR.put(new Character('w') , 0);
		DIR.put(new Character('a') , 2);
		DIR.put(new Character('s') , 1);
		DIR.put(new Character('d') , 3);
	}
	/**
	 * Current game map
	 */
	private Map map;
	/**
	 * All villains of current logic
	 */
	private ArrayList<GameCharacter> villains = new ArrayList<GameCharacter>();
	/**
	 * Current hero
	 */
	private Hero hero;

	
	/**
	 * Constructor
	 * @param game_map Map to be used in the the logic
	 * @param guard Which guard to generate (see Map constructor)
	 * 			If game_map is null default first level will be used, if game_map is not null then it will use its map
	 * 			and its characters, making guard variable irrelevant
	 */
	public GameLogic(Map game_map, int guard) { 
		this.map = (game_map == null) ? new DungeonMap(guard) : game_map; //if null assume New Game
		for (GameCharacter ch : this.map.getCharacters() ) //get all characters from the map
			if (ch instanceof Hero)
				this.hero = (Hero)ch;
			else
				this.villains.add(ch);
	}

	/**
	 * Moves all villains
	 *  	   Before moving the villains it checks overlap and if the map is free in that spot.
	 * 		   if one of those conditions fail, it will recalculate the position that failed to meet that criteria
	 */
	public void moveAllVillains() {
		ArrayList< Pair<Integer,Integer> > pos = new ArrayList<Pair<Integer,Integer> >();
		for (GameCharacter ch : this.villains){
			int change = 0;
			if ( !checkStuck( ch.getPos()) )
				do{
					pos = ch.moveCharacter(pos, change);
				} while (( (change = this.map.isFree(pos)) != -1 ) );
			ch.setPos(pos);
			ch.checkKeyTriggers(this.map.getKey());
			if(ch instanceof Ogre)
				checkStuns((Ogre)ch);
		}
	}
	
	/**
	 * Moves hero in given direction
	 * @param direction Direction to move hero
	 * @return True if its supposed to go to next level, false otherwise
	 */
	public boolean moveHero(char direction) {
		ArrayList< Pair<Integer,Integer> > temp = null;  Integer dir;
		if ( (dir = GameLogic.DIR.get(new Character(direction))) != null ) //translate char to int
			temp = this.hero.moveCharacter(temp,dir);
		else
			return false;
		
		if (this.map.isFree(temp) == -1) {
			for (GameCharacter ch : getVillains() )
				if ( temp.equals(ch.getPos()) ) //If hero tried to jump on top of g just ignore it
					return false;
			this.hero.setPos(temp);
		}
		return checkHeroTriggers(temp.get(0));
	}

	/**
	 *Checks if hero got to the final stairs
	 * @return true if hero is in final stairs
	 */
	public boolean wonGame() {
		for ( Pair<Integer,Integer> p : this.hero.getPos() )
			if (this.map.getTile( p )== 'S' && this.map.nextMap(0) == null)
				return true;
				
		return false;
	}

	/**
	 * Checks if its game over
	 * @return True if it is game over, false otherwise
	 *  	   First gathers all villains game over positions then checks to see if it is in an adjacent square of any
	 * 		   If a villain is an Ogre then only check the first position(its the club), if the hero is armed
	 */
	public boolean isGameOver(){
		for (GameCharacter ch : getVillains())
			for (Pair<Integer,Integer> pos : ( (ch instanceof Ogre) ? ch.getGameOverPos().subList(0,1) : ch.getGameOverPos()) ){
				if (inAdjSquares(this.hero.getPos().get(0) , pos) )
					return true;
			}

		return false;
	}

	/**
	 * Checks whether the character can move or if it is stuck
	 * @param positions Positions of character to check
	 * @return True if he is stuck, false if not
	 */
	private boolean checkStuck(ArrayList<Pair<Integer,Integer> > positions){
		for (Pair<Integer,Integer> p : positions){
			if ( checkWallStuck(p) == 4 )
				return true;
		}
		return false;
	}
	
	/**
	 * Checks how many walls surround given position
	 * @param p Position to check 
	 * @return Number of walls surrounding position
	 */
	private int checkWallStuck( Pair<Integer,Integer> p){
			int how_many = 0;
			how_many += ( this.map.getTile( new Pair<Integer,Integer>(p.getFirst()+1,p.getSecond())) == ' ' || 
						  this.map.getTile( new Pair<Integer,Integer>(p.getFirst()+1,p.getSecond())) == 'k') ? 0 : 1;
			how_many += ( this.map.getTile( new Pair<Integer,Integer>(p.getFirst()-1,p.getSecond())) == ' ' || 
						  this.map.getTile( new Pair<Integer,Integer>(p.getFirst()-1,p.getSecond())) == 'k') ? 0 : 1;
			how_many += ( this.map.getTile( new Pair<Integer,Integer>(p.getFirst(),p.getSecond()+1)) == ' ' || 
						  this.map.getTile( new Pair<Integer,Integer>(p.getFirst(),p.getSecond()+1)) == 'k') ? 0 : 1;
			how_many += ( this.map.getTile( new Pair<Integer,Integer>(p.getFirst(),p.getSecond()-1)) == ' ' || 
						  this.map.getTile( new Pair<Integer,Integer>(p.getFirst(),p.getSecond()-1)) == 'k') ? 0 : 1;
			return how_many;
	}
	
	
	/**	
	 * Checks if p1 is in adjacent square of p2
	 * @param p1 position to check
	 * @param p2 Position to check + adjacent to these
	 * @return True if hero is in adjacent, false if not
	 */
	private boolean inAdjSquares(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2) {
		return ((p1.getFirst() == p2.getFirst() - 1 && p1.getSecond() == p2.getSecond()) || 
				(p1.getFirst() == p2.getFirst() + 1 && p1.getSecond() == p2.getSecond()) ||
				(p1.getFirst() == p2.getFirst() && p1.getSecond() == p2.getSecond() - 1) ||
				(p1.getFirst() == p2.getFirst() && p1.getSecond() == p2.getSecond() + 1) ||
				(p1.getFirst() == p2.getFirst() && p1.getSecond() == p2.getSecond()));
	}

	
	/**
	 * Checks if hero triggered something
	 * @param p Position of hero
	 * @return True if he triggered next level, false otherwise
	 */
	private boolean checkHeroTriggers( Pair<Integer,Integer> p) { 
		if (p.equals(this.map.getKey())){
			boolean b = this.map.pickUpKey();
			this.map.openDoors( b );
			this.hero.setKey( b );
		}
		else if (this.map.getTile(p) == 'S') //Next Level
			return true;
		else if ( this.map.getDoors().contains( p ) && this.hero.hasKey()) 
			this.map.openDoors( false );
		
		this.hero.checkKeyTriggers(this.map.getKey());
		return false;
	}
	
	
	
	/**
	 * Checks if the hero stunned an Ogre
	 * @param ch Ogre to check stuns
	 */
	private void checkStuns(Ogre ch){
		if (inAdjSquares(ch.getPos().get(0) , this.hero.getPos().get(0) ) )
			ch.stunOgre( Ogre.STUN_ROUNDS );
		else
			ch.roundPassed();
	}
	
	/**
	 * Returns the next level to go	
	 * @param enemies How many enemies of the next level to generate
	 * @return If current game map is DungeonMap it returns an object of ArenaMap, if current is ArenaMap it returns 
	 * 		   an object of DungeonMap (because of GameLogic(Map,int) specifics), !DO NOT USE TO CHECK GAME OVER!
	 */
	public GameLogic getNextLevel(int enemies){
		Random rand = new Random();
		return new GameLogic(this.map.nextMap(enemies) , 0); //number is irrelevant
	}
	
	/**
	 * Gets all Villains in a single container
	 * @return Array with all villains
	 */
	public ArrayList<GameCharacter> getVillains(){
		return (ArrayList<GameCharacter>)this.villains.clone();
	}
	
	
	/**
	 * Gets all Characters in a single container
	 * @return Array with all Characters
	 */
	public ArrayList<GameCharacter> getAllCharacters() {
		ArrayList<GameCharacter> temp = (ArrayList<GameCharacter>)this.villains.clone();
		if (null != this.hero)
			temp.add(0, this.hero);
		return temp;
	}
	
	/**
	 * Gets current game Map
	 * @return Current Game Map
	 */
	public Map getMap() {
		return this.map;
	}
	
	/**
	 * Gets Hero
	 * @return Hero
	 */
	public Hero getHero() {
		return this.hero;
	}
}