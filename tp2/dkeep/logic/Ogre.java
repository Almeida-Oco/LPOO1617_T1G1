package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;

public class Ogre extends GameCharacter {
	private Pair<Integer,Integer> club = new Pair<Integer,Integer>(-1,-1);
	private String club_representation = "*";
	private int stun=0;
	private boolean nearkill=true;
	
	public Ogre(int x , int y, int MAP_SIZE, boolean near){
		super(x,y);
		this.nearkill= near;
		this.representation = "O";
		if (x >= 0 && x <= MAP_SIZE && y >= 0 && y <= MAP_SIZE)
			this.club = new Pair<Integer,Integer>(x,y);
		
	}
	
	//change must be value returned by checkOverlap in GameLogic
	public ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change , int MAP_SIZE){;		
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >(current.subList(0, change));

		if( 0 == this.stun && 0 == change){ //If he is not stunned and is supposed to change all positions
			Random rand = new Random();
			while(temp.size() == 0){
				int dir = rand.nextInt(4);

				if		(dir == 0 && (this.position.get(0).getFirst().intValue()-1) >= 0)// move up
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()-1 , this.position.get(0).getSecond().intValue()) );

				else if (dir == 1 && (this.position.get(0).getFirst().intValue()+1) < MAP_SIZE) //move down
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()+1 , this.position.get(0).getSecond().intValue()));

				else if (dir == 2 && (this.position.get(0).getSecond().intValue()-1) >= 0) //move left
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()-1));

				else if (dir == 3 && (this.position.get(0).getSecond().intValue()+1) < MAP_SIZE) //move right
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()+1));
			}
		}else if (this.stun > 0) //if he is stunned only move club, maintain current ogre position
			temp = (ArrayList<Pair<Integer,Integer> >)this.position.clone();
		
		temp.add( moveClub( temp.get(0) , MAP_SIZE));
		return temp;
	}

	public Pair<Integer,Integer> moveClub(Pair<Integer,Integer> pos , int MAP_SIZE){
		Random rand = new Random();
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(-1,-1);
		while(temp.getFirst().intValue() == -1){
			int dir = rand.nextInt(4);
			if		(dir == 0 && (pos.getFirst().intValue()-1) >= 0) // move up
				temp = new Pair<Integer,Integer>( pos.getFirst().intValue()-1, pos.getSecond().intValue());
			
			else if (dir == 1 && (pos.getFirst().intValue()+1) < MAP_SIZE) //move down
				temp = new Pair<Integer,Integer>( pos.getFirst().intValue()+1, pos.getSecond().intValue());
			
			else if (dir == 2 && (pos.getSecond().intValue()-1) >= 0) //move left
				temp = new Pair<Integer,Integer>( pos.getFirst().intValue() ,  pos.getSecond().intValue()-1);
			
			else if (dir == 3 && (pos.getSecond().intValue()+1) < MAP_SIZE) //move right
				temp = new Pair<Integer,Integer>( pos.getFirst().intValue() ,  pos.getSecond().intValue()+1);
			
		}
		return temp;
	}
	
	//Club must !ALWAYS! be in the last position
	@Override
	public int setPos(ArrayList<Pair<Integer, Integer>> vp, int MAP_SIZE) { 
		int i = 0;
		for ( Pair<Integer,Integer> p : vp){
			if (p.getFirst() >= 0 && p.getFirst() < MAP_SIZE && p.getSecond() >= 0 && p.getSecond() < MAP_SIZE) {
				if ( i != vp.size()-1) 
					this.position.set(i++, p);
				else 
					setClub(p,MAP_SIZE);
			}
			else //if fail to set ogre position
				return i;
		}
		return -1;
	}

	public boolean setClub(Pair<Integer,Integer> p, int MAP_SIZE){
		if (p.getFirst().intValue() >= 0 && p.getFirst().intValue() <= MAP_SIZE && p.getSecond().intValue() >= 0 && p.getSecond().intValue() <= MAP_SIZE){
			this.club = p;
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
		for(Pair<Integer,Integer> p : this.position)
			temp.add( new Pair< Pair<Integer,Integer> ,String>(p,this.representation));
		
		return temp;
	}
	
	@Override	
	public ArrayList<Pair<Integer, Integer>> getGameOverPos(){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList< Pair<Integer,Integer> >();
		temp.add(this.club);
		if(this.nearkill)
			for (int i = 0 ; i < this.position.size() ; i++)
				temp.add( this.position.get(i));
		return temp;
	}
	
	public void setClubRepresentation(String s){
		this.club_representation=s;
	}
	
	public void stunOgre(){
		this.stun=2;
		representation="8";
	}
	
	public void roundPassed(){
		if(stun > 0){
			this.stun --;
			representation= (this.stun==0) ? "O" : "8";
		}
	}
}