package com.brandy.ants;

public class MovementException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovementException(){
		super("You can't move that way");
	}

}
