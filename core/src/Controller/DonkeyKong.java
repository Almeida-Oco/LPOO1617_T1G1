package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class DonkeyKong extends Entity {
    private static DonkeyKong instance = null;
    private DonkeyKong(){}

    @Override
    public void setType(type t) {
        if (    t == type.DK_FRONT || t == type.DK_LEFT_BARREL || t == type.DK_LEFT_HAND || t == type.DK_RIGHT_BARREL ||
                t == type.DK_RIGHT_HAND || t == type.DK_THROW_LEFT || t == type.DK_THROW_RIGHT )
            this.current_type = t;
    }

    public static DonkeyKong getInstance(){
        if (instance == null)
            return (instance = new DonkeyKong());
        else
            return instance;
    }
}
