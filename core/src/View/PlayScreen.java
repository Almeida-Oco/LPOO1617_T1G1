package View;

import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by asus on 02/06/2017.
 */

public abstract class PlayScreen extends ScreenAdapter {
    protected boolean change;
    public abstract ScreenAdapter renderAndUpdate(float delta);

}
