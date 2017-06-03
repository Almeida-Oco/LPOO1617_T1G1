package Controller;

public class SmartMovement extends MoveStrategy {
    private final int NEXT_CRANES = 31;


    @Override
    public Pair<Integer, Integer> move(Map map, Pair<Integer, Integer> mario_pos, Pair<Integer,Integer> curr_pos) {

        if ( (map.YConverter(mario_pos.getSecond())-map.YConverter(curr_pos.getSecond())) > NEXT_CRANES ){ //mario is in higher crane
            //procurar escada mais proxima no msm nivel
        }
        else //Mario ta no msm nivel, apenas mudar o x
            this.moveHorizontally(map,curr_pos, (curr_pos.getFirst() > mario_pos.getFirst()) ? -1 : 1);



        return curr_pos;
    }

}
