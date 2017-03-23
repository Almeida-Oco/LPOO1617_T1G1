package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;

public class Hero extends GameCharacter {
	private boolean has_key = false;
	private boolean is_armed=false;
	
	public Hero(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super(pos,map_size);
		representation="H";
	}
	
	public String toString(){
		if (has_key)
			return "K";
		else
			return this.representation;
	}

	public ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int dir){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		for (Pair<Integer,Integer> p : (ArrayList< Pair<Integer,Integer> >)this.position.clone() )
			temp.add( (Pair<Integer,Integer>)p.clone());
		
		if (dir == 1)//move right
			temp.get(0).setSecond(temp.get(0).getSecond().intValue()+1);
		else if (dir == 2)//move left
			temp.get(0).setSecond(temp.get(0).getSecond().intValue()-1);
		else if (dir == 3)//move down
			temp.get(0).setFirst(temp.get(0).getFirst().intValue()+1);
		else if (dir == 4)//move up
			temp.get(0).setFirst(temp.get(0).getFirst().intValue()-1);

		return temp;
	}	

	
	public void setKey(boolean val){
		this.has_key = val;
	}

	public boolean hasKey(){
		return this.has_key;
	}
	
	public ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable( boolean to_file){
		String temp_rep = this.representation;
		ArrayList< Pair< Pair<Integer,Integer> ,String> > temp = new ArrayList< Pair< Pair<Integer,Integer> ,String> >(1);
		if( to_file ){
			this.representation = ( ( this.is_armed && this.has_key ) ? "a" :
								  ( ( !this.is_armed && !this.has_key)? "H" :
								  ( ( this.is_armed ) ? "A" : "K" )));
		}
		temp.add( new Pair< Pair<Integer,Integer > ,String>( this.position.get(0) ,this.representation));
		this.representation = temp_rep;
		return temp;
	}

	public boolean isArmed(){
		return this.is_armed;
	}
	
	public void setArmed(boolean armed){
		this.is_armed=armed;
		this.representation = (armed) ? "A" : "H";
	}

	@Override
	public ArrayList<Pair<Integer, Integer>> getGameOverPos() {return null;}

	public void updateRepresentation(boolean to_file){
		if (to_file)
			this.representation = ( ( this.is_armed && this.has_key ) ? "a" : ( ( !this.is_armed && !this.has_key)? "H" :  ( ( this.is_armed ) ? "A" : "K" )));
		else
			this.representation = ( ( !this.is_armed && !this.has_key)? "H" :  ( ( this.is_armed ) ? "A" : "K" ) );

	}
}