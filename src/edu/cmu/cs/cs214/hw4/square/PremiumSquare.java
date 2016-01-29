package edu.cmu.cs.cs214.hw4.square;

import edu.cmu.cs.cs214.hw4.core.Point;

public abstract class PremiumSquare extends Square
{
	private int multiplier;
	private boolean used;
	
	public PremiumSquare(Point p,int m) 
	{
		super(p);
		used = false;
		multiplier = m;
	}
	public int getMultiplier()
	{
		return multiplier;
	}
	/**
	 * can be used only once
	 * @return used
	 */
	public boolean isUsed()
	{
		return used;
	}
	protected void setUsed()
	{
		used = true;
	}

}
