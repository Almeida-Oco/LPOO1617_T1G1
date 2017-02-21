class Character {
	private int map_size = 8;
	private int[] position = {-1,-1};
	public static void main(String[] args) {
		
	}

	public Character(int x , int y){
		if( x >= 0 && x <= this.map_size && y >= 0 && y <= this.map_size){
			position[0] = x;
			position[1] = y;
		}
	}


	private boolean moveCharacter(boolean vertical, int steps){
		if (vertical && steps > 0 && (position[0]-steps))
	}
}