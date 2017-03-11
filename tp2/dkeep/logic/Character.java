package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;	

public abstract class Character {
	protected Pair<Integer,Integer> position;
	protected String representation;

	public Character(int x , int y){
		if(x >= 0 && y >= 0)
			this.position = new Pair<Integer,Integer>(x,y);
	}
	
	public String toString(){
		return this.representation;
	}
	
	public abstract Pair<Integer,Integer> moveCharacter(int MAP_SIZE);

	public boolean setPos(int x , int y , int MAP_SIZE){
		if( x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE){
			this.position = new Pair<Integer,Integer>(x,y);
			return true;
		}
		return false;
	}

	public Pair<Integer,Integer> getPos(){
		return (Pair<Integer,Integer>)this.position.clone();
	}
	
	public int getX(){
		return this.position.getFirst().intValue();
	}
	
	public int getY(){
		return this.position.getSecond().intValue();
	}
	
	public void setRepresentation(String s ){
		this.representation=s;
	}
		
	public String getRepresentation(){
		return this.representation;
	}
	
	public abstract ArrayList< Pair<Integer,Integer>> getGameOverPos(int level); 
	
	public abstract ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable();
	
}