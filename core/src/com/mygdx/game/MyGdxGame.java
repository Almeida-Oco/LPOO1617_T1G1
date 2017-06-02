package com.mygdx.game;

import com.badlogic.gdx.Game;

import View.Play;

//TODO check if there is a way to save DEFAULT_SCALE differently
public class MyGdxGame extends Game {
	public static final float DEFAULT_SCALE = 2.4107144f;

	@Override
	public void create () {
		this.setScreen( new Play() );
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
