package game.controllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.physics.BoundingCircle;
import engine.window.Window;
import game.GameState;
import game.entities.PlayerPuck;
import game.entities.Puck;
import game.entities.ScorePanel;

public class GameLogicController {
	private PlayerPuck p1Puck;
	private PlayerPuck p2Puck;
	private Puck mainPuck;
	ScorePanel p1ScorePanel;
	ScorePanel p2ScorePanel;
	private InputStreamReader networkInputStream;
	private OutputStream networkOutputStream;
	
	private final float PLAYER_HITTING_FORCE = 8;
	
	public GameLogicController(
		PlayerPuck p1Puck,
		PlayerPuck p2Puck,
		Puck mainPuck,
		ScorePanel p1ScorePanel,
		ScorePanel p2ScorePanel,
		Socket socket,
		InputStreamReader networkInputStream,
		OutputStream networkOutputStream
	) {
		this.p1Puck = p1Puck;
		this.p2Puck = p2Puck;
		this.mainPuck = mainPuck;
		this.p1ScorePanel = p1ScorePanel;
		this.p2ScorePanel = p2ScorePanel;
		this.networkInputStream = networkInputStream;
		this.networkOutputStream = networkOutputStream;
	}
	
	public void update() {	
		if (GameState.isPlayer1) {
			Vector4f networkPosData = receiveNetworkPositionData();
			if (networkPosData.x != -1 && networkPosData.y != -1) {
				networkPosData.y = Window.get().getHeight() - networkPosData.y;
				p2Puck.setPosition(new Vector2f(networkPosData.x, networkPosData.y));
			}
			
			checkForCollisionDetections();
			sendNetworkPositionData(p1Puck.getPosition(), mainPuck.getPosition());
			
			extraPlayerPuckSanitationFromNetworkDisturbance(p2Puck, true);
		}
		else {
			sendNetworkPositionData(p1Puck.getPosition(), new Vector2f(-1, -1));
			
			Vector4f networkPosData = receiveNetworkPositionData();
			if (networkPosData.x != -1 && networkPosData.y != -1) {
				networkPosData.y = Window.get().getHeight() - networkPosData.y;
				p2Puck.setPosition(new Vector2f(networkPosData.x, networkPosData.y));
			}
			if (networkPosData.z != -1 && networkPosData.w != -1) {
				networkPosData.w = Window.get().getHeight() - networkPosData.w;
				mainPuck.setPosition(new Vector2f(networkPosData.z, networkPosData.w));
			}
			
			extraPlayerPuckSanitationFromNetworkDisturbance(p2Puck, true);
		}
	}
	
	private void sendNetworkPositionData(Vector2f pos1, Vector2f pos2) {
		String posStr = String.valueOf(pos1.x) + "," + String.valueOf(pos1.y) + "," + String.valueOf(pos2.x) + "," + String.valueOf(pos2.y);
		try {
			networkOutputStream.write(posStr.getBytes());
			networkOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendNetworkScoreData(int p1Score, int p2Score) {
		String data = "score:" + String.valueOf(p1Score) + "," + String.valueOf(p2Score);
		try {
			networkOutputStream.write(data.getBytes());
			networkOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Vector4f receiveNetworkPositionData() {
		Vector4f result = new Vector4f(-1, -1, -1, -1);
		
		char[] posDataBuffer = new char[32];
		try {
			networkInputStream.read(posDataBuffer);
			List<String> posData = Arrays.asList(String.valueOf(posDataBuffer).split(","));
			try {
				result.x = Float.valueOf(posData.get(0));
				result.y = Float.valueOf(posData.get(1));
				result.z = Float.valueOf(posData.get(2));
				result.w = Float.valueOf(posData.get(3));
			}
			catch (Exception e1) {
				String possibleScoreStr = String.valueOf(posDataBuffer);
				if (possibleScoreStr.startsWith("score:")) {
					p1ScorePanel.setTexture(Integer.valueOf(possibleScoreStr.substring(8, 9)).intValue());
					p2ScorePanel.setTexture(Integer.valueOf(possibleScoreStr.substring(6, 7)).intValue());
					
					GameState.player1Score = p1ScorePanel.getScore();
					GameState.player2Score = p2ScorePanel.getScore();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return result;
	}
		
	private void checkForCollisionDetections() {
		if (BoundingCircle.colliding(p1Puck.getBoundingCircle(), mainPuck.getBoundingCircle())) {
			float dx = mainPuck.getPosition().x - p1Puck.getPosition().x;
			float dy = mainPuck.getPosition().y - p1Puck.getPosition().y;
			
			mainPuck.setVelocity(new Vector2f(dx * PLAYER_HITTING_FORCE / 100.0f, dy * PLAYER_HITTING_FORCE / 100.0f));
		}
		
		if (BoundingCircle.colliding(p2Puck.getBoundingCircle(), mainPuck.getBoundingCircle())) {
			float dx = mainPuck.getPosition().x - p2Puck.getPosition().x;
			float dy = mainPuck.getPosition().y - p2Puck.getPosition().y;
			
			mainPuck.setVelocity(new Vector2f(dx * PLAYER_HITTING_FORCE / 100.0f, dy * PLAYER_HITTING_FORCE / 100.0f));
		}
		
		if (mainPuck.getPosition().y > (float)Window.get().getHeight()) {
			// P1 Scored a Goal
			mainPuck.setPosition(new Vector2f(Window.get().getWidth() / 2, Window.get().getHeight() / 2));
			mainPuck.setVelocity(new Vector2f());
			GameState.player1Score++;
			
			p1ScorePanel.setTexture(GameState.player1Score);
			sendNetworkScoreData(GameState.player1Score, GameState.player2Score);
		}
		
		if (mainPuck.getPosition().y < 0) {
			// P2 Scored a Goal
			mainPuck.setPosition(new Vector2f(Window.get().getWidth() / 2, Window.get().getHeight() / 2));
			mainPuck.setVelocity(new Vector2f());
			GameState.player2Score++;
			
			p2ScorePanel.setTexture(GameState.player2Score);
			sendNetworkScoreData(GameState.player1Score, GameState.player2Score);
		}
	}
	
	private void extraPlayerPuckSanitationFromNetworkDisturbance(PlayerPuck puck, boolean invertYPos) {
		float puckRadius = puck.getSize().x / 2.0f;
		float screenWidth = (float)Window.get().getWidth();
		float midlineYCoord = (float)Window.get().getHeight() / 2.0f;
		Vector2f puckPosition = puck.getPosition();
		
		if (puckPosition.x > screenWidth - puckRadius)
			puckPosition.x = screenWidth - puckRadius;
		else if (puckPosition.x < puckRadius)
			puckPosition.x = puckRadius;
		
		if (!invertYPos) {
			if (puckPosition.y > midlineYCoord - puckRadius)
				puckPosition.y = midlineYCoord - puckRadius;
			else if (puckPosition.y < puckRadius)
				puckPosition.y = puckRadius;
		}
		else {
			if (puckPosition.y < midlineYCoord + puckRadius)
				puckPosition.y = midlineYCoord + puckRadius;
			else if (puckPosition.y > Window.get().getHeight() - puckRadius)
				puckPosition.y = Window.get().getHeight() - puckRadius;
		}
		
		puck.setPosition(puckPosition);
	}
}
