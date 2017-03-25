package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import de.aps.prinz_of_pc.PrinzGame;
import de.aps.prinz_of_pc.fonts.MyFonts;

public class GameScreen implements Screen{

	PrinzGame game;
	MyFonts fonts;
	ShapeRenderer shapeRenderer;
	final float boxWidth = PrinzGame.WIDTH;
	final float boxHeight = 100;
	
	public GameScreen(PrinzGame game){
		this.game = game;
		this.fonts = new MyFonts();
		this.shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		drawMenuBox();
		
		game.batch.begin();
		game.batch.end();
		
	}
	
	private void drawMenuBox(){
		float positionX = 0;
		float positionY = PrinzGame.HEIGHT * 0.95f;
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(positionX, positionY, boxWidth, boxHeight);
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
