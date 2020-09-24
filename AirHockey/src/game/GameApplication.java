package game;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.joml.Vector2f;

import engine.application.IApplication;
import engine.camera.OrthographicCamera;
import engine.events.Event;
import engine.rendering.Renderer;
import engine.rendering.Renderer2D;
import engine.window.Window;
import game.controllers.GameLogicController;
import game.controllers.PlayerPuckController;
import game.entities.GameBoard;
import game.entities.PlayerPuck;
import game.entities.Puck;
import game.entities.ScorePanel;

public class GameApplication extends IApplication {

	public GameApplication(int windowWidth, int windowHeight, String windowTitle) {
		super(windowWidth, windowHeight, windowTitle);
	}

	private OrthographicCamera orthoCamera;

	private ServerSocket serverSocket = null;
	private Socket socket = null;

	private GameBoard gameBoard;
	private PlayerPuck p1Puck;
	private PlayerPuck p2Puck;
	private PlayerPuckController playerController;
	private Puck mainPuck;
	private ScorePanel p1ScorePanel;
	private ScorePanel p2ScorePanel;
	private GameLogicController gameLogicController;

	@Override
	public void onInitialize() {
		String connectionInfo = JOptionPane.showInputDialog("Server IP and Port (x.x.x.x@4554): ");

		String ip = connectionInfo.substring(0, connectionInfo.indexOf("@"));
		String port = connectionInfo.substring(connectionInfo.indexOf("@") + 1);

		System.out.println("IP: " + ip + "\nPort: " + port);

		if (ip.equals("0.0.0.0")) {
			// Player 1 Settings
			try {
				serverSocket = new ServerSocket(Integer.parseInt(port));

				socket = serverSocket.accept();

				// Receiving the connection confirmation
				InputStream inputStream = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(inputStream);
				char[] buffer = new char[128];
				reader.read(buffer);

			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
				System.exit(4);
			}
		} else {
			// Player 2 Settings
			GameState.isPlayer1 = false;
			try {
				socket = new Socket(ip, Integer.parseInt(port));

				TimeUnit.MILLISECONDS.sleep(200);

				// Sending the connection confirmation
				OutputStream outputStream = socket.getOutputStream();
				String confirmation = "connection confirmed";
				outputStream.write(confirmation.getBytes());

				System.out.println("Client Confirmation Sent");

			} catch (NumberFormatException | IOException | InterruptedException e) {
				e.printStackTrace();
				System.exit(4);
			}
		}

		orthoCamera = new OrthographicCamera();
		gameBoard = new GameBoard();

		p1Puck = new PlayerPuck();
		p1Puck.setPosition(new Vector2f(250, 200));

		p2Puck = new PlayerPuck();
		p2Puck.setPosition(new Vector2f(250, 800));

		mainPuck = new Puck();
		mainPuck.setPosition(new Vector2f(Window.get().getWidth() / 2, Window.get().getHeight() / 2));
		mainPuck.setVelocity(new Vector2f(3, 0));

		p1ScorePanel = new ScorePanel(new Vector2f(Window.get().getWidth() - 100, Window.get().getHeight() / 2 - 120));
		p2ScorePanel = new ScorePanel(new Vector2f(Window.get().getWidth() - 100, Window.get().getHeight() / 2 + 120));

		playerController = new PlayerPuckController(p1Puck);
		try {
			gameLogicController = new GameLogicController(p1Puck, p2Puck, mainPuck, p1ScorePanel, p2ScorePanel, socket,
					new InputStreamReader(socket.getInputStream()), socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(5);
		}

		System.out.println("Game Initialized");
	}

	@Override
	public void onShutdown() {
		try {
			socket.close();

			if (serverSocket != null)
				serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Game Shutdown");
	}

	@Override
	public void onUpdate(double timestep) {
		Renderer.clearScreenBuffers();
		Renderer.clearSreenColor(0, 0, 0, 1.0f);

		if (!GameState.isGameOver)
			gameLogicController.update();

		Renderer2D.beginScene(orthoCamera);

		gameBoard.onRender();
		p1ScorePanel.onRender();
		p2ScorePanel.onRender();
		p1Puck.onRender();
		p2Puck.onRender();
		mainPuck.onRender();

		Renderer2D.endScene();
		
		if (!GameState.isGameOver) {
			if (GameState.player1Score >= GameState.MAX_GAME_SCORE ||
				GameState.player2Score >= GameState.MAX_GAME_SCORE) {
				// Game Over
				GameState.isGameOver = true;
					
				boolean localPlayerWon = (GameState.player1Score >= GameState.MAX_GAME_SCORE);
					
				JOptionPane.showConfirmDialog(null,
					localPlayerWon ? "You Won!" : "You Lost",
	                "Game Over",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.PLAIN_MESSAGE
				);
					
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onEvent(Event event) {
		playerController.update(event);
	}
}
