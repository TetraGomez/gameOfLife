package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;

public class Cell
{
    private final Color ON_COLOR = Color.BLACK;
    private final Color OFF_COLOR = Color.LIGHT_GRAY;
    private final boolean SHOW_OFF_CELLS = true;

    // Constant storage of coordinates to ensure contract is met with the
    // Set interface (HashSet in Plane) for use with equals() and hashCode()
    private final int X;
    private final int Y;

    private Point pos;
    private boolean isOn;
    private boolean willBeOn;

    protected static final int DEFAULT_SIDE_LEN = 25;
    private static volatile int sideLen = DEFAULT_SIDE_LEN;

    public Cell(
            final Point pos,
            final boolean isOn)
    {
        this.pos = pos;
        this.X = pos.x;
        this.Y = pos.y;
        this.isOn = isOn;
        this.willBeOn = false;
    }

    // TODO: Method does not properly cull cells, fix this
    @SuppressWarnings("unused")
    private boolean isInBounds(final Point negOffset, final JFrame window)
    {
        Dimension drawBound = window.getSize();
        // Constants adjust left and top edges of rendering distance to be in
        // line with the edge of the window
        if (this.pos.x > negOffset.x - sideLen * 3 
                && this.pos.y > negOffset.y - sideLen * 3
                && this.pos.x < drawBound.width + sideLen + negOffset.x
                && this.pos.y < drawBound.height + sideLen + negOffset.y)
            return true;
        return false;
    }

    protected Point pos() { return new Point(this.pos); }
    protected boolean isOn() { return this.isOn; }
    protected void setIsOn(final boolean isOn) { this.isOn = isOn; }
    protected static int sideLen() { return Cell.sideLen; }
    protected static void setCellSideLen(final int s) { Cell.sideLen = s; }
    protected void update() { this.isOn = this.willBeOn; }
    
    protected int sumNeighbors(final Plane plane)
    {
        int sum = 0;
        for (Direction dir : Direction.values())
        {
            Point point = new Point(pos);
            point.translate(dir.relativeX(), dir.relativeY());
            Cell cell = plane.get(point);
            if (cell != null && cell.isOn)
                sum += 1;
        }
        return sum;
    }

    protected void mark(final Plane plane)
    {
        int sum = sumNeighbors(plane);
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

    protected void draw(
            final Graphics g,
            final Point offset,
            final Point negOffset,
            final JFrame window)
    {
        if (true) // isInBounds(negOffset, window))
        {
            int xDraw = pos.x * sideLen + offset.x;
            int yDraw = pos.y * sideLen + offset.y;
            if (this.isOn)
            {
                g.setColor(ON_COLOR);
                g.fillRect(xDraw, yDraw, sideLen, sideLen);
            }
            else if (SHOW_OFF_CELLS)
            {
                g.setColor(OFF_COLOR);
                g.fillRect(xDraw, yDraw, sideLen, sideLen);
            }
        }
    }

    @Override
    public int hashCode()
    {
        long bits = Double.doubleToLongBits(X);
        bits ^= Double.doubleToLongBits(Y);
        return ((int) bits) ^ ((int) bits >> 31);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Cell)
        {
            Cell cl = (Cell) obj;
            return (this.X == cl.X) && (this.Y == cl.Y);
        }
        return super.equals(obj);
    }
}