package Controller;


import com.mygdx.game.MyGdxGame;

public abstract class Entity {
    public enum type {MARIO_LEFT,MARIO_RIGHT, DONKEYKONG, BARREL, FIRE};
    // [width, height]
    protected Pair<Integer,Integer> rep_size = new Pair<Integer, Integer>(0,0);
    // [x,y]
    protected Pair<Integer,Integer> position;
    // [x,y]
    protected Pair<Float,Float> velocity = new Pair<Float,Float>(3.0f,-3f);

    private float gravity = 1f;
    private boolean mid_air = false;
    private float MAX_FALL_VELOCITY = 4;
    private float MAX_X_VELOCITY = 3.0f;

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
        return (int)this.velocity.getFirst().floatValue();
    }

    public float getYSpeed(){
        return this.velocity.getSecond();
    }

    public boolean isMidAir(){
        return this.mid_air;
    }


    public void setMidAir(boolean b){
        this.mid_air = b;
    }

    public void setPos(Pair<Integer,Integer> pos){
        this.position = pos;
    }

    public void setRepSize(int width, int height){
        this.rep_size.setFirst(width);
        this.rep_size.setSecond(height);
    }

    public void setYVelocity( float vel ){
        if ( vel > 0 )
            this.velocity.setSecond(vel);
    }

    public void updateYVelocity(){
        if (this.velocity.getSecond() - this.gravity > -MAX_FALL_VELOCITY)
            this.velocity.setSecond( this.velocity.getSecond() - this.gravity );
        else
            this.velocity.setSecond(-MAX_FALL_VELOCITY);

    }

    public void updateXVelocity( int direction ){
        if ( !this.mid_air )
            this.velocity.setFirst( direction*MAX_X_VELOCITY );
    }

    public void setScale (float scale ){
        this.gravity = this.gravity*scale/MyGdxGame.DEFAULT_SCALE;
        this.MAX_X_VELOCITY = this.MAX_X_VELOCITY*scale/MyGdxGame.DEFAULT_SCALE;
        this.MAX_FALL_VELOCITY = this.MAX_FALL_VELOCITY*scale/MyGdxGame.DEFAULT_SCALE;
    }

    public abstract void setType(type t);

    public abstract type getType();


}
