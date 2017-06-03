package Controller;

public class DonkeyKong extends Entity {
    private final int ANIMATION_RESET = 40;
    private final int ANIMATION_RATE = 20;
    private final int THROW = 1;
    private final int HAND = 0;
    private int curr_animation = -1;

    protected int tick;
    private static DonkeyKong instance = null;
    private boolean free_barrel = false;

    private DonkeyKong(int x, int y){
        super(x,y);
        this.tick = 0;
        this.current_type=type.DK_THROW_LEFT;
    }

    @Override
    public boolean collidesWith(Pair<Integer, Integer> pos, Pair<Integer, Integer> rep_size) {
        return  (pos.getFirst() <= (this.getX()+this.rep_size.getFirst())) && (pos.getSecond() >=  this.getY()) &&
                ((pos.getSecond()+rep_size.getSecond()) <= (this.getY()+this.rep_size.getSecond()) );
    }


    @Override
    public void setType(type t) {
        if (    t == type.DK_FRONT || t == type.DK_LEFT_BARREL || t == type.DK_LEFT_HAND || t == type.DK_RIGHT_BARREL ||
                t == type.DK_RIGHT_HAND || t == type.DK_THROW_LEFT || t == type.DK_THROW_RIGHT )
            this.current_type = t;
    }

    /**
     * @brief Since DK does not move, this is used to know which sprite to represent it
     * @param map Current map of the game
     * @param throw_or_hand If 1 then do barrel throw animation, if 0 do hand motion
     * @param first_barrel If 1 then it is the first barrel thrown so it forces the free_fall animation, 0 otherwise
     * @return null if DK is about to throw a barrel, this object otherwise
     */
    @Override
    public Entity moveEntity(Map map, int throw_or_hand, int first_barrel) {
        if (this.curr_animation == -1){
            this.free_barrel = ((Math.random()*10) > 7) || first_barrel == THROW;
            this.curr_animation = throw_or_hand;
            this.tick = 0;
        }
        this.tickTock();
        if ( this.curr_animation == HAND)
            this.updateSpriteKong();
        else if (this.curr_animation == THROW)
            this.updateSpriteThrow();

        if ( this.tick == ANIMATION_RATE && this.curr_animation == THROW )
            return null;
        else
            return this;
    }


    /**
     * @brief Updates DK status to animate it when he is not throwing a barrel
     */
    private void updateSpriteKong() {
        if(this.tick >= ANIMATION_RATE)
            this.current_type=type.DK_RIGHT_HAND;
        else if(this.tick < ANIMATION_RATE)
            this.current_type=type.DK_LEFT_HAND;
    }

    /**
     * @brief Updates DK status to animate it when he is throwing a barrel
     */
    private void updateSpriteThrow() {
        if(this.tick >= ANIMATION_RATE)
            this.current_type = (this.free_barrel) ? type.DK_FRONT : type.DK_RIGHT_BARREL;
        else if(this.tick < ANIMATION_RATE)
            this.current_type=type.DK_THROW_LEFT;
    }

    /**
     * @brief Used to know when to animate DK
     */
    private void tickTock() {
        this.tick++;
        if ( ANIMATION_RESET == this.tick ){
            this.tick = 0;
            this.curr_animation = -1;
        }
    }

    public static DonkeyKong getInstance(){
        if (instance == null) {
            return (instance = new DonkeyKong(0,0));
        }
        else
            return instance;
    }

    @Override
    public boolean toRemove(Map map) {
        return false;
    }
}
