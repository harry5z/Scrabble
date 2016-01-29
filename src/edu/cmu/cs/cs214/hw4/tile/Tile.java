package edu.cmu.cs.cs214.hw4.tile;

public abstract class Tile 
{
	private char letter;
	private int value;
	public Tile (char l, int v)
	{
		letter = l;
		value = v;
	}
	protected Tile(int v)//delay setting up letter for blank tile
	{
		value = v;
	}
	public final int getValue()
	{
		return value;
	}
	protected void setLetter(char l)//for blank tile only
	{
		letter = l;
	}
	public char getLetter()
	{
		return letter;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Tile)
		{
			Tile t = (Tile)obj;
			return letter == t.getLetter() && value == t.getValue();
		}
		else
			return false; 
	}
	@Override
	public int hashCode()
	{
		return value;
	}
}
