package dkeep.logic;
import java.util.Random;

public class SuspiciousGuard extends Guard{
	public SuspiciousGuard(){
		super();
	}

	public int[] moveCharacter(int MAP_SIZE){
		Random rand = new Random();
		this.position = this.movement[this.index];
		this.step = (rand.nextInt(2) == 0) ? 1 : -1;
		incIndex();
		
		return this.position;
	}
}