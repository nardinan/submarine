//Synthesizer EAI program.

import java.io.IOException;
import java.io.EOFException;
import java.net.*;
import java.applet.*;
import vrml.external.field.*;
import vrml.external.Node;
import vrml.external.Browser;
import vrml.external.exception.*;

public class synth extends Applet implements EventOutObserver {

  // Browser we're using
  Browser browser;

  Node Note;
  EventOutSFTime NoteCclicked;
  EventOutSFTime touchNotes[] = new EventOutSFTime[12];
  EventOutSFTime touchOctaves[] = new EventOutSFTime[4];
  EventOutSFTime touchRecord = new EventOutSFTime();
  EventOutSFTime touchPlay = new EventOutSFTime();

  // interface to the MIDI engine
  int		      MIDI_port = 3337;
  InetAddress		   localaddr;
  DatagramSocket      MIDIsocket;
  int		      Octave = 1;

  // Recording string
  String Recording = "";
  boolean DoRecording = false;


  public void start_up() {
    System.out.println ("start of init");

    // Get the browser
    browser = Browser.getBrowser(this);

    // Get root node of the scene
    try {
	for (int i=0; i<12; i++) {
	  Note = browser.getNode("Note" + (i+1));
	  touchNotes[i] = (EventOutSFTime) Note.getEventOut("touchTime");
          touchNotes[i].advise (this, "Note" + (i+1));
	}
	for (int i=0; i<4; i++) {
	  Note = browser.getNode("Oct" + (i+1));
	  touchOctaves[i] = (EventOutSFTime) Note.getEventOut("touchTime");
          touchOctaves[i].advise (this, "Oct" + (i+1));
	}
	Note = browser.getNode("Play");
	touchPlay = (EventOutSFTime) Note.getEventOut("touchTime");
        touchPlay.advise (this, "Play");
	Note = browser.getNode("Record");
	touchRecord = (EventOutSFTime) Note.getEventOut("touchTime");
        touchRecord.advise (this, "Record");
    } catch (InvalidNodeException e) {
	System.out.println ("caught " + e);
    }


    // Now, we have to open the datagram socket to the MIDI engine.
    try {
      localaddr = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      System.out.println ("synth: can't get local host???");
    }

    try {
      MIDIsocket = new DatagramSocket();
    } catch (IOException e) {
      System.err.println("Failed connecting to MIDI");
    }

  }

  public void callback (EventOut who, double when, Object which) {
    String whichString = (String) which;
    if (whichString.startsWith("Note")) {
      try {
        int num = Integer.parseInt(whichString.substring(4));
        noteClicked(num-1);
      } catch (NumberFormatException ex) {
      }
    } else if (whichString.startsWith("Oct")) {
      try {
        int num = Integer.parseInt(whichString.substring(3));
        octClicked(num-1);
      } catch (NumberFormatException ex) {
      }
    } else if (whichString.equals("Play")) {
      Play();
    } else if (whichString.equals("Record")) {
       Record();
    }
  }

  private void Play() {
    if (!DoRecording) return;
    DoRecording = false;

    System.out.println ("Play clicked");
    System.out.println ("Note " + Recording);
    String names = "@head { $tempo 100 $time_sig 4/4 } @body { @channel 1 bass { /l2" +
	Recording +
	" } }";
    SendToMidi(names);
  }

  private void Record() {
    System.out.println ("Record clicked");
    Recording = "";
    DoRecording = true;
  }

  private void octClicked(int note) { // 0 <= pos <= 11
    System.out.println ("Octave " + note);
    Octave = note;
  }

  private void noteClicked(int note) { // 0 <= pos <= 11
    System.out.println ("Note " + note);
    if (DoRecording) Recording = Recording + noteString(note) +(Octave+3) + " ";
    String thisnote = "@head { $tempo 100 $time_sig 4/4 } @body { @channel 1 bass { /l2" +
	noteString(note) +
	(Octave+3) +
	" } }";
    SendToMidi (thisnote);
  }

  private String noteString(int note) {
    switch (note) {
	case  0:	return "/c";
	case  1: 	return "/c+";
	case  2:	return "/d";
	case  3:	return "/d+";
	case  4:	return "/e";
	case  5:	return "/f";
	case  6:	return "/f+";
	case  7:	return "/g";
	case  8:	return "/g+";
	case  9:	return "/a";
	case 10:	return "/a+";
	case 11:	return "/b";
	}
	return "/c";
  }

  private void SendToMidi (String midlines) {
    int strlen = midlines.length();
    try {
      byte buf[] = new byte[strlen];
      buf = midlines.getBytes();
      System.out.println ("sending "+midlines + "\n length " + buf.length + " data to " + localaddr);
        MIDIsocket.send(new DatagramPacket(buf,buf.length,
                                localaddr,MIDI_port));
    } catch (IOException e) {
       System.err.println("Failed sending to MIDI");
       return;
    }
  }



  public static void main(String[] args) {
    System.out.println ("start of main");
    synth thissynth = new synth();
    thissynth.start_up();
  }
}
