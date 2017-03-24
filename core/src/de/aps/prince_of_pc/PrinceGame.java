package de.aps.prince_of_pc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PrinceGame extends ApplicationAdapter implements InputProcessor {
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Texture charUp;
    private Texture charDown;
    private Texture charLeft;
    private Texture charRight;
    private Sprite spriteUp;
    private Sprite spriteDown;
    private Sprite spriteLeft;
    private Sprite spriteRight;
    
    private String lastState = "down";
    

    
	@Override
    public void create () {
		//Spielercharacter
    	batch = new SpriteBatch();
    	//Verschiedene Texturen für Richtungen
    	charUp = new Texture(Gdx.files.internal("char_up.png"));
    	charDown = new Texture(Gdx.files.internal("char_down.png"));
    	charLeft = new Texture(Gdx.files.internal("char_left.png"));
    	charRight = new Texture(Gdx.files.internal("char_right.png"));
        spriteUp = new Sprite(charUp); 
        spriteDown = new Sprite(charDown);
        spriteLeft = new Sprite(charLeft);
        spriteRight = new Sprite(charRight);

        //Kamera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(1280, 720);
        camera.setToOrtho(false,w,h);
        camera.position.set(camera.viewportWidth / 4, camera.viewportHeight * 4f, 0);
        camera.update();
        
        //Map
        tiledMap = new TmxMapLoader().load("probemap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
       
        Gdx.input.setInputProcessor(this);
        
       
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
    
    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Kamera u. Map rendern
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        //Characterposition
        spriteUp.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        spriteUp.setOrigin(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        spriteDown.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        spriteLeft.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        spriteRight.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        
        //Movement
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
   		 camera.translate(-2f,0);
   		 lastState = "left";
   		 
        }
   		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
      		 camera.translate(2f,0);
      		lastState = "right";
   		}
   		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
	   		 camera.translate(0,2f);
	   		lastState = "up";	
	   		 }
   		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	   		 camera.translate(0,-2f);	
	   		lastState = "down";
   		}
      	
      	
      	 batch.begin();
      	 //Kontrollstruktur für Anzeige der Charaktertextur
         switch(lastState){
         case "up": 
        	 spriteUp.draw(batch);
        	 break;
         case "down":
        	 spriteDown.draw(batch);
        	 break;
         case "left":
        	 spriteLeft.draw(batch);
        	 break;
         case "right":
        	 spriteRight.draw(batch);
        	 break;
         default:
        	 spriteDown.draw(batch);
        	 break;
         }
         batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
         batch.end();
    }


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
