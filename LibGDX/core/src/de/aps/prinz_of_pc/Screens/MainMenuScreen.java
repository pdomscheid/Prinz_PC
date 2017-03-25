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

/**
 * Startbildschirm Programm
 * @author patrickdomscheid
 *
 */
public class MainMenuScreen extends Games implements Screen{

	GlyphLayout layout;
	Texture background;
	ShapeRenderer shapeRenderer;

	final float boxWidth = 465;
	final float boxHeight = 100;

	public MainMenuScreen(PrinzGame game) {
		super(game);
		this.game = game;
		this.fonts = new MyFonts();
		this.layout = new GlyphLayout();
		this.background = new Texture("MenueScreen.jpeg");
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		// Settings
		float boxGapY = PrinzGame.HEIGHT * 0.1f;
		int half = 2;
		// Set the Positions of the Fonts
		String startGame = "Spiel starten";
		layout.setText(fonts.arial, startGame);
		float positionXStartGame = PrinzGame.WIDTH / half - layout.width / half;
		float positionYStartGame = PrinzGame.HEIGHT / half + PrinzGame.HEIGHT / 4;
		float positionXSecondStartGame = positionXStartGame + layout.width;
		float positionYBoxStartGame = positionYStartGame - boxGapY;
		float positionSecondYBoxStartGame = positionYBoxStartGame + boxHeight;
	
		String description = "Anleitung";
		layout.setText(fonts.arial, description);
		float positionXDescription = PrinzGame.WIDTH / half - layout.width / half;
		float positionYDescription = PrinzGame.HEIGHT / half;
		float positionXSecondDescription = positionXDescription + layout.width;
		float positionYBoxDescription = positionYDescription - boxGapY;
		float positionSecondYBoxDescription = positionYBoxDescription + boxHeight;

		String closeGame = "Spiel beenden";
		layout.setText(fonts.arial, closeGame);
		float positionXCloseGame = PrinzGame.WIDTH / half - layout.width / half;
		float positionYCloseGame = PrinzGame.HEIGHT / half - PrinzGame.HEIGHT / 4;
		float positionXSecondCloseGame = positionXCloseGame + layout.width;
		float positionYBoxCloseGame = positionYCloseGame - boxGapY;
		float positionSecondYBoxCloseGame = positionYBoxCloseGame + boxHeight;

		// Here begins the drawing od the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(background, 0, 10);
		game.batch.end();
		
		drawBoxes(positionXStartGame, positionYBoxStartGame, positionYBoxDescription, positionYBoxCloseGame);
		
		//Draws buttons
		game.batch.begin();
		drawFont(startGame, positionXStartGame, positionXSecondStartGame, positionYStartGame, positionYBoxStartGame, positionSecondYBoxStartGame, fonts.arial);
		drawFont(description, positionXDescription, positionXSecondDescription, positionYDescription, positionYBoxDescription, positionSecondYBoxDescription, fonts.arial);
		drawFont(closeGame, positionXCloseGame, positionXSecondCloseGame, positionYCloseGame, positionYBoxCloseGame, positionSecondYBoxCloseGame, fonts.arial);
		game.batch.end();

	}
	/**
	 * Draws all Boxes 
	 * @param positionXStartGame = Biggest font
	 * @param positionYStartGame = First menu button
	 * @param positionYDescription = Second menu button
	 * @param positionYCloseGame = Third menu button
	 */
	private void drawBoxes(float positionXStartGame, float positionYFirstBox, float positionYSecondBox, float positionYThirdBox){
		float boxGapX = PrinzGame.WIDTH * 0.05f;
		float positionXBox = positionXStartGame - boxGapX;
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.DARK_GRAY);
		shapeRenderer.rect(positionXBox, positionYFirstBox, boxWidth, boxHeight);
		shapeRenderer.rect(positionXBox, positionYSecondBox, boxWidth, boxHeight);
		shapeRenderer.rect(positionXBox, positionYThirdBox, boxWidth, boxHeight);
		shapeRenderer.end();
		
		checkIfOneButtonIsClicked(positionXBox, positionYFirstBox, positionYSecondBox, positionYThirdBox);

	}

	/**
	 * If one button is clicked, the method will react 
	 * @param positionX
	 * @param positionYFirstBox
	 * @param positionYSecondBox
	 * @param positionYThirdBox
	 */
	private void checkIfOneButtonIsClicked(float positionX, float positionYFirstBox, float positionYSecondBox, float positionYThirdBox){
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			float positionSecondX = positionX + boxWidth;
			float positionSecondYFirstBox = positionYFirstBox + boxHeight;
			float positionSecondYSecondBox = positionYSecondBox + boxHeight;
			float positionSecondYThirdBox = positionYThirdBox + boxHeight;

			if(ToolMethods.checkIfMouseIsInTheArea(positionX, positionYFirstBox, positionSecondX, positionSecondYFirstBox)){
				this.dispose();
				game.setScreen(new GameScreen(game));
			} 
			else if(ToolMethods.checkIfMouseIsInTheArea(positionX, positionYSecondBox, positionSecondX, positionSecondYSecondBox)){
				this.dispose();
				game.setScreen(new DescriptionScreen(game));
			} else if(ToolMethods.checkIfMouseIsInTheArea(positionX, positionYThirdBox, positionSecondX, positionSecondYThirdBox)){
				Gdx.app.exit();
			}
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
		super.dispose();
		shapeRenderer.dispose();
		background.dispose();
	}

}
