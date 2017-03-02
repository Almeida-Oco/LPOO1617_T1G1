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
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public void testMoveHeroIntoFreeCell(){
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map);
		assertEquals()
	}

}
