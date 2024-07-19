package gameOfLife;

public class Main 
{
	public static void main(String[] args)
	{
		char[][] seed = 
			{
					{ 'O', 'O', '.' },
					{ '.', 'O', 'O' },
					{ '.', 'O', '.' }
			};
		AppWindow app = new AppWindow(seed);
		app.run();
	}
}
