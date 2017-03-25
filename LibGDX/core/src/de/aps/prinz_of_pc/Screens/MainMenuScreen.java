package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import de.aps.prinz_of_pc.PrinzGame;
import de.aps.prinz_of_pc.fonts.MyFonts;
import de.aps.prinz_of_pc.tool_methods.ToolMethods;

public class MainMenuScreen implements Screen{

	PrinzGame game;
	MyFonts fonts;
	GlyphLayout layout;
	Texture castle;
	ShapeRenderer shapeRenderer;

	final float boxWidth = 465;
	final float boxHeight = 100;

	public MainMenuScreen(PrinzGame game) {
		this.game = game;
		this.fonts = new MyFonts();
		this.layout = new GlyphLayout();
		this.castle = new Texture("Castle.jpeg");
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		// Settings
		int half = 2;
		// Fonts
		String startGame = "Spiel starten";
		layout.setText(fonts.arial, startGame);
		float positionXStartGame = PrinzGame.WIDTH / half - layout.width / half;
		float positionYStartGame = PrinzGame.HEIGHT / half + PrinzGame.HEIGHT / 4;

		String description = "Anleitung";
		layout.setText(fonts.arial, description);
		float positionXDescription = PrinzGame.WIDTH / half - layout.width / half;
		float positionYDescription = PrinzGame.HEIGHT / half;

		String closeGame = "Spiel beenden";
		layout.setText(fonts.arial, closeGame);
		float positionXCloseGame = PrinzGame.WIDTH / half - layout.width / half;
		float positionYCloseGame = PrinzGame.HEIGHT / half - PrinzGame.HEIGHT / 4;

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		drawBoxes(positionXStartGame, positionYStartGame, positionYDescription, positionYCloseGame);

		game.batch.begin();

		//Draws buttons
		fonts.arial.setColor(Color.BLACK);
		fonts.arial.draw(game.batch, "Spiel starten", positionXStartGame, positionYStartGame);
		fonts.arial.draw(game.batch, "Anleitung", positionXDescription, positionYDescription);
		fonts.arial.draw(game.batch, "Spiel beenden", positionXCloseGame, positionYCloseGame);
		game.batch.end();

	}
	/**
	 * Draws all Boxes 
	 * @param positionXStartGame = Biggest font
	 * @param positionYStartGame = First menu button
	 * @param positionYDescription = Second menu button
	 * @param positionYCloseGame = Third menu button
	 */
	private void drawBoxes(float positionXStartGame, float positionYStartGame, float positionYDescription, float positionYCloseGame){
		float boxGapX = PrinzGame.WIDTH * 0.05f;
		float boxGapY = PrinzGame.HEIGHT * 0.1f;
		float positionXBox = positionXStartGame - boxGapX;
		float positionYFirstBox = positionYStartGame - boxGapY;
		float positionYSecondBox = positionYDescription - boxGapY;
		float positionYThirdBox = positionYCloseGame - boxGapY;

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(positionXBox, positionYFirstBox, boxWidth, boxHeight);
		shapeRenderer.rect(positionXBox, positionYSecondBox, boxWidth, boxHeight);
		shapeRenderer.rect(positionXBox, positionYThirdBox, boxWidth, boxHeight);
		shapeRenderer.end();

		checkIfOneButtonIsClicked(positionXBox, positionYFirstBox, positionYSecondBox, positionYThirdBox);
	}

	private void checkIfOneButtonIsClicked(float positionX, float positionYFirstBox, float positionYSecondBox, float positionYThirdBox){
		float positionSecondX = positionX + boxWidth;
		float positionSecondYFirstBox = positionYFirstBox + boxHeight;
		float positionSecondYSecondBox = positionYSecondBox + boxHeight;
		float positionSecondYThirdBox = positionYThirdBox + boxHeight;

		if(Gdx.input.isButtonPressed(Buttons.LEFT) && ToolMethods.checkIfMouseIsInTheArea(positionX, positionYFirstBox, positionSecondX, positionSecondYFirstBox)){
			this.dispose();
			game.setScreen(new GameScreen(game));
		} 
		else if(Gdx.input.isButtonPressed(Buttons.LEFT) && ToolMethods.checkIfMouseIsInTheArea(positionX, positionYSecondBox, positionSecondX, positionSecondYSecondBox)){
			this.dispose();
			game.setScreen(new DescriptionScreen(game));
		} else if(Gdx.input.isButtonPressed(Buttons.LEFT) && ToolMethods.checkIfMouseIsInTheArea(positionX, positionYThirdBox, positionSecondX, positionSecondYThirdBox)){
			Gdx.app.exit();
		}
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