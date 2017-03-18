package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;

public class SuspiciousGuard extends Guard{
	public SuspiciousGuard(int x , int y){
		super(new Pair<Integer,Integer>(x,y) );
	}
	
	public SuspiciousGuard(){
		super( Guard.movement.get( movement.size()-1));
	}
	
	public boolean setPos(ArrayList<Pair<Integer,Integer> > vp , int MAP_SIZE){
		Random rand = new Random();
		this.step = (rand.nextInt(2) == 0) ? 1 : -1;
		this.incIndex();
		return super.setPos(vp, MAP_SIZE);
	}
	
	public ArrayList<Pair<Integer, Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change , int MAP_SIZE){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		temp.add(this.movement.get(this.index));
		return temp;
	}
}