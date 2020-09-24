package game.entities;

import org.joml.Vector2f;

import engine.modelData.Texture2D;
import engine.physics.BoundingCircle;
import engine.rendering.Renderer2D;
import engine.window.Window;

public class Puck {
	private Texture2D texture = null;
	private Vector2f size = new Vector2f(60, 60);
	private Vector2f position = new Vector2f();
	private BoundingCircle boundingCircle;
	
	private Vector2f velocity = new Vector2f();

	public Puck() {
		texture = Texture2D.loadFromFile("res/textures/puck.png");
		boundingCircle = new BoundingCircle(size.x / 2.0f);
	}

	public void onRender() {
		position.x += velocity.x;
		position.y += velocity.y;
		sanitizePuckPosition();
		
		Renderer2D.drawQuad(position, size, texture, 1.0f);
	}

	public Vector2f getSize() {
		return size;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setSize(Vector2f size) {
		this.size = size;
		boundingCircle.setRadius(size.x / 2.0f);
	}

	public void setPosition(Vector2f position) {
		this.position = position;
		boundingCircle.setPosition(position);
	}
	
	public BoundingCircle getBoundingCircle() {
		return boundingCircle;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}
	
	private void sanitizePuckPosition() {
		float puckRadius = size.x / 2.0f;
		float screenWidth = (float)Window.get().getWidth();
		
		if (position.x > screenWidth - puckRadius) {
			position.x = screenWidth - puckRadius;
			velocity.x = -velocity.x;
		}	
		else if (position.x < puckRadius) {
			position.x = puckRadius;
			velocity.x = -velocity.x;
		}
		
		if (position.y > (float)Window.get().getHeight() - puckRadius) {
			if (position.x > screenWidth / 2.0f + puckRadius * 2.0f ||
				position.x < screenWidth / 2.0f - puckRadius * 2.0f) {
				position.y = (float)Window.get().getHeight() - puckRadius;
				velocity.y = -velocity.y;
			}
		}
		else if (position.y < puckRadius) {
			if (position.x > screenWidth / 2.0f + puckRadius * 2.0f ||
					position.x < screenWidth / 2.0f - puckRadius * 2.0f) {
					position.y = puckRadius;
					velocity.y = -velocity.y;
			}
		}
	}
}
