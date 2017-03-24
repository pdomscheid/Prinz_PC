package de.aps.prinz_of_pc.tool_methods;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import de.aps.prinz_of_pc.PrinzGame;

public class ToolMethods {

	public static GlyphLayout layout = new GlyphLayout();
	
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
	public static boolean checkIfMouseIsInTheArea(float firstX, float firstY, float secondX, float secondY, BitmapFont font, String str){
		// correct the button area
		layout.setText(font, str);
		firstY -= layout.height;
		secondY -= layout.height;
		
		if(Gdx.input.getX() > firstX && Gdx.input.getX() < secondX && PrinzGame.HEIGHT - Gdx.input.getY() > firstY && PrinzGame.HEIGHT - Gdx.input.getY() < secondY){
			return true;
		}
		return false;
	}
}
