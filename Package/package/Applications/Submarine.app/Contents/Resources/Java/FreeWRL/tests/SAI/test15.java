// CreateTest.java
//
//	J. Jeffrey Close
//	2/5/97
//
//  Description: tests a call to createVrmlFromString() bug in Cosmo.
//    it creates a Transform node with createXX, then prints it out, and
//    gets a field and prints that out.
//    Next it creates a node with a PROTO and an instance in it, where the
//    PROTO is based on a script node.  Then it tries to get access to an
//    eventOut of that PROTO and print that out.
//
//	Tested Browsers:
//		Cosmo
//

import vrml.*;
import vrml.node.*;
import vrml.field.*;

import java.util.*;
import java.awt.*;



public class test15 extends Script
{
    static boolean initialized = false;
    int testStep = 0;

    vrml.Browser browser = null;

	SFBool set_enabled;
	SFBool enabled;
	SFBool enabled_changed;

  public void initialize()
  {
	try {
        testStep = 0;
	    // set_enabled = (SFBool) getEventIn("set_enabled");
	  	enabled = (SFBool) getField("enabled");
		enabled_changed = (SFBool) getEventOut("enabled_changed");

		// get pointer to Browser.
		browser = getBrowser();

        if (!initialized)
        {
        System.out.println("\n\nCreateVRMLTest:");
        System.out.println("j. jeffrey close\n");
        System.out.println("This will perform a series of tests using createVrmlFromString\n");
        System.out.println("Each time you click on the ball, the script will try to do a");
        System.out.println("call to createVrmlFromString with a string it has constructed.");
        System.out.println("It will print out the string of the VRML it is going to create,");
        System.out.println("then create the VRML; print it out; then, try to access a field");
        System.out.println("of the created node; and finally, if successful to this point");
        System.out.println("try to print out the accessed field.");
        System.out.println("*** NOTE:");
        System.out.println("For some reason this often needs to be reloaded at the start");
        }
        initialized = true;

	}
	catch (Exception exc)
		{
			System.out.println("CreateTest: Exception in Initialize");
				System.out.println("  Exception "+exc.toString()+
								", Message "+exc.getMessage());
		}
  }


