package View;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by asus on 02/06/2017.
 */

public abstract class State extends ScreenAdapter {
    protected boolean advanceState;
    protected static State current_state;
    protected SpriteBatch batch;

    public static State getInstance(){
        if(current_state==null){
            current_state= new MainMenu();
            return current_state;
        }
        return current_state;
    }
    public boolean getAdvanceState(){
        return this.advanceState;
    }
    public void setAdvanceState(boolean advanceState){
       this.advanceState=advanceState;
    }
    public abstract void render(float delta);
    protected abstract void handleInput(float delta);
    public abstract void dispose();

}
