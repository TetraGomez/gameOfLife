package gameOfLife;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AppWindow implements Runnable
{
	public static final char ON = 'O';
	public static final char OFF = '.';
	
	private final int HEIGHT = 480;
	private final int WIDTH = 640;
	
	private final JFrame window;
	private final GamePanel game;
	private final Plane plane;
	private final MouseHandler mouse;
	private final ImageIcon icon;
	
	public AppWindow(final char[][] seed)
	{
		this.window = new JFrame();
		this.plane = new Plane(window, AppWindow.charSeedToBool(seed));
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
	
	public static boolean seedIsValid(final char[][] seed)
	{
		boolean isValid = true;
		int size = seed[0].length;
		for (char[] arr : seed)
			if (arr.length != size)
				isValid = false;
		return isValid;
	}
	
	public static boolean[][] charSeedToBool(final char[][] seed)
	{
		boolean[][] result = null;
		if (seed.length == 0 || seed[0].length == 0 || !seedIsValid(seed))
			System.err.println("Invalid seed dimension. No seed loaded.");
		else
		{
			result = new boolean[seed.length][seed[0].length];
			for (int i = 0; i < seed.length; i++)
				for (int k = 0; k < seed[0].length; k++)
					if (seed[i][k] == ON)
						result[i][k] = true;
					else if (seed[i][k] != OFF)
						System.err.println("Invalid value in seed. "
								+ "Setting cell in its position to off state.");
		}
		return result;
	}
}
