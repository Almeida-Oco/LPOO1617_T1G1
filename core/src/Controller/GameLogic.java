package Controller;

import java.util.ArrayList;

import Model.Entity;
import Model.Pair;
import View.ScoreTimer;

public class GameLogic {
    private final int N_LIVES = 3;

    private static GameLogic instance;
    private Model.Map map = null;
    private float time_passed = -1;
    private int time_to_throw = 3;
    private boolean first_barrel_falled;
    private boolean first_barrel_thrown;

    //!Mario should always be first character!
    Model.Entity mario;
    Model.Entity DK;
    private ArrayList<Model.Entity> barrels = new ArrayList<Model.Entity>();
    private ArrayList<Model.Entity> fires = new ArrayList<Model.Entity>();
    private int lives;
    private boolean die;


    private GameLogic(){
        this.lives = N_LIVES;
        this.die = false;
        this.first_barrel_falled = false;
        this.first_barrel_thrown = false;
    }

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
        this.fires.clear();
        this.fires.add(Entity.newFire(this.map));
        ScoreTimer.setLives(lives);
    }

    public void updateDK( float delta ) {
        if ( this.time_passed < time_to_throw && delta < time_to_throw)
            this.time_passed+=delta;
        if(DK.collidesWith( mario.getPos(), mario.getRepSize()) )
            killMario();

        if ( this.time_passed > time_to_throw ) {
            if ( DK.moveEntity( map, new Pair<Integer, Integer>(1, (this.first_barrel_thrown) ? 0 : 1)) == null ) {
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
        if(die){
            this.barrels.clear();
            die=false;
           // this.fires.add(Entity.newFire(this.map));
        }

        this.score();
    }

    public boolean gameWon(){
        if(mario.getPos().getFirst()>=532 && mario.getPos().getFirst()<=812 && mario.getPos().getSecond()>=1632)
            return true;
        else
            return false;
    }

    public void moveEnemies(float delta){
        this.moveFires();
        this.moveBarrels();
        this.updateDK(delta);
    }

    public void moveBarrels(){
        for (int i = 0 ; i < this.barrels.size() ; i++) {
           boolean died =  this.barrels.get(i).collidesWith(mario.getPos(), mario.getRepSize());
            if ( died || this.barrels.get(i).toRemove(this.map)){
                this.barrels.remove(i);
                if ( died )
                    killMario();
                this.first_barrel_falled = true;
            }else
                this.barrels.set(i,this.barrels.get(i).moveEntity(map, new Pair<Integer, Integer>(0,0) )); //numbers are irrelevant
        }
    }

    private void killMario() {
        mario.setType(Entity.type.MARIO_DYING_UP);
        lives--;
        ScoreTimer.setLives(lives);
        die=true;
    }

    public void moveFires(){
        for ( int i = 0 ; i < this.fires.size() ; i++){
            if ( this.fires.get(i).collidesWith( mario.getPos(), mario.getRepSize()) ){
                killMario();
                this.fires.remove(i);
            }
            else
                this.fires.get(i).moveEntity(this.map, mario.getPos() );
        }
    }

    private int testjumps(ArrayList<Model.Entity> entities){
        int ret=0;
        for (int i = 0 ; i < entities.size() ; i++){
            int     mario_x = mario.getX(), mario_y = mario.getY(), entity_x = entities.get(i).getPos().getFirst(), entity_y = entities.get(i).getPos().getSecond()+ entities.get(i).getRepSize().getSecond(),
                    entity_y_wwidth=entities.get(i).getPos().getSecond()+ entities.get(i).getRepSize().getSecond()*2,
                    b_img_w = entities.get(i).getRepSize().getFirst(), score_x = entity_x + b_img_w/2;
            float mario_x_speed = this.mario.getXSpeed();

            Model.Pair<Integer,Integer> delta_x = new Model.Pair<Integer, Integer>( score_x - (int)(Math.floor(mario_x_speed/2)) , score_x + (int)Math.ceil(mario_x_speed/2) ),
                    delta_y = new Model.Pair<Integer, Integer>(entity_y, entity_y_wwidth);

            if(mario_x>=delta_x.getFirst() && mario_x<= delta_x.getSecond() && mario_y >= delta_y.getFirst() &&  mario_y<=delta_y.getSecond()  )
                ret+=100;

        }
        return ret;
    }

    public void score(){
        int score=0;
        score+=testjumps(this.fires);
        score+= testjumps(this.barrels);
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

    public boolean firstBarrelFalled() {
        return this.first_barrel_falled;
    }

    public boolean isDead(){
        if( this.lives == 0 ){
            this.lives = N_LIVES;
            return true;
        }
        else
            return false;
    }

}
