package engine.rendering.shaders;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class BasicGUIShader extends IShader {
	private static String sourceFilename = "res/glsl/Default.glsl";
	
	private int transformLocation;
	private int projectionLocation;
	private int viewMatrixLocation;
	private int colorLocation;
	
	public BasicGUIShader() {
		super(ShaderBuilder.readShaderSourceFromFile(sourceFilename));
	}

	@Override
	protected void getAllUniformLocations() {
		transformLocation = super.getUniformLocation("u_Transform");
		projectionLocation = super.getUniformLocation("u_Projection");
		viewMatrixLocation = super.getUniformLocation("u_ViewMatrix");
		colorLocation = super.getUniformLocation("u_Color");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "in_Position");
		super.bindAttribute(1, "in_UV");
		super.bindAttribute(2, "in_Normal");
	}
	
	public void setMVPBuffer(Matrix4f transform, Matrix4f projection, Matrix4f view) {
		super.loadMatrix4(transformLocation, transform);
		super.loadMatrix4(projectionLocation, projection);
		super.loadMatrix4(viewMatrixLocation, view);
	}
	
	public void setBasicShaderBufferColor(Vector4f color) {
		super.loadVector(colorLocation, color);
	}
}
