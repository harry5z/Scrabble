package edu.cmu.cs.cs214.hw4.core;

import edu.cmu.cs.cs214.hw4.tile.*;

public class Game 
{
	private Board board;
	private Player[] players;
	private TileBag bag;
	private int playerCount;//total # of players
	private Dictionary dict;
	private Player currentPlayer;
	private Tile currentTile;//tile that player is "holding on hand"
	private SpecialTile currentSpecialTile;//special tile "on hand"
	private Point[] currentPlayedPoints;//list of points player placed a tile on
	private int currentPointCount;//num of tiles played this round
	private boolean firstRound;//is it the first round?
	private byte scorelessTurnCount;//how many consecutive skips?

	public Game()
	{
		board = new Board();
		bag = new TileBag();
		dict = new Dictionary();
		playerCount = 0;
		scorelessTurnCount = 0;
	}
	/**
	 * expose current tile on hand
	 * @return current tile
	 */
	public Tile getCurrentTile()
	{
		return currentTile;
	}
	/**
	 * expose current special tile on hand
	 * @return current special tile
	 */
	public SpecialTile getCurrentSpecialTile()
	{
		return currentSpecialTile;
	}
	/**
	 * check how many tiles are already played in this round
	 * @return count of currently played tiles
	 */
	public int getPlayedCount()
	{
		return currentPointCount;
	}
	/**
	 * add c players to the game, where
	 * 2 <= c <= 4
	 * @param c
	 */
	public void addPlayer(int c)
	{
		firstRound = true;
		playerCount = c;
		players = new Player[c];
		for(int i = 0;i < c;i++)
		{
			players[i] = new Player(i);
			players[i].addTiles(bag.takeTiles(Player.MAX_TILES));
		}
		currentPlayer = players[0];//first player
	}
	/**
	 * expose current player
	 * @return current player
	 */
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	/**
	 * start a new turn
	 */
	public void play()
	{
		currentPlayedPoints = new Point[Player.MAX_TILES];
		currentPointCount = 0;
	}
	/**
	 * player sets the character of a blank tile
	 * must be done as soon as tile is selected
	 * @param t : blank tile
	 * @param c : character to be set
	 */
	public void setBlankTileChar(Tile t, char c)
	{
		((BlankTile)t).setLetter(c);
	}
	/**
	 * player chooses exchange a list of tiles
	 * @param list : exchange tiles
	 * @return true on success, false on failure
	 */
	public boolean exchangePressed(Tile[] list)
	{
		int amount = list.length;
		Tile[] newTiles = bag.takeTiles(amount);
		if(newTiles.length < amount)//why do you want to take so many!
			return false;
		for(Tile t : list)//remove player tiles
			currentPlayer.removeTile(t);
		for(Tile t : newTiles)//give to player
			currentPlayer.addTile(t);
		bag.recycleTiles(list);//recycle
		scorelessTurnCount++;
		return true;
	}
	
