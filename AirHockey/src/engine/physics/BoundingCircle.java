package engine.physics;

import org.joml.Vector2f;

public class BoundingCircle {
	private Vector2f position = new Vector2f();
	private float radius;
	
	public BoundingCircle(float radius) {
		this.radius = radius;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public static boolean colliding(BoundingCircle lhs, BoundingCircle rhs) {
		float distanceSquared = (float)(Math.pow(rhs.position.x - lhs.position.x, 2) + Math.pow(rhs.position.y - lhs.position.y, 2));
		float allowedDistanceSquared = (float)(Math.pow((lhs.radius + rhs.radius), 2));
		
		return distanceSquared <= allowedDistanceSquared;
	}
}
