package Model;

import com.badlogic.gdx.Gdx;

/**
 * Created by asus on 04/05/2017.
 */

public class Entity {
    // [width, height]
    protected Pair<Integer,Integer> rep_size;
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
}
