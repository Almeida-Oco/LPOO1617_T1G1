package dkeep.logic;

public class GameLogic{
	private Map map;
	private Ogre ogres[];
	private Hero hero ;
	private Guard guard;
	private int[] key;
	
	public Game(Map game_map){
		map =game_map;
		hero=new Hero();
		guard=new Guard();
		
	}
	
	public boolean isGameOver( ){
	
		for(int i=0; i<ogres.size();i++){
			if(ogres[i].checkGameOver())
				return true;
		}
		if(guard.checkGameOVer())
		return true;
		
		else
			return false;
	}
	

	public Map getGameMap(){
		return map;
		}
	
	
}


	
