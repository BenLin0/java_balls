import javax.swing.JFrame;
//import javax.swing.BORder;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.BorderLayout;



public class SwingSample extends JFrame implements Runnable  {
    BallCanvas ballCanvas;
    BallControls ballControl;
    Thread RunThread;

    public SwingSample(){
        setTitle("Canvas Example");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.ballCanvas = new BallCanvas();
		this.ballControl = new BallControls(ballCanvas);
        //ballCanvas.update()


        add( ballControl, BorderLayout.NORTH);
        add( ballCanvas, BorderLayout.CENTER);

    	

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }
        });

        setVisible(true);
        if (this.RunThread == null) {
            this.RunThread = new Thread(this);
            this.RunThread.start();
         }
    }

    
   public void run() {
        while(true) {
        this.ballCanvas.moveStep();

        try {
            Thread.sleep(100L);
        } catch (Exception var2) {
        }
        }
    }

    public static void main(String[] args) {
        new SwingSample();
    }
}