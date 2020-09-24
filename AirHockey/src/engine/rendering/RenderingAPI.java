package engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;

public class RenderingAPI {
	public static void drawIndexed(VertexArray va) {
		 GL11.glDrawElements(GL11.GL_TRIANGLES, va.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public static void drawInstanced(VertexArray va, int instanceCount) {
		GL31.glDrawElementsInstanced(GL11.GL_TRIANGLES, va.getVertexCount(), GL11.GL_UNSIGNED_INT, 0L, instanceCount);
	}
}
