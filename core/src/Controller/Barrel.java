package Controller;

public abstract class Barrel extends Entity {
    protected int tick;
    protected int x_direction;
    protected boolean inverted;
    protected int n_times_inverted;

    /**
     * @brief Default constructor for a barrel
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
     * @brief Creates a new barrel and returns it
     * @param x X coordinate to create barrel
     * @param y Y coordinate to create barrel
     * @param free_falling Whether the barrel is free falling or not
     * @return A new barrel
     */
    public static Barrel createBarrel(int x , int y, boolean fire, boolean free_falling){
        Barrel ret;
        if ( free_falling )
            ret =  new BarrelFall(x,y,1,fire,true);
        else
            ret = new BarrelRolling(x,y,1,false);

        return ret;
    }

    @Override
    public void setType(type t) {
        if (    t == type.BARREL_FALL_BACK || t == type.BARREL_FALL_FRONT || t == type.BARREL_ROLLING1 || t == type.BARREL_ROLLING2 || t == type.BARREL_ROLLING3 ||
                t == type.BARREL_ROLLING4 || t == type.FIRE_BARREL_FALL_BACK || t == type.FIRE_BARREL_FALL_FRONT || t == type.FIRE_BARREL_ROLLING )
            this.current_type = t;
    }

    /**
     * @brief Represents the passing of time in the game, each time character moves this should be called
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
     * @brief Inverts the direction of the movement
     */
    public abstract void invertDirection();

    public abstract boolean canInvert();
}
