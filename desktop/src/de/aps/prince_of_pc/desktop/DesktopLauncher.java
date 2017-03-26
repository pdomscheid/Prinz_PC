package de.aps.prince_of_pc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.aps.prince_of_pc.PrinceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Prince of PC";
	      config.width = 1280;
	      config.height = 720;
	      //Fenstergroeße nicht veränderbar
	      config.resizable = false; 
	      //FPS einstellung
	      config.foregroundFPS = 60;
		new LwjglApplication(new PrinceGame(), config);

	}
}
