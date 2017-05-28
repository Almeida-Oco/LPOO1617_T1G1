package Controller;

public class Mario extends Entity {
    private boolean in_stair = false;
    private static Mario instance = null;
    private Mario(){};

    public static Mario getInstance(){
        if (instance == null)
            return (instance = new Mario());
        else
            return instance;
    }

    public Mario(int x , int y){
        super(x,y);
    }

    @Override
    public type getType() {
        return type.MARIO;
    }

    public boolean isOnStair(){
        return this.in_stair;
    }

    public void setInStair(boolean b){
        this.in_stair = b;
    }

}
