package edu.cmu.cs.cs214.hw4.core;


import edu.cmu.cs.cs214.hw4.square.*;
import edu.cmu.cs.cs214.hw4.tile.*;

public class Board 
{
	private Square[][] grid;
	public static final int LENGTH = 15;
	private static final Point CENTER = new Point(LENGTH/2,LENGTH/2);
	private boolean hasCenterTile;
	public Board()
	{
		grid = new Square[LENGTH][LENGTH];
		hasCenterTile = false;
		init();
	}
	/**
	 * returns Square at point p.
	 * @param p : at point p
	 * @return square at p
	 */
	public Square getSquare(Point p)
	{
		return grid[p.getX()][p.getY()];
	}
	/**
	 * @param p : at point p
	 * @return owner of special tile
	 */
	public Player getSpecialTileOwner(Point p)
	{
		Square s = grid[p.getX()][p.getY()];
		if(s.hasSpecialTile())
			return s.getSpecialTile().getOwner();
		else
			return null;
	}
	/**
	 * return null if no tile at p, or removing from
	 * center while other tiles are present, or if doing
	 * so leaves some other tile isolated
	 */
	public Tile removeTileFrom(Point p)
	{
		Tile t = grid[p.getX()][p.getY()].removeTile();
		if(p.equals(CENTER))//if center is removed
			hasCenterTile = false;
		return t;	
	}
	/**
	 * remove and return special tile at p
	 * @param p
	 * @return special tile at p
	 */
	public SpecialTile removeSpecialTileFrom(Point p)
	{
		return grid[p.getX()][p.getY()].removeSpecialTile();
	}
	/**
	 * Note: first tile must be place on center
	 * @param t
	 * @param p
	 * @return true on success, false on failure
	 */
	public boolean addTileTo(Tile t,Point p)
	{
		//if center is empty and p is not center
		if(!hasCenterTile && !p.equals(CENTER))
			return false;
		Square s = grid[p.getX()][p.getY()];
		if(p.equals(CENTER))//is center
		{
			hasCenterTile = true;
			return s.setTile(t);
		}
		else if(hasNonEmptyNeighbors(p))//if next to some square with tile
			return s.setTile(t);
		else //middle of nowhere
			return false;
	}
	/**
	 * check whether square at p has a special tile
	 * @param p
	 * @return true on yes, false on no
	 */
	public boolean hasSpecialTileAt(Point p)
	{
		return grid[p.getX()][p.getY()].hasSpecialTile();
	}
	/**
	 * add a special tile to point p on board,
	 * overriding previous special tile if any,
	 * @param t : special tile
	 * @param p : location which t is added to
	 * @return false if a tile is at p, true otherwise
	 */
	public boolean addSpecialTileTo(SpecialTile t,Point p)
	{
		Square s = grid[p.getX()][p.getY()];
		if(!s.isEmpty())//already has a tile
			return false;
		return s.setSpecialTile(t);
	}
	private boolean hasNonEmptyNeighbors(Point p)
	{
		int x = p.getX();
		int y = p.getY();
		//left and right
		for(int i = x-1;i <= x+1;i += 2)
			if(i >= 0 && i < LENGTH && !grid[i][y].isEmpty())			
					return true;
		//up and down
		for(int j = y-1; j <= y+1; j += 2)
			if(j >= 0 && j < LENGTH && !grid[x][j].isEmpty())			
				return true;	
		return false;
	}
	/**
	 * return the word on the row at point p
	 * @param p
	 * @return
	 */
	public String getRowWord(Point p)
	{
		int x = p.getX();
		int y = p.getY();
		
		int left;//leftmost char's location
		int right;//rightmost char's location+1
		for(left = x;left >= 0;left--)
			if(grid[left][y].isEmpty())//locate the leftmost char
				break;
		for(right = x;right < LENGTH;right++)//locate the rightmost char
			if(grid[right][y].isEmpty())
				break;
		left++;
		char[] word = new char[right-left];	
		for(int i = left;i < right;i++)//create this char array
			word[i-left] = grid[i][y].getTile().getLetter();
		return new String(word);//make it a word
	}
	/**
	 * return word of the column at point p
	 * @param p
	 * @return
	 */
	public String getColWord(Point p)
	{
		int x = p.getX();
		int y = p.getY();
		
		int top;//uppermost char's location
		int bottom;//lowermost char's location+1
		for(top = y;top >= 0;top--)
			if(grid[x][top].isEmpty())//locate the uppermost
				break;
		for(bottom = y;bottom < LENGTH;bottom++)//locate the lowermost
			if(grid[x][bottom].isEmpty())
				break;
		top++;
		char[] word = new char[bottom-top];	
		for(int i = top;i < bottom;i++)//create a char array
			word[i-top] = grid[x][i].getTile().getLetter();
		return new String(word);//make it a string
	}
	/**
	 * assuming that the row has a valid word
	 * return the score of the word
	 * @param p
	 * @return
	 */
	public int getRowScore(Point p)
	{
		int x = p.getX();
		int y = p.getY();
		
		int left;
		int right;
		for(left = x;left >= 0;left--)
			if(grid[left][y].isEmpty())
				break;
		for(right = x;right < LENGTH;right++)
			if(grid[right][y].isEmpty())
				break;
		left++;
		
		int sum = 0;
		//add normal scores and lettersquare scores
		for(int i = left;i < right;i++)
			sum += grid[i][y].getScore();
		//apply effect of word squares
		for(int i = left;i < right;i++)
			if(grid[i][y] instanceof WordSquare)
				sum = ((WordSquare)grid[i][y]).multiplyScore(sum);
		return sum;
	}
	/**
	 * assuming that the column has a valid word
	 * return the score of this word
	 * @param p
	 * @return
	 */
	public int getColScore(Point p)
	{
		int x = p.getX();
		int y = p.getY();
		
		int top;
		int bottom;
		for(top = y;top >= 0;top--)
			if(grid[x][top].isEmpty())
				break;
		for(bottom = y;bottom < LENGTH;bottom++)
			if(grid[x][bottom].isEmpty())
				break;
		top++;
		
		int sum = 0;	
		//add normal scores and lettersquare scores
		for(int i = top;i < bottom;i++)
			sum += grid[x][i].getScore();
		//apply effect of word squares
		for(int i = top;i < bottom;i++)
			if(grid[x][i] instanceof WordSquare)
				sum = ((WordSquare)grid[x][i]).multiplyScore(sum);
		return sum;
	}
	
