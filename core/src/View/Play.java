package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.sun.org.apache.regexp.internal.RE;

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

    private final float JUMP_MIN_VAL = 5f;
    private final float MOVE_MIN_VAL = 1.5f;
    private final float CLIMB_MIN_VAL = 1.5f;
    private final float REAL_GRAVITY = 9.8f;

    public void show() {
        this.batch = new SpriteBatch();
        this.map = GameLogic.getInstance().getMap().getMap();
        float scale = this.mapScaling();
        this.renderer = new OrthogonalTiledMapRenderer(this.map, scale );
        GameLogic.getInstance().getMap().setScale(scale);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.renderer.setView(this.camera);
        this.assets = new AssetManager();
        this.loadAssets();
    }


    private void loadAssets(){
        this.assets.load( "mario_left.png", Texture.class);
        this.assets.load("mario_right.png",Texture.class);
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
            EntityView ent_view = ViewFactory.makeView(this.assets,ent);
            ent.setRepSize( (int)(ent_view.getSprite().getWidth()*ent_view.getImgScale()) , (int)(ent_view.getSprite().getHeight()*ent_view.getImgScale()) );
            ent_view.updatePos(ent.getX(),ent.getY());
            ent_view.draw(this.batch);
        }
    }


    //TODO when mario is jumping x_velocity is constant
    private void handleInput(){
        GameLogic game = GameLogic.getInstance();
        if (this.enoughToJump())
            game.marioJump();

        float move_x = -Gdx.input.getAccelerometerX(), move_y = Gdx.input.getAccelerometerY();

        if (Math.abs(move_y) > CLIMB_MIN_VAL)
            game.marioClimb( (int)(-move_y/Math.abs(move_y)) );

        if (Math.abs(move_x) > MOVE_MIN_VAL)
            game.moveMario( (int)(move_x/Math.abs(move_x)) ); // we only want either 1 or -1
        else
            game.moveMario( 0 );


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
