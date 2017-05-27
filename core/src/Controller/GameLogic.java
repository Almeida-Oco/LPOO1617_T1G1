package Controller;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;

public class GameLogic {
    private static GameLogic instance;
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    private GameLogic(){
        this.map = new Map();
        this.chars.add( new Mario( 2*this.map.getMapTileWidth(), 80 ) );
    };


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

    public void marioJump(){
        this.chars.getFirst().setYVelocity(10);
    }

    /**
     * @brief Moves Mario in the horizontal direction given and updates y coordinates
     * @param direction Direction to move Mario 1-> right , -1 -> left
     */
    public void moveMario(int direction){
        Entity mario = this.chars.getFirst();
        Pair<Integer,Integer> curr_pos = mario.getPos();
        Pair<Integer,Integer> rep_size = mario.getRepSize();
        Pair<Integer,Integer> new_pos = this.moveSingleEntity(curr_pos,rep_size, new Pair<Integer,Integer>( direction*mario.getXSpeed() , (int)mario.getYSpeed() ));

        if( curr_pos.equals(new_pos) ) //collision y_velocity = 0
            this.chars.getFirst().setYVelocity(0);
        else{ //no collision
            this.chars.getFirst().setPos(new_pos);
            this.chars.getFirst().updateYVelocity();
        }
    }


    public boolean collisionOnX(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_x){
        boolean collision_x = false;
        if(new_x < old_pos.getFirst()) //moving left
            collision_x = this.map.collidesLeft(old_pos,rep_size);
        if(new_x > old_pos.getFirst()) //moving right
            collision_x = this.map.collidesRight(old_pos,rep_size);

        return collision_x;
    }

    public boolean collisionOnY(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_y){
        boolean collision_y = false;
        if( new_y < old_pos.getSecond() ) //moving down
            collision_y = this.map.collidesBottom(old_pos,rep_size);

        return collision_y;
    }

    //TODO only check for collisions below mario
    public Pair<Integer,Integer> moveSingleEntity(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, Pair<Integer,Integer> move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( old_pos.getFirst()+move.getFirst() , old_pos.getSecond()+move.getSecond());

        if(new_pos.getFirst() < 0 || new_pos.getFirst() > (Gdx.graphics.getWidth()-rep_size.getFirst()) ) {
            System.out.println("adeus");
            new_pos.setFirst( old_pos.getFirst() );
        }

        if ( collisionOnY( old_pos, rep_size, new_pos.getSecond() ) || new_pos.getSecond() < 0 || new_pos.getSecond() > (Gdx.graphics.getHeight()-rep_size.getSecond())) {
            System.out.println("ola");
            new_pos.setSecond( old_pos.getSecond() );
        }
        return new_pos;
    }
}
