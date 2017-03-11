package dkeep.logic;
import java.util.ArrayList;

import pair.Pair;
	
public abstract class Map {
	protected int MAP_SIZE;
	protected char[][] map;
	protected ArrayList< Pair<Pair<Integer,Integer> , String> > doors = new ArrayList< Pair< Pair<Integer,Integer> , String> >();
	protected Pair<Integer,Integer> key;
	
	public Map(){};
	
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

	public char getTile(Pair<Integer,Integer> p){
		return this.map[p.getFirst().intValue()][p.getSecond().intValue()];
	}
	
	public boolean isFree( Pair<Integer,Integer> p){
		return (this.map[p.getFirst().intValue()][p.getSecond().intValue()] == ' ' || 
				this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'K' || 
				this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'S' || 
				this.map[p.getFirst().intValue()][p.getSecond().intValue()] == 'k' );
	}
	
	public void openDoors(){
		for (Pair< Pair<Integer,Integer> , String> pos : this.doors)
			this.map[ pos.getFirst().getFirst().intValue() ][ pos.getFirst().getSecond().intValue() ] = pos.getSecond().charAt(0);
	}
	
	public abstract Map nextMap();
	
	public abstract void pickUpKey();
	
}