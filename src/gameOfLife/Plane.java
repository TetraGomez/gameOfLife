package gameOfLife;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;

public class Plane 
{
	private volatile Point offset;
	private volatile Point negOffset;
	private JFrame window;
	
	private Hashtable<Point, Cell> onCells;
	
	public Plane(
			final JFrame window,
			final boolean[][] seed)
	{
		this.window = window;
		this.onCells = new Hashtable<Point, Cell>();
		setOffset(new Point());
		
		initCells(seed);
	}
	
	private void initCells(boolean[][] seed)
	{
		Point p = null;
		
		if (seed != null)
			for (int i = 0; i < seed.length; i++)
				for (int k = 0; k < seed[0].length; k++)
					if (seed[i][k] == true)
					{
						p = new Point(i, k);
						onCells.put(p, new Cell(p));
					}
	}
	
	private void drawGrid(final Graphics g)
	{
		int side = Cell.sideLen();
		Point offset = new Point(this.offset);
		Point modOffset = new Point(offset.x % side, offset.y % side);
		Dimension dim = window.getSize();
		int numCellsWidth = dim.width / side;
		int numCellsHeight = dim.height / side;
		
		// Draw grid aligned with cell positions, adjust drawing amount and starting
		// position so that there is no gap on the edges of the screen.
		for (int i = 0; i < numCellsWidth + 2; i++)
			for (int k = 0; k < numCellsHeight + 2; k++)
				g.drawRect(
						(i - 1) * side + modOffset.x, 
						(k - 1) * side + modOffset.y, 
						side, side);
	}
	
	private void drawCells(final Graphics g)
	{
		Point offset = new Point(this.offset);
		Point negOffset = new Point(this.negOffset);
		Cell c = null;
		
		Enumeration<Cell> e = onCells.elements();
		while (e.hasMoreElements())
		{
			c = e.nextElement();
			c.draw(g, offset, negOffset, window);
		}
	}
	
	protected synchronized void setOffset(final Point offset) 
	{
		this.offset = offset;
		this.negOffset = new Point(0 - offset.x, 0 - offset.y);
	}
	
	protected Point offset() { return this.offset; }
	protected Point negOffset() { return this.negOffset; }
	
	protected void draw(final Graphics g)
	{				
		drawGrid(g);
		drawCells(g);
	}
	
	protected void tick()
	{
		
	}
}

/*
protected Cell get(
			final int x,
			final int y)
{
	Quadrant q = Quadrant.find(x, y);
	int fixedX = Math.abs(x + q.relativeX());
	int fixedY = Math.abs(y + q.relativeY());
	
	return quadrants.get(q.ordinal()).get(fixedX).get(fixedY);
}
*/