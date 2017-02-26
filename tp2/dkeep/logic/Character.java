package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;

public abstract class Character {
	protected int[] position = {-1,-1};
	protected String representation;

	public String toString(){
		return this.representation;
	}
	public abstract int[] moveCharacter(int MAP_SIZE);

	public boolean setPos(int x , int y , int MAP_SIZE){
		if( x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE){
			this.position[0] = x;
			this.position[1] = y;
			return true;
		}
		return false;
	}

	public int getX(){
		return this.position[0];
	}
	public int getY(){
		return this.position[1];
	}
	
	public abstract ArrayList< Pair<int[],String> > getPrintable();
}