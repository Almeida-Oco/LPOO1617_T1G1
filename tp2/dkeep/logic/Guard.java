package dkeep.logic;
import java.util.ArrayList;
import java.util.Arrays;
import pair.Pair;

public class Guard extends Character{
	protected ArrayList< Pair<Integer,Integer> > movement = new ArrayList< Pair<Integer,Integer> >( 
   Arrays.asList(new Pair<Integer,Integer>(1,7),new Pair<Integer,Integer>(2,7),new Pair<Integer,Integer>(3,7),new Pair<Integer,Integer>(4,7),
				 new Pair<Integer,Integer>(5,7),new Pair<Integer,Integer>(5,6),new Pair<Integer,Integer>(5,5),new Pair<Integer,Integer>(5,4),
				 new Pair<Integer,Integer>(5,3),new Pair<Integer,Integer>(5,2),new Pair<Integer,Integer>(5,1),new Pair<Integer,Integer>(6,1),
				 new Pair<Integer,Integer>(6,2),new Pair<Integer,Integer>(6,3),new Pair<Integer,Integer>(6,4),new Pair<Integer,Integer>(6,5),
				 new Pair<Integer,Integer>(6,6),new Pair<Integer,Integer>(6,7),new Pair<Integer,Integer>(6,8),new Pair<Integer,Integer>(5,8),
				 new Pair<Integer,Integer>(4,8),new Pair<Integer,Integer>(3,8),new Pair<Integer,Integer>(2,8),new Pair<Integer,Integer>(1,8)) );
				 
	protected int index = 0;
	protected int step = 1;

	public Guard(int x , int y){
		super(x,y);
		this.representation="G";
	}

	public ArrayList< Pair<Integer,Integer> > moveCharacter(int change , int MAP_SIZE){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		temp.add((Pair<Integer,Integer>)this.movement.get(this.index).clone());
		incIndex();
		return temp;
	}

	protected void incIndex(){
		if (this.index+this.step == this.movement.size())
			this.index = 0;
		else if (this.index+this.step < 0)
			this.index = (this.movement.size()-1);
		else
			this.index+=this.step;
	}

	public ArrayList< Pair<Integer,Integer> > getGameOverPos(int level){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList< Pair<Integer,Integer> >(1);
		if (level == 0){
			temp.add(this.position.get(0));
		}
		return temp;
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
}