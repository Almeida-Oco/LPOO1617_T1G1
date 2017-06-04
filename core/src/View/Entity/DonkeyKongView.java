package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Controller.Entity;

/**
 * Created by oco on 5/27/17.
 */

public class DonkeyKongView extends ElementView {
    private static final float DEFAULT_SCALE = 3;

    public DonkeyKongView(AssetManager assets, float screen_scale){
        super(DEFAULT_SCALE,screen_scale);
        this.loadImageNames();
        this.assets = assets;
        this.last_type = Entity.type.DK_FRONT;
        Texture text = assets.get( this.image_names.get(this.last_type));
        this.representation = new Sprite(text, text.getWidth(), text.getHeight() );
        this.representation.setScale(this.img_scale);
    }

    @Override
    protected void loadImageNames() {
        this.image_names.put(Entity.type.DK_FRONT,          "dk/front.png");
        this.image_names.put(Entity.type.DK_THROW_LEFT,     "dk/throw_left.png");
        this.image_names.put(Entity.type.DK_THROW_RIGHT,    "dk/throw_right.png");
        this.image_names.put(Entity.type.DK_LEFT_BARREL,    "dk/left_barrel.png");
        this.image_names.put(Entity.type.DK_LEFT_HAND,      "dk/left_hand.png");
        this.image_names.put(Entity.type.DK_RIGHT_BARREL,   "dk/right_barrel.png");
        this.image_names.put(Entity.type.DK_RIGHT_HAND,     "dk/right_hand.png");
    }

}
