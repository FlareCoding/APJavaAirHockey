package game.entities;

import java.util.Map;

import org.joml.Vector2f;

import engine.modelData.Texture2D;
import engine.rendering.Renderer2D;

public class ScorePanel {
	private static Map<Integer, String> scoreNumberTextureMap = Map.of(
			0, "res/textures/scoreNumber0.png",
			1, "res/textures/scoreNumber1.png",
			2, "res/textures/scoreNumber2.png",
			3, "res/textures/scoreNumber3.png",
			4, "res/textures/scoreNumber4.png",
			5, "res/textures/scoreNumber5.png",
			6, "res/textures/scoreNumber6.png",
			7, "res/textures/scoreNumber7.png"
	);
	
	private Texture2D texture = null;
	private int score;
	private Vector2f position;
	private Vector2f size = new Vector2f(90, 90);
	
	public ScorePanel(Vector2f position) {
		this.position = position;
		setTexture(0);
	}
	
	public void onRender() {
		Renderer2D.drawQuad(position, size, texture, 1.0f);
	}
	
	public Texture2D getTexture() {
		return texture;
	}
	
	public void setTexture(int score) {
		texture = Texture2D.loadFromFile(getTextureNameFromNumber(score));
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	private String getTextureNameFromNumber(int score) {
		return scoreNumberTextureMap.get(Integer.valueOf(score));
	}
 }
