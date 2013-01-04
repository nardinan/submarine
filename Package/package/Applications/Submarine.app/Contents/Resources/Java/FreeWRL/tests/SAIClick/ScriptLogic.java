/*
 * ScriptLogic.java
 * Receives set_b1/2/3 events, when correct combination
 *   is received outputs a startTime event.
 */
import vrml.*;
import vrml.field.*;
import vrml.node.*;

import java.applet.Applet;

public class ScriptLogic extends Script {
  int b1;
  int b2;
  int b3;
  SFTime startTime;
  public void initialize() {
    startTime  = (SFTime) getEventOut("startTime");
  }
  public void processEvent( Event e ) {
    String name = e.getName();
    if ( name.equals( "set_b1" )) {
      b1 = ((ConstSFInt32)e.getValue()).getValue();
    } else if ( name.equals( "set_b2" )) {
      b2 = ((ConstSFInt32)e.getValue()).getValue();
    } else if ( name.equals( "set_b3" )) {
      b3 = ((ConstSFInt32)e.getValue()).getValue();
    }
    if ((b1 == 1) && (b2 == 0) && (b3 == 1))
      startTime.setValue(e.getTimeStamp());
  }
}
