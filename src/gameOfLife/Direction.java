package gameOfLife;

import java.awt.Point;

public enum Direction
{
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);

    private int relativeX;
    private int relativeY;

    private Direction(final int relativeX, final int relativeY)
    {
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    private Direction rot(final int rotation)
    {
        Direction[] dirs = Direction.values();
        int i = (this.ordinal() + rotation) % dirs.length;
        if (i < 0)
            i = dirs.length + i;
        return dirs[i];
    }

    public Direction opposite() { return rot(Direction.values().length / 2); }
    public Direction clockwise() { return rot(1); }
    public Direction counterClockwise() { return rot(-1); }

    public static Cell translate(final Cell cell, Direction dir)
    {
        return new Cell(new Point(
                cell.pos().x + dir.relativeX,
                cell.pos().y + dir.relativeY), cell.isOn());
    }

    public int relativeX() { return this.relativeX; }
    public int relativeY() { return this.relativeY; }
}