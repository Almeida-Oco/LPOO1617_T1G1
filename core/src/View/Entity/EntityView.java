package View.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class EntityView {
    protected Sprite representation;
    protected boolean canJump;

    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }


    public void updatePos(int x , int y){
        this.representation.setX(x);
        this.representation.setY(y);
    }

    public Sprite getSprite(){
        return this.representation;
    }
}
