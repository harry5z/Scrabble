package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;

public class Dictionary 
{
	private Set<String> dict;
	public Dictionary()
	{
		dict = new HashSet<>();
		new Thread() {
			@Override
			public void run()
			{
				init();
			}
		}.start();
	}
	public boolean isWord(String word)
	{
		return dict.contains(word);
	}

	private void init()
	{
		Scanner scanner;
		try 
		{
			scanner = new Scanner(new File("assets/words.txt"));
			while (scanner.hasNext()) {
				dict.add(scanner.next());
			}
			scanner.close();
		} 
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "File Not Found");
		}
	}
}
