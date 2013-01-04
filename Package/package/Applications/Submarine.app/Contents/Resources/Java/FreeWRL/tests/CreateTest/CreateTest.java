// Example applet which tests createVrmlFromString and replaceWorld methods
// of the browser API.
// Slightly modified applet of AddRemoveTest found in the EAI Spec
// 1/98 Daniel.Schneider@tecfa.unige.ch
// Freeware (of course)

import java.awt.*;
import java.applet.*;
import vrml.external.field.*;
import vrml.external.Node;
import vrml.external.Browser;
import vrml.external.exception.*;

public class CreateTest extends Applet {
  TextArea output = null;
  Browser browser = null;
  Node material = null;
  EventInSFColor diffuseColor = null;
  EventOutSFColor outputColor = null;
  EventOutSFTime touchTime = null;
  boolean error = false;

  public void init() {
    output = new TextArea(5, 40);
    add(output);

    browser = Browser.getBrowser(this);

    try {
      output.append("Starting\n");
      // Create the two root nodes
      Node[] scene = browser.createVrmlFromString("DEF Camera Viewpoint {\n" +
						  "  position 0 0 5}\n" +
						  "DEF MySphere Transform {}\n");
      output.append("Done\n");
      if (scene.length != 2)
	{
	  output.append("Uh Oh...scene was only " + scene.length +
			    " nodes long (expected 2)! Aborting...\n");
	  return;
	}

      // Make the shape node and its children
      Node[] shape = browser.createVrmlFromString("Shape {}");
      output.append("Done\n");
      if (shape.length != 1)
	{
	  output.append("Uh Oh...shape was " + shape.length +
			    " nodes long (expected 1)! Aborting...\n");
	  return;
	}

      Node[] appearance = browser.createVrmlFromString("Appearance {}");
      output.append("Done\n");
      if (appearance.length != 1)
	{
	  output.append("Uh Oh...appearance was " + appearance.length +
			    " nodes long (expected 1)! Aborting...\n");
	  return;
	}

      Node[] geometry = browser.createVrmlFromString("Sphere {}");
      output.append("Done\n");
      if (geometry.length != 1)
	{
	  output.append("Uh Oh...geometry was " + geometry.length +
			    " nodes long (expected 1)! Aborting...\n");
	  return;
	}

      Node[] material = browser.createVrmlFromString("Material {}");
      output.append("Done\n");
      if (material.length != 1)
	{
	  output.append("Uh Oh...material was " + material.length +
			    " nodes long (expected 1)! Aborting...\n");
	  return;
	}

      EventInSFColor set_diffuseColor =
	(EventInSFColor) material[0].getEventIn("diffuseColor");

      float[] col = new float[3];
      col[0] = 0.2f; col[1] = 0.2f; col[2] = .8f;
      set_diffuseColor.setValue(col);

      // Add the material to the appearance
      EventInSFNode nodeIn =
	(EventInSFNode) appearance[0].getEventIn("set_material");
      nodeIn.setValue(material[0]);

      // Add the appearance to the shape
      nodeIn = (EventInSFNode) shape[0].getEventIn("set_appearance");
      nodeIn.setValue(appearance[0]);

      // Add the geometry to the shape
      nodeIn = (EventInSFNode) shape[0].getEventIn("set_geometry");
      nodeIn.setValue(geometry[0]);

      // Add the shape to the transform
      EventInMFNode nodesIn =
	(EventInMFNode) scene[1].getEventIn("addChildren");
      nodesIn.setValue(shape);

      // Set the VRML scene
      browser.replaceWorld(scene);
    }
    catch (InvalidVrmlException ie) {
      output.append("Problem! " + ie);
    }
    catch (InvalidEventInException ei) {
      output.append("Problem! " + ei);
    }
    catch (ArrayIndexOutOfBoundsException oob) {
      output.append("Problem! " + oob);
    }
  }
}
