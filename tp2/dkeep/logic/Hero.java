package dkeep.logic;

public class Hero extends Character {
	private boolean has_key = false;

	public Hero(){
		this.position[0] 	= 1;
		this.position[1] 	= 1;
		this.representation = "H";
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

	public boolean moveHero(int MAP_SIZE,int dir){
		if (dir == 1 && this.position[0]+1 <= MAP_SIZE) { //move right
			this.position[0] = this.position[0]+1;
			return true;
		}
		else if (dir == 2 && this.position[0]-1 >= 0) { //move left
			this.position[0] = this.position[0]-1;
			return true;
		}
		else if (dir == 3 && this.position[1]+1 <= MAP_SIZE) { //move down
			this.position[1] = this.position[1]+1;
			return true;
		}
		else if (dir == 4 && this.position[0]-1 >= 0) { //move up
			this.position[0] = this.position[0]-1;
			return true;
		}

		return false;
	}	

	public void setKey(boolean val){
		this.has_key = val;
	}
}