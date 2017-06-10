package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Model.Entity;
import Model.Mario;
import Model.Pair;

public class FireTester extends GameTest {
    Entity fire;

    @Before
    public void initFire(){
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
        assertFalse( this.fire.toRemove(map) );
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

    @Test (timeout = 5000)
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

    @Test (timeout = 5000)
    public void testAIMarioClimbingUpperLeft(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(2,9),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

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

        assertTrue( fire.collidesWith(mario_pixels, new Pair<Integer, Integer>(4,6) ));
    }

    @Test (timeout = 5000)
    public void testAIMarioClimbingUpperRight(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(8,9),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

        int x = fire.getX(), y = fire.getY();
        while ( !(this.fire.getX() >= 78 && this.fire.getX() <= 82) ){
            assertEquals( x , fire.getX() );
            x+=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 78 )
                y+= 3;
            else if ( fire.getX() == 110 || fire.getX() == 126 || fire.getX() == 142)
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

    }

    @Test (timeout = 5000)
    public void testAIMarioUpperLevel(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(6,16),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

        int x = fire.getX(), y = fire.getY();
        while ( !(this.fire.getX() >= 78 && this.fire.getX() <= 82) ){
            System.out.println("X ="+fire.getX()+", Y = "+fire.getY() );
            assertEquals( x , fire.getX() );
            x+=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 78 )
                y+= 3;
            else if ( fire.getX() == 110 || fire.getX() == 126 || fire.getX() == 142)
                y-= 3;
            this.fire.moveEntity(map, mario_pixels);
        }

        //it should now start climbing
        x = fire.getX(); y = fire.getY();
        while ( this.fire.getY() < 48 ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            y+=1;
            this.fire.moveEntity(map,mario_pixels);
        }

        //go after mario
        x = fire.getX(); y = fire.getY();
        while ( !( this.fire.getX() >= mario_pixels.getFirst() ) ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            x+=2;
            this.fire.moveEntity(map,mario_pixels);
        }

        assertTrue( this.fire.collidesWith(mario_pixels, new Pair<Integer, Integer>(4,6)) );
    }

    @Test (timeout = 5000)
    public void testAIMarioClimbingLowerLeft(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(3,2),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

        int x = fire.getX(), y = fire.getY();
        while ( !(this.fire.getX() >= 48 && this.fire.getX() <= 50) ){
            assertEquals( x , fire.getX() );
            x-=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 48 )
                y-= 3;
            this.fire.moveEntity(map, mario_pixels);
        }

        //it should now start climbing
        x = fire.getX(); y = fire.getY();
        while ( !(this.fire.getY() >= 6 && this.fire.getY() <= 8) ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            y-=1;
            this.fire.moveEntity(map,mario_pixels);
        }

        assertTrue( fire.collidesWith(mario_pixels, new Pair<Integer, Integer>(4,6) ));
    }

    @Test (timeout = 5000)
    public void testAIMarioClimbingLowerRight(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,7), mario_pos = new Pair<Integer, Integer>(6,2),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

        int x = fire.getX(), y = fire.getY();
        while ( !(this.fire.getX() >= 94 && this.fire.getX() <= 98) ){
            assertEquals( x , fire.getX() );
            x+=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 78 )
                y+= 3;
            else if ( fire.getX() == 110 || fire.getX() == 126 || fire.getX() == 142)
                y-= 3;
            this.fire.moveEntity(map, mario_pixels);
        }

        //it should now start climbing
        x = fire.getX(); y = fire.getY();
        while ( !(this.fire.getY() >= 6 && this.fire.getY() <= 8) ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            y-=1;
            this.fire.moveEntity(map,mario_pixels);
        }

    }

    @Test (timeout = 5000)
    public void testAIMarioLowerLevel(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(4,16), mario_pos = new Pair<Integer, Integer>(3,7),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        this.fire.setPos((Pair<Integer,Integer>)pixel_pos.clone());
        this.fire.upgrade();

        int x = fire.getX(), y = fire.getY();
        while ( !(this.fire.getX() >= 78 && this.fire.getX() <= 82) ){
            assertEquals( x , fire.getX() );
            x+=2;
            assertEquals( y , fire.getY() );
            if ( fire.getX() == 78 )
                y+= 3;
            else if ( fire.getX() == 110 || fire.getX() == 126 || fire.getX() == 142)
                y-= 3;
            this.fire.moveEntity(map, mario_pixels);
        }

        //it should now start climbing
        x = fire.getX(); y = fire.getY();
        while ( this.fire.getY() >= 24 ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            y-=1;
            this.fire.moveEntity(map,mario_pixels);
        }

        //go after mario
        x = fire.getX(); y = fire.getY();
        while ( this.fire.getX() >= mario_pixels.getFirst() ){
            assertEquals(x, fire.getX());
            assertEquals(y, fire.getY());
            x-=2;
            this.fire.moveEntity(map,mario_pixels);
        }

        assertTrue( this.fire.collidesWith(mario_pixels, new Pair<Integer, Integer>(4,6)) );

    }

    @Test
    public void testSimpleMovement(){
        Pair<Integer,Integer> map_pos = new Pair<Integer,Integer>(5,8), pixel_pos = map.mapPosToPixels(map_pos),
                mario_pos = new Pair<Integer, Integer>(4,16), mario_pixel = map.mapPosToPixels(mario_pos);
        map_pos.setFirst( map_pos.getFirst() + 10 );
        this.fire.setPos( (Pair<Integer,Integer>)pixel_pos.clone() );
        int stay = 0, left = 0, right = 0, up = 0, down = 0, N = 25;

        while ( stay < N && left < N && right < N && up < N && down < N ){
            fire.moveEntity(map, mario_pixel);
            int x = fire.getX(), y = fire.getY();
            if ( pixel_pos.getFirst() < x )
                right++;
            else if ( pixel_pos.getFirst() > x )
                left++;
            else if ( pixel_pos.getSecond() < y)
                up++;
            else if ( pixel_pos.getSecond() > y)
                down++;
            else
                stay++;
        }
    }
}
