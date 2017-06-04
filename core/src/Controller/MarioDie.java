package Controller;



public class MarioDie extends Mario {

    private final int ANIMATION_RATE = 10;


    /**
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     * @brief Mario constructor
     */
    protected MarioDie(int x, int y) {
        super(x, y);
    }

    @Override
    protected void tickTock() {

    }

    @Override
    public Entity moveEntity(Map map, int x_move, int y_move) {
        return null;
    }
}
