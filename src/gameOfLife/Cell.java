package gameOfLife;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;

public class Cell 
{
	private boolean isOn;
	private boolean willBeOn;

	private Point pos;
	private Quadrant quadrant;
	
	protected static final int DEFAULT_SIDE_LEN = 25;
	private static volatile int sideLen = DEFAULT_SIDE_LEN;
	
	public Cell(
			final Point pos,
			final Quadrant quadrant)
	{
		this.isOn = false;
		this.willBeOn = false;
		this.quadrant = quadrant;
		this.pos = drawPos(pos);
	}
	
	private Point drawPos(final Point pos)
	{
		return new Point(
				pos.x * sideLen - this.quadrant.relativeX() * sideLen,
				pos.y * sideLen - this.quadrant.relativeY() * sideLen);
	}
	
	private int sumNeighbors()
	{
		/*
		int sum = 0;
		for (Cell cell : neighbors)
			if (cell.isOn)
				sum += 1;
		return sum;
		*/
		return 0;
	}
	
	private boolean isInBounds(
			final Point negOffset,
			final JFrame window)
	{
		boolean inBounds = false;
		
		Dimension drawBound = window.getSize();
		drawBound.setSize(new Dimension(
				drawBound.width + sideLen,
				drawBound.height + sideLen));
		
		if (this.pos.x > negOffset.x - sideLen && this.pos.y > negOffset.y - sideLen
				&& this.pos.x < drawBound.width + negOffset.x
				&& this.pos.y < drawBound.height + negOffset.y)
			inBounds = true;

		return inBounds;
	}
	
	protected void prepareTick()
	{
		int sum = sumNeighbors();
		if (isOn)
		{
			if (sum < 2 || sum > 3)
				this.willBeOn = false;
			else
				this.willBeOn = true;
		}
		else if (sum == 3)
			this.willBeOn = true;
	}
	
	protected void tick() { this.isOn = this.willBeOn; }
	protected static void setCellSideLen(final int sideLen) { Cell.sideLen = sideLen; }
	
	protected void draw(
			final Graphics g, 
			final Point offset,
			final Point negOffset,
			final JFrame window)
	{		
		if (isInBounds(negOffset, window))
		{
			int xDraw = pos.x + offset.x;
			int yDraw = pos.y + offset.y;
			
			g.drawRect(xDraw, yDraw, sideLen, sideLen);
			if (isOn)
				g.fillRect(xDraw, yDraw, sideLen, sideLen);
		}
	}
}
