package de.aps.prince_of_pc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {
	
	private float xPos = Gdx.graphics.getWidth()/2;
	private float yPos = Gdx.graphics.getHeight()/2;
	float oldX;
	float oldY;
	
	private Vector2 movement = new Vector2(0,0);
    private Texture character;
    private TiledMapTileLayer collisionLayer;
    

	public Player (Sprite sprite, TiledMapTileLayer collisionLayer, float x, float y) {
		super(sprite);
		this.collisionLayer = collisionLayer;
		this.xPos = x;
		this.yPos = y;
	}
	
	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	
	public void update(){
		boolean collisionX = false;
		boolean collisionY = false;
		
		
		
		if(getxPos() <  oldX) // going left
			collisionX = collidesLeft();
		else if(getxPos() > oldX) // going right
			collisionX = collidesRight();
		
		// react to x collision
				if(collisionX) {
					setX(oldX);
				} 
				
		if(getyPos() < oldY) // going down
			collisionY = collidesBottom();
				else if(getyPos() > oldY) // going up
					collisionY = collidesTop();

				// react to y collision
				if(collisionY) {
					setY(oldY);

				}
				oldX = getxPos();
				oldY = getyPos();
//				System.out.println(collisionX + " " + collisionY);
//				System.out.println(xPos + "  " + yPos);
		
	}
	
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) ( x / collisionLayer.getTileWidth()), (int)(y/collisionLayer.getTileHeight()));
		
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("1");
	}
	
	public boolean collidesRight() {
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(!isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		System.out.println("rechts frei");
		return false;
	}
	
	public boolean collidesLeft() {
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(!isCellBlocked(getX(), getY() + step))
				return true;
		System.out.println("links frei");
		return false;
	}

	public boolean collidesTop() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(!isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		System.out.println("oben frei");
		return false;

	}

	public boolean collidesBottom() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(!isCellBlocked(getX() + step, getY()))
				return true;
		System.out.println("unten frei");
		return false;
	}
	
	

}
