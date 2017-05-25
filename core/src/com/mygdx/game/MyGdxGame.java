package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;

import View.Play;

public class MyGdxGame extends ApplicationAdapter {
	private Model.Map game;
	
	@Override
	public void create () {
		game=new Model.Map();
	}

	@Override
	public void render () {
		game.render();
	}
	
	@Override
	public void dispose () {
		game.dispose();
	}
	@Override
	public void resize(int width, int height){
		game.resize(width,height);
	}

	@Override
	public void pause() {
		game.pause();
	}

	@Override
	public void resume() {
		game.resume();
	}
}
