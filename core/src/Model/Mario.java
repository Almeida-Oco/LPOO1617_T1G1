package Model;

public class Mario extends Entity {
    private Mario instance = null;
    private Mario(){};

    public Mario getInstance(){
        if (this.instance == null)
            return (this.instance = new Mario());
        else
            return this.instance;
    }
}
