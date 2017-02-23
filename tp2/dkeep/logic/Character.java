public abstract class Character {
	private int[] position;
	private char representation;

	public String toString(){
		return this.representation;
	}
	public abstract boolean moveCharacter(int x , int y);

	public int getX(){
		return this.representation[1];
	}
	public int getY(){
		return this.representation[0];
	}

	public abstract boolean checkGameOver(int x , int y);
}