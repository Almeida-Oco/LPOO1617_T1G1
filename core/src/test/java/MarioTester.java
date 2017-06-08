package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Test;
import static org.junit.Assert.*;

import Model.Entity;
import Model.Mario;
import Model.Pair;

public class MarioTester extends GameTest {

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
}
