package Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

//TODO check velocity for different screens
public class GameLogic {
    private static GameLogic instance;
    //!Mario should always be first character!
    private ArrayList<Entity> chars = new ArrayList<Controller.Entity>();

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
                                barrel_pos =new Pair<Integer, Integer>(5, 222),
                                DK_pos = new Pair<Integer, Integer>(3,222);
        mario_pos = this.map.mapPosToPixels(mario_pos);
        barrel_pos= this.map.mapPosToPixels(barrel_pos);
        DK_pos= this.map.mapPosToPixels(DK_pos);
        this.chars.add( Mario.createMario(mario_pos.getFirst(), mario_pos.getSecond()));
        Entity DK = DonkeyKong.getInstance();
        DK.setPos(DK_pos);
        this.chars.add( DK );
        this.chars.add( Barrel.createBarrel(barrel_pos.getFirst(),barrel_pos.getSecond()) );
        this.chars.add( Barrel.createBarrel(barrel_pos.getFirst(),barrel_pos.getSecond()) );
        this.chars.add( Barrel.createBarrel(barrel_pos.getFirst(),barrel_pos.getSecond()) );
        this.chars.add( Barrel.createBarrel(barrel_pos.getFirst(),barrel_pos.getSecond()) );
    }

    public ArrayList<Entity> getCharacters(){
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
        //((DonkeyKong)this.chars.get(2)).catchThrow();
    }
    public void Kong(){
        ((DonkeyKong)this.chars.get(1)).Kong();
    }

    public void moveMario(int x_move, int y_move){
        this.chars.set( 0 , this.chars.get(0).moveEntity(this.map,x_move,y_move) );
    }

    public void moveBarrel(){
        for (int i = 2 ; i < this.chars.size() ; i++){
            if ( this.chars.get(i).collidesWith(this.chars.get(0).getPos(), this.chars.get(0).getRepSize()) )
                this.chars.remove(i);
            else
                this.chars.set(i,this.chars.get(i).moveEntity(map,0,0)); //numbers are irrelevant
        }
    }
}
