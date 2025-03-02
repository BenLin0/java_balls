/*
BallCanvas, add balls in here.
result: http://fadshop.net/resume/javademo/ball.htm

*/

import java.applet.*;
import java.awt.*;


public class BallCanvas extends Canvas 
{
	final int MAXBALLNUM = 20;
	HideBall[] ballList = new HideBall[MAXBALLNUM];
	int width, height;
   Image backbuffer;
   Graphics backg;

	private int ballNum;


	private void CheckCollision(HideBall[] b, int iBallNum)
	{
	   int i, j;

	   for (i=0; i<iBallNum-1; i++)
	   {
		   for (j=i+1; j<iBallNum; j++)
		   {
			   b[i].CheckCollision(b[j]);
		   }
	   }

	}

	public void moveStep()
	{
		CheckCollision(ballList, ballNum);
		for (int i=0; i<ballNum; i++)
		{
			ballList[i].step();
		}		//next i;
		repaint();
		
	}

	public boolean addBall(HideBall b)
	{

		if (ballNum == MAXBALLNUM)
		{
			System.out.println("ballNum == MAXBALLNUM, cannot add another one.");
			return false;
		}
		//add
		b.setBorder(0, 0, width, height);
		if (b.Enable(true))
		{
			System.out.println("ok!");
			ballList[ballNum] = b;
			ballNum++;
			return true;
		}
		else
		{
			System.out.println("cannot enable ball.");
			return false;
		}
	}

	public boolean delLastBall()
	{
		if (ballNum > 0)
		{
			ballNum --;
			return true;
		}
		else
			return false;
	}

   // Executed whenever the applet is asked to redraw itself.
	public void update (Graphics g)
	{
		// Initial
		if (backbuffer == null)
		{
			width = getSize().width;
			height = getSize().height;
			backbuffer = createImage (width, height);
			backg = backbuffer.getGraphics ();
		}

		// clear backg.
		backg.setColor (getBackground ());
		backg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		// paint in backg.
		paint (backg);

		// show the backg.
		g.drawImage (backbuffer, 0, 0, this);
	}

	public void paint( Graphics g )		
	{	// paint in backbuffer.
		g.clearRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width-1, height-1);
		for (int i=0; i<ballNum; i++)
		{
			ballList[i].drawBall(g);
		}
	}
}