package View.Entity;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by oco on 5/27/17.
 */

public class BarrelView extends EntityView {
    private static final float DEFAULT_SCALE = 3;

    public BarrelView(AssetManager assets, float screen_scale){
        super(DEFAULT_SCALE,screen_scale);
    }
}
