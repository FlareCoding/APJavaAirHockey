package engine.modelData;

import engine.rendering.VertexArray;

public class Mesh {
	private VertexArray vertexArray;
	private MeshData meshData;
	
	public Mesh(MeshData meshData) {
		this.meshData = meshData;
		this.vertexArray = new VertexArray(meshData.vertices, meshData.indices);
	}
	
	public VertexArray getVertexArray() {
		return vertexArray;
	}
	
	public MeshData getMeshData() {
		return meshData;
	}
}
