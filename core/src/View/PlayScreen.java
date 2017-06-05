package View;

import com.badlogic.gdx.ScreenAdapter;


public abstract class PlayScreen extends ScreenAdapter {
    protected boolean change;
    protected ScreenAdapter prev_state;
    public abstract ScreenAdapter renderAndUpdate(float delta);



}
