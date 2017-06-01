package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Controller.Entity;


public class BarrelView extends EntityView {
    private static final float DEFAULT_SCALE = 3;

    public BarrelView(AssetManager assets, float screen_scale){
        super(DEFAULT_SCALE,screen_scale);
        this.loadImageNames();
        this.last_type = Entity.type.BARREL_ROLLING;
        this.assets = assets;
        Texture text = assets.get(this.image_names.get(this.last_type));
        this.representation = new Sprite(text, text.getWidth(), text.getHeight() );
        this.representation.setScale(this.img_scale);
    }

    @Override
    protected void loadImageNames() {
        this.image_names.put(Entity.type.BARREL_ROLLING,        "barrels/barrel_rolling1.png");

        this.image_names.put(Entity.type.BARREL_FALL_FRONT,     "barrels/falling_front.png");
        this.image_names.put(Entity.type.BARREL_FALL_BACK,      "barrels/falling_back.png");
        this.image_names.put(Entity.type.FIRE_BARREL_ROLLING,   "barrels/fire_rolling.png");
        this.image_names.put(Entity.type.FIRE_BARREL_FALL_FRONT,"barrels/fire_falling_front.png");
        this.image_names.put(Entity.type.FIRE_BARREL_FALL_BACK, "barrels/fire_falling_back.png");
    }
}
