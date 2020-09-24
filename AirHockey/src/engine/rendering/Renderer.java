package engine.rendering;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public class Renderer {
	public static void init() {
		Renderer2D.init();
	}
	
	public static void shutdown() {
		Renderer2D.shutdown();
	}
	
	public static void clearSreenColor(Vector4f color) {
		GL11.glClearColor(color.x, color.y, color.z, color.w);
	}
	
	public static void clearSreenColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}
	
	public static void clearScreenBuffers() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void setWireframe(boolean wf) {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, wf ? GL11.GL_LINE : GL11.GL_FILL);
	}
	
	public static void setViewport(int x, int y, int width, int height) {
		GL11.glViewport(x, y, width, height);
	}
}
