package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by asus on 04/05/2017.
 */

public class Entity {
    protected Texture representation;
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
        else if (this.position.getFirst() + x >= (Gdx.graphics.getWidth()-this.representation.getWidth()) )
            this.position.setFirst(Gdx.graphics.getWidth()-this.representation.getWidth());
        else
            this.position.setFirst( this.position.getFirst() + x );
    }

    public void moveY(int y){
        if (this.position.getSecond() + y <= 0)
            this.position.setSecond(0);
        else if (this.position.getSecond() + y >= (Gdx.graphics.getHeight()-this.representation.getHeight()) )
            this.position.setSecond(Gdx.graphics.getHeight()-this.representation.getHeight());
        else
            this.position.setSecond( this.position.getSecond() + y);
    }

    public void draw(SpriteBatch batch){
        batch.draw(this.representation , this.position.getFirst(), this.position.getSecond() );
    }
}
