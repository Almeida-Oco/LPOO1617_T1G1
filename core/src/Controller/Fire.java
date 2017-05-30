package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class Fire extends Entity {
    private Strategy strategy;

    @Override
    public void setType(type t) {
        if ( t == type.FIRE_LEFT || t == type.FIRE_LEFT_IGNITE || t == type.FIRE_RIGHT || t == type.FIRE_RIGHT_IGNITE )
            this.current_type = t;
    }
}
