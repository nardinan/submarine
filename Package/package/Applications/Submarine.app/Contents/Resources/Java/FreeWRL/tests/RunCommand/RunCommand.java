/* RunCommand.java 1.0
 * Receive VRML Script events and execute OS commands, show results
 * Created 07/19/2004 by Exile In Paradise (exile@exileinparadise.com)
 * The author has released this code to the public domain.
 * Source assembled with examples from:
 * Java For 3D and VRML Worlds
 * by Rodger Lea, Koichi Matsuda, Ken Miyashita
 * Copyright (c) 1996 New Riders Publishing
 * Listing 3.8: The Event Handler Class, p78
 * and
 * Java Developers Almanac 1.4, Volume 1
 * by Patrick Chan
 * Copyright (c) 2002 Pearson Education, Inc.
 * Example 90: Reading Output From A Command, p99
 */

import vrml.*;
import vrml.field.*;
import vrml.node.*;
import java.io.InputStream;

public class RunCommand extends Script {
	private static final boolean DEBUG = false;
	private SFString commandLine; // command passed from Script Node

	static {
		if (DEBUG) System.err.println("Java loading RunCommand class.");
	}

	public void initialize() {
		commandLine = (SFString) getField("commandLine");
	}

	public void processEvent( Event e ) {
		if (DEBUG) System.err.println("EventTime: "+e.getTimeStamp() );
		if (DEBUG) System.err.println("EventName: "+e.getName() );
		ConstSFBool v = (ConstSFBool) e.getValue();
		if ( v.getValue() ) { // got a touch event
			String command = commandLine.getValue();
			System.out.println("RunCommand \""+command+"\" beginning.");
			try {
				Process child = Runtime.getRuntime().exec(command);
				InputStream in = child.getInputStream();
				int c;
				while (( c = in.read()) != -1 ) {
					System.out.print((char)c); // writes to STDOUT
				}
				in.close();
				child.waitFor(); // wait for process to exit safely
			} catch (Exception err) { // uh-oh
				err.printStackTrace(); // tell me what exploded
			}
			System.out.println("RunCommand \""+command+"\" complete.");
		}
	}
}
