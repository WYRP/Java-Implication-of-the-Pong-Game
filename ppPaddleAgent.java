package ppPackage;

import java.awt.Color;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import ppPackage.ppBallA4.*;

/**
 * A class that is responsible for the the computer controlled
 * Left Paddle 
 * @author yongr
 *
 */
public class ppPaddleAgent extends ppPaddle{

	//instance variables
	ppBallA4 myBall;

	/**
	 * constructor that inherited some parameters from the ppPaddle class
	 * @param X
	 * @param Y
	 * @param myColor
	 * @param myTable
	 * @param GProgram 
	 */
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		//create a ppPaddle
		super(X, Y, myColor, myTable, GProgram);
	}

	public void run() {
		int AgentLag = 5;
		int ballSkip = 0;
		while(true) {
			//allow us to update the paddle position every few iterations
			if (ballSkip ++ >= AgentLag) {
				//get ball Y position
				double Y = myBall.getP().getY();
				//set paddle position to that Y;
				this.setP(new GPoint(this.getP().getX(),Y));
				ballSkip = 0;
			}
		} 
	}

	public void attachBall(ppBallA4 myBall) {
		//sets the myball to the ball instance variable in ppPaddleAgent
		this.myBall = myBall;

	}
}
