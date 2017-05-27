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


    public boolean collisionOnX(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_x){
        boolean collision_x = false;
        if(new_x < old_pos.getFirst()) //moving left
            collision_x = this.map.collidesLeft(old_pos,rep_size);
        if(new_x > old_pos.getFirst()) //moving right
            collision_x = this.map.collidesRight(old_pos,rep_size);

        return collision_x;
    }

    public boolean collisionOnY(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, int new_y){
        boolean collision_y = false;
        if( new_y < old_pos.getSecond() ) //moving down
            collision_y = this.map.collidesBottom(old_pos,rep_size);

        if( new_y > old_pos.getSecond() ) //moving up
            collision_y = this.map.collidesTop(old_pos,rep_size);

        return collision_y;
    }

    public Pair<Integer,Integer> moveSingleEntity(Pair<Integer,Integer> old_pos, Pair<Integer,Integer> rep_size, Pair<Integer,Integer> move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( old_pos.getFirst()+move.getFirst() , old_pos.getSecond()+move.getSecond());

        if( collisionOnX( old_pos, rep_size, move.getFirst()) ) {
            System.out.println("adeus");
            new_pos.setFirst( old_pos.getFirst() );
        }

        if ( collisionOnY( old_pos, rep_size, move.getSecond() ) ) {
            System.out.println("ola");
            new_pos.setSecond( old_pos.getSecond() );
        }
        return new_pos;
    }
}
