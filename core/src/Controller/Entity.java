package Controller;

import com.badlogic.gdx.Gdx;

/**
 * Created by asus on 04/05/2017.
 */

public abstract class Entity {
    public enum type {MARIO, DONKEYKONG, BARREL, FIRE};
    // [width, height]
    protected Pair<Integer,Integer> rep_size = new Pair<Integer, Integer>(0,0);
    // [x,y]
    protected Pair<Integer,Integer> position;
    // [x,y]
    protected Pair<Float,Float> velocity = new Pair<Float,Float>(3.0f,-3f);

    private float gravity = 0.8f;
    private boolean mid_air = false;

    private final float MAX_Y_VELOCITY = 4;


    public Entity(){
        this.position = new Pair<Integer, Integer>(0,0);
    }

    public Entity(int x , int y){
        this.position = new Pair<Integer,Integer>(x,y);
    }


    public void updateYVelocity(){
        this.velocity.setSecond( this.velocity.getSecond() - this.gravity );
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

    public float getMaxSpeed(){
        return this.MAX_Y_VELOCITY;
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
        if (this.velocity.getSecond()+vel < MAX_Y_VELOCITY)
            this.velocity.setSecond(vel);
        else
            this.velocity.setSecond(MAX_Y_VELOCITY);
        this.mid_air = true;
    }

    public abstract type getType();
}
