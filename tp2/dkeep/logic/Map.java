package dkeep.logic;

public abstract class Map {
	protected char[][] map;

	public char[][] getMap(){
		return this.map;
	}

	public boolean isFree(int x , int y){
		return (this.map[y][x] == ' ');
	}
}