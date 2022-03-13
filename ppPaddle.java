package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/***
 * a class to create the graphic paddle representation
 */

public class ppPaddle extends Thread {
	double X;
	double Y;
	double Vx; //going to be zero at the beginning
	double Vy;
	GRect myPaddle;
	ppTable myTable;
	GraphicsProgram GProgram;
	ppBallA4 myBall;
	Color myColor;
	
	/**
	 * The constructor for the ppPaddle class copies parameters to instance variables
	 * @param X - the position of the paddle
	 * @param Y - the position of the paddle
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 * @param myBall - Graphics object representing ball
	 * @param myTable - an instance from the ppTable class creating the wall and the floor
	 */

	public ppPaddle(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		this.X = X;
		this.Y = Y; 
		this.myTable = myTable;
		this.GProgram = GProgram;
		this.Vx = 0;
		this.Vy = 0;
		//world
		double upperLeftX = X - ppPaddleW/2;
		double upperLeftY = Y + ppPaddleH/2;
		
		//p is in screen coords
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		
		//screen
		double ScrX = p.getX();
		double ScrY = p.getY();
		
		this.myPaddle = new GRect(ScrX, ScrY, ppPaddleW*Xs, ppPaddleH*Ys);
		
		myPaddle.setColor(myColor);
		myPaddle.setFilled(true);
		GProgram.add(myPaddle);
	}

	public void run() {
		double lastX = X;
		double lastY = Y;
		while (true) {
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			GProgram.pause(TICK*TSCALE); // Time to mS
		}
	}

	public void setP(GPoint P) {
		this.X = P.getX();
		this.Y = P.getY();

		double upperLeftX = X - ppPaddleW/2;
		double upperLeftY = Y + ppPaddleH/2;
		
		//p is in screen coords
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		//screen
		double ScrX = p.getX();
		double ScrY = p.getY();
		this.myPaddle.setLocation(ScrX,ScrY);

	}


	public GPoint getP() {
		return new GPoint(X,Y); 
	}

	public GPoint getV(){
		return new GPoint(Vx,Vy);
	}

	public boolean contact(double Sx, double Sy) {
		//logical operators and (&&), or (||), not (!)
		return (Sy >= Y - ppPaddleH/2) && (Sy <= Y + ppPaddleH/2);
	}

	public double getSgnVy() {
		if (Vy>=0)return 1;
		else return -1;
	}
	
}
