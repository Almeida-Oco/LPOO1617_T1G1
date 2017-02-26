package dkeep.logic;
import java.util.ArrayList;
import pair.Pair;

public class Guard extends Character{
	protected int[][] movement =  {{1,7},{2,7},{3,7},{4,7},{5,7},{5,6},{5,5},{5,4},{5,3},{5,2},{5,1},{6,1},
								   {6,2},{6,3},{6,4},{6,5},{6,6},{6,7},{6,8},{5,8},{4,8},{3,8},{2,8},{1,8}};
	protected int index = 0;
	protected int step = 1;

	public Guard(){
		this.position[0] = 1;
		this.position[1] = 8;
		this.representation = "G";
	}

	public int[] moveCharacter(int MAP_SIZE){
		this.position[0] = this.movement[this.index][0];
		this.position[1] = this.movement[this.index][1];
		incIndex();
		return this.position;
	}

	protected void incIndex(){
		if (this.index+this.step == this.movement.length)
			this.index = 0;
		else if (this.index+this.step < 0)
			this.index = (this.movement.length-1);
		else
			this.index+=this.step;
	}

	public ArrayList<int[]> getGameOverPos(int level){
		ArrayList<int[]> temp = new ArrayList<int[]>(1);
		if (level == 0){
			temp.add((int[])this.position.clone());
		}
		return temp;
	}
	
	public ArrayList< Pair<int[],String> > getPrintable(){
		ArrayList< Pair<int[],String> > temp = new ArrayList< Pair<int[],String> >(1);
		
		int[] pos = {this.position[0],this.position[1]};
		temp.add( new Pair<int[],String>(pos,this.representation));
		
		return temp;
	}
}