package com.brandy.ants;

import java.awt.Point;

public class Ant {
	
	
	private int id;
	private Point p;
	private Point prev;
	
	public Point getPrev() {
		return prev;
	}

	public void setPrev(Point prev) {
		this.prev = prev;
	}

	Ant(Point p, int id){
		this.setP(p);
		this.id = id;
	}
	
	public void move(int x, int y){
			prev = (Point) p.clone();
			p.move(x, y);
	}
	
	
	public void move(int x, int y, boolean canMoveBack) throws MovementException {
		if (!canMoveBack) {
			if (prev != null && prev.equals(new Point(x, y)))
			{
				throw new MovementException();
			}
		} else {
			move(x,y);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}
	


}
