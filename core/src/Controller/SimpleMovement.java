package Controller;

public class SimpleMovement extends MoveStrategy {
    private int CHANGE_DIR = 5;
    private final int SURE_DIR = 20;

    private int dir = 0;

    public SimpleMovement() {
        this.tick = 0;
    }

    @Override
    public Pair<Integer, Integer> move(Map map, Pair<Integer, Integer> mario_pos, Pair<Integer,Integer> curr_pos) {
        this.randomMove(map,curr_pos);
//        if ( this.tick == SURE_STEP ){
//            this.tickTock(); //CHAMAR A FUNCAO MOVE DO SMART MOVEMENT
//        }
//        else
//            this.randomMove(map,curr_pos);

        this.tickTock();
        return null;
    }


    private void randomMove(Map map, Pair<Integer,Integer> curr_pos){
        boolean ladder = (Math.random()*10) < 1;
        if ( this.tick >= CHANGE_DIR){
            this.dir = ((Math.random()*2) < 1) ? RIGHT : LEFT;
            this.CHANGE_DIR = (int)(Math.random()*4)+1;
        }

        if ( (ladder && ( (this.dir == 1 && this.checkUpperLadder(map,curr_pos)) || this.dir == -1 && this.checkLowerLadder(map,curr_pos) )) || this.in_ladder ){
            this.in_ladder = true;
            this.in_ladder = moveVertically(map,curr_pos, dir );
        }
        else{
            this.in_ladder = false;
            moveHorizontally(map,curr_pos, dir );
        }

    }

    private void tickTock(){
        if (this.tick >= CHANGE_DIR)
            this.tick = 0;

        this.tick++;
    }
}

