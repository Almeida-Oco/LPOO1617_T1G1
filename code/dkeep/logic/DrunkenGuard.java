package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to represent a DrunkenGuard, inherits from Guard
 * @author Joao Almeida , Jose Pedro Machado
 */
public class DrunkenGuard extends Guard{
	private static final long serialVersionUID = 4866561219380999834L;
	/**
	 * Used to tell whether guard is asleep or not
	 */
	private boolean asleep = false;
	
	/**
	 * Constructor with position (only used for testing)
	 * @param pos Initial Guard position
	 * @param map_size Size of the map
	 */
	public DrunkenGuard(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super(pos,map_size);
		this.asleep = false;
		this.representation = "G";
	}
	
	/**
	 * Default Constructor, initial position from movement (see Guard)
	 * @param map_size Size of map
	 */
	public DrunkenGuard(Pair<Integer,Integer> map_size){
		super( Guard.movement.get( movement.size()-1) , map_size);
	}
	
	/**
	 * If the position in the argument is the same as the current position
	 *  assume Guard is asleep
	 */
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		this.asleep = (vp.get(0).equals(this.position.get(0)));
		this.representation = (this.asleep) ? "g" : "G" ; 
		if (!this.asleep)
			this.incIndex();
			
		super.setPos(vp);
	}
	

	public ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change){
		Random rand = new Random();
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		int result = rand.nextInt(2);
		if(0 == result && !this.asleep) //Dont fall asleep | Dont wake up
			temp.add(Guard.movement.get(this.index));
		
		else if (1 == result && this.asleep){ //Fall asleep | Wake up
			this.step *= (rand.nextInt(2) == 0) ? 1 : -1;
			temp.add(Guard.movement.get(this.index));
		}

		return ((temp.size() == 0) ? this.position : temp);
	}

	public boolean isAsleep(){
		return this.asleep;
	}
	
	public String toString(){
		return (this.asleep) ? "g" : "G";
	}
	
	@Override
	public ArrayList<Pair<Integer, Integer>> getGameOverPos() {
		return (this.asleep) ? new ArrayList<Pair<Integer,Integer> >() : this.position;
	}
}