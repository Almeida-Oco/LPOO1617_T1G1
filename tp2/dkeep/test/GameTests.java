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
	public void testMoveOgre(){
		ArenaMap map = new ArenaMap();
		GameLogic game = new GameLogic(map,1);
		int[] test = { game.getOgres().get(0).getX() , game.getOgres().get(0).getY() };
		int[] temp;
		for(int i = 0 ; i < 10 ; i++){
			do{
				temp = game.getOgres().get(0).moveCharacter(map.getMapSize());
			}while( !map.isFree( temp[0] , temp[1] ) );
			game.getOgres().get(0).setPos(temp[0], temp[1], map.getMapSize());
			
			assertEquals( inAdjSquares(test[0],test[1],game.getOgres().get(0).getX(),game.getOgres().get(0).getY()) , true);
			test[0] = game.getOgres().get(0).getX(); test[1] = game.getOgres().get(0).getY();
		}
	}
	
	public void testClubSwing(){
		ArenaMap map = new ArenaMap();
		GameLogic game = new GameLogic(map,1);
		int[] test = { game.getOgres().get(0).getX() , game.getOgres().get(0).getY() }, temp;
		for (int i = 0 ; i < 10 ; i++){
			do{
				temp = game.getOgres().get(0).moveClub(map.getMapSize());
			}while( !map.isFree(temp[0], temp[1]) );
			game.getOgres().get(0).setClub(temp[0], temp[1], map.getMapSize());
			
			assertEquals( inAdjSquares(test[0],test[1],game.getOgres().get(0).getClubX(),game.getOgres().get(0).getClubY() ) , true ) ;
			
		}
	}
	
	
	
	private boolean inAdjSquares(int x_previous , int y_previous , int x_current , int y_current){ //check if hero is in adjacent square
		return ( (x_current == x_previous-1 && y_current == y_previous) || (x_current == x_previous+1 && y_current == y_previous) || 
			   (x_current == x_previous && y_current == y_previous-1) || (x_current == x_previous && y_current == y_previous+1) );
	}
}