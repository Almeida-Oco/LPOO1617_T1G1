package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Model.Entity;


public class FireView extends ElementView {
    private static final float DEFAULT_SCALE = 4.5f;

    /**
     * Constructor for FireView
     * @param assets Assets of game
     * @param screen_scale Scale of game
     */
    public FireView(AssetManager assets, float screen_scale){
        super( DEFAULT_SCALE , screen_scale );
        this.loadImageNames();
        this.last_type = Entity.type.FIRE_RIGHT;
        this.assets = assets;
        Texture text = assets.get(image_names.get(this.last_type));
        this.representation = new Sprite(text, text.getWidth(), text.getHeight() );
        this.representation.setSize( this.representation.getWidth()*this.img_scale , this.representation.getHeight()*this.img_scale);
    }

    @Override
    protected void loadImageNames() {
        this.image_names.put(Entity.type.FIRE_LEFT,         "fire/left.png");
        this.image_names.put(Entity.type.FIRE_LEFT_IGNITE,  "fire/left_ignite.png");
        this.image_names.put(Entity.type.FIRE_RIGHT,        "fire/right.png");
        this.image_names.put(Entity.type.FIRE_RIGHT_IGNITE, "fire/right_ignite.png");
    }
}
