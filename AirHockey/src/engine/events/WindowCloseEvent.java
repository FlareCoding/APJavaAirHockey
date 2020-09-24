package engine.events;

public class WindowCloseEvent extends Event {
	public WindowCloseEvent() {
		name = "WindowClose";
		categoryFlags = Event.EventCategoryWindow;
	}
	
	@Override
	public String toString() {
		return "WindowCloseEvent";
	}

	@Override
	public EventType getEventType() {
		return EventType.WindowClose;
	}

	public static EventType getStaticType() {
		return EventType.WindowClose;
	}
}
