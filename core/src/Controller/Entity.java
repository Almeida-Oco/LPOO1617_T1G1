package Controller;


import com.mygdx.game.MyGdxGame;

public abstract class Entity {

    public enum type {  MARIO_LEFT,MARIO_RIGHT,MARIO_CLIMB_LEFT, MARIO_CLIMB_RIGHT, MARIO_CLIMB_OVER, MARIO_RUN_LEFT,MARIO_RUN_RIGHT, MARIO_DYING_UP,MARIO_DYING_DOWN, MARIO_DYING_LEFT, MARIO_DYING_RIGHT, MARIO_DIED,
                        DK_THROW_LEFT, DK_THROW_RIGHT, DK_FRONT, DK_LEFT_BARREL, DK_RIGHT_BARREL, DK_LEFT_HAND, DK_RIGHT_HAND,
                        BARREL_FALL_BACK, BARREL_FALL_FRONT, BARREL_ROLLING1, BARREL_ROLLING2, BARREL_ROLLING3, BARREL_ROLLING4,
                        FIRE_BARREL_FALL_BACK, FIRE_BARREL_FALL_FRONT, FIRE_BARREL_ROLLING,
                        FIRE_LEFT, FIRE_LEFT_IGNITE, FIRE_RIGHT, FIRE_RIGHT_IGNITE,
                        BARREL_FIRE_MIN1, BARREL_FIRE_MIN2, BARREL_FIRE_MAX1, BARREL_FIRE_MAX2};
    private final float DEFAULT_GRAVITY = 1f;
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


    public Entity(){
        this.position = new Pair<Integer, Integer>(0,0);
    }

    public Entity(int x , int y){
        this.position = new Pair<Integer,Integer>(x,y);
        System.out.println(position.getFirst()+ "  " + position.getSecond());
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

    public void setPos(Pair<Integer,Integer> pos){
        this.position = pos;
    }

    public void setRepSize(int width, int height, float scale){
        this.setScale(scale);
        this.rep_size.setFirst(width);
        this.rep_size.setSecond(height);
    }

    public abstract boolean collidesWith(Pair<Integer, Integer> pos, Pair<Integer, Integer> rep_size);

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
     * @param x_move Movement in the X direction {-1,0,1}
     * @param y_move Movement in the Y direction {-1,0,1,2}
     * @return Either this object if state has not changed, or a different object if state has changed
     */
    public abstract Entity moveEntity(Map map, int x_move, int y_move);

    /**
     *  Whether this entity should be removed or not
     * @param map Current game map
     * @return True is it should be removed, false otherwise
     */
    public abstract boolean toRemove(Map map);

    public type getType(){
        return this.current_type;
    };

}
