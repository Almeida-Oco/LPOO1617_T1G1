package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;

public class SuspiciousGuard extends Guard{
	public SuspiciousGuard(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super( pos , map_size );
	}
	
	public SuspiciousGuard(Pair<Integer,Integer> map_size){
		super( Guard.movement.get( movement.size()-1) , map_size);
	}
	
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		Random rand = new Random();
		this.step = (rand.nextInt(2) == 0) ? 1 : -1;
		this.incIndex();
		super.setPos(vp);
	}
	
	public ArrayList<Pair<Integer, Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		temp.add(this.movement.get(this.index));
		return temp;
	}
	
	public ArrayList<Pair<Pair<Integer,Integer> , String > > getPrintable(boolean to_file){
		return super.getPrintable(to_file);
	}
}