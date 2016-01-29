package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.tile.SpecialTile;
import edu.cmu.cs.cs214.hw4.tile.Tile;

public class Player 
{
	public static final int MAX_TILES = 7;
	public static final int MAX_SPECIAL_TILES = 5;
	private Tile[] myTiles;
	private int tileCount;
	private int ID;
	private ArrayList<SpecialTile> specialTiles;
	private ArrayList<SpecialTile> specialTilesOnBoard;
	private int score;
	
	public Player(int id)
	{
		myTiles = new Tile[MAX_TILES];
		tileCount = 0;
		ID = id;
		specialTiles = new ArrayList<SpecialTile>(MAX_SPECIAL_TILES);
		specialTilesOnBoard = new ArrayList<SpecialTile>();
		score = 0;
	}
	/**
	 * try to purchase a special tile,
	 * return true on success, false on failure
	 * (too many special tiles or not enough score)
	 * @return true on success, false on failure
	 */
	public boolean purchaseSpecialTile(SpecialTile s)
	{
		if(getSpecialTileCount() == MAX_SPECIAL_TILES)
			return false;
		int price = s.getPrice();
		if(score < price)
			return false;
		score -= price;
		specialTiles.add(s);
		return true;
	}
	/**
	 * add the score of the turn
	 * @param amount
	 */
	public void addScore(int amount)
	{
		score += amount;
	}
	/**
	 * return current score
	 * @return score
	 */
	public int getScore()
	{
		return score;
	}
	/**
	 * return an array of current special tiles
	 * @return a copy of specialTiles
	 */
	public SpecialTile[] getSpecialTiles()
	{
		SpecialTile[] temp = new SpecialTile[specialTiles.size()];
		int c = 0;
		for(SpecialTile t : specialTiles)
		{
			temp[c] = t;
			c++;
		}
		return temp;
	}
	/**
	 * show number of tile on rack
	 * @return tileCount
	 */
	public int getTileCount()
	{
		return tileCount;
	}
	/**
	 * show tiles on rack
	 * @return a copy of myTiles
	 */
	public Tile[] getTiles()
	{
		Tile[] temp = new Tile[tileCount];
		int c = 0;
		for(Tile t : myTiles)
		{
			if(t != null)
			{
				temp[c] = t;
				c++;
			}
		}
		return temp;
	}
	/**
	 * remove special tile t from collection
	 * @param t
	 */
	public void removeSpecialTile(SpecialTile t)
	{
		if(getSpecialTileCount() == 0)//shouldn't reach here
			return;
		specialTiles.remove(t);
	}
	public void useSpecialTile(SpecialTile st)
	{
		specialTilesOnBoard.remove(st);
	}
	private int getSpecialTileCount()
	{
		return specialTiles.size() + specialTilesOnBoard.size();
	}
	/**
	 * put a special tile back to collection
	 * @param t
	 */
	public void addSpecialTile(SpecialTile t)
	{
		if(getSpecialTileCount() > MAX_SPECIAL_TILES)
			return;
		specialTiles.add(t);

	}
	public void placeSpecialTileOnBoard(SpecialTile t)
	{
		specialTilesOnBoard.add(t);
	}
	public ArrayList<SpecialTile> getSpecialTilesOnBoard()
	{
		return specialTilesOnBoard;
	}
	public void removeSpecialTileFromBoard(SpecialTile st)
	{
		specialTilesOnBoard.remove(st);
	}
	/**
	 * add a list of tiles
	 * @param tiles
	 */
	public void addTiles(Tile[] tiles)
	{
		for(Tile t : tiles)
			addTile(t);
	}
	/**
	 * add a single tile
	 * @param t
	 */
	public void addTile(Tile t)
	{
		for(int i = 0;i < MAX_TILES; i++)
		{
			if(myTiles[i] == null)//empty space
			{
				myTiles[i] = t;
				tileCount++;
				return;
			}
		}
	}
	/**
	 * remove tile t from rack
	 * @param t
	 * @return
	 */
	public void removeTile(Tile t)
	{
		if(tileCount == 0)//test, shouldn't reach here
			System.err.println("removing from empty");
		for(int i = 0; i < MAX_TILES;i++)
		{
			if(myTiles[i] == t)//match
			{
				myTiles[i] = null;
				tileCount--;
				return;
			}
		}
	}
	/**
	 * show player ID
	 * @return ID
	 */
	public int getIdentity()
	{
		return ID;
	}
}
