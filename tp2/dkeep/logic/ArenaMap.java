package dkeep.logic;

import java.util.ArrayList;
import pair.Pair;

public class ArenaMap extends Map{
	
	public ArenaMap(int guards , int ogres){
		super(-1,ogres,new char[][]{{'X','X','X','X','X','X','X','X','X','X'} ,
									{'I',' ',' ',' ',' ',' ',' ',' ','k','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
									{'X','X','X','X','X','X','X','X','X','X'} });
		this.chars.add(0,new Hero(new Pair<Integer,Integer>(8,1) , new Pair<Integer,Integer>(this.width,this.height) ));
		((Hero)this.chars.get(0)).setArmed(true);
		this.key = new Pair<Integer,Integer>( new Integer(1) , new Integer(8) );
		this.doors.add( new Pair< Pair<Integer,Integer> ,String>( new Pair<Integer,Integer>( new Integer(1) ,new Integer(0)) , "S"));
	}
	
	public Map nextMap(int enemies){
		return null;
	}

	public boolean pickUpKey(){
		this.map[ this.key.getFirst().intValue() ][ this.key.getSecond().intValue() ] = ' ';
		return true;
	}
}