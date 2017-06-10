package Model;


import com.mygdx.game.MyGdxGame;

public abstract class Mario extends Model.Entity {
    private final float DEFAULT_JUMP_VELOCITY = 13f;
    protected final int JUMP = 2;
    protected final int GO_UP = 1;
    protected final int GO_DOWN = -1;
    protected final int GO_LEFT = -1;
    protected final int GO_RIGHT = 1;
    protected final int STAY_STILL = 0;
    protected float JUMP_VELOCITY = 13f;
    protected boolean died;

    protected int tick;

    /**
     *  Mario constructor
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     */
    protected Mario(int x , int y){
        super(x,y);
        this.tick = 0;
        this.died=false;
    }

    /**
     *  Used to create the default Mario (MarioRun), on given position
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     * @return MarioRun object
     */
    public static Mario createMario(int x , int y){
        Mario ret = new MarioRun(x,y);
        ret.setType(type.MARIO_RIGHT);
        return ret;
    }

    @Override
    public void setType(type t) {
        if (    t == type.MARIO_LEFT || t == type.MARIO_RIGHT || t == type.MARIO_CLIMB_LEFT ||
                t == type.MARIO_CLIMB_RIGHT || t == type.MARIO_RUN_RIGHT|| t == type.MARIO_RUN_LEFT ||
                t == type.MARIO_CLIMB_OVER || t==type.MARIO_DYING_UP)
            this.current_type = t;
    }

    @Override
    public boolean collidesWith(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size){
        return false;
    }

    @Override
    public boolean toRemove(Map map){
        return false;
    }

    @Override
    public void setScale (float scale){
        super.setScale(scale);
        this.JUMP_VELOCITY = DEFAULT_JUMP_VELOCITY*scale/ MyGdxGame.DEFAULT_SCALE;
    }

    @Override
    public void upgrade(){}

    /**
     *  Represents the passing of time in the game, each time character moves this should be called
     */
    protected abstract void tickTock();
}
