package edu.cmu.cs.cs214.hw4.square;

import edu.cmu.cs.cs214.hw4.core.Point;
import edu.cmu.cs.cs214.hw4.tile.SpecialTile;
import edu.cmu.cs.cs214.hw4.tile.Tile;

public abstract class Square
{
	private Tile myTile;
	private boolean empty;
	private Point myPos;
	private SpecialTile special;
	private boolean hasSpecial;
	
	public Square(Point p)
	{
		empty = true;
		hasSpecial = false;
		myPos = p;
	}
	/**
	 * returns the position of this square on board
	 * @return myPos
	 */
	public Point getPosition()
	{
		return myPos;
	}
	/**
	 * set a special tile on the square, 
	 * overriding previous special tile (if any)
	 * but does not override your own special tile
	 * @param t : special tile to be inserted
	 * @return true on success, false otherwise
	 */
	public boolean setSpecialTile(SpecialTile t)
	{
		//you don't want to override your own
		if(hasSpecial && special.getOwner() == t.getOwner())
			return false;
		special = t;
		hasSpecial = true;
		return true;		
	}
	/**
	 * set a tile on the square,
	 * return true on success, false on failure
	 * (already has a tile)
	 * @param t
	 * @return true on success, false on failure
	 */
	public boolean setTile(Tile t) 
	{
		if(!empty)
			return false;
		myTile = t;
		empty = false;
		return true;
	}
	/**
	 * returns true if a special tile is present
	 * @return hasSpecial
	 */
	public boolean hasSpecialTile()
	{
		return hasSpecial;
	}
	/**
	 * expose the special tile
	 * @return current special tile
	 */
	public SpecialTile getSpecialTile()
	{
		return special;
	}
	/**
	 * remove and return the special tile
	 * @return current special tile
	 */
	public SpecialTile removeSpecialTile()
	{
		SpecialTile t = special;
		special = null;
		hasSpecial = false;
		return t;
	}
	/**
	 * remove and return the tile
	 * @return current tile
	 */
	public Tile removeTile()
	{
		if(empty)
			return null;
		Tile t = myTile;
		myTile = null;
		empty = true;
		return t;
	}
	/**
	 * expose its tile
	 * @return current tile
	 */
	public Tile getTile()
	{
		return myTile;
	}
	/**
	 * return true if square does not have a tile
	 * false if it does
	 * @return empty
	 */
	public boolean isEmpty()
	{
		return empty;
	}
	/**
	 * return the total score on this square,
	 * possibly including double/triple letter score
	 * @return tile value
	 */
	public int getScore()
	{
		return myTile.getValue();
	}
}
