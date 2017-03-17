package dkeep.cli;
import dkeep.logic.GameLogic;
import dkeep.logic.Character;
import java.util.Scanner;
import pair.Pair;

public class UserInput{
	GameLogic game;
	
	public static void main(String[] args) {
		UserInput io = new UserInput(0,0);
		io.cpu();
	}
	
	public UserInput(int ogres, int guard){
		this.game = new GameLogic(0,ogres,guard);
	}
	
	public GameLogic getGame(){
		return game;
	}
	
	public String getPrintableMap(GameLogic gm, boolean clear, boolean spaced){
		char[][] temp_map = gm.getMap().getMap();
		if(clear)
			clearScreen();
		StringBuilder result = new StringBuilder();
		for(Character ch : gm.getAllCharacters())
			for( Pair< Pair<Integer,Integer> ,String> p : ch.getPrintable() )
				temp_map[p.getFirst().getFirst().intValue()][p.getFirst().getSecond().intValue()] = p.getSecond().charAt(0);

		for ( int i = 0 ; i < gm.getMap().getMap().length ; i++ ) {
			for ( int j = 0 ; j < gm.getMap().getMap()[i].length ; j++ ) {
				result.append(temp_map[i][j] + ((spaced) ? " " : ""));
			}
			result.append("\n");
		}
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
		boolean change_lvl = false;
		do{
			System.out.println(getPrintableMap(this.game,true,true));
			change_lvl=this.game.moveHero( readChar());
			System.out.println( this.game.getHero().getPos() );
			if (change_lvl && !this.game.wonGame() )
				this.game = this.game.getNextLevel(-1,-1);
			if (this.game.isGameOver() || this.game.wonGame())
				break;
			
			this.game.moveAllVillains();			
		}while (!this.game.wonGame() && !this.game.isGameOver());
		System.out.println(getPrintableMap(this.game,true,true));
		if (this.game.wonGame())
			System.out.print("   YOU WIN!   \n");
		else
			System.out.print("   GAME OVER!  \n");
	}
	
}