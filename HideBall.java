import java.awt.*;
import java.math.*;
/* Kernel of this program
define the attributes and methods of a ball.
result: http://fadshop.net/resume/javademo/ball.htm
Author: Ben (ben@fadshop.net)
16, April, 2002

18, April: changed: add a DrawBall in this class, and delete "SavedColor".
*/

class HideBall 
{
	private double PositionX;
	private double PositionY;

	private int DeltaX;		// +1 or -1
	private double DeltaY;	//tg of the direction. Delta Y / Delta x
	private int Radius;
	private int Radius3;		//Radius3 = Radius^3, ~ mass.
	private Color BallColor;
//	private Color SavedColor;
	private double Speed;
	private boolean ready;
	private boolean colliding;	// if colliding, use Color.white to show it.
	
	private int BorderStartX;
	private int BorderStartY;
	private int BorderEndX;
	private int BorderEndY;

	public void HideBall()
	{
		colliding = false;
		ready = false;				//Not ready before Border, Radius, Delta are set.
	}
	
	public void setPositionX(double x){	PositionX = x;	}
	public double getPositionX(){	return PositionX;	}

	public void setPositionY(double x){	PositionY = x;	}
	public double getPositionY(){	return PositionY;	}

	public void setDeltaX(int x){	
		if (x>0)	DeltaX = 1;
		else		DeltaX = -1;
		}
	public int getDeltaX(){		return DeltaX;	}

	public void setDeltaY(double x){	DeltaY = x;	}
	public double getDeltaY(){		return DeltaY;	}

	public void setSpeed(double x){	Speed = x;	}
	public double getSpeed(){		return Speed;	}

	public void setRadius(int x){	Radius = x; Radius3 = Radius*Radius*Radius;	}
	public int getRadius(){		return Radius;	}
	public int getRadius3(){		return Radius3;	}

	public void setBallColor(Color x){	BallColor = x;	}
//	public void changeBallColor(Color x){	BallColor = x;	}
//	public Color getBallColor(){		return BallColor;	}
//	public Color getSavedColor(){		return SavedColor;	}
	public void setColliding(boolean x){	colliding = x;	}

	public void setBorder(int StartX, int StartY, int EndX, int EndY){
		if (StartX < EndX)
		{
			BorderStartX = StartX;
			BorderEndX = EndX;
		}
		else
		{
			BorderStartX = EndX;
			BorderEndX = StartX;
		}
		if (StartY < EndY)
		{
			BorderStartY = StartY;
			BorderEndY = EndY;
		}
		else
		{
			BorderStartY = EndY;
			BorderEndY = StartY;
		}
	}

	public boolean Enable(boolean bEnable)
	{	//Not ready before Border, Radius, DeltaX are set.
		//maybe I should check if (PositionX, PositionY) is located between Border.
		if (bEnable)
		{
			if ( (BorderStartX < BorderEndX) &&
				(BorderStartY < BorderEndY) &&
				(Radius > 0) &&
				( (DeltaX == 1) || (DeltaX == -1) ) )
			{
				ready = true;
			}
			return ready;
		}
		else
		{
			ready = false;
			return true;	// success.
		}
	}

	public boolean IsReady()
	{
		return ready;
	}

