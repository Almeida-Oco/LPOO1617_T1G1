package dkeep.cli;

public class UserInput{
	private void printGame(char[][] map){
		System.out.print("\033[H\033[2J"); //clears screen

		for ( int i = 0 ; i < map.length ; i++ ) {
			for ( int j = 0 ; j < map[i].length ; j++ ) {
					System.out.print(temp[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("W-A-S-D controls");
	}

	private char readChar(){
		Scanner scan = new Scanner(System.in);
		String line;
		line = scan.nextLine();
		char key=' ';

		if (line.length() > 0)
				key = line.charAt(0);
			else
				key = ' ';
		return key;
	}


	private void cpu(){
		this.printGame();
		int guard_pos = 23;
		//IN-GAME
		while( (!checkGameOver(guard[guard_pos]) && level1) || (!checkGameOver(ogre) && !checkGameOver(club) && !level1) ){
			System.out.print("Insert movement :  ");			
			//move hero based on input
			this.movePlayer(readChar());
			//checks if its game over
			if( (checkGameOver(guard[guard_pos]) && level1) || (checkGameOver(ogre) && !checkGameOver(club) && !level1))
				break;
			guard_pos=moveNPC(guard_pos,level1);
			//PLAYER GOES UP THE STAIRS
			if( (this.hero[0] == this.final_doors[0][0] && this.hero[1] == this.final_doors[0][1]) ||
				(this.hero[0] == this.final_doors[1][0] && this.hero[1] == this.final_doors[1][1]) ){
				this.level1 = false;
				this.has_key= false;
				this.object_on_top_of = ' ';
				this.hero[0] = 8; this.hero[1] = 1;
			}
			this.printGame();
			if(!level1 && this.hero[0] == this.last_door[0] && this.hero[1] == this.last_door[1] ){
				System.out.println("   YOU WIN ");
				return;
			}
		}
		
		//GAME OVER
		guard_pos=moveNPC(guard_pos-1,!this.level1);
		this.printGame();
		System.out.println("\n GAME OVER!");	
	}
}