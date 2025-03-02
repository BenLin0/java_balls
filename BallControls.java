import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/*
input a new ball:
	 double PositionX;
	 double PositionY;
	 int DeltaX;		// +1 or -1
	 double DeltaY;	//tg of the direction. Delta Y / Delta x
	 int Radius;
	 Color BallColor;
	 double Speed;			//Initial speed:1----10. 1 is slowest.
*/

class BallControls extends Panel implements ActionListener 
{
	BallCanvas canvas;

	TextField PositionXField;
	TextField PositionYField;

	Choice DeltaXChoice = new Choice();		// +1 or -1
	TextField DeltaYField;		//tan of the direction. Delta Y / Delta x
	TextField RadiusField;
	TextField BallColorField;
	TextField SpeedField;

	Choice ColorChoice = new Choice();
	Label ResultLabel;
	
	public BallControls(BallCanvas canvas)
	{
		this.canvas = canvas;

		setLayout(new GridLayout(5, 4));
		Label tempLabel;

		tempLabel = new Label("PositionX");
		add(tempLabel);
		PositionXField = new TextField("140", 5);
		add(PositionXField);

		tempLabel = new Label("PositionY");
		add(tempLabel);
		PositionYField = new TextField("50", 5);
		add(PositionYField);

		tempLabel = new Label("DeltaX");
		add(tempLabel);
		DeltaXChoice.add("1");
		DeltaXChoice.add("-1");
		add(DeltaXChoice);

		tempLabel = new Label("DeltaY");
		add(tempLabel);
		DeltaYField = new TextField("0.7", 5);
		add(DeltaYField);

		tempLabel = new Label("Radius");
		add(tempLabel);
		RadiusField = new TextField("8", 5);
		add(RadiusField);


		tempLabel = new Label("Speed");
		add(tempLabel);
		SpeedField = new TextField("3", 5);
		add(SpeedField);

		tempLabel = new Label("BallColorX");
		add(tempLabel);
		ColorChoice.add("Green");
		ColorChoice.add("White");
		ColorChoice.add("Black");
		ColorChoice.add("Blue");
		ColorChoice.add("Cyan");
		ColorChoice.add("Gray");
		ColorChoice.add("Magenta");
		ColorChoice.add("Orange");
		ColorChoice.add("Pink");
		ColorChoice.add("Red");
		ColorChoice.add("Yellow");
		add(ColorChoice);


		Button btn = new Button("Add a new ball");
		btn.addActionListener(this);
		add(btn);
		btn = new Button("Delete last ball");
		btn.addActionListener(this);
		add(btn);

		ResultLabel = new Label("Ready");
		add(ResultLabel);
	}

	public void actionPerformed(ActionEvent ev)
	{
		String label = ev.getActionCommand();
		HideBall b;
		String choice;
		double PositionX;

		if (label.equals("Add a new ball"))
		{
			b = new HideBall();
			PositionX = Double.valueOf(PositionXField.getText()).doubleValue();
			b.setPositionX(PositionX);

			b.setPositionY(Double.valueOf(PositionYField.getText()).doubleValue());

			choice = DeltaXChoice.getSelectedItem();
			if (choice.equals("1")) b.setDeltaX(1);
			else if (choice.equals("-1")) b.setDeltaX(-1);

			b.setDeltaY(Double.valueOf(DeltaYField.getText()).doubleValue());
			b.setRadius(Integer.valueOf(RadiusField.getText()).intValue());
			b.setSpeed(Double.valueOf(SpeedField.getText()).doubleValue());

			choice = ColorChoice.getSelectedItem();
			if (choice.equals("White")) b.setBallColor(Color.white);
			else if (choice.equals("Black"))	b.setBallColor(Color.black);
			else if (choice.equals("Blue"))	b.setBallColor(Color.blue);
			else if (choice.equals("Cyan"))	b.setBallColor(Color.cyan);
			else if (choice.equals("Gray"))	b.setBallColor(Color.gray);
			else if (choice.equals("Green"))	b.setBallColor(Color.green);
			else if (choice.equals("Magenta"))	b.setBallColor(Color.magenta);
			else if (choice.equals("Orange"))	b.setBallColor(Color.orange);
			else if (choice.equals("Pink"))	b.setBallColor(Color.pink);
			else if (choice.equals("Red"))	b.setBallColor(Color.red);
			else if (choice.equals("Yellow"))	b.setBallColor(Color.yellow);
			
			//modify  position.
			PositionX -= 50;
			if (PositionX<50)
			{
				PositionX = 250;
			}
			PositionXField.setText(String.valueOf(PositionX));

			if (canvas.addBall(b))
				ResultLabel.setText("Add success.");
			else
				ResultLabel.setText("Add failed!");
		}
		if (label.equals("Delete last ball"))
		{
			if (canvas.delLastBall())
				ResultLabel.setText("Del success.");
			else
				ResultLabel.setText("Del failed!");
		}
	}
}
