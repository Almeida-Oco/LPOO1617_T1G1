package test.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import Model.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MapTester extends test.java.GameTest {
    private Map map;
    private final String MAP_1 = "DKMap.tmx";

    @Test
    public void createMap() {
        this.map = new Map();
        this.map.setMap(this.mock_map, this.mock_stairs);
        assertNotNull(this.map.getMap());
        assertNotNull(this.map.getCollisionLayer());
    }

}
