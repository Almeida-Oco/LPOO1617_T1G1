package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic{
	private Map map;
	private Guard guard;
	private ArrayList<Ogre> ogres;
	private Hero hero;
	private int[] key;

	public GameLogic(int level){
		Random rand = new Random();
		if(0 == level){
			this.map 	= new DungeonMap();
			this.hero 	= new Hero();
			int res = rand.nextInt(3);
			if(0 == res)
				this.guard = new RookieGuard();
			else if (1 == res)
				this.guard = new DrunkenGuard();
			else if (2 == res)
				this.guard = new SuspiciousGuard();
		}
		else if (1 == level){
			this.map = new ArenaMap();
			this.hero = new Hero();
			int res = rand.nextInt(3)+1;
			for (int i = 0 ; i < res ; i++)
				this.ogres.add(new Ogre(rand.nextInt(9)+1,rand.nextInt(9)+1,map.getMapSize()));	
		}
	}
	
	private boolean inAdjSquares(int x , int y){
		if ( x != -1 && y != -1)
			if( hero.getX() == x-1 || hero.getX() == x+1 || hero.getY() == y-1 || hero.getY() == y+1)
				return true;
			
		return false;
	}
	
	public boolean isGameOver( ){
		if(inAdjSquares(this.guard.getX() , this.guard.getY())) // check Guard
			return true;
		
		for( Ogre o : this.ogres ){ // check Ogres
			if (inAdjSquares( o.getX(),o.getY()) || inAdjSquares(o.getClubX(),o.getClubY()))
				return true;
		}
		
		
		return false;
	}
	
	public void moveAllCharacters(int level){
		int []pos;
		if ( 0 == level ){ //move only guards
			do{
				pos = guard.moveCharacter(map.getMapSize());
			}while( !this.map.isFree(pos[0],pos[1]) );
		}
		else if (1 == level ){ //move only ogres
			for (Ogre o : this.ogres ){
				int[] pos_club;
				do{
					pos = o.moveCharacter(this.map.getMapSize());
				}while( !this.map.isFree(pos[0],pos[1]));
				
				do{
					pos_club = o.moveClub(this.map.getMapSize());
				}while( !this.map.isFree(pos[0],pos[1]));
			}
		}
	}
}