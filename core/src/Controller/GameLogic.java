package Controller;

import java.util.ArrayList;

import Model.Entity;
import Model.Pair;
import View.ScoreTimer;

public class GameLogic {
    private Pair<Integer,Integer> barrels_pos = new Pair<Integer, Integer>(6,222);
    private static GameLogic instance;
    private Model.Map map = null;
    private float time_passed = -1;
    private int time_to_throw = 3;
    private boolean first_barrel_falled = false;
    private boolean first_barrel_thrown = false;

    //!Mario should always be first character!
    Model.Entity mario;
    Model.Entity DK;
    private ArrayList<Model.Entity> barrels = new ArrayList<Model.Entity>();
    private ArrayList<Model.Entity> fires = new ArrayList<Model.Entity>();


    private GameLogic(){}

    public static GameLogic getInstance(){
        if (instance == null)
            return (instance = new GameLogic());
        else
            return instance;
    }

    public void initializeCharacters() {
        ArrayList<Entity> init_chrs = Entity.createInitialCharacters(this.map);
        mario = init_chrs.get(0);
        DK = init_chrs.get(1);
        this.fires.add(Entity.newFire(this.map));
    }


    public void updateDK( float delta ) {
        if ( this.time_passed < time_to_throw && delta < time_to_throw)
            this.time_passed+=delta;

        if ( this.time_passed > time_to_throw ) {
            if ( DK.moveEntity( map, new Pair<Integer, Integer>(1, (this.first_barrel_thrown) ? 0 : 1)) == null ) { //second number is irrelevant
                this.addNewBarrel( DK.getType() == Model.Entity.type.DK_FRONT );
                this.time_passed = 0;
                this.time_to_throw = (int)(Math.random()*3)+1;
            }
        }
        else
            DK.moveEntity( map,new Pair<Integer, Integer>(0,0) );
    }

    private void addNewBarrel(boolean free_fall ){
        this.barrels.add( Model.Barrel.createBarrel(this.map, new Pair<Boolean, Boolean>(!this.first_barrel_thrown , free_fall) ) );
        this.first_barrel_thrown = true;
    }

    public void moveMario(int x_move, int y_move){
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(x_move,y_move)) ;
        this.score();
    }

    public void moveEnemies(){
        this.moveFires();
        this.moveBarrels();
    }

    public void moveBarrels(){
        for (int i = 0 ; i < this.barrels.size() ; i++) {
            boolean die =  this.barrels.get(i).collidesWith(mario.getPos(), mario.getRepSize());
            if ( die || this.barrels.get(i).toRemove(this.map)){
                this.barrels.remove(i);
                if ( die )
                    mario.setType(Model.Entity.type.MARIO_DYING_UP);
                this.first_barrel_falled = true;
            }else
                this.barrels.set(i,this.barrels.get(i).moveEntity(map, new Pair<Integer, Integer>(0,0) )); //numbers are irrelevant
        }
    }

    public void moveFires(){
        for ( int i = 0 ; i < this.fires.size() ; i++){
            if ( this.fires.get(i).collidesWith( mario.getPos(), mario.getRepSize()) ){
                mario.setType( Entity.type.MARIO_DYING_UP );
                this.fires.remove(i);
            }

            else
                this.fires.get(i).moveEntity(this.map, mario.getPos() );
        }
    }

    public void score(){
        int score=0;
        for (int i = 0 ; i < this.barrels.size() ; i++){
            int     mario_x = mario.getX(), mario_y = mario.getY(), barrel_x = this.barrels.get(i).getPos().getFirst(), barrel_y = this.barrels.get(i).getPos().getSecond()+ this.barrels.get(i).getRepSize().getSecond()*2,
                    barrel_y_wwidth=this.barrels.get(i).getPos().getSecond()+ this.barrels.get(i).getRepSize().getSecond()*2,
                    b_img_w = this.barrels.get(i).getRepSize().getFirst(), score_x = barrel_x + b_img_w/2;
            float mario_x_speed = this.mario.getXSpeed();

            Model.Pair<Integer,Integer> delta_x = new Model.Pair<Integer, Integer>( score_x - (int)(Math.floor(mario_x_speed/2)) , score_x + (int)Math.ceil(mario_x_speed/2) ),
                                delta_y = new Model.Pair<Integer, Integer>(barrel_y, barrel_y_wwidth);

            if(mario_x>=delta_x.getFirst() && mario_x<= delta_x.getSecond() && mario_y >= delta_y.getFirst() &&  mario_y<=delta_y.getSecond()  )
                score+=100;

        }
        ScoreTimer.addScore(score);
    }


    public ArrayList<Model.Entity> getCharacters(){
        ArrayList<Model.Entity> all_chars = new ArrayList<Model.Entity>();
        all_chars.add(this.mario);
        all_chars.add(DK);
        all_chars.addAll(this.barrels);
        all_chars.addAll(this.fires);
        return all_chars;
    }

    public Model.Map getMap(){
        return this.map;
    }

    public void setMap(String map_name, String collision_layer){
        this.map = new Model.Map();
        this.map.loadMap(map_name, collision_layer);
    }

    public boolean firstBarrelFalled(){
        return this.first_barrel_falled;
    }


}
