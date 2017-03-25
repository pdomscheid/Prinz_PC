package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
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
		
		String menu = "Menue";
		layout.setText(fonts.arial, menu);
		float positionXBack = PrinzGame.WIDTH * 0.025f;
		float positionYBack = PrinzGame.HEIGHT * 0.95f;
		float positionSecondXBack = positionXBack + layout.width;
		float positionSecondYBack = positionYBack + layout.height;
		
		String heading = "Anleitung";
		layout.setText(fonts.arial, heading);
		float positionXHeading = PrinzGame.WIDTH / 2 - layout.width / 2;
		float positionYHeading = PrinzGame.HEIGHT * 0.95f;
		
		String description = "Ziel:\n"
							+ "Ziel des Spiels ist es, den entfuehrten Prinzen aus der Gefangenschaft "
							+ "zu befreien.\nIndem man Leute anspricht und deren Fragen richtig beantwortet, "
							+ "kommt man dem\nZiel naeher. Bei falschen Antworten verliert man an Leben."
							+ "Wenn alle Leben\naufgebraucht sind, ist das Spiel vorbei\n\n"
							+ "Steuerung:" 
							+ "Bewegung nach oben (w), nach links (a), nach unten (s), nach rechts (d)\n"
							+ "Dialoge weiterklicken: Leertaste\n"
							+ "Dialog beginnen: Leertaste";
		layout.setText(fonts.description, description);
		float positionXDescription = PrinzGame.WIDTH / 2 - layout.width / 2;
		float positionYDescription = PrinzGame.HEIGHT / 2 + layout.height / 2;
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		fonts.arial.setColor(Color.BLACK);
		fonts.arial.draw(game.batch, menu, positionXBack, positionYBack);
		fonts.arial.draw(game.batch, heading, positionXHeading, positionYHeading);
		fonts.description.setColor(Color.BLACK);
		fonts.description.draw(game.batch, description, positionXDescription, positionYDescription);
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && ToolMethods.checkIfMouseIsInTheArea(positionXBack, positionYBack, positionSecondXBack, positionSecondYBack)){
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
