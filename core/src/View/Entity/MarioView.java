package View.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;

/**
 * Created by oco on 5/27/17.
 */

public class MarioView extends EntityView {
    private Texture texture;
    private int last_texture;


    public MarioView(AssetManager assets){
        this.last_texture=0;
        image_names = new HashMap<Integer, String>();
        image_names.put(new Integer(0),"mario_left.png");
        image_names.put(new Integer(1),"mario_right.png");
        image_names.put(new Integer(2),"mario_climb_left.png");
        image_names.put(new Integer(3),"mario_climb_right.png");
        image_names.put(new Integer(4),"mario_morrer_cima.png");
        this.assets=assets;
        this.canJump = true;
        this.texture = assets.get(image_names.get(new Integer(0)));
        this.representation = new Sprite(texture,texture.getWidth(),texture.getHeight());
        this.img_scale = 3;
        this.representation.scale(this.img_scale);
    }


    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

    public void changeSprite(int option) {
        if(option!=this.last_texture) {
                    this.texture = assets.get(image_names.get(new Integer(option)));
                    this.representation = new Sprite(texture,texture.getWidth(),texture.getHeight());
                    this.representation.scale(this.img_scale);
            this.last_texture=option;
        }

    }


    }




