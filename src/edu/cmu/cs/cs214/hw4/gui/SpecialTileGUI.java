package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import edu.cmu.cs.cs214.hw4.tile.SpecialTile;

public class SpecialTileGUI extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2773015596337096653L;
	private SpecialTile tile;
	private boolean empty;
	public SpecialTileGUI()
	{
		tile = null;
		empty = true;
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		setSize(ButtonGUI.WIDTH*4/5,SquareGUI.LENGTH);
		setOpaque(true);
	}
	/**
	 * get current special tile
	 * @return tile : its special tile
	 */
	public SpecialTile getSpecialTile()
	{
		return tile;
	}
	/**
	 * set special tile if currently empty
	 * @param t : special tile to be added
	 */
	public void setSpecialTile(SpecialTile t)
	{
		if(!empty)
			return;
		tile = t;
		setText(tile.getName());
		setBackground(Color.GREEN);
		empty = false;
	}
	/**
	 * remove and return current special tile
	 * @return current special tile
	 */
	public SpecialTile removeSpecialTile()
	{
		SpecialTile temp = tile;
		setText("");
		tile = null;
		empty = true;
		setBackground(Color.RED);
		return temp;
	}
	/**
	 * @return false if has special tile, false otherwise
	 */
	public boolean isEmpty()
	{
		return empty;
	}
}
