package dkeep.logic;


public class DungeonMap extends Map{
	public DungeonMap(){
		this.MAP_SIZE = 10;
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
		int i = 0;
		for( char[] c : temp){
			this.map[i] = (char[])c.clone();
			i++;
		}
		
	}
	
	public void openDoors(){
		this.map[5][0] = 'S';
		this.map[6][0] = 'S';
	}
	
	public Map nextMap(){
		return new ArenaMap();
	}
}