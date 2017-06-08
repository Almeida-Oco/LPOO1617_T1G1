import org.junit.Test;

import Model.Pair;

import static org.junit.Assert.*;

public class PairTester extends GameTest {

    @Test
    public void testPairClass(){
        Pair<Integer,Integer> test = new Pair<Integer,Integer>(1,2);
        assertEquals(1,test.getFirst().intValue()); assertEquals(2, test.getSecond().intValue());
        assertEquals( "[1,2]" , test.toString());
        assertEquals(false , test.equals( new Pair<Integer,Integer>(1,1)));
        assertEquals(false , test.equals( new Pair<Integer,Integer>(2,1)));
        assertEquals(false , test.equals( new Pair<Integer,Integer>(2,2)));
        assertEquals(false , test.equals( new Integer(1) ));
        assertEquals(test  , test.clone());
        test.setFirst(-1);
        assertEquals(-1, test.getFirst().intValue() );
        test.setSecond( -5 );
        assertEquals( -5, test.getSecond().intValue() );
        assertEquals( "[-1,-5]", test.toString() );
    }
}
