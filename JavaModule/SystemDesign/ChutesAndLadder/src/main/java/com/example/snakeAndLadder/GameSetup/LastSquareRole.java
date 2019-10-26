package com.example.snakeAndLadder.GameSetup;

/**
 * LastSquareRole 
 * 	it is a LastSquare
 * @author nanditha
 *
 */
public final class LastSquareRole extends SquareRole {

	public LastSquareRole ( Square s) {
		super (s);
	}

	@Override
	public boolean isLastSquare() {
		return true ;
	}
 }
