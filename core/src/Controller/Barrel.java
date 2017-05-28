package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class Barrel extends Entity {
    private Strategy strategy;

    @Override
    public void setType(type t) {
        if (t == type.BARREL)
            this.current_type = t;
    }

    @Override
    public type getType() {
        return type.BARREL;
    }
}
