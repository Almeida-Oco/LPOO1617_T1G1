package Model;

public class SmartMovement extends MoveStrategy {
    private final int SAME_LEVEL = 0;
    private final int UPPER_LADDER = 1;
    private final int UPPER_LEVEL = 2;
    private final int ABOVE_2 = 3;
    private final int LOWER_LADDER = -1;
    private final int LOWER_LEVEL = -2;
    private final int BELOW_2  = -3;
    private final int NEXT_CRANES = 15;
    private final int X_TOLERANCE = 3;


    @Override
    public void move(Model.Map map, Pair<Integer, Integer> mario_pos, Pair<Integer,Integer> curr_pos) {
        int action = map.checkLevelDifference(curr_pos, mario_pos);
        System.out.println("    ACTION = "+action+", FIRE POS = "+curr_pos.toString()+", MARIO POS = "+mario_pos.toString());
        if ( SAME_LEVEL == action )
            this.processSameLevel(map, mario_pos, curr_pos);
        else if ( action >= UPPER_LADDER )
            this.processUpperLevel(map, mario_pos, curr_pos, action);
        else if ( action <=  LOWER_LADDER )
            this.processLowerLevel(map, mario_pos, curr_pos, action);
    }

    /**
     * Process what should happen when mario is on an upper level
     * @param map Current game map
     * @param mario_pos Mario position
     * @param curr_pos Fire position
     * @param diff Difference of positions, can be UPPER_LADDER, UPPER_LEVEL or ABOVE_2
     * //TODO perhaps too many if else
     */
    private void processUpperLevel(Model.Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos , int diff){
        if ( this.in_ladder )
            this.moveVertically(map, curr_pos, (curr_pos.getSecond() > mario_pos.getSecond()) ? DOWN : UP );
        else{
            int ladder_x = (int)(map.closestUpperStair( curr_pos.getFirst(), curr_pos.getSecond() )*map.getMapTileWidth());
            if (UPPER_LEVEL == diff || ABOVE_2 == diff){
                if ( Math.abs(ladder_x - curr_pos.getFirst()) > X_TOLERANCE )
                    this.moveHorizontally(map, curr_pos, (ladder_x > curr_pos.getFirst()) ? RIGHT : LEFT );
                else
                    this.moveVertically(map,curr_pos, UP);
            }

            else if ( Math.abs(ladder_x - curr_pos.getFirst()) <= X_TOLERANCE || Math.abs(mario_pos.getFirst() - curr_pos.getFirst()) <= X_TOLERANCE )
                this.moveVertically(map, curr_pos, UP );

            else if ( Math.abs(mario_pos.getFirst() - curr_pos.getFirst()) > X_TOLERANCE)
                this.moveHorizontally(map, curr_pos, (mario_pos.getFirst() > curr_pos.getFirst()) ? RIGHT : LEFT);
        }
    }

    /**
     * Process what should happen when mario is on a lower level
     * @param map Current game map
     * @param mario_pos Mario position
     * @param curr_pos Fire position
     * @param diff Difference of positions, can be LOWER_LADDER, LOWER_LEVEL or BELOW_2
     */
    private void processLowerLevel(Model.Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos, int diff){
        if ( this.in_ladder )
            this.moveVertically( map,curr_pos, (this.previous_dir == UP && diff != LOWER_LEVEL) ? UP : DOWN );
        else{
            int ladder_x = (int)(map.closestLowerStair( curr_pos.getFirst() , curr_pos.getSecond() )*map.getMapTileWidth());
            if (LOWER_LEVEL == diff || BELOW_2 == diff){
                if ( Math.abs(ladder_x - curr_pos.getFirst()) > X_TOLERANCE )
                    this.moveHorizontally(map, curr_pos, (ladder_x > curr_pos.getFirst()) ? RIGHT : LEFT );
                else
                    this.moveVertically(map,curr_pos, DOWN);
            }
            else if ( Math.abs(mario_pos.getFirst() - curr_pos.getFirst()) <= X_TOLERANCE )
                this.moveVertically(map, curr_pos, DOWN);

            else if ( Math.abs(mario_pos.getFirst() - curr_pos.getFirst()) > X_TOLERANCE)
                this.moveHorizontally(map, curr_pos, (mario_pos.getFirst() > curr_pos.getFirst()) ? RIGHT : LEFT);
        }
    }

    /**
     * Process what should happen when Mario is on the same level as the Fire
     * @param map Current game map
     * @param mario_pos Mario position
     * @param curr_pos Fire position
     */
    private void processSameLevel(Model.Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos ){
        if ( !this.in_ladder )
            this.moveHorizontally(map,curr_pos, (mario_pos.getFirst() > curr_pos.getFirst()) ? RIGHT : LEFT );
        else
            if ( map.collidesBottom(curr_pos,this.rep_size.getFirst()/2) != -1 )
                this.moveVertically(map, curr_pos, UP );
            else
                this.moveVertically(map, curr_pos, DOWN );

    }

}
