package de.aps.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

import de.aps.prince_of_pc.Player;

public class Playscreen implements Screen {
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Player player;
	Texture img;
    private Sprite playerSprite;
    private SpriteBatch batch;
    
    TiledMapRenderer tiledMapRenderer;
    
    private Texture character;
    
    private float delta;

	@Override
	public void show() {
	 tiledMap = new TmxMapLoader().load("probemap.tmx");
	 renderer = new OrthogonalTiledMapRenderer(tiledMap);
	 float w = Gdx.graphics.getWidth();
     float h = Gdx.graphics.getHeight();
     camera = new OrthographicCamera(1280, 720);
     camera.setToOrtho(false,w,h);

     
     batch = new SpriteBatch();
     
    //Textur für Character
 	img = new Texture(Gdx.files.internal("char_down.png"));
 	//Spielercharacter erstellen
    playerSprite = new Sprite(img);
    
    player = new Player(playerSprite, (TiledMapTileLayer) tiledMap.getLayers().get("blocked"));
    player.setPosition(20 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 17) * player.getCollisionLayer().getTileHeight());
    
    
     Gdx.input.setInputProcessor(player);

		
	}

	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(1, 1, 1, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
	     
	     camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		 camera.update();
		 
			
	     renderer.setView(camera);
	     renderer.render();
	    
	     
	     batch.begin();
	     batch.setProjectionMatrix(camera.combined);
	     player.draw(batch);
	     batch.end();
	     
	     
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
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
		dispose();
		tiledMap.dispose();
		renderer.dispose();
		player.getTexture().dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
