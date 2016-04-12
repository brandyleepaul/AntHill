package com.brandy.ants;

import java.awt.Point;

public class ChessBoard {
	
	private int[][] board;
	private int boardSize;

	
	//private ArrayList(Point) board;
	
	public ChessBoard(int boardSize){
		this.boardSize = boardSize;
		
		this.board = new int[boardSize][boardSize];
	}
	
	
	public int[][] zeroOutBoard(){
		
		for (int row = 0; row < this.boardSize; row ++)
            for (int col = 0; col < this.boardSize; col++)
            	this.board[row][col] = 0;
		return this.board;
	}
	
	public int[][] getBoard(){
		return this.board;
	}
	
	public void setPosition(Point p, int antId){
		//check validity 
		//if(r > this.rows || c > this.cols){
		//	System.out.println("Invalid Move for Ant: ant" + antId);
		//}
		
		this.board[(int) p.getX()][(int) p.getY()] = antId;
	}
	
	public void addOccupant(Point p, int antId){
		//check validity 
		//if(r > this.rows || c > this.cols){
		//	System.out.println("Invalid Move for Ant: ant" + antId);
		//}
		
		this.board[(int) p.getX()][(int) p.getY()] = antId;
	}
	
	public boolean checkForOccupants(Point p, int antId){
		//check validity 
		return this.board[(int) p.getX()][(int) p.getY()] == 0 ? false : true;
	}
	
	public boolean tryMove(Ant ant, Direction dir, boolean allowMoveBack) throws MovementException{
		System.out.println("Ant " + ant.getId() + " is moving " + dir.toString());
		Point p = ant.getP();
		int x = (int) p.getX();
		int y = (int) p.getY();
		boolean retVal = true;
		
		switch(dir){
		case UP:
			if(y > 0)
				ant.move(x, y - 1, allowMoveBack);
			else
				retVal = false;
			break;
		case DOWN:
			if(y < boardSize)
				ant.move(x, y + 1, allowMoveBack);
			else
				retVal = false;
			break;
		case LEFT:
			if(x > 0)
				ant.move(x-1, y, allowMoveBack);
			else
				retVal = false;
			break;
		case RIGHT:
			if(x < boardSize)
				ant.move(x+1, y, allowMoveBack);
			else
				retVal = false;
			break;
		case DIAG_UP_LEFT:
			if(x > 0 && y > 0)
				ant.move(x-1, y - 1, allowMoveBack);
			else
				retVal = false;
			break;
		case DIAG_UP_RIGHT:
			if(x < boardSize && y > 0)
				ant.move(x+1, y - 1, allowMoveBack);
			else
				retVal = false;
			break;
		case DIAG_DOWN_LEFT:
			if(x > 0 && y < boardSize)
				ant.move(x-1, y + 1, allowMoveBack);
			else
				retVal = false;
			break;
		case DIAG_DOWN_RIGHT:
			if(x < boardSize && y < boardSize)
				ant.move(x + 1, y + 1, allowMoveBack);
			else
				retVal = false;
			break;
		default:
			System.out.println("Invalid direction");
			retVal = false;
			break;
		}
		return retVal;
		
	}


}
