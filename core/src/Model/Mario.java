package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Mario extends Entity {
    private Mario instance = null;
    private Mario(){};

    public Mario getInstance(){
        if (this.instance == null)
            return (this.instance = new Mario());
        else
            return this.instance;
    }

    public Mario(int x , int y){
        super(x,y);
        this.representation = new Texture(Gdx.files.internal("badlogic.jpg") );
    }

}
