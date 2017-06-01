package Controller;

import java.util.LinkedList;

//TODO check velocity for different screens
public class GameLogic {
    private static GameLogic instance;
    //!Mario should always be first character!
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    //TODO find way to initialize Mario always in same relative position
    private GameLogic(){}

    public static GameLogic getInstance(){
        if (instance == null)
            return (instance = new GameLogic());
        else
            return instance;
    }

    public void initializeCharacters(){
        Pair<Integer,Integer>   mario_pos = new Pair<Integer, Integer>(4,7),
                                barrel_pos =new Pair<Integer, Integer>(7, 222);
        mario_pos = this.map.mapPosToPixels(mario_pos);
        barrel_pos= this.map.mapPosToPixels(barrel_pos);
        this.chars.add( Mario.createMario(mario_pos.getFirst(), mario_pos.getSecond()));
        this.chars.add( Barrel.createBarrel(barrel_pos.getFirst(),barrel_pos.getSecond()));
        this.chars.add(DonkeyKong.getInstance());
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
    public void animateDK(){
        ((DonkeyKong)this.chars.get(2)).catchThrow();
    }

    public void moveMario(int x_move, int y_move){
        this.chars.set( 0 , ((Mario)this.chars.getFirst()).moveMario(this.map,x_move,y_move) );
    }

    public void moveBarrel(){
        //System.out.println("x: "+((Barrel)this.chars.get(1)).getX()+" "+"y: "+ ((Barrel)this.chars.get(1)).getY());

        this.chars.set( 1 , ((Barrel)this.chars.get(1)).moveBarrel(this.map) );
    }
}
