package gameOfLife;

public enum Direction 
{
	N(0, -1),
	NE(1, -1),
	E(-1, 0),
	SE(1, 1),
	S(0, 1),
	SW(-1, 1),
	W(-1, 0),
	NW(-1, -1);
	
	private int relativeX;
	private int relativeY;
	
	private Direction(
			final int relativeX,
			final int relativeY)
	{
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}
	
	private Direction rotate(final int rotation)
	{
		Direction[] dirs = Direction.values();
		int i = (this.ordinal() + rotation) % dirs.length;
		if (i < 0)
			i = dirs.length + i;
		return dirs[i];
	}
	
	public Direction opposite() { return rotate(Direction.values().length / 2); }
	public Direction clockwise() { return rotate(1); }
	public Direction counterClockwise() { return rotate(-1); }
	
	public int relativeX() { return this.relativeX; }
	public int relativeY() { return this.relativeY; }
}
