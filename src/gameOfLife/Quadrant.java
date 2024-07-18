package gameOfLife;

public enum Quadrant {
	I(0, 0),
	II(1, 0),
	III(1, 1),
	IV(0, 1);
	
	private int relativeX;
	private int relativeY;
	
	private Quadrant(
			final int relativeX,
			final int relativeY)
	{
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}
	
	public int relativeX() { return this.relativeX; }
	public int relativeY() { return this.relativeY; }
	
	public int getXParity()
	{
		int xParity = 0;
		if (this.relativeX == 0)
			xParity = 1;
		else
			xParity = -1;
		return xParity;
	}
	
	public int getYParity()
	{
		int yParity = 0;
		if (this.relativeY == 0)
			yParity = 1;
		else
			yParity = -1;
		return yParity;
	}
	
	// TODO: This method doesn't seem to properly align with the origin (input 0, 0; etc)
	public static Quadrant find(
			final int x,
			final int y)
	{
		Quadrant q = null;
		if (x > 0)
			if (y > 0)
				q = Quadrant.I;
			else // y <= 0
				q = Quadrant.IV;
		else // x <= 0
			if (y > 0)
				q = Quadrant.II;
			else // y <= 0
				q = Quadrant.III;
		return q;
	}
}
