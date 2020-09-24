package engine.time;

import org.lwjgl.glfw.GLFW;

public class Time {
	private static double elapsedTime = 0;
	private static double timeScale = 0;
	private static double timestep = 0;
	private static double initialTime = -1;
	private static double lastTrackedTime = 0;
	
	public static double getElapsedTime() {
		return elapsedTime;
	}
	
	public static double getTimeScale() {
		return timeScale;
	}
	
	public static void setTimeScale(double scale) {
		timeScale = scale;
	}
	
	public static double getTimestep() {
		return timestep;
	}
	
	public static double getInitialTime() {
		return initialTime;
	}
	
	public static void frameUpdate() {
		if (initialTime == -1) {
			initialTime = GLFW.glfwGetTime();
		}
		
		double currentTime = GLFW.glfwGetTime();
		
		elapsedTime = currentTime - initialTime;
		timestep = elapsedTime - lastTrackedTime;
		
		lastTrackedTime = elapsedTime;
	}
}