	/**
	 * player chooses to skip the turn
	 */
	public void skipPressed()
	{
		scorelessTurnCount++;
	}
	/**
	 * when player selects a tile from rack
	 * @param t
	 */
	public void receiveTile(Tile t)
	{
		currentTile = t;
		currentPlayer.removeTile(t);
	}
	/**
	 * when player removes a tile just played.
	 * under current setting, only the last tile 
	 * played can be removed
	 * @param p
	 */
	public boolean takeTileAt(Point p)
	{
		if(currentPointCount == 0)//nothing played yet
			return false;
		//not removing the last
		if(!currentPlayedPoints[currentPointCount-1].equals(p))
			return false;
		currentTile = board.removeTileFrom(p);
		if(currentTile == null)//removing from center/leaving blocks isolated
			return false;
		currentPlayedPoints[currentPointCount-1] = null;
		currentPointCount--;
		return true;
	}
	/**
	 * when player puts a tile back to rack
	 */
	public void dropCurrentTile()
	{
		currentPlayer.addTile(currentTile);
		currentTile = null;
	}
	/**
	 * when player puts a special tile back to rack
	 */
	public void dropCurrentSpecialTile()
	{
		currentPlayer.addSpecialTile(currentSpecialTile);
		currentSpecialTile = null;
	}
	private boolean isOnRow(Point p)
	{
		//check if all points played are on the same row
		int y = p.getY();
		int left = p.getX();
		int right = left;
		for(int i = 0;i < currentPointCount;i++)
		{
			if(currentPlayedPoints[i] != null)
			{
				if(currentPlayedPoints[i].getY() != y)
					return false;
				else if(currentPlayedPoints[i].getX() < left)
					left = currentPlayedPoints[i].getX();
				else if(currentPlayedPoints[i].getX() > right)
					right = currentPlayedPoints[i].getX();
			}
		}
		for(int i = left+1;i < right;i++)
			if(board.getSquare(new Point(i,y)).isEmpty())
				return false;
		return true;
	}
	private boolean isOnCol(Point p)
	{
		//check if all points played are on the same column
		int x = p.getX();
		int top = p.getY();
		int bottom = top;
		for(int i = 0;i < currentPointCount;i++)
		{
			if(currentPlayedPoints[i] != null)
			{
				if(currentPlayedPoints[i].getX() != x)
					return false;
				else if(currentPlayedPoints[i].getY() < top)
					top = currentPlayedPoints[i].getY();
				else if(currentPlayedPoints[i].getY() > bottom)
					bottom = currentPlayedPoints[i].getY();
			}
		}
		for(int i = top+1;i < bottom;i++)
			if(board.getSquare(new Point(x,i)).isEmpty())
				return false;
		return true;
	}
	/**
	 * when player places a tile at point p
	 * (player must have a tile "on hand")
	 * (point p must not be occupied)
	 * @param p
	 * @return true on success, false on failure
	 */
	public boolean addTileAt(Point p)
	{
		//has to be on the same col/row
		if(currentPointCount != 0 && !(isOnRow(p) || isOnCol(p)))
			return false;
		if(board.addTileTo(currentTile, p))//can add to p
		{
			currentTile = null;
			currentPlayedPoints[currentPointCount] = p;
			currentPointCount++;
			return true;
		}
		return false;
	}
	/**
	 * when player selects a special tile
	 * afterwards, special tile t is "on hand"
	 * @param t
	 */
	public void receiveSpecialTile(SpecialTile t)
	{
		currentSpecialTile = t;
		currentPlayer.removeSpecialTile(t);
	}
	/**
	 * take a special tile from board to relocate
	 * return true on success, false on failure
	 * @param p
	 */
	public boolean takeSpecialTileAt(Point p)
	{
		//not its owner or no special tile
		if(currentPlayer != board.getSpecialTileOwner(p))
			return false;
		if(!board.getSquare(p).getSpecialTile().isRelocatble())//not relocatable
			return false;
		currentSpecialTile = board.removeSpecialTileFrom(p);
		currentSpecialTile.getOwner().removeSpecialTileFromBoard(currentSpecialTile);
		return true;
	}
	/**
	 * when player places a special tile on p
	 * @param p
	 * @return always true under current setting
	 */
	public boolean addSpecialTileAt(Point p)
	{
		if(board.addSpecialTileTo(currentSpecialTile, p))
		{
			currentPlayer.placeSpecialTileOnBoard(currentSpecialTile);
			currentSpecialTile.setRelocatable(false);
			currentSpecialTile = null;
			return true;
		}
		return false;		
	}
	/**
	 * try to purchase a special tile
	 * @param type : Bomberman/NegativePoint/StealScore
	 * @return true on success and false on failure(too many/insufficient fund)
	 */
	public boolean purchase(int type)
	{
		if(type == SpecialTile.BOMBERMAN)
			return currentPlayer.purchaseSpecialTile(new BombermanTile(currentPlayer));
		else if(type == SpecialTile.NEGATIVEPOINT)
			return currentPlayer.purchaseSpecialTile(new NegativePointTile(currentPlayer));
		else if(type == SpecialTile.STEALSCORE)
			return currentPlayer.purchaseSpecialTile(new StealScoreTile(currentPlayer));
		else
			return false;
	}
	/**
	 * player submits and waits for scoring
	 * @return true on success, false on failure (not a word/no tile played)
	 */
	public boolean submitPressed()
	{
		//only played one tile on the first round?
		if(firstRound && currentPointCount <= 1)
			return false;
		//don't want to play any tile?
		if(currentPointCount == 0)
			return false;
		Point first = currentPlayedPoints[0];
		int score = 0;
		boolean isOnRow = isOnRow(first);//is the main word on row/column?
		if(currentPointCount == 1)//only one tile played
		{
			if(dict.isWord(board.getRowWord(first)))//if row is word
				isOnRow = true;
			else //more than 1 char on row
				isOnRow = false;
		}
		if(isOnRow)
		{
			String word = board.getRowWord(first);
			if(!dict.isWord(word))//is it a word?
				return false;
			score = board.getRowScore(first);//main word score
			//check for secondary words (on columns)
			for(Point p : currentPlayedPoints)
			{
				if(p == null)
					continue;
				String secondary = board.getColWord(p);
				if(secondary.length() <= 1)
					continue;
				if(dict.isWord(secondary))//is a word
					score += board.getColScore(p);
				else 
					return false;
			}
		}
		else if(isOnCol(first))
		{
			
			String word = board.getColWord(first);
			if(!dict.isWord(word))//is it a word?
				return false;
			score = board.getColScore(first);
			//check for secondary words
			for(Point p : currentPlayedPoints)
			{
				if(p == null)
					continue;
				String secondary = board.getRowWord(p);
				if(secondary.length() <= 1)
					continue;
				if(dict.isWord(secondary))//is a word
					score += board.getRowScore(p);
				else
					return false;
			}
		}
		else
			return false;
		//apply special effects
		for(Point p : currentPlayedPoints)
		{
			if(p == null || !board.hasSpecialTileAt(p))//no special tile on p
				continue;
			SpecialTile st = board.removeSpecialTileFrom(p);
			score = st.applyEffect(this,p,score);
			st.getOwner().useSpecialTile(st);
		}
		currentPlayer.addScore(score);
		scorelessTurnCount = 0;
		return true;
	}
	public TileBag getBag()
	{
		return bag;
	}
	/**
	 * when a turn is ended, switch player
	 */
	public void endTurn()
	{
		if(firstRound)
			firstRound = false;
		//refill
		int need = Player.MAX_TILES - currentPlayer.getTileCount();	
		if(need > 0)
			currentPlayer.addTiles(bag.takeTiles(need));
		//change player
		currentPlayer = players[(currentPlayer.getIdentity()+1)%playerCount];
	}
	/**
	 * check if game should end
	 * @return true if game ends, false otherwise
	 */
	public boolean gameEnded()
	{
		if(scorelessTurnCount == 6)
			return true;
		//no more tile to play
		return bag.getTileCount() == 0 && currentPlayer.getTileCount() == 0;
	}
	/**
	 * if game ends, check for winner
	 * @return ID of winner, or -1 if game draws
	 */
	public int getWinner()
	{
		boolean drawed = false;
		Player goingOut = null;
		int sum = 0;
		for(Player p : players)
		{
			int penalty = 0;
			if(p.getTileCount() == 0)
				goingOut = p;
			else
			{
				for(Tile t : p.getTiles())
					penalty += t.getValue();
			}
			p.addScore(-penalty);
			sum += penalty;
		}
		if(goingOut != null)//someone went out
			goingOut.addScore(sum);
		int highest = players[0].getScore();//highest score
		int winner = 0;//player with highest score
		for(int i = 1;i < playerCount;i++)
		{
			int newScore = players[i].getScore();
			if(newScore > highest)
			{
				drawed = false;
				highest = newScore;
				winner = i;
			}
			else if(newScore == highest)
				drawed = true;
		}
		if(drawed)
			return -1;
		return winner;
	}
	/**
	 * show the board
	 * @return board
	 */
	public Board getBoard()
	{
		return board;
	}
	
}
