// Simple applet illustrating use of add/removeChildren fields.
//  Slightly modified applet of AddRouteTest found in the EAI Spec
//  It is better policy to include some error handling in the code, this example
//  is designed to teach both Java and EAI basics.
// 1/98 Daniel.Schneider@tecfa.unige.ch
// Freeware (of course)

import java.awt.*;
import java.applet.*;
import vrml.external.field.*;
import vrml.external.Node;
import vrml.external.Browser;
import vrml.external.exception.*;

public class AddRoute extends Applet {
  TextArea output = null;
  boolean error = false;

  // Browser we're using
  Browser browser;
  // Root of the scene graph (to which we add our nodes)
  Node root;

  Node[] Shape;
  Node[] Clock;
  Node[] ColumnPath;

  // EventIns of the root node
  EventInMFNode addChildren;
  EventInMFNode removeChildren;

  // Add and Remove Buttons
  Button addButton;
  Button removeButton;
  Button addRButton;
  Button removeRButton;

  public void init() {
    // Paint the Java Buttons
    add(addButton = new Button("Add Object"));
    add(removeButton = new Button("Remove Object"));
    add(addRButton = new Button("Add Route"));
    add(removeRButton = new Button("Remove Route"));

    // Get the browser
    browser = Browser.getBrowser(this);
    // Get root node of the scene
    try
	{ root = browser.getNode("ROOT"); }
    catch (InvalidNodeException e) {
	System.out.println ("caught " + e);
    }


    // Instantiate (get handle to) the EventIn objects

    addChildren = (EventInMFNode) root.getEventIn("addChildren");
    removeChildren = (EventInMFNode) root.getEventIn("removeChildren");


    // Instantiate a lovely shape
    Shape = browser.createVrmlFromString(
	"Transform { \n" +
	"	rotation 0.0 0.0 1.0 0.0 \n" +
	"	children Shape { \n" +
	"		appearance Appearance { \n" +
	"			material Material {} \n" +
	"		} \n" +
	"		geometry Cylinder { \n" +
	"			height 1.0 \n" +
	"			radius 0.2 \n" +
	"		} \n" +
	"	} \n" +
	"} ");

    Clock =  browser.createVrmlFromString(
	"TimeSensor { \n" +
	"	cycleInterval 4.0 \n" +
	"	loop TRUE \n" +
	"}");

    ColumnPath =  browser.createVrmlFromString(
	"OrientationInterpolator { \n" +
	"	key [ 0.0 0.5 1.0 ] \n" +
	"	keyValue [ \n" +
	"		0.0 0.0 1.0 0.0, \n" +
	"		0.0 0.0 1.0 3.14, \n" +
	"		0.0 0.0 1.0 6.28 \n" +
	"	] \n" +
	"}");

    // Add the clock and interpolator here, even if they are not used yet.
    addChildren.setValue(Clock);
    addChildren.setValue(ColumnPath);
  }

  // Main Program
  // Event Handler for the Java Buttons

  public boolean action(Event event, Object what) {
	// Catch all Events from type Button
	if (event.target instanceof Button) {
		Button b = (Button) event.target;
		// either addButton or removeButton has been clicked on
		if (b == addButton) {
			addChildren.setValue(Shape);
		} else if (b == removeButton) {
			removeChildren.setValue(Shape);
		} else if (b == addRButton) {
			try {
				browser.addRoute (Clock[0], "fraction_changed", ColumnPath[0],
					"set_fraction");
				browser.addRoute (ColumnPath[0], "value_changed", Shape[0],
					"set_rotation");
			} catch (IllegalArgumentException e) {
				System.out.println ("caught " + e);
			}
		} else if (b == removeRButton) {
			try {
				browser.deleteRoute (Clock[0], "fraction_changed", ColumnPath[0],
					"set_fraction");
				browser.deleteRoute (ColumnPath[0], "value_changed", Shape[0],
					"set_rotation");
			} catch (IllegalArgumentException e) {
				System.out.println ("caught " + e);
			}
		}
	}
	return true;
  }

}
