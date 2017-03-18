package dkeep.logic;

import java.util.ArrayList;

import pair.Pair;

public class TestMap extends Map {
	private char pick_key = ' ';
	
	public TestMap(char[][] map){
		super(-1,-1,map);
	}
	
	public void setDoors(ArrayList<Pair< Pair<Integer,Integer> , String > > doors){
		for (Pair< Pair<Integer,Integer> , String> p : doors)
			this.doors.add(p);
	}
	
	public void setCharacters(ArrayList<GameCharacter> chars){
		this.chars = chars;
	}
	
	public void setKey(Pair<Integer,Integer> key , char rep){
		this.key = key;
		this.pick_key = rep;
	}
	
	@Override
	public Map nextMap(int enemies) {
		return null;
	}
	
	@Override
	public boolean pickUpKey() {
		this.map[ this.key.getFirst() ][ this.key.getSecond() ] = 'k';
		return false;
	}

}
