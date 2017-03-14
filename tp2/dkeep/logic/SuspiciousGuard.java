package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;

public class SuspiciousGuard extends Guard{
	public SuspiciousGuard(int x , int y){
		super(x,y);
	}
	
	public SuspiciousGuard(){
		super(1,8);
	}

	public ArrayList<Pair<Integer, Integer> > moveCharacter(int MAP_SIZE){
		Random rand = new Random();
		this.position.set(0,this.movement.get(this.index));
		this.step = (rand.nextInt(2) == 0) ? 1 : -1;
		incIndex();
		
		return (ArrayList<Pair<Integer,Integer> >)this.position.clone();
	}
}