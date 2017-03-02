package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

public class DrunkenGuard extends Guard{
	private boolean asleep = false;

	public DrunkenGuard(int x, int y){
		super(x,y);
	}
	
	public DrunkenGuard(){
		super();
	}
	public int[] moveCharacter(int MAP_SIZE){
		Random rand = new Random();
		int result = rand.nextInt(2);
		if(0 == result){ //Dont fall asleep | Dont wake up
			if(!this.asleep){
				this.position = this.movement[this.index];
				incIndex();
			}
		}
		else if (1 == result){ //Fall asleep | Wake up
			if (this.asleep){
				this.asleep = false;
				this.representation = "G";
				this.step*= (rand.nextInt(2) == 0) ? 1 : -1;
				this.position = this.movement[this.index];
			}
			else{
				this.asleep = true;
				this.representation = "g";
			}
		}

		return this.position;
	}

	public boolean isAsleep(){
		return this.asleep;
	}

	public ArrayList<int[]> getGameOverPos(int level){
		ArrayList<int[]> temp = new ArrayList<int[]>(1);
		if(!asleep && level == 0)
			temp.add((int[])this.position.clone());
		return temp;
	}
	
}