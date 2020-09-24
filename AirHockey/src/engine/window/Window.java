package engine.window;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.opengl.GL;

import engine.events.MouseButton;
import engine.events.MouseButtonPressedEvent;
import engine.events.MouseButtonReleasedEvent;
import engine.events.MouseMovedEvent;
import engine.events.WindowCloseEvent;

public class Window {
	private static Window s_WindowInstance = null;

	private static float s_PreviousMousePosX = 0.0f;
	private static float s_PreviousMousePosY = 0.0f;
	
	private long windowHandle = 0;
	private int width, height;
	private String title;
	private WindowEventCallback eventCallback = null;

	public static Window get() {
		return s_WindowInstance;
	}

	public static Window create(int width, int height, String title) {
		return new Window(width, height, title);
	}

	private Window(int width, int height, String title) {
		s_WindowInstance = this;

		this.width = width;
		this.height = height;
		this.title = title;

		init();
	}

	private void init() {
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW");
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

		windowHandle = glfwCreateWindow(width, height, title, 0, 0);
		if (windowHandle == 0) {
			throw new IllegalStateException("Failed to create window");
		}
		
		glfwShowWindow(windowHandle);
		glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		
		glfwSetWindowCloseCallback(windowHandle, new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
				if (eventCallback != null) {
					eventCallback.event = new WindowCloseEvent();
					eventCallback.run();
				}
			}
		});
		
		glfwSetCursorPosCallback(windowHandle, new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double x, double y) {
				if (eventCallback != null) {
					float dx = (float)x - s_PreviousMousePosX;
					float dy = (float)y - s_PreviousMousePosY;
					
					s_PreviousMousePosX = (float)x;
					s_PreviousMousePosY = (float)y;
					
					eventCallback.event = new MouseMovedEvent((float)x, (float)y, dx, dy);
					eventCallback.run();
				}
			};
		});
		
		glfwSetMouseButtonCallback(windowHandle, new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button_code, int action, int mods) {
				if (eventCallback != null) {
					MouseButton button = MouseButton.None;
					
					if (button_code == GLFW.GLFW_MOUSE_BUTTON_1) button = MouseButton.Left;
					if (button_code == GLFW.GLFW_MOUSE_BUTTON_2) button = MouseButton.Right;
					if (button_code == GLFW.GLFW_MOUSE_BUTTON_3) button = MouseButton.Middle;
					if (button_code == GLFW.GLFW_MOUSE_BUTTON_4) button = MouseButton.Button4;
					if (button_code == GLFW.GLFW_MOUSE_BUTTON_5) button = MouseButton.Button5;
					
					if (action == GLFW_PRESS) {
						eventCallback.event = new MouseButtonPressedEvent(button);
						eventCallback.run();
					}
					else if (action == GLFW_RELEASE) {
						eventCallback.event = new MouseButtonReleasedEvent(button);
						eventCallback.run();
					}
				}
			}
		});
	}

	public long getNativeHandle() {
		return windowHandle;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}
	
	public void setOnEventFn(WindowEventCallback windowEventCallback) {
		eventCallback = windowEventCallback;
	}

	public boolean isOpened() {
		return !glfwWindowShouldClose(windowHandle);
	}

	public void destroy() {
		glfwDestroyWindow(windowHandle);
		glfwTerminate();
	}

	public void processEvents() {
		 glfwPollEvents();
	}
	
	public void update() {
		if (isOpened()) {
			glfwSwapBuffers(windowHandle);
		}
	}
}
