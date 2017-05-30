package Controller;

public abstract class Mario extends Entity {
    protected final int JUMP = 2;
    protected final int GO_UP = 1;
    protected final int GO_DOWN = -1;
    protected final int GO_LEFT = -1;
    protected final int GO_RIGHT = 1;
    protected final int STAY_STILL = 0;

    protected int tick;

    protected Mario(int x , int y){
        super(x,y);
        this.current_type=type.MARIO_RIGHT;
        this.tick = 0;
    }

    public static Mario createMario(int x , int y){
        return new MarioRun(x,y);
    }

    @Override
    public void setType(type t) {
        if (t == type.MARIO_LEFT || t == type.MARIO_RIGHT || t==type.MARIO_CLIMB_LEFT || t==type.MARIO_CLIMB_RIGHT)
            this.current_type = t;
    }

    @Override
    public type getType() {
        return current_type;
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
