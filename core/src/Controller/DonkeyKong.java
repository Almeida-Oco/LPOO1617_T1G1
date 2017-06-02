package Controller;

public class DonkeyKong extends Entity {
    private final int ANIMATION_RESET = 40;
    private final int ANIMATION_RATE = 20;
    private int curr_animation = -1;

    protected int tick;
    private static DonkeyKong instance = null;

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


    private void updateSpriteKong() {
        if(this.tick== ANIMATION_RATE)
            this.current_type=type.DK_RIGHT_HAND;
        else if(this.tick==0)
            this.current_type=type.DK_LEFT_HAND;
    }

    private void updateSpriteThrow() {
        if(this.tick== ANIMATION_RATE)
            this.current_type=type.DK_RIGHT_BARREL;
        else if(this.tick==0)
            this.current_type=type.DK_THROW_LEFT;
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
     * @param x_move If 1 then do hand animation, if 0 do barrel throw animation
     * @param y_move Irrelevant
     * @return null if DK is about to throw a barrel, this object otherwise
     */
    @Override
    public Entity moveEntity(Map map, int x_move, int y_move) {
        if (this.curr_animation == -1){
            this.curr_animation = x_move;
            this.tick = 0;
        }
        if ( this.curr_animation == 1)
            this.updateSpriteKong();
        else if (this.curr_animation == 0)
            this.updateSpriteThrow();

        this.tickTock();
        if ( this.tick == ANIMATION_RATE && this.curr_animation == 0)
            return null;
        else
            return this;
    }


    public static DonkeyKong getInstance(){
        if (instance == null) {
            return (instance = new DonkeyKong(0,0));
        }
        else
            return instance;
    }


    protected void tickTock() {
        this.tick++;
        if ( ANIMATION_RESET == this.tick ){
            this.tick = 0;
            this.curr_animation = -1;
        }
    }
}
