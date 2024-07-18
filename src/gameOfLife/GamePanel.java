package gameOfLife;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel
{
	private static final long serialVersionUID = -4147541065232569100L;
	private Plane plane;
	
	public GamePanel(final Plane plane)
	{
		this.plane = plane;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.plane.draw(g);
	}
}
