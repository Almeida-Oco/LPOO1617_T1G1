package Controller;

import java.util.ArrayList;

import Model.Entity;
import Model.Pair;
import View.ScoreTimer;

public class GameLogic {
    private final int N_LIVES = 3;
    private final int TIME_LIMIT = 480;

    private static GameLogic instance;
    private Model.Map map = null;
    private float time_passed = -1;
    private int time_to_throw = 3;
    private boolean first_barrel_falled;
    private boolean first_barrel_thrown;

    Model.Entity mario;
    Model.Entity DK;
    private ArrayList<Model.Entity> barrels = new ArrayList<Model.Entity>();
    private ArrayList<Model.Entity> fires = new ArrayList<Model.Entity>();
    private int lives;
    private boolean die;

    /**
     * Default GameLogic constructo
     */
    private GameLogic(){
        this.lives = N_LIVES;
        this.die = false;
        this.first_barrel_falled = false;
        this.first_barrel_thrown = false;
    }

    /**
     * Gets an instance of GameLogic
     * @return The single instance of GameLogic present throughout the whole program
     */
    public static GameLogic getInstance(){
        if (instance == null)
            return (instance = new GameLogic());
        else
            return instance;
    }

    /**
     * Initializes the game characters
     */
    public void initializeCharacters() {
        ArrayList<Entity> init_chrs = Entity.createInitialCharacters(this.map);
        mario = init_chrs.get(0);
        DK = init_chrs.get(1);
        this.fires.clear();
        ScoreTimer.setLives(lives);
    }

    /**
     * Updates DonkeyKong sprites
     * @param delta How much time has passed since last call to this function
     */
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

    /**
     * Adds a new barrel to the barrel array
     * @param free_fall Whether the barrel is supposed to free fall or not
     */
    private void addNewBarrel(boolean free_fall ){
        this.barrels.add( Model.Entity.newBarrel(this.map, new Pair<Boolean, Boolean>(!this.first_barrel_thrown , free_fall) ) );
        this.first_barrel_thrown = true;
    }

    /**
     * Moves Mario according to given directions
     * @param x_move X movement, 1 -> RIGHT, 0 Stay still, -1 -> LEFT
     * @param y_move Y movement, -1 -> DOWN, 0 Stay still, 1 -> UP, 2 -> JUMP
     */
    public void moveMario(int x_move, int y_move){
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(x_move,y_move)) ;
        if(die){
            this.barrels.clear();
            die=false;
        }
        this.checkOnTopOfFire( mario.getX(), mario.getY() );

