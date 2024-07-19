package gameOfLife;

import java.math.BigInteger;

public class BigPoint
{
	private BigInteger x;
	private BigInteger y;

	public BigPoint()
	{
		this(new BigInteger(BigInteger.ZERO.toByteArray()), 
				new BigInteger(BigInteger.ZERO.toByteArray()));
	}
	
	public BigPoint(final BigPoint p)
	{
		this(p.x, p.y);
	}
	
	public BigPoint(
			final BigInteger x, 
			final BigInteger y)
	{
		this.x = new BigInteger(x.toByteArray());
		this.y = new BigInteger(y.toByteArray());
	}
	
	public BigInteger getX()
	{
		return new BigInteger(this.x.toByteArray());
	}
	
	public BigInteger getY()
	{
		return new BigInteger(this.y.toByteArray());
	}
	
	public BigPoint getLocation()
	{
		return new BigPoint(x, y);
	}
	
	public void setLocation(
			final int x,
			final int y)
	{
		this.x = BigInteger.valueOf(x);
		this.y = BigInteger.valueOf(y);
	}
	
	public void setLocation(
			final double x,
			final double y)
	{
		this.x = BigInteger.valueOf((int) Math.floor(x + 0.5));
		this.y = BigInteger.valueOf((int) Math.floor(y + 0.5));
	}
	
	public void setLocation(
			final BigInteger x,
			final BigInteger y)
	{
		move(x, y);
	}
	
	public void move(
			final BigInteger dx,
			final BigInteger dy)
	{
		this.x = new BigInteger(x.toByteArray());
		this.y = new BigInteger(y.toByteArray());
	}
	
	public void translate(
			final BigInteger dx,
			final BigInteger dy)
	{
		this.x.add(dx);
		this.y.add(dy);
	}
	
	@Override
	public int hashCode()
	{
		int result = 0;
		result = 31 * result + x.hashCode();
		result = 31 * result + y.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof BigPoint)
		{
			BigPoint bp = (BigPoint) obj;
			return (x.equals(bp.x) && y.equals(bp.y));
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString()
	{
		return getClass().getName() + "[x=" + x.toString() + ",y" + y.toString() + "]";
	}
}
