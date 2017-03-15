package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;
	
public abstract class Map {
	protected int MAP_SIZE;
	protected char[][] map;
	protected ArrayList< Pair<Pair<Integer,Integer> , String> > doors = new ArrayList< Pair< Pair<Integer,Integer> , String> >();
	protected ArrayList<Character> chars = new ArrayList<Character>();
	protected Pair<Integer,Integer> key;
	
	public Map(int guards , int ogres, char[][] map){ // 1-Rookie, 2 - Drunken, 3-Suspicious 
		Random rand = new Random();
		this.map = map;
		if (guards != -1){ //MEANING NO GUARDS TO GENERATE
			if (guards == 0) //IF GUARD IS 0 THEN RANDOMLY SELECT GUARD
				guards = rand.nextInt(3)+1;
			this.chars.add( (1 == guards) ? new RookieGuard() : ( (2 == guards) ? new DrunkenGuard() : new SuspiciousGuard()));
		}
		
		if (ogres != -1){ // MEANING NO OGRE TO GENERATE
			if (ogres == 0) //IF OGRE IS 0 THEN RANDOMLY SELECT NUMBER OF OGRES
				ogres = rand.nextInt(3) + 1;
			for (int i = 0; i < ogres; i++)
				this.chars.add(new Ogre(rand.nextInt(8) + 1, rand.nextInt(8) + 1, this.map.length , false));
		}
	};
	
	public Map(char[][] game_map){
		this.map = game_map;
		this.MAP_SIZE = game_map.length;
	}
	
	public char[][] getMap(){
		char[][] temp = new char[this.MAP_SIZE][];
		int i = 0;
		for (char[] arr : this.map){
			temp[i] = (char[])arr.clone();
			i++;
		}
		return temp;
	}

	public Pair<Integer,Integer> getKey(){
		return this.key;
	}
	
	public int getMapSize(){
		return this.MAP_SIZE;
	}

	public ArrayList<Character> getCharacters(){
		return (ArrayList<Character>)this.chars.clone();
	}
	
	public char getTile(Pair<Integer,Integer> p){
		return this.map[p.getFirst().intValue()][p.getSecond().intValue()];
	}
	
	public int isFree( ArrayList< Pair<Integer,Integer> > l){
		int i = 0;
		for (Pair<Integer,Integer> p : l){
			if (this.map[p.getFirst().intValue()][p.getSecond().intValue()] == ' ' || 
				this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'k' || 
				this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'S' || 
				this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'k' )
				i++;
			else
				return i;
		}
		
		return -1;
	}
	
	public void openDoors(){
		for (Pair< Pair<Integer,Integer> , String> pos : this.doors)
			this.map[ pos.getFirst().getFirst().intValue() ][ pos.getFirst().getSecond().intValue() ] = pos.getSecond().charAt(0);
	}
	
	public abstract Map nextMap();
	
	public abstract void pickUpKey();
	
}