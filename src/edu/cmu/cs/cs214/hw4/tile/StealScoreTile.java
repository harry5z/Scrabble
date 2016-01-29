package edu.cmu.cs.cs214.hw4.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Point;

public class StealScoreTile extends SpecialTile
{
	private static final int PRICE = 10;
	public StealScoreTile(Player p) 
	{
		super(p,PRICE);
	}
	/**
	 * if score is negative, give it to player p
	 * if score is positive, give it to owner
	 * @param p
	 * @param score
	 */
	public int applyEffect(Game game,Point p,int score)
	{
		if(score < 0)
			game.getCurrentPlayer().addScore(score);
		else
			getOwner().addScore(score);
		return 0;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof StealScoreTile)
			return getOwner() == ((StealScoreTile)obj).getOwner();
		else
			return false;
	}
	@Override
	public int hashCode() 
	{
		return PRICE;
	}
	@Override
	public String getName()
	{
		return "Steal Score";
	}
}
