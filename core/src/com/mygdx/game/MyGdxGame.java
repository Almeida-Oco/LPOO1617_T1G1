package com.mygdx.game;

import com.badlogic.gdx.Game;

import View.MainMenu;
import View.Play;
import View.State;

//TODO check if there is a way to save DEFAULT_SCALE differently
public class MyGdxGame extends Game {
	public static final float DEFAULT_SCALE = 2.4107144f;

	@Override
	public void create () {
		this.setScreen(new Play() );
	}

	@Override
	public void dispose () {
		super.dispose();
	}


	public void render(){
		System.out.println("adeus");
		//if(State.getInstance().getAdvanceState()){
		//	this.setScreen(State.getInstance() );
		//	State.getInstance().setAdvanceState(false);
		//}
		//State.getInstance().render(delta);
	}
}
