package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonGUI extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3194037499704113360L;
	public static final int WIDTH = 250;
	public static final int HEIGHT = 50;
	public ButtonGUI(String text, ActionListener a)
	{
		super(text);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		setSize(WIDTH,HEIGHT);
		addActionListener(a);
	}
}
