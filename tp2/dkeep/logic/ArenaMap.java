package dkeep.logic;

public class ArenaMap extends Map{
	
	public ArenaMap(char[][] temp){ //WARNING!! USE ONLY FOR TESTING PURPOSES !!
		super(temp);
		this.doors_to_open = new int[2][2];
		this.doors_to_open[0][0] = 0; this.doors_to_open[0][1] = 3; 
		this.doors_to_open[1][0] = 0; this.doors_to_open[1][1] = 4; 
	}
	public ArenaMap(){
		super();
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
		this.map = new char[this.MAP_SIZE][];
		this.doors_to_open = new int[1][];
		this.doors_to_open[0][0] = 1; this.doors_to_open[0][1] = 0;
		int i = 0;
		for( char[] c : temp){
			this.map[i] = c;
			i++;
		}
	}
	
	public void openDoors(){
		for (int [] pos : this.doors_to_open)
			this.map[pos[0]][pos[1]] = 'S';
	}
	
	public Map nextMap(){
		return new ArenaMap();
	}

	public void pickUpKey(){
		this.map[1][8] = ' ';
	}
}