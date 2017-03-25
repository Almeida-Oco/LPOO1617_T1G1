package dkeep.logic;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Base class for the guards, stores their movement
 * @author Joao Almeida , Jose Pedro Machado
 */
public class Guard extends GameCharacter{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Positions of movement of a guard
	 */
	protected static ArrayList< Pair<Integer,Integer> > movement = new ArrayList< Pair<Integer,Integer> >( 
   Arrays.asList(new Pair<Integer,Integer>(1,7),new Pair<Integer,Integer>(2,7),new Pair<Integer,Integer>(3,7),new Pair<Integer,Integer>(4,7),
				 new Pair<Integer,Integer>(5,7),new Pair<Integer,Integer>(5,6),new Pair<Integer,Integer>(5,5),new Pair<Integer,Integer>(5,4),
				 new Pair<Integer,Integer>(5,3),new Pair<Integer,Integer>(5,2),new Pair<Integer,Integer>(5,1),new Pair<Integer,Integer>(6,1),
				 new Pair<Integer,Integer>(6,2),new Pair<Integer,Integer>(6,3),new Pair<Integer,Integer>(6,4),new Pair<Integer,Integer>(6,5),
				 new Pair<Integer,Integer>(6,6),new Pair<Integer,Integer>(6,7),new Pair<Integer,Integer>(6,8),new Pair<Integer,Integer>(5,8),
				 new Pair<Integer,Integer>(4,8),new Pair<Integer,Integer>(3,8),new Pair<Integer,Integer>(2,8),new Pair<Integer,Integer>(1,8)) );
	/**
	 * Current position in the movement array	 
	 */
	protected int index = 0;
	/**
	 * Increment/Decrement of index
	 */
	protected int step = 1;
	
	/**
	 * Constructor of Guard
	 * @param pos Initial position
	 * @param map_size Size of the map
	 */
	public Guard(Pair<Integer,Integer> pos, Pair<Integer,Integer> map_size){
		super(pos,map_size);
		this.representation = "G";
	}
	
	/**
	 * Default moveCharacter of Guard, gets next pos from movement array
	 */
	public ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		temp.add((Pair<Integer,Integer>)this.movement.get(this.index).clone());
		incIndex();
		return temp;
	}
	
	/**
	 * Increments the guard movement index
	 */
	protected void incIndex(){
		if (this.index+this.step == movement.size())
			this.index = 0;
		else if (this.index+this.step < 0)
			this.index = (movement.size()-1);
		else
			this.index+=this.step;
	}
	
	public ArrayList< Pair<Pair<Integer,Integer> , String> > getPrintable(){
		ArrayList< Pair< Pair<Integer,Integer> ,String> > temp = new ArrayList< Pair< Pair<Integer,Integer> ,String> >(1);
		for (Pair<Integer,Integer> p : this.position )
			temp.add( new Pair< Pair<Integer,Integer> ,String>(p,this.representation));
		
		return temp;
	}

	@Override
	public ArrayList<Pair<Integer, Integer>> getGameOverPos() {
		return this.position;
	}

	@Override
	public void checkKeyTriggers(Pair<Integer, Integer> pos) {}
}