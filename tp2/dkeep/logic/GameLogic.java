package dkeep.logic;
import pair.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
	private Map map;
	private ArrayList<Character> chars = new ArrayList<Character>();
	//private Guard guard;
	//private ArrayList<Ogre> ogres = new ArrayList<Ogre>();
	private Hero hero;
	private Pair<Integer,Integer> key;
	private int level = 0;

	public GameLogic(Map game_map, int level) { // WARNING!! ONLY FOR TESTING !!
		
		if (level == 0) {
			this.level = level;
			this.chars.add(new RookieGuard(1, 3));
			this.map = game_map;
			this.hero = new Hero(1, 1);
		} else if (level == 1) {
			this.level = 1;
			Ogre o = new Ogre(2, 2, game_map.getMapSize(), true);
			this.chars.add(o);
			this.map = game_map;
			this.hero = new Hero(1, 1);
			this.key = new Pair<Integer,Integer>(1,2);
		} else if (level == 2) {
			this.map = game_map;
			this.level = 1;
			this.hero = new Hero(1, 1);
			this.key = new Pair<Integer,Integer>(1,2);
		} else if (level == 3) {
			this.map = game_map;
			this.level = 1;
			this.hero = new Hero(1, 1);
			hero.setArmed(true);
			Ogre o = new Ogre(1, 3, game_map.getMapSize(), false);
			this.chars.add(o);
		}

	}

	public GameLogic(int level, int ogre, int guard) {
		this.level = level;
		this.map = ( (level == 0) ? new DungeonMap(guard,-1) : new ArenaMap(-1,ogre) );
		//get initial characters from map
		for (Character ch : this.map.getCharacters() )
			if (ch instanceof Hero)
				this.hero = (Hero)ch;
			else
				this.chars.add(ch);
		this.key = this.map.getKey();
	}

	public void moveAllVillains() { // move all villains based on current level
		ArrayList< Pair<Integer,Integer> > pos;
		for (Character ch : this.chars){
			do{
				pos = ch.moveCharacter(map.getMapSize());
			}
		}
		
		
		
		if (0 == this.level) { // move only guards
			do {
				pos = guard.moveCharacter(map.getMapSize());
			} while (!this.map.isFree(pos) || pos.equals(this.hero.getPos())); 
		}
		else if (1 == this.level) { // move only ogres
			for (Ogre o : this.ogres) {
				do {
					pos = o.moveCharacter(this.map.getMapSize());
				} while (!this.map.isFree(pos) || pos.equals(this.hero.getPos()));
				//After getting a valid position sets position of ogre and representation
				o.setRepresentation( (pos.equals(this.key)) ? "$" : "O");
				o.setPos(pos, this.map.getMapSize());
				
				if (this.hero.checkArmed()) 
					if (inAdjSquares(pos))
						o.stunOgre();
					else
						o.roundPassed();
				
				do {
					pos = o.moveClub(this.map.getMapSize());
				} while (!this.map.isFree(pos));
				//After getting a valid position sets position of club and representation
				o.setClubRepresentation( (pos.equals(this.key)) ? "$" : "*");
				o.setClub(pos, this.map.getMapSize());
			}
		}
	}

	public GameLogic moveHero(char direction) { // moves hero, returns an object of GameLogic, either next level or same level
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();

		if ('w' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize(), 4);
		else if ('a' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize(), 2);
		else if ('s' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize(), 3);
		else if ('d' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize(), 1);
		else
			return this;

		if (checkTriggers(temp.get(0))) //IF hero is supposed to go to next level then return the next level 
			return (this.level == 0) ? new GameLogic(++this.level,0,0) : this;
		else
			this.hero.setPos(temp, this.map.getMapSize());

		if (this.map.isFree(temp.get(0))) {
			for (Character ch : getAllCharacters() )
				if ( temp.equals(ch.getPos()) ) //If hero tried to jump on top of something just ignore it
					return this;
			this.hero.setPos(temp , this.map.getMapSize() );
		}
		if (temp.get(0).equals(this.key) && level == 1){
			hero.setRepresentation("K");
			this.map.pickUpKey();
		}

		return this;
	}
	
	public boolean wonGame() { // checks if hero got to the final stairs
		for ( Pair<Integer,Integer> p : this.hero.getPos() )
			if (this.level == 1 && this.map.getTile( p )== 'S')
				return true;
				
		return false;
	}

	public boolean isGameOver() { // Gets all characters game over positions and checks
		for (Character ch : getAllCharacters())
			for (Pair<Integer,Integer> pos : ch.getGameOverPos(this.level))
				if (inAdjSquares(pos))
					return true;

		return false;
	}

	
	private boolean checkTriggers( Pair<Integer,Integer> p) { // checks if hero is in a key/lever or entered a door/stairs
		if (p.equals(this.key) && level != 1)
			this.map.openDoors();
		else if (this.map.getTile(p) == 'I' && this.hero.hasKey()) {
			if (level == 1)
				p.setSecond(p.getSecond().intValue()+1); // stop hero from going inside stairs at first attempt
			this.map.openDoors();
		} else if (p.equals(this.key) && !this.hero.hasKey()) {
			this.hero.setKey(true);
			this.map.pickUpKey();
		} else if (this.map.getTile(p) == 'S') //Next Level
			return true;

		return false;
	}

	private boolean inAdjSquares( Pair<Integer,Integer> p) { // check if hero is in adjacent square
		if (p.getFirst().intValue() != -1 && p.getSecond().intValue() != -1)
			if ((this.hero.getX() == p.getFirst().intValue() - 1 && this.hero.getY() == p.getSecond().intValue()) || 
				(this.hero.getX() == p.getFirst().intValue() + 1 && this.hero.getY() == p.getSecond().intValue()) ||
				(this.hero.getX() == p.getFirst().intValue() && this.hero.getY() == p.getSecond().intValue() - 1) ||
				(this.hero.getX() == p.getFirst().intValue() && this.hero.getY() == p.getSecond().intValue() + 1) ||
				(this.hero.getX() == p.getFirst().intValue() && this.hero.getY() == p.getSecond().intValue()))
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
		for ( Pair<Integer,Integer> p_l : l){
			for (Character ch : this.chars ){
				for (Pair<Integer,Integer> p_ch : ch.getPos() )
					if (p_ch.equals(p_l))
						return i;
			}
			i++;
		}
		
		return -1;
	}
	
	/**
	 * @brief Gets all Villains in a single container
	 * @return Array with all villains
	 */
	public ArrayList<Character> getVillains(){
		return (ArrayList<Character>)this.chars.clone();
	}
	/**
	 * @brief Gets all Characters in a single container
	 * @return Array with all Characters
	 */
	public ArrayList<Character> getAllCharacters() { // gathers all characters (hero,guard,ogre) in an ArrayList
		ArrayList<Character> temp = (ArrayList<Character>)this.chars.clone();
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