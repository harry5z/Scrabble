package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class TextFieldGUI extends JTextField
{
	public TextFieldGUI(String text)
	{
		setText(text);
		setBackground(Color.GREEN);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		setSize(ButtonGUI.WIDTH,ButtonGUI.HEIGHT);
		setEditable(false);
		setHorizontalAlignment(JTextField.CENTER);
	}
}
