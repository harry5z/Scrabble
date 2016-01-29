package edu.cmu.cs.cs214.hw4.core;

public class Point 
{
	private int xPos;
	private int yPos;
	public Point(int x, int y)
	{
		xPos = x;
		yPos = y;
	}
	public int getX()
	{
		return xPos;
	}
	public int getY()
	{
		return yPos;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Point))
			return false;
		Point p = (Point)obj;
		return p.getX() == xPos && p.getY() == yPos;
	}
	@Override
	public int hashCode()
	{
		return xPos * (yPos+1);
	}
}
