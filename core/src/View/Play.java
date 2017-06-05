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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import Controller.GameLogic;
import Model.Entity;
import Model.Pair;
import View.Entity.ElementView;
import View.Entity.ViewFactory;

public class Play extends PlayScreen {
    private ScoreTimer score;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private AssetManager assets = null;
    private float scale;
    private Entity.type barrel_fire = Entity.type.BARREL_FIRE_MIN1;
    private int tick = 0;
    protected SpriteBatch batch;
    protected TextField name;

    private final int JUMP = 2;
    private final float JUMP_MIN_VAL = 6f;
    private final float MOVE_MIN_VAL = 1.5f;
    private final float CLIMB_MIN_VAL = 1.5f;
    private final float REAL_GRAVITY = 9.8f;
    private final String MAP_1 = "DKMap.tmx";
    private final String COLLISION = "Floor";
    private final int FPS = 60;
    private final int ANIMATION_RATE = 20;


    private long diff, start = System.currentTimeMillis();

    public Play(ScreenAdapter next_screen) {
        super(next_screen);
    }


    public void sleep(int fps) {
        if (fps > 0) {
            diff = System.currentTimeMillis() - start;
            long targetDelay = 1000 / fps;
            if (diff < targetDelay) {
                try {
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException e) {
                }
            }
            start = System.currentTimeMillis();
        }
    }

    public void show() {
        this.change = false;
        this.batch = new SpriteBatch();
        this.score = new ScoreTimer(batch);
        GameLogic.getInstance().setMap(MAP_1, COLLISION);
        this.map = GameLogic.getInstance().getMap().getMap();
        this.scale = this.mapScaling();
        this.renderer = new OrthogonalTiledMapRenderer(this.map, this.scale);
        GameLogic.getInstance().getMap().setScale(this.scale);
        GameLogic.getInstance().initializeCharacters();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.renderer.setView(this.camera);
        if (assets == null){
            this.assets = new AssetManager();
            this.loadAssets();
        }

    }

    private void loadAssets() {
        this.loadMarioAsssets();
        this.loadBarrelsAssets();
        this.loadDKAssets();
        this.loadFireAssets();
        this.assets.finishLoading();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.renderer.render();

        this.drawEntities();
        this.animateBackground();

        this.handleInput(delta);

        batch.setProjectionMatrix(score.stage.getCamera().combined);
        score.stage.draw();
        if (GameLogic.getInstance().isDead())
            change = true;
        this.sleep(FPS);
    }

    private void drawEntities() {
        for (Entity ent : GameLogic.getInstance().getCharacters()) {
            ElementView ent_view = ViewFactory.makeView(this.assets, ent.getType(), this.scale);
            ent.setRepSize(ent_view.getImgWidth(), ent_view.getImgHeight(), this.scale);
            ent_view.changeSprite(ent.getType());
            ent_view.updatePos(ent.getX(), ent.getY());
            this.batch.begin();
            ent_view.draw(this.batch);
            this.batch.end();
        }
    }

    protected void handleInput(float delta) {
        GameLogic game = GameLogic.getInstance();
        int x_move = 0, y_move = 0;
        float acc_x = -Gdx.input.getAccelerometerX(), acc_y = -Gdx.input.getAccelerometerY();
        float jump_offset = 0f;
        if (this.enoughToJump())
            jump_offset = 0.75f;

        if (Math.abs(acc_y) > CLIMB_MIN_VAL)
            y_move = (int) (acc_y / Math.abs(acc_y));
        if (Math.abs(acc_x) > (MOVE_MIN_VAL - jump_offset))
            x_move = (int) (acc_x / Math.abs(acc_x));
        if (jump_offset != 0)
            y_move = JUMP;


        game.moveMario(x_move, y_move);
        game.moveBarrels();
        game.updateDK(delta);
        game.moveFires();
        score.update(delta);
    }

    private boolean enoughToJump() {
        float x = Gdx.input.getAccelerometerX(), y = Gdx.input.getAccelerometerY(), z = Gdx.input.getAccelerometerZ();
        return (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= JUMP_MIN_VAL && x < REAL_GRAVITY && y < REAL_GRAVITY && z < REAL_GRAVITY);
    }


    private void animateBackground() {
        if (this.tick == ANIMATION_RATE)
            this.tick = 0;

        if (GameLogic.getInstance().firstBarrelFalled())
            this.drawBarrelFire();

        this.tick++;
    }

