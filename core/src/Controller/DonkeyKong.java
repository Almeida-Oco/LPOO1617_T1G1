package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class DonkeyKong extends Entity {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 30;

    protected int tick;
    private static DonkeyKong instance = null;

    private DonkeyKong(int x, int y){
        super(x,y);
        this.tick = 0;
        this.current_type=type.DK_THROW_LEFT;
    }

    public void catchThrow(){
        this.tickTock();
        this.updateSpriteThrow();

    }

    public void Kong(){
        this.tickTock();
        this.updateSpriteKong();

    }

    private void updateSpriteKong() {
        if(this.tick==20){
            this.current_type=type.DK_RIGHT_HAND;
        }
        if(this.tick==1){
            this.current_type=type.DK_LEFT_HAND;
        }
    }

    private void updateSpriteThrow() {
        if(this.tick==20){
            this.current_type=type.DK_RIGHT_BARREL;
        }
        if(this.tick==1){
            this.current_type=type.DK_THROW_LEFT;
        }
    }


    @Override
    public void setType(type t) {
        if (    t == type.DK_FRONT || t == type.DK_LEFT_BARREL || t == type.DK_LEFT_HAND || t == type.DK_RIGHT_BARREL ||
                t == type.DK_RIGHT_HAND || t == type.DK_THROW_LEFT || t == type.DK_THROW_RIGHT )
            this.current_type = t;
    }



    public static DonkeyKong getInstance(int x, int y){
        if (instance == null) {
            return (instance = new DonkeyKong(x, y));
        }
        else
            return instance;
    }


    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;
    }
}