	public boolean step() 
	{
		if (ready == false)
		{
			return false;
		}
		PositionX = PositionX + Speed * DeltaX;
		if (PositionX+Radius > BorderEndX)
		{
			DeltaX = (-1) * DeltaX;
			PositionX = BorderEndX - (PositionX - BorderEndX) - 2*Radius;
			colliding = true;
		}
		if (PositionX-Radius < BorderStartX)
		{
			DeltaX = (-1) * DeltaX;
			PositionX = BorderStartX + (BorderStartX - PositionX) + 2*Radius;
			colliding = true;
		}

		PositionY = PositionY + (Speed * DeltaY);
		if (PositionY+Radius > BorderEndY)
		{
			DeltaY = (-1) * DeltaY;
			PositionY = BorderEndY - (PositionY - BorderEndY) - 2*Radius;
			colliding = true;
		}
		if (PositionY-Radius < BorderStartY)
		{
			DeltaY = (-1) * DeltaY;
			PositionY = BorderStartY + (BorderStartY - PositionY) + 2*Radius;
			colliding = true;
		}
		return true;
	}
//After collict,	x2 = (m2*v2+2*m1*v1-m1*v2)/(m1+m2);
//					x1 = (m1*v1+2*m2*v2-m2*v1)/(m1+m2)
//		m ~ r^3;
	public boolean CheckCollision(HideBall ballAnother)
	{
		if (ready == false)
		{
			return false;
		}
		double DistanceSquare ;
		double vOriginalX1, vOriginalY1, vOriginalX2, vOriginalY2;
		double vCurrentX1, vCurrentY1, vCurrentX2, vCurrentY2;
		DistanceSquare = (PositionX-ballAnother.getPositionX())*(PositionX-ballAnother.getPositionX()) +
			(PositionY-ballAnother.getPositionY())*(PositionY-ballAnother.getPositionY());
		if (DistanceSquare < (Radius+ballAnother.getRadius())*(Radius+ballAnother.getRadius()))	//collision occured.
		{
//			System.out.println("Clision! Data:");
//			System.out.println("x=" + (PositionX-ballAnother.getPositionX) + "y=" + (PositionY-ballAnother.getPositionY));
//			System.out.println(" Radius=" + Radius + " AnotherRadius=" + ballAnother.getRadius);
//			System.out.println(" Speed=" + Speed + " AnotherSpeed=" + ballAnother.getSpeed);
			vOriginalX1 = Speed * DeltaX;
			vOriginalY1 = Speed * DeltaY;
			vOriginalX2 = ballAnother.getSpeed() * ballAnother.getDeltaX();
			vOriginalY2 = ballAnother.getSpeed() * ballAnother.getDeltaY();

			vCurrentX2 = (ballAnother.getRadius3() * vOriginalX2 + 2 * Radius3 * vOriginalX1 - Radius3*vOriginalX2)
				/ (Radius3 + ballAnother.getRadius3());
			vCurrentX1 = (Radius3 * vOriginalX1 + 2 * ballAnother.getRadius3() * vOriginalX2 - ballAnother.getRadius3() * vOriginalX1)
				/ (Radius3 + ballAnother.getRadius3());

			vCurrentY2 = (ballAnother.getRadius3() * vOriginalY2 + 2 * Radius3 * vOriginalY1 - Radius3*vOriginalY2)
				/ (Radius3 + ballAnother.getRadius3());
			vCurrentY1 = (Radius3 * vOriginalY1 + 2 * ballAnother.getRadius3() * vOriginalY2 - ballAnother.getRadius3() * vOriginalY1)
				/ (Radius3 + ballAnother.getRadius3());
			if (vCurrentX1 > 0)
			{
				DeltaX = 1;
				Speed = vCurrentX1;
			}
			else
			{
				DeltaX = -1;
				Speed = (-1) * vCurrentX1;
			}
			DeltaY = vCurrentY1 / Speed;

			if (vCurrentX2 > 0)
			{
				ballAnother.setDeltaX( 1 );
				ballAnother.setSpeed( vCurrentX2 );
			}
			else
			{
				ballAnother.setDeltaX( -1 );
				ballAnother.setSpeed( (-1) * vCurrentX2 );
			}
			ballAnother.setDeltaY( vCurrentY2 / ballAnother.getSpeed());

			colliding = true;
			ballAnother.setColliding( true );
			int iCalNum = 0;
			while(DistanceSquare < (Radius+ballAnother.getRadius())*(Radius+ballAnother.getRadius()) && (iCalNum<10))
			{	iCalNum++;
				step();
				ballAnother.step();
				DistanceSquare = (int)((PositionX-ballAnother.getPositionX())*(PositionX-ballAnother.getPositionX()) +
					(PositionY-ballAnother.getPositionY())*(PositionY-ballAnother.getPositionY()));
			}
			if (iCalNum > 3)
			{
				System.out.println("iCalNum="+iCalNum);
			}
		}

		return true;	//ok
	}

	public void drawBall (Graphics g)
	{
		if (colliding == true)
		{
			g.setColor (Color.white);
			colliding = false;
		}
		else
		{
			g.setColor (BallColor);
		}
		g.fillOval ((int)PositionX - Radius, (int)PositionY - Radius, 2 * Radius, 2 * Radius);
	}

}
