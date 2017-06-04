package Controller;

import java.util.ArrayList;

import View.ScoreTimer;

public class GameLogic {
    private Pair<Integer,Integer> barrels_pos = new Pair<Integer, Integer>(6,222);
    private Pair<Integer,Integer> mario_pos = new Pair<Integer, Integer>(4,8);
    private static GameLogic instance;
    private Map map = null;
    private float time_passed = -1;
    private int time_to_throw = 3;
    private boolean first_barrel_falled = false;
    private boolean first_barrel_thrown = false;

    //!Mario should always be first character!
    private ArrayList<Entity> chars = new ArrayList<Controller.Entity>();
    private ArrayList<Entity> fires = new ArrayList<Entity>();


    private GameLogic(){}

    public static GameLogic getInstance(){
        if (instance == null)
            return (instance = new GameLogic());
        else
            return instance;
    }

    public void initializeCharacters() {
        Pair<Integer,Integer> DK_pos = new Pair<Integer, Integer>(3,222);
        Pair<Integer,Integer> mario_pos = this.map.mapPosToPixels(this.mario_pos);
        Pair<Integer,Integer> fire_pos = this.map.mapPosToPixels(new Pair<Integer,Integer>(15,51));
        DK_pos= this.map.mapPosToPixels(DK_pos);
        this.chars.add( Mario.createMario(mario_pos.getFirst(), mario_pos.getSecond()));
        Entity DK = DonkeyKong.getInstance();
        this.fires.add( new Fire(fire_pos.getFirst(), fire_pos.getSecond(), new SmartMovement() ));
        DK.setPos(DK_pos);
        this.chars.add( DK );
    }


    public void updateDK( float delta ) {
        if ( this.time_passed < time_to_throw && delta < time_to_throw)
            this.time_passed+=delta;

        if ( this.time_passed > time_to_throw) {
            if ( this.chars.get(1).moveEntity(map, 1, (this.first_barrel_thrown) ? 0 : 1) == null ) { //second number is irrelevant
                this.createNewBarrel((this.chars.get(1).getType() == Entity.type.DK_FRONT));
                this.time_passed = 0;
                this.time_to_throw = (int)(Math.random()*3)+1;
            }
        }
        else
            this.chars.get(1).moveEntity(map,0,0);
    }

    private void createNewBarrel( boolean free_fall ){
        Pair<Integer,Integer> barrel_pos = (Pair<Integer,Integer>)this.barrels_pos.clone();
        if ( free_fall ){ //free falling barrel
            barrel_pos.setFirst(barrel_pos.getFirst() - 2);
            barrel_pos.setSecond(barrel_pos.getSecond() - 5);
        }

        barrel_pos = this.map.mapPosToPixels(barrel_pos);
        this.chars.add( Barrel.createBarrel(barrel_pos.getFirst(), barrel_pos.getSecond(), !this.first_barrel_thrown , free_fall) );
        this.first_barrel_thrown = true;
    }

    public void moveMario(int x_move, int y_move){
        this.chars.set( 0 , this.chars.get(0).moveEntity(this.map,x_move,y_move) );
        Score();
    }

    public void moveBarrels(){
        for (int i = 2 ; i < this.chars.size() ; i++) {
            if ( this.chars.get(i).collidesWith(this.chars.get(0).getPos(), this.chars.get(0).getRepSize()) || this.chars.get(i).toRemove(this.map)){
                this.chars.remove(i);
                this.first_barrel_falled = true;
            }else
                this.chars.set(i,this.chars.get(i).moveEntity(map,0,0)); //numbers are irrelevant
        }
    }

    public void moveFires(){
        for ( int i = 0 ; i < this.fires.size() ; i++){
            if ( this.fires.get(i).collidesWith( this.chars.get(0).getPos(), this.chars.get(0).getRepSize()) )
                this.fires.remove(i);
            else
                this.fires.get(i).moveEntity(this.map, this.chars.get(0).getX(), this.chars.get(0).getY() );
        }
    }

    public void Score(){
        int score=0;
        for (int i = 2 ; i < this.chars.size() ; i++){
           /* if(this.chars.get(0).getPos().getFirst()<= (this.chars.get(i).getPos().getFirst()+ this.chars.get(i).getRepSize().getFirst()) &&
                    (this.chars.get(0).getPos().getFirst() + this.chars.get(0).getRepSize().getFirst())>= this.chars.get(i).getPos().getFirst() &&
                    this.chars.get(0).getPos().getSecond()>= ( this.chars.get(i).getPos().getSecond() + this.chars.get(0).getRepSize().getSecond())
                    && this.chars.get(0).getPos().getSecond()<= ( this.chars.get(i).getPos().getSecond() + (this.chars.get(0).getRepSize().getSecond()*2)))*/

           Pair<Integer,Integer>delta_x= new Pair<Integer, Integer>(new Integer((this.chars.get(i).getPos().getFirst()+ this.chars.get(i).getRepSize().getFirst()/2)-(int)(Math.floor(this.chars.get(0).getXSpeed()/2))),
                   new Integer((this.chars.get(i).getPos().getFirst()+ this.chars.get(i).getRepSize().getFirst()/2)+(int)(Math.ceil(this.chars.get(0).getXSpeed()/2))));
            Pair<Integer,Integer>delta_y = new Pair<Integer, Integer>(new Integer(this.chars.get(i).getPos().getSecond()),new Integer(this.chars.get(i).getPos().getSecond()*2));
                if(this.chars.get(0).getPos().getFirst()>=delta_x.getFirst() && this.chars.get(0).getPos().getFirst()<= delta_x.getSecond() &&
                        this.chars.get(0).getPos().getSecond() >= delta_y.getFirst() &&  this.chars.get(0).getPos().getSecond()<=delta_y.getSecond()   )
                score+=100;

        }
        ScoreTimer.addScore(score);
    }




    public ArrayList<Entity> getCharacters(){
        ArrayList<Entity> all_chars = new ArrayList<Entity>();
        all_chars.addAll(this.chars);
        all_chars.addAll(this.fires);
        return all_chars;
    }

    public Controller.Map getMap(){
        return this.map;
    }

    public void setMap(String map_name, String collision_layer){
        this.map = new Map();
        this.map.loadMap(map_name, collision_layer);
    }

    public boolean firstBarrelFalled(){
        return this.first_barrel_falled;
    }


}
