package ppPackage;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import ppPackage.ppPaddle.*;

import static ppPackage.ppSimParams.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JToggleButton;


/**
 * A class that allow the simulation to happen
 */
public class ppSim extends GraphicsProgram{

	ppTable myTable;
	ppBallA4 myBall;
	ppPaddleAgent LPaddle;
	ppPaddle RPaddle;
	RandomGenerator rgen;

	public static void main(String[] args) {
		new ppSim().start(args);
	}

	public void init() {

		//resize
		this.resize(xmax+WIDTH, ymax+OFFSET);

		//creating buttons
		JButton newServeButton = new JButton("New Serve");
		JButton quitButton = new JButton("Quit");
		traceButton = new JToggleButton("Trace", false);

		//add buttons
		add(newServeButton, SOUTH);
		add(quitButton, SOUTH);
		add(traceButton, SOUTH);

		//add the listeners to allow their execution
		addActionListeners();
		addMouseListeners();

		//random generator
		rgen = RandomGenerator.getInstance();
		rgen.setSeed(RSEED);

		//create Table, Paddle 
		myTable =  new ppTable(this);
		myBall = newBall();
		newGame();
	}
	//method that generates parameters and create a graphic ball representation
	ppBallA4 newBall(){
		//generate parameters for ppBall
		Color iColor = Color.RED;
		double iYinit = rgen.nextDouble(YinitMIN,YinitMAX);
		double iLoss = rgen.nextDouble(EMIN,EMAX);
		double iVel = rgen.nextDouble(VoMIN,VoMAX);
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);

		//return new ball instance
		return new ppBallA4(Xinit,iYinit,iVel,iTheta,iLoss,iColor,this,myTable);
	}

	//initialize elements to get ready for a new game
	public void newGame() {
		if (myBall != null) myBall.kill(); // stop current game in play
		myTable.newScreen();
		myBall = newBall();
		RPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, Color.green, myTable,this);
		LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.blue, myTable, this);
		LPaddle.attachBall(myBall);
		myBall.setRightPaddle(RPaddle);
		myBall.setLeftPaddle(LPaddle);
		pause(STARTDELAY);
		myBall.start();
		LPaddle.start();
		RPaddle.start();
	}
	//a method that allow the mouse move control of the right paddle
	public void mouseMoved(MouseEvent e) {
		if (myTable==null || RPaddle==null) return;
		GPoint Pm = myTable.S2W(new GPoint(e.getX(), e.getY()));
		double PaddleX = RPaddle.getP().getX();
		double PaddleY = Pm.getY();
		RPaddle.setP(new GPoint(PaddleX, PaddleY));
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("New Serve")) {
			newGame();
		}
		else if (command.equals("Quit")) {
			System.exit(0);
		}
	}

	

}
