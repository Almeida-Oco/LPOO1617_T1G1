package Controller;


public abstract class Mario extends Entity {
    protected final int JUMP = 2;
    protected final int GO_UP = 1;
    protected final int GO_DOWN = -1;
    protected final int GO_LEFT = -1;
    protected final int GO_RIGHT = 1;
    protected final int STAY_STILL = 0;
    protected int ladder_x_offset = 10;

    protected int tick;

    /**
     * @brief Mario constructor
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     */
    protected Mario(int x , int y){
        super(x,y);
        this.tick = 0;
    }

    /**
     * @brief Used to create the default Mario (MarioRun), on given position
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     * @return MarioRun object
     */
    public static Mario createMario(int x , int y){
        Mario ret = new MarioRun(x,y);
        ret.setType(type.MARIO_RIGHT);
        return ret;
    }

    //TODO put these in array/hashMap
    @Override
    public void setType(type t) {
        if (    t == type.MARIO_LEFT || t == type.MARIO_RIGHT || t == type.MARIO_CLIMB_LEFT ||
                t == type.MARIO_CLIMB_RIGHT || t == type.MARIO_RUN_RIGHT|| t == type.MARIO_RUN_LEFT ||
                t == type.MARIO_CLIMB_OVER)
            this.current_type = t;
    }

    /**
     * @brief Tries to move Mario in the given direction
     * @param map Current map of the game
     * @param x_move Movement in the X direction {-1,0,1}
     * @param y_move Movement in the Y direction {-1,0,1,2}
     * @return Either this object if state has not changed, or an object of MarioRun if state has changed
     */
    public abstract Mario moveMario( Map map, int x_move , int y_move );

    /**
     * @brief Represents the passing of time in the game, each time character moves this should be called
     */
    protected abstract void tickTock();
}
