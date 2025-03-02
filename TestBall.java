//import java.applet.*;
import java.awt.*;
/* an Applet to test HideBall.java.
result: http://fadshop.net/resume/javademo/ball.htm

*/

public class TestBall  implements Runnable {
	BallCanvas ballCanvas;
	BallControls ballControl;
	Thread RunThread;

	public void start()
	{
		if (RunThread == null)
		{
			RunThread = new Thread(this);
			RunThread.start();	//start
		}
	}

	public void init()
	{
		ballCanvas = new BallCanvas();
		ballControl = new BallControls(ballCanvas);
		
		setLayout(new BorderLayout());
		add("Center", ballCanvas);
		add("North", ballControl);
	}

	public void run()
	{
		while(true)
		{
			ballCanvas.moveStep();
			try{
	            Thread.sleep( 20 );  // interval given in milliseconds
			}catch(Exception sleepProblem)
			{
			}
         }
	}
   
}
