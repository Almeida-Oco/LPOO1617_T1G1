package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic{
	private Map map;
	private Guard guard;
	private ArrayList<Ogre> ogres = new ArrayList<Ogre>();
	private Hero hero;
	private int[] key = new int[2];
	private int level = 0;

	public GameLogic(Map game_map,int level){ // WARNING!! ONLY FOR TESTING !!
		if (level == 0){
			this.level = level;
			this.guard = new RookieGuard(1,3);
			this.map = game_map;
			this.hero = new Hero(1,1);
		}
		else if(level==1){
			this.level=1;
			Ogre o=new Ogre(2,2,game_map.getMapSize(),true);
			ogres.add(o);
			this.map = game_map;
			this.hero = new Hero(1,1);
			this.key[0]=1;
			this.key[1]=2;
		}
		else if(level==2){
			this.map = game_map;
			this.level=1;
			this.hero = new Hero(1,1);
			this.key[0] = 1; 
			this.key[1] = 2;
		}
	}
	
	public GameLogic(int level){
		Random rand = new Random();
		this.level = level;
		
		if(0 == level){	
			this.map 	= new DungeonMap();
			this.hero 	= new Hero(this.level,false);
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
			this.hero = new Hero(this.level, true);
			this.key[0] = 1;
			this.key[1] = 8;
			int res = rand.nextInt(3)+1;
			for (int i = 0 ; i < res ; i++)
				this.ogres.add(new Ogre(rand.nextInt(8)+1,rand.nextInt(8)+1,map.getMapSize(),false));	
		}
	}
	
	private boolean inAdjSquares(int x , int y){ //check if hero is in adjacent square
		if ( x != -1 && y != -1)
			if( (this.hero.getX() == x-1 && this.hero.getY() == y) || (this.hero.getX() == x+1 && this.hero.getY() == y) || 
				(this.hero.getX() == x && this.hero.getY() == y-1) || (this.hero.getX() == x && this.hero.getY() == y+1) || (this.hero.getX() == x && this.hero.getY() == y))
				return true;
			
		return false;
	}
	
	public boolean isGameOver(){ //Gets all characters game over positions and checks
		for(Character ch : getAllCharacters() )
			for (int[] pos : ch.getGameOverPos(this.level) )
				if ( inAdjSquares(pos[0],pos[1]) )
					return true;
		
		return false;
	}
	
	public void moveAllVillains(){ //move all villains based on current level
		int []pos;
		if ( 0 == this.level ){ //move only guards
			do{
				pos = guard.moveCharacter(map.getMapSize());
			}while(!this.map.isFree(pos[0],pos[1]) && this.map.getMap()[pos[0]][pos[1]]!='H' && this.map.getMap()[pos[0]][pos[1]]!='A');
		}
		else if (1 == this.level ){ //move only ogres
			for (Ogre o : this.ogres ){
				do{
					pos = o.moveCharacter(this.map.getMapSize());
				}while(!this.map.isFree(pos[0],pos[1]));
				if(pos[0]==key[0] && pos[1]==key[1]){
					o.setRepresentation("$");
				}
				else{
					o.setRepresentation("O");
				}
				o.setPos(pos[0], pos[1], this.map.getMapSize());
				if(this.hero.checkArmed()){
				if(checkStun(pos[0],pos[1]))
					o.stunOgre();
				else
					o.roundPassed();
				}
				do{
					pos = o.moveClub(this.map.getMapSize());
				}while( !this.map.isFree(pos[0],pos[1]));
				if(pos[0]==key[0] && pos[1]==key[1]){
					o.setClubRepresentation("$");
				}
				else{
					o.setClubRepresentation("*");
				}
				o.setClub(pos[0], pos[1], this.map.getMapSize());
			}
		}
	}

	public GameLogic moveHero(char direction){ //moves hero, returns an object of GameLogic, either next level or same level
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
			return this;
		
		if (checkTriggers(temp)) //check if level up
			return (this.level == 0) ? new GameLogic(++this.level) : this;

		boolean free=true;
		if( this.map.isFree(temp[0],temp[1])){
			ArrayList<Character> tempa =getAllCharacters();
			 for(int i=1; i< tempa.size(); i++){
				 if(temp[0]== tempa.get(i).getX()&& temp[1]== tempa.get(i).getY())
					 free=false;
			 }
				if(free) 
			this.hero.setPos(temp[0], temp[1], this.map.getMapSize());
		}
		if(temp[0]==key[0] && temp[1]==key[1]&& level==1){
			hero.setRepresentation("K");
		}
		
		
//		if(level ==1){
//		do{
//			temp = hero.moveClub(this.map.getMapSize());
//		}while( !this.map.isFree(temp[0],temp[1]));
//		if(temp[0]==key[0] && temp[1]==key[1]){
//			hero.setClubRepresentation("$");
//		}
//		else{
//			hero.setClubRepresentation("c");
//		}
//		hero.setClub(temp[0], temp[1], this.map.getMapSize());
//		
//		}
		
		
		
		return this;
	}

	public ArrayList<Character> getAllCharacters(){ //gathers all characters (hero,guard,ogre) in an ArrayList
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
	
	private boolean checkTriggers(int[] pos){ //checks if hero is in a key/lever or entered a door/stairs
		if(level == 0 && pos[0] == this.key[0] && pos[1] == this.key[1] )
			this.map.openDoors();
		else if (level == 1 && this.map.getMap()[pos[0]][pos[1]] == 'I' && this.hero.hasKey()){
			this.map.openDoors();
			pos[1]++; //stop hero from going inside stairs at first attempt
		}
		else if (level == 1 && pos[0] == this.key[0] && pos[1] == this.key[1] && !this.hero.hasKey()){
			this.hero.setKey(true);
			this.map.pickUpKey();
		}
		else if (this.map.getMap()[pos[0]][pos[1]] == 'S'){
			this.hero.setPos(pos[0], pos[1], this.map.getMapSize());
			return true;
		}
			
		
		return false;
	}
	
	public boolean wonGame(){ //checks if hero got to the final stairs
		return (this.level == 1 && this.map.getMap()[this.hero.getX()][this.hero.getY()] == 'S');
	}
	
	public boolean checkStun(int x, int y){
			if( (this.hero.getX() == x-1 && this.hero.getY() == y) || (this.hero.getX() == x+1 && this.hero.getY() == y) || 
				(this.hero.getX() == x && this.hero.getY() == y-1) || (this.hero.getX() == x && this.hero.getY() == y+1) )
				return true;
			
			return false;
	}

	public Map getMap(){
		return this.map;
	}

	public ArrayList<Ogre> getOgres(){
		return this.ogres;
	}
	
	public int getLevel(){
		return this.level;
	}

	public Hero getHero(){
		return this.hero;
	}
	
}