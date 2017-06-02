package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class Fire extends Entity {
    private Strategy strategy;

    @Override
    public boolean collidesWith(Pair<Integer, Integer> pos, Pair<Integer, Integer> rep_size) {
        return true;
    }

    @Override
    public void setType(type t) {
        if ( t == type.FIRE_LEFT || t == type.FIRE_LEFT_IGNITE || t == type.FIRE_RIGHT || t == type.FIRE_RIGHT_IGNITE )
            this.current_type = t;
    }

    @Override
    public Entity moveEntity(Map map, int x_move, int y_move) {
        return null;
    }

    @Override
    public boolean toRemove(Map map) {
        return false;
    }
}
