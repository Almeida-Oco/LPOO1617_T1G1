package Model;

public abstract class Barrel extends Entity {
    private final static Pair<Integer,Integer> def_pos =    new Pair<Integer, Integer>(6 ,222);
    protected int tick;
    protected int x_direction;
    protected boolean inverted;
    protected int n_times_inverted;

    /**
     *  Default constructor for a barrel
     * @param x X coordinate to create barrel
     * @param y Y coordinate to create barrel
     * @param x_dir Direction in which barrel is supposed to move
     */
    protected Barrel(int x, int y, int x_dir) {
        super(x,y);
        this.tick = 0;
        this.x_direction = x_dir;
        this.inverted = false;
        this.n_times_inverted = 0;
    }

    /**
     *  Creates a new barrel and returns it
     * @param map Current game map
     * @param state State of the barrel, first if it is a fire barrel, second if it is free falling
     * @return A new barrel
     */
    public static Barrel createBarrel(Map map, Pair<Boolean,Boolean> state){
        Pair<Integer,Integer> pos = (Pair<Integer,Integer>)def_pos.clone();
        if ( state.getSecond() ){
            pos.setFirst(pos.getFirst() - 2);
            pos.setSecond(pos.getSecond() - 5);
        }
        pos = map.mapPosToPixels(pos);
        return (state.getSecond()) ? new BarrelFall(pos,state, 0) :
                                new BarrelRolling(pos,state.getFirst(),1);
    }

    @Override
    public void setType(type t) {
        if (    t == type.BARREL_FALL_BACK || t == type.BARREL_FALL_FRONT || t == type.BARREL_ROLLING1 || t == type.BARREL_ROLLING2 || t == type.BARREL_ROLLING3 ||
                t == type.BARREL_ROLLING4 || t == type.FIRE_BARREL_FALL_BACK || t == type.FIRE_BARREL_FALL_FRONT || t == type.FIRE_BARREL_ROLLING )
            this.current_type = t;
    }

    /**
     *  Represents the passing of time in the game, each time character moves this should be called
     */
    protected abstract void tickTock();


    @Override
    public boolean collidesWith(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size){
        float   sphere_x = this.getX() + this.rep_size.getFirst()/2,
                sphere_y = this.getY() + this.rep_size.getSecond()/2,
                delta_x = sphere_x - Math.max( pos.getFirst() , Math.min( sphere_x , pos.getFirst() + rep_size.getFirst() )),
                delta_y = sphere_y - Math.max( pos.getSecond(), Math.min( sphere_y , pos.getSecond() + rep_size.getSecond() ));

        return (Math.pow(delta_x,2) + Math.pow(delta_y,2)) < Math.pow(this.rep_size.getFirst()/2 , 2);
    }

    @Override
    public abstract boolean toRemove(Map map);

    /**
     *  Inverts the direction of the movement
     */
    public abstract void invertDirection();

    /**
     * Checks if barrel can be inverted
     * @return Whether the barrel can be inverted or not
     */
    public abstract boolean canInvert();
}
