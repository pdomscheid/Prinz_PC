package de.aps.prince_of_pc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.sound.sampled.Line;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import de.aps.prinz_of_pc.fonts.MyFonts;

public class PrinceGame extends ApplicationAdapter implements InputProcessor {

	Player player;

	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	private SpriteBatch batch;
	private Texture character;
	private Sprite playerSprite;
	MyFonts font;
	public int[][] arr = new int[200][200];
	ShapeRenderer shapeRenderer;
	boolean [] dialogNPCs=new boolean[12];
	int [] dialogNPCsTextField=new int [12];
	
	private String lastState = "down";

	@Override
	public void create() {

		// Spielercharacter
		batch = new SpriteBatch();
		// Verschiedene Texturen f�r Richtungen
		character = new Texture(Gdx.files.internal("char_down.PNG"));

		// Map
		tiledMap = new TmxMapLoader()
				.load("probemap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		//Font
		font=new MyFonts();
		shapeRenderer=new ShapeRenderer();
		
		//Sound
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("bilders-sound/02-the-superstar-saga.mp3"));

		// Spielercharacter erstellen
		playerSprite = new Sprite(character);
		player = new Player(playerSprite, (TiledMapTileLayer) tiledMap.getLayers().get("blocked"),
				Gdx.graphics.getWidth() * 1f, Gdx.graphics.getHeight() * 4f);

		Gdx.input.setInputProcessor(this);
		sound.play(1.0f);
		sound.loop();
		updateMap();
		
		try {
			getValueOfLayerBlocked();
		} catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Fehler");
			System.exit(0);
		}
		
		printArray();
		