	private void make(Square s)
	//makes four squares at each quadrant, reflecting
	//over the middle column, the middle row, and the center
	{
		int i = s.getPosition().getX();
		int j = s.getPosition().getY();
		Point ul = s.getPosition();//upper-left
		Point ur = new Point(LENGTH-i-1,j);//upper-right
		Point lr = new Point(LENGTH-i-1,LENGTH-j-1);//lower-right
		Point ll = new Point(i,LENGTH-j-1);//lower-left
		if(s instanceof WordSquare)
		{
			int m = ((WordSquare)s).getMultiplier();
			grid[ul.getX()][ul.getY()] = new WordSquare(ul,m);
			grid[ur.getX()][ur.getY()] = new WordSquare(ur,m);
			grid[lr.getX()][lr.getY()] = new WordSquare(lr,m);
			grid[ll.getX()][ll.getY()] = new WordSquare(ll,m);
		}
		else if(s instanceof LetterSquare)
		{
			int m = ((LetterSquare)s).getMultiplier();
			grid[ul.getX()][ul.getY()] = new LetterSquare(ul,m);
			grid[ur.getX()][ur.getY()] = new LetterSquare(ur,m);
			grid[lr.getX()][lr.getY()] = new LetterSquare(lr,m);
			grid[ll.getX()][ll.getY()] = new LetterSquare(ll,m);			
		}
		else //NormalSquare
		{
			grid[ul.getX()][ul.getY()] = new NormalSquare(ul);
			grid[ur.getX()][ur.getY()] = new NormalSquare(ur);
			grid[lr.getX()][lr.getY()] = new NormalSquare(lr);
			grid[ll.getX()][ll.getY()] = new NormalSquare(ll);	
		}
	}
	private void init()//initialize
	{
		for(int i = 0;i <= 7;i++)
		{
			for(int j = i;j <= 7;j++)
			{
				//for each point at the upper half triangle
				//of the second quadrant
				Point p = new Point(i,j);
				//and its corresponding reflection point at
				//the lower half triangle
				Point pRev = new Point(j,i);
				if(i == j)//point on the diagonal
				{
					if(i == 0)//triple word
						make(new WordSquare(p,3));
					else if(i == 5)//triple letter
						make(new LetterSquare(p,3));
					else if(i == 6)//double letter
						make(new LetterSquare(p,2));
					else//double word, including center
						make(new WordSquare(p,2));
				}
				else if(i == 0 && j == 7)//triple word
				{
					make(new WordSquare(p,3));
					make(new WordSquare(pRev,3));
				}
				else if(i == 1 && j == 5)//triple letter
				{
					make(new LetterSquare(p,3));
					make(new LetterSquare(pRev,3));				
				}
				else if((i==0&&j==3)||(i==2&&j==6)||(i==3&&j==7))
				{
					make(new LetterSquare(p,2));
					make(new LetterSquare(pRev,2));
				}
				else //normal squares
				{
					make(new NormalSquare(p));
					make(new NormalSquare(pRev));
				}
			}
		}
	}
}
