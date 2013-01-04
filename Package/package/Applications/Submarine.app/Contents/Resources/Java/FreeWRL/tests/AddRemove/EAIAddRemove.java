import vrml.external.Browser;
import vrml.external.field.*;
import vrml.external.Node;

import java.applet.*;

public class EAIAddRemove extends Applet
{
	public static void main(String[] args)
	{
		EAIAddRemove ee = new EAIAddRemove();
		ee.start();
	}
	public void start() {
  // Shape group hierarchy
  Node[] shape1, shape2, shape3, shape4, shape5, shape6;
  
  // EventIns of the root node
  EventInMFNode addChildren;
  EventInMFNode removeChildren;

		System.out.println ("going to get browser");
		Browser browser = Browser.getBrowser(this);
		System.out.println ("got browser");
		
		if ((browser) == null) {
			System.out.println("FATAL ERROR! no browser");
		} 
		
		System.out.println("Got browser: " + browser);
		
		Node root = browser.getNode("ROOTNODE");
		System.out.println("Got node: " + root);

	    // Instantiate (get handle to) the EventIn objects
	    addChildren = (EventInMFNode) root.getEventIn("addChildren");
	    removeChildren = (EventInMFNode) root.getEventIn("removeChildren");
	
	    // Instantiate a lovely blue ball
	    shape1 = browser.createVrmlFromString(
			"Transform{translation -2.3 2.1 0 children Shape{\n"+
			 "  appearance Appearance {\n" +
			 "    material Material {\n" +
			 "      diffuseColor 0.2 0.2 0.8\n" +
			 "    }\n" +
			 "  }\n" +
			 "  geometry Sphere {}\n" +
			 "}}\n");

	//try {Thread.sleep (2000);} catch (InterruptedException f) { }

	    // Instantiate a lovely red ball
	    shape2 = browser.createVrmlFromString(
			"Transform{translation 0 2.1 0 children Shape{\n"+
			 "  appearance Appearance {\n" +
			 "    material Material {\n" +
			 "      diffuseColor 0.8 0.2 0.2\n" +
			 "    }\n" +
			 "  }\n" +
			 "  geometry Sphere {}\n" +
			 "}}\n");

	    // Instantiate a lovely green ball
	    shape3 = browser.createVrmlFromString(
			"Transform{translation 2.3 2.1 0 children Shape{\n"+
			 "  appearance Appearance {\n" +
			 "    material Material {\n" +
			 "      diffuseColor 0.2 0.8 0.2\n" +
			 "    }\n" +
			 "  }\n" +
			 "  geometry Sphere {}\n" +
			 "}}\n");

	    // Instantiate a lovely blue box
	    shape4 = browser.createVrmlFromString(
			"Transform{translation -2.3 -2.1 0 children Shape{\n"+
			 "  appearance Appearance {\n" +
			 "    material Material {\n" +
			 "      diffuseColor 0.2 0.2 0.8\n" +
			 "    }\n" +
			 "  }\n" +
			 "  geometry Box {}\n" +
			 "}}\n");

	    // Instantiate a lovely blue Cone
	    shape5 = browser.createVrmlFromString(
			"Transform{translation 0 -2.1 0 children Shape{\n"+
			 "  appearance Appearance {\n" +
			 "    material Material {\n" +
			 "      diffuseColor 0.2 0.2 0.8\n" +
			 "    }\n" +
			 "  }\n" +
			 "  geometry Cone {}\n" +
			 "}}\n");

	    // Instantiate a lovely blue Cylinder
	    shape6 = browser.createVrmlFromString(
			"Transform{translation 2.3 -2.1 0 children Shape{\n"+
			 "  appearance Appearance {\n" +
			 "    material Material {\n" +
			 "      diffuseColor 0.2 0.2 0.8\n" +
			 "    }\n" +
			 "  }\n" +
			 "  geometry Cylinder {}\n" +
			 "}}\n");

System.out.println ("adding the shapes");
	addChildren.setValue(shape1);
	//try {Thread.sleep (2000);} catch (InterruptedException f) { }
	addChildren.setValue(shape2);
	//try {Thread.sleep (2000);} catch (InterruptedException f) { }
	addChildren.setValue(shape3);
	//try {Thread.sleep (2000);} catch (InterruptedException f) { }
	addChildren.setValue(shape4);
	//try {Thread.sleep (2000);} catch (InterruptedException f) { }
	addChildren.setValue(shape5);
	//try {Thread.sleep (2000);} catch (InterruptedException f) { }
	addChildren.setValue(shape6);
	try {Thread.sleep (2000);} catch (InterruptedException f) { }


System.out.println ("going to do the remove shape1");
	removeChildren.setValue(shape1);
	try {Thread.sleep (2000);} catch (InterruptedException f) { }

System.out.println ("going to do the remove shape3");
	removeChildren.setValue(shape3);
	try {Thread.sleep (2000);} catch (InterruptedException f) { }

System.out.println ("going to do the remove shape5");
	removeChildren.setValue(shape5);
	try {Thread.sleep (2000);} catch (InterruptedException f) { }

System.out.println ("going to add shape 1 back in again");
	addChildren.setValue(shape1);

System.out.println ("so, now we are finished with this Java applet");

	}
}
