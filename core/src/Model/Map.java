package Model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.ArrayList;

import View.Play;

/**
 * Created by asus on 04/05/2017.
 */

public class Map extends Game {



    @Override
    public void create() {
        setScreen(new Play());
    }

    @Override
    public void dispose() {
    super.dispose();
    }

    @Override
    public void render() {
    super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize( width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }


}
