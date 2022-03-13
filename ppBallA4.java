package ppPackage;
import acm.graphics.*;
import acm.program.*;

import static ppPackage.ppSimParams.*;

import java.awt.*;

/**
 * A class that is mainly responsible for the physics of collision 
 * Throughout the simulation.
 * @author yongr
 *
 */

public class ppBallA4 extends Thread {
	//private static double ScrX = 0;
	//private static double ScrY = 0;

	private double Xinit; // Initial position of ball - X
	private double Yinit; // Initial position of ball - Y
	private double Vo; // Initial velocity (Magnitude)
	private double theta; // Initial direction
	private double loss; // Energy loss on collision
	Color color; // Color of ball
	GraphicsProgram GProgram; // Instance of ppSim class (this)
	GOval myBall; // Graphics object representing ball
	ppPaddle RPaddle;
	ppTable myTable;
	ppPaddleAgent LPaddle;

	double Vx;
	double Vy;
	double X, Xo;
	double Y, Yo;
	boolean running;

	/**
	 * The constructor for the ppBall class copies parameters to instance variables, creates an
	 * instance of a GOval to represent the ping-pong ball, and adds it to the display.
	 *
	 * @param Xinit - starting position of the ball X (meters)
	 * @param Yinit - starting position of the ball Y (meters)
	 * @param Vo - initial velocity (meters/second)
	 * @param theta - initial angle to the horizontal (degrees)
	 * @param loss - loss on collision ([0,1])
	 * @param color - ball color (Color)
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 * @param myBall - Graphics object representing ball
	 * @param RPaddle - Graphics object representing the paddle
	 * @param myTable - an instance from the ppTable class creating the wall and the floor
	 */

	public ppBallA4(double Xinit, double Yinit, double Vo, double theta, double loss, Color color,  GraphicsProgram GProgram, ppTable myTable){

		this.Xinit=Xinit; // Copy constructor parameters to instance variables
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.color=color;
		this.GProgram=GProgram;
		this.myTable=myTable;
	}	

	/**
	 * In a thread, the run method is NOT started automatically (like in Assignment 1).
	 * Instead, a start message must be sent to each instance of the ppBall class, e.g.,
	 * ppBall myBall = new ppBall (--parameters--);
	 * myBall.start();
	 * The body of the run method is essentially the simulator code you wrote for A1.
	 */	

	public void run() {

		GPoint p = myTable.W2S(new GPoint(Xinit,Yinit));		
		double ScrX = p.getX();						// Convert simulation to screen coordinates
		double ScrY = p.getY();	
		this.myBall = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys);
		myBall.setColor(color);
		myBall.setFilled(true);
		this.GProgram.add(myBall);
		this.GProgram.pause(1000);

		Xo = Xinit;							// Set initial X position
		Yo = Yinit;							// Set initial Y position
		double time = 0;							// Time starts at 0 and counts up
		double Vt = bMass*g / (4*Pi*bSize*bSize*k); // Terminal velocity
		double Vox=Vo*Math.cos(theta*Pi/180);		// X component of velocity
		double Voy=Vo*Math.sin(theta*Pi/180);		// Y component of velocity

		double KEx = ETHR;
		double KEy = ETHR;
		double PE = ETHR;


		// Simulation loop.  Calculate position and velocity, print, increment
		// time.  Do this until ball hits the ground.

		running = true;						// Initial state = running.

		// Important - X and Y are ***relative*** to the initial starting position Xo,Yo.
		// So the absolute position is Xabs = X+Xo and Yabs = Y+Yo.
		// Also - print out a header line for the displayed values.

		System.out.printf("\t\t\t Ball Position and Velocity\n");

