package edu.cmu.cs.cs214.hw4.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Point;

public class NegativePointTile extends SpecialTile
{
	private static final int PRICE = 10;
	public NegativePointTile(Player p) 
	{
		super(p,PRICE);
	}
	@Override
	public String getName()
	{
		return "Negative Point";
	}
	/**
	 * guarantees a negative score for player p
	 * @param p
	 * @param score
	 */
	@Override
	public int applyEffect(Game game, Point p,int score)
	{
		if(score < 0)
			return score;
		else
			return -score;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof NegativePointTile)
			return getOwner() == ((NegativePointTile)obj).getOwner();
		else
			return false;
	}
	@Override
	public int hashCode()
	{
		return PRICE;
	}
}
