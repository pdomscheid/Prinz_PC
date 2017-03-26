package de.aps.prince_of_pc.tool_methods;

import com.badlogic.gdx.Gdx;

import de.aps.prince_of_pc.PrinceGame;

public class ToolMethods {

	/**
	 * Checks if the Mouse is between the parameters
	 * @param firstX = Button down-left side
	 * @param firstY = Button top-left side
	 * @param secondX = Button down-right side
	 * @param secondY = Button top-right side
	 * @return
	 */
	public static boolean checkIfMouseIsInTheArea(float firstX, float firstY, float secondX, float secondY){
		if(Gdx.input.getX() > firstX && Gdx.input.getX() < secondX && PrinceGame.HEIGHT - Gdx.input.getY() > firstY && PrinceGame.HEIGHT - Gdx.input.getY() < secondY){
			return true;
		}
		return false;
	}
}