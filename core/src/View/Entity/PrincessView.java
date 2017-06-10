package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Model.Entity;


public class PrincessView extends ElementView {

    private static final float DEFAULT_SCALE = 5.5f;

    PrincessView(AssetManager assets, float screen_scale) {
        super(DEFAULT_SCALE, screen_scale);
        this.loadImageNames();
        this.last_type = Entity.type.PRINCESS_1;
        this.assets = assets;
        Texture text = assets.get(image_names.get(this.last_type));
        this.representation = new Sprite(text, text.getWidth(),text.getHeight());
        this.representation.setSize( (float)(this.representation.getWidth()*1 ), (float)(this.representation.getHeight()*1 ));
    }

    @Override
    protected void loadImageNames() {
        this.image_names.put(Entity.type.PRINCESS_1, "princess/princess_1.png");
        this.image_names.put(Entity.type.PRINCESS_2, "princess/princess_2.png");
    }
}
