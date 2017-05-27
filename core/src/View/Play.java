package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Play implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;


    private final float JUMP_MIN_VAL = 6f;
    private final int MOVE_MIN_VAL = 2;

    @Override
    public void show() {
        this.map = (new TmxMapLoader()).load(Gdx.files.internal("maps/DKMap.tmx").path());
        //TODO make scaling based on monitor size

        this.renderer = new OrthogonalTiledMapRenderer(map, 2.4f);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.renderer.setView(this.camera);
        this.renderer.render();



        super.render();

        Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        this.view.render(0);

        if ( Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) ){
            game.render();
            this.batch.begin();
            this.Mario.draw(this.batch);
            this.batch.end();
        }
        if (this.enoughToJump())
            this.Mario.moveY(30);

        int move_x = -(int)Gdx.input.getAccelerometerX() , move_y = -(int)Gdx.input.getAccelerometerY();
        if (Math.abs(move_x) > MOVE_MIN_VAL)
            this.Mario.moveX( move_x );
        if (Math.abs(move_y) > MOVE_MIN_VAL)
            this.Mario.moveY( move_y );

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
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    private boolean enoughToJump(){
        float x = Gdx.input.getAccelerometerX(), y = Gdx.input.getAccelerometerY(), z = Gdx.input.getAccelerometerZ();
        return Math.sqrt( Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2) ) <= JUMP_MIN_VAL;
    }
}
