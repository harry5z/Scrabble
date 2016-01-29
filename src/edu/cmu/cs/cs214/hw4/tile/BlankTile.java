package edu.cmu.cs.cs214.hw4.tile;

public class BlankTile extends Tile
{
	private boolean hasLetter;
	public BlankTile()
	{
		super(0);
		hasLetter = false;
	}
	public boolean hasLetter()
	{
		return hasLetter;
	}
	/**
	 * set blank tile's letter
	 * @param l
	 */
	@Override
	public void setLetter(char l)
	{
		super.setLetter(l);
		hasLetter = true;
	}
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof BlankTile;
	}
	@Override
	public int hashCode()
	{
		return getValue();
	}
}
