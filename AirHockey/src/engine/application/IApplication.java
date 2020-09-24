package engine.application;

import engine.events.Event;
import engine.events.WindowCloseEvent;
import engine.rendering.Renderer;
import engine.time.Time;
import engine.window.Window;
import engine.window.WindowEventCallback;

public abstract class IApplication {
	private static IApplication s_ApplicationInstance = null;
	
	public static IApplication get() {
		return s_ApplicationInstance;
	}
	
	private boolean running = false;
	private Window window = null;
	private int windowWidth, windowHeight;
	private String windowTitle;
	
	public IApplication(int windowWidth, int windowHeight, String windowTitle) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.windowTitle = windowTitle;
		
		s_ApplicationInstance = this;
	}
	
	public abstract void onInitialize();
	public abstract void onUpdate(double timestep);
	public abstract void onEvent(Event event);
	public abstract void onShutdown();
	
	public boolean isRunning() {
		return running;
	}
	
	public void close() {
		running = false;
	}
	
	public void run() {
		running = true;
		initializeWindow();
		
		onInitialize();
		
		while (running) {
			Time.frameUpdate();
			window.processEvents();
			
			onUpdate(Time.getTimestep());
			
			window.update();
		}
		
		onShutdown();
		Renderer.shutdown();
		window.destroy();
	}
	
	private void initializeWindow() {
		window = Window.create(windowWidth, windowHeight, windowTitle);
		window.setOnEventFn(new WindowEventCallback() {
			@Override
			public void run() {
				internalEventHandler(event);
			};	
		});
		
		Renderer.init();
	}
	
	private void internalEventHandler(Event event) {
		if (event.getEventType() == WindowCloseEvent.getStaticType()) {
			running = false;
		}
		else {
			onEvent(event);
		}
	}
}
