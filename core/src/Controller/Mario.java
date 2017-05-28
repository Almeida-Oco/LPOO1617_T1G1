package Controller;

public class Mario extends Entity {
    private boolean in_stair = false;
    private static Mario instance = null;
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
    }

    @Override
    public void setType(type t) {
        if (t == type.MARIO_LEFT || t == type.MARIO_RIGHT)
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

}
