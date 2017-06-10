package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;

import Model.DonkeyKong;
import Model.Entity;
import Model.Pair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class DKTester extends GameTest {

    private Entity DK;

    @Before
    public void initDK(){
        this.DK= DonkeyKong.getInstance();
        DK.setRepSize(4,6, MyGdxGame.DEFAULT_SCALE );
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(5, 16),
                pixel_pos = this.map.mapPosToPixels(map_pos);
         DK.setPos(pixel_pos);

    }

    @Test(timeout=2000)
    public void animateDK(){
        assertFalse( DK.toRemove(this.map) );
       assertEquals(Entity.type.DK_THROW_LEFT,DK.getType());
        int animation_count;
    }
}
