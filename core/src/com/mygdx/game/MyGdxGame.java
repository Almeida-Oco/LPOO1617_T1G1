package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

import View.MainMenu;
import View.Play;
import View.PlayScreen;

public class MyGdxGame extends Game {
	public static final float DEFAULT_SCALE = 2.4107144f;
    private ScreenAdapter curr_state;
    private long prev_time ;

	@Override
	public void create () {
        prev_time =0;
        PlayScreen temp = new Play(null);
        this.curr_state = new MainMenu( temp );
        temp.setNextScreen( this.curr_state  );
		this.setScreen ( curr_state );
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
