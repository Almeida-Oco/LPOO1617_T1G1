package View.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;

import Controller.Entity;
import View.Play;

public class ViewFactory {
    private static HashMap<Entity.type,EntityView> cache = new HashMap<Entity.type, EntityView>();
    public static EntityView makeView(AssetManager assets, Entity entity){
        Entity.type type = entity.getType();
        if ( !cache.containsKey(type) ){
            if (type == Entity.type.MARIO)
                cache.put(type, new MarioView(assets,(TiledMapTileLayer) Play.getMap().getMap().getLayers().get("Floor") ));

        }

        return cache.get(type);
    }


}
