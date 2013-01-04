/*
 * ToggleScript.java
 * Toggles an integer between 0 to 1 every time a time event
 *   is received
 */
import vrml.*;
import vrml.field.*;
import vrml.node.*;

import java.applet.Applet;

public class ToggleScript extends Script {
  SFInt32 which_changed;
  public void initialize() {
    which_changed  = (SFInt32) getEventOut("which_changed");
    which_changed.setValue(0);
  }
  public void processEvent( Event e ) {
    String name = e.getName();
    if ( name.equals( "touch" )) {
      which_changed.setValue(1 - which_changed.getValue());
    }
  }
}
