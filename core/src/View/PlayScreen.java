package View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;


public abstract class PlayScreen extends ScreenAdapter {
    protected boolean change;
    protected ScreenAdapter next_screen;

    public PlayScreen( ScreenAdapter next_screen ){
        this.next_screen = next_screen;
    }
    public abstract ScreenAdapter renderAndUpdate(float delta);

    public void setNextScreen( ScreenAdapter next ){
        this.next_screen = next;
    }



}
