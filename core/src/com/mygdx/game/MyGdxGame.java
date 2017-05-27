package com.mygdx.game;

import com.badlogic.gdx.Game;

import View.Play;

public class MyGdxGame extends Game {
	@Override
	public void create () {
		this.setScreen( new Play() );
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
