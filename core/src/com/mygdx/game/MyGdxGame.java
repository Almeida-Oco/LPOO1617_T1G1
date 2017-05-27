package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;

import Model.Mario;
import Model.Entity;
import View.Play;

public class MyGdxGame extends Game {
	//private Model.Map game;
	private SpriteBatch batch;
	private AssetManager assets;
	//private Entity Mario;
	//private Play view;

	private final String MARIO_IMG = "badlogic.jpg";

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.view = new Play();
		/*
		game=new Model.Map();
		this.Mario = new Mario(200,20);
		*/
		this.setScreen( new Play() );
	}

	@Override
	public void dispose () {
		this.batch.dispose();
		this.game.dispose();
	}

	public AssetManager getAssets(){
		return this.assets;
	}

	public SpriteBatch getBatch(){
		return this.batch;
	}

	private void loadAssets(){
		this.assets.load(MARIO_IMG, Texture.class );
	}

}
