package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

import View.MainMenu;
import View.PlayScreen;

//TODO check if there is a way to save DEFAULT_SCALE differently
public class MyGdxGame extends Game {
	public static final float DEFAULT_SCALE = 2.4107144f;
    private ScreenAdapter curr_state;
    private long prev_time ;

	@Override
	public void create () {
        prev_time =0;
        this.curr_state=new MainMenu();
		this.setScreen (curr_state);
	}

	@Override
	public void dispose () {
		super.dispose();
	}


	public void render(){
        long curr_time= System.currentTimeMillis();
        long delta=curr_time -prev_time;
        prev_time=curr_time;
        ScreenAdapter temp= ((PlayScreen)curr_state).renderAndUpdate((float)delta/1000);
        if (temp!=curr_state){
            this.curr_state=temp;
            this.setScreen(curr_state);
        }
    }



}
