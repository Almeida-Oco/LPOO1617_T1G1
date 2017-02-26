package dkeep.logic;
import java.util.Random;

public class Ogre extends Character {
	private int[] club;
	
	public Ogre(int x , int y, int MAP_SIZE){
		this.representation = "O";
		if (x >= 0 && x <= MAP_SIZE && y >= 0 && y <= MAP_SIZE){
			this.club[1] = y;
			this.club[0] = x;
			return;
		}
		moveClub(MAP_SIZE);
	}

	public int[] moveCharacter(int MAP_SIZE){
		Random rand = new Random();
		int[] temp = {-1,-1};
		while(temp[0] == -1){
			int dir = rand.nextInt(4);
			if(dir == 0 && (this.position[0]-1) >= 0){ // move left
				temp[0] = this.position[0]-1;  temp[1] = this.position[1];
			}
			else if (dir == 1 && (this.position[0]+1) < MAP_SIZE){ //move right
				temp[0] = this.position[0]+1;  temp[1] = this.position[1];
			}
			else if (dir == 2 && (this.position[1]-1) >= 0){ //move up
				temp[0] = this.position[0];  temp[1] = this.position[1]-1;
			}
			else if (dir == 3 && (this.position[1]+1) < MAP_SIZE){ //move down
				temp[0] = this.position[0];  temp[1] = this.position[1]+1;
			}
		}
		return temp;
	}

	public int[] moveClub(int MAP_SIZE){
		Random rand = new Random();
		int[] temp = {-1,-1};
		while(temp[0] == -1){
			int dir = rand.nextInt(4);
			if(dir == 0 && (this.position[0]-1) >= 0){ // move left
				temp[0] = this.position[0]-1;    temp[1] = this.position[1];
			}

			else if (dir == 1 && (this.position[0]+1) <= MAP_SIZE){ //move right
				temp[0] = this.position[0]+1;    temp[1] = this.position[1];
			}

			else if (dir == 2 && (this.position[1]-1) >= 0){ //move up
				temp[0] = this.position[0];    temp[1] = this.position[1]-1;
			}

			else if (dir == 3 && (this.position[1]+1) <= MAP_SIZE){ //move down
				temp[0] = this.position[0];    temp[1] = this.position[1]+1;
			}
		}
		return temp;
	}

	public boolean setClub(int x , int y, int MAP_SIZE){
		if (x >= 0 && x <= MAP_SIZE && y >= 0 && y <= MAP_SIZE){
			this.club[1] = y;
			this.club[0] = x;
			return true;
		}
		return false;
	}

	public int getClubX(){
		return this.club[0];
	}
	public int getClubY(){
		return this.club[1];
	}
}