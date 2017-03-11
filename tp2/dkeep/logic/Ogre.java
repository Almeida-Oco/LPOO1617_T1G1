package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;

public class Ogre extends Character {
	private Pair<Integer,Integer> club = new Pair<Integer,Integer>(-1,-1);
	private String club_representation = "*";
	private int stun=0;
	private boolean nearkill=true;
	
	public Ogre(int x , int y, int MAP_SIZE, boolean near){
		super(x,y);
		this.nearkill= near;
		this.representation = "O";
		if (x >= 0 && x <= MAP_SIZE && y >= 0 && y <= MAP_SIZE){
			this.position = new Pair<Integer,Integer>(x,y);
			this.club = new Pair<Integer,Integer>(x,y);
		}
			
		
	}

	public Pair<Integer,Integer> moveCharacter(int MAP_SIZE){
		
		if(stun==0){
			
			Random rand = new Random();
			Pair<Integer,Integer> temp = new Pair<Integer,Integer>(-1,-1);
			while(temp.getFirst().intValue() == -1){
				int dir = rand.nextInt(4);

				if(dir == 0 && (this.position.getFirst().intValue()-1) >= 0)// move left
					temp = new Pair<Integer,Integer>( this.position.getFirst().intValue()-1 , this.position.getSecond().intValue());
				else if (dir == 1 && (this.position.getFirst().intValue()+1) < MAP_SIZE) //move right
					temp = new Pair<Integer,Integer>( this.position.getFirst().intValue()+1 , this.position.getSecond().intValue());
				else if (dir == 2 && (this.position.getSecond().intValue()-1) >= 0) //move up
					temp = new Pair<Integer,Integer>( this.position.getFirst().intValue() , this.position.getSecond().intValue()-1);
				else if (dir == 3 && (this.position.getSecond().intValue()+1) < MAP_SIZE) //move down
					temp = new Pair<Integer,Integer>( this.position.getFirst().intValue() , this.position.getSecond().intValue()+1);
			}
			return temp;
		}
		else
			return position;
	}
	

	public Pair<Integer,Integer> moveClub(int MAP_SIZE){
		Random rand = new Random();
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(-1,-1);
		while(temp.getFirst().intValue() == -1){
			int dir = rand.nextInt(4);
			if(dir == 0 && (this.position.getFirst().intValue()-1) >= 0) // move left
				temp = new Pair<Integer,Integer>( this.position.getFirst().intValue()-1 , this.position.getSecond().intValue());
			else if (dir == 1 && (this.position.getFirst().intValue()+1) <= MAP_SIZE) //move right
				temp = new Pair<Integer,Integer>( this.position.getFirst().intValue()+1 , this.position.getSecond().intValue());
			else if (dir == 2 && (this.position.getSecond().intValue()-1) >= 0) //move up
				temp = new Pair<Integer,Integer>( this.position.getFirst().intValue() , this.position.getSecond().intValue()-1);
			else if (dir == 3 && (this.position.getSecond().intValue()+1) <= MAP_SIZE) //move down
				temp = new Pair<Integer,Integer>( this.position.getFirst().intValue() , this.position.getSecond().intValue()+1);
			
		}
		return temp;
	}

	public boolean setClub(int x , int y, int MAP_SIZE){
		if (x >= 0 && x <= MAP_SIZE && y >= 0 && y <= MAP_SIZE){
			this.club = new Pair<Integer,Integer>(x,y);
			return true;
		}
		return false;
	}

	public int getClubX(){
		return this.club.getFirst().intValue();
	}
	public int getClubY(){
		return this.club.getSecond().intValue();
	}

	public ArrayList<Pair<Pair<Integer, Integer>, String>> getPrintable(){
		ArrayList< Pair< Pair<Integer,Integer> ,String> > temp = new ArrayList< Pair< Pair<Integer,Integer> ,String> >(2);
		temp.add( new Pair< Pair<Integer,Integer> ,String>(this.club,this.club_representation));
		temp.add( new Pair< Pair<Integer,Integer> ,String>(this.position,this.representation));
		
		return temp;
	}

	public ArrayList<Pair<Integer, Integer>> getGameOverPos(int level){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList< Pair<Integer,Integer> >(2);
		if(level == 1){
			temp.add(this.club);
			if(this.nearkill){
			temp.add(this.position);
			}
		}
		return temp;
	}
	
	public void setClubRepresentation(String s ){
		this.club_representation=s;
	}
	
	public void stunOgre(){
		this.stun=2;
		representation="8";
	}
	
	public void roundPassed(){
		if(stun >0){
		this.stun --;
		representation="8";
		}
	}
	
	
	

}