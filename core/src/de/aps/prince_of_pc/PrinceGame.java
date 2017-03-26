package de.aps.prince_of_pc;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.aps.prince_of_pc.screens.MenuScreen;

public class PrinceGame extends Game {

	//Window sizes
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}