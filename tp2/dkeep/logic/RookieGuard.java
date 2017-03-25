package dkeep.logic;
import java.util.ArrayList;

public class RookieGuard extends Guard{
	private static final long serialVersionUID = 1944373727124139128L;

	public RookieGuard(Pair<Integer,Integer> pos , Pair<Integer,Integer> map_size){
		super( pos , map_size);
	}
	
	public RookieGuard(Pair<Integer,Integer> map_size){
		super( Guard.movement.get( movement.size()-1) , map_size);
	}
	
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		super.setPos(vp);
	}
}