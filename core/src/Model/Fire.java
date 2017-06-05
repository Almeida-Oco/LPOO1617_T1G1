package Model;


public class Fire extends Entity {
    private final int ANIMATION_RATE = 5;
    private MoveStrategy move_strategy;
    private int prev_x;
    private int tick;

    public Fire(Pair<Integer,Integer> pos, MoveStrategy strategy){
        super(pos.getFirst(), pos.getSecond());
        this.prev_x = pos.getFirst();
        this.current_type = type.FIRE_RIGHT;
        this.move_strategy = new SmartMovement();
        this.DEFAULT_MAX_X_VELOCITY = 2;
        this.tick = 0;
    }

    @Override
    public boolean collidesWith(Pair<Integer, Integer> pos, Pair<Integer, Integer> rep_size) {
        float   sphere_x = this.getX() + this.rep_size.getFirst()/2,
                sphere_y = this.getY() + this.rep_size.getFirst()/2,
                delta_x = sphere_x - Math.max( pos.getFirst() , Math.min( sphere_x , pos.getFirst() + rep_size.getFirst() )),
                delta_y = sphere_y - Math.max( pos.getSecond(), Math.min( sphere_y , pos.getSecond() + rep_size.getSecond() ));

        return (Math.pow(delta_x,2) + Math.pow(delta_y,2)) < Math.pow(this.rep_size.getFirst()/2 , 2);
    }

    @Override
    public void setType(type t) {
        if ( t == type.FIRE_LEFT || t == type.FIRE_LEFT_IGNITE || t == type.FIRE_RIGHT || t == type.FIRE_RIGHT_IGNITE )
            this.current_type = t;
    }

    /**
     *  Used to move fire
     * @param map Current map of the game
     * @param mario_x X coordinate of Mario
     * @param mario_y Y coordinate of Mario
     * @return Always this object
     * It will slowly move towards Mario
     */
    @Override
    public Entity moveEntity(Map map, Pair<Integer,Integer> mario_pos) {
        this.move_strategy.setSpeed(this.velocity);
        this.move_strategy.move(map, mario_pos, this.position );
        this.tickTock();
        return this;
    }

    @Override
    public boolean toRemove(Map map) {
        return false;
    }

    protected void tickTock(){
        if ( (type.FIRE_LEFT_IGNITE == this.current_type || type.FIRE_LEFT == this.current_type) && (prev_x-this.position.getFirst()) < 0 )
            this.changeDirection();
        else if ( (type.FIRE_RIGHT_IGNITE == this.current_type || type.FIRE_RIGHT == this.current_type) && (prev_x-this.position.getFirst()) > 0 )
            this.changeDirection();

        this.prev_x = this.getX();

        if ( this.tick == ANIMATION_RATE ){
            this.updateType();
            this.tick = 0;
        }
    }

    private void updateType(){
        if ( type.FIRE_LEFT == this.current_type )
            this.current_type = type.FIRE_LEFT_IGNITE;
        else if ( type.FIRE_RIGHT == this.current_type )
            this.current_type = type.FIRE_RIGHT_IGNITE;
        else if ( type.FIRE_LEFT_IGNITE == this.current_type )
            this.current_type = type.FIRE_LEFT;
        else if ( type.FIRE_RIGHT_IGNITE == this.current_type )
            this.current_type = type.FIRE_RIGHT;
    }

    private void changeDirection(){
        if ( type.FIRE_LEFT == this.current_type )
            this.current_type = type.FIRE_RIGHT;
        else if ( type.FIRE_RIGHT == this.current_type )
            this.current_type = type.FIRE_LEFT;
        else if ( type.FIRE_LEFT_IGNITE == this.current_type )
            this.current_type = type.FIRE_RIGHT_IGNITE;
        else if ( type.FIRE_RIGHT_IGNITE == this.current_type )
            this.current_type = type.FIRE_LEFT_IGNITE;
    }

    @Override
    public void setRepSize(int width, int height, float scale){
        this.setScale(scale);
        this.rep_size.setFirst(width);
        this.rep_size.setSecond(height);
        this.move_strategy.setRep(this.rep_size);
    }
}
