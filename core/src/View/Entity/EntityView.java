package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;

import Controller.Entity;


public abstract class EntityView {
    protected Sprite representation;
    protected AssetManager assets;
    protected HashMap<Integer, String>image_names = new HashMap<Integer, String>();

    protected float img_scale;
    protected int last_texture;

    protected EntityView( float default_scale, float screen_scale ){
        this.img_scale = screen_scale*default_scale/ MyGdxGame.DEFAULT_SCALE ;
    }

    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }

    public void updatePos(int x , int y){
        this.representation.setPosition(x,y);
    }

    public Sprite getSprite(){
        return this.representation;
    }


    public int getImgWidth(){
        return (int)(this.representation.getWidth()*this.img_scale);
    }

    public int getImgHeight(){
        return (int)(this.representation.getHeight()*this.img_scale);
    }
}
