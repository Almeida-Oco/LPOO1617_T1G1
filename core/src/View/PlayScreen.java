package View;

import com.badlogic.gdx.ScreenAdapter;


public abstract class PlayScreen extends ScreenAdapter {
    protected boolean change;
    protected ScreenAdapter next_screen;

    /**
     * Constructor for PlayScreen
     * @param next_screen Next screen to use
     */
    public PlayScreen( ScreenAdapter next_screen ){
        this.next_screen = next_screen;
    }

    /**
     * Used to render and update the current Screen
     * @param delta How much time has passed since last time this function was called
     * @return The screen to set
     */
    public abstract ScreenAdapter renderAndUpdate(float delta);

    /**
     * Sets the next screen
     * @param next Screen to use
     */
    public void setNextScreen( ScreenAdapter next ){
        this.next_screen = next;
    }



}
