package com.example.snakeAndLadder.GameSetup;

import com.example.snakeAndLadder.Player;
import com.example.snakeAndLadder.Exceptions.InitialSetupException;

/**
 * Each square is characterized by:
 * position in the board
 * Board : 
 *  	findFirstSquare and findLastSquare
 *  	findRelativeSquare - the next square to jump to based on the shift we get from die from the board
 *  
 *  player :
 *  	present or not 
 *  
 *  squareRole - of type SquareRole
 *  	 RegularSquareRole, 
 *  	 LastSquareRole, 
 *  	 FirstSquareRole (can contain many players),
 *  	 LadderRole (move die throw position to ladder's (shift/ transport valued)+ position, 
 *  	 SnakeRole (move die throw position to snakes's (shift/ transport valued)+ position,
 *   
 *  	player to enter and leave the square
 *  	check if It can be occupied, if player is present or not
 * 		check if It can be last
 * 		To find a valid square position to move upon die throw and if the square is occupied or not.
 * 
 */
public class Square {
	private Board board = null;
	private Player player = null;
	private int position;
	private SquareRole squareRole = null;

	public Square(int pos, Board b) {
		
		assert pos >= 0 : " Square number must be positive or zero ";
		position = pos;
		board = b;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		player = p;
	}

	public int getPosition() {
		return position;
	}

	public void setSquareRole(SquareRole sr) {
		assert sr != null;
		squareRole = sr;
	}

	public boolean isOccupied() {
		return squareRole.isOccupied();
	}

	public boolean isLastSquare() {
		return squareRole.isLastSquare();
	}

	public Square makeValidMove(int moves) throws InitialSetupException {
		return squareRole.makeValidMove(moves);
	}

	public Square landHereOrGoHome() throws InitialSetupException {
		return squareRole.landHereOrGoHome();

	}

	public void enter(Player p) {
		squareRole.enter(p);
	}

	public void leave(Player p) {
		squareRole.leave(p);
	}

	public Square findRelativeSquare(int shift) throws InitialSetupException {
		return board.findSquare(position + shift);
	}

	public Square findFirstSquare() {
		return board.firstSquare();
	}

	public Square findLastSquare() {
		return board.lastSquare();
	}
}
