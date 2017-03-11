package dkeep.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import dkeep.logic.ArenaMap;
import dkeep.logic.GameLogic;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import pair.Pair;


public class GameTests {
	char[][] map = {{'X','X','X','X','X'},
					{'X','H',' ','G','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
	
	@Test
	public void testMoveHeroIntoFreeCell(){
		Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(1,1),test2 = new Pair<Integer,Integer>(2,1);
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		assertEquals( test1 , game.getHero().getPos());
		game.moveHero('s');
		assertEquals( test2 , game.getHero().getPos());
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
		Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(1,1);
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		assertEquals( test1 , game.getHero().getPos());
		game.moveHero('a');
		assertEquals( test1 , game.getHero().getPos());
		game.moveHero('w');
		assertEquals( test1 , game.getHero().getPos());
	}

	@Test
	public void testMoveHeroIntoClosedDoor(){
		Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(1,1), test2 = new Pair<Integer,Integer>(2,1);
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		assertEquals( test1, game.getHero().getPos());
		game.moveHero('s');
		assertEquals( test2, game.getHero().getPos());
		game.moveHero('a');
		assertEquals( test2, game.getHero().getPos());
}

	@Test
	public void testOpenDoors(){
		Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(3,1);
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		game.moveHero('s');
		game.moveHero('s');
		assertEquals( test1 , game.getHero().getPos());
		game.getMap().openDoors();
		assertEquals( 'S' , game.getMap().getMap()[0][3]); //door 1
		assertEquals( 'S' , game.getMap().getMap()[0][4]); //door 2
	}

	@Test
	public void testEnterDoors(){
		Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(3,1);
		ArenaMap game_map = new ArenaMap(this.map);
		GameLogic game = new GameLogic(game_map,0);
		game.moveHero('s');
		game.moveHero('s');
		//System.out.print( "HERO["+game.getHero().getPos()[0]+","+game.getHero().getPos()[1]+"]\n");
		assertEquals( test1 , game.getHero().getPos());
		assertEquals( test1 , game.getHero().getPos());
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
	
	@Test(timeout=1000)
	public void testMoveAndClub(){
		ArenaMap map = new ArenaMap();
		GameLogic game = new GameLogic(map,1);
		Pair<Integer,Integer> temp;
		int px = game.getOgres().get(0).getX() , py = game.getOgres().get(0).getY();
		boolean cnn= false,cns=false,cnw=false,cne=false, csn=false , css=false , csw=false , cse=false, 
				cen=false , ces=false , cew=false , cee=false , cwn=false , cws=false , cwe=false , cww=false;
		while ( !(cnn && cns && cnw && cne && csn && css && csw && cse && cen && ces && cew && cee && cwn && cws && cwe && cww) ){
			do{ //MOVE OGRE FIRST
				temp = game.getOgres().get(0).moveCharacter(map.getMapSize());
			}while( !map.isFree(temp) );
			game.getOgres().get(0).setPos(temp.getFirst().intValue(), temp.getSecond().intValue() ,map.getMapSize());
			game.getOgres().get(0).setClub(temp.getFirst().intValue(), temp.getSecond().intValue() , map.getMapSize());
			
			do{ //MOVE CLUB
				temp = game.getOgres().get(0).moveClub(map.getMapSize());
			}while( !map.isFree(temp) );
			game.getOgres().get(0).setClub(temp.getFirst().intValue(), temp.getSecond().intValue() , map.getMapSize());
			int ox = game.getOgres().get(0).getX(), oy = game.getOgres().get(0).getY(), 
				cx = game.getOgres().get(0).getClubX(), cy = game.getOgres().get(0).getClubY();
			
			if 		( (px-2) == cx &&   py   == cy && (px-1) == ox && py == oy ) //Ogre north, club north
				cnn = true;
			else if ( (px-1) == cx && (py-1) == cy && (px-1) == ox && py == oy ) //Ogre north, club west
				cnw = true;
			else if ( (px-1) == cx && (py+1) == cy && (px-1) == ox && py == oy ) //Ogre north, club east
				cne = true;
			else if (   px 	 == cx &&   py   == cy && (px-1) == ox && py == oy ) //Ogre north, club south
				cns = true;
			else if (   px   == cx &&   py   == cy && (px+1) == ox && py == oy ) //Ogre south, club north
				csn = true;
			else if ( (px+1) == cx && (py-1) == cy && (px+1) == ox && py == oy ) //Ogre south, club west
				csw = true;
			else if ( (px+1) == cx && (py+1) == cy && (px+1) == ox && py == oy ) //Ogre south, club east
				cse = true;
			else if ( (px+2) == cx &&   py   == cy && (px+1) == ox && py == oy ) //Ogre south, club south
				css = true;
			else if ( (px-1) == cx && (py-1) == cy && px == ox && (py-1) == oy ) //Ogre west, club north
				cwn = true;
			else if (   px   == cx && (py-2) == cy && px == ox && (py-1) == oy ) //Ogre west, club west
				cww = true;
			else if (   px   == cx &&   py   == cy && px == ox && (py-1) == oy ) //Ogre west, club east
				cwe = true;
			else if ( (px+1) == cx && (py-1) == cy && px == ox && (py-1) == oy ) //Ogre west, club south
				cws = true;
			else if ( (px-1) == cx && (py+1) == cy && px == ox && (py+1) == oy ) //Ogre east, club north
				cen = true;
			else if (   px   == cx &&   py   == cy && px == ox && (py+1) == oy ) //Ogre east, club west
				cew = true;
			else if (   px   == cx && (py+2) == cy && px == ox && (py+1) == oy ) //Ogre east, club east
				cee = true;
			else if ( (px+1) == cx && (py+1) == cy && px == ox && (py+1) == oy ) //Ogre east, club south
				ces = true;
			else
				fail("Unknown error");
			px = game.getOgres().get(0).getX(); py = game.getOgres().get(0).getY();
		}
	}
	

	
	@Test
	public void testFailOpenDoor(){
		int[] door={1,0};
		int[] heroi={1,1};
		ArenaMap game_map = new ArenaMap();
		GameLogic game = new GameLogic(game_map,2);
		assertEquals('I',game_map.getMap()[door[0]][door[1]]);
		game.moveHero('a');
		assertEquals(heroi[0],game.getHero().getX());
		assertEquals(heroi[1],game.getHero().getY());
		assertEquals('I',game_map.getMap()[door[0]][door[1]]);
		
		
	}
	@Test
	public void testSuccessOpenDoor(){
		int[] door={1,0};
		ArenaMap game_map = new ArenaMap();
		GameLogic game = new GameLogic(game_map,2);
		assertEquals('I',game_map.getMap()[door[0]][door[1]]);
		Hero h=game.getHero();
		assertEquals("H",h.getRepresentation());
		game.moveHero('d');
		assertEquals("K",h.getRepresentation());
		game.moveHero('a');
		game.moveHero('a');
		assertEquals('S',game_map.getMap()[door[0]][door[1]]);
		
		
	}
	@Test
	public void testVictory(){
		int[] door={1,0};
		ArenaMap game_map = new ArenaMap();
		GameLogic game = new GameLogic(game_map,2);
		assertEquals('I',game_map.getMap()[door[0]][door[1]]);
		Hero h=game.getHero();
		assertEquals("H",h.getRepresentation());
		game.moveHero('d');
		assertEquals("K",h.getRepresentation());
		game.moveHero('a');
		game.moveHero('a');
		game.moveHero('a');
		assertEquals(true,game. wonGame());
	}
	
	@Test
	public void stunOgre(){
		//hero is at position 1,1 and ogre 1,3
		ArenaMap game_map = new ArenaMap();
		GameLogic game = new GameLogic(game_map,3);
		ArrayList<Ogre> ogres=game.getOgres();
		assertEquals("O",ogres.get(0).getRepresentation());
		game.moveHero('d');
		ogres.get(0).stunOgre();
		ogres.get(0).setClub(1, 4, game_map.getMapSize());
		assertEquals( false,game.isGameOver());
		int x=ogres.get(0).getX();
		int y=ogres.get(0).getY();
		//assertEquals(true,game.checkStun(ogres.get(0).getX(), ogres.get(0).getY()));
		game.moveAllVillains();
		assertEquals("8",ogres.get(0).getRepresentation());
		assertEquals(x,ogres.get(0).getX());
		assertEquals(y,ogres.get(0).getY());
		
		}
	
	@Test
	
	public void moveinDungeon(){
		Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(1,1), test2 = new Pair<Integer,Integer>(1,2);
		GameLogic game =new GameLogic(0);
		assertEquals( test1 , game.getHero().getPos());
		game.moveHero('d');
		assertEquals( test2 , game.getHero().getPos());
		
	}
	
	
	
	
}