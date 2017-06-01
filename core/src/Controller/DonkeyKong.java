package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class DonkeyKong extends Entity {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    protected int tick;
    private static DonkeyKong instance = null;

    private DonkeyKong(int x, int y){
        super(x,y);
        this.tick = 0;
        this.current_type=type.DK_THROW_LEFT;
    }

    private void catchThrow(){
        this.tickTock();
        this.updateSprite();

    }

    private void updateSprite() {
        if(this.tick==2){
            this.current_type=type.DK_LEFT_BARREL;
        }
    }


    @Override
    public void setType(type t) {
        if (    t == type.DK_FRONT || t == type.DK_LEFT_BARREL || t == type.DK_LEFT_HAND || t == type.DK_RIGHT_BARREL ||
                t == type.DK_RIGHT_HAND || t == type.DK_THROW_LEFT || t == type.DK_THROW_RIGHT )
            this.current_type = t;
    }



    public static DonkeyKong getInstance(){
        if (instance == null)
            return (instance = new DonkeyKong(150,1429));
        else
            return instance;
    }


    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;
    }
}
