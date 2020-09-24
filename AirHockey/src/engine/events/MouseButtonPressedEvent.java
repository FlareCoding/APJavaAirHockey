package engine.events;

public class MouseButtonPressedEvent extends Event {
	private MouseButton button;
	
	public MouseButtonPressedEvent(MouseButton button) {
		name = "MouseButtonPressed";
		categoryFlags = Event.EventCategoryInput | Event.EventCategoryMouse | Event.EventCategoryMouseButton;
		
		this.button = button;
	}
	
	@Override
	public String toString() {
		return "Mouse Pressed: " + button;
	}

	@Override
	public EventType getEventType() {
		return EventType.MouseButtonPressed;
	}

	public static EventType getStaticType() {
		return EventType.MouseButtonPressed;
	}
	
	public MouseButton getButton() {
		return button;
	}
}
