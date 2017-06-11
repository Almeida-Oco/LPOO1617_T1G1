package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;

import Model.Entity;


public abstract class ElementView {
    protected Sprite representation;
    protected AssetManager assets;
    protected HashMap<Entity.type, String>image_names = new HashMap<Entity.type, String>();

    protected float img_scale;
    protected Entity.type last_type;

    /**
     * Constructor for Element View
     * @param default_scale Default scale of this view
     * @param screen_scale Current scale of view
     */
    protected ElementView(float default_scale, float screen_scale ){
        this.img_scale = screen_scale*default_scale/ MyGdxGame.DEFAULT_SCALE ;
    }

    /**
     * Draws this view
     * @param batch Batch to draw in
     */
    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }

    /**
     * Updates current position of sprite
     * @param x X pixel coordinate
     * @param y Y pixel coordinate
     */
    public void updatePos(int x , int y){
        this.representation.setX(x);
        this.representation.setY(y);
    }

    /**
     * Gets current image width
     * @return Sprite width
     */
    public int getImgWidth(){
        return (int)(this.representation.getWidth() );
    }

    /**
     * Gets current sprite height
     * @return Sprite height
     */
    public int getImgHeight(){
        return (int)(this.representation.getHeight() );
    }

    /**
     * Loads all image names into image_names
     */
    protected abstract void loadImageNames();

    /**
     * Changes the sprite of this View
     * @param option Type to use to know which sprite to change into
     */
    public void changeSprite(Entity.type option){
        if( option != this.last_type) {
            Texture texture = assets.get(image_names.get(option));
            this.representation = new Sprite(texture,texture.getWidth(),texture.getHeight());
            this.representation.setSize(this.representation.getWidth()*this.img_scale, this.representation.getHeight()*this.img_scale);
            this.last_type = option;
        }
    }

}
