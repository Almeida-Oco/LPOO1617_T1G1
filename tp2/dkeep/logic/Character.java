package dkeep.logic;


public abstract class Character {
	protected int[] position = {-1,-1};
	protected String representation;

	public String toString(){
		return this.representation;
	}
	public abstract int[] moveCharacter(int MAP_SIZE);

	public boolean setPos(int x , int y , int MAP_SIZE){
		if( x >= 0 && x <= 8 && y >= 0 && y <= 8){
			this.position[0] = y;
			this.position[1] = x;
			return true;
		}
		return false;
	}

	public int getX(){
		return this.position[1];
	}
	public int getY(){
		return this.position[0];
	}
}