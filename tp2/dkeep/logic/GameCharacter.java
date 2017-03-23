package dkeep.logic;
import pair.Pair;
import java.util.ArrayList;	

/**
 * @brief Represents a single Character of the game
 * @var position Current position(s) of the character
 * @var representation The String representation of the Character
 * @details Possible to have multi tiles characters
 */
public abstract class GameCharacter implements java.io.Serializable{
	protected ArrayList< Pair<Integer,Integer> > position;
	protected String representation;
	
	/**
	 * @brief Constructor
	 * @param pos Pair with (x,y) coordinate of character
	 * @param map_size Pair with (width,height) of map
	 * @details Before initializing the x and y coordinates it check if they are valid
	 */
	public GameCharacter( Pair<Integer,Integer> pos, Pair<Integer,Integer> map_size){
		ArrayList<Pair<Integer,Integer> > temp = new ArrayList<Pair<Integer,Integer> >();
		if(pos.getFirst() >= 0 && pos.getFirst() < map_size.getSecond() && pos.getSecond() >= 0 && pos.getSecond() < map_size.getFirst() ){
			temp.add(pos);
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
	 * @param change Value returned by checkOverlap from GameLogic or isFree from Map, determines which part of the returned array is overlapping
	 * @return Returns an array with all the positions all objects associated with the character will try to move to
	 * 		   For more complex game mechanics override this function
	 */
	public abstract ArrayList< Pair<Integer,Integer> > moveCharacter(ArrayList<Pair<Integer,Integer> > current,int change); 
	
	/**
	 * @brief Sets the character position to the values in the array
	 * @param vp Array returned by moveCharacter 
	 * @details For more complex game mechanics override this function
	 */
	public void setPos(ArrayList<Pair<Integer,Integer> > vp){
		int i = 0;
		for ( Pair<Integer,Integer> p : vp)
				this.position.set(i++ , p);
	}
	
	/**
	 * @brief Gets the position of the character
	 * @return Clone of the current position
	 */
	public ArrayList< Pair<Integer,Integer> > getPos(){
		return (ArrayList<Pair<Integer,Integer> >)this.position.clone();
	}
	
	/**
	 * @brief Get X coordinate
	 * @return X coordinate
	 */
	public int getX(){
		return this.position.get(0).getFirst().intValue();
	}
	
	/**
	 * @brief Get Y coordinate
	 * @return Y coordinate
	 */
	public int getY(){
		return this.position.get(0).getSecond().intValue();
	}
	
	/**
	 * @brief Sets the character representation
	 * @param s String to set the representation (can be longet than 1 char, but the game will only use the first char)
	 */
	public void setRepresentation(String s ){
		this.representation=s;
	}

	/**
	 * @brief Gets what to print for this character 
	 * @param to_file Whether this printable is supposed to go to a file or not
	 * @return Array with position to paint and what String to paint there
	 */
	public abstract ArrayList< Pair< Pair<Integer,Integer> ,String> > getPrintable( boolean to_file);
	
	/**
	 * @brief Gets the gameOver position for this character
	 * @return Array with Game Over Positions, if Hero is in that tile or adjacent then he loses
	 */
	public abstract ArrayList<Pair<Integer, Integer>> getGameOverPos();

//	public abstract static GameCharacter parseCharacter(String line);
//	
//	private abstract static GameCharacter matchType( Pair<Integer,Integer> pos , char chr);
//	
	//TODO check errors function?
//	private boolean lineErrorFree(String line){
//		
//	}
}