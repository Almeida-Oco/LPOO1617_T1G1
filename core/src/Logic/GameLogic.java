package Logic;
import java.util.ArrayList;
import Model.Entity;

public class GameLogic {
    private GameLogic instance;
    private ArrayList<Entity> chars;
    private Map map;

    private GameLogic(){};


    public GameLogic getInstance(){
        if (this.instance == null)
            return (this.instance = new GameLogic());
        else
            return this.instance;
    }
}
