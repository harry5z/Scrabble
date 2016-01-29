package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import edu.cmu.cs.cs214.hw4.core.Game;

public class WelcomeGUI extends JFrame implements ActionListener
{
	private ButtonGUI addPlayer;
	private Game g;
	private JFormattedTextField count;
	public WelcomeGUI()//a simple welcome UI
	{
		setTitle("Welcome to Scrabble!");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(500, 100);
		g = new Game();

		JPanel setup = new JPanel();
		setup.setBackground(Color.green);
		JLabel countInput = new JLabel("How many people are playing?");
		countInput.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		setup.add(countInput);
		
		count = new JFormattedTextField("");
		FontMetrics f = count.getFontMetrics(count.getFont());
		int w = f.getWidths()['a'] * 6;
		count.setPreferredSize(new Dimension(w,30));
		count.setFont(new Font(Font.SERIF,Font.BOLD,18));
		count.addActionListener(this);
		setup.add(count);
		
		addPlayer = new ButtonGUI("Add Player",this);
		setup.add(addPlayer);
		
		//picture I found :)
		JPanel pic = new JPanel(){
			@Override
			public void paint(Graphics g)
			{
				Image i = null;
				try
				{
					i = ImageIO.read(getClass().getResource("scrabble.jpg"));
				}
				catch(IOException e)
				{
					System.err.println("File not found");
				}
				g.drawImage(i,0,0,null);
			}
		};
		
		add(pic,BorderLayout.CENTER);
		add(setup,BorderLayout.NORTH);
		pack();
		setSize(705,405);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int c;
		try
		{
			c = Integer.parseInt(count.getText());
			if(c < 2 || c > 4)
			{
				JOptionPane.showMessageDialog(null, "Too many/too few players. 2-4 players allowed.", "Error", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			setVisible(false);//disappear
			start(c);//start game
		}
		catch(NumberFormatException exp)
		{
			JOptionPane.showMessageDialog(null, "Invalid Player count", "Error", JOptionPane.PLAIN_MESSAGE);
		}		
	}
	private void start(int count)
	{
		g.addPlayer(count);
		GameGUI[] frames = new GameGUI[count];
		for(int i = 0; i < count;i++)//initialize 'count' GUIs
			frames[i] = new GameGUI(g,i);	
		for(int i = 0; i < count;i++)//make a loop
			frames[i].setNext(frames[(i+1)%count]);
		frames[0].startTurn();//begin the first player
	}

}
