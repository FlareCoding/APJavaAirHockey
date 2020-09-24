package game.entities;

import org.joml.Vector2f;

import engine.modelData.Texture2D;
import engine.rendering.Renderer2D;
import engine.window.Window;

public class GameBoard {
	private Texture2D texture = null;

	public GameBoard() {
		texture = Texture2D.loadFromFile("res/textures/board.png");
	}

	public void onRender() {
		Renderer2D.drawQuad(
				new Vector2f((float) Window.get().getWidth() / 2.0f, (float) Window.get().getHeight() / 2.0f),
				new Vector2f((float) Window.get().getWidth(), (float) Window.get().getHeight()), texture, 1.0f);
	}
}
