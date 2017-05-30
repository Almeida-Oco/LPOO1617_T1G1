package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Controller.Entity;


public class FireView extends EntityView {
    private static final float DEFAULT_SCALE = 3;

    public FireView(AssetManager assets, float screen_scale){
        super( DEFAULT_SCALE , screen_scale );
        this.loadImageNames();
        this.last_type = Entity.type.FIRE_RIGHT;
        this.assets = assets;
        Texture text = assets.get(image_names.get(this.last_type));
        this.representation = new Sprite( text, text.getWidth(), text.getHeight() );
        this.representation.scale(this.img_scale);
    }

    @Override
    protected void loadImageNames() {
        this.image_names.put(Entity.type.FIRE_LEFT,         "fire_barrel/left.png");
        this.image_names.put(Entity.type.FIRE_LEFT_IGNITE,  "fire_barrel/left_ignite.png");
        this.image_names.put(Entity.type.FIRE_RIGHT,        "fire_barrel/right.png");
        this.image_names.put(Entity.type.FIRE_RIGHT_IGNITE, "fire_barrel/right_ignite.png");
    }
}
