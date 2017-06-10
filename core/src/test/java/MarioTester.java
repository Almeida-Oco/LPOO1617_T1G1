package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;
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
    }


    @Test
    public void testXMovement(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(0,6),
                        pixel_pos = this.map.mapPosToPixels(map_pos);
        this.mario.setPos( pixel_pos );
        assertEquals( pixel_pos, mario.getPos() );
        mario = mario.moveEntity(this.map, new Pair<Integer,Integer>(LEFT,DOWN));
        assertEquals( pixel_pos, mario.getPos() );
        mario = mario.moveEntity(this.map , new Pair<Integer, Integer>(RIGHT, UP) );
        pixel_pos.setFirst( pixel_pos.getFirst() + mario.getXSpeed() );
        assertEquals( pixel_pos, mario.getPos() );
        pixel_pos.setFirst( pixel_pos.getFirst() - mario.getXSpeed() );
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(LEFT,0) );
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,0) );
        assertEquals( pixel_pos, mario.getPos() ); //pixel pos [0,18]
        for (int i = 0 ; (i*mario.getXSpeed()) < (3*this.map.getMapTileWidth()) ; i++ ) //move mario to first stair
            mario.moveEntity(this.map, new Pair<Integer, Integer>(RIGHT,0) );

        //mario walks 3 pixels per run, so we need to do an interval of X where he could be
        Pair<Integer,Integer> delta_x = new Pair<Integer, Integer>( (int)(3*this.map.getMapTileWidth()) , (int)(3*this.map.getMapTileWidth()) + Math.abs(mario.getXSpeed()) );
        assertTrue( mario.getPos().getFirst() >= delta_x.getFirst() && mario.getPos().getFirst() <= delta_x.getSecond() );
        assertEquals( mario.getPos().getSecond().intValue() , (int)(7*this.map.getMapTileHeight()) );

        int offset = mario.getX();
        for (int i = 0 ; (i*mario.getXSpeed() + offset) < (9*this.map.getMapTileWidth()) ; i++ ) //move mario to last right tile
            mario.moveEntity(this.map, new Pair<Integer, Integer>(RIGHT,0) );

        delta_x.setFirst( (int)(9*this.map.getMapTileWidth()) );
        delta_x.setSecond( (int)(9*this.map.getMapTileWidth()) + Math.abs(mario.getXSpeed()) );
        assertTrue( mario.getPos().getFirst() >= delta_x.getFirst() && mario.getPos().getFirst() <= delta_x.getSecond() );
        assertEquals( mario.getY(), (int)(5*this.map.getMapTileHeight()) );
    }

    @Test //Testing ladder on the left of map
    public void testClimbingLadder1(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(2,6),
                pixel_pos = this.map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);

        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,UP) );
        assertEquals( Entity.type.MARIO_CLIMB_LEFT , mario.getType() );
        pixel_pos.setFirst( pixel_pos.getFirst() + (int)(this.map.getMapTileWidth()-4)/2 );
        assertEquals( pixel_pos, mario.getPos());
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(RIGHT,UP) );
        pixel_pos.setSecond( pixel_pos.getSecond() + MARIO_CLIMB_RATE );
        assertEquals( pixel_pos, mario.getPos());
        int i;
        for ( i = 0 ; i < 5 ; i++ ) // GO UP
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(LEFT, UP) );

        pixel_pos.setSecond( pixel_pos.getSecond() + MARIO_CLIMB_RATE*i );
        assertEquals(pixel_pos, mario.getPos());

        for ( i = 0 ; i < 5 ; i++ ) // GO DOWN
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(RIGHT, DOWN) );

        pixel_pos.setSecond( pixel_pos.getSecond() - MARIO_CLIMB_RATE*i );
        assertEquals(pixel_pos, mario.getPos());
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(LEFT, JUMP) ); //should be ignored
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(LEFT, DOWN) );
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(LEFT, DOWN) );
        assertEquals( Entity.type.MARIO_CLIMB_OVER, mario.getType() );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );

        //Go all up the ladder
        for ( i = 0 ; i < (this.map.getMapTileHeight()*8) ; i++ )
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,UP) );

        pixel_pos.setSecond( (int)(this.map.getMapTileHeight()*14) );
        assertEquals(pixel_pos, mario.getPos() );
        assertEquals( "Model.MarioRun" , mario.getClass().getName() );
        //MARIO IS NOW RUNNING
        mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(RIGHT, DOWN) );
        assertEquals( "Model.MarioClimb" , mario.getClass().getName());
        assertEquals( pixel_pos, mario.getPos() );

        //Go all down the ladder
        for ( i = 0 ; i < (this.map.getMapTileHeight()*8 + 1) ; i++ )
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,DOWN) );

        pixel_pos.setSecond( (int)(this.map.getMapTileHeight()*6) );
        assertEquals(pixel_pos, mario.getPos() );
        assertEquals( "Model.MarioRun" , mario.getClass().getName() );
    }

    @Test //Testing ladder on the right of map
    public void testClimbingLadder2(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(8,6),
                pixel_pos = this.map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );

        for (int i = 0 ; i < (this.map.getMapTileHeight()*8) ; i++ )
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,UP) );

        pixel_pos.setFirst( pixel_pos.getFirst() + (int)(this.map.getMapTileWidth()-4)/2 );
        pixel_pos.setSecond( (int)(14*this.map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );

        for (int i = 0 ; i < (this.map.getMapTileHeight()*9) ; i++ )
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,DOWN) );

        pixel_pos.setSecond( (int)(6*this.map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
    }

    @Test
    public void testUnclimbableLadder(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(5,8),
                pixel_pos = this.map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );

        for (int i = 0 ; i < (this.map.getMapTileHeight()*3) ; i++ )
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,UP) );

        pixel_pos.setFirst( pixel_pos.getFirst() + (int)(this.map.getMapTileWidth()-4)/2 );
        pixel_pos.setSecond( (int)(10*this.map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioClimb", mario.getClass().getName() );

        for (int i = 0 ; i < (this.map.getMapTileHeight()*4) ; i++ )
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0,DOWN) );

        pixel_pos.setSecond( (int)(8*this.map.getMapTileHeight()) );
        assertEquals(pixel_pos, mario.getPos());
        assertEquals( "Model.MarioRun", mario.getClass().getName() );
    }

    @Test
    public void testJump(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(3,7),
                pixel_pos = this.map.mapPosToPixels(map_pos);
        this.mario.setPos(pixel_pos);
        assertEquals( pixel_pos, mario.getPos() );
        assertEquals( "Model.MarioRun", mario.getClass().getName() );

        int prev_y = pixel_pos.getSecond();
        for ( int i = 0 ; i < 38 ; i++ ){
            mario = mario.moveEntity(this.map, new Pair<Integer, Integer>(0, JUMP));
            int expected_delta = ((i <= 13) ? ((i == 0) ? 0 : (13 - (i-1)) ) : ( (i > 17) ? -4 : (13-i+1) )); //Mario has terminal velocity going down
            assertEquals( prev_y + expected_delta , mario.getY() , 1);
            prev_y = mario.getY();
        }
    
    }
}
