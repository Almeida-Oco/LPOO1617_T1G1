package Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.LinkedList;

public class GameLogic {
    private static GameLogic instance;
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    private GameLogic(){
        this.map = new Map();
        this.chars.add( Mario.getInstance() );
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
        Mario mario = Mario.getInstance();
        if ( !mario.isMidAir() && !mario.isOnStair()){
            mario.setYVelocity(12);
            mario.setMidAir(true);
        }

    }

    public boolean marioClimb( int direction ){ //1 up , -1 down
        Mario mario = Mario.getInstance();
        int new_x;
        if ( !mario.isMidAir() && (((new_x = this.map.nearLadder(mario.getPos(),mario.getRepSize())) != -1) || direction == -1 )) {
            if (1 == direction)
                marioClimbUp(mario,new_x);
            else
                marioClimbDown(mario);
        }
        if(mario.isOnStair()){
            updateMarioState(mario,2 );
        }
        return mario.isOnStair();
    }


    /**
     * @brief Moves Mario in the horizontal direction given and updates y coordinates
     * @param direction Direction to move Mario 1-> right , -1 -> left
     */
    public void moveMario(int direction){
        Mario mario = Mario.getInstance();
        if ( !mario.isOnStair() ){
            updateMarioState(mario,direction);
            Pair<Integer,Integer>   velocity = new Pair<Integer,Integer>( mario.getXSpeed() , (int)mario.getYSpeed() ),
                                    curr_pos = mario.getPos(),
                                    new_pos = this.moveSingleEntity(mario, curr_pos,mario.getRepSize(), velocity);

            mario.setPos(new_pos);
            mario.updateYVelocity();
        }
    }


    private Pair<Integer,Integer> moveSingleEntity(Entity ent, Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, Pair<Integer,Integer> move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( old_pos.getFirst()+move.getFirst() , old_pos.getSecond()+move.getSecond());

        new_pos.setFirst( checkOutOfScreenWidth( new_pos.getFirst() , rep_size.getFirst() ) );

        int new_y = old_pos.getSecond();
        new_pos.setSecond( checkOutOfScreenHeight( new_pos.getSecond(), rep_size.getSecond() ));
        if ( (new_y = collisionOnY( old_pos, rep_size, new_pos.getSecond() ) ) != -1){
            new_pos.setSecond( new_y );
            ent.setYVelocity(0);
            ent.setMidAir(false);
        }

        return new_pos;
    }
    
    private int collisionOnY(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_y){
        int collision_y = -1;
        if( new_y < old_pos.getSecond() ) //moving down
            collision_y = this.map.collidesBottom(old_pos,rep_size);

        return collision_y;
    }

    private void marioClimbUp(Mario mario, int new_x){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(new_x,mario.getY());
        int y_offset = 1;
        mario.setInStair(true);
        new_pos.setSecond(new_pos.getSecond()+y_offset);
        Pair<Integer,Integer> upper_pos = new Pair<Integer,Integer>(new_pos.getFirst(),new_pos.getSecond()+(int)this.map.getMapTileHeight()); //force upper tile
        if ( collisionOnY(mario.getPos(),mario.getRepSize(), -1) != -1 &&  this.map.nearLadder(upper_pos, mario.getRepSize()) == -1 )
            mario.setInStair(false);
        else
            mario.setPos(new_pos);
    }

    //TODO make mario go down stairs
    private void marioClimbDown(Mario mario){
        Pair<Integer,Integer> lower_pos = mario.getPos();
        Pair<Integer,Integer> rep = mario.getRepSize();
        lower_pos.setSecond( lower_pos.getSecond() - (int)this.map.getMapTileHeight() ); //force lower tile
        int new_x = this.map.nearLadder(lower_pos,rep);
        if (new_x != -1 && /*( collisionOnY( lower_pos ,mario.getRepSize(),-1) == -1 */ ( this.map.ladderAndCraneBelow(lower_pos,rep) && mario.isOnStair() ) ){ //there is still ladder below and no floor below
            Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(new_x,mario.getY());
            new_pos.setSecond(new_pos.getSecond()-1);
            mario.setPos(new_pos);
            mario.setInStair(true);
        }
        else
            mario.setInStair(false);

    }


    /**
     * @brief Checks if given number is out of screen height bounds
     * @param pos Number to check, represents Y coordinate of object
     * @param img_height Height of the image representing the object
     * @return If image is out of bounds then the closest coordinate possible to that bound, otherwise param pos
     */
    private int checkOutOfScreenHeight( int pos , int img_height){
        if (pos < 0)
            return 0;
        else if (pos > (Gdx.graphics.getHeight()-img_height) )
            return (Gdx.graphics.getHeight()-img_height);
        else
            return pos;
    }

    /**
     * @brief Checks if given number is out of screen width bounds
     * @param pos Number to check, represents X coordinate of object
     * @param img_width Height of the image representing the object
     * @return If image is out of bounds then the closest coordinate possible to that bound, otherwise param pos
     */
    private int checkOutOfScreenWidth( int pos, int img_width) {
        if (pos < img_width/2)
            return img_width/2;
        else if (pos > Gdx.graphics.getWidth() - img_width)
            return Gdx.graphics.getWidth() - img_width;
        else
            return pos;
    }

    private void updateMarioState(Mario mario, int direction){
        //TODO Change this shit
        if (1 == direction&& !mario.isOnStair())
            mario.setType(Entity.type.MARIO_RIGHT);
        else if (-1 == direction && !mario.isOnStair())
            mario.setType(Entity.type.MARIO_LEFT);
        else if(2==direction){
            mario.setType(Entity.type.MARIO_CLIMB_LEFT);
        }
        else if(3==direction){
            mario.setType(Entity.type.MARIO_CLIMB_RIGHT);
        }

        mario.updateXVelocity(direction);
    }
}
