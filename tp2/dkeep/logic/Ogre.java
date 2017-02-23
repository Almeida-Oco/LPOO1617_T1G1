import java.util.Random

public class Ogre extends Character {
	private int[] club;

	public static void main(String[] args) {
	}

	public Ogre(int x , int y){
		this.representation = 'O';
		this.position[1] = x;
		this.position[0] = y;
		moveClub();
	}

	public void moveCharacter(){
		Random rand = new Random();
		while(true){
			int dir = rand.nextInt(4);
			if(dir == 0 && (this.position[0]-1) >= 0){ // move left
				this.position[1]-=1;
				break;
			}
			else if (dir == 1 && (this.position[0]+1) <= 8){ //move right
				this.position[1]+=1;
				break;
			}
			else if (dir == 2 && (this.position[1]-1) >= 0){ //move up
				this.position[0]-=1;
				break;
			}
			else if (dir == 3 && (this.position[1]+1) <= 8){ //move down
				this.position[0]+=1;
				break;
			}
		}

		moveClub();
	}

	private moveClub(){
		Random rand = new Random();
		while(true){
			int dir = rand.nextInt(4);
			if(dir == 0 && (this.position[0]-1) >= 0){ // move left
				this.club[1] = this.position[0]-1;
				break;
			}
			else if (dir == 1 && (this.position[0]+1) <= 8){ //move right
				this.club[1] = this.position[0]+1;
				break;
			}
			else if (dir == 2 && (this.position[1]-1) >= 0){ //move up
				this.club[0] = this.position[1]-1;
				break;
			}
			else if (dir == 3 && (this.position[1]+1) <= 8){ //move down
				this.club[0] = this.position[1]+1;
				break;
			}
		}
	}

	public String toString(){
		return "O";
	}

	public int getClubX(){
		return this.club[0];
	}
	public int getClubY(){
		return this.club[1];
	}
}