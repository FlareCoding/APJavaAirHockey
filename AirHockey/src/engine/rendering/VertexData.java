package engine.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class VertexData {
	private Vector3f position 	= new Vector3f(0, 0, 0);
	private Vector2f uv 		= new Vector2f(0, 0);
	private Vector3f normal 	= new Vector3f(0, 0, 0);
	
	public VertexData() {}
	
	public VertexData(Vector3f position, Vector2f uv, Vector3f normal) {
		this.position = position;
		this.uv = uv;
		this.normal = normal;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector2f getUv() {
		return uv;
	}

	public void setUv(Vector2f uv) {
		this.uv = uv;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
}
