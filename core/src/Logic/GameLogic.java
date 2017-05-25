package Logic;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import Model.Entity;
import Model.Map;

public class GameLogic {
    private GameLogic instance;
    private ArrayList<Entity> chars;
    private TiledMap map;

    private GameLogic(){};


    public GameLogic getInstance(){
        if (this.instance == null)
            return (this.instance = new GameLogic());
        else
            return this.instance;
    }
}
