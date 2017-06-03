package Controller;


public class Fire extends Entity {
    private MoveStrategy moveStrategy;

    @Override
    public boolean collidesWith(Pair<Integer, Integer> pos, Pair<Integer, Integer> rep_size) {
        return true;
    }

    @Override
    public void setType(type t) {
        if ( t == type.FIRE_LEFT || t == type.FIRE_LEFT_IGNITE || t == type.FIRE_RIGHT || t == type.FIRE_RIGHT_IGNITE )
            this.current_type = t;
    }

    /**
     * @brief Used to move fire
     * @param map Current map of the game
     * @param mario_x X coordinate of Mario
     * @param mario_y Y coordinate of Mario
     * @return Always this object
     * It will slowly move towards Mario
     */
    @Override
    public Entity moveEntity(Map map, int mario_x, int mario_y) {
        moveStrategy.move(map, new Pair<Integer, Integer>( mario_x,mario_y ) );

        return this;
    }

    @Override
    public boolean toRemove(Map map) {
        return false;
    }
}
