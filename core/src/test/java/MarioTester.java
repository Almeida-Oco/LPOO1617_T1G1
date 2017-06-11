package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import Model.Entity;
import Model.Mario;
import Model.Pair;

public class MarioTester extends GameTest {
    private final int LEFT = -1, RIGHT = 1, DOWN = -1, UP = 1, JUMP = 2, MARIO_CLIMB_RATE = 1;
    private Entity mario;

    @Before
    public void initMario(){
        this.mario = Mario.createMario(0,0);
        mario.setRepSize(4,6, MyGdxGame.DEFAULT_SCALE ); //Needs to be smaller than usual because of collisions with higher floors!
    }

    @Test
    public void testCreationAndEntity(){
        Mario mario = Mario.createMario(25,25);
        assertEquals(Entity.type.MARIO_RIGHT, mario.getType() );
        assertFalse( mario.collidesWith(null, null) );
        assertFalse( mario.toRemove(null) );
        mario.setRepSize(10, 10, MyGdxGame.DEFAULT_SCALE);
        assertEquals( new Pair<Integer,Integer>(10,10), mario.getRepSize() );
        assertEquals(3, mario.getXSpeed() );
        assertEquals( 0, mario.getYSpeed() );
        assertEquals(25, mario.getX() );
        assertEquals(25, mario.getY() );
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(100,100);
        mario.setPos(new_pos);
        mario.setYVelocity( 0 );
        mario.setXVelocity( 0 );
        assertEquals(0, mario.getXSpeed());
        assertEquals(0, mario.getYSpeed());
        mario.updateYVelocity();
        assertEquals(-1, mario.getYSpeed());
        mario.updateYVelocity();
        mario.updateYVelocity();
        mario.updateYVelocity();
        mario.updateYVelocity();
        assertEquals(-4, mario.getYSpeed());

        mario.setScale( MyGdxGame.DEFAULT_SCALE*2  );
        assertEquals(6, mario.getXSpeed());
        assertEquals(new_pos, mario.getPos());
        mario.setType( Entity.type.MARIO_CLIMB_OVER );
        assertEquals( Entity.type.MARIO_CLIMB_OVER, mario.getType() );

        ArrayList<Entity> inits = Entity.createInitialCharacters( map );
        assertEquals( 2, inits.size() );
        assertEquals( "Model.MarioRun", inits.get(0).getClass().getName() );
        assertEquals( "Model.DonkeyKong", inits.get(1).getClass().getName() );

    }


