package test.java;

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
        Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(0,0);
        Pair<Integer,Integer> test2 = new Pair<Integer,Integer>( 2*16, 6*3 );
        assertNotEquals(-1, map.nearLadder( test2.getFirst(), test2.getSecond()) );
        assertEquals( -1, map.nearLadder(test1.getFirst(), test1.getSecond() ) );
        Pair<Integer,Integer> test3 = new Pair<Integer,Integer>( 4*16, 7*3 );
        assertEquals( 5 , map.closestUpperStair(test3.getFirst(), test3.getSecond()) );
        assertEquals( 3 , map.closestLowerStair(test3.getFirst(), test3.getSecond()) );
        test3.setFirst(test3.getFirst() + 16 ); test3.setSecond(test3.getSecond() + 3);
        assertEquals( 5 , map.closestUpperStair(test3.getFirst(), test3.getSecond()) );
        assertEquals( 6 , map.closestLowerStair(test3.getFirst(), test3.getSecond()) );
    }

}
