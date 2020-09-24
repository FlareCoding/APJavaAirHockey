package engine.rendering.shaders;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class IShader {
	private int shaderProgramID, vsID, psID;
	private static FloatBuffer matrix4Buffer = BufferUtils.createFloatBuffer(16);
	
	protected abstract void getAllUniformLocations();
	protected abstract void bindAttributes();
	
	public IShader(List<String> sources) {
		vsID = createShader(sources.get(0), GL20.GL_VERTEX_SHADER);
		psID = createShader(sources.get(1), GL20.GL_FRAGMENT_SHADER);
		shaderProgramID = GL20.glCreateProgram();
		GL20.glAttachShader(shaderProgramID, vsID);
		GL20.glAttachShader(shaderProgramID, psID);
		bindAttributes();
		GL20.glLinkProgram(shaderProgramID);
		GL20.glValidateProgram(shaderProgramID);
		getAllUniformLocations();
	}
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(shaderProgramID, uniformName);
	}

	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(shaderProgramID, attribute, variableName);
	}

	public void start() {
		GL20.glUseProgram(shaderProgramID);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}

	protected void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}

	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}

	protected void loadVector(int location, Vector4f vector) {
		GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
	}

	protected void loadVector(int location, Vector2f vector) {
		GL20.glUniform2f(location, vector.x, vector.y);
	}

	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, value ? 1 : 0);
	}

	protected void loadMatrix4(int location, Matrix4f matrix) {
		matrix.get(matrix4Buffer);
		GL20.glUniformMatrix4fv(location, false, matrix4Buffer);
	}

	public void release() {
		stop();
		GL20.glDetachShader(shaderProgramID, vsID);
		GL20.glDetachShader(shaderProgramID, psID);
		GL20.glDeleteShader(vsID);
		GL20.glDeleteShader(psID);
		GL20.glDeleteProgram(shaderProgramID);
	}
	
	private static int createShader(String source, int type) {
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, source);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
}
