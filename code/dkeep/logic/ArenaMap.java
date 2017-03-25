package dkeep.logic;

import java.util.ArrayList;

/**
 * Map of the 2nd level
 * @author Joao Almeida , Jose Pedro Machado
 */
public class ArenaMap extends Map{
	
	/**
	 * Generate an ArenaMap
	 * @param ogres How many ogres to generate (see Map constructor)
	 * 		Hero default position (8,1) , door (1,0) , key (1,8)
	 */
	public ArenaMap(int ogres){
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