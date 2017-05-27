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

    public Entity(){
        this.position = new Pair<Integer, Integer>(0,0);
    }

    public Entity(int x , int y){
        this.position = new Pair<Integer,Integer>(x,y);
    }

    public void moveX(int x){
        if (this.position.getFirst() + x <= 0)
            this.position.setFirst(0);
        else if (this.position.getFirst() + x >= (Gdx.graphics.getWidth()-this.rep_size.getFirst() ) )
            this.position.setFirst(Gdx.graphics.getWidth()-this.rep_size.getFirst());
        else
            this.position.setFirst( this.position.getFirst() + x );
    }

    public void moveY(int y){
        if (this.position.getSecond() + y <= 0)
            this.position.setSecond(0);
        else if (this.position.getSecond() + y >= (Gdx.graphics.getHeight()-this.rep_size.getSecond()) )
            this.position.setSecond(Gdx.graphics.getHeight()-this.rep_size.getSecond());
        else
            this.position.setSecond( this.position.getSecond() + y);
    }

    public int getX(){
        return this.position.getFirst();
    }

    public int getY(){
        return this.position.getSecond();
    }

    public void setRepSize(int width, int height){
        this.rep_size.setFirst(width);
        this.rep_size.setSecond(height);
    }

    public abstract type getType();
}
