package de.aps.prince_of_pc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PrinceGame extends ApplicationAdapter implements InputProcessor {
   
	Player player;
	
	Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Texture character;
    private Sprite playerSprite;

    
    private String lastState = "down";
    

    
	@Override
    public void create () {
		//Spielercharacter
    	batch = new SpriteBatch();
    	//Verschiedene Texturen für Richtungen
    	character = new Texture(Gdx.files.internal("char_down.png"));
    	
        //Map
        tiledMap = new TmxMapLoader().load("probemap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
      //Spielercharacter erstellen
        playerSprite = new Sprite(character);
        player = new Player(playerSprite, (TiledMapTileLayer) tiledMap.getLayers().get("blocked"),Gdx.graphics.getWidth() * 1f, Gdx.graphics.getHeight() * 4f);
       
        Gdx.input.setInputProcessor(this);
       
        
        
        //Kamera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(1280, 720);
        camera.setToOrtho(false,w,h);
        camera.position.set(player.getxPos(), player.getyPos(), 0);


        
       
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
        
        player.update();
        
        //Kamera u. Map rendern
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        //Characterposition
        player.setxPos(Gdx.graphics.getWidth()/2);
        player.setyPos(Gdx.graphics.getHeight()/2);       
        player.setPosition(player.getxPos(), player.getyPos());
        
        
        //Movement
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
   		 player.translate(-2f,0);
   		 //lastState = "left";
   		 
        }
   		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
      		 player.translate(2f,0);
      		//lastState = "right";
   		}
   		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
	   		 player.translate(0,2f);
	   		//lastState = "up";	
	   		 }
   		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	   		 player.translate(0,-2f);	
	   		//lastState = "down";
   		}
      	
      	
   		 
      	 batch.begin();
      	 player.draw(batch);
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
