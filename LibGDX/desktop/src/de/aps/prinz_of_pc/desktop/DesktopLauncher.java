package de.aps.prinz_of_pc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.aps.prinz_of_pc.PrinzGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Set window sizes
		config.width = PrinzGame.WIDTH;
		config.height = PrinzGame.HEIGHT;
		// Window is not resizable
		config.resizable = false;
		// Wenn der Spieler spielt
		config.foregroundFPS = 60;
		config.title = "Prinz PC";
		new LwjglApplication(new PrinzGame(), config);
	}
}
