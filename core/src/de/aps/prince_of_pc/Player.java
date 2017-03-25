package de.aps.prince_of_pc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor{
	
	
	//Movement
	private Vector2 velocity = new Vector2(0,0);
	private float speed = 60 * 2;
	
    private TiledMapTileLayer collisionLayer;
    

	public Player (Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		//update(Gdx.graphics.getDeltaTime());
		this.collisionLayer = collisionLayer;
		
	}
	
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public void update(float delta){
		float oldX = getX();
		float oldY = getY();
		
		boolean collisionX = false;
		boolean collisionY = false;
		
		//Movement
		// move on x
		setX(getX() + velocity.x * delta);
		
		//check for x axis collision
		if(velocity.x <  0) // going left
			collisionX = collidesLeft();
		else if(velocity.x > 0) // going right
			collisionX = collidesRight();
		
		// react to x collision
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
		} 
		
		
		//move on y
		setY(getY() + velocity.y * delta);
		
		//check for y axis collision
		if(velocity.y < 0) // going down
			collisionY = collidesBottom();
		else if(velocity.y > 0) // going up
			collisionY = collidesTop();
		
		// react to y collision
				if(collisionY) {
					setY(oldY);
					velocity.y = 0;
				}
				
				System.out.println(collisionX + " " + collisionY);
				System.out.println(oldX + " " + oldY);
				System.out.println(getX() + "  " + getY());
		
	}
	
	
	//Ansatz mit Rectangles
	private boolean checkCollision(){
		
		MapObjects objects = collisionLayer.getObjects();
		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

		    Rectangle rectangle = rectangleObject.getRectangle();
		    
		    if (Intersector.overlaps(rectangle, this.getBoundingRectangle())) {
		        return true;
		    }  
		    
		}
		    
		
		return false;
	}
	
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) ( x / collisionLayer.getTileWidth()), (int)(y/collisionLayer.getTileHeight()));
		
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}
	
	public boolean collidesRight() {
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}
	
	public boolean collidesLeft() {
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			velocity.y = speed * 1.8f;
			break;
		case Keys.A:
			velocity.x = -speed;
			break;
		case Keys.D:
			velocity.x = speed;
			break;
		case Keys.S:
			velocity.y = -speed * 1.8f;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.A:
			velocity.x = 0;
			break;
		case Keys.D:
			velocity.x = 0;
			break;
		case Keys.W:
			velocity.y = 0;
			break;
		case Keys.S:
			velocity.y = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
