package dkeep.logic;
import pair.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
	private Map map;
	private Guard guard;
	private ArrayList<Ogre> ogres = new ArrayList<Ogre>();
	private Hero hero;
	private Pair<Integer,Integer> key;
	private int level = 0;

	public GameLogic(Map game_map, int level) { // WARNING!! ONLY FOR TESTING !!
		if (level == 0) {
			this.level = level;
			this.guard = new RookieGuard(1, 3);
			this.map = game_map;
			this.hero = new Hero(1, 1);
		} else if (level == 1) {
			this.level = 1;
			Ogre o = new Ogre(2, 2, game_map.getMapSize(), true);
			ogres.add(o);
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
			this.ogres.add(o);
		}

	}

	public GameLogic(int level, int ogre, int guard) {
		Random rand = new Random();
		this.level = level;

		if (0 == level) {
			this.map = new DungeonMap();
			this.hero = new Hero(this.level, false);
			this.key = this.map.getKey();
			if (guard == 0) //IF GUARD IS 0 THEN RANDOMLY SELECT GUARD
				guard = rand.nextInt(3)+1;
			if (1 == guard)
				this.guard = new RookieGuard();
			else if (2 == guard)
				this.guard = new DrunkenGuard();
			else if (3 == guard)
				this.guard = new SuspiciousGuard();
		} else if (1 == level) {
			this.map = new ArenaMap();
			this.hero = new Hero(this.level, true);
			this.key = this.map.getKey();
			if (ogre == 0) //IF OGRE IS 0 THEN RANDOMLY SELECT NUMBER OF OGRES
				ogre = rand.nextInt(3) + 1;
			for (int i = 0; i < ogre; i++)
				this.ogres.add(new Ogre(rand.nextInt(8) + 1, rand.nextInt(8) + 1, map.getMapSize(), false));
		
		}
	}

	public ArrayList<Character> getAllCharacters() { // gathers all characters (hero,guard,ogre) in an ArrayList
		ArrayList<Character> temp = new ArrayList<Character>();
		temp.add(this.hero);
		if (0 == this.level)
			temp.add(this.guard);
		else if (1 == this.level) {
			for (Ogre o : this.ogres)
				temp.add(o);
		}

		return temp;
	}
	
	public void moveAllVillains() { // move all villains based on current level
		Pair<Integer,Integer> pos;
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
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(-1, -1);

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

		if (checkTriggers(temp)) //IF hero is supposed to go to next level then return the next level 
			return (this.level == 0) ? new GameLogic(++this.level,0,0) : this;

		if (this.map.isFree(temp)) {
			for (Character ch : getAllCharacters() )
				if ( temp.equals(ch.getPos()) ) //If hero tried to jump on top of something just ignore it
					return this;
			this.hero.setPos(temp , this.map.getMapSize() );
		}
		if (temp.equals(this.key) && level == 1){
			hero.setRepresentation("K");
			this.map.pickUpKey();
		}

		return this;
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
		} else if (this.map.getTile(p) == 'S') {
			this.hero.setPos(p, this.map.getMapSize());
			return true;
		}

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

	
	public boolean wonGame() { // checks if hero got to the final stairs
		return (this.level == 1 && this.map.getTile( this.hero.getPos() )== 'S');
	}

	public boolean isGameOver() { // Gets all characters game over positions and checks
		for (Character ch : getAllCharacters())
			for (Pair<Integer,Integer> pos : ch.getGameOverPos(this.level))
				if (inAdjSquares(pos))
					return true;

		return false;
	}

	public Map getMap() {
		return this.map;
	}

	public ArrayList<Ogre> getOgres() {
		return this.ogres;
	}

	public int getLevel() {
		return this.level;
	}

	
	public Hero getHero() {
		return this.hero;
	}

}