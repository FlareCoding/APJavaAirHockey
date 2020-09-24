package engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.events.Event;
import engine.window.Window;

public class OrthographicCamera extends ICamera {

	private float zNear = 1000000.0f, zFar = 0.0f;
	
	public OrthographicCamera() {
		updateProjectionMatrix();
		updateViewMatrix();
	}
	
	public OrthographicCamera(float zNear, float zFar) {
		this.zNear = zNear;
		this.zFar = zFar;
		
		updateProjectionMatrix();
		updateViewMatrix();
	}
	
	@Override
	protected void updateProjectionMatrix() {
		this.projectionMatrix = new Matrix4f().ortho(0, (float)Window.get().getWidth(), 0, (float)Window.get().getHeight(), zNear, zFar);
	}

	@Override
	protected void updateViewMatrix() {
		viewMatrix = new Matrix4f();
		viewMatrix = viewMatrix.rotate((float)Math.toRadians(pitch), 	new Vector3f(1, 0, 0));
		viewMatrix = viewMatrix.rotate((float)Math.toRadians(yaw), 		new Vector3f(0, 1, 0));
		viewMatrix = viewMatrix.rotate((float)Math.toRadians(roll), 	new Vector3f(0, 0, 1));
		viewMatrix = viewMatrix.translate(position);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void onEvent(Event event) {
		
	}
}
