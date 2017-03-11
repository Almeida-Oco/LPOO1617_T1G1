package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;

public class Hero extends Character {
	private boolean has_key = false;
	private boolean is_armed=false;
	
	public Hero(int x , int y){
		super(x,y);
		representation="H";
	}
	
	public Hero(int level, boolean armed){
		super((level == 0) ? 1 : 8 ,(level == 0) ? 1 : 1 );
		this.representation = (armed == false) ? "H" : "A";
			this.is_armed=armed;
	
			
		
	}

	public String toString(){
		if (has_key)
			return "K";
		else
			return this.representation;
	}

	public Pair<Integer, Integer> moveCharacter(int MAP_SIZE){
		return new Pair<Integer,Integer>(-1,-1);
	}

	public Pair<Integer,Integer> moveCharacter(int MAP_SIZE,int dir){
		Pair<Integer,Integer> temp = (Pair<Integer,Integer>)this.position.clone();
		
		if (dir == 1 && temp.getSecond().intValue()+1 < MAP_SIZE) { //move right
			temp.setSecond(temp.getSecond().intValue()+1);
			return temp;
		}
		else if (dir == 2 && temp.getSecond().intValue()-1 >= 0) { //move left
			temp.setSecond(temp.getSecond().intValue()-1);
			return temp;
		}
		else if (dir == 3 && temp.getFirst().intValue()+1 < MAP_SIZE) { //move down
			temp.setFirst(temp.getFirst().intValue()+1);
			return temp;
		}
		else if (dir == 4 && temp.getFirst().intValue()-1 >= 0) { //move up
			temp.setFirst(temp.getFirst().intValue()-1);
			return temp;
		}

		return temp;
	}	

	public void setKey(boolean val){
		this.has_key = val;
	}

	public boolean hasKey(){
		return this.has_key;
	}
	
	public ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable(){
		ArrayList< Pair< Pair<Integer,Integer> ,String> > temp = new ArrayList< Pair< Pair<Integer,Integer> ,String> >(1);
		temp.add( new Pair< Pair<Integer,Integer > ,String>( this.position,this.representation));
		
		return temp;
	}
	
	public ArrayList<Pair<Integer,Integer> > getGameOverPos(int level){
		return new ArrayList<Pair<Integer,Integer> >();
	}

	public boolean checkArmed(){
		return this.is_armed;
	}
	
	public void setArmed(boolean armed){
		this.is_armed=armed;
	}

}