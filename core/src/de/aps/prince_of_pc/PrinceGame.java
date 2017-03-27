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
	Sound sound;
	long id;
	
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
		 sound = Gdx.audio.newSound(Gdx.files.internal("C:/Users/AsimB/OneDrive/Dokumente/GitHub/PC/Prinz_PC/core/assets/bilders-sound/02-the-superstar-saga.mp3"));
		
		// Spielercharacter erstellen
		playerSprite = new Sprite(character);
		player = new Player(playerSprite, (TiledMapTileLayer) tiledMap.getLayers().get("blocked"),
				Gdx.graphics.getWidth() * 1f, Gdx.graphics.getHeight() * 4f);

		Gdx.input.setInputProcessor(this);
		id=sound.play(1.0f);
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
			dialogNPCs[2]=true;
			dialogWithNiceGirl();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 133 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[3]==true){
			//Detlef
			dialogNPCs[3]=true;
			dialogWithDetlef();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 134 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[4]==true){
			//Security
			dialogNPCs[4]=true;
			dialogWithSecurity();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 135 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[5]==true){
			//Ulrike
			dialogNPCs[5]=true;
			dialogWithUlrike();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 136 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[6]==true){
			//Sascha
			dialogNPCs[6]=true;
			dialogWithSascha();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 137 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[7]==true){
			//Jonas
			dialogNPCs[7]=true;
			dialogWithJonas();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 138 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[8]==true){
			//Security
			dialogNPCs[8]=true;
			dialogWithDorfaelteste();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 139 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[9]==true){
			//Security
			dialogNPCs[9]=true;
			dialogWithMark2();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 140 &&Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| dialogNPCs[10]==true){
			//Security
			dialogNPCs[10]=true;
			dialogWithKleinerBruder();
		}else if(arr[(((int) camera.position.y / 16 - 199) * (-1))][((int) camera.position.x / 16)] == 141 || dialogNPCs[11]==true){
			//Security
			dialogNPCs[11]=true;
			dialogWithMarkFinal();
		}
		
	}

	private void dialogWithMarkFinal() {
		drawTextBox();
		String dialog11;
		
		switch (dialogNPCsTextField[11]) {
		case 0:
			dialog11="Du hast es geschafft mich bis hier zu verfolgen, aber so einfach werde ich nicht aufgeben. Um\n"
					+ "deinen herzallerliebsten Prinzen zu befreien musst du mir erst diese Masterfrage beantworten, \n"
					+ "sonst werde ich euch zwei einsperren und mit meiner gewaltigen und unfassbar komplexen \n"
					+ "Normalmach-Maschine zu normalen Menschen machen.";
			font.description.draw(batch, dialog11, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[11]=1;
			}
			break;
		case 1:
			dialog11="Mark: Wer sollte ein Jobangebot bekommen?\n"
					+ "a) Der mit den besten Qualifikationen\n"
					+ "b) Eine Frau oder eine Minderheit um eine bestimmte Quote zu erfüllen?\n"
					+ "c) Donald Trump?";
			font.description.draw(batch, dialog11, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
				
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
				
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
				
			}
			break;
		default:
			break;
		}
	}

	private void dialogWithKleinerBruder() {
		drawTextBox();
		String dialog10;
		
		switch (dialogNPCsTextField[10]) {
		case 0:
			dialog10="Hey Prinz. Ich bin total verwirrt. Ich weiß einfach nicht was ich machen soll. Ich möchte \n"
					+ "einem Mädchen den Hof machen, weiß aber einfach nicht was ich machen soll um nachher \n"
					+ "nicht als Sexist oder Vergewaltiger zu gelten?";
			font.description.draw(batch, dialog10, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[10]=1;
			}
			break;
		case 1:
			dialog10="Entscheide dich:\n"
					+ "a) Ach was die Sorgen sind unbegründet mach ihr Komplimente und Zeige ihr, dass du \n"
					+ "sie begehrst und Wertschätzt, dann wird es zu nichts schlimmen kommen.\n"
					+ "b) Hier hast du ein Formular auf dem du und das Mädchen unterschreiben , dass sie \n"
					+ "mit den hier vereinbarten Handlungen einverstanden sind.\n"
					+ "c) Lass das mit den Frauen einfach und mach es wie ich und suche dir einen netten Freund";
			font.description.draw(batch, dialog10, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
				dialogNPCsTextField[10]=2;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.A)||Gdx.input.isKeyJustPressed(Input.Keys.C)){
				dialogNPCsTextField[10]=3;
			}
			break;
		case 2:
			dialog10="WOW, das ist eine fantastische Idee!";
			font.description.draw(batch, dialog10, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[10]=4;
			}
			break;
		case 3:
			dialog10="Waaas? Das ist doch totaler Mist!";
			font.description.draw(batch, dialog10, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[10]=0;
			}
			break;
		case 4:
			dialog10="Ja Ich habe gestern einen mysteriösen Mann gesehen, der einen Prinzen in den \n"
					+ "unheimlichen Turm im Norden geschleppt hat. Dort solltest du nachsehen.";
			font.description.draw(batch, dialog10, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[10]=false;
			}
			break;
		default:
			break;
		}
	}

	private void dialogWithMark2() {
		drawTextBox();
		String dialog9="Mark: Hey ich kann dir immer noch nicht helfen. Mich verwirrt das ganze seltsame Gerede von euch \n"
				+ "Wesen dieser Welt nur. Rede doch mit meinem kleinen Bruder auf der Nine-Gag-Lichtung im Wald \n"
				+ "im Norden. Vielleicht kann er dir helfen, wenn du ihm mit seinen verrückten Problemen hilfst.";
		font.description.draw(batch, dialog9, 100, 190);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			dialogNPCs[9]=false;
		}
		
	}

	private void dialogWithDorfaelteste() {
		drawTextBox();
		String dialog8;
		
		switch (dialogNPCsTextField[8]) {
		case 0:
			dialog8="Dorfaelteste: So du hast also mit Jonas geredet *grml*, so ein Unfug \n"
					+ "Kampfhubschrauber, was das denn für ein Bloedsinn sein?";
			font.description.draw(batch, dialog8, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[8]=1;
			}
			break;
		case 1:
			dialog8="Entscheide dich:\n"
					+ "a) Liebe Dorfälteste, sogar das allwissende Medium Facebook hat erkannt, dass es mehr \n"
					+ "als zwei Gender gibt und man dort sein Sender freu bestimmen!\n"
					+ "b) Sogar der amerikanische Kongress überlegt momentan ob es mehr als 34 Gender gibt ist das \n"
					+ "nicht Grund genug Jonas zu akzeptieren?\n"
					+ "c) Jeder Mensch fühlt sich halt wie er sich fühlt das ist normal";
			font.description.draw(batch, dialog8, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
				dialogNPCsTextField[8]=2;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.C)){
				dialogNPCsTextField[8]=3;
			}
			break;
		case 2:
			dialog8="Dorfaelteste: Hmm so habe ich das noch nie gesehen. \n"
					+ "Ich werde Jonas wohl so akzeptieren wie er ist.";
			font.description.draw(batch, dialog8, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[8]=4;
				dialogNPCsTextField[7]=1;
			}
			break;
		case 3:
			dialog8="Dorfaelteste: Was ein quatsch du bist ja genauso verrueckt wie Jonas";
			font.description.draw(batch, dialog8, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[8]=0;
			}
			break;
		case 4:
			dialog8="Dorfaelteste: Im Westen ist eine einsame Huette vielleicht ist dein Prinz da.";
			font.description.draw(batch, dialog8, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[8]=false;
			}
			break;
		default:
			break;
		}
		
	}

	private void dialogWithJonas() {
		drawTextBox();
		String dialog7;
		
		switch (dialogNPCsTextField[7]) {
		case 0:
			dialog7="Jonas: Hey mein Prinz kannst du mir Bitte helfen? Die Dorfälteste möchte mich verstoßen und das nur\n"
					+ "weil ich mich als Apachekampfhubschrauber fühle. Ich möchte mich einfach nicht in diese unsinnigen \n"
					+ "Geschlechterrollen von Mann und Frau drängen lassen.";
			font.description.draw(batch, dialog7, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[7]=false;
			}
			break;
		case 1:
			dialog7="Jonas: Nun fliege ich wie ein Apachekampfhubschrauber!";
			font.description.draw(batch, dialog7, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[7]=false;
			}
			break;
		default:
			break;
		}
		
	}

	private void dialogWithSascha() {
		drawTextBox();
		String dialog6;
		
		switch (dialogNPCsTextField[6]) {
		case 0:
			dialog6="Sascha: Was mache ich nur? Was mache ich nur?";
			font.description.draw(batch, dialog6, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[6]=1;
			}
			break;
		case 1:
			dialog6="Prinz PC: ...";
			font.description.draw(batch, dialog6, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[6]=2;
			}
			break;
		case 2:
			dialog6="Ah du suchst deinen Freund? Ich was mir nicht sicher ob es eine Entfuehrung oder einfach nur \n"
					+ "eine sexuelle Vorliebe war. Ich wollte auf keinen fall intolerant sein.\n"
					+ "Also da ich so hilfsbereit bin werde ich ohne Gegenleistung mein Wissen mit dir teilen!\n"
					+ "Gestern Nacht sah ich wie ein mysteriöser Mann einen zweiten Mann gefesselt in Richtung \n"
					+ "Westen zum See der eiskalten Engel schleppte.";
			font.description.draw(batch, dialog6, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[6]=3;
			}
			break;
		case 3:
			dialog6="Ich wünsche dir alles Glück der Welt , auf das du und dein Prinz wieder glücklich vereint werdet.";
			font.description.draw(batch, dialog6, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[6]=false;
			}
			break;
		default:
			break;
		}
	}

	private void dialogWithDetlef() {
		drawTextBox();
		String dialog3;
		
		switch (dialogNPCsTextField[3]) {
		case 0:
			dialog3="Detlef:Hey du Lauch! Komm mit mir ins Gym dort werde ich dich zu einem wahren Mann formen. \n"
					+ "Erstmal checke ich aber ob du verstanden hast wie man richtig trainiert! \n";
			font.description.draw(batch, dialog3, 100, 190);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[3]=1;
			}
			break;
		case 1:
			dialog3="Was ist ein gutes Trainingsprogramm?\n"
					+ "a) Brust und Bizeps \n"
					+ "b) Ganzkörper\n"
					+ "c) Beine";
			font.description.draw(batch, dialog3, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
				dialogNPCsTextField[3]=2;	
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
				dialogNPCsTextField[3]=3;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
				dialogNPCsTextField[3]=4;
			}
			break;
			
		case 2:
			dialog3="Detlef: Ja da hast du recht, aber wenn ich es mir recht überlege sind bei dir Hopfen und Malz \n"
					+ "verloren. Geh lieber nach Süden ins Dorf der Toleranz und rede mit \n"
					+ "Ulrike da bist du besser aufgehoben.";
			font.description.draw(batch, dialog3, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[3]=false;
			}
			
			break;
		case 3:
			dialog3="Detlef: Komm wieder wenn du es besser weißt.";
			font.description.draw(batch, dialog3, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[3]=0;
			}
			break;
		case 4:
			dialog3="Detlef: Man trainiert nicht die Beine, weil man sie im Club nicht sieht.";
			font.description.draw(batch, dialog3, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[3]=0;
			}
			break;
		default:
			break;
		}
	}
	
	private void dialogWithSecurity(){
		drawTextBox();
		dialogNPCs[4]=true;
		String dialog4;
		
		dialog4="Tuersteher: Hier ist nur Platz fuer wahre Maenner!";
		font.description.draw(batch, dialog4, 100, 190);
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			dialogNPCs[4]=false;
		}
		
	}
	
	private void dialogWithUlrike(){
		drawTextBox();
		dialogNPCs[5]=true;
		String dialog5;
		
		switch (dialogNPCsTextField[5]) {
		case 0:
			dialog5="Ulrike: Black life matters!!!";
			font.description.draw(batch, dialog5, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[5]=1;
			}
			break;
		case 1:
			dialog5="Prinz PC: Finde ich auch. Ich suche meinen Freund. Kannst du mir helfen?";
			font.description.draw(batch, dialog5, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[5]=2;
			}
			break;
		case 2:
			dialog5="Oh du suchst deinen Freund? Hmm ich weiß nicht ob ich dir helfen moechte, ich habe Angst, \n"
					+ "dass du ein Rassist bist.";
			font.description.draw(batch, dialog5, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[5]=3;
			}
			break;
		case 3:
			dialog5="Beantworte mir doch schnell diese Frage, wie werden Menschen meiner Hautfarbe genannt?\n"
					+ "a) Schwarze\n"
					+ "b) Afroamerikaner\n"
					+ "c) People of Color";
			font.description.draw(batch, dialog5, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
				dialogNPCsTextField[5]=4;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.A)){
				dialogNPCsTextField[5]=5;
			}
			break;
		case 4:
			dialog5="Wow endlich mal einer der sich auskennt. Rede doch mit Sasha er hat heute morgen etwas \n"
					+ "in diese Richtung erwaehnt.";
			font.description.draw(batch, dialog5, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[5]=false;
			}
			break;
		case 5:
			dialog5="AHHHHHH du Rassist!!!";
			font.description.draw(batch, dialog5, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[5]=0;
			}
			break;
		default:
			break;
		}
		
	}

	private void dialogWithNiceGirl() {
		drawTextBox();
		dialogNPCs[2]=true;
		String dialog2;
		
		switch (dialogNPCsTextField[2]) {
		case 0:
			dialog2="Schwester der Feministin: Hey mein kleiner. Du siehst ja voellig erschoepft aus, \n"
					+ "komm doch in meinen Safespace und ruhe dich etwas aus.";
			font.description.draw(batch, dialog2, 100, 190);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCsTextField[2]=1;
				
			}
			
			break;
		case 1:
			dialog2="Schwester der Feministin: Ich liebe es zu kochen!";
			font.description.draw(batch, dialog2, 100, 190);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dialogNPCs[2]=false;
			}
			
			break;
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
			dialog0="Hmm wenn ich es mir recht ueberlege habe ich gestern eine mysteriöse Gestalt gesehen, die \n"
					+ "sich in Richtung Sueden davon machte. Schau dich doch im Dorf der Toleranz um. \n"
					+ "Ein wunderbarer Ort…. *säuftz*";
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
				dialogNPCs[1]=false;
				
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
			dialog0="Mysterioeser Typ: Ach du suchst deinen Freund? Es tut mir leid ich habe nichts gesehen,\n"
					+ "aber gehe doch nach Suedosten zur Feministin Felicias sie kann dir sicher weiterhelfen.";
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
							|| lines.get(k).split(",")[k2].equals("138") || lines.get(k).split(",")[k2].equals("139")
							|| lines.get(k).split(",")[k2].equals("140") || lines.get(k).split(",")[k2].equals("141")
							|| lines.get(k).split(",")[k2].equals("7") || lines.get(k).split(",")[k2].equals("9")) {
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
