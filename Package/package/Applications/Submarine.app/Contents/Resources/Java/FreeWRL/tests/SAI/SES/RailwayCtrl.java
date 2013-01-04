// Controller, that handles one direction of the railway

import vrml.*;
import vrml.field.*;
import vrml.node.*;

public class RailwayCtrl {
  private boolean sensorsOK[] = {true, true, true};
  private SFColor[] sensorColors;

  public Train[] train;

  private int counter = 0;

  private int    direction; // +1 for left to right, -1 for right to left
  private double lastMove = -1;  // time of last movement

  private CommClient comm = null;

  private int       number;
  private float[]   position;
  private boolean[] isActive;

  public static final float STARTING_POS   = -73;
  public static final float ENDING_POS     =  73;
  public static final float TRAIN_DISTANCE =  18;
  public static final float SENSOR_POS[]   = { (float) -48,
						(float) -21.5,
						(float)  21.5    };
  public static final float SIGNAL_POS     = -22;

  public RailwayCtrl ( MFNode t,
		       int dir,
		       CommClient communicator,
		       SFColor[]  colors) {
    direction = dir;
    comm = communicator;
    sensorColors = colors;
    number = t.getSize();
    position = new float[number];
    isActive = new boolean[number];

    train = new Train[number];

    for (int i = 0; i < number; i++)
	train[i] = new Train((Node) t.get1Value(i), dir);
  }

  public void continueMoving () {
    lastMove = -1;
  }

  public final
  void moveTrains (int signalState, double now, double ticksPerSec )
  {
    if (lastMove < 0 || now < lastMove) {
      lastMove = now;
      return;
    }
    double time = now - lastMove;
    if (time < .05) return;

    float movement = (float)(time * comm.readTrainSpeed() * ticksPerSec);
    lastMove = now;

    for (int i=0; i<number; i++) {
	float mm = movement;
	if (train[i].isActive) {
	    for (int j=0; j<number; j++)
		if (train[j].isActive && (i != j) &&
		    trainInFront (train[i].linpos, train[i].linpos+mm,
				  train[j].linpos)) {
		    mm /= 2;
		    if (trainInFront (train[i].linpos, train[i].linpos+mm,
				      train[j].linpos))
			mm = 0;
	    }

	    if (mm > 0 && atSignal (train[i].linpos, train[i].linpos+mm,
				    SIGNAL_POS)) {
		if (signalState != 2) {
		    mm /= 2;
		    if (atSignal (train[i].linpos, train[i].linpos+mm,
				  SIGNAL_POS))
			mm = 0;
		}
	    }

	    for (int j = 0; j < 3; j++)
		if (atContakt(train[i].linpos, train[i].linpos+mm,
			      SENSOR_POS[j]) )
		    comm.sensorActivated (direction, j);
	}
	train[i].moveTrain(signalState, mm);
    }
  }


  private static final boolean atContakt ( float oldTrainPos,
					   float trainPos,
					   float contaktPos ){
    return inRange (oldTrainPos, trainPos, contaktPos, (float)0, (float)0);
  }

  private static final boolean atSignal ( float oldTrainPos,
					  float trainPos,
					  float signalPos ){
    // range of 4m to .5m befor the signal will be relevant to train
    return inRange (oldTrainPos, trainPos, signalPos, (float)-4, (float)-.5);
  }

  private static final boolean trainInFront (float oldThisPos,
					     float thisPos,
					     float otherPos ){
    if (otherPos == STARTING_POS)
      return false;
    // move not nearer than 9m to the other train
    return inRange (oldThisPos, thisPos, otherPos,
                    - TRAIN_DISTANCE, (float)0);
  }

  private static final boolean inRange (float oldTestPos,
					float testPos,
					float otherPos,
					float beginning,
					float end) {
    return (oldTestPos <= otherPos + end) && (testPos >= otherPos + beginning);
  }

  private final float calculateZPos (float xPos) {
    if (direction == 1) return 5;
    float absX = Math.abs( xPos );
    if ( absX > 30) return -5;
    if ( absX < 15) return 5;
    absX = absX - 15;
    return 5 - (absX * 10) / 15;
  }

  private final SFVec3f interpolatePosition (float xPos) {
    return new SFVec3f (xPos * direction, 0, calculateZPos (xPos));
  }

  private final SFRotation interpolateOrientation (float xPos) {
    return new SFRotation (0, 1, 0, calculateAngle (xPos));
  }

  private final float calculateAngle (float xPos) {
    if (direction == 1) return (float)Math.PI;
    float absX = Math.abs( xPos );
    if ( absX > 32) return 0;
    if ( absX < 13) return 0;
    float angle = ( xPos > 0 ? (float)-.5878 : (float).5878);
    if ( absX > 17 && absX < 28 )
      return angle;
    absX = (absX <= 17) ? absX - (float)13 : (float)32 - absX;
    return (absX * angle) / 4;
  }

  private final SFColor directionColor () {
    if (counter == 5) {
      counter = 0;
      return new SFColor ((float)1,(float)1,(float).2);
    } else
      counter ++;

    if (direction == 1)
      return new SFColor ((float)1,(float).3,(float).2);
    else
      return new SFColor ((float).2,(float).3,(float)1);
  }


  public void sendInit () {
    for (int i = 0; i < 3; i++)
      if (sensorsOK[i])
	comm.sensorOK(direction, i);
      else
	comm.sensorDefect(direction, i);
  }



  public void toggleSensorState (int i) {
    sensorsOK[i] = !sensorsOK[i];
    if (sensorsOK[i]) {
      comm.sensorOK(direction, i);
      if (direction > 0)
	sensorColors[i].setValue((float)1, (float).3, (float).2);
      else
	sensorColors[i].setValue((float).2, (float).3, (float)1);
    } else {
      comm.sensorDefect(direction, i);
      if (direction > 0)
	sensorColors[i].setValue((float).3, (float).1, (float).1);
      else
	sensorColors[i].setValue((float).1, (float).1, (float).3);
    }
  }

  final public void reset ()
  {
    for (int i = 0; i < number; i++ ) {
      if (train[i].isActive)
	train[i].activate ();
    }
  }

}

