package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import de.aps.prinz_of_pc.PrinzGame;

public class DescriptionScreen implements Screen{

	PrinzGame game;
	Texture back;
	
	public DescriptionScreen(PrinzGame game) {
		this.game = game;
		this.back = new Texture("Zurueck.png");
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float positionXBack = PrinzGame.WIDTH * 0.025f;
		float positionYBack = PrinzGame.HEIGHT * 0.9f;
		float positionSecondXBack = positionXBack + back.getWidth();
		float positionSecondYBack = positionYBack + back.getHeight();
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(back, positionXBack, positionYBack);
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfButtonIsPressed(positionXBack, positionYBack, positionSecondXBack, positionSecondYBack)){
			this.dispose();
			game.setScreen(new MainMenuScreen(game));
		}
		game.batch.end();
		
	}
	
	private boolean checkIfButtonIsPressed(float firstX, float firstY, float secondX, float secondY){
		if(Gdx.input.getX() > firstX && Gdx.input.getX() < secondX && PrinzGame.HEIGHT - Gdx.input.getY() > firstY && PrinzGame.HEIGHT - Gdx.input.getY() < secondY){
			return true;
		}
		return false;
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
