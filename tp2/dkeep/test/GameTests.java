package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;
import dkeep.logic.ArenaMap;
import dkeep.logic.GameLogic;
import dkeep.logic.Hero;


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

	@Test
	public void testMoveHeroIntoOccupiedCell(){
		int[] test1 = {1,1};
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
		game.moveHero('a');
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
		game.moveHero('w');
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
	}

	@Test
	public void testMoveHeroIntoClosedDoor(){
		int[] test1 = {1,1}, test2 = {2,1};
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
		game.moveHero('s');
		assertEquals( test2[0] , game.getHero().getPos()[0]);
		assertEquals( test2[1] , game.getHero().getPos()[1]);
		game.moveHero('a');
		assertEquals( test2[0] , game.getHero().getPos()[0]);
		assertEquals( test2[1] , game.getHero().getPos()[1]);
}

	@Test
	public void testOpenDoors(){
		int[] test1 = {3,1};
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		game.moveHero('s');
		game.moveHero('s');
		//System.out.print( "HERO["+game.getHero().getPos()[0]+","+game.getHero().getPos()[1]+"]\n");
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
		game.getMap().openDoors();
		assertEquals( 'S' , game.getMap().getMap()[0][3]); //door 1
		assertEquals( 'S' , game.getMap().getMap()[0][4]); //door 2
	}

	@Test
	public void testEnterDoors(){
		int[] test1 = {3,1};
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		game.moveHero('s');
		game.moveHero('s');
		//System.out.print( "HERO["+game.getHero().getPos()[0]+","+game.getHero().getPos()[1]+"]\n");
		assertEquals( test1[0] , game.getHero().getPos()[0]);
		assertEquals( test1[1] , game.getHero().getPos()[1]);
		game.getMap().openDoors();
		assertEquals( 'S' , game.getMap().getMap()[0][3]); //door 1
		assertEquals( 'S' , game.getMap().getMap()[0][4]); //door 2
	}
	
	@Test
    public void testMoveHeroNextOgre(){
	ArenaMap game_map = new ArenaMap();
	GameLogic game = new GameLogic(game_map,1);
	game.moveHero('d');
	assertEquals( true,game.isGameOver());
	
	}
	
	@Test
	public void testChangeRepresentation(){
		ArenaMap game_map = new ArenaMap();
		GameLogic game = new GameLogic(game_map,1);
		Hero h=game.getHero();
		assertEquals("H",h.getRepresentation());
		game.moveHero('d');
		assertEquals("K",h.getRepresentation());
		
		
	}
}