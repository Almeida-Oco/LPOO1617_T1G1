package View.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by oco on 5/27/17.
 */

public class MarioView extends EntityView {
    public static final String MARIO_IMG = "mario_left.png";

    public MarioView(AssetManager assets){
        this.canJump = true;
        Texture text = assets.get(MARIO_IMG);
        this.representation = new Sprite(text,text.getWidth(),text.getHeight());
        this.img_scale = 3;
        this.representation.scale(this.img_scale);
    }


    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

}
