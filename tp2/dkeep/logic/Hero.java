package dkeep.logic;
import java.util.ArrayList;

public class Hero extends GameCharacter {
	private static final long serialVersionUID = -8094004902611530978L;
	private boolean has_key = false;
	private boolean is_armed=false;
	
	public Hero(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super(pos,map_size);
		representation="H";
	}
	
	@Override
	public String toString(){
		if (has_key)
			return "K";
		else
			return this.representation;
	}
	
	@Override
	public ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current, int dir){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		temp.add( changePos(this.position.get(0) , dir) );
		
		return temp;
	}	
	
	/**
	 * Sets whether hero has key or not
	 * @param val boolean to represent if hero has key
	 */
	public void setKey(boolean val){
		this.has_key = val;
	}
	
	/**
	 * Sets whether hero is armed or not
	 * @param armed boolean to represent if hero is armed
	 */
	public void setArmed(boolean armed){
		this.is_armed=armed;
		this.representation = (armed) ? "A" : "H";
	}
	
	/**
	 * @return true if hero has key, false otherwise
	 */
	public boolean hasKey(){
		return this.has_key;
	}
	
	/**
	 * @return true if hero is armed, false otherwise
	 */
	public boolean isArmed(){
		return this.is_armed;
	}
	
	@Override
	public ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable(  ){
		ArrayList< Pair< Pair<Integer,Integer> ,String> > temp = new ArrayList< Pair< Pair<Integer,Integer> ,String> >(1);
		temp.add( new Pair< Pair<Integer,Integer > ,String>( this.position.get(0) ,this.representation));
		return temp;
	}

	@Override
	public ArrayList<Pair<Integer, Integer>> getGameOverPos() {return null;}

	@Override
	public void checkKeyTriggers(Pair<Integer, Integer> pos) {
		boolean same_pos = false;
		for (Pair<Integer,Integer> p : this.position )
			same_pos = (same_pos || pos.equals(p));
		
		this.representation = (same_pos || this.has_key) ? "K" : ( (!this.is_armed && !this.has_key) ? "H" : "A" );
	}
}