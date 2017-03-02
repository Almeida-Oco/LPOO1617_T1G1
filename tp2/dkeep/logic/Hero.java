package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;

public class Hero extends Character {
	private boolean has_key = false;
	private boolean is_armed=false;
//	private int[] club = new int[2];
//	private String club_representation = "c";
	
	public Hero(int x , int y){
		super(x,y);
	}
	
	public Hero(int level, boolean armed){
		super((level == 0) ? 1 : 8 ,(level == 0) ? 1 : 1 );
		this.representation = (armed == false) ? "H" : "A";
			this.is_armed=armed;
	
			
		
	}

	public String toString(){
		if (has_key)
			return "K";
		else
			return this.representation;
	}

	public int[] moveCharacter(int MAP_SIZE){
		int[] temp = {0,0};
		return temp;
	}

	public int[] moveCharacter(int MAP_SIZE,int dir){
		int[] temp = (int[])this.position.clone();
		if (dir == 1 && temp[1]+1 < MAP_SIZE) { //move right
			temp[1]++;
			return temp;
		}
		else if (dir == 2 && temp[1]-1 >= 0) { //move left
			temp[1]--;
			return temp;
		}
		else if (dir == 3 && temp[0]+1 < MAP_SIZE) { //move down
			temp[0]++;
			return temp;
		}
		else if (dir == 4 && temp[0]-1 >= 0) { //move up
			temp[0]--;
			return temp;
		}

		return temp;
	}	

	public void setKey(boolean val){
		this.has_key = val;
	}

	public boolean hasKey(){
		return this.has_key;
	}
	
	public int[] getPos(){
		return (int[])this.position.clone();
	}
	
	public ArrayList< Pair<int[],String> > getPrintable(){
		ArrayList< Pair<int[],String> > temp = new ArrayList< Pair<int[],String> >(1);
		
		int[] pos = {this.position[0],this.position[1]};
		temp.add( new Pair<int[],String>(pos,this.representation));
		
		return temp;
	}
	
	public ArrayList<int[]> getGameOverPos(int level){
		ArrayList<int[]> temp = new ArrayList<int[]>(0);
		return temp;
	}

	public boolean checkArmed(){
		return this.is_armed;
	}

}