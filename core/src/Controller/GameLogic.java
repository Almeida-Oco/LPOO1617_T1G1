package Controller;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;

//TODO check velocity for different screens
public class GameLogic {
    private static GameLogic instance;
    //!Mario should always be first character!
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    //TODO find way to initialize Mario always in same relative position
    private GameLogic(){
        System.out.println("ola");
        this.map = new Map();
        this.chars.add( Mario.createMario( 250, 70 ) );
        this.chars.add( Barrel.createBarrel(250,1460));
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


    public void moveMario(int x_move, int y_move){
        this.chars.set( 0 , ((Mario)this.chars.getFirst()).moveMario(this.map,x_move,y_move) );
    }

    public void moveBarrel(int x_move, int y_move){
        //System.out.println("x: "+((Barrel)this.chars.get(1)).getX()+" "+"y: "+ ((Barrel)this.chars.get(1)).getY());
        this.chars.set( 1 , ((Barrel)this.chars.get(1)).moveBarrel(this.map,x_move,y_move) );
    }

    private int collisionOnY(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_y){
        int collision_y = -1;
        if( new_y < old_pos.getSecond() ) //moving down
            collision_y = this.map.collidesBottom(old_pos,rep_size);

        return collision_y;
    }
}
