// Controller, that handles one direction of the railway

import vrml.*;
import vrml.field.*;
import vrml.node.*;

public class Train {

    public static final float STARTING_POS   = -73;
    public static final float ENDING_POS     =  73;

    private int   direction; // +1 for left to right, -1 for right to left
    float linpos;

    float[] position = new float[3];
    float[] inactivePos = new float[3];
    boolean isActive;

    float[] normalColor;
    float[] funnyColor;

    SFRotation vrmlRotation;
    SFVec3f vrmlPosition;
    SFColor vrmlColor;

    public Train(Node node, int dir) {
	vrmlColor = (SFColor) node.getExposedField("color");
	vrmlPosition = (SFVec3f) node.getExposedField("translation");
	vrmlRotation = (SFRotation) node.getExposedField("rotation");
	((SFVec3f) node.getExposedField("inactivePos"))
	    .getValue(inactivePos);
	System.arraycopy(inactivePos, 0, position, 0, 3);
	isActive = false;
	direction = dir;

	if (direction == 1)
	    normalColor = new float[] { 1F, .3F, .2F};
	else
	    normalColor = new float[] { .2F, .3F, 1F};
	funnyColor = new float[] { 1F, 1F, .2F};
    }

    private final float calculateZPos (float xPos) {
	if (direction == 1)
	    return 5;
	float absX = Math.abs( xPos );
	if ( absX > 30) return -5;
	if ( absX < 15) return 5;
	absX = absX - 15;
	return 5 - (absX * 10) / 15;
    }

    private final float calculateAngle (float xPos) {
	if (direction == 1)
	    return (float)Math.PI;

	float absX = Math.abs( xPos );
	if ( absX > 32 || absX < 13)
	    return 0;

	float angle = ( xPos > 0 ? (float)-.5878 : (float).5878);
	if ( absX < 17 )
	    return (absX - 13)/4 * angle;
	else if ( absX < 28 )
	    return angle;
	else
	    return (32 - absX)/4 * angle;
    }

    public void setPosition (float xPos) {
	linpos = xPos;
	position[0] = xPos * direction;
	position[1] = 0;
	position[2] = calculateZPos (xPos);

	vrmlPosition.setValue(position);
	vrmlRotation.setValue(0, 1, 0, calculateAngle (xPos));
    }

    private static int counter;
    private final float[] directionColor () {
	if (counter == 5) {
	    counter = 0;
	    return funnyColor;
	} else
	    counter ++;

	return normalColor;
    }

    public void moveTrain(int signalState, float movement) {
	if (isActive) {
	    if ( linpos > ENDING_POS ) {
		if (Math.random() > .5) {
		    activate();
		} else {
		    setPosition(STARTING_POS);
		    vrmlColor.setValue(directionColor());
		}
	    } else
		setPosition(linpos + movement);
	} else {
	    if (Math.random() < movement / 100)
		activate();
	}
    }


    public final void activate ()
    {
	if (isActive) {
	    System.arraycopy(inactivePos, 0, position, 0, 3);
	    vrmlPosition.setValue(position);
	    isActive = false;
	} else {
	    setPosition(STARTING_POS);
	    isActive = true;
	}
	System.err.println("Train.activate("+isActive+")");
    }
}
