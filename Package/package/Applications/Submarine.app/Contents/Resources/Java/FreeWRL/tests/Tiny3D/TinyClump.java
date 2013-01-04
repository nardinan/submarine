// Helper class for encapsulating the objects we add to the Tiny3D scene.

import java.lang.*;
import java.applet.*;
import vrml.external.field.*;
import vrml.external.exception.*;
import vrml.external.Node;
import vrml.external.Browser;
import Tiny3D;

public class TinyClump extends Object implements EventOutObserver {
  public final static int CUBE = 0;
  public final static int SPHERE = 1;
  public final static int CONE = 2;

  // Parent Tiny3D instance
  Tiny3D parent;
  // CosmoPlayer we're using to instantiate these nodes
  Browser browser;

  // Nodes we're interested in
  public Node[] transArray;
  Node transform;
  Node material;

  // EventIns we'll modify
  public EventInSFRotation set_rotation;
  public EventInSFVec3f set_scale;
  public EventInSFVec3f set_translation;
  public EventInSFColor set_diffuseColor;

  // EventOuts we'll query
  public EventOutSFRotation rotation_changed;
  public EventOutSFVec3f scale_changed;
  public EventOutSFVec3f translation_changed;
  public EventOutSFColor diffuseColor_changed;
  public EventOutSFTime touchTime_changed;

  // State associated with the clump
  int xang;
  int yang;
  int zang;

  TinyClump(Tiny3D myParent, int whichType) throws IllegalArgumentException {
    parent = myParent;
    browser = parent.getBrowser();
    xang = yang = zang = 0;

    if ((whichType != TinyClump.CUBE) &&
	(whichType != TinyClump.SPHERE) &&
	(whichType != TinyClump.CONE)) {
      throw(new IllegalArgumentException());
    }

    try {
      // Instantiate common nodes
      transArray = browser.createVrmlFromString("Transform {}");
      Node[] shapeArray = browser.createVrmlFromString("Shape {}");
      Node[] matArray = browser.createVrmlFromString("Material {}");
      Node[] appArray = browser.createVrmlFromString("Appearance {}");
      Node[] sensArray = browser.createVrmlFromString("TouchSensor {}");
      Node[] geomArray = null;

      if (whichType == TinyClump.CUBE) {
	geomArray = browser.createVrmlFromString("Box {}");
      }
      if (whichType == TinyClump.SPHERE) {
	geomArray = browser.createVrmlFromString("Sphere {}");
      }
      if (whichType == TinyClump.CONE) {
	geomArray = browser.createVrmlFromString("Cone {}");
      }

      // Put together the hierarchy
      transform = transArray[0];
      material = matArray[0];
      EventInSFNode nodeIn =
	(EventInSFNode) shapeArray[0].getEventIn("appearance");
      nodeIn.setValue(appArray[0]);
      nodeIn = (EventInSFNode) appArray[0].getEventIn("material");
      nodeIn.setValue(material);
      nodeIn = (EventInSFNode) shapeArray[0].getEventIn("geometry");
      nodeIn.setValue(geomArray[0]);
      EventInMFNode nodesIn =
	(EventInMFNode) transform.getEventIn("addChildren");
      nodesIn.setValue(shapeArray);
      nodesIn.setValue(sensArray);

      // Extract EventIns and EventOuts of transform and material node
      set_rotation = (EventInSFRotation) transform.getEventIn("rotation");
      set_scale = (EventInSFVec3f) transform.getEventIn("scale");
      set_translation = (EventInSFVec3f) transform.getEventIn("translation");
      set_diffuseColor = (EventInSFColor) material.getEventIn("diffuseColor");

      rotation_changed = (EventOutSFRotation) transform.getEventOut("rotation");
      scale_changed = (EventOutSFVec3f) transform.getEventOut("scale");
      translation_changed =
	(EventOutSFVec3f) transform.getEventOut("translation");
      diffuseColor_changed =
	(EventOutSFColor) material.getEventOut("diffuseColor");
      touchTime_changed =
	(EventOutSFTime) sensArray[0].getEventOut("touchTime");

      // Set EventOut callback for this clump's touch sensor,
      // so we can later read in its values when it's clicked
      touchTime_changed.advise(this, null);
    }

    catch (InvalidVrmlException e) {
      System.out.println("PROBLEMS!: " + e);
    }

    catch (InvalidEventInException e) {
      System.out.println("PROBLEMS!: " + e);
    }

    catch (InvalidEventOutException e) {
      System.out.println("PROBLEMS!: " + e);
    }
  }

  // We'll take care of handling the callback every time our
  // TouchSensor is clicked.
  public void callback(EventOut event, double time, Object userData) {
    // Make ourselves the current clump
    parent.makeCurrent(this);
  }
}
