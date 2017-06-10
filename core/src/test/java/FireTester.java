package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Model.Entity;
import Model.Fire;
import Model.Mario;
import Model.Pair;

public class FireTester extends GameTest {
    Entity fire;

    @Before
    public void initMario(){
        this.fire = Entity.newFire(map);
        this.fire.setRepSize( 4,5, MyGdxGame.DEFAULT_SCALE );
    }

    @Test
    public void testCreation(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(0,6),
                pixel_pos = map.mapPosToPixels(map_pos);
        this.fire.setPos(pixel_pos);
        assertEquals( pixel_pos, this.fire.getPos() );
        this.fire.setType( Entity.type.FIRE_LEFT_IGNITE );
        assertEquals( Entity.type.FIRE_LEFT_IGNITE , fire.getType() );
        this.fire.setType( Entity.type.FIRE_LEFT );
        assertEquals( Entity.type.FIRE_LEFT , fire.getType() );
    }

    @Test ( timeout = 1000 )
    public void testCollisionWithMario(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(2,6), mario_pos = new Pair<Integer, Integer>(0,6),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos(pixel_pos);
        Entity mario = Mario.createMario(0,0);
        mario.setPos(mario_pixels);
        mario.setRepSize(4,6,MyGdxGame.DEFAULT_SCALE);
        this.fire.upgrade();

        int x = fire.getX(), y = fire.getY();
        while( !this.fire.collidesWith(mario.getPos(), mario.getRepSize()) ){
            assertEquals( x , fire.getX() );
            x-=2;
            assertEquals( y , fire.getY() );
            fire.moveEntity(map, mario.getPos() );
        }

    }

    @Test (timeout = 1000)
    public void testAISameLevel(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(0,6),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

        //mario on left
        int x = fire.getX(), y = fire.getY();
        while ( !(fire.getX() >= 0 && fire.getX() <= 3) ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            x-=2;
            if ( fire.getX() == 48 )
                y-= 3;
            fire.moveEntity(map, mario_pixels);
        }
        assertTrue( fire.collidesWith(mario_pixels, new Pair<Integer, Integer>(4,6) ) );

        fire.setPos( pixel_pos );
        mario_pos = new Pair<Integer, Integer>(9,5);
        mario_pixels = map.mapPosToPixels(mario_pos);
        mario_pixels.setFirst( mario_pixels.getFirst() + 5 );

        //mario on right
        x = fire.getX(); y = fire.getY();
        while ( !( fire.getX() >= 149 && fire.getX() <= 153 ) ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            x+=2;
            if ( fire.getX() == 78 )
                y+= 3;
            else if ( fire.getX() == 110 || fire.getX() == 126 || fire.getX() == 142)
                y-= 3;
            fire.moveEntity(map, mario_pixels);
        }
        assertTrue( fire.collidesWith(mario_pixels, new Pair<Integer, Integer>(4,6) ) );

    }

    @Test
    public void testAIMarioClimbing(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(2,9),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();
        //MARIO ON LEFT STAIR
        int x = fire.getX(), y = fire.getY();
        while ( !(this.fire.getX() >= 32 && this.fire.getX() <= 34) ){
            assertEquals( x , fire.getX() );
            x-=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 48 )
                y-= 3;
            this.fire.moveEntity(map, mario_pixels);
        }

        //it should now start climbing
        x = fire.getX(); y = fire.getY();
        while ( !(this.fire.getY() >= 27 && this.fire.getY() <= 29) ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            y+=1;
            this.fire.moveEntity(map,mario_pixels);
        }
        //END MARIO ON LEFT STAIR

        //MARIO ON RIGHT STAIR
        this.fire.setPos(pixel_pos);
        mario_pos = new Pair<Integer, Integer>(8,9);
        mario_pixels = map.mapPosToPixels( mario_pos );

        x = fire.getX(); y = fire.getY();
        while ( !(this.fire.getX() >= 133 && this.fire.getX() <= 135) ){
            assertEquals( x , fire.getX() );
            x+=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 78 )
                y+= 3;
            else if ( fire.getX() == 110 || fire.getX() == 126 || fire.getX() == 142)
                y-= 3;
            this.fire.moveEntity(map, mario_pixels);
        }
    }
}
