package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic{
	private Map map;
	private Guard guard;
	private ArrayList<Ogre> ogres;
	private Hero hero;
	private int[] key = new int[2];
	private int level = 0;

	public GameLogic(int level){
		Random rand = new Random();
		this.level = level;
		
		if(0 == level){	
			this.map 	= new DungeonMap();
			this.hero 	= new Hero();
			this.key[0] = 8; 
			this.key[1] = 7;
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
			this.key[0] = 1;
			this.key[1] = 8;
			int res = rand.nextInt(3)+1;
			for (int i = 0 ; i < res ; i++)
				this.ogres.add(new Ogre(rand.nextInt(9)+1,rand.nextInt(9)+1,map.getMapSize()));	
		}
	}
	
	private boolean inAdjSquares(int x , int y){
		if ( x != -1 && y != -1)
			if( (this.hero.getX() == x-1 && this.hero.getY() == y) || (this.hero.getX() == x+1 && this.hero.getY() == y) || 
				(this.hero.getX() == x && this.hero.getY() == y-1) || (this.hero.getX() == x && this.hero.getY() == y+1) )
				return true;
			
		return false;
	}
	
	public boolean isGameOver(){
		if(0 == this.level && inAdjSquares(this.guard.getX() , this.guard.getY())) // check Guard
			return true;
		else if (1 == this.level){
			for( Ogre o : this.ogres ){ // check Ogres
				if (inAdjSquares( o.getX(),o.getY()) || inAdjSquares(o.getClubX(),o.getClubY()))
					return true;
			}
		}
		
		return false;
	}
	
	public void moveAllVillains(){
		int []pos;
		if ( 0 == this.level ){ //move only guards
			do{
				pos = guard.moveCharacter(map.getMapSize());
			}while(!this.map.isFree(pos[0],pos[1]) );
		}
		else if (1 == this.level ){ //move only ogres
			for (Ogre o : this.ogres ){
				int[] pos_club;
				do{
					pos = o.moveCharacter(this.map.getMapSize());
				}while(this.map.isFree(pos[0],pos[1]));
				
				do{
					pos_club = o.moveClub(this.map.getMapSize());
				}while( !this.map.isFree(pos_club[0],pos_club[1]));
			}
		}
	}

	public boolean moveHero(char direction){ //true if hero moved
		int[] temp = {-1,-1};
		
		if	   ('w' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize() , 4);
		else if('a' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize() , 2);		
		else if('s' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize() , 3);
		else if('d' == direction)
			temp = this.hero.moveCharacter(this.map.getMapSize() , 1);
		else
			return false;
		
		checkTriggers(temp);
		if( !this.map.isFree(temp[0],temp[1]))
			return false;
		else
			this.hero.setPos(temp[0], temp[1], this.map.getMapSize());
		
		return true;
	}

	public ArrayList<Character> getAllCharacters(){
		ArrayList<Character> temp = new ArrayList<Character>();
		temp.add(this.hero);
		if(0 == this.level)
			temp.add(this.guard);
		else if (1 == this.level){
			for(Ogre o : this.ogres)
				temp.add(o);
		}
		
		return temp;
	}
	
	private void checkTriggers(int[] pos){
		if(level == 0 && this.map.getMap()[pos[0]][pos[1]] == 'K')
			this.map.openDoors();
		else if (level == 1 && this.map.getMap()[pos[0]][pos[1]] == 'I')
			this.map.openDoors();
		else if (this.map.getMap()[pos[0]][pos[1]] == 'S' && level == 0)
			this.map = this.map.nextMap();
	}
	
	public boolean wonGame(){
		return (level == 1 && this.map.getMap()[this.hero.getX()][this.hero.getY()] == 'S');
	}
	
	public Map getMap(){
		return this.map;
	}

	public int getLevel(){
		return this.level;
	}
}