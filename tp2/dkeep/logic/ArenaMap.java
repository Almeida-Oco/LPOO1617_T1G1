package dkeep.logic;

import java.util.ArrayList;
import pair.Pair;

public class ArenaMap extends Map{
	
	public ArenaMap(char[][] temp){ //WARNING!! USE ONLY FOR TESTING PURPOSES !!
		super(temp);
		this.doors = new ArrayList< Pair< Pair<Integer,Integer> , String> >();
		this.doors.add( new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(2) ,new Integer(0)) , "S"));
		this.doors.add( new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(3) ,new Integer(0)) , "S"));
	}
	
	public ArenaMap(int guards , int ogres){
		super(-1,ogres);
		this.chars.add( new Hero(8,1) );
		char[][]temp={{'X','X','X','X','X','X','X','X','X','X'} ,
					  {'I',' ',' ',' ',' ',' ',' ',' ','k','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X','X','X','X','X','X','X','X','X','X'} };
		this.MAP_SIZE = temp.length;
		this.map = new char[this.MAP_SIZE][];
		this.key = new Pair<Integer,Integer>( new Integer(1) , new Integer(8) );
		this.doors.add( new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(1) ,new Integer(0)) , "S"));
		int i = 0;
		for( char[] c : temp){
			this.map[i] = c;
			i++;
		}
	}
	
	public Map nextMap(){
		return null;
	}

	public void pickUpKey(){
		this.map[ this.key.getFirst().intValue() ][ this.key.getSecond().intValue() ] = ' ';
	}
}