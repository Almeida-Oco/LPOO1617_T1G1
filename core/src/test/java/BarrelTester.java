package test.java;


import com.mygdx.game.MyGdxGame;

import org.junit.Test;

import Model.Barrel;
import Model.Entity;
import Model.Mario;
import Model.Pair;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BarrelTester extends GameTest {
    private Entity barrel;


    @Test
    public void testCreateBarrel() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(0, 6),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        assertEquals(Entity.type.BARREL_ROLLING1, barrel.getType());
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        assertEquals(new Pair<Integer, Integer>(4, 4), barrel.getRepSize());
        pixel_pos = new Pair<Integer, Integer>(10, 10);
        barrel.setPos(pixel_pos);
        assertEquals(pixel_pos, barrel.getPos());
        barrel.setType(Entity.type.BARREL_ROLLING2);
        assertEquals(Entity.type.BARREL_ROLLING2, barrel.getType());

    }


    @Test
    public void moveRollingBarrel() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(1, 15),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setFirst(pixel_pos.getFirst() + 3);
        assertEquals(pixel_pos, barrel.getPos());
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setFirst(pixel_pos.getFirst() + 3);
        assertEquals(pixel_pos, barrel.getPos());
        ((Barrel) barrel).invertDirection();
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setFirst(pixel_pos.getFirst() - 3);
        assertEquals(pixel_pos, barrel.getPos());

    }


    @Test
    public void moveFreeFallingBarrel() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, true));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(1, 15),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);

        assertEquals(Entity.type.BARREL_FALL_FRONT, barrel.getType());

        int i = 0, y = barrel.getY(), x = barrel.getX();
        while ( !barrel.getClass().getName().equals( "Model.BarrelRolling") ){
            assertFalse( barrel.toRemove(map) );
            assertEquals( y-=i, barrel.getY() );
            assertEquals( x , barrel.getX() );
            if ( i == 0 ) //gravity
                i=2;
            else if (i < 4 && y <= 29 )
                i+=1;
            barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0,0) );
        }

        assertEquals( 22 , barrel.getY() ,0.1 );
        /*
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setSecond(pixel_pos.getSecond() - 2);
        assertEquals(pixel_pos, barrel.getPos());
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setSecond(pixel_pos.getSecond() - 2);
        assertEquals(pixel_pos, barrel.getPos());
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        assertEquals(Entity.type.BARREL_FALL_BACK, barrel.getType());
        */

    }

    @Test
    public void startFalling() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(6, 16),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);

        assertEquals(Entity.type.BARREL_ROLLING1, barrel.getType());
        assertEquals(pixel_pos, barrel.getPos());
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setFirst(pixel_pos.getFirst() + 15);

        assertEquals(pixel_pos, barrel.getPos());
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos = barrel.getPos();
        assertEquals(Entity.type.BARREL_FALL_FRONT, barrel.getType());
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        pixel_pos.setSecond(pixel_pos.getSecond() - 2);
        assertEquals(pixel_pos, barrel.getPos());


    }

    @Test(timeout = 1000)
    public void startRolling() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(3, 8),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        while (barrel.getType() != Entity.type.BARREL_ROLLING2) {
            barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        }
        pixel_pos = barrel.getPos();
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        assertTrue(barrel.getX() > pixel_pos.getFirst());


    }

    @Test
    public void collidesWithMap() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(4, 15), //initialize barrel on the floor
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        assertTrue(barrel.getY() > pixel_pos.getSecond());
    }

    @Test(timeout = 1000)
    public void ClimbDownStairs() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(1, 15), //initialize barrel on the floor
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);

        while ( !barrel.getClass().getName().equals( "Model.BarrelFall") ) {
            barrel.setPos(pixel_pos);
            for (int i = 0; i < 10; i++)
                barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        }

        assertEquals( "Model.BarrelFall", barrel.getClass().getName() );
        int y = barrel.getY(), x = barrel.getX(), i = 0;
        while ( !barrel.getClass().getName().equals( "Model.BarrelRolling") ){
            assertFalse( barrel.toRemove(map) );
            assertEquals( y-=i, barrel.getY() );
            assertEquals( x , barrel.getX() );
            if ( i == 0 ) //gravity
                i=2;
            else if ( i < 4 )
                i+= 1;
            barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0,0) );
        }
        assertEquals("Model.BarrelRolling", barrel.getClass().getName() );
        assertEquals( 6*map.getMapTileHeight() , barrel.getY() , 0.1);
    }

    @Test (timeout = 1000)
    public void createNMoveFireBarrel() {
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(true, true));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(1, 15),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        assertEquals(Entity.type.FIRE_BARREL_FALL_FRONT, barrel.getType());
        barrel= barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        assertEquals(pixel_pos.getFirst(),barrel.getPos().getFirst());
        assertTrue(pixel_pos.getSecond()>barrel.getPos().getSecond());

        while ( !barrel.getClass().getName().equals("Model.BarrelRolling") ){
            assertFalse( barrel.toRemove( map ) );
            barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        }

        assertEquals( "Model.BarrelRolling", barrel.getClass().getName() );
        for ( int i = 0 ; i < 10 ; i++ )
            barrel = barrel.moveEntity( map , new Pair<Integer, Integer>( 0 , 0 ) );
        assertTrue( barrel.toRemove( map ) );

    }

    @Test(timeout = 2000)
    public void invertBarrelRolling(){
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(1, 6),
                pixel_pos = map.mapPosToPixels(map_pos);
        barrel.setPos(pixel_pos);
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        while(barrel.getType()!=Entity.type.BARREL_ROLLING2) {
            barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        }
        assertTrue(pixel_pos.getFirst()<barrel.getPos().getFirst());//moving right
        barrel.upgrade();
        barrel.setPos(pixel_pos);
        barrel.setType(Entity.type.BARREL_ROLLING1);
        while(barrel.getType()!=Entity.type.BARREL_ROLLING2) {
            barrel = barrel.moveEntity(map, new Pair<Integer, Integer>(0, 0));
        }

       assertTrue(pixel_pos.getFirst()>barrel.getPos().getFirst());
    }

    @Test ( timeout = 2000 )
    public void collidesWithMario(){
        this.barrel = Entity.newBarrel(map, new Pair<Boolean, Boolean>(false, false));
        barrel.setRepSize(4, 4, MyGdxGame.DEFAULT_SCALE);
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(0, 6), mario_pos = new Pair<Integer, Integer>(3,7),
                pixel_pos = map.mapPosToPixels(map_pos), mario_pixels = map.mapPosToPixels(mario_pos);
        barrel.setPos(pixel_pos);
        assertTrue( barrel.toRemove( map ) );
        Entity mario = Mario.createMario(0,0);
        mario.setRepSize(4,6,MyGdxGame.DEFAULT_SCALE);
        mario.setPos(mario_pixels);

        while ( !barrel.collidesWith(mario.getPos(), mario.getRepSize()) )
            barrel.moveEntity( map, new Pair<Integer, Integer>(0,0) );
    }
}