  public void processEvent(vrml.Event e)
  {

    String vrmlString;
    String fieldstring;
    Node[] newVrml = null;

		// check for event type
        if (e.getName().equals("set_enabled") && ((ConstSFBool) e.getValue()).getValue())
        {
        System.out.println("\nCreateTest: test "+testStep);
            // create VRML
            switch (testStep)	{
                case 0:
					testStep++;
                        try {

						vrmlString = "Transform {  \n"+
							"children Shape { geometry Sphere { } } \n"+
							"translation 0 -4 0 \n"+
							"} \n";
						fieldstring = "translation_changed";

						System.out.println("CreateTest:  about to create VRML, string =\n\n"+vrmlString);
						newVrml = (Node []) browser.createVrmlFromString(vrmlString);

						// send VRML to message event of uiItem node
						if (newVrml != null)
						{
							System.out.println("CreateTest:  testing created VRML, VRML = "+newVrml[0]);

							Node testNode = (Node) newVrml[0];
							SFVec3f transField = null;

							if (testNode != null)
							{
								System.out.println("CreateTest: testNode = "+testNode);
								System.out.println("CreateTest: testNode type = "+testNode.getType());

								transField = (SFVec3f) testNode.getExposedField("translation");
                                System.out.println("Trying to access 'translation' field");

								if (transField == null)
								{
										System.out.println("CreateTest: translation field = null");
								}
								else
								{
									System.out.println("CreateTest: translation = \n  "+
										transField.getX()+", "+transField.getY()+", "+
										transField.getZ());
								}
							}
					}


} catch (Exception exc) {
			System.out.println("CreateTest: Exception in processEvent");
				System.out.println("  Exception "+exc.toString()+
								", Message "+exc.getMessage());
		}
				break;


                case 1:
                    testStep++;
                    try {

						vrmlString = "PROTO Foo [ exposedField MFString foo [] ]\n"+
							"{ Group {} } \n" +
							"DEF FROB  Foo { foo \"Fred\" } \n";
//							"field MFString foo \"blah\" \n"+
//							"url \"vrmlscript: function initialize() { foo_changed = foo; }\" \n"+

						// send VRML to message event of uiItem node

						System.out.println("CreateTest:  about to create VRML, string =\n\n"+vrmlString);
						newVrml = (Node []) browser.createVrmlFromString(vrmlString);

						if (newVrml != null)
						{
							System.out.println("CreateTest:  testing created VRML, VRML = "+newVrml[0]);

							Node testNode = (Node) newVrml[0];
//							ConstMFString fooString;
							MFString fooString = null;

							if (testNode != null)
							{
								System.out.println("CreateTest: testNode = "+testNode);
								System.out.println("CreateTest: testNode type = "+testNode.getType());

                                System.out.println("Trying to get 'foo' field");

								fooString = (MFString) testNode.getExposedField("foo");
//								fooString = (ConstMFString) testNode.getEventOut("foo_changed");
                                System.out.println("Trying to access 'foo' field value");

								if (fooString == null)
								{
										System.out.println("CreateTest: fooString field = null");
								}
								else
								{
									System.out.println("CreateTest: foo = \n  "+
										fooString.get1Value(0));
								}
							}
						}

} catch (Exception exc) {
			System.out.println("CreateTest: Exception in processEvent");
				System.out.println("  Exception "+exc.toString()+
								", Message "+exc.getMessage());
		}
		break;


                case 2:
                    testStep++;
                    try {

						vrmlString = "PROTO Foo [ exposedField MFNode children [] ]\n"+
							"{ Group { children IS children } } \n" +
							"DEF FROB  Foo { children [ Box { size .1 .1 .1 } ] } \n";

						// send VRML to message event of uiItem node

						System.out.println("CreateTest:  about to create VRML, string =\n\n"+vrmlString);
						newVrml = (Node []) browser.createVrmlFromString(vrmlString);

						if (newVrml != null)
						{
							System.out.println("CreateTest:  testing created VRML, VRML = "+newVrml[0]);

							Node testNode = (Node) newVrml[0];
                            MFNode field;

							if (testNode != null)
							{
								System.out.println("CreateTest: testNode = "+testNode);
								System.out.println("CreateTest: testNode type = "+testNode.getType());

                                System.out.println("Trying to get 'children' field ");
								field = (MFNode) testNode.getExposedField("children");

                                System.out.println("Trying to access 'children' field value");

								if (field == null)
								{
										System.out.println("CreateTest: field == null");
								}
								else
								{
									System.out.println("CreateTest: field = \n  "+field);
								}
							}
						}
} catch (Exception exc) {
			System.out.println("CreateTest: Exception in processEvent");
				System.out.println("  Exception "+exc.toString()+
								", Message "+exc.getMessage());
		}
               break;

            case 3:
                testStep++;
                try {

                vrmlString = "Script { eventOut MFString foo_changed \n"+
							"field MFString foo \"blah\" \n"+
							"url \"vrmlscript: function initialize() { foo_changed = foo; }\" \n"+
							"} ";

						// send VRML to message event of uiItem node

						System.out.println("CreateTest:  about to create VRML, string =\n\n"+vrmlString);
						newVrml = (Node []) browser.createVrmlFromString(vrmlString);

						if (newVrml != null)
						{
							System.out.println("CreateTest:  testing created VRML, VRML = "+newVrml[0]);

							Node testNode = (Node) newVrml[0];
							ConstMFString fooString = null;

							if (testNode != null)
							{
								System.out.println("CreateTest: testNode = "+testNode);
								System.out.println("CreateTest: testNode type = "+testNode.getType());

                                System.out.println("Trying to access 'foo_changed' field ");
								fooString = (ConstMFString) testNode.getEventOut("foo_changed");

                                System.out.println("Trying to access 'foo_changed' field value");

								if (fooString == null)
								{
										System.out.println("CreateTest: foo_changed reference = null");
								}
								else
								{
									System.out.println("CreateTest: foo_changed = \n  "+
										fooString.get1Value(0));
								}
							}
						}
} catch (Exception exc) {
			System.out.println("CreateTest: Exception in processEvent");
				System.out.println("  Exception "+exc.toString()+
								", Message "+exc.getMessage());
		}
					break;


					case 4:
                        testStep++;
                        try {

						vrmlString = "Group { children [ Script { eventOut MFString foo_changed \n"+
							"field MFString foo \"blah\" \n"+
							"url \"vrmlscript: function initialize() { foo_changed = foo; }\" \n"+
							"} ] } ";

						// send VRML to message event of uiItem node

						System.out.println("CreateTest:  about to create VRML, string =\n\n"+vrmlString);
						newVrml = (Node []) browser.createVrmlFromString(vrmlString);

						if (newVrml != null)
						{
							System.out.println("CreateTest:  testing created VRML, VRML = "+newVrml[0]+"\n");

							Node testNode = (Node) newVrml[0];
							Node scriptNode = null;
							ConstMFString fooString = null;
							MFNode children = null;
	//						MFString fooField = null;

							if (testNode != null)
							{
								System.out.println("CreateTest: testNode = "+testNode);
								System.out.println("CreateTest: testNode type = "+testNode.getType());

                                System.out.println("Trying to get 'children' field ");

								children = (MFNode) testNode.getExposedField("children");
								System.out.println("CreateTest: got children field from Group");

                                System.out.println("Trying to get Script from children");
								scriptNode = (Node) children.get1Value(0);
								System.out.println("CreateTest. got scriptNode from children");

                                System.out.println("Trying to get 'foo_changed' field ");
								fooString = (ConstMFString) scriptNode.getEventOut("foo_changed");
								System.out.println("CreateTest: got foo_changed field from Script");

								if (fooString == null)
								{
										System.out.println("CreateTest: fooString reference = null");
								}
								else
								{
									System.out.println("CreateTest: foo = \n  "+
										fooString.get1Value(0));
								}
							}
						}
					}
         catch (Exception exc) {
			System.out.println("CreateTest: Exception in processEvent");
				System.out.println("  Exception "+exc.toString()+
								", Message "+exc.getMessage());
		}
        break;

                    case 5:
                        System.out.println("End of tests");
                        System.out.println("Next click will be at start of test again.");
                        testStep = 0;
                        break;

				}
			}
	}
}

