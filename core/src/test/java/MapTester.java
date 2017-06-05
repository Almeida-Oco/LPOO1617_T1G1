import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import Model.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MapTester extends GameTest{
    private Map map;


    @Test
    public void createMap() {
        this.map = mock(Map.class);
        assertNotNull( this.map.getMap() );
        assertNotNull( this.map.getCollisionLayer() );
        assertEquals(2, this.map.getMapTileHeight());
        assertEquals(16, this.map.getMapTileWidth());
    }
}
