package View.Entity;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;

import Controller.Entity;
public class ViewFactory {
    private static HashMap<Entity.type,ElementView> cache = new HashMap<Entity.type, ElementView>();

    public static ElementView makeView(AssetManager assets, Entity.type type, float screen_scale){
        if ( !cache.containsKey(type) ){
            if ( type == Entity.type.MARIO_LEFT || type == Entity.type.MARIO_RIGHT || type == Entity.type.MARIO_CLIMB_LEFT || type == Entity.type.MARIO_CLIMB_RIGHT ||
                    type == Entity.type.MARIO_CLIMB_OVER)
                insertAllMario(assets,screen_scale);
            else if ( type == Entity.type.BARREL_ROLLING1 || type == Entity.type.BARREL_ROLLING2 || type == Entity.type.BARREL_ROLLING3 || type == Entity.type.BARREL_ROLLING4 ||
                    type == Entity.type.BARREL_FALL_FRONT || type == Entity.type.BARREL_FALL_BACK|| type == Entity.type.FIRE_BARREL_FALL_BACK || type == Entity.type.FIRE_BARREL_FALL_FRONT ||
                    type == Entity.type.FIRE_BARREL_ROLLING)
                insertAllBarrel(assets,screen_scale);
            else if ( type == Entity.type.DK_FRONT || type == Entity.type.DK_RIGHT_HAND || type == Entity.type.DK_LEFT_HAND || type == Entity.type.DK_LEFT_BARREL ||
                    type == Entity.type.DK_RIGHT_BARREL || type == Entity.type.DK_THROW_LEFT || type == Entity.type.DK_THROW_RIGHT)
                insertAllDK(assets,screen_scale);
            else if ( type == Entity.type.BARREL_FIRE_MIN1 || type == Entity.type.BARREL_FIRE_MIN2 || type == Entity.type.BARREL_FIRE_MAX1 || type == Entity.type.BARREL_FIRE_MAX2)
                insertAllFire(assets,screen_scale);
        }

        return cache.get(type);
    }

    private static void insertAllMario(AssetManager assets, float screen_scale){
        ElementView ent_view = new MarioView(assets,screen_scale);
        cache.put(Entity.type.MARIO_LEFT,ent_view);
        cache.put(Entity.type.MARIO_RIGHT,ent_view);
        cache.put(Entity.type.MARIO_RUN_LEFT,ent_view);
        cache.put(Entity.type.MARIO_RUN_RIGHT,ent_view);
        cache.put(Entity.type.MARIO_CLIMB_RIGHT,ent_view);
        cache.put(Entity.type.MARIO_CLIMB_LEFT,ent_view);
        cache.put(Entity.type.MARIO_CLIMB_OVER,ent_view);
        cache.put(Entity.type.MARIO_DIED,ent_view);
        cache.put(Entity.type.MARIO_DYING_DOWN,ent_view);
        cache.put(Entity.type.MARIO_DYING_LEFT,ent_view);
        cache.put(Entity.type.MARIO_DYING_UP,ent_view);
        cache.put(Entity.type.MARIO_DYING_RIGHT,ent_view);
    }

    private static void insertAllBarrel(AssetManager assets, float screen_scale ){
        ElementView ent_view = new BarrelView(assets,screen_scale);
        cache.put(Entity.type.BARREL_ROLLING1,ent_view);
        cache.put(Entity.type.BARREL_ROLLING2,ent_view);
        cache.put(Entity.type.BARREL_ROLLING3,ent_view);
        cache.put(Entity.type.BARREL_ROLLING4,ent_view);
        cache.put(Entity.type.BARREL_FALL_FRONT,ent_view);
        cache.put(Entity.type.BARREL_FALL_BACK,ent_view);
        cache.put(Entity.type.FIRE_BARREL_FALL_BACK,ent_view);
        cache.put(Entity.type.FIRE_BARREL_FALL_FRONT,ent_view);
        cache.put(Entity.type.FIRE_BARREL_ROLLING,ent_view);
    }

    private static void insertAllDK(AssetManager assets, float screen_scale){
        ElementView ent_view = new DonkeyKongView(assets,screen_scale);
        cache.put(Entity.type.DK_FRONT,ent_view);
        cache.put(Entity.type.DK_RIGHT_HAND,ent_view);
        cache.put(Entity.type.DK_RIGHT_BARREL,ent_view);
        cache.put(Entity.type.DK_THROW_RIGHT,ent_view);
        cache.put(Entity.type.DK_LEFT_HAND,ent_view);
        cache.put(Entity.type.DK_LEFT_BARREL,ent_view);
        cache.put(Entity.type.DK_THROW_LEFT,ent_view);
    }

    private static void insertAllFire(AssetManager assets, float screen_scale){
        ElementView ent_view = new BarrelFireView(assets,screen_scale);
        cache.put(Entity.type.BARREL_FIRE_MIN1,ent_view);
        cache.put(Entity.type.BARREL_FIRE_MIN2,ent_view);
        cache.put(Entity.type.BARREL_FIRE_MAX1,ent_view);
        cache.put(Entity.type.BARREL_FIRE_MAX2,ent_view);
    }
}
