package View.Entity;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;

import Model.Entity;
public class ViewFactory {
    private static HashMap<Entity.type,ElementView> cache = new HashMap<Entity.type, ElementView>();

    /**
     * Makes a view matching the given type
     * @param assets Assets of game
     * @param type Type to match
     * @param screen_scale Scale of the screen
     * @return The store ElementView object
     */
    public static ElementView makeView(AssetManager assets, Entity.type type, float screen_scale){
        if ( !cache.containsKey(type) )
            if ( isMario(type) )
                insertAllMario(assets,screen_scale);
            else if ( isBarrel(type))
                insertAllBarrel(assets,screen_scale);
            else if ( isDK(type))
                insertAllDK(assets,screen_scale);
            else if ( isBarrelFire(type) )
                insertAllBarrelFire(assets,screen_scale);
            else if ( isFire(type) )
                insertAllFire(assets,screen_scale);
            else if(isPrincess(type))
                insertAllPrincess(assets,screen_scale);

        return cache.get(type);
    }

    /**
     * Inserts all princess states into cache
     * @param assets Assets of game
     * @param screen_scale Scale of screen
     */
    private static void insertAllPrincess(AssetManager assets, float screen_scale) {
        ElementView ent_view = new PrincessView(assets, screen_scale);

        cache.put(Entity.type.PRINCESS_1,ent_view);
        cache.put(Entity.type.PRINCESS_2,ent_view);
    }

    /**
     * Inserts all mario states into cache
     * @param assets Assets of game
     * @param screen_scale Scale of screen
     */
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

    /**
     * Inserts all barrel states into cache
     * @param assets Assets of game
     * @param screen_scale Scale of screen
     */
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

    /**
     * Inserts all donkey kong states into cache
     * @param assets Assets of game
     * @param screen_scale Scale of screen
     */
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

    /**
     * Inserts all fire barrel states into cache
     * @param assets Assets of game
     * @param screen_scale Scale of screen
     */
    private static void insertAllBarrelFire(AssetManager assets, float screen_scale){
        ElementView ent_view = new BarrelFireView(assets,screen_scale);
        cache.put(Entity.type.BARREL_FIRE_MIN1,ent_view);
        cache.put(Entity.type.BARREL_FIRE_MIN2,ent_view);
        cache.put(Entity.type.BARREL_FIRE_MAX1,ent_view);
        cache.put(Entity.type.BARREL_FIRE_MAX2,ent_view);
    }

    /**
     * Inserts all fire states into cache
     * @param assets Assets of game
     * @param screen_scale Scale of screen
     */
    private static void insertAllFire(AssetManager assets, float screen_scale){
        ElementView el_view = new FireView(assets,screen_scale);
        cache.put(Entity.type.FIRE_LEFT,el_view);
        cache.put(Entity.type.FIRE_RIGHT,el_view);
        cache.put(Entity.type.FIRE_RIGHT_IGNITE,el_view);
        cache.put(Entity.type.FIRE_LEFT_IGNITE,el_view);
    }

    /**
     * Checks if given type is of princess or not
     * @param type Type to check
     * @return Whether it is of princess or not
     */
    private static boolean isPrincess(Entity.type type) {
        return ( type == Entity.type.PRINCESS_1|| type == Entity.type.PRINCESS_2);
    }

    /**
     * Checks if given type is of mario or not
     * @param type Type to check
     * @return Whether it is of mario or not
     */
    private static boolean isMario(Entity.type type){
        return (type == Entity.type.MARIO_LEFT || type == Entity.type.MARIO_RIGHT || type == Entity.type.MARIO_CLIMB_LEFT || type == Entity.type.MARIO_CLIMB_RIGHT || type == Entity.type.MARIO_CLIMB_OVER);
    }

    /**
     * Checks if given type is of barrel or not
     * @param type Type to check
     * @return Whether it is of barrel or not
     */
    private static boolean isBarrel(Entity.type type){
        return ( type == Entity.type.BARREL_ROLLING1 || type == Entity.type.BARREL_ROLLING2 || type == Entity.type.BARREL_ROLLING3 || type == Entity.type.BARREL_ROLLING4 ||
                type == Entity.type.BARREL_FALL_FRONT || type == Entity.type.BARREL_FALL_BACK|| type == Entity.type.FIRE_BARREL_FALL_BACK || type == Entity.type.FIRE_BARREL_FALL_FRONT ||
                type == Entity.type.FIRE_BARREL_ROLLING);
    }

    /**
     * Checks if given type is of DonkeyKong or not
     * @param type Type to check
     * @return Whether it is of DonkeyKong or not
     */
    private static boolean isDK(Entity.type type){
        return  (type == Entity.type.DK_FRONT || type == Entity.type.DK_RIGHT_HAND || type == Entity.type.DK_LEFT_HAND || type == Entity.type.DK_LEFT_BARREL ||
                type == Entity.type.DK_RIGHT_BARREL || type == Entity.type.DK_THROW_LEFT || type == Entity.type.DK_THROW_RIGHT);
    }

    /**
     * Checks if given type is of fire barrel or not
     * @param type Type to check
     * @return Whether it is of fire barrel or not
     */
    private static boolean isBarrelFire(Entity.type type){
        return ( type == Entity.type.BARREL_FIRE_MIN1 || type == Entity.type.BARREL_FIRE_MIN2 || type == Entity.type.BARREL_FIRE_MAX1 || type == Entity.type.BARREL_FIRE_MAX2);
    }

    /**
     * Checks if given type is of fire or not
     * @param type Type to check
     * @return Whether it is of fire or not
     */
    private static boolean isFire(Entity.type type){
        return ( type == Entity.type.FIRE_LEFT || type == Entity.type.FIRE_RIGHT || type == Entity.type.FIRE_LEFT_IGNITE || type == Entity.type.FIRE_RIGHT_IGNITE );
    }
}
