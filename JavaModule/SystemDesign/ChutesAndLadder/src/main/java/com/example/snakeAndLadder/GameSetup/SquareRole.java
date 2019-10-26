package com.example.snakeAndLadder.GameSetup;

import com.example.snakeAndLadder.Player;
import com.example.snakeAndLadder.Exceptions.InitialSetupException;

/**
 * SquareRole behaviors:
 * 
 * 1. player to enter and leave the square
 * 2. check if It can be occupied, if player is present or not
 * 2. check if It can be Mid squares or first or last
 * 3. moveAndLand : To find a valid initial square position to move upon die throw.
 * 	 i.e cannot be beyond the last quare
 * 4. landHereOrGoHome : Assumption : Each square can be occupied by only one player.
 * 	i.e if occupied, stay at firstSquare else move to new place
 */
public abstract class SquareRole {

	protected Square square = null;

	public SquareRole(Square s) {
		assert s != null : " Null square for square role ";
		square = s;
	}

	public boolean isOccupied() {
		return square.getPlayer() != null;
	}

	public boolean isFirstSquare() {
		return false;
	}

	public boolean isLastSquare() {
		return false;
	}

	public void enter(Player player) {
		square.setPlayer(player);
		player.setSquare(square);
	}

	public void leave(Player player) {
		square.setPlayer(null);
	}
	
	/*
	 * To find a valid square position to move upon die throw
	 * 
	 */
	public Square makeValidMove(int moves) throws InitialSetupException {
		int lastPosition = square.findLastSquare().getPosition();
		int presentPosition = square.getPosition();
		if (presentPosition + moves > lastPosition) {
			System.out.println(" Should go to " + (presentPosition + moves + 1) + " beyond last square "
					+ (lastPosition + 1) + " so don 't move ");
			return square;
		} else {
			System.out.print((square.getPosition() + 1) );
			return square.findRelativeSquare(moves).landHereOrGoHome();
		}
	}
	

	/*
	 * Assumption : Each square can be occupied by only one player. 
	 * 	if occupied, stay at firstSquare else move to new place
	 */
	public Square landHereOrGoHome() throws InitialSetupException {
		if (square.isOccupied()) {
			System.out.println("--> " + (square.getPosition() + 1) + "--OCCUPIED-->" + " (return to) 1");
		} else {
			System.out.println("--> "+ (square.getPosition() + 1));
		}
		return square.isOccupied() ? square.findFirstSquare() : square;
	}

	
}
