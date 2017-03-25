package dkeep.logic;
import java.util.ArrayList;	

/**
 * @author Joao Almeida, Jose Pedro Machado
 */
public abstract class GameCharacter implements java.io.Serializable{
	protected ArrayList< Pair<Integer,Integer> > position;
	protected String representation;
	
	/**
	 * Constructor
	 * @param pos Pair with (x,y) coordinate of character
	 * @param map_size Pair with (width,height) of map
	 * Before initializing the x and y coordinates it check if they are valid
	 */
	public GameCharacter( Pair<Integer,Integer> pos, Pair<Integer,Integer> map_size){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		if(pos.getFirst() >= 0 && pos.getFirst() < map_size.getSecond() && pos.getSecond() >= 0 && pos.getSecond() < map_size.getFirst() ){
			temp.add(pos);
			this.position = temp;
		}
	}
	
	/**
	 * Converts class to String
	 * @return String representing the class
	 */
	public String toString(){
		return this.representation;
	}
	
	/**
	 * Moves the character
	 * @param current Used in case there's a need to recalculate something, such as the club, current will hold all positions which were valid after a first pass on this function
	 * @param change Value returned by checkOverlap from GameLogic or isFree from Map, determines which part of the returned array is overlapping
	 * 		  Returns an array with all the positions all objects associated with the character will try to move to
	 */
	public abstract ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change); 
	
	/**
	 * Sets the character position to the values in the array
	 * @param vp Array returned by moveCharacter 
	 * For more complex game mechanics override this function
	 */
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		int i = 0;
		for ( Pair<Integer,Integer> p : vp)
				this.position.set(i++ , p);
	}
	
	/**
	 *  Gets the position of the character
	 * @return Clone of the current position
	 */
	public ArrayList< Pair<Integer,Integer> > getPos(){
		return (ArrayList<Pair<Integer,Integer> >)this.position.clone();
	}
	
	/**
	 * Action to perform when GameCharacter on top of key
	 * @param pos Position of key (more complex game mechanics can use this with other positions)
	 */
	public abstract void checkKeyTriggers( Pair<Integer,Integer> pos);
	

	/**
	 * Gets what to print for this character 
	 * @return Array with position to paint and what String to paint there
	 */
	public abstract ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable();
	
	/**
	 *  Gets the gameOver position for this character
	 *  @return Array with Game Over Positions, if Hero is in that tile or adjacent then he loses
	 */
	public abstract ArrayList<Pair<Integer, Integer>> getGameOverPos();
	
	/**
	 * Changes parameter pos based on direction
	 * @param pos Position to change
	 * @param dir Direction to go
	 * @return parameter pos
	 */
	protected Pair<Integer,Integer> changePos(Pair<Integer,Integer> pos , int dir){
		if		(dir == 0)// move up
			return new Pair<Integer,Integer>( pos.getFirst()-1 , pos.getSecond()) ;
		else if (dir == 1) //move down
			return new Pair<Integer,Integer>( pos.getFirst()+1 , pos.getSecond());
		else if (dir == 2) //move left
			return new Pair<Integer,Integer>( pos.getFirst() , pos.getSecond()-1);
		else if (dir == 3) //move right
			return new Pair<Integer,Integer>( pos.getFirst() , pos.getSecond()+1);
		
		return pos;
	}
}