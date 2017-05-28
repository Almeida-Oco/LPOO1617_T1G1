package Controller;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;

public class GameLogic {
    private static GameLogic instance;
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    private GameLogic(){
        this.map = new Map();
        this.chars.add( new Mario( 21 , 75 ) );
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
        if ( !this.chars.getFirst().isMidAir() )
            this.chars.getFirst().setYVelocity(4);
    }

    public void marioClimb( int direction ){ //1 up , -1 down
        Mario mario = (Mario)this.chars.getFirst();
        int new_x;
        if ( (new_x = this.map.nearLadder(mario.getPos(),mario.getRepSize())) != -1 || direction == -1) {
            if (1 == direction)
                marioClimbUp(mario,new_x);
            else
                marioClimbDown(mario);
        }

    }


    /**
     * @brief Moves Mario in the horizontal direction given and updates y coordinates
     * @param direction Direction to move Mario 1-> right , -1 -> left
     */
    public void moveMario(int direction){
        Mario mario = (Mario)this.chars.getFirst();
        if ( !mario.isOnStair() ){
            Pair<Integer,Integer> curr_pos = mario.getPos();
            Pair<Integer,Integer> rep_size = mario.getRepSize();
            mario.current_type = (direction == 1) ? Entity.type.MARIO_RIGHT : Entity.type.MARIO_LEFT ;
            Pair<Integer,Integer> new_pos = this.moveSingleEntity(curr_pos,rep_size, new Pair<Integer,Integer>( (mario.isMidAir()) ? 0 : direction*mario.getXSpeed() , (int)mario.getYSpeed() ));

            if( curr_pos.equals(new_pos) ) { //collision y_velocity = 0
                this.chars.getFirst().setYVelocity(0);
                this.chars.getFirst().setMidAir(false);
            }
            else //no collision
                this.chars.getFirst().setPos(new_pos);

            this.chars.getFirst().updateYVelocity();
        }
    }

    public Pair<Integer,Integer> moveSingleEntity(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, Pair<Integer,Integer> move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( old_pos.getFirst()+move.getFirst() , old_pos.getSecond()+move.getSecond());

        new_pos.setFirst( checkOutOfScreenWidth( new_pos.getFirst() , rep_size.getFirst() ) );
        if (collisionOnX(old_pos,rep_size,new_pos.getFirst()))
            new_pos.setSecond(new_pos.getSecond()+3);

        int new_y = old_pos.getSecond();
        new_pos.setSecond( checkOutOfScreenHeight( new_pos.getSecond(), rep_size.getSecond() ));
        if ( (new_y = collisionOnY( old_pos, rep_size, new_pos.getSecond() ) ) != -1)
            new_pos.setSecond( new_y );


        return new_pos;
    }


    public boolean collisionOnX(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_x){
        boolean collision_x = false;
        if(new_x < old_pos.getFirst()) //moving left
            collision_x = this.map.collidesLeft(old_pos,rep_size);
        if(new_x > old_pos.getFirst()) //moving right
            collision_x = this.map.collidesRight(old_pos,rep_size);

        return collision_x;
    }

    public int collisionOnY(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_y){
        int collision_y = -1;
        if( new_y < old_pos.getSecond() ) //moving down
            collision_y = this.map.collidesBottom(old_pos,rep_size);

        return collision_y;
    }

    //TODO when climbing up reset Mario::in_stair when end of stair reached
    private void marioClimbUp(Mario mario, int new_x){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(new_x,mario.getY());
        int y_offset = 1;
        mario.setInStair(true);
        new_pos.setSecond(new_pos.getSecond()+y_offset);
        if ( collisionOnY(new_pos,mario.getRepSize(), -1) != -1)
            mario.setInStair(false);

        mario.setPos(new_pos);
    }

    // TODO when going down mario does not go all the way down
    // TODO 2nd ladder when going down makes mario fall
    private void marioClimbDown(Mario mario){
        Pair<Integer,Integer> next_pos = mario.getPos();
        next_pos.setSecond( (int)(next_pos.getSecond()-(this.map.getMapTileHeight()*this.map.getMapScale() )) );
        int new_x = this.map.nearLadder(next_pos,mario.getRepSize());
        if (new_x != -1){ //there is still ladder below
            Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(new_x,mario.getY());
            int y_offset = -1;
            new_pos.setSecond(new_pos.getSecond()+y_offset);
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
        if (pos < 0)
            return 0;
        else if (pos > Gdx.graphics.getWidth() - img_width)
            return Gdx.graphics.getWidth() - img_width;
        else
            return pos;
    }
}
