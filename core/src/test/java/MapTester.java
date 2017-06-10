package test.java;

import com.badlogic.gdx.Gdx;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import Model.Map;
import Model.Pair;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MapTester extends test.java.GameTest {

    @Test
    public void createMap() {
        assertNotNull(map.getMap());
        assertNotNull(map.getCollisionLayer());
        assertEquals(16f, map.getMapTileWidth() , 0.01);
        assertEquals(3f, map.getMapTileHeight() , 0.01);
        Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(1,3);
        Pair<Integer,Integer> res1 = new Pair<Integer,Integer>(16,9);
        map.setScale(1);
        assertEquals( res1, map.mapPosToPixels( test1 ) );
    }

    @Test
    public void mapAssertions(){
        assertEquals(Gdx.graphics.getWidth(), map.checkOutOfScreenWidth(1090, 0) );
        assertEquals(Gdx.graphics.getHeight(), map.checkOutOfScreenHeight(2000, 0) );
        assertEquals(0, map.checkOutOfScreenHeight(-20,0) );

        Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(0,0);
        Pair<Integer,Integer> test2 = new Pair<Integer,Integer>( (int)(2*map.getMapTileWidth()), (int)(6*map.getMapTileHeight()) );
        assertNotEquals(-1, map.nearLadder( test2.getFirst(), test2.getSecond()) );
        assertEquals( -1, map.nearLadder(test1.getFirst(), test1.getSecond() ) );
        Pair<Integer,Integer> test3 = new Pair<Integer,Integer>( (int)(4*map.getMapTileWidth()), (int)(7*map.getMapTileHeight()) );
        assertEquals( 5 , map.closestUpperStair(test3.getFirst(), test3.getSecond()) );
        assertEquals( 3 , map.closestLowerStair(test3.getFirst(), test3.getSecond()) );
        test3.setFirst(test3.getFirst() + (int)map.getMapTileWidth() ); test3.setSecond(test3.getSecond() + (int)map.getMapTileHeight());
        assertEquals( 5 , map.closestUpperStair(test3.getFirst(), test3.getSecond()) );
        assertEquals( 6 , map.closestLowerStair(test3.getFirst(), test3.getSecond()) );
        test1 = new Pair<Integer, Integer>((int)(1*map.getMapTileWidth()), (int)(6*map.getMapTileHeight()));
        test2 = new Pair<Integer, Integer>((int)(9*map.getMapTileWidth()), (int)(5*map.getMapTileHeight()));
        assertEquals( 3 , map.closestLowerStair(test1.getFirst(), test1.getSecond()) );
        assertEquals( 6 , map.closestLowerStair(test2.getFirst(), test2.getSecond()) );

        assertEquals( 2 , map.closestUpperStair(test1.getFirst(), test1.getSecond()));
        assertEquals( 8 , map.closestUpperStair(test2.getFirst(), test2.getSecond()));
    }

}
