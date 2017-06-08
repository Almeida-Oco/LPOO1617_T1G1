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
        this.map = new Map();
        this.map.setMap(this.mock_map, this.mock_stairs);
        assertNotNull( this.map.getMap() );
        assertNotNull( this.map.getCollisionLayer() );
    }
}
