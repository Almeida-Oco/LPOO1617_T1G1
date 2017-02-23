public class DungeonMap extends Map{
	public DungeonMap(){
		this.map = {{'X','X','X','X','X','X','X','X','X','X'} ,
					{'X',' ',' ',' ','I',' ','X',' ',' ','X'} ,
					{'X','X','X',' ','X','X','X',' ',' ','X'} ,
					{'X',' ','I',' ','I',' ','X',' ',' ','X'} ,
					{'X','X','X',' ','X','X','X',' ',' ','X'} ,
					{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} ,
					{'X','X','X',' ','X','X','X','X',' ','X'} ,
					{'X',' ','I',' ','I',' ','X','K',' ','X'} ,
					{'X','X','X','X','X','X','X','X','X','X'} };
	}

	public Map nextMap(){
		return new ArenaMap();
	}
}