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
		if (x >= 0 && x <= MAP_SIZE && y >= 0 && y <= MAP_SIZE)
			this.club = new Pair<Integer,Integer>(x,y);
		
	}
	
	//change must be value returned by checkOverlap in GameLogic
	public ArrayList< Pair<Integer,Integer> > moveCharacter(int change , int MAP_SIZE){;		
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		if(stun==0 && change == 0){
			Random rand = new Random();
			while(temp.get(0).getFirst().intValue() == -1){
				int dir = rand.nextInt(4);
				
				if(dir == 0 && (this.position.get(0).getFirst().intValue()-1) >= 0)// move left
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()-1 , this.position.get(0).getSecond().intValue()) );
				else if (dir == 1 && (this.position.get(0).getFirst().intValue()+1) < MAP_SIZE) //move right
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()+1 , this.position.get(0).getSecond().intValue()));
				else if (dir == 2 && (this.position.get(0).getSecond().intValue()-1) >= 0) //move up
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()-1));
				else if (dir == 3 && (this.position.get(0).getSecond().intValue()+1) < MAP_SIZE) //move down
					temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()+1));
			}
		}
		else if (change == 1)
			temp.add( moveClub(MAP_SIZE) );
		return temp;
	}

	//Club must !ALWAYS! be in the last position
	@Override
	public boolean setPos(ArrayList<Pair<Integer, Integer>> vp, int MAP_SIZE) { 
		int i = 0;
		for ( Pair<Integer,Integer> p : vp){
			if (p.getFirst() >= 0 && p.getFirst() < MAP_SIZE && p.getSecond() >= 0 && p.getSecond() < MAP_SIZE) {
				if ( i != vp.size()-1) 
					this.position.set(i, p);
				else
					this.club = p;
				return true;
			}
			i++;			
		}
		return false;
	}
	
	public Pair<Integer,Integer> moveClub(int MAP_SIZE){
		Random rand = new Random();
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(-1,-1);
		while(temp.getFirst().intValue() == -1){
			int dir = rand.nextInt(4);
			if(dir == 0 && (this.position.get(0).getFirst().intValue()-1) >= 0) // move left
				temp = new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()-1 , this.position.get(0).getSecond().intValue());
			else if (dir == 1 && (this.position.get(0).getFirst().intValue()+1) <= MAP_SIZE) //move right
				temp = new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()+1 , this.position.get(0).getSecond().intValue());
			else if (dir == 2 && (this.position.get(0).getSecond().intValue()-1) >= 0) //move up
				temp = new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()-1);
			else if (dir == 3 && (this.position.get(0).getSecond().intValue()+1) <= MAP_SIZE) //move down
				temp = new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()+1);
			
		}
		return temp;
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
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList< Pair<Integer,Integer> >(2);
		temp.add(this.club);
		if(this.nearkill)
			temp.addAll((ArrayList<Pair<Integer,Integer> >)this.position.subList(0, this.position.size()-2 ));
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
		if(stun >0){
			this.stun --;
			representation="8";
		}
	}

	@Override
	public ArrayList<Pair<Integer, Integer>> getGameOverPos(int level) {
		// TODO Auto-generated method stub
		return null;
	}



}