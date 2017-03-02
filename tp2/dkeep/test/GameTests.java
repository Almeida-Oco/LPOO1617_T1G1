package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;
import dkeep.logic.ArenaMap;
import dkeep.logic.GameLogic;

public class GameTests {
	char[][] map = {{'X','X','X','X','X'},
					{'X','H',' ','G','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
	
	@Test
	public void testMoveHeroIntoFreeCell(){
		int[] test1 = {1,1},test2 = {2,1};
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
		game.moveHero('s');
		assertEquals( test2[0] , game.getHero().getPos()[0]);
		assertEquals( test2[1] , game.getHero().getPos()[1]);
	}
	@Test
	public void testHeroIsCapturedByGuard(){
		ArenaMap gameMap =new ArenaMap(map);
		GameLogic game=new GameLogic(gameMap,0);
		assertFalse(game.isGameOver());
		game.moveHero('d');
		assertTrue(game.isGameOver());
		//assertEquals(Game:DEFEAT,game.getEndStatus());
	}
}