package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;	

/**
 * @brief Represents a single Character of the game
 * @var position Current position(s) of the character
 * @var representation The String representation of the Character
 * @details Possible to have multi tiles characters
 */
public abstract class GameCharacter {
	protected ArrayList< Pair<Integer,Integer> > position;
	protected String representation;
	
	/**
	 * @brief Constructor
	 * @param x X Position 
	 * @param y Y Position
	 */
	public GameCharacter(int x , int y){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		if(x >= 0 && y >= 0){
			temp.add(new Pair<Integer,Integer>(x,y));
			this.position = temp;
		}
	}
	
	/**
	 * @brief Converts class to String
	 * @return String representing the class
	 */
	public String toString(){
		return this.representation;
	}
	
	/**
	 * @brief Moves the character
	 * @param current Used in case there's a need to recalculate something, such as the club, current will hold all positions which were valid after a first pass on this function
	 * @param change Value returned by checkOverlap in GameLogic, determines which part of the returned array is overlapping
	 * @param MAP_SIZE Size of the current game map
	 * @return Returns an array with all the positions all objects associated with the character will try to move to
	 */
	public abstract ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change ,int MAP_SIZE); 
	
	/**
	 * @brief Sets the character position to the values in the array
	 * @param vp Array returned by moveCharacter (must be Overwritten for more complex game mechanics)
	 * @param MAP_SIZE Size of the current gameMap
	 * @return True if assignment was successful , false otherwise
	 */
	public boolean setPos(ArrayList<Pair<Integer,Integer> > vp, int MAP_SIZE){
		int i = 0;
		for ( Pair<Integer,Integer> p : vp){
			if (p.getFirst() >= 0 && p.getFirst() < MAP_SIZE && p.getSecond() >= 0 && p.getSecond() < MAP_SIZE) {
				this.position.set(i, p);
				i++;
			}
		}
		return (i!=0);
	}
		
	public ArrayList< Pair<Integer,Integer> > getPos(){
		return (ArrayList<Pair<Integer,Integer> >)this.position.clone();
	}
	
	public int getX(){
		return this.position.get(0).getFirst().intValue();
	}
	
	public int getY(){
		return this.position.get(0).getSecond().intValue();
	}
	
	public void setRepresentation(String s ){
		this.representation=s;
	}

	/**
	 * @brief Gets what to print for this character 
	 * @return Array with position to paint and what String to paint there
	 */
	public abstract ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable();
	
	/**
	 * @brief Gets the gameOver position for this character
	 * @return Array with Game Over Positions, if Hero is in that tile or adjacent then he loses
	 */
	public abstract ArrayList<Pair<Integer, Integer>> getGameOverPos();
}