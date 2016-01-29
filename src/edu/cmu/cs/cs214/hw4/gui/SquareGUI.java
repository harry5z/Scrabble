package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import edu.cmu.cs.cs214.hw4.core.Point;
import edu.cmu.cs.cs214.hw4.square.*;
import edu.cmu.cs.cs214.hw4.tile.BlankTile;

public class SquareGUI extends JButton
{
	private Square square;
	private int ID;
	public static final int LENGTH = 50;
	public SquareGUI(int playerID,Square s)
	{
		ID = playerID;
		square = s;
		setSize(LENGTH,LENGTH);
	}
	/**
	 * @return the position of this square
	 */
	public Point getPosition()
	{
		return square.getPosition();
	}
	@Override
	public void paint(Graphics g)
	{
		Image i = null;
		try
		{
			if(square instanceof NormalSquare)
				i = ImageIO.read(getClass().getResource("normal.jpg"));
			else if(square instanceof WordSquare)
			{
				if(((WordSquare)square).getMultiplier() == 2)
					if(square.getPosition().getX() == 7 && square.getPosition().getY() == 7)
						i = ImageIO.read(getClass().getResource("center.jpg"));
					else
						i = ImageIO.read(getClass().getResource("doubleword.jpg"));
				else
					i = ImageIO.read(getClass().getResource("tripleword.jpg"));
			}
			else//letter square
			{
				if(((LetterSquare)square).getMultiplier() == 2)
					i = ImageIO.read(getClass().getResource("doubleletter.jpg"));
				else
					i = ImageIO.read(getClass().getResource("tripleletter.jpg"));
			}
			g.drawImage(i,0,0,null);
			if(!square.isEmpty())//draw tile
			{
				if(square.getTile() instanceof BlankTile)
				{
					g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
					g.setColor(Color.WHITE);
					g.drawString(""+Character.toUpperCase(square.getTile().getLetter()),15,37);
				}
				else//normal tile
				{
					String path = new String("tiles/"+square.getTile().getLetter()+".gif");
					i = ImageIO.read(getClass().getResource(path));
					g.drawImage(i,1,1,null);
				}
				return;
			}
			if(square.hasSpecialTile())//draw special tile if no tile
			{
				//draw only when the special tile belongs to THIS player
				if(ID == square.getSpecialTile().getOwner().getIdentity())
				{
					String name = square.getSpecialTile().getName();
					g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
					g.setColor(Color.RED);
					g.drawString(name.substring(0,1), 15, 37);
				}
			}
		}
		catch(IOException e)
		{
			System.err.println("File not found");
		}
	}
}
