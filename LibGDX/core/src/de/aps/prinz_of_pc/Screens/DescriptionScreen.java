package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import de.aps.prinz_of_pc.PrinzGame;
import de.aps.prinz_of_pc.fonts.MyFonts;
import de.aps.prinz_of_pc.tool_methods.ToolMethods;

public class DescriptionScreen implements Screen{

	PrinzGame game;
	MyFonts fonts;
	GlyphLayout layout;
	
	public DescriptionScreen(PrinzGame game) {
		this.game = game;
		this.fonts = new MyFonts();
		this.layout = new GlyphLayout();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		String back = "Zurueck";
		layout.setText(fonts.arial, back);
		float positionXBack = PrinzGame.WIDTH * 0.025f;
		float positionYBack = PrinzGame.HEIGHT * 0.9f;
		float positionSecondXBack = positionXBack + layout.width;
		float positionSecondYBack = positionYBack + layout.height;
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		fonts.arial.draw(game.batch, back, positionXBack, positionYBack);
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && ToolMethods.checkIfMouseIsInTheArea(positionXBack, positionYBack, positionSecondXBack, positionSecondYBack, fonts.arial, back)){
			this.dispose();
			game.setScreen(new MainMenuScreen(game));
		}
		game.batch.end();
		
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
