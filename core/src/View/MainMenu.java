package View;

import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by asus on 02/06/2017.
 */



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


public class MainMenu extends State {


        private Texture background;

        private Button startButton;
        private Button exitButton;

        private Sprite startSprite;
        private Sprite exitSprite;

        private TextField name;
        private Skin skin;

        public MainMenu(StateManager sm) {
            this.sm =sm;
            background = new Texture("initial_menu.png");


            startSprite = new Sprite(new Texture("play.png"));
            exitSprite = new Sprite(new Texture("exit.png"));
            startButton = new Button(new SpriteDrawable(startSprite));
            startButton.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*2/7);
            startButton.setX(Gdx.graphics.getWidth()*3/4-startButton.getWidth()/2);
            startButton.setY(Gdx.graphics.getHeight()/2);

            exitButton = new Button(new SpriteDrawable(exitSprite));
            exitButton.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*2/7);
            exitButton.setX(Gdx.graphics.getWidth()*3/4-exitButton.getWidth()/2);
            exitButton.setY(Gdx.graphics.getHeight()/12);




        }


        public void handleInput( float delta ) {


        }



        @Override
        public void render(float delta) {
            this.batch.begin();
            this.batch.draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            exitButton.draw(this.batch, 1);
            startButton.draw(this.batch, 1);
            name.draw(this.batch,1);
            this.batch.end();
        }

        @Override
        public void dispose() {
            background.dispose();
            startSprite.getTexture().dispose();
            exitSprite.getTexture().dispose();
        }
    }

