package dkeep.logic;
import java.util.ArrayList;

import pair.Pair;

public class RookieGuard extends Guard{
	public RookieGuard(int x , int y){
		super(new Pair<Integer,Integer>(x,y) );
	}
	
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		super.setPos(vp);
	}
	
	public RookieGuard(){
		super( Guard.movement.get( movement.size()-1));
	}
}