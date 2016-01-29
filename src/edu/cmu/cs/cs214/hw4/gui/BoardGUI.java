package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.Point;

public class BoardGUI extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4704951396197173842L;
	public static final int LENGTH = Board.LENGTH * SquareGUI.LENGTH;
	public BoardGUI(Board b,int playerID,ActionListener a)
	{
		int len = Board.LENGTH;
		setLayout(null);
		setSize(LENGTH,LENGTH);
		for(int x = 0;x < len;x++)
		{
			for(int y = 0;y < len;y++)	
			{
				//initialize board
				SquareGUI temp = new SquareGUI(playerID,b.getSquare(new Point(x,y)));
				temp.setLocation(SquareGUI.LENGTH*x,SquareGUI.LENGTH*y);
				temp.addActionListener(a);
				add(temp);
			}
		}
	}
}
