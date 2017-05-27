package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;


public abstract class EntityView {
    protected Sprite representation;
    protected TiledMapTileLayer collisionLayer;
    protected Vector2 velocity = new Vector2();
    protected float speed = 60 * 2, gravity = 60 * 1.8f;

    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }

    public void update(int x , int y){
        this.representation.setCenter(x,y);


    }

    public Sprite getSprite(){
        return this.representation;
    }



    /*
    private void update(float deltaTime) {

        velocity.y -= gravity * deltaTime;

        // clamp velocity
        if(velocity.y > speed)
            velocity.y = speed;
        else if(velocity.y < -speed)
            velocity.y = -speed;

        // save old position
        float oldX = this.representation.getX(), oldY = this.representation.getY();
        boolean collisionX = false, collisionY = false;

        // move on x
        this.representation.setX( this.representation.getX() + velocity.x * deltaTime);

        if(velocity.x < 0) // going left
            collisionX = collidesLeft();
        else if(velocity.x > 0) // going right
            collisionX = collidesRight();

        // react to x collision
        if(collisionX) {
            this.representation.setX(oldX);
            velocity.x = 0;
        }

        // move on y
        this.representation.setY(getY() + velocity.y * deltaTime * 5f);

        if(velocity.y < 0) // going down
            canJump = collisionY = collidesBottom();
        else if(velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if(collisionY) {
            this.representation.setY(oldY);
            velocity.y = 0;
        }
    }
    */

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    public boolean collidesRight() {
        for(float step = 0; step < this.representation.getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(this.representation.getX() + this.representation.getWidth(), this.representation.getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step < this.representation.getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(this.representation.getX(), this.representation.getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step < this.representation.getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(this.representation.getX() + step, this.representation.getY() + this.representation.getHeight()) )
                return true;
        return false;

    }

    public boolean collidesBottom() {
        for(float step = 0; step < this.representation.getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(this.representation.getX() + step, this.representation.getY()))
                return true;
        return false;
    }

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

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

}
