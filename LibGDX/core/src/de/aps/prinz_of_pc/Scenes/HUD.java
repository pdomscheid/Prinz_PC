package de.aps.prinz_of_pc.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.aps.prinz_of_pc.PrinzGame;

public class HUD {

	public Stage stage;
	private Viewport viewport;
	
	private int currentLives = 3; 
	
	Label livesLabel;
	Label currentLivesLabel;
	
	public HUD(SpriteBatch sb){
		this.currentLives = 3;
		this.viewport = new FitViewport(PrinzGame.WIDTH, PrinzGame.WIDTH);
		stage = new Stage(viewport, sb);
		
		Table table = new Table(); // Für Struktur in der Stage
		table.top(); // Setzt es an die Spitze der Stage
		table.setFillParent(true); // Größe der Stage
		
		livesLabel = new Label("Leben: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		currentLivesLabel = new Label(String.format("%01d", currentLives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(livesLabel).expand().padTop(10);
		table.row();
		table.add(currentLivesLabel).expand().padTop(10);
		
		stage.addActor(table);
	}
}
