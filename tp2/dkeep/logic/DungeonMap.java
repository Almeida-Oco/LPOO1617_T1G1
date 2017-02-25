package dkeep.logic;


public class DungeonMap extends Map{
	public DungeonMap(){
		this.MAP_SIZE = 8;
		char[][]temp={{'X','X','X','X','X','X','X','X','X','X'} ,
					  {'X',' ',' ',' ','I',' ','X',' ',' ','X'} ,
					  {'X','X','X',' ','X','X','X',' ',' ','X'} ,
					  {'X',' ','I',' ','I',' ','X',' ',' ','X'} ,
					  {'X','X','X',' ','X','X','X',' ',' ','X'} ,
					  {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					  {'X','X','X',' ','X','X','X','X',' ','X'} ,
					  {'X',' ','I',' ','I',' ','X','K',' ','X'} ,
					  {'X','X','X','X','X','X','X','X','X','X'} };
		System.arraycopy(temp, 0, this.map, 0, temp.length);
		
	}

	public Map nextMap(){
		return new ArenaMap();
	}
}