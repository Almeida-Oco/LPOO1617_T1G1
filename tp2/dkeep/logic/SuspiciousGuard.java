package dkeep.logic;
import java.util.Random;

import pair.Pair;

public class SuspiciousGuard extends Guard{
	public SuspiciousGuard(int x , int y){
		super(x,y);
	}
	
	public SuspiciousGuard(){
		super();
	}

	public Pair<Integer, Integer> moveCharacter(int MAP_SIZE){
		Random rand = new Random();
		this.position = this.movement.get(this.index);
		this.step = (rand.nextInt(2) == 0) ? 1 : -1;
		incIndex();
		
		return (Pair<Integer,Integer>)this.position.clone();
	}
}