package engine.modelData;

import java.util.Arrays;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.rendering.VertexData;

public class Shapes {
	public static MeshData quad = new MeshData(
		Arrays.asList(
			new VertexData(new Vector3f(-0.5f, 0.5f, 0), new Vector2f(0, 0), new Vector3f(0, 1, 0)),
			new VertexData(new Vector3f(-0.5f, -0.5f, 0), new Vector2f(0, 1), new Vector3f(0, 1, 0)),
			new VertexData(new Vector3f(0.5f, 0.5f, 0), new Vector2f(1, 0), new Vector3f(0, 1, 0)),
			new VertexData(new Vector3f(0.5f, -0.5f, 0), new Vector2f(1, 1), new Vector3f(0, 1, 0))
		),
		Arrays.asList(
			0, 1, 2,
			2, 1, 3
		)
	);
}
