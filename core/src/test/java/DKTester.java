package test.java;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Model.DonkeyKong;
import Model.Entity;
import Model.Mario;
import Model.Pair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    @Test(timeout = 1000)
    public void animateDK(){
        assertFalse( DK.toRemove(this.map) );
       assertEquals(Entity.type.DK_THROW_LEFT,DK.getType());
        boolean throw_left=false, throw_front=false, right_barrel=false,left_hand=false, right_hand=false;

        while( !throw_left || !throw_front || !right_barrel && !left_hand && !right_hand){
                DK.moveEntity(this.map,new Pair<Integer, Integer>((Math.random()<0.5)?0:1,0));
            switch (DK.getType().ordinal()) {
                case 12:
                    throw_left=true;
                    break;
                case 14:
                    throw_front=true;
                    break;
                case 16:
                    right_barrel=true;
                    break;
                case 17:
                    left_hand=true;
                    break;
                case 18:
                    right_hand=true;
                    break;
            }
        }
    }

    @Test
    public void collidesWithMario() {
        this.DK= DonkeyKong.getInstance();
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(4, 16), //initialize barrel on the floor
                pixel_pos = map.mapPosToPixels(map_pos);

        DK.setPos(pixel_pos);
        DK.setType(Entity.type.DK_LEFT_BARREL);
        DK.setRepSize(4,6, MyGdxGame.DEFAULT_SCALE );;
        Entity mario = Mario.createMario(0,0);
        mario.setRepSize(4,6,MyGdxGame.DEFAULT_SCALE);
        pixel_pos.setFirst(pixel_pos.getFirst()+2);
        mario.setPos(pixel_pos);
        assertTrue(DK.collidesWith(mario.getPos(), mario.getRepSize()));


    }
}
