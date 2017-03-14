package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;

public class DrunkenGuard extends Guard{
	private boolean asleep = false;

	public DrunkenGuard(int x, int y){
		super(x,y);
	}
	
	public DrunkenGuard(){
		super(1,8);
	}
	
	public ArrayList< Pair<Integer,Integer> > moveCharacter(int MAP_SIZE){
		Random rand = new Random();
		int result = rand.nextInt(2);
		if(0 == result){ //Dont fall asleep | Dont wake up
			if(!this.asleep){
				this.position.set(0,this.movement.get(this.index));
				incIndex();
			}
		}
		else if (1 == result){ //Fall asleep | Wake up
			if (this.asleep){
				this.asleep = false;
				this.representation = "G";
				this.step*= (rand.nextInt(2) == 0) ? 1 : -1;
				this.position.set(0,this.movement.get(this.index));
			}
			else{
				this.asleep = true;
				this.representation = "g";
			}
		}

		return (ArrayList< Pair<Integer,Integer> >)this.position.clone();
	}

	public boolean isAsleep(){
		return this.asleep;
	}

	public ArrayList< Pair<Integer,Integer> > getGameOverPos(int level){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList< Pair<Integer,Integer> >(1);
		if(!asleep && level == 0)
			temp = this.position;
		return temp;
	}
	
}