package edu.cmu.cs.cs214.hw4.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Point;

public abstract class SpecialTile 
{
	private Player owner;
	private int price;
	private boolean relocatable;
	public static final int NEGATIVEPOINT = 0;
	public static final int BOMBERMAN = 1;
	public static final int STEALSCORE = 2;
	public SpecialTile(Player p, int price) 
	{
		owner = p;
		relocatable = true;
		this.price = price;
	}
	public void setRelocatable(boolean b)
	{
		relocatable = b;
	}
	public boolean isRelocatble()
	{
		return relocatable;
	}
	/**
	 * get the name of the special tile
	 * @return name
	 */
	public abstract String getName();
	/**
	 * @return its owner
	 */
	public Player getOwner()
	{
		return owner;
	}
	public abstract int applyEffect(Game game,Point p,int score);
	/**
	 * @return its price
	 */
	public int getPrice()
	{
		return price;
	}
}