		while (running) {

			X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));				// Update relative position
			Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
			Vx = Vox*Math.exp(-g*time/Vt);						// Update velocity
			Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;
			KEx=0.5*bMass*Vx*Vx*(1-loss);
			KEy=0.5*bMass*Vy*Vy*(1-loss);
			PE=bMass*g*Y;

			// Display current values (1 time/second)

			System.out.printf("t: %.2f\t\t X: %.2f\t Y: %.2f\t Vx: %.2f\t Vy: %.2f\n",
					time,X+Xo,Y+Yo,Vx,Vy);

			GProgram.pause(TICK*TSCALE);// So we can see the starting point of the ball

			//check for collision with the ground	
			if ( Yo+Y <= bSize) {

				KEx=0.5*bMass*Vx*Vx*(1-loss);
				KEy=0.5*bMass*Vy*Vy*(1-loss); 
				PE=0; //potential energy at the ground is zero
				Vox=Math.sqrt(2*KEx/bMass);
				Voy=Math.sqrt(2*KEy/bMass);

				time=0; // time is reset at every collision
				Xo+=X; // need to accumulate distance between collisions
				Yo=bSize; // the absolute position of the ball on the ground
				X=0; // (X,Y) is the instantaneous position along an arc,
				Y=0; // Absolute position is (Xo+X,Yo+Y).

				if (Vx<0) Vox=-Vox;
				if ((KEx+KEy+PE)<ETHR) running=false;

			}
			//check for collision with the invisible ceiling
			if ( Yo+Y >= Ymax) {

				KEx=0.5*bMass*Vx*Vx*(1-loss);
				KEy=0.5*bMass*Vy*Vy*(1-loss);
//				PE=0;
				PE=bMass*g*Y;
				Vox=Math.sqrt(2*KEx/bMass);
				Voy=-Math.sqrt(2*KEy/bMass);

				time=0; // time is reset at every collision
				Xo+=X; // need to accumulate distance between collisions
				Yo=Ymax; // the absolute position of the ball on the ground
				X=0; // (X,Y) is the instantaneous position along an arc,
				Y=0; // Absolute position is (Xo+X,Yo+Y).

				if (Vx<0) Vox=-Vox;
				if ((KEx+KEy+PE)<ETHR) running=false;

			}
			
			//check for collision with right paddle
			if ((Vx>0) && (Xo+X)>=(RPaddle.getP().getX()-ppPaddleW/2 - bSize)) {
				//possible collision
				if(RPaddle.contact(Xo+X, Yo+Y)) {

					KEx=0.5*bMass*Vx*Vx*(1-loss);
					KEy=0.5*bMass*Vy*Vy*(1-loss);
					PE=bMass*g*Y;
					Vox=-Math.sqrt(2*KEx/bMass);
					Voy=Math.sqrt(2*KEy/bMass);

					//add more energy
					Vox=Vox*ppPaddleXgain;							// Scale X component of velocity
					Voy=Voy*ppPaddleYgain*RPaddle.getSgnVy();		// Scale Y + same dir. as paddle

					time=0; // time is reset at every collision
					Xo=RPaddle.getP().getX()-ppPaddleW/2 - bSize; // need to accumulate distance between collisions
					Yo=Y+Yo; // the absolute position of the ball
					X=0;// (X,Y) is the instantaneous position along an arc,
					Y=0; // Absolute position is (Xo+X,Yo+Y).
				}
				else {
					running = false;
				}
			}


			//check for collision with left paddle
			if ((Vx<0) && (Xo+X)<=(LPaddle.getP().getX()+ ppPaddleW/2 + bSize)) {
				//possible collision
				if(LPaddle.contact(Xo+X, Yo+Y)) {

					KEx=0.5*bMass*Vx*Vx*(1-loss);
					KEy=0.5*bMass*Vy*Vy*(1-loss);
					PE=bMass*g*Y;
					Vox=Math.sqrt(2*KEx/bMass);
					Voy=Math.sqrt(2*KEy/bMass);

					//add more energy
					Vox=Vox*ppPaddleXgain;	// Scale X component of velocity	
					//check for Vox > VoxMax
					//if exceeds, limit the Vox to VoxMax
					if (Vox >= VoxMAX) Vox = VoxMAX;
					Voy=Voy*ppPaddleYgain*LPaddle.getSgnVy();		// Scale Y + same dir. as paddle

					time=0; // time is reset at every collision
					Xo=LPaddle.getP().getX() + ppPaddleW/2 + bSize; // reset Xo everytime after collision
					Yo=Y+Yo; // the absolute position of the ball
					X=0;// (X,Y) is the instantaneous position along an arc,
					Y=0; // Absolute position is (Xo+X,Yo+Y).
				}
				else {
					running = false;
				}
			}

			p = myTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize));		// Get current position in screen coordinates
			ScrX = p.getX();
			ScrY = p.getY();
			myBall.setLocation(ScrX,ScrY);
			//trace point
			if(traceButton.isSelected()) {
				trace(ScrX+Xs*bSize,ScrY+Xs*bSize);
			}

			if (TEST)System.out.printf("t: %.2f X: %.2f Y: %.2f Vx: %.2f Vy:%.2f\n",time,Xo+X,Yo+Y,Vx,Vy);
			time +=TICK;
//			GProgram.add(myBall);
		}

		this.GProgram.pause(1000);
	}


	/***
	 * A simple method to plot a dot at the current location in screen coordinates
	 * @param scrX
	 * @param scrY
	 */

	private void trace(double ScrX, double ScrY) {
		GOval pt = new GOval(ScrX,ScrY,PD,PD);
		pt.setColor(Color.BLACK);
		pt.setFilled(true);
		GProgram.add(pt);	

	}
	public void setRightPaddle(ppPaddle myPaddle) {
		this.RPaddle = myPaddle;
	}
	public void setLeftPaddle(ppPaddleAgent myPaddle) {
		this.LPaddle = myPaddle;
	}
	public GPoint getP() {
		//return some GPoint
		//value of the absolute position of the ball
		return new GPoint(X+Xo,Y+Yo);
	}
	
	public GPoint getV() {
		//return velocity of the ball in GpOInt object
		return new GPoint(Vx, Vy);
		
	}
	
	void kill() {
		running = false;
	}

	/***
	 * the above code is written largely based on the source Code
	 *  @author ferrie
	 */


}


