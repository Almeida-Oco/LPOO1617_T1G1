package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import Controller.GameLogic;
import Controller.Entity;
import View.Entity.EntityView;
import View.Entity.ViewFactory;

public class Play extends ScreenAdapter {
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private AssetManager assets;
    private float scale;

    private final int JUMP = 2;
    private final float JUMP_MIN_VAL = 5f;
    private final float MOVE_MIN_VAL = 1.5f;
    private final float CLIMB_MIN_VAL = 1.5f;
    private final float REAL_GRAVITY = 9.8f;
    private final String MAP_1 = "DKMap.tmx";
    private final String COLLISION = "Floor";

    public void show() {
        this.batch = new SpriteBatch();
        GameLogic.getInstance().setMap(MAP_1, COLLISION);
        this.map = GameLogic.getInstance().getMap().getMap();
        this.scale = this.mapScaling();
        this.renderer = new OrthogonalTiledMapRenderer(this.map, this.scale );
        GameLogic.getInstance().getMap().setScale(this.scale);
        GameLogic.getInstance().initializeCharacters();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.renderer.setView(this.camera);
        this.assets = new AssetManager();
        this.loadAssets();
    }

    //TODO this will start to get quite big, separate by type, MARIO, FIRE, DK, BARREL ...
    private void loadAssets(){
        this.assets.load("mario/left.png", Texture.class);
        this.assets.load("mario/right.png",Texture.class);
        this.assets.load("mario/run_left.png", Texture.class);
        this.assets.load("mario/run_right.png",Texture.class);
        this.assets.load("mario/climb_left.png",Texture.class);
        this.assets.load("mario/climb_right.png",Texture.class);
        this.assets.load("mario/climb_over.png",Texture.class);
        this.assets.load("barrels/rolling.png", Texture.class);
        this.assets.load("barrels/falling_back.png",Texture.class);
        this.assets.load("barrels/falling_front.png",Texture.class);
        this.assets.load("dk/front.png",Texture.class);
        this.assets.load("dk/left_barrel.png",Texture.class);
        this.assets.load("dk/right_hand.png",Texture.class);
        this.assets.load("dk/throw_right.png",Texture.class);
        this.assets.load("dk/throw_left.png",Texture.class);
        this.assets.load("dk/right_barrel.png",Texture.class);
        this.assets.load("dk/right_hand.png",Texture.class);
        this.assets.load("dk/left_hand.png",Texture.class);
        this.assets.finishLoading();
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        this.renderer.render();

        this.batch.begin();
            this.drawEntities();
        this.batch.end();

        this.handleInput();
    }


    private void drawEntities(){
        for (Entity ent : GameLogic.getInstance().getCharacters() ){
            EntityView ent_view = ViewFactory.makeView(this.assets,ent, this.scale);
            ent.setRepSize( ent_view.getImgWidth(), ent_view.getImgHeight() , this.scale);
            ent_view.updatePos(ent.getX(),ent.getY());
            ent_view.draw(this.batch);
        }
    }


    private void handleInput(){
        GameLogic game = GameLogic.getInstance();
        int x_move = 0 , y_move = 0;
        float acc_x = -Gdx.input.getAccelerometerX(), acc_y = -Gdx.input.getAccelerometerY();
        float jump_offset = 0f;
        if ( this.enoughToJump() )
            jump_offset = 0.75f;

        if (Math.abs(acc_y) > CLIMB_MIN_VAL)
            y_move = (int)(acc_y/Math.abs(acc_y));
        if (Math.abs(acc_x) > (MOVE_MIN_VAL-jump_offset))
            x_move = (int)(acc_x/Math.abs(acc_x));
        if (jump_offset != 0)
            y_move = JUMP;


        game.moveMario(x_move, y_move);
        game.moveBarrel();
        game.Kong();
    }

    private boolean enoughToJump(){
        float x = Gdx.input.getAccelerometerX(), y = Gdx.input.getAccelerometerY(), z = Gdx.input.getAccelerometerZ();
        return (Math.sqrt( Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2) ) <= JUMP_MIN_VAL && x < REAL_GRAVITY && y < REAL_GRAVITY && z < REAL_GRAVITY);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        this.map.dispose();
        this.renderer.dispose();
        this.batch.dispose();
        this.assets.dispose();
    }

    private float mapScaling(){
        return Gdx.graphics.getWidth() / (((TiledMapTileLayer)this.map.getLayers().get("Floor")).getWidth()*((TiledMapTileLayer)this.map.getLayers().get("Floor")).getTileWidth());
    }
}