        this.updateScore();
    }

    /**
     * Checks if the game was won
     * @return Whether the game was won or not
     */
    public boolean gameWon(){
        Pair<Integer,Integer> win_pos = new Pair<Integer,Integer>(16,254),
                mario_pos = new Pair<Integer, Integer>(this.map.XConverter(mario.getX()), this.map.YConverter(mario.getY()));
        if( win_pos.equals(mario_pos) )
            return true;
        else
            return false;
    }

    /**
     * Moves all enemies
     * @param delta How much time has passed since last call to this function
     */
    public void moveEnemies(float delta){
        if( ScoreTimer.getTime() < TIME_LIMIT )
            for  ( Entity fire : this.fires )
                fire.upgrade();

        this.moveFires();
        this.moveBarrels();
        this.updateDK(delta);
    }

    /**
     * Moves the barrels
     */
    public void moveBarrels(){
        for (int i = 0 ; i < this.barrels.size() ; i++) {
           boolean died =  this.barrels.get(i).collidesWith(mario.getPos(), mario.getRepSize());
            if ( died || this.barrels.get(i).toRemove(this.map)){
                this.barrels.remove(i);
                this.first_barrel_falled = true;
                if ( died )
                    killMario();
            }else
                this.barrels.set(i,this.barrels.get(i).moveEntity(map, new Pair<Integer, Integer>(0,0) )); //numbers are irrelevant
        }
    }

    /**
     * Kills mario
     */
    private void killMario() {
        mario.setType(Entity.type.MARIO_DYING_UP);
        lives--;
        ScoreTimer.setLives(lives);
        this.fires.clear();
        this.barrels.clear();
        this.first_barrel_falled = false;
        this.first_barrel_thrown = false;
        die=true;
    }

    /**
     * Moves the fires
     */
    public void moveFires(){
        for ( int i = 0 ; i < this.fires.size() ; i++){
            if ( this.fires.get(i).collidesWith( mario.getPos(), mario.getRepSize()) )
                killMario();
            else
                this.fires.get(i).moveEntity(this.map, mario.getPos() );
        }
    }

    /**
     * Checks if Mario jumped across an enemy, in which case he gains points
     * @param entities Enemies to check if mario jumped on top of
     * @return How many points mario made
     */
    private int testJumps(ArrayList<Model.Entity> entities){
        int ret=0;
        for (int i = 0 ; i < entities.size() ; i++){
            int     mario_x = mario.getX(), mario_y = mario.getY(), entity_x = entities.get(i).getPos().getFirst(), entity_y = entities.get(i).getPos().getSecond()+ entities.get(i).getRepSize().getSecond(),
                    entity_y_width=entities.get(i).getPos().getSecond()+ entities.get(i).getRepSize().getSecond()*2,
                    b_img_w = entities.get(i).getRepSize().getFirst(), score_x = entity_x + b_img_w/2;
            float mario_x_speed = this.mario.getXSpeed();

            Model.Pair<Integer,Integer> delta_x = new Model.Pair<Integer, Integer>( score_x - (int)(Math.floor(mario_x_speed/2)) , score_x + (int)Math.ceil(mario_x_speed/2) ),
                    delta_y = new Model.Pair<Integer, Integer>(entity_y, entity_y_width);

            if(mario_x>=delta_x.getFirst() && mario_x<= delta_x.getSecond() && mario_y >= delta_y.getFirst() &&  mario_y<=delta_y.getSecond()  )
                ret+=100;

        }
        return ret;
    }

    /**
     * Checks if mario is on top of Fire Barrel
     * @param x X pixel coordinate of mario
     * @param y Y pixel coordinate of mario
     */
    private void checkOnTopOfFire(int x , int y){
        x = this.map.XConverter(x); y = this.map.YConverter(y);
        if ( x >= 1 && x <= 2 && y >= 24 && y <= 34 && !this.marioDying() )
            this.killMario();
    }

    /**
     * Updates game score
     */
    public void updateScore(){
        int score=testJumps(this.fires);
        score+= testJumps(this.barrels);
        ScoreTimer.addScore(score);
    }

    /**
     * Gets all characters of the game
     * @return Array with all game characters
     */
    public ArrayList<Model.Entity> getCharacters(){
        ArrayList<Model.Entity> all_chars = new ArrayList<Model.Entity>();
        all_chars.add(this.mario);
        all_chars.add(DK);
        all_chars.addAll(this.barrels);
        all_chars.addAll(this.fires);
        return all_chars;
    }

    /**
     * Gets current game map
     * @return Current game map
     */
    public Model.Map getMap(){
        return this.map;
    }

    /**
     * Sets the current game map
     * @param map_name The name of the map to be loaded
     * @param collision_layer The name of the collision layer of this map
     */
    public void setMap(String map_name, String collision_layer){
        this.map = new Model.Map();
        this.map.loadMap(map_name, collision_layer);
    }

    /**
     * Checks if the first barrel has fallen
     * @return Whether the first barrel has fallen or not
     */
    public boolean firstBarrelFalled() {
        if ( this.first_barrel_falled && this.fires.size() == 0 )
            fires.add( Entity.newFire( this.map ));

        return this.first_barrel_falled;
    }

    /**
     * Checks if mario ran out of lives
     * @return Whether mario ran out of lives or not
     */
    public boolean isDead(){
        if( this.lives == 0 ){
            this.lives = N_LIVES;
            return true;
        }
        else
            return false;
    }

    /**
     * Checks if mario is currently in the dying animation
     * @return Whether mario is in the dying animation or not
     */
    private boolean marioDying(){
        Entity.type t = mario.getType();
        return (Entity.type.MARIO_DIED == t || Entity.type.MARIO_DYING_DOWN == t || Entity.type.MARIO_DYING_LEFT == t ||
                Entity.type.MARIO_DYING_RIGHT == t || Entity.type.MARIO_DYING_UP == t );
    }
}
