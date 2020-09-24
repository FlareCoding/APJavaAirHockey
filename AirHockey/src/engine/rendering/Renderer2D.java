package engine.rendering;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import engine.camera.ICamera;
import engine.modelData.Mesh;
import engine.modelData.Shapes;
import engine.modelData.Texture2D;
import engine.rendering.shaders.BasicGUIShader;

public class Renderer2D {
	private static Mesh quad = null;
	private static BasicGUIShader shader = null;
	private static Texture2D whiteTexture = null;
	
	private static Matrix4f sceneProjectionMatrix = null;
	private static Matrix4f sceneViewMatrix = null;
	
	public static void init() {
		quad = new Mesh(Shapes.quad);
		shader = new BasicGUIShader();
		whiteTexture = Texture2D.createFromColor(255, 255, 255);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void shutdown() {
		shader.release();
	}
	
	public static void beginScene(ICamera camera) {
		sceneProjectionMatrix = camera.getProjection();
		sceneViewMatrix = camera.getViewMatrix();
		
		shader.start();
	}
	
	public static void endScene() {
		shader.stop();
	}
	
	public static void drawQuad(Vector2f position, Vector2f size, Vector3f color, float opacity) {
		drawQuad(new Vector3f(position.x, position.y, 0.0f), size, color, opacity);
	}
	
	public static void drawQuad(Vector3f position, Vector2f size, Vector3f color, float opacity) {
		Matrix4f transform = new Matrix4f();
		transform = transform.translate(position);
		transform = transform.scale(size.x, size.y, 1.0f);
		
		whiteTexture.bind();
		
		shader.setBasicShaderBufferColor(new Vector4f(color.x, color.y, color.z, opacity));
		shader.setMVPBuffer(transform, sceneProjectionMatrix, sceneViewMatrix);
		
		quad.getVertexArray().bind();
		RenderingAPI.drawIndexed(quad.getVertexArray());
		
		quad.getVertexArray().unbind();
		whiteTexture.unbind();
	}
	
	public static void drawQuad(Vector2f position, Vector2f size, Texture2D texture, float opacity) {
		drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, opacity);
	}
	
	public static void drawQuad(Vector3f position, Vector2f size, Texture2D texture, float opacity) {
		Matrix4f transform = new Matrix4f();
		transform = transform.translate(position);
		transform = transform.scale(size.x, size.y, 1.0f);
		
		texture.bind();
		
		shader.setBasicShaderBufferColor(new Vector4f(1, 1, 1, opacity));
		shader.setMVPBuffer(transform, sceneProjectionMatrix, sceneViewMatrix);
		
		quad.getVertexArray().bind();
		RenderingAPI.drawIndexed(quad.getVertexArray());
		
		quad.getVertexArray().unbind();
		texture.unbind();
	}
}
