package View.Entity;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Model.Entity;

/**
 * Created by oco on 5/27/17.
 */

public abstract class EntityView {
    protected Sprite representation;

    EntityView(AssetManager assets){
        this.createSprite(assets);
    }

    public void draw(SpriteBatch batch){
        this.representation.draw(batch);
    }

    public abstract void createSprite(AssetManager assets);

    public void update(int x , int y){
        this.representation.setCenter(x,y);
    }

}
