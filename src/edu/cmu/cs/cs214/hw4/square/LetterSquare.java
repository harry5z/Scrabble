package edu.cmu.cs.cs214.hw4.square;

import edu.cmu.cs.cs214.hw4.core.Point;

public class LetterSquare extends PremiumSquare
{
	public LetterSquare(Point p,int m)
	{
		super(p,m);
	}
	@Override
	public int getScore()
	{
		if(!isUsed())//if not used yet
		{
			setUsed();//make it "used"
			return super.getScore() * getMultiplier();
		}
		else
			return super.getScore();
	}
}
