package engine.events;

public class MouseButtonReleasedEvent extends Event {
	private MouseButton button;
	
	public MouseButtonReleasedEvent(MouseButton button) {
		name = "MouseButtonReleased";
		categoryFlags = Event.EventCategoryInput | Event.EventCategoryMouse | Event.EventCategoryMouseButton;
		
		this.button = button;
	}
	
	@Override
	public String toString() {
		return "Mouse Released: " + button;
	}

	@Override
	public EventType getEventType() {
		return EventType.MouseButtonReleased;
	}

	public static EventType getStaticType() {
		return EventType.MouseButtonReleased;
	}
	
	public MouseButton getButton() {
		return button;
	}
}

