package View;

/**
 * Created by asus on 02/06/2017.
 */

import java.util.Stack;

public class StateManager {

    private Stack<State> states;

    public StateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }



    public void render(float delta){
        states.peek().render(delta);
    }
}