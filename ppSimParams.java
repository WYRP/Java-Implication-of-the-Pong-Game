package ppPackage;

import javax.swing.JToggleButton;

/**
 * a parameter class that contains all of the necessary parameters for the pong game project
 */


public class ppSimParams {
	
	//Global variables
	public static JToggleButton traceButton; // Ref. to trace button
	//traceButton.isSelected(); //returns boolean
	
	//1. Paramters defined in screen coordinates (pixels, acm coordinates)
	public static final int WIDTH = 1280;							// n.b. screen coordinates
	public static final int HEIGHT = 600;
	public static final int OFFSET = 200;	

	//2.Ping-pong able parameters 
	public static final double ppTableXlen = 2.74; // Length
	public static final double ppTableHgt = 1.52; // Ceiling
	public static final double XwallL = 0.05; 						// Position of left wall
	public static final double XwallR = 2.69; 	// Position of right wall

	//3. Simulation Parameters

	public static final double g = 9.8;	          // gravitational constant
	public static final double k = 0.1316;        // air friction
	public static final double Pi = 3.1416;       // Pi to 4 places
	public static final double bSize = 0.02;      // Radius of ball (m)
	public static final double bMass = 0.0027;    // Mass of ball (kg)
	public static final double ETHR = 0.001; // Minimum ball energy
	public static final int SLEEP = 10;		
	public static final double TICK=SLEEP/1000.0; // Clock increment at each iteration.
	public static final double Xmin = 0.0;							// Minimum value of X (pp table)
	public static final double Xmax = 2.74;						// Maximum value of X
	public static final double Ymin = 0.0;							// Minimum value of Y
	public static final double Ymax = 1.52;						// Maximum value of Y (height above table)
	public static final int xmin = 0;								// Minimum value of x
	public static final int xmax = WIDTH;							// Maximum value of x
	public static final int ymin = 0;								// Minimum value of y
	public static final int ymax = HEIGHT;							// Maximum value of y
	public static final double Xs = (xmax-xmin)/(Xmax-Xmin);		// Scale factor X
	public static final double Ys = (ymax-ymin)/(Ymax-Ymin);		// Scale factor Y	    
	public static final double PD = 1;		// Trace point diameter
	public static final double TSCALE = 5000; // Scaling parameter for pause()
	public static final double Xinit = XwallL;	      // Initial X position of ball
	public static final double Yinit = Ymax/2;    // Initial Y position of ball
	static final double VoxMAX = 3.0; //maximum velocity allowed for Vox
	
	//4.Paddle Parameters
	static final double ppPaddleH = 8*2.54/100; // Paddle height
	static final double ppPaddleW = 0.5*2.54/100; // Paddle width
	static final double ppPaddleXinit = XwallR-ppPaddleW/2; // Initial Paddle X
	static final double ppPaddleYinit = Yinit; // Initial Paddle Y
	static final double ppPaddleXgain = 2.0; // Vx gain on right paddle hit
	static final double ppPaddleYgain = 2.0; // Vy gain on right paddle hit
	static final double LPaddleXinit = XwallL-ppPaddleW/2;
	static final double LPaddleYinit = Yinit;
	static final double LPaddleXgain = 2.0;// Vx gain on left paddle hit
	static final double LPaddleYgain = 2.0;// Vy gain on left paddle hit
	
	//5.parameters used by the ppSim class
	static final double YinitMAX = 0.75*Ymax; // Max inital height at 75% of range
	static final double YinitMIN = 0.25*Ymax; // Min inital height at 25% of range
	static final double EMIN = 0.2; // Minimum loss coefficient
	static final double EMAX = 0.2; // Maximum loss coefficient
	static final double VoMIN = 5.0; // Minimum velocity
	static final double VoMAX = 5.0; // Maximum velocity
	static final double ThetaMIN = 0.0; // Minimum launch angle
	static final double ThetaMAX = 20.0; // Maximum launch angle
	static final long RSEED = 8976232; // Random number gen. seed value


	public static final double Vdef = 3.0;		  // Default velocity (m/s)
	public static final double Tdef = 30.0;		  // Default angle (degrees)


	//6.Miscellaneous
	public static final boolean DEBUG = false;
	public static final boolean MESG = true;
	public static final int STARTDELAY = 1000;
	public static final boolean TEST = false; // print if test true


}
