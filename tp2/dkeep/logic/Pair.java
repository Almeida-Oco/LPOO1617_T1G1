package dkeep.logic;

public class Pair<L,R> implements java.io.Serializable{
	private L first;
	private R second;
	
	/**
	 * Constructs a Pair
	 * @param left First value
	 * @param right Second Value
	 */
	public Pair(L left , R right){
		this.first = left;
		this.second = right;
	}
	
	/**
	 * @return First Value
	 */
	public L getFirst(){
		return this.first;
	}
	
	/**
	 * @return Second Value
	 */
	public R getSecond(){
		return this.second;
	}
	
	/**
	 * Sets the first value
	 * @param f Value to set
	 */
	public void setFirst(L f){
		this.first = f;
	}
	
	/**
	 * Sets the second value
	 * @param f Value to set
	 */
	public void setSecond(R s){
		this.second = s;
	}
	
	/**
	 * Converts to string
	 * @return String of type "[x,y]"
	 */
	public String toString(){
		return "["+this.first.toString()+","+this.second.toString()+"]";
	}

	/**
	 * Compares 2 Pair objects
	 * @return true if they have same value, false if not or not instance of Pair
	 */
	public boolean equals(Object o){
		if ( !(o instanceof Pair))
			return false;
		else{
			Pair<L,R> o2 = (Pair<L,R>)o;
			Pair<L,R> p = o2;
			return (this.first.equals(p.getFirst()) && this.second.equals(p.getSecond()) );
		}
	}
	
	/**
	 * @return Equivalent Pair object
	 */
	public Object clone(){
		return new Pair<L,R>(this.first,this.second);
	}
} 
