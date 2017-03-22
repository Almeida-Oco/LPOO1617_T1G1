package dkeep.logic;
import java.util.ArrayList;

import pair.Pair;

public class RookieGuard extends Guard{
	public RookieGuard(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super( pos , map_size);
	}
	
	public RookieGuard(Pair<Integer,Integer> map_size){
		super( Guard.movement.get( movement.size()-1) , map_size);
	}
	
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		super.setPos(vp);
	}
	
	public ArrayList<Pair<Pair<Integer,Integer> , String > > getPrintable(boolean to_file){
		if (to_file)
			this.representation = "R";
		ArrayList<Pair<Pair<Integer,Integer> , String > > temp = super.getPrintable(to_file);
		this.representation = "G";
		return temp;
	}
}