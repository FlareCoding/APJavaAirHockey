package engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.events.Event;

public abstract class ICamera {
	protected Matrix4f projectionMatrix;
	protected Matrix4f viewMatrix;
	
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected float yaw = 0, pitch = 0, roll = 0;
	
	protected abstract void updateProjectionMatrix();
	protected abstract void updateViewMatrix();
	
	public abstract void update();
	public abstract void onEvent(Event event);
	
	public Matrix4f getProjection() {
		return projectionMatrix;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f pos) {
		position = pos;
		updateViewMatrix();
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
		updateViewMatrix();
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
		updateViewMatrix();
	}
	
	public float getRoll() {
		return roll;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
		updateViewMatrix();
	}
	
	public void translate(Vector3f vec) {
		position.x += vec.x;
		position.y += vec.y;
		position.z += vec.z;
		updateViewMatrix();
	}
	
	public void translate(float x, float y, float z) {
		position.x += x;
		position.y += y;
		position.z += z;
		updateViewMatrix();
	}
	
	public void rotate(Vector3f vec) {
		yaw += vec.x;
		pitch += vec.y;
		roll += vec.z;
		updateViewMatrix();
	}
	
	public void rotate(float x, float y, float z) {
		yaw += x;
		pitch += y;
		roll += z;
		updateViewMatrix();
	}
}
