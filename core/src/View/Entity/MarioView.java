package View.Entity;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class MarioView extends EntityView {
    private Texture texture;
    private static final float DEFAULT_SCALE = 3;


    public MarioView(AssetManager assets, float screen_scale){
        super(DEFAULT_SCALE,screen_scale);
        this.loadImageNames();
        this.last_texture=0;
        this.assets=assets;
        this.texture = assets.get(image_names.get(0));
        this.representation = new Sprite(texture,texture.getWidth(),texture.getHeight());
        this.representation.scale(this.img_scale);
    }


    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

    public void changeSprite(int option) {
        if(option!=this.last_texture) {
            this.texture = assets.get(image_names.get(option));
            this.representation = new Sprite(texture,texture.getWidth(),texture.getHeight());
            this.representation.scale(this.img_scale);
            this.last_texture=option;
        }

    }

    private void loadImageNames(){
        this.image_names.put(0,"mario_left.png");
        this.image_names.put(1,"mario_right.png");
        this.image_names.put(2,"mario_climb_left.png");
        this.image_names.put(3,"mario_climb_right.png");
        this.image_names.put(4,"mario_run_left.png");
        this.image_names.put(5,"mario_run_right.png");
        this.image_names.put(6,"mario_morrer_cima.png");
        this.image_names.put(7,"mario_climb_over.png");
    }

}




