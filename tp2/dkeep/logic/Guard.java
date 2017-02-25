package dkeep.logic;

public class Guard extends Character{
	protected int[][] movement =  {{1,7},{2,7},{3,7},{4,7},{5,7},{5,6},{5,5},{5,4},{5,3},{5,2},{5,1},{6,1},
								 {6,2},{6,3},{6,4},{6,5},{6,6},{6,7},{6,8},{5,8},{4,8},{3,8},{2,8},{1,8}};
	protected int index = 0;
	protected int step = 1;

	public Guard(){
		this.position[0] = 8;
		this.position[1] = 1;
		this.representation = "G";
	}

	public int[] moveCharacter(int MAP_SIZE){
		this.position = this.movement[index];
		incIndex();
		return this.position;
	}

	protected void incIndex(){
		if (this.index == (this.movement.length-1) && this.step == 1)
			this.index = 0;
		else if (this.index == 0 && this.step == -1)
			this.index = (this.movement.length-1);
		else
			this.index+=this.step;
	}
}