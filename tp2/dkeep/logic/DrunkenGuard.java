package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

public class DrunkenGuard extends Guard{
	private boolean asleep = false;
	
	public DrunkenGuard(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size, boolean is_asleep){
		super(pos,map_size);
		this.asleep = is_asleep;
		this.representation = (this.asleep) ? "g" : "G";
	}

	public DrunkenGuard(Pair<Integer,Integer> map_size){
		super( Guard.movement.get( movement.size()-1) , map_size);
	}
	
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		this.asleep = (vp.get(0).equals(this.position.get(0))); //if the position wanting to set is same as previous then guard is asleep
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

	public ArrayList<Pair<Pair<Integer,Integer> , String > > getPrintable(boolean to_file ){
		String t = this.representation;
		if (to_file)
			this.representation = (this.asleep) ? "g" : "D";
		ArrayList<Pair<Pair<Integer,Integer> , String > > temp = super.getPrintable(to_file);
		this.representation = t;
		return temp;
	}
}