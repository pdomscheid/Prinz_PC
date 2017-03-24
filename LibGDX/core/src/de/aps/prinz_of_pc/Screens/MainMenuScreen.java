package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import de.aps.prinz_of_pc.PrinzGame;
import de.aps.prinz_of_pc.fonts.MyFonts;

public class MainMenuScreen implements Screen{

	PrinzGame game;
	MyFonts fonts;
	GlyphLayout layout;
	
	public MainMenuScreen(PrinzGame game) {
		this.game = game;
		fonts = new MyFonts();
		layout = new GlyphLayout();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Settings
		String startGame = "Spiel starten";
		layout.setText(fonts.arial, startGame);
		float positionXStartGame = PrinzGame.WIDTH / 2 - layout.width / 2;
		float positionYStartGame = PrinzGame.HEIGHT / 2 + PrinzGame.HEIGHT / 4;
		float positionSecondXStartGame = positionXStartGame + layout.width;
		float positionSecondYStartGame = positionYStartGame + layout.height;
		
		String description = "Anleitung";
		layout.setText(fonts.arial, description);
		float positionXDescription = PrinzGame.WIDTH / 2 - layout.width / 2;
		float positionYDescription = PrinzGame.HEIGHT / 2;
		float positionSecondXDescription = positionXDescription + layout.width;;
		float positionSecondYDescription = positionYDescription + layout.height;
		
		String closeGame = "Spiel beenden";
		layout.setText(fonts.arial, closeGame);
		float positionXCloseGame = PrinzGame.WIDTH / 2 - layout.width / 2;
		float positionYCloseGame = PrinzGame.HEIGHT / 2 - PrinzGame.HEIGHT / 4;
		float positionSecondXCloseGame = positionXCloseGame + layout.width;;
		float positionSecondYCloseGame = positionYCloseGame + layout.height;

		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		//Draws buttons
		fonts.arial.setColor(Color.BLACK);
		fonts.arial.draw(game.batch, "Start Game", positionXStartGame, positionYStartGame);
		fonts.arial.draw(game.batch, "Anleitung", positionXDescription, positionYDescription);
		fonts.arial.draw(game.batch, "Spiel beenden", positionXCloseGame, positionYCloseGame);
		
		// check if one button is clicked
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfMouseIsInTheArea(positionXStartGame, positionYStartGame, positionSecondXStartGame, positionSecondYStartGame, fonts.arial, startGame)){
			this.dispose();
			game.setScreen(new GameScreen(game));
		} 
		else if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfMouseIsInTheArea(positionXDescription, positionYDescription, positionSecondXDescription, positionSecondYDescription, fonts.arial, description)){
			this.dispose();
			game.setScreen(new DescriptionScreen(game));
		} else if(Gdx.input.isButtonPressed(Buttons.LEFT) && checkIfMouseIsInTheArea(positionXCloseGame, positionYCloseGame, positionSecondXCloseGame, positionSecondYCloseGame, fonts.arial, closeGame)){
			Gdx.app.exit();
		}
		
		game.batch.end();
		
	}
	/**
	 * Checks if the Mouse is between the parameters
	 * @param firstX = Button down-left side
	 * @param firstY = Button top-left side
	 * @param secondX = Button down-right side
	 * @param secondY = Button top-right side
	 * @param font = Font of the button
	 * @param str = Text of the button
	 * @return
	 */
	private boolean checkIfMouseIsInTheArea(float firstX, float firstY, float secondX, float secondY, BitmapFont font, String str){
		// correct the button area
		layout.setText(font, str);
		firstY -= layout.height;
		secondY -= layout.height;
		
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
