package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;

import Controller.Entity;


public abstract class EntityView {
    protected Sprite representation;
    protected AssetManager assets;
    protected HashMap<Entity.type, String>image_names = new HashMap<Entity.type, String>();

    protected float img_scale;
    protected Entity.type last_type;

    protected EntityView( float default_scale, float screen_scale ){
        this.img_scale = screen_scale*default_scale/ MyGdxGame.DEFAULT_SCALE ;
    }

    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }

    public void updatePos(int x , int y){
        this.representation.setX(x);
        this.representation.setY(y);
    }

    public int getImgWidth(){
        return (int)(this.representation.getWidth() );
    }

    public int getImgHeight(){
        return (int)(this.representation.getHeight() );
    }

    protected abstract void loadImageNames();

    public void changeSprite(Entity.type option){
        if( option != this.last_type) {
            Texture texture = assets.get(image_names.get(option));
            this.representation = new Sprite(texture,texture.getWidth(),texture.getHeight());
            this.representation.setSize(this.representation.getWidth()*this.img_scale, this.representation.getHeight()*this.img_scale);
            this.last_type = option;
        }
    }

}
