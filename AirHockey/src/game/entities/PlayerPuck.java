package game.entities;

import org.joml.Vector2f;

import engine.modelData.Texture2D;
import engine.physics.BoundingCircle;
import engine.rendering.Renderer2D;

public class PlayerPuck {
	private Texture2D texture = null;
	private Vector2f size = new Vector2f(100, 100);
	private Vector2f position = new Vector2f(0, 0);
	private BoundingCircle boundingCircle;
	
	public PlayerPuck() {
		texture = Texture2D.loadFromFile("res/textures/player_puck.png");
		boundingCircle = new BoundingCircle(size.x / 2.0f);
	}

	public void onRender() {
		Renderer2D.drawQuad(position, size, texture, 1.0f);
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
		boundingCircle.setRadius(size.x / 2.0f);
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
		boundingCircle.setPosition(position);
	}
	
	public BoundingCircle getBoundingCircle() {
		return boundingCircle;
	}
}
