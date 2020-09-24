package engine.events;

public class MouseMovedEvent extends Event {
	private float x, y, dx, dy;
	
	public MouseMovedEvent(float x, float y, float dx, float dy) {
		name = "MouseMoved";
		categoryFlags = Event.EventCategoryInput | Event.EventCategoryMouse;
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public String toString() {
		return "Mouse Moved: (" + dx + ", " + dy + ")";
	}

	@Override
	public EventType getEventType() {
		return EventType.MouseMoved;
	}

	public static EventType getStaticType() {
		return EventType.MouseMoved;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return dy;
	}
}
