package Controller;

import java.util.LinkedList;

//TODO check velocity for different screens
public class GameLogic {
    private static GameLogic instance;
    //!Mario should always be first character!
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    //TODO find way to initialize Mario always in same relative position
    private GameLogic(){
        this.chars.add( Mario.createMario( 150, 38 ) );
    }


    public static GameLogic getInstance(){
        if (instance == null)
            return (instance = new GameLogic());
        else
            return instance;
    }

    public LinkedList<Controller.Entity> getCharacters(){
        return this.chars;
    }

    public Controller.Map getMap(){
        return this.map;
    }

    public void setMap(String map_name, String collision_layer){
        this.map = new Map();
        this.map.loadMap(map_name, collision_layer);
    }

    public void moveMario(int x_move, int y_move){
        this.chars.set( 0 , ((Mario)this.chars.getFirst()).moveMario(this.map,x_move,y_move) );
    }

}
