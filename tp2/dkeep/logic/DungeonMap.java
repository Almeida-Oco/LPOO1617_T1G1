package dkeep.logic;

public class DungeonMap extends Map{
	public DungeonMap(char[][] temp){ //WARNING!! USE SOLELY FOR TESTING PURPOSES !!
		super(temp);
	}
	
	public DungeonMap(){
		super();
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
		this.map = new char[this.MAP_SIZE][];
		this.doors_to_open = new int[2][2];
		this.doors_to_open[0][0] = 0; this.doors_to_open[0][1] = 5; 
		this.doors_to_open[1][0] = 0; this.doors_to_open[1][1] = 6; 
		int i = 0;
		for( char[] c : temp){
			this.map[i] = c;
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
	
	public void pickUpKey(){};
}