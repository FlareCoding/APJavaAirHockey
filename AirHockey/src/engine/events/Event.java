package engine.events;

public abstract class Event {
	public static long EventCategoryWindow 		= 1 << 0;
	public static long EventCategoryInput 		= 1 << 1;
	public static long EventCategoryKeyboard	= 1 << 2;
	public static long EventCategoryMouse 		= 1 << 3;
	public static long EventCategoryMouseButton = 1 << 4;
	
	private boolean handled = false;
	protected String name = "";
	protected long categoryFlags = 0;
	
	public abstract String toString();
	public abstract EventType getEventType();
	
	public boolean isHandled() {
		return handled;
	}
	
	public void setHandled(boolean state) {
		handled = state;
	}

	public String getName() {
		return name;
	}

	public long getCategoryFlags() {
		return categoryFlags;
	}

	public boolean isInCategory(long category) {
		return (getCategoryFlags() & category) != 0;
	}
}
