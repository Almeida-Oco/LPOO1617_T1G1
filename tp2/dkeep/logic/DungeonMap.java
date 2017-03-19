package dkeep.logic;

import java.util.Random;

import pair.Pair;

public class DungeonMap extends Map{
	
	public DungeonMap(int guards , int ogres){
		super(guards,-1, new char[][]{{'X','X','X','X','X','X','X','X','X','X'} ,
			  						  {'X',' ',' ',' ','I',' ','X',' ',' ','X'} ,
			  						  {'X','X','X',' ','X','X','X',' ',' ','X'} ,
			  						  {'X',' ','I',' ','I',' ','X',' ',' ','X'} ,
			  						  {'X','X','X',' ','X','X','X',' ',' ','X'} ,
			  						  {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
			  						  {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
			  						  {'X','X','X',' ','X','X','X','X',' ','X'} ,
			  						  {'X',' ','I',' ','I',' ','X','k',' ','X'} ,
			  						  {'X','X','X','X','X','X','X','X','X','X'} });
		
		this.chars.add(new Hero( new Pair<Integer,Integer>(1,1) , new Pair<Integer,Integer>(this.width , this.height) ));
		this.key = new Pair<Integer,Integer>(8,7);
		this.doors.add( new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(5) ,new Integer(0)) , new String("S")));
		this.doors.add( new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(6) ,new Integer(0)) , new String("S")));
		
	}
	
	public Map nextMap(int enemies){
		Random rand = new Random();
		return new ArenaMap(-1 , (enemies == 0) ? rand.nextInt(3)+1 : enemies);
	}
	
	public boolean pickUpKey(){
		return false;
	}
}