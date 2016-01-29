package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import edu.cmu.cs.cs214.hw4.tile.NormalTile;
import edu.cmu.cs.cs214.hw4.tile.Tile;

public class TileGUI extends JButton
{
	private Tile tile;
	private boolean empty;
	public TileGUI()
	{
		tile = null;
		empty = true;
		setSize(SquareGUI.LENGTH,SquareGUI.LENGTH);
		this.setOpaque(true);
	}
	/**
	 * expose its tile
	 * @return current tile
	 */
	public Tile getTile()
	{
		return tile;
	}
	/**
	 * set tile
	 * @param t : tile to be added
	 */
	public void setTile(Tile t)
	{
		tile = t;
		empty = false;
	}
	/**
	 * remove and return current tile
	 * @return current tile
	 */
	public Tile removeTile()
	{
		Tile temp = tile;
		tile = null;
		empty = true;
		return temp;
	}
	/**
	 * @return false if no tile, true otherwise
	 */
	public boolean isEmpty()
	{
		return empty;
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		setBackground(Color.WHITE);
		if(tile == null)//empty slot
		{
			setText("");
			return;
		}
		if(tile instanceof NormalTile)
		{
			Image i;
			try
			{
				String path = new String("tiles/"+tile.getLetter()+".gif");
				i = ImageIO.read(getClass().getResource(path));
				g.drawImage(i,1,1,null);
			}
			catch(IOException e)
			{
				System.out.println("File Not Found");
			}
			
		}
		else//blank tile
		{
			setBackground(Color.YELLOW);//blank tile with background color yellow
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
			g.drawString(""+Character.toUpperCase(tile.getLetter()),15,37);
		}
	}
}
