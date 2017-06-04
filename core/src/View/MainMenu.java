package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.ScreenAdapter;



public class MainMenu extends PlayScreen {


        private Texture background;

        private Button startButton;
        private Button exitButton;

        private Sprite startSprite;
        private Sprite exitSprite;
        private SpriteBatch batch;
        private Stage stage;




    public void show() {

        stage = new Stage();
        this.change=false;
        this.batch = new SpriteBatch();
        background = new Texture("initial_menu.png");
        startSprite = new Sprite(new Texture("play.png"));
        exitSprite = new Sprite(new Texture("exit.png"));
        startButton = new Button(new SpriteDrawable(startSprite));
        startButton.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() * 1 / 7);
        startButton.setX(Gdx.graphics.getWidth() * 1/2 - startButton.getWidth() / 2);
        startButton.setY(Gdx.graphics.getHeight() *3/8);

        exitButton = new Button(new SpriteDrawable(exitSprite));
        exitButton.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() * 1 / 7);
        exitButton.setX(Gdx.graphics.getWidth() * 1 / 2 - exitButton.getWidth() / 2);
        exitButton.setY(Gdx.graphics.getHeight() * 1/8);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(startButton);
        stage.addActor(exitButton);
    }
        public void handleInput( float delta ) {
       //     System.out.println("adeus");

            if(startButton.isPressed())
                this.change=true;

            if(exitButton.isPressed())
                Gdx.app.exit();



        }




        @Override
        public void render(float delta) {
            this.batch.begin();
            this.batch.draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            exitButton.draw(this.batch, 1);
            startButton.draw(this.batch, 1);
            //  name.draw(this.batch,1);
            this.batch.end();
            handleInput(delta);
        }

        @Override
        public void dispose() {
            background.dispose();
            startSprite.getTexture().dispose();
            exitSprite.getTexture().dispose();
        }

    @Override
    public ScreenAdapter renderAndUpdate(float delta) {
        this.render(delta);
        if(change)
            return new Play();
        else
            return this;
    }
}

