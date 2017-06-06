package test.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import Controller.GameLogic;
import Model.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MapTester extends GameTest{
    private Map map;
    private final String MAP_1 = "DKMap.tmx";
    private final String COLLISION = "Floor";
    private GameLogic game=GameLogic.getInstance();



    @Test
    public void TestGameLogicSingleton(){
        this.game=GameLogic.getInstance();
        assertNotNull(game);
    }

    @Test
    public void TestLoadMap(){
        game.setMap(MAP_1,COLLISION);
        assertNotNull(game.getMap());


    }
    @Test
    public void createAndGetCharacters(){
        game.initializeCharacters();
        assertNotNull(game.getCharacters());
    }

}
