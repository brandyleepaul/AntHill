package com.brandy.ants;

import java.awt.Point;

public class ChessQuickStart {

	public static final int BOARD_SIZE = 4;

	public static void main(String[] args) {

		ChessBoard board = new ChessBoard(BOARD_SIZE);
		board.zeroOutBoard();

		Ant topCornerAnt = new Ant(new Point(0, 0), 1);
		Ant bottomCornerAnt = new Ant(new Point(BOARD_SIZE - 1, BOARD_SIZE - 1), 2);

		int timeToMeet;
		try {
			
			
			//One way to meet
			timeToMeet = meetInTheCorner(board, topCornerAnt, bottomCornerAnt);
			printMeetTime(timeToMeet, "corner of the board");
			resetAnts(topCornerAnt, bottomCornerAnt);

			//Another way to meet, and then cross paths
			timeToMeet = meetInTheMiddleAndCrossPaths(board, topCornerAnt, bottomCornerAnt);
			printMeetTime(timeToMeet, "middle of the board");
			resetAnts(topCornerAnt, bottomCornerAnt);

			//moving diagonally
			timeToMeet = moveDiag(board, topCornerAnt, bottomCornerAnt);
			printMeetTime(timeToMeet, "Diagonal");
			resetAnts(topCornerAnt, bottomCornerAnt);
			
			//can't go back
			timeToMeet = meetInTheCorner(board, topCornerAnt, bottomCornerAnt);
			printMeetTime(timeToMeet, "corner of the board, now go back to where you started");
			sendAntsBack(board, topCornerAnt, bottomCornerAnt, false);
			
		} catch (MovementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			//let's try this instead
			resetAnts(topCornerAnt, bottomCornerAnt);
			
		}

	}

	public static int meetInTheMiddleAndCrossPaths(ChessBoard board, Ant ant1, Ant ant2) throws MovementException {
		boolean aCanMove = true;
		boolean bCanMove = true;
		int time = 0;
		int timeToMeet = 0;

		while (aCanMove && bCanMove) {
			// check if they're already in the same place, if not move them
			time++;

			if (time % 2 != 0) {
				// move ant 1 down & right
				aCanMove = board.tryMove(ant1, Direction.DOWN, true);
				System.out.println("Ant 1 is here: " + ant1.getP());

				if (ant1.getP().equals(ant2.getPrev()) || ant2.getP().equals(ant1.getPrev())) {
					printCrossedPaths(time * 10, "middle of the board");
				}

				// move ant 2 left
				bCanMove = board.tryMove(ant2, Direction.LEFT, true);
				System.out.println("Ant 2 is here: " + ant2.getP());
			} else {

				aCanMove = board.tryMove(ant1, Direction.RIGHT, true);
				System.out.println("Ant 1 is here: " + ant1.getP());

				if (ant1.getP().equals(ant2.getPrev()) || ant2.getP().equals(ant1.getPrev())) {
					printCrossedPaths(time * 10, "middle of the board");
				}

				// move ant 2 left
				bCanMove = board.tryMove(ant2, Direction.UP, true);
				System.out.println("Ant 2 is here: " + ant2.getP());
			}

			if (ant1.getP().equals(ant2.getP())) {
				timeToMeet = time * 10;
			}
		}

		return timeToMeet;
	}

	// start out assuming Ant 1 is in top left corner and Ant 2 is in bottom
	// right
	public static int moveDiag(ChessBoard board, Ant ant1, Ant ant2) throws MovementException {

		boolean aCanMove = true;
		boolean bCanMove = true;
		int time = 0;
		int timeToMeet = 0;

		while (aCanMove || bCanMove) {
			time++;

			// move ant 1 down and right
			aCanMove = board.tryMove(ant1, Direction.DIAG_DOWN_RIGHT, true);

			if (ant1.getP().equals(ant2.getPrev()) || ant2.getP().equals(ant1.getPrev())) {
				printCrossedPaths(time, "diagonal");
			}
			// move ant 2 left and up
			bCanMove = board.tryMove(ant2, Direction.DIAG_UP_LEFT, true);
			if (ant1.getP().equals(ant2.getPrev()) || ant2.getP().equals(ant1.getPrev())) {
				printCrossedPaths(time, "diagonal");
			}
			if (ant1.getP().equals(ant2.getP())) {
				timeToMeet = time * 10;
			}

		}

		return timeToMeet;
	}

	// start out assuming Ant 1 is in top left corner and Ant 2 is in bottom
	// right
	public static int meetInTheCorner(ChessBoard board, Ant ant1, Ant ant2) throws MovementException {

		boolean aCanMove = true;
		boolean bCanMove = true;
		int time = 0;

		while (!(ant1.getP().equals(ant2.getP()))) {
			// move ant 1 down
			aCanMove = board.tryMove(ant1, Direction.DOWN, true);
			// move ant 2 left
			bCanMove = board.tryMove(ant2, Direction.LEFT, true);
			time += 10;

			if (!aCanMove || !bCanMove) {
				System.out.println("Somebody's stuck ");
				break;
			}
		}
		return time;
	}
	
	
	//Send them back where they came from......will fail if they're  not allowed to go back where they came from
	public static void sendAntsBack(ChessBoard board, Ant ant1, Ant ant2, boolean allowBackwards) throws MovementException {
		boolean aCanMove = true;
		boolean bCanMove = true;

		while (aCanMove || bCanMove) {
			// move ant 1 down
			aCanMove = board.tryMove(ant1, Direction.UP, allowBackwards);
			// move ant 2 left
			bCanMove = board.tryMove(ant2, Direction.RIGHT, allowBackwards);
			
		}
	}

	public static void resetAnts(Ant ant1, Ant ant2) {
		System.out.println("Moving ants back to start");
		// move back to top left
		ant1.move(0, 0);
		// move to bottom right
		ant2.move(BOARD_SIZE - 1, BOARD_SIZE - 1);
	}

	public static void printMeetTime(int time, String location) {
		System.out.println("**************************************");
		if (time == 0) {
			System.out.println("Did not meet successfully on the " + location);
		} else {
			System.out.println("Met in " + (time) + " seconds on the " + location);
		}
		System.out.println("**************************************");
	}

	public static void printCrossedPaths(int time, String location) {
		System.out.println("**************************************");
		System.out.println("Crossed paths in " + (time) + " seconds on the " + location);
		System.out.println("**************************************");
	}

}
