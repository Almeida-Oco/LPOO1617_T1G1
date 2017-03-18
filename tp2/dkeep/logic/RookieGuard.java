package dkeep.logic;
import java.util.ArrayList;

import pair.Pair;

public class RookieGuard extends Guard{
	public RookieGuard(int x , int y){
		super(new Pair<Integer,Integer>(x,y) );
	}
	
	public boolean setPos(ArrayList<Pair<Integer,Integer> > vp , int MAP_SIZE){
		return super.setPos(vp, MAP_SIZE);
	}
	
	public RookieGuard(){
		super( Guard.movement.get( movement.size()-1));
	}
}