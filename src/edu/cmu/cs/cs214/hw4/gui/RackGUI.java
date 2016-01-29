package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.tile.Tile;

public class RackGUI extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5376452010355685562L;
	public static final int HEIGHT = 100;
	TileGUI[] tiles;
	public RackGUI(ActionListener a)
	{
		setLayout(null);
		tiles = new TileGUI[Player.MAX_TILES];
		//initialize 7 tiles
		for(int i = 0;i < tiles.length;i++)
		{
			TileGUI t = new TileGUI();
			t.addActionListener(a);
			t.setLocation(BoardGUI.LENGTH/(Player.MAX_TILES*2+1)+SquareGUI.LENGTH*2*i,HEIGHT/4);
			tiles[i] = t;
			add(t);
		}
		setSize(BoardGUI.LENGTH,HEIGHT);
	}
	@Override
	public void paint(Graphics g)
	{
		Image i;
		try
		{
			//set background picture
			i = ImageIO.read(getClass().getResource("rack.jpg"));
			g.drawImage(i,0,0,null);
			super.paintComponents(g);
		}
		catch(IOException e)
		{
			System.out.println("File Not Found");
		}
	}
	/**
	 * refresh the rack with given list
	 * @param tList : new tile list
	 */
	public void resetTiles(Tile[] tList)
	{
		//set all tiles
		for(int i = 0;i < tList.length;i++)
			tiles[i].setTile(tList[i]);
		//clear the rest
		for(int i = tList.length;i < Player.MAX_TILES;i++)
			tiles[i].removeTile();
	}
}
