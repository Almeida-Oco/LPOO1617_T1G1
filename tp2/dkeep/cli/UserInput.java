package dkeep.cli;
import dkeep.logic.GameLogic;
import dkeep.logic.Character;
import java.util.Scanner;
import pair.Pair;

public class UserInput{
	GameLogic game;
	
	public static void main(String[] args) {
		UserInput io = new UserInput();
		io.cpu();
	}
	
	public UserInput(){
		this.game = new GameLogic(0);
	}
	
	public UserInput(int ogres, int guard){
		this.game = new GameLogic(3,ogres,guard);
	}
	
	
	public GameLogic getGame(){
		return game;
	}
	public String printGame(char[][] map,int level){
		clearScreen();
		StringBuilder result = new StringBuilder();
		for(Character ch : this.game.getAllCharacters())
			for( Pair<int[],String> p : ch.getPrintable() )
				map[p.getFirst()[0]][p.getFirst()[1]] = p.getSecond().charAt(0);

		for ( int i = 0 ; i < map.length ; i++ ) {
			for ( int j = 0 ; j < map[i].length ; j++ ) {
				result.append(map[i][j] + " ");
			}
			result.append("\n");
		}
		result.append("      W-A-S-D");
		
		return result.toString();
	}

	private void clearScreen(){
		for(int i = 0; i < 15 ; i++)
			System.out.println("");
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
		do{
			System.out.println(printGame(this.game.getMap().getMap(),this.game.getLevel()));
			this.game = this.game.moveHero(readChar());
			this.game.moveAllVillains();			
		}while (!this.game.wonGame() && !this.game.isGameOver());
		printGame(this.game.getMap().getMap(),this.game.getLevel());
		if (this.game.wonGame())
			System.out.print("   YOU WIN!   \n");
		else
			System.out.print("   GAME OVER!  \n");
	}
	
}