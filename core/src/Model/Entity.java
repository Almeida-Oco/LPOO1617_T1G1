package Model;


import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

public abstract class Entity {
    private final static Pair<Integer,Integer> mario_init_pos = new Pair<Integer, Integer>(4 ,8);
    private final static Pair<Integer,Integer> DK_pos =         new Pair<Integer, Integer>(3 ,222);
    private final static Pair<Integer,Integer> fire_pos =       new Pair<Integer,Integer> (15,51);

    public enum type {  MARIO_LEFT,MARIO_RIGHT,MARIO_CLIMB_LEFT, MARIO_CLIMB_RIGHT, MARIO_CLIMB_OVER, MARIO_RUN_LEFT,MARIO_RUN_RIGHT, MARIO_DYING_UP,MARIO_DYING_DOWN, MARIO_DYING_LEFT, MARIO_DYING_RIGHT, MARIO_DIED,
                        DK_THROW_LEFT, DK_THROW_RIGHT, DK_FRONT, DK_LEFT_BARREL, DK_RIGHT_BARREL, DK_LEFT_HAND, DK_RIGHT_HAND,
                        BARREL_FALL_BACK, BARREL_FALL_FRONT, BARREL_ROLLING1, BARREL_ROLLING2, BARREL_ROLLING3, BARREL_ROLLING4,
                        FIRE_BARREL_FALL_BACK, FIRE_BARREL_FALL_FRONT, FIRE_BARREL_ROLLING,
                        FIRE_LEFT, FIRE_LEFT_IGNITE, FIRE_RIGHT, FIRE_RIGHT_IGNITE,
                        BARREL_FIRE_MIN1, BARREL_FIRE_MIN2, BARREL_FIRE_MAX1, BARREL_FIRE_MAX2}

    private final float DEFAULT_MAX_Y_VELOCITY = 4f;
    protected float DEFAULT_MAX_X_VELOCITY = 3f;

    protected Pair<Integer,Integer> rep_size = new Pair<Integer, Integer>(0,0);
    protected Pair<Integer,Integer> position;
    protected Pair<Float,Float> velocity = new Pair<Float,Float>(DEFAULT_MAX_X_VELOCITY,-2f);

    private float gravity = 1f;
    private float max_y_velocity = 0;
    private float max_x_velocity = 3.0f;

    protected type current_type;
    protected float scale;


    public Entity(int x , int y){
        this.position = new Pair<Integer,Integer>(x,y);
    }

    public int getX(){
        return this.position.getFirst();
    }

    public int getY(){
        return this.position.getSecond();
    }

    public Pair<Integer,Integer> getPos(){
        return (Pair<Integer,Integer>)this.position.clone();
    }

    public Pair<Integer,Integer> getRepSize(){
        return this.rep_size;
    }

    public int getXSpeed(){
        return (int)Math.round((double)this.velocity.getFirst().floatValue());
    }

    public int getYSpeed(){
        return (int)Math.round((double)this.velocity.getSecond().floatValue());
    }

    public type getType(){
        return this.current_type;
    }

    public void setPos(Pair<Integer,Integer> pos){
        this.position = pos;
    }

    public void setRepSize(int width, int height, float scale){
        this.setScale(scale);
        this.rep_size.setFirst(width);
        this.rep_size.setSecond(height);
    }


    public void setYVelocity( float vel ){
            this.velocity.setSecond(vel);
    }

    public void setXVelocity( int vel ){
        this.velocity.setFirst((float)vel);
    }

    public void updateYVelocity(){
        if (this.velocity.getSecond() - this.gravity > -this.max_y_velocity)
            this.velocity.setSecond(this.velocity.getSecond() - this.gravity );
        else
            this.velocity.setSecond(-this.max_y_velocity);

    }

    public void setScale (float scale){
        this.max_x_velocity = this.DEFAULT_MAX_X_VELOCITY*scale/MyGdxGame.DEFAULT_SCALE;
        this.velocity.setFirst(this.max_x_velocity);
        this.max_y_velocity = this.DEFAULT_MAX_Y_VELOCITY*scale/MyGdxGame.DEFAULT_SCALE;
        this.scale = scale;
    }

    public abstract void setType(type t);


    /**
     *  Tries to move the Entity in the given direction
     * @param map Current map of the game
     * @param move Contains the movement in X coordinate and Y coordinate
     * @return Either this object if state has not changed, or a different object if state has changed
     */
    public abstract Entity moveEntity(Map map, Pair<Integer,Integer> move);

    /**
     *  Whether this entity should be removed or not
     * @param map Current game map
     * @return True is it should be removed, false otherwise
     */
    public abstract boolean toRemove(Map map);

    /**
     * Checks if this entity collides with another entity on given position
     * @param pos Position of other entity
     * @param rep_size Representation size of other entity
     * @return Whether it collides or not
     */
    public abstract boolean collidesWith(Pair<Integer, Integer> pos, Pair<Integer, Integer> rep_size);

    /**
     * Creates the initial characters of the game
     * @param map Current game map
     * @return Array with initial characters of game
     */
    public static ArrayList<Entity> createInitialCharacters(Map map){
        ArrayList<Entity> chrs = new ArrayList<Entity>();
        Pair<Integer,Integer> mario_pos = map.mapPosToPixels(mario_init_pos);
        Pair<Integer,Integer> dk_pos = map.mapPosToPixels(DK_pos);
        Entity dk = DonkeyKong.getInstance();
        dk.setPos( dk_pos );
        chrs.add( Mario.createMario(mario_pos.getFirst(), mario_pos.getSecond()) );
        chrs.add( dk );
        return chrs;
    }

    /**
     * Creates a new barrel with the given state
     * @param map Current game map
     * @param state Current state of object, First if it is a fire, second if it is free falling
     * @return The newly created barrel
     */
    public static Entity newBarrel(Map map, Pair<Boolean,Boolean> state){
        return Barrel.createBarrel(map, state );
    }

    public static Entity newFire(Map map){
        return new Fire(map.mapPosToPixels(fire_pos), new SmartMovement() );
    }
}
