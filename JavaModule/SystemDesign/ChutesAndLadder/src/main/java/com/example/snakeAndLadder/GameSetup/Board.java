package com.example.snakeAndLadder.GameSetup;

import java.util.ArrayList;

import com.example.snakeAndLadder.Exceptions.InitialSetupException;

public final class Board {

	private ArrayList<Square> squares = new ArrayList<Square>();
	private static int MINNUMSQUARES = 10;

	public Board(int numSquares, int[][] ladders, int[][] snakes) throws InitialSetupException {

		if (numSquares < MINNUMSQUARES) {
			throw new InitialSetupException(" There must be at least " + MINNUMSQUARES + " squares ");
		}

		makeSquares(numSquares);
		makeLadders(ladders);
		makeSnakes(snakes);
	}

	/*
	 * Setup the Squares in the board as ArrayList<Square>
	 */
	private void makeSquares(int numSquares) throws InitialSetupException {
		System.out.println("<<INITIAL BOARD SETUP>>"+ " There are " + numSquares + " squares ");

		for (int position = 0; position < numSquares; position++) {
			Square square = new Square(position, this);
			squares.add(square);
			square.setSquareRole(new RegularSquareRole(square));
		}
		firstSquare().setSquareRole(new FirstSquareRole(firstSquare()));
		lastSquare().setSquareRole(new LastSquareRole(lastSquare()));
	}

	public Square firstSquare() {
		return squares.get(0);
	}

	public Square lastSquare() {
		return squares.get(squares.size() - 1);
	}

	public Square findSquare(int position) throws InitialSetupException {
		if ((position < 0) && (position < numberOfSquares())) {
			throw new InitialSetupException(" inexistent square ");
		}
		//assert (position > 0) && (position < numberOfSquares()) : " inexistent square ";
		return squares.get(position);
	}

	private int numberOfSquares() {
		assert !squares.isEmpty();
		return squares.size();
	}

	private void makeSnakes(int[][] snakes) {
		for (int i = 0; i < snakes.length; i++) {
			//should have fromPosition, toPosition
			assert snakes[i].length == 2;

			int fromPosition = snakes[i][0] - 1;
			int toPosition = snakes[i][1] - 1;
			int transport = toPosition - fromPosition;

			assert transport < 0 : "In snake , destination after origin ";
			assert (toPosition > 0) && (toPosition < numberOfSquares() - 1);
			assert (fromPosition < numberOfSquares() - 1) && (fromPosition > 0);

			System.out.println("<<INITIAL BOARD SETUP>>" +" snake from " + (fromPosition + 1) + " to " + (toPosition + 1));

			Square snakeSquare = squares.get(fromPosition);
			//shift negatively
			snakeSquare.setSquareRole(new SnakeRole(snakeSquare, transport));
		}
	}

	private void makeLadders(int[][] ladders) {
		for (int i = 0; i < ladders.length; i++) {
			assert ladders[i].length == 2;

			int fromPosition = ladders[i][0] - 1;
			int toPosition = ladders[i][1] - 1;

			//get the shift count
			int transport = toPosition - fromPosition;

			assert transport > 0 : "In ladder , origin after destination ";
			assert (toPosition < numberOfSquares()) && (toPosition > 0);
			assert (fromPosition > 0) && (fromPosition < numberOfSquares());

			System.out.println("<<INITIAL BOARD SETUP>>"+ " ladder from " + (fromPosition + 1) + " to " + (toPosition + 1));

			//current position
			Square ladderSquare = squares.get(fromPosition);
			// do the transport positive shift
			ladderSquare.setSquareRole(new LadderRole(ladderSquare, transport));
		}
	}
}