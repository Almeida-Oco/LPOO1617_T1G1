package dkeep.logic;

public class ArenaMap extends Map{
	public ArenaMap(){
		this.MAP_SIZE = 10;
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
		
		int i = 0;
		for( char[] c : temp){
			this.map[i] = c;
			i++;
		}
	}
	
	public void openDoors(){
		this.map[1][0] = 'S';
	}
	
	public Map nextMap(){
		return new ArenaMap();
	}
}