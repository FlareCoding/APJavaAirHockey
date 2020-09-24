package game.controllers;

import org.joml.Vector2f;

import engine.events.Event;
import engine.events.MouseButton;
import engine.events.MouseMovedEvent;
import engine.input.Input;
import engine.window.Window;
import game.entities.PlayerPuck;

public class PlayerPuckController {
	private PlayerPuck puck;
	
	public PlayerPuckController(PlayerPuck puck) {
		this.puck = puck;
	}
	
	public void update(Event event) {
		if (event.getEventType() == MouseMovedEvent.getStaticType()) {
			if (Input.isMouseButtonPressed(MouseButton.Left)) {
				float x = ((MouseMovedEvent)event).getX();
				float y = ((MouseMovedEvent)event).getY();
				
				puck.setPosition(new Vector2f(x, Window.get().getHeight() - y));
				sanitizePuckPosition();
			}
		}
	}
	
	private void sanitizePuckPosition() {
		float puckRadius = puck.getSize().x / 2.0f;
		float screenWidth = (float)Window.get().getWidth();
		float midlineYCoord = (float)Window.get().getHeight() / 2.0f;
		Vector2f puckPosition = puck.getPosition();
		
		if (puckPosition.x > screenWidth - puckRadius)
			puckPosition.x = screenWidth - puckRadius;
		else if (puckPosition.x < puckRadius)
			puckPosition.x = puckRadius;
		
		if (puckPosition.y > midlineYCoord - puckRadius)
			puckPosition.y = midlineYCoord - puckRadius;
		else if (puckPosition.y < puckRadius)
			puckPosition.y = puckRadius;
	}
}
