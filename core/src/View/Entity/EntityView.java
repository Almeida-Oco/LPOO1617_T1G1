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
        this.representation.setCenter(x,y);
    }

    public Sprite getSprite(){
        return this.representation;
    }


    /*
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    */
}
