package dkeep.logic;
import java.util.Random;

public class DrunkenGuard extends Guard{
	private boolean asleep = false;

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
				this.step-= rand.nextInt(2)*2;
			}
			else
				this.asleep = true;
		}

		return this.position;
	}

	public boolean isAsleep(){
		return this.asleep;
	}
}