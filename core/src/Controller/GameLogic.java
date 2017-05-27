package Controller;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;

public class GameLogic {
    private static GameLogic instance;
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    private GameLogic(){
        this.map = new Map();
        this.chars.add( new Mario( 20 , 65 ) );
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
        this.chars.getFirst().setYVelocity(4);
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
        else //no collision
            this.chars.getFirst().setPos(new_pos);

        this.chars.getFirst().updateYVelocity();
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

    //TODO only check for collisions below mario
    public Pair<Integer,Integer> moveSingleEntity(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, Pair<Integer,Integer> move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( old_pos.getFirst()+move.getFirst() , old_pos.getSecond()+move.getSecond());

        new_pos.setFirst( checkOutOfScreeWidth( new_pos.getFirst() , rep_size.getFirst() ) );
        if (collisionOnX(old_pos,rep_size,new_pos.getFirst()))
            new_pos.setSecond(new_pos.getSecond()+3);

        int new_y = old_pos.getSecond();
        new_pos.setSecond( checkOutOfScreenHeight( new_pos.getSecond(), rep_size.getSecond() ));
        if ( (new_y = collisionOnY( old_pos, rep_size, new_pos.getSecond() ) ) != -1)
            new_pos.setSecond( new_y );

        return new_pos;
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
    private int checkOutOfScreeWidth( int pos, int img_width) {
        if (pos < 0)
            return 0;
        else if (pos > Gdx.graphics.getWidth() - img_width)
            return Gdx.graphics.getWidth() - img_width;
        else
            return pos;
    }
}
