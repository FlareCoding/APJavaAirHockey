package engine.input;

import org.lwjgl.glfw.GLFW;

import engine.events.MouseButton;
import engine.window.Window;

public class Input {
	public static boolean isMouseButtonPressed(MouseButton button) {
		switch (button) {
		case Left:
			return GLFW.glfwGetMouseButton(Window.get().getNativeHandle(), GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
		case Right:
			return GLFW.glfwGetMouseButton(Window.get().getNativeHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS;
		case Middle:
			return GLFW.glfwGetMouseButton(Window.get().getNativeHandle(), GLFW.GLFW_MOUSE_BUTTON_3) == GLFW.GLFW_PRESS;
		case Button4:
			return GLFW.glfwGetMouseButton(Window.get().getNativeHandle(), GLFW.GLFW_MOUSE_BUTTON_4) == GLFW.GLFW_PRESS;
		case Button5:
			return GLFW.glfwGetMouseButton(Window.get().getNativeHandle(), GLFW.GLFW_MOUSE_BUTTON_5) == GLFW.GLFW_PRESS;
		default: break;
		}
		
		return false;
	}
}
