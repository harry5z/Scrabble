package edu.cmu.cs.cs214.hw4.core;

import java.util.Random;
import edu.cmu.cs.cs214.hw4.tile.*;

public class TileBag 
{
	private Tile[] tiles;
	private static final int MAX = 100;
	private int tileCount;
	public TileBag()
	{
		tiles = new Tile[MAX];
		tileCount = 0;
		init();
	}
	private void init()
	{
		for(int i = 1;i <= 12;i++)
		{
			addTile(new NormalTile('e',1));
			if(i <= 9)
			{
				addTile(new NormalTile('a',1));
				addTile(new NormalTile('i',1));
			}
			if(i <= 8)
			{
				addTile(new NormalTile('o',1));
			}
			if(i <= 6)
			{
				addTile(new NormalTile('n',1));
				addTile(new NormalTile('r',1));
				addTile(new NormalTile('t',1));
			}
			if(i <= 4)
			{
				addTile(new NormalTile('l',1));
				addTile(new NormalTile('s',1));
				addTile(new NormalTile('u',1));
				addTile(new NormalTile('d',2));
			}
			if(i <= 3)
			{
				addTile(new NormalTile('g',2));
			}
			if(i <= 2)
			{
				addTile(new NormalTile('b',3));
				addTile(new NormalTile('c',3));
				addTile(new NormalTile('m',3));
				addTile(new NormalTile('p',3));
				addTile(new NormalTile('f',4));
				addTile(new NormalTile('h',4));
				addTile(new NormalTile('v',4));
				addTile(new NormalTile('w',4));
				addTile(new NormalTile('y',4));
				addTile(new BlankTile());
			}
			if(i <= 1)
			{
				addTile(new NormalTile('k',5));
				addTile(new NormalTile('j',8));
				addTile(new NormalTile('x',8));
				addTile(new NormalTile('q',10));
				addTile(new NormalTile('z',10));
			}
		}
		reorder();
	}
	private void reorder()
	{
		int remaining = tileCount;
		Tile[] newList = new Tile[MAX];
		Random r = new Random();
		for(int i = 0; i < tileCount;i++)
		{
			int loc = r.nextInt(remaining);//take a random
			newList[i] = tiles[loc];
			for(int rest = loc;rest < remaining-1;rest++)
				tiles[rest] = tiles[rest+1];//move the rest up
			remaining--;
		}
		tiles = newList;
	}
	private void addTile(Tile t)
	{
		tiles[tileCount] = t;
		tileCount++;		
	}
	/**
	 * add a list of tiles back to the bag,
	 * and randomize the bag again.
	 * @param tList
	 */
	public void recycleTiles(Tile[] tList)
	{
		for(Tile t : tList)
			addTile(t);
		reorder();
	}
	/**
	 * take as many tile as possible
	 * @param num
	 * @return tileList where 0 <= length <= num
	 */
	public Tile[] takeTiles(int num)
	{
		if(num > tileCount)//more than I have
			num = tileCount;
		Tile[] ret = new Tile[num];
		for(int i = num-1; i >= 0;i--)
		{
			tileCount--;
			ret[i] = tiles[tileCount];
		}
		return ret;
	}
	/**
	 * @return tileCount
	 */
	public int getTileCount()
	{
		return tileCount;
	}
}
