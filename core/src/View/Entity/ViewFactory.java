package View.Entity;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;

import Controller.Entity;

public class ViewFactory {
    private static HashMap<Entity.type,EntityView> cache = new HashMap<Entity.type, EntityView>();

    public static EntityView makeView(AssetManager assets, Entity entity){
        Entity.type type = entity.getType();
        if ( !cache.containsKey(type) ){
            if (type == Entity.type.MARIO_LEFT) {
               MarioView temp=new MarioView(assets);
                temp.changeSprite(0);
                cache.put(type, temp);
            }
            if (type == Entity.type.MARIO_RIGHT) {
                MarioView temp=new MarioView(assets);
                temp.changeSprite(1);
                cache.put(type, temp);
            }


        }

        return cache.get(type);
    }


}