		// Kamera
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1280, 720);
		camera.setToOrtho(false, w, h);
		camera.position.set(player.getxPos(), player.getyPos(), 0);

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		player.update();
		colision();
		
		
		// System.out.println("Cam-Pos: "+camera.position);
		// Kamera u. Map rendern
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		// Characterposition
		player.setxPos(Gdx.graphics.getWidth() / 2);
		player.setyPos(Gdx.graphics.getHeight() / 2);
		player.setPosition(player.getxPos(), player.getyPos());

		// Movement
		if (!collisionleft()) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				camera.translate(-2f, 0);
				// lastState = "left";
			}
		}

		if (!collisionright()) {
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				// player.translate(2f,0);
				camera.translate(2f, 0);
				// lastState = "right";
			}
		}

		if (!collisionup()) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				camera.translate(0, 2f);
				// lastState = "up";
			}
		}

		if (!collisiondown()) {
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				camera.translate(0, -2f);
				// lastState = "down";
			}
		}

		batch.begin();
		
		
		startDialog();
		player.draw(batch);
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}

	private void startDialog() {
		font.description.setColor(Color.WHITE);
		if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 131 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[1]==true) {
			System.err.println("Dialog möglich!");
			dialogWithFeminist();
		}else if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 132 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[1]==true) {
			
		}
		
	}

	private void dialogWithFeminist() {
		drawTextBox();
		dialogNPCs[1]=true;
		
		switch (dialogNPCsTextField[1]) {
		case 0:
			String dialog0="Feministin Felicitas: Das haette dir woh so gepasst. Aber ich werde mir die \n"
					+ "Unterdrückung des Patriarchats nicht weiter gefallen lassen du Chauvinist.";
			
			font.description.draw(batch, dialog0, 100, 190);
			dialogNPCsTextField[1]=0;
			
			break;

		default:
			break;
		}
		
		
		
		
	}
	
	private void drawTextBox(){
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(80, 0, 1120, 200);
		shapeRenderer.end();
		batch.begin();
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

	public void getValueOfLayerBlocked()
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		
		/**
		 * Nur nötig bei aktualissiierung der map
		 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		System.out.println("angelegt vorher");
		Document doc = builder.parse(new File("C:/Users/AsimB/OneDrive/Dokumente/GitHub/PC/Prinz_PC/core/assets/probemap.tmx"));
		System.out.println("angelegt");
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName("layer");
		// System.out.println("ukupno:"+nodeList.getLength());

		PrintWriter writer = new PrintWriter("aktmap.txt", "UTF-8");
		
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node nNode = nodeList.item(i);
			Node an2 = null;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				// System.out.println("Kachelebene : " + Element.getAttribute("name"));
				if (eElement.getAttribute("name").equals("blocked")) {
					NodeList nl2 = nNode.getChildNodes();
					for (int i2 = 0; i2 < nl2.getLength(); i2++) {
						an2 = nl2.item(i2);
						// DEBUG PRINTS1
						// System.out.println(an2.getNodeName() + ": type (" + an2.getNodeType() + "):");
						if (an2.hasChildNodes()) {
							// System.out.println(an2.getFirstChild().getTextContent());
						}
						if (an2.hasChildNodes()) {
							// System.out.println(an2.getFirstChild().getNodeValue());
						}
						/**
						 * Beides liefern das gleiche Ergebnis. Ergebnis sind
						 * die ids der Kachelebene: "blocked"
						 */
//						System.out.println(an2.getTextContent());
//						System.out.println(an2.getNodeValue());

						updateMap();

						 if(i2==1){
//						 fillArray(lines);
						 writer.println(an2.getFirstChild().getTextContent());
						 writer.close();
						
						 }



					}

				}
			}

		}

	}

	public void printArray() {
		System.out.println("Array");
		for (int i = 0; i < arr.length; i++) {
			System.out.println(i + ": ");
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + ", ");
			}
			//System.out.println("|||");
		}

	}

	public void fillArray(List<String> lines) {
		int zaehler = 199;
		for (int index = 199; zaehler >= 0; index--) {
			if ((index) == -1) {
				index = 199;
			}
			System.out.println("Zaehler: " + zaehler + " index: " + index + "= "
					+ Integer.parseInt(lines.toString().replaceAll("\\[, ", "").split(",")[index]));
			arr[zaehler][index] = Integer.parseInt(lines.toString().replaceAll("\\[, ", "").split(",")[index]);
			if (index == 0) {
				zaehler--;
			}
		}
	}

	/**
	 * Aktualisierung der Map
	 */
	private void updateMap() {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("../desktop/aktmap.txt"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("No such file!");
		}
		
		/**
		 * Kartenaktualisierung(id belegung der probemap.tmx wird an ein 2D
		 * Array übergeben)
		 */
		for (int k = 0, indexFuerArray = 0; k < lines.size(); k++) {

			for (int k2 = 0, zahl = 0; k2 < lines.get(k).split(",").length; k2++) {
				if (lines.get(k).split(",").length > 199) {
					//System.out.println(lines.get(k).split(",").length);
					if (lines.get(k).split(",")[k2].equals("193") || lines.get(k).split(",")[k2].equals("0") || lines.get(k).split(",")[k2].equals("131")) {
						//System.out.print("WERT: " + lines.get(k).split(",")[k2] + ", ");

						arr[indexFuerArray][zahl] = Integer.parseInt(lines.get(k).split(",")[k2]);
						zahl++;
						if (zahl == 200) {
							indexFuerArray++;
							zahl = 0;
						}

					} else {

					}

				}
			}

		}

	}

	/**
	 * y ist x und x ist y
	 * Kollisionermittlung
	 */
	public void colision() {
		// System.out.println(camera.position);
		System.out.println("x: " + ((int) camera.position.x / 16) + ", y: " + (((int) camera.position.y / 16 - 199) * (-1)));

		// collision
		if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 193) {
			// System.exit(0);
			System.err.println("Collision");
		}

	}

	public boolean collisionleft() {

		// collision left
		if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)-1] == 193) {
			// System.exit(0);
			System.err.println("Collision left");
			return true;
		}

		return false;
	}

	public boolean collisionright() {

		// collision right
		if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16) +1] == 193 ||
			arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16) +2] == 193) {
			// System.exit(0);
			System.err.println("Collision right");
			return true;
		}
		return false;

	}

	public boolean collisionup() {

		// collision up
		if (arr[(((int) camera.position.y / 16 - 199) * (-1)) - 1][((int) camera.position.x / 16)] == 193 ||
				arr[(((int) camera.position.y / 16 - 199) * (-1)) - 1][((int) camera.position.x / 16)+1] == 193) {
			System.err.println("Collision up");
			return true;
		}

		return false;
	}

	public boolean collisiondown() {

		// collision down
		if (arr[(((int) camera.position.y / 16 - 199) * (-1)) + 1][((int) camera.position.x / 16)] == 193 ||
				arr[(((int) camera.position.y / 16 - 199) * (-1)) + 1][((int) camera.position.x / 16)+1] == 193) {
			// System.exit(0);
			System.err.println("Collision down");
			return true;
		}
		return false;

	}
}
