package de.aps.prince_of_pc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import de.aps.screen.Playscreen;

public class PrinceGame extends Game {
//   
//	Player player;
//	
//	Texture img;
//    TiledMap tiledMap;
//    OrthographicCamera camera;
//    TiledMapRenderer tiledMapRenderer;
//    private SpriteBatch batch;
//    private Texture character;
//    private Sprite playerSprite;
//    private float delta;
    

    
	@Override
    public void create () {
		setScreen(new Playscreen());
		
//		//Spielercharacter
//    	batch = new SpriteBatch();
//    	
//    	//Textur für Character
//    	character = new Texture(Gdx.files.internal("char_down.png"));
//    	
//        //Map
//        tiledMap = new TmxMapLoader().load("probemap.tmx");
//        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//        
//      //Spielercharacter erstellen
//        playerSprite = new Sprite(character);
//        player = new Player(playerSprite, (TiledMapTileLayer) tiledMap.getLayers().get("blocked"),Gdx.graphics.getWidth() * 1f, Gdx.graphics.getHeight() * 4f);
//        player.setX(Gdx.graphics.getWidth() * 1f);
//        player.setY(Gdx.graphics.getHeight() * 4f);
//        
//        //Gdx.input.setInputProcessor(this);
//       
//        
//        
//        //Kamera
//        float w = Gdx.graphics.getWidth();
//        float h = Gdx.graphics.getHeight();
//        camera = new OrthographicCamera(1280, 720);
//        camera.setToOrtho(false,w,h);
//        camera.position.set(player.getX(), player.getY(), 0);


        
       
    }

    @Override
    public void dispose() {
    	super.dispose();
        //batch.dispose();
    }
    
    @Override
    public void render () {
    	super.render();
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        
//       // player.update();
//        delta = Gdx.graphics.getDeltaTime();
//        
//        //Kamera u. Map rendern
//        camera.update();
//        tiledMapRenderer.setView(camera);
//        tiledMapRenderer.render();
//        
//        //Characterposition
//        player.setxPos(Gdx.graphics.getWidth()/2);
//        player.setyPos(Gdx.graphics.getHeight()/2);       
//        player.setPosition(player.getxPos(), player.getyPos());
//        
//        
//        //Movement
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//   		 player.translate(-2f,0);
//   		 
//        }
//   		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//      		 //player.translate(2f,0);
//   			    
//   		}
//   		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//	   		 player.translate(0,2f);
//	   		 }
//   		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//	   		 player.translate(0,-2f);	
//   		}
//      	
//      	
//   		 
//      	 batch.begin();
//      	 player.draw(batch);
//         batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//         batch.end();
    }
}
