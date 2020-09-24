package engine.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArray {
	private int id;
	private int vertexCount;
	private List<Integer> vbos = new ArrayList<Integer>();	
	
	public VertexArray(List<VertexData> vertexData, List<Integer> indices) {
		id = createVertexArrayID();
		bind();
		
		createAndBindVertexBuffer(vertexData);
		createAndBindIndexBuffer(indices);
	}
	
	public int getID() {
		return id;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public void release() {
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		GL30.glDeleteVertexArrays(id);
	}
	
	private int createVertexArrayID() {
		return GL30.glGenVertexArrays();
	}
	
	public void bind() {
		GL30.glBindVertexArray(id);
		GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
	}
	
	public void unbind() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void createAndBindVertexBuffer(List<VertexData> data) {
		// Positions
		addAttributef(0, 3, floatListToArray(createVertexPositionsList(data)));
		
		// UVs
		addAttributef(1, 2, floatListToArray(createVertexUVsList(data)));
		
		// Normals
		addAttributef(2, 3, floatListToArray(createVertexNormalsList(data)));
	}
	
	private void addAttributef(int attribNum, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = toFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribNum, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void createAndBindIndexBuffer(List<Integer> indices) {
		this.vertexCount = indices.size();
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		int[] array = indices.stream().mapToInt(i->i).toArray();
		IntBuffer buffer = toIntBuffer(array);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer toIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer toFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private List<Float> createVertexPositionsList(List<VertexData> vertexData) {
		List<Float> list = new ArrayList<Float>();
		
		for (VertexData vertex : vertexData) {
			list.add(vertex.getPosition().x);
			list.add(vertex.getPosition().y);
			list.add(vertex.getPosition().z);
		}
		
		return list;
	}
	
	private List<Float> createVertexUVsList(List<VertexData> vertexData) {
		List<Float> list = new ArrayList<Float>();
		
		for (VertexData vertex : vertexData) {
			list.add(vertex.getUv().x);
			list.add(vertex.getUv().y);
		}
		
		return list;
	}
	
	private List<Float> createVertexNormalsList(List<VertexData> vertexData) {
		List<Float> list = new ArrayList<Float>();
		
		for (VertexData vertex : vertexData) {
			list.add(vertex.getNormal().x);
			list.add(vertex.getNormal().y);
			list.add(vertex.getNormal().z);
		}
		
		return list;
	}
	
	private float[] floatListToArray(List<Float> floatList) {
		float[] floatArray = new float[floatList.size()];
		int i = 0;

		for (Float f : floatList) {
		    floatArray[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		return floatArray;
	}
}
