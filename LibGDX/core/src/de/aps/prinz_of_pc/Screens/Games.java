package de.aps.prinz_of_pc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.aps.prinz_of_pc.PrinzGame;
import de.aps.prinz_of_pc.fonts.MyFonts;
import de.aps.prinz_of_pc.tool_methods.ToolMethods;

public class Games implements Screen{

	PrinzGame game;
	MyFonts fonts;
	
	public Games(PrinzGame game){
		this.game = game;
		this.fonts = new MyFonts();
	}
	
	/**
	 * Checks if the mouse is in the box 
	 * @param firstX
	 * @param secondX
	 * @return
	 */
	private boolean checkHoverEffect(float firstX, float secondX, float firstY, float secondY){
		if(ToolMethods.checkIfMouseIsInTheArea(firstX, firstY, secondX, secondY)){
			return true;
		}
		return false;
	}
	
	/**
	 * Draws the Fonts in Black or in Gray (hovered = true)
	 * @param str = String to draw
	 * @param firstX = Position bottom-left
	 * @param firstY = Position top -left
	 * @param secondX = Position bottom-right
	 */
	protected void drawFont(String str, float firstX, float secondX, float firstY, float firstYBox, float secondYBox, BitmapFont font){
		if(checkHoverEffect(firstX, secondX, firstYBox, secondYBox)){
			font.setColor(Color.GRAY);
			font.draw(game.batch, str, firstX, firstY);
		} else{
			font.draw(game.batch, str, firstX, firstY);
		}
		font.setColor(Color.BLACK);
	}
	
	/**
	 * Checks if the Mouse is between the parameters
	 * @param firstX = Button down-left side
	 * @param firstY = Button top-left side
	 * @param secondX = Button down-right side
	 * @param secondY = Button top-right side
	 * @return
	 */
	public static boolean checkIfMouseIsInTheArea(float firstX, float firstY, float secondX, float secondY){
		if(Gdx.input.getX() > firstX && Gdx.input.getX() < secondX && PrinzGame.HEIGHT - Gdx.input.getY() > firstY && PrinzGame.HEIGHT - Gdx.input.getY() < secondY){
			return true;
		}
		return false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		fonts.arial.dispose();
		fonts.description.dispose();
		fonts.menue.dispose();
		
	}
}
