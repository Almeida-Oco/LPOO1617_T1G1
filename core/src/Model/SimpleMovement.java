package Model;

public class SimpleMovement extends MoveStrategy {
    private int CHANGE_DIR = 5;
    private int SURE_STEP = 5;
    private int STAY_STILL = 5;

    private final MoveStrategy smart = new SmartMovement();

    private int dir = 0;

    public SimpleMovement() {
        this.tick = 0;
    }

    @Override
    public void setRep(Pair<Integer,Integer> rep){
        this.smart.setRep(rep);
        this.rep_size = rep;
    }

    @Override
    public void move(Model.Map map, Pair<Integer, Integer> mario_pos, Pair<Integer,Integer> curr_pos) {
        this.smart.setSpeed( new Pair<Float, Float>((float)this.x_speed, (float)this.y_speed));
        if ( SURE_STEP != -1)
            smart.move(map,mario_pos,curr_pos);
        else if ( CHANGE_DIR != -1 )
            this.randomMove(map,curr_pos);

        this.tickTock();
    }


    private void randomMove(Model.Map map, Pair<Integer,Integer> curr_pos){
        boolean ladder = (Math.random()*10) < 4;
        if ( this.tick >= CHANGE_DIR && !this.in_ladder )
            this.moveHorizontally(map,curr_pos, dir );
        else if ( (ladder && ( (this.dir == 1 && this.checkUpperLadder(map,curr_pos)) || this.dir == -1 && this.checkLowerLadder(map,curr_pos) )) || this.in_ladder )
            this.moveVertically(map,curr_pos, dir );
    }


    /**
     * To be called every time tick equals any of the states
     */
    private void randomize(){
        int rand = (int)(Math.random()*10);
        if ( rand < 4 ){
            this.STAY_STILL = (int)(Math.random()*5+1);
            this.SURE_STEP = -1;
            this.CHANGE_DIR = -1;
        } else if ( rand < 8 ){
            this.STAY_STILL = -1;
            this.SURE_STEP = -1;
            this.CHANGE_DIR = (int)(Math.random()*5+1);
            this.dir = ((Math.random()*2)<1) ? RIGHT : LEFT;
        } else{
            this.STAY_STILL = -1;
            this.SURE_STEP = (int)(Math.random()*4+1);
            this.CHANGE_DIR = -1;
        }
    }

    /**
     * Represents the passing of time
     */
    private void tickTock(){
        this.tick++;
        if ( this.tick == CHANGE_DIR || this.tick == SURE_STEP || this.tick == STAY_STILL ){
            this.randomize();
            this.tick = 0;
        }
    }
}

