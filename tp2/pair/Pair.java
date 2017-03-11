package pair;

public class Pair<L,R> {
	private L first;
	private R second;
	
	public Pair(L left , R right){
		this.first = left;
		this.second = right;
	}

	public L getFirst(){
		return this.first;
	}
	
	public R getSecond(){
		return this.second;
	}
	
	public void setFirst(L f){
		this.first = f;
	}
	
	public void setSecond(R s){
		this.second = s;
	}
	
	public String toString(){
		return "<"+this.first.toString()+","+this.second.toString()+">";
	}

	public boolean equals(Object o){
		if ( !(o instanceof Pair))
			return false;
		else{
			Pair<L,R> o2 = (Pair<L,R>)o;
			Pair<L,R> p = o2;
			return (this.first.equals(p.getFirst()) && this.second.equals(p.getSecond()) );
		}
	}

	public Object clone(){
		return new Pair<L,R>(this.first,this.second);
	}
} 
