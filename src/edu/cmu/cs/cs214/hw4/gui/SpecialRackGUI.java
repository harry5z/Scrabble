package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.tile.SpecialTile;

public class SpecialRackGUI extends JPanel
{
	SpecialTileGUI[] tiles;
	public SpecialRackGUI(ActionListener a)
	{
		setLayout(null);
		tiles = new SpecialTileGUI[Player.MAX_SPECIAL_TILES];
		TextFieldGUI name = new TextFieldGUI("SpecialTiles");
		name.setLocation(0,0);
		name.setBackground(Color.YELLOW);
		add(name);
		//initialize speical rack
		for(int i = 0;i < tiles.length;i++)
		{
			SpecialTileGUI t = new SpecialTileGUI();
			t.addActionListener(a);
			t.setLocation(ButtonGUI.WIDTH/10,25+SquareGUI.LENGTH+i*SquareGUI.LENGTH*9/5);
			tiles[i] = t;
			add(t);
		}
		setBackground(Color.LIGHT_GRAY);
		setSize(BoardGUI.LENGTH,2*(1+SquareGUI.LENGTH*Player.MAX_SPECIAL_TILES));
	}
	/**
	 * refresh the rack with given list
	 * @param tList : new list of special tiles
	 */
	public void resetSpecialTiles(SpecialTile[] tList)
	{
		//fill the rack as much as possible
		for(int i = 0;i < tList.length;i++)
			tiles[i].setSpecialTile(tList[i]);
		//clean the rest
		for(int i = tList.length;i < Player.MAX_SPECIAL_TILES;i++)
			tiles[i].removeSpecialTile();
	}
}
