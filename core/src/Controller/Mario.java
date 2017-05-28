package Controller;

public class Mario extends Entity {
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
        this.current_type=type.MARIO_LEFT;
    }

    @Override
    public type getType() {
        return current_type;
    }

}
