package gameOfLife;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AppWindow implements Runnable
{
	private final int HEIGHT = 480;
	private final int WIDTH = 640;
	
	private final JFrame window;
	private final GamePanel game;
	private final Plane plane;
	private final MouseHandler mouse;
	private final ImageIcon icon;
	
	public AppWindow()
	{
		this.window = new JFrame();
		this.plane = new Plane(window);
		this.game = new GamePanel(plane);
		this.mouse = new MouseHandler(plane, game);
		this.icon = new ImageIcon("resources/conway.png");
		configure();
	}
	
	private void configure()
	{
		game.addMouseListener(mouse);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Game Of Life");
		window.setIconImage(icon.getImage());
		window.add(game);
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	public void run() 
	{
		while (true)
		{
			plane.tick();
			game.repaint();
		}
	}
	
}
