package gameOfLife;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter
{
    private Plane plane;
    private GamePanel game;
    private PanThread pan;

    public MouseHandler(final Plane plane, final GamePanel game)
    {
        this.plane = plane;
        this.game = game;
        this.pan = null;
    }

    public void mousePressed(MouseEvent e)
    {
        this.pan = new PanThread(this.plane, this.game);
        this.pan.start();
    }

    public void mouseReleased(MouseEvent e)
    {
        this.pan.stopPanning();
        try
        {
            this.pan.join();
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
    }
}