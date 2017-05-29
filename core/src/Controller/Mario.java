package Controller;

public class Mario extends Entity {
    private boolean in_stair = false;
    private static Mario instance = null;
    private int climbing;
    private int running;

    private Mario(){};

    public static Mario getInstance(){
        if (instance == null)
            return (instance = new Mario(21,69));
        else
            return instance;
    }

    private Mario(int x , int y){
        super(x,y);
        this.current_type=type.MARIO_LEFT;
        this.climbing=0;
        this.running=0;
    }

    @Override
    public void setType(type t) {
        if (t == type.MARIO_LEFT || t == type.MARIO_RIGHT || t==type.MARIO_CLIMB_LEFT || t==type.MARIO_CLIMB_RIGHT || t==type.MARIO_RUN_RIGHT|| t==type.MARIO_RUN_LEFT )
            this.current_type = t;
    }

    @Override
    public type getType() {
        return current_type;
    }

    public boolean isOnStair(){
        return this.in_stair;
    }

    public void setInStair(boolean b){
        this.in_stair = b;
    }

    public int getClimbing(){return this.climbing;}

    public void setClimbing(int climbing){this.climbing= climbing;}

    public int getRunning(){return this.running;}

    public void setRunning(int running){this.running= running;}

}
