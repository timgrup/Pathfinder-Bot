package de.vitbund.vitmaze.players;

import java.nio.file.Path;

import de.vitbund.vitmaze.players.Vector2.Direction;

public class ActionHandler {
	
	private InputHandler inputHandler;
	
	public ActionHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	public boolean isOK() {
		String lar = inputHandler.getLastActionResult();
		if (lar.equals("OK")) {
			return true;
		}
		return false;
	}

	public boolean moveSuccess(Direction direction) {
		String lar = inputHandler.getLastActionResult();
		String check = "OK " + direction.toString().toUpperCase();
		if (lar.equals(check) && !PathfinderBot.player.lastActionWasKick) {
			return true;
		}
		return false;
	}
	
	public boolean isBlocked() {
		String lar = inputHandler.getLastActionResult();
		if(lar.equals("NOK BLOCKED")) {
			return true;
		}
		return false;
	}
	
	public boolean isTakeable() {
		String lar = inputHandler.getLastActionResult();
		if(lar.equals("NOK EMPTY")) {
			return false;
		}
		return true;
	}

}