    @Test
    public void testRandomMovements(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(0,6),
                        pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos( pixel_pos );
        assertEquals( pixel_pos, mario.getPos() );
        mario = mario.moveEntity(map, new Pair<Integer,Integer>(LEFT,DOWN));
        assertEquals( pixel_pos, mario.getPos() );
        mario = mario.moveEntity(map , new Pair<Integer, Integer>(RIGHT, UP) );
        pixel_pos.setFirst( pixel_pos.getFirst() + mario.getXSpeed() );
        assertEquals( pixel_pos, mario.getPos() );
        pixel_pos.setFirst( pixel_pos.getFirst() - mario.getXSpeed() );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT,0) );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,0) );
        assertEquals( pixel_pos, mario.getPos() ); //pixel pos [0,18]
        for (int i = 0 ; (i*mario.getXSpeed()) < (3*map.getMapTileWidth()) ; i++ ) //move mario to first stair
            mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT,0) );

        //mario walks 3 pixels per run, so we need to do an interval of X where he could be
        Pair<Integer,Integer> delta_x = new Pair<Integer, Integer>( (int)(3*map.getMapTileWidth()) , (int)(3*map.getMapTileWidth()) + Math.abs(mario.getXSpeed()) );
        assertTrue( mario.getPos().getFirst() >= delta_x.getFirst() && mario.getPos().getFirst() <= delta_x.getSecond() );
        assertEquals( mario.getPos().getSecond().intValue() , (int)(7*map.getMapTileHeight()) );

        int offset = mario.getX();
        for (int i = 0 ; (i*mario.getXSpeed() + offset) < (9*map.getMapTileWidth()) ; i++ ) //move mario to last right tile
            mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT,0) );

        delta_x.setFirst( (int)(9*map.getMapTileWidth()) );
        delta_x.setSecond( (int)(9*map.getMapTileWidth()) + Math.abs(mario.getXSpeed()) );
        assertTrue( mario.getPos().getFirst() >= delta_x.getFirst() && mario.getPos().getFirst() <= delta_x.getSecond() );
        assertEquals( mario.getY(), (int)(5*map.getMapTileHeight()) );
    }

    @Test
    public void testXMovement(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(0,6),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos( pixel_pos );
        assertEquals( pixel_pos, mario.getPos() );

        //move to tile [7,7]
        for ( int i = 1, x = mario.getX() , y = mario.getY(); i < ((8*(int)map.getMapTileWidth())/3 ) ; i++ ){
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT, 0) );
            assertEquals(x+(i*3) , mario.getX() );
            if ( mario.getX() == 48  || mario.getX() == 81  )
                y+=map.getMapTileHeight();
            else if ( mario.getX() == 114 )
                y-= map.getMapTileHeight();

            assertEquals(y, mario.getY() );

            assertEquals( "Model.MarioRun" , mario.getClass().getName() );
        }
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,0) );
        assertEquals( 7 , map.XConverter( mario.getX() ) );
        assertEquals( 7 , map.YConverter( mario.getY() ) );

        //move back to tile [0,6]
        for ( int i = 1, x = mario.getX(), y = mario.getY() ; i < ((8*(int)map.getMapTileWidth())/3 ) ; i++ ){
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, 0) );
            assertEquals(x-(i*3) , mario.getX() );
            if ( mario.getX() == 45  || mario.getX() == 78  )
                y-=map.getMapTileHeight();
            else if ( mario.getX() == 111 )
                y+= map.getMapTileHeight();

            assertEquals(y, mario.getY() );

            assertEquals( "Model.MarioRun" , mario.getClass().getName() );
        }
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,0) );
        assertEquals( 0 , map.XConverter( mario.getX() ) );
        assertEquals( 6 , map.YConverter( mario.getY() ) );

    }

    @Test //Testing ladder on the left of map
    public void testClimbingLadder1(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(2,6),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);

        mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,UP) );
        assertEquals( Entity.type.MARIO_CLIMB_LEFT , mario.getType() );
        pixel_pos.setFirst( pixel_pos.getFirst() + (int)(map.getMapTileWidth()-4)/2 );
        assertEquals( pixel_pos, mario.getPos());
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT,UP) );
        pixel_pos.setSecond( pixel_pos.getSecond() + MARIO_CLIMB_RATE );
        assertEquals( pixel_pos, mario.getPos());
        int i;
        for ( i = 0 ; i < 5 ; i++ ) // GO UP
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, UP) );

        pixel_pos.setSecond( pixel_pos.getSecond() + MARIO_CLIMB_RATE*i );
        assertEquals(pixel_pos, mario.getPos());

        for ( i = 0 ; i < 5 ; i++ ) // GO DOWN
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT, DOWN) );

        pixel_pos.setSecond( pixel_pos.getSecond() - MARIO_CLIMB_RATE*i );
        assertEquals(pixel_pos, mario.getPos());
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, JUMP) ); //should be ignored
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, DOWN) );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, DOWN) );
        assertEquals( Entity.type.MARIO_CLIMB_OVER, mario.getType() );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );

        //Go all up the ladder
        for ( i = 0 ; i < (map.getMapTileHeight()*8) ; i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,UP) );

        pixel_pos.setSecond( (int)(map.getMapTileHeight()*14) );
        assertEquals(pixel_pos, mario.getPos() );
        assertEquals( "Model.MarioRun" , mario.getClass().getName() );
        //MARIO IS NOW RUNNING
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT, DOWN) );
        assertEquals( "Model.MarioClimb" , mario.getClass().getName());
        assertEquals( pixel_pos, mario.getPos() );

        //Go all down the ladder
        for ( i = 0 ; i < (map.getMapTileHeight()*8 + 1) ; i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,DOWN) );

        pixel_pos.setSecond( (int)(map.getMapTileHeight()*6) );
        assertEquals(pixel_pos, mario.getPos() );
        assertEquals( "Model.MarioRun" , mario.getClass().getName() );
    }

    @Test //Testing ladder on the right of map
    public void testClimbingLadder2(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(8,6),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );

        for (int i = 0 ; i < (map.getMapTileHeight()*8) ; i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,UP) );

        pixel_pos.setFirst( pixel_pos.getFirst() + (int)(map.getMapTileWidth()-4)/2 );
        pixel_pos.setSecond( (int)(14*map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );

        for (int i = 0 ; i < (map.getMapTileHeight()*9) ; i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,DOWN) );

        pixel_pos.setSecond( (int)(6*map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
    }

    @Test (timeout = 1000)
    public void testGoingDownLadder(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(5,8),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );

        while ( !mario.getClass().getName().equals( "Model.MarioClimb") )
            mario = mario.moveEntity(map , new Pair<Integer, Integer>(RIGHT, DOWN) );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, DOWN) );
        assertEquals( "Model.MarioClimb", mario.getClass().getName() );
        assertEquals( 8*map.getMapTileHeight() - 1, mario.getY() , 0.1);

        for ( int i = 2 ; i < 20 ; i++ ){ //GO down ladder
            mario = mario.moveEntity( map, new Pair<Integer, Integer>(RIGHT, DOWN) );
            assertEquals( (int)(8*map.getMapTileHeight()) - i , mario.getY() );
        }
        int y = mario.getY();

        for ( int i = 1 ; i < 18 ; i++ ){
            mario = mario.moveEntity( map, new Pair<Integer, Integer>(RIGHT, UP) );
            assertEquals(y + ((i == 17) ? i-1 : i) , mario.getY() );
        }
        assertEquals( "Model.MarioRun" , mario.getClass().getName() );
    }

    @Test
    public void testUnclimbableLadder(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(5,8),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );

        for (int i = 0 ; i < (map.getMapTileHeight()*3) ; i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,UP) );

        pixel_pos.setFirst( pixel_pos.getFirst() + (int)(map.getMapTileWidth()-4)/2 );
        pixel_pos.setSecond( (int)(10*map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioClimb", mario.getClass().getName() );

        for (int i = 0 ; i < (map.getMapTileHeight()*4) ; i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0,DOWN) );

        pixel_pos.setSecond( (int)(8*map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
    }

    @Test
    public void testJump(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(3,7),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );

        int prev_y = pixel_pos.getSecond();
        for ( int i = 0 ; i < 40 ; i++ ){
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(0, JUMP));
            int expected_delta = ((i <= 13) ? ((i == 0) ? 0 : (13 - (i-1)) ) : ( (i > 17) ? -4 : (13-i+1) )); //Mario has terminal velocity going down
            assertEquals( (i == 39) ? pixel_pos.getSecond() : prev_y + expected_delta , mario.getY() , 1);
            prev_y = mario.getY();
        }

        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
    }

    @Test
    public void testFall(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(2,14),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);

        for (int i = 0 ; i < 6 ; i++ ){
            assertEquals( "Model.MarioRun", mario.getClass().getName() );
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT,0) );
        }
        assertEquals( "Model.MarioFall", mario.getClass().getName() );
        pixel_pos.setFirst( pixel_pos.getFirst() + 6*3 );
        pixel_pos.setSecond( pixel_pos.getSecond() - 3);
        assertEquals( pixel_pos, mario.getPos() );

        int prev_y = pixel_pos.getSecond(), curr_x = pixel_pos.getFirst();
        for (int i = 0 ; i < 6 ; i++){
            mario = mario.moveEntity(map, new Pair<Integer, Integer>( (i < 3) ? RIGHT : LEFT ,(i < 3) ? UP : DOWN ) );
            int expected_delta = (i > 2) ? -4 : -(i+2) ; //Mario has terminal velocity going down
            assertEquals( (i != 5) ? (prev_y + expected_delta) : 7*3 , mario.getY() );
            assertEquals( curr_x, mario.getX() );
            prev_y = mario.getY();
        }

        assertEquals( "Model.MarioRun", mario.getClass().getName() );
        pixel_pos = map.mapPosToPixels(new Pair<Integer, Integer>(3,7));
        pixel_pos.setFirst( pixel_pos.getFirst() + 2);
        assertEquals( pixel_pos, mario.getPos() );
    }

    @Test (timeout = 1000)
    public void testDying(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(0,6),
                pixel_pos = map.mapPosToPixels(map_pos), die_pos = map.mapPosToPixels( new Pair<Integer, Integer>(4,8) );
        this.mario.setPos(pixel_pos);
        //Run
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
        mario.setType( Entity.type.MARIO_DYING_UP );
        mario = mario.moveEntity(map, new Pair<Integer,Integer>(LEFT,UP) );
        assertEquals( "Model.MarioDie", mario.getClass().getName() );

        for (int i = 0 ; i < 61 ; i++ ) //Do full animation
            mario = mario.moveEntity(map, new Pair<Integer, Integer>((i < 30) ? LEFT : RIGHT, (i < 30) ? UP : DOWN) );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
        assertEquals(die_pos, mario.getPos() );

        //Falling
        mario = Mario.createMario(0,0);
        map_pos.setFirst(2);
        map_pos.setSecond(14);
        pixel_pos = map.mapPosToPixels( map_pos );
        mario.setPos( pixel_pos );
        while ( !mario.getClass().getName().equals( "Model.MarioFall" ) )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>(RIGHT, UP) );
        assertEquals("Model.MarioFall", mario.getClass().getName());
        mario.setType( Entity.type.MARIO_DYING_UP );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, UP) );
        assertEquals( "Model.MarioDie", mario.getClass().getName() );
        for (int i = 0 ; i < 61 ; i++ ) //Do full animation
            mario = mario.moveEntity(map, new Pair<Integer, Integer>((i < 30) ? LEFT : RIGHT, (i < 30) ? UP : DOWN) );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
        assertEquals(die_pos, mario.getPos() );

        //Climbing
        mario = Mario.createMario(0,0);
        map_pos.setFirst(2);
        map_pos.setSecond(14);
        pixel_pos = map.mapPosToPixels( map_pos );
        mario.setPos( pixel_pos );
        for(int i = 0 ; (i < 6 || !mario.getClass().getName().equals( "Model.MarioClimb") ); i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>( (i < 3) ? LEFT : RIGHT , DOWN) );
        assertEquals("Model.MarioClimb", mario.getClass().getName());
        mario.setType( Entity.type.MARIO_DYING_UP );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, UP) );
        assertEquals( "Model.MarioDie", mario.getClass().getName() );
        for (int i = 0 ; i < 61 ; i++ ) //Do full animation
            mario = mario.moveEntity(map, new Pair<Integer, Integer>((i < 30) ? LEFT : RIGHT, (i < 30) ? UP : DOWN) );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
        assertEquals(die_pos, mario.getPos() );

        mario = Mario.createMario(0,0);
        map_pos.setFirst(2);
        map_pos.setSecond(14);
        pixel_pos = map.mapPosToPixels( map_pos );
        mario.setPos( pixel_pos );
        for(int i = 0 ; (i < 6 || !mario.getClass().getName().equals( "Model.MarioJump") ); i++ )
            mario = mario.moveEntity(map, new Pair<Integer, Integer>( RIGHT , JUMP) );
        assertEquals("Model.MarioJump", mario.getClass().getName());
        mario.setType( Entity.type.MARIO_DYING_UP );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT, UP) );
        assertEquals( "Model.MarioDie", mario.getClass().getName() );
        for (int i = 0 ; i < 61 ; i++ ) //Do full animation
            mario = mario.moveEntity(map, new Pair<Integer, Integer>((i < 30) ? LEFT : RIGHT, (i < 30) ? UP : DOWN) );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
        assertEquals(die_pos, mario.getPos() );
    }

    @Test
    public void testLeftCollision(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(3,7),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        this.mario.setRepSize(4,20,MyGdxGame.DEFAULT_SCALE );

        assertEquals(pixel_pos, mario.getPos() );
        mario = mario.moveEntity(map, new Pair<Integer, Integer>(LEFT,0) );
        pixel_pos.setFirst( pixel_pos.getFirst() + 1);
        assertEquals(pixel_pos, mario.getPos());

    }
}
