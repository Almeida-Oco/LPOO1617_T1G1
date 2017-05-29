package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by oco on 5/27/17.
 */

public class DonkeyKongView extends EntityView {
    private static final float DEFAULT_SCALE = 3;
    public static final String DONKEYKONG_IMG = "badlogic.jpg";

    public DonkeyKongView(AssetManager assets, float screen_scale){
        super(DEFAULT_SCALE,screen_scale);
        Texture text = assets.get(DONKEYKONG_IMG);
        this.representation = new Sprite(text, text.getWidth(), text.getHeight());
    }

}