    private void drawBarrelFire() {
        this.updateBarrelFireState();
        ElementView el_view = ViewFactory.makeView(this.assets, this.barrel_fire, this.scale);
        el_view.changeSprite(this.barrel_fire);
        Pair<Integer, Integer> map_pos = new Pair<Integer, Integer>(1, 24);
        map_pos = GameLogic.getInstance().getMap().mapPosToPixels(map_pos);
        el_view.updatePos(map_pos.getFirst(), map_pos.getSecond());
        this.batch.begin();
        el_view.draw(this.batch);
        this.batch.end();
    }

    private void updateBarrelFireState() {
        if (this.tick == 0) {
            if (Entity.type.BARREL_FIRE_MIN1 == this.barrel_fire)
                this.barrel_fire = Entity.type.BARREL_FIRE_MIN2;
            else if (Entity.type.BARREL_FIRE_MIN2 == this.barrel_fire)
                this.barrel_fire = Entity.type.BARREL_FIRE_MAX1;
            else if (Entity.type.BARREL_FIRE_MAX1 == this.barrel_fire)
                this.barrel_fire = Entity.type.BARREL_FIRE_MAX2;
            else if (Entity.type.BARREL_FIRE_MAX2 == this.barrel_fire)
                this.barrel_fire = Entity.type.BARREL_FIRE_MIN1;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
        this.score.dispose();
    }

    private float mapScaling() {
        return Gdx.graphics.getWidth() / (((TiledMapTileLayer) this.map.getLayers().get("Floor")).getWidth() * ((TiledMapTileLayer) this.map.getLayers().get("Floor")).getTileWidth());
    }


    private void loadMarioAsssets() {
        this.assets.load("mario/left.png", Texture.class);
        this.assets.load("mario/right.png", Texture.class);
        this.assets.load("mario/run_left.png", Texture.class);
        this.assets.load("mario/run_right.png", Texture.class);
        this.assets.load("mario/climb_left.png", Texture.class);
        this.assets.load("mario/climb_right.png", Texture.class);
        this.assets.load("mario/climb_over.png", Texture.class);
        this.assets.load("mario/dying_up.png", Texture.class);
        this.assets.load("mario/dying_down.png", Texture.class);
        this.assets.load("mario/dying_left.png", Texture.class);
        this.assets.load("mario/dying_right.png", Texture.class);
        this.assets.load("mario/died.png", Texture.class);
    }

    private void loadBarrelsAssets() {
        this.assets.load("barrels/rolling1.png", Texture.class);
        this.assets.load("barrels/rolling2.png", Texture.class);
        this.assets.load("barrels/rolling3.png", Texture.class);
        this.assets.load("barrels/rolling4.png", Texture.class);
        this.assets.load("barrels/falling_back.png", Texture.class);
        this.assets.load("barrels/falling_front.png", Texture.class);
        this.assets.load("barrels/fire_falling_back.png", Texture.class);
        this.assets.load("barrels/fire_falling_front.png", Texture.class);
        this.assets.load("barrels/fire_rolling.png", Texture.class);
        this.assets.load("fire_barrel/min1.png", Texture.class);
        this.assets.load("fire_barrel/min2.png", Texture.class);
        this.assets.load("fire_barrel/max1.png", Texture.class);
        this.assets.load("fire_barrel/max2.png", Texture.class);
    }

    private void loadDKAssets() {
        this.assets.load("dk/front.png", Texture.class);
        this.assets.load("dk/left_barrel.png", Texture.class);
        this.assets.load("dk/right_hand.png", Texture.class);
        this.assets.load("dk/throw_right.png", Texture.class);
        this.assets.load("dk/throw_left.png", Texture.class);
        this.assets.load("dk/right_barrel.png", Texture.class);
        this.assets.load("dk/right_hand.png", Texture.class);
        this.assets.load("dk/left_hand.png", Texture.class);
    }

    private void loadFireAssets() {
        this.assets.load("fire/left.png", Texture.class);
        this.assets.load("fire/right.png", Texture.class);
        this.assets.load("fire/left_ignite.png", Texture.class);
        this.assets.load("fire/right_ignite.png", Texture.class);
    }

    @Override
    public ScreenAdapter renderAndUpdate(float delta) {
        this.render(delta);
        if ( this.change ) {
            this.change = false;
            return this.next_screen;
        }

        return this;
    }
}
