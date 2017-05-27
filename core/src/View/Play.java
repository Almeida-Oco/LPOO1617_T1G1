package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import Controller.GameLogic;
import Controller.Entity;
import Controller.Map;
import View.Entity.EntityView;
import View.Entity.MarioView;
import View.Entity.ViewFactory;

public class Play extends ScreenAdapter {
    private SpriteBatch batch;
    private static Map map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private AssetManager assets;


    private final float JUMP_MIN_VAL = 6f;
    private final float MOVE_MIN_VAL = 1.5f;

    public void show() {
        this.batch = new SpriteBatch();
        this.map = GameLogic.getInstance().getMap();
        //TODO scaling based on monitor size
        this.renderer = new OrthogonalTiledMapRenderer(this.map.getMap(), 2.4f);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.renderer.setView(this.camera);
        this.assets = new AssetManager();
        this.loadAssets();
//      this.mario= new MarioView( new Sprite( new Texture("mario_left.png")) , (TiledMapTileLayer)map.getLayers().get("Floor") );
//      mario.setPosition(4*mario.getCollisionLayer().getTileWidth(),10*mario.getCollisionLayer().getHeight());
    }


    private void loadAssets(){
        this.assets.load( MarioView.MARIO_IMG, Texture.class );
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
            EntityView entview = ViewFactory.makeView(this.assets,ent);
            ent.setRepSize( (int)entview.getSprite().getWidth() , (int)entview.getSprite().getHeight() );
            entview.update(ent.getX(),ent.getY());
            entview.draw(this.batch);
        }
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
    }


    private void handleInput(){
        if (this.enoughToJump())
            GameLogic.getInstance().marioJump();

        float move_x = -Gdx.input.getAccelerometerX() , move_y = -Gdx.input.getAccelerometerY();
        if (Math.abs(move_x) > MOVE_MIN_VAL)
            GameLogic.getInstance().marioMoveX((int)move_x);
        if (Math.abs(move_y) > MOVE_MIN_VAL)
            GameLogic.getInstance().marioMoveY((int)move_y);
    }


    private boolean enoughToJump(){
        float x = Gdx.input.getAccelerometerX(), y = Gdx.input.getAccelerometerY(), z = Gdx.input.getAccelerometerZ();
        return Math.sqrt( Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2) ) <= JUMP_MIN_VAL;
    }
    public static Map getMap(){
        return map;
    }
}
