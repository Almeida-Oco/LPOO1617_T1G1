package View.Entity;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;

import Controller.Entity;
//TODO Hashmap<type,int>
public class ViewFactory {
    private static HashMap<Entity.type,EntityView> cache = new HashMap<Entity.type, EntityView>();

    public static EntityView makeView(AssetManager assets, Entity entity, float screen_scale){
        Entity.type type = entity.getType();
        if ( !cache.containsKey(type) ){
            MarioView temp=new MarioView(assets,screen_scale);
            BarrelView temp_barrel=new BarrelView(assets,screen_scale);
            if (type == Entity.type.MARIO_LEFT) {
                temp.changeSprite(type);
                cache.put(type, temp);
            }
            else if (type == Entity.type.MARIO_RIGHT) {
                temp.changeSprite(type);
                cache.put(type, temp);
            }
            else if (type == Entity.type.MARIO_CLIMB_LEFT) {
                temp.changeSprite(type);
                cache.put(type, temp);
            }
            else if (type == Entity.type.MARIO_CLIMB_RIGHT) {
                temp.changeSprite(type);
                cache.put(type, temp);
            }
            else if (type == Entity.type.MARIO_RUN_LEFT){
                temp.changeSprite(type);
                cache.put(type,temp);
            }
            else if (type == Entity.type.MARIO_RUN_RIGHT){
                temp.changeSprite(type);
                cache.put(type,temp);
            }
            else if (type == Entity.type.MARIO_CLIMB_OVER){
                temp.changeSprite(type);
                cache.put(type,temp);
            }
            else if (type == Entity.type.MARIO_CLIMB_OVER){
                temp.changeSprite(type);
                cache.put(type,temp);
            }
            else if(type== Entity.type.BARREL_ROLLING){
                temp_barrel.changeSprite(type);
                cache.put(type,temp_barrel);
            }
            else if(type == Entity.type.BARREL_FALL_BACK) {
                temp_barrel.changeSprite(type);
                cache.put(type,temp_barrel);
            }
            else if(type == Entity.type.BARREL_FALL_FRONT) {
                temp_barrel.changeSprite(type);
                cache.put(type,temp_barrel);
            }




        }

        return cache.get(type);
    }


}
