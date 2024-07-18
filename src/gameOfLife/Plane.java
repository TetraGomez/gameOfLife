package gameOfLife;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Plane 
{	
	private final int INIT_AMOUNT = 500;
	
	private ArrayList<ArrayList<ArrayList<Cell>>> quadrants;
	private volatile Point offset;
	private volatile Point negOffset;
	private int size;
	private JFrame window;
	
	public Plane(final JFrame window)
	{
		this.quadrants = new ArrayList<ArrayList<ArrayList<Cell>>>();
		this.size = INIT_AMOUNT;
		this.window = window;
		
		setOffset(new Point(0, 0));
		initQuadrants();
		initCells();
	}
	
	private void initQuadrants()
	{
		for (Quadrant q : Quadrant.values())
		{
			// Add a quadrant
			quadrants.add(new ArrayList<ArrayList<Cell>>());
			// Initialize quadrant columns
			for (int i = 0; i < INIT_AMOUNT; i++)
				quadrants.get(q.ordinal()).add(new ArrayList<Cell>());
		}
	}
	
	private void initCells()
	{
		for (int i = 0; i < INIT_AMOUNT; i++)
			for (int k = 0; k < INIT_AMOUNT; k++)
				for (Quadrant q : Quadrant.values())
					quadrants.get(q.ordinal()).get(i).add(
							new Cell(new Point(
									i * q.getXParity(), 
									k * q.getYParity()), q));
	}
	
	private void addCells()
	{
		for (Quadrant q : Quadrant.values())
		{
			ArrayList<ArrayList<Cell>> planeQ = quadrants.get(q.ordinal());
			// Add new column
			planeQ.add(new ArrayList<Cell>());
			// Add cells up to the corner
			for (int i = 0; i < this.size; i++)
			{
				// Fill new row
				planeQ.get(i).add(
						new Cell(new Point(
								this.size * q.getXParity(), 
								i * q.getYParity()), q));
				// Fill new column
				planeQ.get(this.size).add(
						new Cell(new Point(
								i * q.getXParity(), 
								this.size * q.getYParity()), q));
			}
			// Add last cell
			planeQ.get(this.size).add(
					new Cell(new Point(
							this.size * q.getXParity(), 
							this.size * q.getYParity()), q));
		}
		this.size += 1;
	}
	
	protected Cell get(
			final int x,
			final int y)
	{
		Quadrant q = Quadrant.find(x, y);
		int fixedX = Math.abs(x + q.relativeX());
		int fixedY = Math.abs(y + q.relativeY());
		
		return quadrants.get(q.ordinal()).get(fixedX).get(fixedY);
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
		Point offset = new Point(this.offset);
		Point negOffset = new Point(this.negOffset);
		
		for (ArrayList<ArrayList<Cell>> planeQ : quadrants)
			for (ArrayList<Cell> col : planeQ)
				for (Cell c : col)
					c.draw(g, offset, negOffset, this.window);
	}
	
	protected void tick()
	{
		
	}
}
