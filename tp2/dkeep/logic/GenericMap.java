package dkeep.logic;

import java.util.ArrayList;

public class GenericMap extends Map {
	
	/**
	 * Constructor of a default map, no enemies generated
	 * @param map Map to be used
	 */
	public GenericMap(char[][] map){
		super(-1,-1,map);
	}
	
	/**
	 * Sets the array of doors
	 * @param doors Doors to use
	 */
	public void setDoors(ArrayList<Pair< Pair<Integer,Integer> , String > > doors){
		for (Pair< Pair<Integer,Integer> , String> p : doors)
			this.doors.add(p);
	}
	
	/**
	 * Sets the GameCharacters of this map, will override current GameCharacters
	 * @param chars Array with the characters to be used
	 */
	public void setCharacters(ArrayList<GameCharacter> chars){
		this.chars = chars;
	}
	
	/**
	 * Sets the key of this map
	 * @param key Position of key
	 * @param lever Whether its a lever or a key
	 */
	public void setKey(Pair<Integer,Integer> key , boolean lever){
		this.key = key;
		this.lever = lever;
	}
	
	@Override
	public Map nextMap(int enemies) {
		return null;
	}
	
	
}
