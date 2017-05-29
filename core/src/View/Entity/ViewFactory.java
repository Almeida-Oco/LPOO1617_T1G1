package View.Entity;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;

import Controller.Entity;

public class ViewFactory {
    private static HashMap<Entity.type,EntityView> cache = new HashMap<Entity.type, EntityView>();

    public static EntityView makeView(AssetManager assets, Entity entity, float screen_scale){
        Entity.type type = entity.getType();
        //TODO change only sprite instead creating other object
        MarioView temp=new MarioView(assets);
        if ( !cache.containsKey(type) ){
            if (type == Entity.type.MARIO_LEFT) {
                temp.changeSprite(0);
                cache.put(type, temp);
            }
            else if (type == Entity.type.MARIO_RIGHT) {
                temp.changeSprite(1);
                cache.put(type, temp);
            }
            else if (type == Entity.type.MARIO_CLIMB_LEFT) {
                temp.changeSprite(2);
                cache.put(type, temp);
            }



        }

        return cache.get(type);
    }


}
