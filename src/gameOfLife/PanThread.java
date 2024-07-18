package gameOfLife;

import java.awt.MouseInfo;
import java.awt.Point;

public class PanThread extends Thread
{
	private Plane plane;
	private GamePanel game;
	private boolean isPanning;
	
	public PanThread(
			final Plane plane,
			final GamePanel game)
	{
		this.plane = plane;
		this.game = game;
		this.isPanning = true;
	}
	
	public void stopPanning() { this.isPanning = false; }
	
	public void run()
	{
		Point current = null;
		Point last = null;
		
		while (isPanning)
		{
			current = MouseInfo.getPointerInfo().getLocation();
			if (last != null)
			{
				Point offset = plane.offset();
				plane.setOffset(new Point(
						offset.x + (current.x - last.x), 
						offset.y + (current.y - last.y)));
			}
			last = current;
			game.repaint();
		}
	}	
}
