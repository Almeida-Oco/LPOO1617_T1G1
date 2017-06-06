package View;

/**
 * Created by asus on 03/06/2017.
 */


        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.OrthographicCamera;
        import com.badlogic.gdx.graphics.g2d.BitmapFont;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Label;
        import com.badlogic.gdx.scenes.scene2d.ui.Table;
        import com.badlogic.gdx.utils.Disposable;
        import com.badlogic.gdx.utils.viewport.FitViewport;
        import com.badlogic.gdx.utils.viewport.Viewport;



public class ScoreTimer implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;
    private static Integer life_number;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label marioLabel;
    private Label life;
    private static Label lifesLabel;


    public ScoreTimer(SpriteBatch sb){
        //define our tracking variables
        worldTimer = 500;
        timeCount = 0;
        score = 0;
        life_number=3;
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        initLabels();
        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(marioLabel).expandX().padTop(10);
        table.add(life).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(lifesLabel).expandX();
        table.add(countdownLabel).expandX();
        //add our table to the stage
        stage.addActor(table);


    }


    private void initLabels(){
        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifesLabel =new Label(String.format("%d",life_number),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        life= new Label("LIFES",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        marioLabel.setFontScale(4);
        timeLabel.setFontScale(4);
        scoreLabel.setFontScale(4);
        countdownLabel.setFontScale(4);
        life.setFontScale(4);
        lifesLabel.setFontScale(4);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void setLifes(int lifes){
        life_number=lifes;
        lifesLabel.setText(String.format("%d", life_number));
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}