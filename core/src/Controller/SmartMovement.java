package Controller;

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

    private int last_action = -10;

    //TODO if fire is in ladder the check if mario is in next level is different
    @Override
    public void move(Map map, Pair<Integer, Integer> mario_pos, Pair<Integer,Integer> curr_pos) {
        int action = map.checkLevelDifference(curr_pos, mario_pos);
        if (last_action != action && map.collidesBottom(curr_pos,this.rep_size.getFirst()) != -1)
            this.first_cranes = true;
        System.out.println("    ACTION = "+action);
        if ( SAME_LEVEL == action )
            this.processSameLevel(map, mario_pos, curr_pos);
        else if ( action >= UPPER_LADDER )
            this.processUpperLevel(map, mario_pos, curr_pos, action);
        else if ( action <=  LOWER_LADDER )
            this.processLowerLevel(map, mario_pos, curr_pos, action);
    }

    /**
     * Processes current difference of positions between Fire and Mario
     * @param map Current game map
     * @param mario_pos Mario position
     * @param curr_pos Fire position
     * @param diff Difference of positions, can be UPPER_LADDER, UPPER_LEVEL or ABOVE_2
     * //TODO perhaps too many if else
     */
    private void processUpperLevel(Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos , int diff){
        if ( this.in_ladder )
            this.moveVertically(map, curr_pos, UP );
        else{
            int ladder_x = (int)(map.closestUpperStair( curr_pos.getFirst(), curr_pos.getSecond() )*map.getMapTileWidth());
            if ( (UPPER_LEVEL == diff || ABOVE_2 == diff) && Math.abs(ladder_x - curr_pos.getFirst()) > X_TOLERANCE )
                this.moveHorizontally(map, curr_pos, (ladder_x > curr_pos.getFirst()) ? RIGHT : LEFT);
            else if ( Math.abs(ladder_x - curr_pos.getFirst()) <= X_TOLERANCE )
                this.moveVertically(map, curr_pos, UP );
            else if ( Math.abs(mario_pos.getFirst() - curr_pos.getFirst()) > X_TOLERANCE)
                this.moveHorizontally(map, curr_pos, (mario_pos.getFirst() > curr_pos.getFirst()) ? RIGHT : LEFT);
        }
    }

    /**
     * Processes current difference of positions between Fire and Mario
     * @param map Current game map
     * @param mario_pos Mario position
     * @param curr_pos Fire position
     * @param diff Difference of positions, can be LOWER_LADDER, LOWER_LEVEL or BELOW_2
     */
    private void processLowerLevel(Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos, int diff){
        if ( this.in_ladder )
            this.moveVertically( map,curr_pos, DOWN );
        else{
            int ladder_x = (int)(map.closestLowerStair( curr_pos.getFirst() , curr_pos.getSecond() )*map.getMapTileWidth());
            if ( (LOWER_LEVEL == diff || BELOW_2 == diff) && Math.abs(ladder_x - curr_pos.getFirst()) > X_TOLERANCE )
                this.moveHorizontally(map, curr_pos, (ladder_x > curr_pos.getFirst()) ? RIGHT : LEFT );

            else if ( Math.abs(ladder_x - curr_pos.getFirst()) <= X_TOLERANCE )
                this.moveVertically(map, curr_pos, DOWN);

            else if ( Math.abs(mario_pos.getFirst() - curr_pos.getFirst()) > X_TOLERANCE)
                this.moveHorizontally(map, curr_pos, (mario_pos.getFirst() > curr_pos.getFirst()) ? RIGHT : LEFT);
        }
    }

    private void processSameLevel( Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos ){
        if ( !this.in_ladder )
            this.moveHorizontally(map,curr_pos, (mario_pos.getFirst() > curr_pos.getFirst()) ? RIGHT : LEFT );
        else
            this.moveVertically(map, curr_pos, DOWN );
    }

    /**
     * Aims fire horizontal direction based
     * @param curr_pos Current position
     * @param ladder_x If Fire is supposed to go to a ladder this will contain the ladder X, -1 otherwise
     * @param curr_dir Current fire direction
     * @return  Direction fire is supposed to go
     */
    private int aimFire(Pair<Integer, Integer> curr_pos, int ladder_x, int curr_dir) {
        int dir = curr_dir;
        if ( ladder_x != -1) {
            if (ladder_x < curr_pos.getFirst())
                dir = LEFT;
            else if (ladder_x > curr_pos.getFirst())
                dir = RIGHT;
            else
                dir = 0;
        }

        return dir;
    }

}
