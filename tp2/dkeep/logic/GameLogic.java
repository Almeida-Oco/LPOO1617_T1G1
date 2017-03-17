package dkeep.logic;
import pair.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
	private Map map;
	private ArrayList<GameCharacter> villains = new ArrayList<GameCharacter>();
	private Hero hero;
	private Pair<Integer,Integer> key;
	private int level = 0;
	
	/**
	 * @brief Constructor !!WARNING!! ONLY FOR TESTING !!
	 * @param game_map Object of game map to use
	 * @param level what hipothetical level it is
	 */
	public GameLogic(Map game_map, int level) { // WARNING!! ONLY FOR TESTING !!
		
		if (level == 0) {
			this.level = level;
			this.villains.add( new RookieGuard(1, 3) );
			this.map = game_map;
			this.hero = new Hero(1, 1);
		} else if (level == 1) {
			this.level = 1;
			this.villains.add( new Ogre(2, 2, game_map.getMapSize(), true));
			this.map = game_map;
			this.hero = new Hero(1, 1);
			this.key = new Pair<Integer,Integer>(1,2);
		} else if (level == 2) {
			this.map = game_map;
			this.level = 0;
			this.hero = new Hero(1, 1);
			this.key = new Pair<Integer,Integer>(3,1);
		} else if (level == 3) {
			this.map = game_map;
			this.level = 1;
			this.hero = new Hero(1, 1);
			hero.setArmed(true);
			Ogre o = new Ogre(1, 3, game_map.getMapSize(), false);
			this.villains.add(o);
		}

	}
	
	/**
	 * @brief Constructor
	 * @param level Current Level
	 * @param ogre How many ogres 
	 * @param guard How many guards
	 */
	public GameLogic(int level, int ogre, int guard) {
		this.level = level;
		this.map = ( (level == 0) ? new DungeonMap(guard,-1) : new ArenaMap(-1,ogre) );
		//get initial characters from map
		for (GameCharacter ch : this.map.getCharacters() )
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
			int change1 = 0, change2 = 0;
			do{
 				pos = ch.moveCharacter(pos, ( (change1 == -1 || (change2 > change1 && change2 != -1)) ? change2 : change1) , map.getMapSize());
			} while ( ( (change1 = this.checkOverlap(pos)) != -1) || ( (change2 = this.map.isFree(pos)) != -1));
			ch.setPos(pos, this.map.getMapSize());
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

		if ('w' == direction)
			temp = this.hero.moveCharacter(temp,this.map.getMapSize(), 4);
		else if ('a' == direction)
			temp = this.hero.moveCharacter(temp,this.map.getMapSize(), 2);
		else if ('s' == direction)
			temp = this.hero.moveCharacter(temp,this.map.getMapSize(), 3);
		else if ('d' == direction)
			temp = this.hero.moveCharacter(temp,this.map.getMapSize(), 1);
		else
			return false;

		if (checkTriggers(temp.get(0))){//IF hero is supposed to go to next level then return true
			this.hero.setPos(temp , this.map.getMapSize() );
			return true;
		}
		
		if (this.map.isFree(temp) == -1) {
			for (GameCharacter ch : getVillains() )
				if ( temp.equals(ch.getPos()) ) //If hero tried to jump on top of something just ignore it
					return false;
			this.hero.setPos(temp , this.map.getMapSize() );
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean wonGame() { // checks if hero got to the final stairs
		for ( Pair<Integer,Integer> p : this.hero.getPos() )
			if (this.level == 1 && this.map.getTile( p )== 'S')
				return true;
				
		return false;
	}

	/**
	 * @brief Checks if its game over
	 * @return True if it is game over, false otherwise
	 */
	public boolean isGameOver() { // Gets all characters game over positions and checks
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
		if (p.equals(this.key) && level != 1){
			this.map.openDoors();
			hero.setRepresentation("K");
		}
		else if (this.map.getTile(p) == 'I' && this.hero.hasKey()) {
			if (level == 1)
				p.setSecond(p.getSecond().intValue()+1); // stop hero from going inside stairs at first attempt
			this.map.openDoors();
		} else if (p.equals(this.key) && !this.hero.hasKey()) {
			hero.setRepresentation("K");
			this.hero.setKey(true);
			this.map.pickUpKey();
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
	
		
	public GameLogic getNextLevel(int guards,int ogres){
		Random rand = new Random();
		return new GameLogic(++this.level, (ogres == -1) ? rand.nextInt(3)+1 : ogres , -1 );
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
	 * @brief Gets current level
	 * @return Current level
	 */
	public int getLevel() {
		return this.level;
	}
	/**
	 * @brief Gets Hero
	 * @return Hero
	 */
	public Hero getHero() {
		return this.hero;
	}

}