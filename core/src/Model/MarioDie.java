package Model;


public class MarioDie extends Mario {
    private final int ANIMATION_RATE = 10;
    private final int ANIMATION_END =60;


    /**
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     * @brief Mario constructor
     */
    protected MarioDie(int x, int y) {
        super(x, y);
        this.current_type=type.MARIO_DYING_UP;
    }

    @Override
    protected void tickTock() {
        this.tick++;
    }

    @Override
    public Model.Entity moveEntity(Map map, Pair<Integer,Integer> irrelevant) {
        Mario ret_val=this;
        if( ANIMATION_END == this.tick ) {
            Pair<Integer,Integer> mario_pos = new Pair<Integer, Integer>(4,8);
            mario_pos = map.mapPosToPixels(mario_pos);
            ret_val= Mario.createMario(mario_pos.getFirst(), mario_pos.getSecond());
        }
        this.updateSprite();
        this.tickTock();
        return ret_val;

    }

    /**
     * Updates current mario type
     */
    private void updateSprite(){
        if(this.tick>ANIMATION_RATE && this.tick < (ANIMATION_RATE*2) )
            this.current_type=type.MARIO_DYING_LEFT;
        else if(this.tick>ANIMATION_RATE*2 && this.tick < (ANIMATION_RATE*3) )
            this.current_type=type.MARIO_DYING_DOWN;
        else if(this.tick>ANIMATION_RATE*3 && this.tick < (ANIMATION_RATE*4) )
            this.current_type=type.MARIO_DYING_RIGHT;
        else if(this.tick>ANIMATION_RATE*5 && this.tick < (ANIMATION_RATE*6) )
            this.current_type=type.MARIO_DIED;


    }
}
