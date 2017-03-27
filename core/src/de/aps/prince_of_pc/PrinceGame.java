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
		tiledMap = new TmxMapLoader().load("probemap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		//Font
		font=new MyFonts();
		shapeRenderer=new ShapeRenderer();
		
		//Sound
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("C:/Users/AsimB/OneDrive/Dokumente/GitHub/PC/Prinz_PC/core/assets/bilders-sound/02-the-superstar-saga.mp3"));

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
		if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 131 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[1]==true) {
			//Feminist
			System.err.println("Dialog möglich!");
			dialogNPCs[1]=true;
			dialogWithFeminist();
		}else if (arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 130 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[0]==true) {
			dialogNPCs[0]=true;
			//Mark
			System.err.println("Dialog mit mark möglich!");
			dialogWithMark();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 132 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[2]==true){
			//Nice Girl, Sister of Feminists
			System.err.println("Dialog möglich!");
			dialogWithNiceGirl();
			dialogNPCs[2]=true;
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 133 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[3]==true){
			//Detlef
			dialogNPCs[3]=true;
			dialogWithDetlef();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 134 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[4]==true){
			
		}
		
	}

	private void dialogWithDetlef() {
		drawTextBox();
		String dialog3;
		
		switch (dialogNPCsTextField[3]) {
		case 0:
			dialog3="Detlef:Hey du Lauch! Komm mit mir ins Gym dort werde ich dich zu einem wahren Mann formen. \n"
					+ "Erstmal checke ich aber ob du verstanden hast wie man richtig trainiert! \n"
					+ "Was ist eine gutes Trainingsprogramm?";
			font.description.draw(batch, dialog3, 100, 190);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[3]=1;
			}
			break;
		case 1:
			dialog3="Detlef: Du hast Potential kleiner!";
			font.description.draw(batch, dialog3, 100, 190);
			break;
		default:
			break;
		}
	}

	private void dialogWithNiceGirl() {
		drawTextBox();
		String dialog2;
		
		switch (dialogNPCsTextField[2]) {
		case 0:
			dialog2="Schwester der Feministin: Hey mein kleiner. Du siehst ja völlig erschöpft aus, \n"
					+ "komm doch in meinen Safespace und ruhe dich etwas aus.";
			font.description.draw(batch, dialog2, 100, 190);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[2]=1;
			}
			break;
		case 1:
			dialog2="Ich liebe Blumen";
		default:
			break;
		}
		
	}

	private void dialogWithFeminist() {
		drawTextBox();
		dialogNPCs[1]=true;
		String dialog0;
		switch (dialogNPCsTextField[1]) {
		case 0:
			dialog0="Feministin Felicitas: Das haette dir wohl so gepasst. Aber ich werde mir die \n"
					+ "Unterdrueckung des Patriarchats nicht weiter gefallen lassen du Chauvinist.";
			
			font.description.draw(batch, dialog0, 100, 190);

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				
				dialogNPCsTextField[1]=1;
			}
			break;
		case 1:
			dialog0="Prince PC: Ich suche meinen Prinzen.";
			font.description.draw(batch, dialog0, 100, 190);

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				
				dialogNPCsTextField[1]=2;
			}
			break;
		case 2:
			dialog0="Feministin Felicias:Ach du suchst deinen Freund? Wie sueß. Bevor ich dir weiter helfe erklaere \n"
					+ "mir doch bitte was Manspredding ist?\n"
					+ "a) Die Ausdehnung von Männern in klassisch weiblichen Berufen\n"
					+ "b) Das breitbeinige sitzen von Maennern\n"
					+ "c) Eine Maskulinistische Bewegung zur Emanzipation von Männern";
			font.description.draw(batch, dialog0, 100, 190);

			if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {				
				dialogNPCsTextField[1]=4;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.C)){
				
				dialogNPCsTextField[1]=5;
			}
			break;	
		case 3:
			dialog0="Emanzipation heisst nicht dem Mann gleichwertig werden zu wollen, sondern unsere Stärken zu \nerkennen"
					+ "und sie zu leben, aber auch die Schwaechen der Maenner zu erkennen und sie zu akzeptieren. \n"
					+ "Nur wenn uns das gelingt, werden wir wahrhaft emanzipiert sein.";
			font.description.draw(batch, dialog0, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[1]=3;
				dialogNPCs[1]=false;
			}
			break;
		case 4:
			dialog0="Hmm wenn ich es mir recht überlege habe ich gestern eine mysteriöse Gestalt gesehen, die \nsich in Richtung"
					+ " Sueden davon machte. Schau dich doch im Dorf der Toleranz um. \nEin wunderbarer Ort…. *säuftz*";
			font.description.draw(batch, dialog0, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[1]=false;
				dialogNPCsTextField[1]=3;
			}
			break;
		case 5:
			dialog0="Du Schwein du solltest dich direkt auf den Weg nach Osten ins Machodorf \nmachen wo du hingehoerst.";
			font.description.draw(batch, dialog0, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[1]=0;
				
			}
			
		default:
			break;
		}
		
		if(dialogNPCs[1]==false){
			dialogNPCsTextField[1]=3;
		}
		
	}
	
	private void dialogWithMark(){
		
		drawTextBox();
		
		String dialog0;
		switch (dialogNPCsTextField[0]) {
		case 0:
			dialog0="Mysterioeser Typ: Hey Prinz was ist los?";
			
			font.description.draw(batch, dialog0, 100, 190);

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				
				dialogNPCsTextField[0]=1;
			}else {
				
			}
			break;
		case 1:
			dialog0="Prince PC: ...";
			font.description.draw(batch, dialog0, 100, 190);

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				
				dialogNPCsTextField[0]=2;
			}
			break;
		case 2:
			dialog0="Mysterioeser Typ: Ach du suchst deinen Freund? Es tut mir leid ich habe nichts gesehen,\naber gehe doch nach Südosten zur Feministin Felicias sie kann dir sicher weiterhelfen.";
			font.description.draw(batch, dialog0, 100, 190);

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[0]=false;
				dialogNPCsTextField[0]=2;
			}
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
					if (lines.get(k).split(",")[k2].equals("193") || lines.get(k).split(",")[k2].equals("130") 
							|| lines.get(k).split(",")[k2].equals("0") || lines.get(k).split(",")[k2].equals("131")
							|| lines.get(k).split(",")[k2].equals("132") || lines.get(k).split(",")[k2].equals("133")
							|| lines.get(k).split(",")[k2].equals("134") || lines.get(k).split(",")[k2].equals("135")
							|| lines.get(k).split(",")[k2].equals("136") || lines.get(k).split(",")[k2].equals("137")
							|| lines.get(k).split(",")[k2].equals("140") || lines.get(k).split(",")[k2].equals("139")
							|| lines.get(k).split(",")[k2].equals("141") || lines.get(k).split(",")[k2].equals("142")) {
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
