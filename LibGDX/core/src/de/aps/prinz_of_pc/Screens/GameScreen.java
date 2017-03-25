package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import de.aps.prinz_of_pc.PrinzGame;
import de.aps.prinz_of_pc.fonts.MyFonts;
import de.aps.prinz_of_pc.tool_methods.ToolMethods;

public class GameScreen implements Screen{

	PrinzGame game;
	MyFonts fonts;
	ShapeRenderer shapeRenderer;
	GlyphLayout layout;
	final float boxWidth = PrinzGame.WIDTH;
	final float boxHeight = 100;
	final float boxBottom = PrinzGame.HEIGHT * 0.95f;
	final float menueHeight = PrinzGame.HEIGHT - PrinzGame.HEIGHT * 0.015f;
	boolean pause = false;
	
	
	public GameScreen(PrinzGame game){
		this.game = game;
		this.fonts = new MyFonts();
		this.shapeRenderer = new ShapeRenderer();
		this.layout = new GlyphLayout();
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
		createMenue();
		game.batch.end();
		
	}
	
	private void createMenue(){
		float positionY = menueHeight;
		float gap = PrinzGame.WIDTH * 0.15f;
		
		String restart = "Neustart";
		float positionXRestart = gap;
		layout.setText(fonts.menue, restart);
		float positionSecondXRestart = positionXRestart + layout.width;
		
		String pause = "Pause";
		float positionXPause = positionXRestart + layout.width + gap;
		layout.setText(fonts.menue, pause);
		float positionSecondXPause = positionXPause + layout.width;
		
		String continues = "Fortsetzen";
		float positionXContinues = positionXPause + layout.width + gap;
		layout.setText(fonts.menue, continues);
		float positionSecondXContinues = positionXContinues + layout.width;
		
		String close = "Spiel beenden";
		float positionXClose = positionXContinues + layout.width + gap;
		layout.setText(fonts.menue, close);
		float positionSecondXClose = positionXClose + layout.width;
		
		checkIfButtonIsPressed(positionXRestart, positionSecondXRestart, positionXPause,
								positionSecondXPause, positionXContinues, positionSecondXContinues, 
								positionXClose, positionSecondXClose);
		
		drawFont(restart, positionXRestart, positionY, positionSecondXRestart);
		drawFont(pause, positionXPause, positionY, positionSecondXPause);
		drawFont(continues, positionXContinues, positionY, positionSecondXContinues);
		drawFont(close, positionXClose, positionY, positionSecondXClose);	}
	
	private void drawFont(String str, float firstX, float firstY, float secondX){
		if(checkHoverEffect(firstX, secondX)){
			fonts.menue.setColor(Color.GRAY);
			fonts.menue.draw(game.batch, str, firstX, firstY);
		} else{
			fonts.menue.draw(game.batch, str, firstX, firstY);
		}
		fonts.menue.setColor(Color.BLACK);
	}
	
	private void checkIfButtonIsPressed(float positionXRestart, float positionSecondXRestart,
										float positionXPause, float positionSecondXPause,
										float positionXContinues, float positionSecondXContinues,
										float positionXClose, float positionSecondXClose){
		float boxTop = PrinzGame.HEIGHT;
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			if(ToolMethods.checkIfMouseIsInTheArea(positionXRestart, boxBottom, positionSecondXRestart, boxTop)){
				this.dispose();
				game.setScreen(new GameScreen(game));
			} else if(ToolMethods.checkIfMouseIsInTheArea(positionXPause, boxBottom, positionSecondXPause, boxTop)){
				this.pause = true;
			} else if(ToolMethods.checkIfMouseIsInTheArea(positionXContinues, boxBottom, positionSecondXContinues, boxTop)){
				this.pause = false;
			} else if(ToolMethods.checkIfMouseIsInTheArea(positionXClose, boxBottom, positionSecondXClose, boxTop)){
				this.dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}
	
	private boolean checkHoverEffect(float firstX, float secondX){
		float boxTop = PrinzGame.HEIGHT;
		if(ToolMethods.checkIfMouseIsInTheArea(firstX, boxBottom, secondX, boxTop)){
			return true;
		}
		return false;
	}
	
	private void drawMenuBox(){
		float positionX = 0;
		float positionY = boxBottom;
		
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
