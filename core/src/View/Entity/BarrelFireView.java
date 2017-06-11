package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Model.Entity;


public class BarrelFireView extends ElementView {
    private static final float DEFAULT_SCALE = 5.5f;

    /**
     * Constructor for BarrelFireView
     * @param assets Assets of game
     * @param screen_scale Scale of game
     */
    protected BarrelFireView(AssetManager assets, float screen_scale) {
        super(DEFAULT_SCALE, screen_scale);
        this.loadImageNames();
        this.last_type = Entity.type.BARREL_FIRE_MIN1;
        this.assets = assets;
        Texture text = assets.get(image_names.get(this.last_type));
        this.representation = new Sprite(text, text.getWidth(),text.getHeight());
        this.representation.setSize( this.representation.getWidth()*this.img_scale , this.representation.getHeight()*this.img_scale );
    }

    @Override
    protected void loadImageNames() {
        this.image_names.put(Entity.type.BARREL_FIRE_MIN1, "fire_barrel/min1.png");
        this.image_names.put(Entity.type.BARREL_FIRE_MIN2, "fire_barrel/min2.png");
        this.image_names.put(Entity.type.BARREL_FIRE_MAX1, "fire_barrel/max1.png");
        this.image_names.put(Entity.type.BARREL_FIRE_MAX2, "fire_barrel/max2.png");
    }
}
