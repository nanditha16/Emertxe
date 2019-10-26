package com.example.snakeAndLadder;

import com.example.snakeAndLadder.Exceptions.InitialSetupException;
import com.example.snakeAndLadder.GameSetup.Square;

public final class Player {
	private Square square = null ;
	private String name ;
	
	
	public Player ( String strname ) {
		name = strname ;
	}
	
	public int position () {
		return square.getPosition();
	}
	
	public boolean wins() {
		return square.isLastSquare();
	}
	
	public void moveForward ( int moves ) throws InitialSetupException {
		assert moves >0 : "non - positive moves ";
		square.leave( this );
		square = square.makeValidMove( moves );
		square.enter( this );
	}
	
	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
