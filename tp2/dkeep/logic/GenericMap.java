package dkeep.logic;

import java.util.ArrayList;

public class GenericMap extends Map {
	
	public GenericMap(char[][] map){
		super(-1,-1,map);
	}
	
	public void setDoors(ArrayList<Pair< Pair<Integer,Integer> , String > > doors){
		for (Pair< Pair<Integer,Integer> , String> p : doors)
			this.doors.add(p);
	}
	
	public void setCharacters(ArrayList<GameCharacter> chars){
		this.chars = chars;
	}
	
	@Override
	public Map nextMap(int enemies) {
		return null;
	}
	
	public void setKey(Pair<Integer,Integer> key , boolean lever){
		this.key = key;
		this.lever = lever;
	}
}
