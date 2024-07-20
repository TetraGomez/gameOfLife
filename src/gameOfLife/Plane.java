package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JFrame;

public class Plane
{
    private final Color GRID_COLOR = Color.BLACK;

    private volatile Point offset;
    private volatile Point negOffset;
    private JFrame window;

    private Hashtable<Point, Cell> trackedCells;
    private HashSet<Cell> newCells;
    private HashSet<Cell> removeCells;

    public Plane(final JFrame window, final boolean[][] seed)
    {
        this.window = window;
        this.trackedCells = new Hashtable<Point, Cell>();
        this.newCells = new HashSet<Cell>();
        this.removeCells = new HashSet<Cell>();
        setOffset(new Point());

        initCells(seed);
    }

    private void initCells(boolean[][] seed)
    {
        if (seed != null)
        {
            for (int i = 0; i < seed.length; i++)
                for (int k = 0; k < seed[0].length; k++)
                    if (seed[i][k] == true)
                    {
                        Point point = new Point(k, i);
                        trackedCells.put(point, new Cell(point, true));
                    }
            stageCells();
            updateCells();
        }
    }

    private void stageCells()
    {
        Enumeration<Cell> e = this.trackedCells.elements();
        while (e.hasMoreElements())
        {
            Cell cell = e.nextElement();
            // If a cell is on, we want to track all of its neighbors as off cells
            if (cell.isOn())
                for (Direction dir : Direction.values())
                {
                    Cell newCell = Direction.translate(cell, dir);
                    newCell.setIsOn(false);
                    if (!this.trackedCells.containsKey(newCell.pos()))
                        this.newCells.add(newCell);
                }
            // If a cell is off, we want to stop tracking it unless it is
            // neighboring an on cell
            else if (cell.sumNeighbors(this) == 0)
                this.removeCells.add(cell);
        }
    }

    private void updateCells()
    {
        for (Cell newCell : this.newCells)
            this.trackedCells.put(newCell.pos(), newCell);
        for (Cell remCell : this.removeCells)
            this.trackedCells.remove(remCell.pos());
        this.newCells.clear();
        this.removeCells.clear();
    }

    private void markState()
    {
        Enumeration<Cell> e = trackedCells.elements();
        while (e.hasMoreElements())
            e.nextElement().mark(this);
    }

    private void updateState()
    {
        Enumeration<Cell> e = trackedCells.elements();
        while (e.hasMoreElements())
            e.nextElement().update();
    }

    private void drawGrid(final Graphics g)
    {
        int side = Cell.sideLen();
        Point offset = new Point(this.offset);
        Point modOffset = new Point(offset.x % side, offset.y % side);
        Dimension dim = window.getSize();
        int numCellsWidth = dim.width / side;
        int numCellsHeight = dim.height / side;

        // Draw grid aligned with cell positions, constants adjust drawing
        // amount andstarting position so that there is no gap on the edges
        // of the screen.
        for (int i = 0; i < numCellsWidth + 2; i++)
            for (int k = 0; k < numCellsHeight + 2; k++)
            {
                g.setColor(GRID_COLOR);
                g.drawRect(
                        (i - 1) * side + modOffset.x, 
                        (k - 1) * side + modOffset.y, 
                        side, side);
            }
    }

    private void drawCells(final Graphics g)
    {
        Point offset = new Point(this.offset);
        Point negOffset = new Point(this.negOffset);

        Enumeration<Cell> e = trackedCells.elements();
        while (e.hasMoreElements())
        {
            Cell c = e.nextElement();
            c.draw(g, offset, negOffset, window);
        }
    }

    protected synchronized void setOffset(final Point offset)
    {
        this.offset = offset;
        this.negOffset = new Point(0 - offset.x, 0 - offset.y);
    }

    protected synchronized void centerOrigin()
    {
        Dimension dim = this.window.getSize();
        setOffset(new Point(
                dim.width / 2 - Cell.sideLen(), 
                dim.height / 2 - Cell.sideLen()));
    }

    protected Point offset() { return this.offset; }
    protected Point negOffset() { return this.negOffset; }
    protected Cell get(final Point pos) { return trackedCells.get(pos); }
    protected void stageCellAdd(final Cell c) { this.newCells.add(c); }
    protected void stageCellRemove(final Cell c) { this.removeCells.add(c); }

    protected synchronized void draw(final Graphics g)
    {
        drawCells(g);
        drawGrid(g);
    }

    protected synchronized void tick()
    {
        markState();
        updateState();
        stageCells();
        updateCells();
    }
}