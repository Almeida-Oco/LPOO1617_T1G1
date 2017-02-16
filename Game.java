//package LPOO1617_T1G1;
import java.util.Scanner;
import java.util.Random;

public class Game{
	private boolean has_key = false;
	private boolean level1 = true;
	private char object_on_top_of = ' ';
	private char[][] map = { {'X','X','X','X','X','X','X','X','X','X'} ,
							 {'X','H',' ',' ','I',' ','X',' ','G','X'} ,
							 {'X','X','X',' ','X','X','X',' ',' ','X'} ,
							 {'X',' ','I',' ','I',' ','X',' ',' ','X'} ,
							 {'X','X','X',' ','X','X','X',' ',' ','X'} ,
							 {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X','X','X',' ','X','X','X','X',' ','X'} ,
							 {'X',' ','I',' ','I',' ','X','K',' ','X'} ,
							 {'X','X','X','X','X','X','X','X','X','X'} };

	private char[][] map2 = {{'X','X','X','X','X','X','X','X','X','X'} ,
							 {'I',' ',' ',' ',' ','O',' ',' ','K','X'} ,
							 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X','H',' ',' ',' ',' ',' ',' ',' ','X'} ,
							 {'X','X','X','X','X','X','X','X','X','X'} };
	private int[][] guard  		= {{1,7},{2,7},{3,7},{4,7},{5,7},{5,6},{5,5},{5,4},{5,3},{5,2},{5,1},{6,1},
								   {6,2},{6,3},{6,4},{6,5},{6,6},{6,7},{6,8},{5,8},{4,8},{3,8},{2,8},{1,8}};
	private int[]   hero        =  {1,1};
	private int[] 	key 		=  {1,8};
	private int[] 	ogre		=  {1,5};
	private int[]   lever       =  {8,7};
	private int[][] doors       = {{4,1} , {4,3} , {2,3} , {2,8} , {4,8}};
	private int[][] final_doors = {{5,0} , {6,0}};
	private int[] last_door		=  {1,0};


	public static void main(String[] args) {
		Game temp = new Game ();
		temp.cpu();
	}

	private void printGame(){
		System.out.print("\033[H\033[2J"); //clears screen
		char[][] temp;
		if(this.level1)
			temp = this.map;
		else
			temp = this.map2;	

		for ( int i = 0 ; i < temp.length ; i++ ) {
			for ( int j = 0 ; j < temp[i].length ; j++ ) {
				System.out.print(temp[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("W-A-S-D controls");
	}

	private void cpu(){
		this.printGame();
		int guard_pos = 23;
		//IN-GAME
		while( (!checkGameOver(guard[guard_pos]) && level1) || (!checkGameOver(ogre) && !level1) ){
			System.out.print("Insert movement :  ");			
			//move hero based on input
			this.movePlayer(readChar());
			//checks if its game over
			if( (checkGameOver(guard[guard_pos]) && level1) || (checkGameOver(ogre) && !level1))
				break;
			guard_pos=moveNPC(guard_pos,level1);
			//PLAYER GOES UP THE STAIRS
			if( (this.hero[0] == this.final_doors[0][0] && this.hero[1] == this.final_doors[0][1]) ||
				(this.hero[0] == this.final_doors[1][0] && this.hero[1] == this.final_doors[1][1]) ){
				this.level1 = false;
				this.has_key= false;
				this.object_on_top_of = ' ';
				this.hero[0] = 8; this.hero[1] = 1;
			}
			this.printGame();
			if(!level1 && this.hero[0] == this.last_door[0] && this.hero[1] == this.last_door[1] ){
				System.out.println("   YOU WIN ");
				return;
			}
		}
		
		//GAME OVER
		guard_pos=moveNPC(guard_pos-1,!this.level1);
		this.printGame();
		System.out.println("\n GAME OVER!");	
	}

	private int movePlayer(char key_pressed){
		boolean top_of_lever = (this.map[this.hero[0]][this.hero[1]] == 'K');

		if( ('w' == key_pressed || 'W' == key_pressed) && 
	 	 ((level1 && this.map[this.hero[0]-1][this.hero[1]] != 'X'   && 
	 	 			 this.map[this.hero[0]-1][this.hero[1]] != 'G'   && 
	 	 			 this.map[this.hero[0]-1][this.hero[1]] != 'O'   && 
	 	 			 this.map[this.hero[0]-1][this.hero[1]] != 'I')  ||
		  (!level1&&this.map2[this.hero[0]-1][this.hero[1]] != 'X'   &&
		  			this.map2[this.hero[0]-1][this.hero[1]] != 'G'   &&
		  			this.map2[this.hero[0]-1][this.hero[1]] != 'O'   && 
		  			this.map2[this.hero[0]-1][this.hero[1]] != 'I')))
		{	
			if (level1)
				this.map[this.hero[0]][this.hero[1]] = this.object_on_top_of;
			else
				this.map2[this.hero[0]][this.hero[1]] = this.object_on_top_of;			
			this.hero[0] -= 1;
		}

		else if ( ('a' == key_pressed || 'A' == key_pressed) && 
			   ((level1 && this.map[this.hero[0]][this.hero[1]-1] != 'X'   && 
			   			   this.map[this.hero[0]][this.hero[1]-1] != 'G'   &&
			   			   this.map[this.hero[0]][this.hero[1]-1] != 'O'   &&
			   			   this.map[this.hero[0]][this.hero[1]-1] != 'I')  ||
			   	(!level1&&this.map2[this.hero[0]][this.hero[1]-1] != 'X'   &&
			   			  this.map2[this.hero[0]][this.hero[1]-1] != 'G'   &&
			   			  this.map2[this.hero[0]][this.hero[1]-1] != 'O'   )))
		{	
			if( !level1 && this.map2[this.hero[0]][this.hero[1]-1] == 'I'){
				this.map2[this.hero[0]][this.hero[1]-1] = 'S';
				this.hero[1] += 1;
			}

			if (level1)
				this.map[this.hero[0]][this.hero[1]] = this.object_on_top_of;
			else
				this.map2[this.hero[0]][this.hero[1]] = this.object_on_top_of;			
			this.hero[1] -= 1;
		}

		else if ( ('s' == key_pressed || 'S' == key_pressed) && 
			   ((level1 && this.map[this.hero[0]+1][this.hero[1]] != 'X'   &&
			   			   this.map[this.hero[0]+1][this.hero[1]] != 'G'   && 
			   			   this.map[this.hero[0]+1][this.hero[1]] != 'O'   &&  
			   			   this.map[this.hero[0]+1][this.hero[1]] != 'I')  ||
			    (!level1&&this.map2[this.hero[0]+1][this.hero[1]] != 'X'   &&
			    		  this.map2[this.hero[0]+1][this.hero[1]] != 'G'   &&
			    		  this.map2[this.hero[0]+1][this.hero[1]] != 'O'   && 
			    		  this.map2[this.hero[0]+1][this.hero[1]] != 'I')))
		{	
			if (level1)
				this.map[this.hero[0]][this.hero[1]] = this.object_on_top_of;
			else
				this.map2[this.hero[0]][this.hero[1]] = this.object_on_top_of;			
			this.hero[0] += 1;
		}

		else if ( ('d' == key_pressed || 'D' == key_pressed) && 
			   ((level1 && this.map[this.hero[0]][this.hero[1]+1] != 'X' &&
			   			   this.map[this.hero[0]][this.hero[1]+1] != 'G' && 
			   			   this.map[this.hero[0]][this.hero[1]+1] != 'O' &&  
			   			   this.map[this.hero[0]][this.hero[1]+1] != 'I')||
			   	(!level1&&this.map2[this.hero[0]][this.hero[1]+1] != 'X' && 
			   			  this.map2[this.hero[0]][this.hero[1]+1] != 'I' &&
			   			  this.map2[this.hero[0]][this.hero[1]+1] != 'G' && 
			   			  this.map2[this.hero[0]][this.hero[1]+1] != 'O')))
		{	
			if (level1)
				this.map[this.hero[0]][this.hero[1]] = this.object_on_top_of;
			else
				this.map2[this.hero[0]][this.hero[1]] = this.object_on_top_of;			if(
					!level1 && has_key && this.map2[this.hero[0]][this.hero[1]+1] == 'I')
				this.map2[this.hero[0]][this.hero[1]+1] = 'S';
			this.hero[1] += 1;
		}
		else
			return -1; //Unknown key

		this.object_on_top_of = (level1) ? this.map[this.hero[0]][this.hero[1]] : this.map2[this.hero[0]][this.hero[1]] ;

		if(this.object_on_top_of == 'K' && level1){
			this.map[this.final_doors[0][0]][this.final_doors[0][1]] = 'S';		
			this.map[this.final_doors[1][0]][this.final_doors[1][1]] = 'S';
			this.has_key = true;
		}
		else if (this.object_on_top_of == 'K' && !level1){
			this.has_key = true;
			this.object_on_top_of = ' ';
		}
		
		if(level1)
			this.map[this.hero[0]][this.hero[1]] = 'H';
		else if (!level1 && !has_key)
			this.map2[this.hero[0]][this.hero[1]] = 'H';
		else 
			this.map2[this.hero[0]][this.hero[1]] = 'K';

		return 0;
	}

	private int moveNPC(int guard_pos , boolean level1){
		if(level1){
			guard_pos++;
			if(guard_pos > 23)
					guard_pos = 0;

			if (guard_pos != 0)
				this.map[this.guard[guard_pos-1][0]][guard[guard_pos-1][1]] = ' ';
			else
				this.map[this.guard[23][0]][guard[23][1]] = ' ';
			System.out.println("NP");
			this.map[this.guard[guard_pos][0]][guard[guard_pos][1]] = 'G';
			return guard_pos;
		}
		else{
			Random direction=new Random();
			int dir;
			boolean valid=false;
			if(this.ogre[0] == this.key[0] && this.ogre[1] == this.key[1])
				this.map2[this.ogre[0]][this.ogre[1]] = 'K';
			else
				this.map2[this.ogre[0]][this.ogre[1]]=' ';
			
			while(true){
				dir=direction.nextInt(4);
				System.out.println("="+dir);
			 	if(dir == 0 && this.map2[this.ogre[0]-1][this.ogre[1]] != 'X' && 
						   	   this.map2[this.ogre[0]-1][this.ogre[1]] != 'S' && 
						       this.map2[this.ogre[0]-1][this.ogre[1]] != 'I')
			 	{
					this.ogre[0]-=1;
					break;
				}
			
				if(dir == 1 && this.map2[this.ogre[0]][this.ogre[1]-1] != 'X' && 
				        	   this.map2[this.ogre[0]][this.ogre[1]-1] != 'S' && 
				        	   this.map2[this.ogre[0]][this.ogre[1]-1] != 'I')
				{
					this.ogre[1]-=1;
					break;
				}

			 	if(dir == 2 && this.map2[this.ogre[0]+1][this.ogre[1]] != 'X'&&
			 	   			   this.map2[this.ogre[0]+1][this.ogre[1]] != 'S' && 
			 	   			   this.map2[this.ogre[0]+1][this.ogre[1]] != 'I')
			 	{
					this.ogre[0]+=1;
					break;
				}
			 	if(dir == 3 && this.map2[this.ogre[0]][this.ogre[1]+1] != 'X' && 
			 	   			   this.map2[this.ogre[0]][this.ogre[1]+1] != 'S' && 
			 	   			   this.map2[this.ogre[0]][this.ogre[1]+1] != 'I')
			 	{
				this.ogre[1]+=1;
					break;
				}
					
			}
			
			if(this.ogre[0] == this.key[0] && this.ogre[1] == this.key[1])
				this.map2[this.ogre[0]][this.ogre[1]]='$';
			else
				this.map2[this.ogre[0]][this.ogre[1]] = 'O';	
			

		}
		

		return 0;
	}

	private boolean checkGameOver( int[] NPC){
		//System.out.println("Guard = ["+this.guard[0]+","+this.guard[1]+"] , Hero = ["+this.hero[0]+","+this.hero[1]+"]\n");
		if( ( (NPC[0]-1) == this.hero[0] && NPC[1] == this.hero[1] ) ||
			( (NPC[0]+1) == this.hero[0] && NPC[1] == this.hero[1] ) ||
			(  NPC[0] == this.hero[0] && (NPC[1]-1) == this.hero[1]) ||
			(  NPC[0] == this.hero[0] && (NPC[1]+1) == this.hero[1]) )
			return true;
		else
			return false;
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
		//System.out.println("'"+line+"'");
		return key;
	}
}
