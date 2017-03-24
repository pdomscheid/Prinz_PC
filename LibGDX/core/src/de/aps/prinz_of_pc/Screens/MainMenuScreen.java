package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import de.aps.prinz_of_pc.PrinzGame;

public class MainMenuScreen implements Screen{

	PrinzGame game;
	Texture startGame;
	Texture description;
	Texture closeGame;
	
	public MainMenuScreen(PrinzGame game) {
		this.game = game;
		this.startGame = new Texture("StartGame.png");
		this.description = new Texture("Anleitung.png");
		this.closeGame = new Texture("SpielBeenden.png");
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		float positionXStartGame = PrinzGame.WIDTH / 2 - startGame.getWidth() / 2;
		float positionYStartGame = PrinzGame.HEIGHT / 2 + PrinzGame.HEIGHT / 4;
		float positionSecondXStartGame = positionXStartGame + startGame.getWidth();
		float positionSecondYStartGame = positionYStartGame + startGame.getHeight();
		
		float positionXDescription = PrinzGame.WIDTH / 2 - description.getWidth() / 2;
		float positionYDescription = PrinzGame.HEIGHT / 2;
		float positionSecondXDescription = positionXDescription + description.getWidth();
		float positionSecondYDescription = positionYDescription + description.getHeight();
		
		float positionXCloseGame = PrinzGame.WIDTH / 2 - closeGame.getWidth() / 2;
		float positionYCloseGame = PrinzGame.HEIGHT / 2 - PrinzGame.HEIGHT / 4;
		float positionSecondXCloseGame = positionXCloseGame + closeGame.getWidth();
		float positionSecondYCloseGame = positionYCloseGame + closeGame.getHeight();

		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(startGame, positionXStartGame, positionYStartGame);
		game.batch.draw(description, positionXDescription, positionYDescription);
		game.batch.draw(closeGame, positionXCloseGame, positionYCloseGame);
		// check if "Spiel starten" is clicked
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfButtonIsPressed(positionXStartGame, positionYStartGame, positionSecondXStartGame, positionSecondYStartGame)){
			game.setScreen(new GameScreen(game));
		} else if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfButtonIsPressed(positionXDescription, positionYDescription, positionSecondXDescription, positionSecondYDescription)){
			game.batch.draw(closeGame, 100, 100);
		} else if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfButtonIsPressed(positionXCloseGame, positionYCloseGame, positionSecondXCloseGame, positionSecondYCloseGame)){
			Gdx.app.exit();
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
