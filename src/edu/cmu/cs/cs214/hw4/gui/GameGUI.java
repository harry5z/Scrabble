package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Point;
import edu.cmu.cs.cs214.hw4.tile.BlankTile;
import edu.cmu.cs.cs214.hw4.tile.SpecialTile;
import edu.cmu.cs.cs214.hw4.tile.Tile;

public class GameGUI extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2684400931445074246L;
	private Game game;
	private BoardGUI board;
	private GameGUI next;
	
	private ButtonGUI submit;
	private ButtonGUI skip;
	private ButtonGUI exchange;
	private ButtonGUI purchase;
	private ButtonGUI end;
	private boolean readyToExchange;
	private boolean readyToPlace;
	private boolean submitted;
	
	private TextFieldGUI remainingTiles;
	private TextFieldGUI score;
	private SpecialRackGUI specialRack;
	private RackGUI rack;
	private JPanel controls;
	
	private ArrayList<Tile> exchangeTiles;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 885;
	public GameGUI(Game g,int id)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exchangeTiles = new ArrayList<Tile>();
		
		JPanel content = new JPanel();
		content.setLayout(null);
		content.setPreferredSize(new Dimension(BoardGUI.LENGTH+ButtonGUI.WIDTH,BoardGUI.LENGTH+RackGUI.HEIGHT));
		
		setResizable(false);
		setTitle("Scrabble - Player "+id);
		setSize(WIDTH,HEIGHT);
		setLocation(100,100);
		game = g;
		
		//set up control panel	
		skip = new ButtonGUI("Skip",this);
		skip.setLocation(0,0);
		exchange = new ButtonGUI("Exchange",this);
		exchange.setLocation(0,50);
		submit = new ButtonGUI("Submit",this);
		submit.setLocation(0, 100);
		purchase = new ButtonGUI("Purchase",this);
		purchase.setLocation(0, 150);
		end = new ButtonGUI("End",this);
		end.setLocation(0, 200);
		purchase.setEnabled(false);
		end.setEnabled(false);
		score = new TextFieldGUI("Score: 0");
		score.setLocation(0,250);
		remainingTiles = new TextFieldGUI("");
		remainingTiles.setLocation(0,300);
		controls = new JPanel();
		controls.setLayout(null);
		controls.setSize(ButtonGUI.WIDTH,7*ButtonGUI.HEIGHT);
		controls.setLocation(BoardGUI.LENGTH,0);
		controls.add(skip);
		controls.add(exchange);
		controls.add(submit);
		controls.add(purchase);
		controls.add(end);
		controls.add(score);
		controls.add(remainingTiles);
		
		//set up rack and special tile rack
		rack = new RackGUI(this);
		rack.setLocation(0,0);
		specialRack = new SpecialRackGUI(this);
		specialRack.setLocation(BoardGUI.LENGTH,350);
		
		//set up board
		board = new BoardGUI(game.getBoard(),id,this);
		board.setLocation(0, RackGUI.HEIGHT);
		
		content.add(rack);
		content.add(specialRack);
		content.add(board);
		content.add(controls);

		add(content);
		pack();
		//initialize buttons and booleans
		resetButtonsAndBools();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		skip.setEnabled(false);//disable skip
		if(o instanceof SquareGUI)
			clickedOnSquare(o);
		else if(o instanceof TileGUI)
			clickedOnTile(o);
		else if(o instanceof SpecialTileGUI)
			clickedOnSpecialTile(o);
		else if(o == skip)
			skipTurn();
		else if(o == exchange)
			exchange();
		else if(o == end)
			endTurn();
		else if(o == purchase)
			purchase();
		else if(o == submit)
			submit();
		
		//if ended
		if(!isVisible())
			return;
		if(readyToExchange)//have tile on hand
		{
			skip.setEnabled(false);
			submit.setEnabled(false);
		}
		//if played anything then cannot skip or exchange
		if(game.getPlayedCount() > 0)
		{
			skip.setEnabled(false);
			exchange.setEnabled(false);
		}
		else if(!readyToPlace && !readyToExchange)
		{
			skip.setEnabled(true);
			exchange.setEnabled(true);
			submit.setEnabled(true);
		}
	}
	private void purchase()
	{
		try
		{
			String message = "Select the type of special tile to purchase: \n"
					+ SpecialTile.NEGATIVEPOINT + " for NegativePoint\n" 
					+ SpecialTile.BOMBERMAN + " for Bomberman\n"
					+ SpecialTile.STEALSCORE + " for StealScore\n";
			int type = Integer.parseInt(JOptionPane.showInputDialog(message));
			if(game.purchase(type))//if successfully purchased
			{
				//update score and special tile rack
				resetScore();
				specialRack.resetSpecialTiles(game.getCurrentPlayer().getSpecialTiles());
			}
			else
				JOptionPane.showMessageDialog(null,"fail to purchase special tiles");
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null,"Invalid format");
		}
	}
	private void submit()
	{
		if(game.submitPressed())//successful
		{
			enableTwoButtons();
			disableThreeButtons();
			submitted = true;
			resetScore();
			board.repaint();
		}
		else
			JOptionPane.showMessageDialog(null, "Not a word");
	}
	private void resetScore()//refresh player's score
	{
		score.setText("Score: "+game.getCurrentPlayer().getScore());
	}
	private void clickedOnSquare(Object o)
	{
		if(readyToExchange)
			return;
		Point p = ((SquareGUI)o).getPosition();
		if(submitted)//dealing with special tiles
		{
			if(!readyToPlace)//try to take a special tile
			{
				if(game.takeSpecialTileAt(p))//successfully take a special tile
				{
					readyToPlace = true;
					disableTwoButtons();
				}
			}
			else
			{
				if(game.addSpecialTileAt(p))//successfully added
				{
					readyToPlace = false;
					enableTwoButtons();
				}
			}
			return;
		}
		if(!readyToPlace)//not holding a tile yet (try to take tile)
		{
			if(game.takeTileAt(p))//successfully take tile from board
			{
				readyToPlace = true;
				disableThreeButtons();
			}
		}
		else //holding a tile, place it on board
		{
			//if successful, then not ready to place
			//if not successful, then do nothing
			if(game.addTileAt(p))
			{
				readyToPlace = false;
				enableThreeButtons();
			}
		}
	}
	private void clickedOnTile(Object o)
	{
		if(submitted)//end of turn
			return;
		TileGUI t = (TileGUI)o;
		if(readyToExchange)//selecting exchange tiles
		{
			if(t.isEmpty())//put tiles back to rack
			{
				if(exchangeTiles.size() != 0)//still have tiles
					t.setTile((exchangeTiles.remove(exchangeTiles.size()-1)));
				else
					JOptionPane.showMessageDialog(null, "No more exchanging tiles");
			}
			else//add tile to exchange list
				exchangeTiles.add(t.removeTile());
			return;
		}
		if(!readyToPlace)//not holding a tile yet
		{
			if(t.isEmpty())//skip empty
				return;
			//dealing with blank tiles
			if(t.getTile() instanceof BlankTile)
			{
				BlankTile bt = (BlankTile)(t.getTile());
				while(true)//set blank tile character
				{
					if(bt.hasLetter())//if already set a character
					{
						int change = JOptionPane.showConfirmDialog(null,"Do you want to change the character?","Set character",JOptionPane.YES_NO_OPTION);
						if(change != 0)//don't want to change
							break;
					}
					String s = JOptionPane.showInputDialog(null, "Please set blank tile character");
					if(s == null || s.length() != 1)//no input/too long
					{
						JOptionPane.showMessageDialog(null, "One character please");
						continue;
					}
					char c = s.charAt(0);
					if(Character.isLetter(c))//if valid letter
					{
						bt.setLetter(Character.toLowerCase(c));
						break;
					}
					else
						JOptionPane.showMessageDialog(null,"Not a character");
				}
			}
			readyToPlace = true;
			game.receiveTile(t.removeTile());
			disableThreeButtons();
		}
		else//putting back to rack
		{
			if(!t.isEmpty())//slot not empty
				return;
			readyToPlace = false;
			t.setTile(game.getCurrentTile());
			game.dropCurrentTile();
			enableThreeButtons();
		}
	}
	private void clickedOnSpecialTile(Object o)
	{
		if(!submitted)//turn not ended yet
			return;
		SpecialTileGUI t = (SpecialTileGUI)o;
		if(!readyToPlace)//taking from special rack
		{
			if(t.isEmpty())
				return;
			readyToPlace = true;
			game.receiveSpecialTile(t.removeSpecialTile());			
			disableTwoButtons();
		}
		else//putting back to special rack
		{
			if(!t.isEmpty())
				return;
			readyToPlace = false;
			t.setSpecialTile(game.getCurrentSpecialTile());
			game.dropCurrentSpecialTile();
			enableTwoButtons();
		}
	}
	private void exchange()
	{
		if(!readyToExchange)//start selecting
		{
			readyToExchange = true;
			exchange.setText("Confirm");
		}
		else//finish selecting
		{
			if(exchangeTiles.size() == 0)//no tile selected
			{
				JOptionPane.showMessageDialog(null, "No Tile selected!", "Error", JOptionPane.PLAIN_MESSAGE);
				readyToExchange = false;
				exchange.setText("Exchange");
				return;
			}
			Tile[] toExchange = new Tile[exchangeTiles.size()];
			for(int i = 0; i < exchangeTiles.size();i++)
				toExchange[i] = exchangeTiles.get(i);
			if(game.exchangePressed(toExchange))//successfully exchanged
			{
				exchangeTiles.clear();//clear the list
				readyToExchange = false;
				exchange.setText("Exchange");
				endTurn();
			}
		}
	}
	/**
	 * used only once to set the "next player"
	 * @param g
	 */
	public void setNext(GameGUI g)
	{
		next = g;
	}
	private void skipTurn()
	{
		game.skipPressed();
		endTurn();
	}
	private void endTurn()
	{
		setVisible(false);
		resetButtonsAndBools();
		if(game.gameEnded())
		{
			if(game.getWinner() == -1) //drawed
				JOptionPane.showMessageDialog(null,"Game over! The game draws");
			else
				JOptionPane.showMessageDialog(null,"Game over! Winner is Player "+game.getWinner());
			return;
		}
		game.endTurn();
		next.startTurn();
	}
	/**
	 * called by the previous GameGUI to start
	 */
	public void startTurn()
	{
		setVisible(true);
		game.play();
		//reset stuff
		resetScore();
		remainingTiles.setText("Remaining Tiles: "+game.getBag().getTileCount());
		rack.resetTiles(game.getCurrentPlayer().getTiles());
		//new round, relocatable
		for(SpecialTile st : game.getCurrentPlayer().getSpecialTilesOnBoard())
			st.setRelocatable(true);
		specialRack.resetSpecialTiles(game.getCurrentPlayer().getSpecialTiles());
	}
	private void resetButtonsAndBools()//to default
	{
		enableThreeButtons();
		disableTwoButtons();
		readyToExchange = false;
		readyToPlace = false;
		submitted = false;
	}
	private void disableThreeButtons()//disable three "pre-submit" buttons
	{
		submit.setEnabled(false);
		exchange.setEnabled(false);
		skip.setEnabled(false);
	}
	private void disableTwoButtons()//disable two "post-submit" buttons
	{
		purchase.setEnabled(false);
		end.setEnabled(false);
	}
	private void enableTwoButtons()
	{
		purchase.setEnabled(true);
		end.setEnabled(true);
	}
	private void enableThreeButtons()
	{
		submit.setEnabled(true);
		exchange.setEnabled(true);
		skip.setEnabled(true);
	}
}
