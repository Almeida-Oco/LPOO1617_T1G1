package dkeep.cli;
import dkeep.logic.GameLogic;
import dkeep.logic.Pair;
import dkeep.logic.GameCharacter;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInput{
	GameLogic game;
	
	/**
	 * Console Mode main function
	 * @param args Optional and irrelevant arguments from the console
	 */
	public static void main(String[] args) {
		UserInput io = new UserInput();
		io.cpu();
	}
	
	/**
	 * Starts a new game with the first map DungeonMap
	 */
	public UserInput(){
		this.game = new GameLogic(null,0);
	}
	
	/**
	 * Gets a printable ready map
	 * @param map Current game map of the game to be printed
	 * @param all_chrs All characters of the game to be printed
	 * @param clear Whether to clear the console or not
	 * @param spaced Whether the return string has spaces between map tiles
	 * @return String with printable map
	 */
	public static String getPrintableMap(char[][] map , ArrayList<GameCharacter> all_chrs, boolean clear, boolean spaced){
		if(clear)
			clearScreen();
		StringBuilder result = new StringBuilder();
		for(GameCharacter ch : all_chrs)
			for( Pair< Pair<Integer,Integer> ,String> p : ch.getPrintable() )
				map[p.getFirst().getFirst().intValue()][p.getFirst().getSecond().intValue()] = p.getSecond().charAt(0);
		for ( int i = 0 ; i < map.length ; i++ ) {
			for ( int j = 0 ; j < map[i].length ; j++ ) 
				result.append(map[i][j] + ((spaced) ? " " : ""));
			result.append("\n");
		}
		return result.toString();
	}
	
	/**
	 * Simple function to clear the console
	 */
	private static void clearScreen(){
		for(int i = 0; i < 15 ; i++)
			System.out.println("");
	}
	
	/**
	 * Function to read the first char put in console
	 * @return The char read
	 */
	private static char readChar(){
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
	
	/**
	 * The console mode Game loop
	 */
	private void cpu(){
		do{
			System.out.println(getPrintableMap(this.game.getMap().getMap() , this.game.getAllCharacters(),true,true));
			
			boolean change_lvl=this.game.moveHero( readChar());
			if (change_lvl && !this.game.wonGame() )
				this.game = this.game.getNextLevel(0); //0 -> random number of enemies
			
			if (!(this.game.isGameOver() || this.game.wonGame()) )
				this.game.moveAllVillains();			
		}while (!this.game.wonGame() && !this.game.isGameOver());
		System.out.println(getPrintableMap(this.game.getMap().getMap() , this.game.getAllCharacters(),true,true));
		System.out.print( (this.game.wonGame()) ? "   YOU WIN!   \n" : "   GAME OVER!  \n");
	}
}