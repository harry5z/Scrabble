package edu.cmu.cs.cs214.hw4.tile;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Point;

public class BombermanTile extends SpecialTile
{
	private static final int PRICE = 10;
	public BombermanTile(Player p) 
	{
		super(p,PRICE);
	}
	/**
	 * return tiles removed from board for recycling
	 * @param b
	 * @param p
	 * @return tiles blown off from board
	 */
	@Override
	public int applyEffect(Game game, Point p, int score)
	{
		Tile[] temp = new Tile[2*Board.LENGTH];
		int count = 0;
		int x = p.getX();
		int y = p.getY();
		//remove horizontal
		for(int i = 0;i < Board.LENGTH;i++)
		{
			Tile t = game.getBoard().removeTileFrom(new Point(i,y));
			if(t != null)
			{
				temp[count] = t;
				count++;
			}
		}
		//remove vertical
		for(int j = 0;j < Board.LENGTH;j++)
		{
			Tile t = game.getBoard().removeTileFrom(new Point(x,j));
			if(t != null)
			{
				temp[count] = t;
				count++;
			}
		}
		Tile[] ret = new Tile[count];//resize
		for(int i = 0;i < count;i++)
			ret[i] = temp[i];
		game.getBag().recycleTiles(ret);
		return 0;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof BombermanTile)
			return getOwner() == ((BombermanTile)obj).getOwner();
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
		return "Bomberman";
	}
}
