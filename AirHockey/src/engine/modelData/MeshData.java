package engine.modelData;

import java.util.ArrayList;
import java.util.List;

import engine.rendering.VertexData;

public class MeshData {
	public List<VertexData> vertices = new ArrayList<VertexData>();
	public List<Integer> indices = new ArrayList<Integer>();
	
	public MeshData() {}
	public MeshData(List<VertexData> vertices, List<Integer> indices) {
		this.vertices = vertices;
		this.indices = indices;
	}
}
