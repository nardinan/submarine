// This class implements a controller for the "SES.wrl" -scene

import vrml.*;
import vrml.field.*;
import vrml.node.*;



public class Controller extends Script {
  private MFNode ltrTrains; // loks moving from left to right
  private MFNode rtlTrains; // loks moving from right to left
  private SFColor collisionOut;

  private SignalCtrl ltrSignal; // Signal controlling left to right railway
  private SignalCtrl rtlSignal; // Signal controlling right to left railway

  private RailwayCtrl ltrCtrl; // Train controlling left to right
  private RailwayCtrl rtlCtrl;

  private double  now = 0;
  private boolean collision = false;
  private boolean start = false;

  // variables to determine speed of simulator
  private int    lastTicks = 0;
  private double lastTickTime = 0;
  private double ticksPerSecond = 0; // ticks from simulator per second

  private String toggle[] =
    {"toggle0", "toggle1", "toggle2", "toggle3", "toggle4", "toggle5" };
  private String[] activateLL;
  private String[] activateLR;


  private CommClient comm = new CommClient ();


  public void initialize(){
    ltrTrains = (MFNode) getField("ltrTrains");
    rtlTrains = (MFNode) getField("rtlTrains");
    ltrSignal = new SignalCtrl ((SFNode) getField("ltrSignal"));
    rtlSignal = new SignalCtrl ((SFNode) getField("rtlSignal"));

    collisionOut = (SFColor) getEventOut("collision");

    SFColor[] ltrColors = new SFColor[3];
    for (int i = 0; i < 3; i++){
      ltrColors[i] = (SFColor) getEventOut("toggleColor" + i);
    }
    ltrCtrl = new RailwayCtrl (ltrTrains, 1, comm, ltrColors);
    int lTrains = ltrTrains.getSize();
    activateLL = new String[lTrains];
    for (int i = 0; i < lTrains; i++) {
      activateLL [i] = new String("activateLL" + i);
    }

    SFColor[] rtlColors = new SFColor[3];
    for (int i = 0; i < 3; i++){
      rtlColors[i] = (SFColor) getEventOut("toggleColor" + (i+3));
    }
    rtlCtrl = new RailwayCtrl (rtlTrains, -1, comm, rtlColors);
    int rTrains = rtlTrains.getSize();
    activateLR = new String[rTrains];
    for (int i = 0; i < rTrains; i++) {
      activateLR [i] = new String("activateLR" + i);
    }
  }


  public void shutdown () {
    comm.close ();
  }


  public void processEvent(Event e){

    int simTick = comm.readTime();

    ltrSignal.setState(comm.readLtRSignal(), now);
    rtlSignal.setState(comm.readRtLSignal(), now);

    if (comm.needsReset()) {
      reset ();
      return;
    }

    if ((simTick >= 0) && (! start)) {
      System.err.println("## init " );
      ltrCtrl.sendInit();
      rtlCtrl.sendInit();
      lastTickTime = now;
      lastTicks = simTick;
      start = true;
    }

    if (start && (simTick > 0) && e.getName().equals("timeStep") ) {

      now = ((ConstSFTime)e.getValue()).getValue();
      calculateSimSpeed (simTick);
      ltrCtrl.moveTrains( ltrSignal.getState(), now, ticksPerSecond );
      rtlCtrl.moveTrains( rtlSignal.getState(), now, ticksPerSecond );
      testForCrash ();
      return;
    }

    int number = ltrCtrl.train.length;

    for (int i = 0; i < number; i++) {
      if (e.getName().equals(activateLL[i])) {
	ltrCtrl.train[i].activate();
	return;
      }
    }

    number = rtlCtrl.train.length;
    for (int i = 0; i < number; i++) {
      if (e.getName().equals(activateLR[i])) {
	rtlCtrl.train[i].activate();
	return;
      }
    }

    for (int i = 0; i < 3; i++) {
      if (e.getName().equals(toggle[i])) {
	ltrCtrl.toggleSensorState (i);
	return;
      }
    }
    for (int i = 0; i < 3; i++) {
      if (e.getName().equals(toggle[i+3])) {
	rtlCtrl.toggleSensorState (i);
	return;
      }
    }

    if (e.getName().equals("signalLR"))
	comm.toggleLtRSignal();
    if (e.getName().equals("signalRL"))
	comm.toggleRtLSignal();
  }

  public void testForCrash () {
    int n = ltrCtrl.train.length;
    int m = rtlCtrl.train.length;
    boolean alreadyCollision = collision;

    collision = false;
    for (int i=0; i < n; i++) {
      float[] pos1 = ltrCtrl.train[i].position;
      for (int j=0; j < m; j++) {
	float[] pos2 = rtlCtrl.train[j].position;
	if ( (Math.abs(pos1[0] - pos2[0]) < 4.6) &&
	     (Math.abs(pos1[2] - pos2[2]) < 2.5) )
	  {
	    collision = true;
	  }
      }
    }
    if (alreadyCollision != collision)
      if (collision)
	collisionOut.setValue((float)1, (float)0, (float)0);
      else
	collisionOut.setValue((float)0, (float)1, (float)0);
  }



  private void calculateSimSpeed (int simTicks)
  {
    double time = now - lastTickTime;
    if (time < .2 )
      return;

    double newTicksPerSecond = (double)(simTicks - lastTicks) / time;
    //ticksPerSecond = .75 * newTicksPerSecond + .25 * ticksPerSecond;
    ticksPerSecond = newTicksPerSecond;
    if (ticksPerSecond < .2 && simTicks <= lastTicks)
      ticksPerSecond = 0;
    lastTickTime = now;
    lastTicks = simTicks;
    //System.err.println("speed [tps]: " + ticksPerSecond);
  }


  private void reset ()
  {
    //deactivate all trains:
    ltrCtrl.reset();
    rtlCtrl.reset();

    //reset simulation time;
    lastTicks = 0;
    lastTickTime = 0;
    ticksPerSecond = 0; // ticks from simulator per second

    ltrCtrl.sendInit();
    rtlCtrl.sendInit();
  }

}
