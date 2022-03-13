package ppPackage;
/***
 * a class that construct the graphic left wall and floor
 */
import acm.program.GraphicsProgram;
import acm.graphics.*;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

public class ppTable{
	GraphicsProgram GProgram;

	public ppTable(GraphicsProgram GProgram) {
		this.GProgram = GProgram;

//		GRect wallL = new GRect(Xs*XwallL, 0, 3, 600);
//		wallL.setColor(Color.BLUE);
//		wallL.setFilled(true);
//		GProgram.add(wallL);
		addGroundPlane();
	}

	/***
	 * Method to convert from world to screen coordinates.
	 * @param P a point object in world coordinates
	 * @return p the corresponding point object in screen coordinates
	 */
	public GPoint W2S (GPoint P) {
		return new GPoint ((P.getX()-Xmin)*Xs,ymax-(P.getY()-Ymin)*Ys);
		/***
		 * Method to convert from screen to world coordinates.
		 * @param P a point object in world coordinates
		 * @return p the corresponding point object in screen coordinates
		 */	
	}
	public GPoint S2W (GPoint P) {
		double ScrX = P.getX();
		double ScrY = P.getY();
		return new GPoint((ScrX/Xs + Xmin),(Ymin+(ymax-ScrY)/Ys));

	}
	public void addGroundPlane() {
		GRect gPlane = new GRect(0,HEIGHT,WIDTH+OFFSET,3);	// A thick line HEIGHT pixels down from the top
		gPlane.setColor(Color.BLACK);
		gPlane.setFilled(true);
		GProgram.add(gPlane);
	}

	public void newScreen() {
		//erases screen, draws a new ground plane.
		GProgram.removeAll();
		addGroundPlane();
	}


}
