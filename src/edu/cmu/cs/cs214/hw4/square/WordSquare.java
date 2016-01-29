package edu.cmu.cs.cs214.hw4.square;

import edu.cmu.cs.cs214.hw4.core.Point;

public class WordSquare extends PremiumSquare
{
	public WordSquare(Point p, int m)
	{
		super(p,m);
	}
	/**
	 * return score * multiplier if not used
	 * return score if used
	 * @param score
	 * @return new score after multiplication
	 */
	public int multiplyScore(int score)
	{
		if(isUsed())
			return score;
		setUsed();
		return score * getMultiplier();
	}
}
