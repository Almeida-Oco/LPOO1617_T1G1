class Character {
	private int[] position = {-1,-1};

	public String toString()

	public static void main(String[] args) {
		
	}

	public Character(int x , int y){
		if( x >= 0 && x <= this.map_size && y >= 0 && y <= this.map_size){
			position[0] = x;
			position[1] = y;
		}
	}


	private boolean moveCharacter(int x , int y){
		if( x >= 0 && x <= this.map_size && y >= 0 && y <= this.map_size){
			position[0] = x;
			position[1] = y;
			return true;
		}
		else 
			return false;

	}
}