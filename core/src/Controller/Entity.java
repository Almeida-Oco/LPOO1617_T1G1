package Controller;


import com.mygdx.game.MyGdxGame;

public abstract class Entity {
    public enum type {  MARIO_LEFT,MARIO_RIGHT,MARIO_CLIMB_LEFT, MARIO_CLIMB_RIGHT, MARIO_CLIMB_OVER, MARIO_RUN_LEFT,MARIO_RUN_RIGHT, MARIO_DYING_UP,
                        DK_THROW_LEFT, DK_THROW_RIGHT, DK_FRONT, DK_LEFT_BARREL, DK_RIGHT_BARREL, DK_LEFT_HAND, DK_RIGHT_HAND,
                        BARREL_FALL_BACK, BARREL_FALL_FRONT, BARREL_ROLLING, FIRE_BARREL_FALL_BACK, FIRE_BARREL_FALL_FRONT, FIRE_BARREL_ROLLING,
                        FIRE_LEFT, FIRE_LEFT_IGNITE, FIRE_RIGHT, FIRE_RIGHT_IGNITE,
                        BARREL_FIRE_MIN1, BARREL_FIRE_MIN2, BARREL_FIRE_MAX1, BARREL_FIRE_MAX2};
    private final float DEFAULT_GRAVITY = 1f;
    private final float DEFAULT_MAX_Y_VELOCITY = 4f;
    private final float DEFAULT_MAX_X_VELOCITY = 3f;

    // [width, height]
    protected Pair<Integer,Integer> rep_size = new Pair<Integer, Integer>(0,0);
    // [x,y]
    protected Pair<Integer,Integer> position;
    // [x,y]
    protected Pair<Float,Float> velocity = new Pair<Float,Float>(DEFAULT_MAX_X_VELOCITY,-3f);

    private float gravity = 1f;
    private boolean mid_air = false;
    private float y_velocity = 0;
    private float x_velocity = 3.0f;

    protected type current_type;


    public Entity(){
        this.position = new Pair<Integer, Integer>(0,0);
    }

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

    public void setPos(Pair<Integer,Integer> pos){
        this.position = pos;
    }

    public void setRepSize(int width, int height, float scale){
        this.setScale(scale);
        this.rep_size.setFirst(width);
        this.rep_size.setSecond(height);
    }

    public void setYVelocity( float vel ){
        if ( vel > 0 )
            this.velocity.setSecond(vel);
    }

    public void setXVelocity( int vel ){
        if (vel == 0)
            this.x_velocity = 0;
        else if (vel == -1) //sets to default value
            this.x_velocity = DEFAULT_MAX_X_VELOCITY;
    }

    public void updateYVelocity(){
        if (this.velocity.getSecond() - this.gravity > -this.y_velocity)
            this.velocity.setSecond( this.velocity.getSecond() - this.gravity );
        else
            this.velocity.setSecond(-this.y_velocity);

    }

    public void updateXVelocity( int direction ){
        if ( !this.mid_air && Math.abs(direction) <= 1)
            this.velocity.setFirst( direction*this.x_velocity );
    }

    public void setScale (float scale ){
        this.gravity = this.DEFAULT_GRAVITY*scale/MyGdxGame.DEFAULT_SCALE;
        this.x_velocity = this.DEFAULT_MAX_X_VELOCITY*scale/MyGdxGame.DEFAULT_SCALE;
        this.velocity.setFirst(this.x_velocity);
        this.y_velocity = this.DEFAULT_MAX_Y_VELOCITY*scale/MyGdxGame.DEFAULT_SCALE;
    }

    public abstract void setType(type t);

    public type getType(){
        return this.current_type;
    };


}
