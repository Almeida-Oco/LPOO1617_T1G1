package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;

import Model.Mario;
import Model.Entity;
import View.Play;

public class MyGdxGame extends Game {
	private Model.Map game;
	private SpriteBatch batch;
	private Entity Mario;
	private Play view;

	private final float JUMP_MIN_VAL = 6f;
	private final int MOVE_MIN_VAL = 2;
	
	@Override
	public void create () {

		this.batch = new SpriteBatch();
		this.view = new Play();
		game=new Model.Map();
		this.Mario = new Mario(200,20);

		this.setScreen(this.view);
	}

	@Override
	public void render () {
		super.render();

		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		this.view.render(0);

		if ( Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) ){
			game.render();
			this.batch.begin();
			this.Mario.draw(this.batch);
			this.batch.end();
		}
		if (this.enoughToJump())
			this.Mario.moveY(30);

		int move_x = -(int)Gdx.input.getAccelerometerX() , move_y = -(int)Gdx.input.getAccelerometerY();
		if (Math.abs(move_x) > MOVE_MIN_VAL)
			this.Mario.moveX( move_x );
		if (Math.abs(move_y) > MOVE_MIN_VAL)
			this.Mario.moveY( move_y );
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


	private boolean enoughToJump(){
		float x = Gdx.input.getAccelerometerX(), y = Gdx.input.getAccelerometerY(), z = Gdx.input.getAccelerometerZ();
		return Math.sqrt( Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2) ) <= JUMP_MIN_VAL;
	}
}
