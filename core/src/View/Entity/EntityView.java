package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;


public abstract class EntityView {
    protected Sprite representation;
    protected boolean canJump;
    protected float img_scale;
    public static  HashMap<Integer, String>image_names;
    protected AssetManager assets;
    public static int current_image;
    

    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }


    public void updatePos(int x , int y){
        this.representation.setPosition(x,y);
    }

    public Sprite getSprite(){
        return this.representation;
    }

    public float getImgScale(){
        return this.img_scale;
    }

}
