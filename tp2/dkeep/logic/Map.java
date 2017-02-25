package dkeep.logic;

public abstract class Map {
	protected int MAP_SIZE;
	protected char[][] map;

	public char[][] getMap(){
		return this.map;
	}

	public int getMapSize(){
		return this.MAP_SIZE;
	}

	public boolean isFree(int x , int y){
		return (this.map[y][x] == ' ');
	}
}