package gameOfLife;

public class Main
{
    static char[][] glider = 
        { 
                { 'O', '.', '.' }, 
                { '.', 'O', 'O' }, 
                { 'O', 'O', '.' } 
        };
    
    static char[][] expander = 
        { 
                { '.', 'O', '.' }, 
                { '.', 'O', 'O' }, 
                { 'O', 'O', '.' } 
        };

    public static void main(String[] args)
    {
        AppWindow app = new AppWindow(expander);
        app.run();
    }
}