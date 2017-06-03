package Controller;

public abstract class MoveStrategy {
    protected int n_times = 0;

    public abstract Pair<Integer,Integer> move(Map map, Pair<Integer,Integer> mario_pos);

}
