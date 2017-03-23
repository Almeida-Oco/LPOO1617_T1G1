package dkeep.logic;
import java.util.ArrayList;
import java.util.Random;

import pair.Pair;


public class Ogre extends GameCharacter {
	public static final int STUN_ROUNDS = 2;
	
	private Pair<Integer,Integer> club = new Pair<Integer,Integer>(-1,-1);
	private String club_representation = "*";
	private int stun=0;
	
	
	public Ogre(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super(pos , map_size);
		this.representation = "O";
		if(pos.getFirst() >= 0 && pos.getFirst() < map_size.getSecond() && pos.getSecond() >= 0 && pos.getSecond() < map_size.getFirst() )
			this.club = pos;
		
	}
	
	public ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change){		
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >(current.subList(0, change));

		if( 0 == this.stun && 0 == change){ //If he is not stunned and is supposed to change all positions
			Random rand = new Random();
			int dir = rand.nextInt(4);

			if		(dir == 0)// move up
				temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()-1 , this.position.get(0).getSecond().intValue()) );
			else if (dir == 1) //move down
				temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue()+1 , this.position.get(0).getSecond().intValue()));
			else if (dir == 2) //move left
				temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()-1));
			else if (dir == 3) //move right
				temp.add(new Pair<Integer,Integer>( this.position.get(0).getFirst().intValue() , this.position.get(0).getSecond().intValue()+1));

		}else if (this.stun > 0) //if he is stunned only move club, maintain current ogre position
			temp = (ArrayList<Pair<Integer,Integer> >)this.position.clone();

		temp.add( moveClub( temp.get(0)));
		return temp;
	}
	
	/**
	 * @brief Moves the ogre club
	 * @param pos Position of the ogre
	 * @return Position where it should put the club (this position will then be verified)
	 */
	private Pair<Integer,Integer> moveClub(Pair<Integer,Integer> pos){
		Random rand = new Random();
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(-1,-1);
		int dir = rand.nextInt(4);

		if		(dir == 0) // move up
			temp = new Pair<Integer,Integer>( pos.getFirst().intValue()-1, pos.getSecond().intValue());
		else if (dir == 1) //move down
			temp = new Pair<Integer,Integer>( pos.getFirst().intValue()+1, pos.getSecond().intValue());
		else if (dir == 2) //move left
			temp = new Pair<Integer,Integer>( pos.getFirst().intValue() ,  pos.getSecond().intValue()-1);
		else if (dir == 3) //move right
			temp = new Pair<Integer,Integer>( pos.getFirst().intValue() ,  pos.getSecond().intValue()+1);
		return temp;
	}
	
	/**
	 * @brief Sets the ogre and club position
	 * @param vp Array of positions (!last position must always be the club!)
	 */
	@Override
	public void setPos(ArrayList<Pair<Integer, Integer>> vp) { 
		int i = 0;
		for ( Pair<Integer,Integer> p : vp)
			if ( i != vp.size()-1) 
				this.position.set(i++, p);
			else 
				setClub(p);
	}

	public void setClub(Pair<Integer,Integer> p){
		if( p != null)
			this.club = p;
	}	

	public Pair<Integer,Integer> getClub(){
		return this.club;
	}

	public ArrayList<Pair<Pair<Integer, Integer>, String>> getPrintable( boolean to_file){
		ArrayList< Pair< Pair<Integer,Integer> ,String> > temp = new ArrayList< Pair< Pair<Integer,Integer> ,String> >(2);
		temp.add( new Pair< Pair<Integer,Integer> ,String>(this.club,this.club_representation));
		for(Pair<Integer,Integer> p : this.position){
			if (to_file && this.stun > 0)
				temp.add( new Pair< Pair<Integer,Integer> ,String>(p,Integer.toString(this.stun)));
			else
				temp.add( new Pair< Pair<Integer,Integer> ,String>(p,this.representation));
		}
			
		
		return temp;
	}
	
	@Override	
	public ArrayList<Pair<Integer, Integer>> getGameOverPos(){
		ArrayList< Pair<Integer,Integer> > temp = new ArrayList< Pair<Integer,Integer> >();
		temp.add(this.club);
		for (int i = 0 ; i < this.position.size() ; i++)
			temp.add( this.position.get(i));
		return temp;
	}
	
	public void setClubRepresentation(String s){
		this.club_representation=s;
	}
	
	public void stunOgre( int rounds ){
		this.stun=rounds;
		if(rounds > 0)
			representation="8";
	}
	
	public void roundPassed(){
		if(stun > 0){
			this.stun --;
			representation= (this.stun==0) ? "O" : "8";
		}
	}
}