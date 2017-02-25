package dkeep.logic;

public class ArenaMap extends Map{
	public ArenaMap(){
		this.MAP_SIZE = 8;
		char[][]temp={{'X','X','X','X','X','X','X','X','X','X'} ,
					  {'I',' ',' ',' ',' ',' ',' ',' ','K','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X','X','X','X','X','X','X','X','X','X'} };
		
		System.arraycopy(temp, 0, this.map, 0, temp.length);
	}
}