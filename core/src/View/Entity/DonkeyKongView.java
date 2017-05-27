package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by oco on 5/27/17.
 */

public class DonkeyKongView extends EntityView {
    public static final String DONKEYKONG_IMG = "badlogic.jpg";

    public DonkeyKongView(AssetManager assets){
        Texture text = assets.get(DONKEYKONG_IMG);
        this.representation = new Sprite(text, text.getWidth(), text.getHeight());
    }

}
