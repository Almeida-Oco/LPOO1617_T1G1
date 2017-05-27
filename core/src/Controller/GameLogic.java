package Controller;

import java.util.LinkedList;

public class GameLogic {
    private static GameLogic instance;
    private LinkedList<Controller.Entity> chars = new LinkedList<Controller.Entity>();
    private Map map;

    private GameLogic(){
        this.chars.add( new Mario(50, 50) );
        this.map = new Map();
    };


    public static GameLogic getInstance(){
        if (instance == null)
            return (instance = new GameLogic());
        else
            return instance;
    }

    public LinkedList<Controller.Entity> getCharacters(){
        return this.chars;
    }

    public Controller.Map getMap(){
        return this.map;
    }

    public void marioJump(){
        this.chars.getFirst().moveY(30);
    }

    public void marioMoveX(int x){
        this.chars.getFirst().moveX(x);
    }

    public void marioMoveY(int y){
        this.chars.getFirst().moveY(y);
    }
}
