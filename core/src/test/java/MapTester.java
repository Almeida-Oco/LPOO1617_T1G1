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
        assertNotNull(this.map.getMap());
        assertNotNull(this.map.getCollisionLayer());
        assertEquals(16f, this.map.getMapTileWidth() , 0.01);
        assertEquals(2f, this.map.getMapTileHeight() , 0.01);
        Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(1,3);
        Pair<Integer,Integer> res1 = new Pair<Integer,Integer>(16,6);
        assertEquals( res1, this.map.mapPosToPixels( test1 ) );
    }

    @Test
    public void mapAssertions(){
        Pair<Integer,Integer> test1 = new Pair<Integer,Integer>(0,0);
        Pair<Integer,Integer> test2 = new Pair<Integer,Integer>( 2*16, 6*2 );
        assertNotEquals(-1, this.map.nearLadder( test2.getFirst(), test2.getSecond()) );
        assertEquals( -1, this.map.nearLadder(test1.getFirst(), test1.getSecond() ) );
        Pair<Integer,Integer> test3 = new Pair<Integer,Integer>( 4*16, 7*2 );
        assertEquals( 5 , this.map.closestUpperStair(test3.getFirst(), test3.getSecond()) );
        assertEquals( 3 , this.map.closestLowerStair(test3.getFirst(), test3.getSecond()) );
        test3.setFirst(test3.getFirst() + 16 ); test3.setSecond(test3.getSecond() + 2);
        assertEquals( 5 , this.map.closestUpperStair(test3.getFirst(), test3.getSecond()) );
        assertEquals( 6 , this.map.closestLowerStair(test3.getFirst(), test3.getSecond()) );
    }

}
