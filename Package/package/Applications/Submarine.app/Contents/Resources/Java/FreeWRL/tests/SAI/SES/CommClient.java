
import java.net.*;
import java.io.*;

import vrml.field.SFColor;


public class CommClient {

  private boolean _run = true;
  private boolean _clientFound = false;

  protected DataInputStream is = null;
  protected PrintStream os = null;
  protected Socket clientSocket = null;

  private int     _ltrSignalState = 0;
  private int     _rtlSignalState = 0;
  private int     _time = -1;
  private int     _trainSpeed = 70;
  private boolean _reset = false;

  // to simulate bouncing of the sensor we need this:
  private int[]     sensorActivateTime  = new int [6];
  private boolean[] sensorActivated     = new boolean [6];
  private int[]     sensorLastSendTime  = new int [6];
  private boolean[] sensorLastSend      = new boolean [6];
  private boolean   sensorWorking[]     = { true, true, true,
					    true, true, true };
  private final static int BOUNCING_TIME_LIMIT = 130;
  private final static int BOUNCING_PERIOD     = 35;

  public CommClient ()
  {
    establish ();
  }

  protected void establish ()
  {
    try {
      clientSocket = new Socket("", 4445);
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: ");
    } catch (IOException e) {
      System.out.println("Could not listen on port: " + 4445 + ", " + e);
      _clientFound = false;
      return;
    }

    System.out.println ("SESAnim-Client OK!");
    try {
      is = new DataInputStream(
	      new BufferedInputStream(clientSocket.getInputStream()));
      os = new PrintStream(
              new BufferedOutputStream(clientSocket.getOutputStream(),
					       1024), false);
      _clientFound = true;
    } catch (IOException e) {
      _clientFound = false;
      e.printStackTrace();
    }

  }



  private final void sendSensor ( int direction,
				  int sensor,
				  boolean value,
				  boolean cValue )
  {
    if (_clientFound) {
      String message = "sensor V " + sensorName (direction, sensor) +
	" " + String.valueOf (value) + " " + _time;
      String cMessage = "sensor CV " + sensorName (direction, sensor) +
	" " + String.valueOf (cValue) + " " + _time;

      if (Math.random() < .5 ) {
	MobyProtocol.printMobyMessage(os, cMessage);
	MobyProtocol.printMobyMessage(os, message);
      } else {
	MobyProtocol.printMobyMessage(os, message);
	MobyProtocol.printMobyMessage(os, cMessage);
      }
    }
  }

  public final void sensorDefect ( int direction, int sensor )
  {
    sensorWorking[sensorNr (direction, sensor)] = false;
    sendSensor ( direction, sensor, false, false );
  }

  public final void sensorOK ( int direction, int sensor )
  {
    int nr = sensorNr (direction, sensor);
    sensorActivated[nr] = false;
    sensorLastSend[nr] = true;
    sensorWorking[nr] = true;
    sendSensor (direction, sensor, false, true);
  }


  public final void sensorActivated ( int direction, int sensor )
  {
    int nr = sensorNr (direction, sensor);
    sensorActivated[nr] = true;
    sensorLastSend[nr] = false;
    sensorActivateTime[nr] = _time;
    sensorLastSendTime[nr] = _time - BOUNCING_PERIOD;
    bounceSensor (direction, sensor);
  }


  private final void bounceSensor ( int direction, int sensor )
  {
    int nr = sensorNr (direction, sensor);
    if (sensorWorking[nr]) {
      if (sensorActivated[nr])
	if (_time < sensorActivateTime[nr] + BOUNCING_TIME_LIMIT ) {
	  if (_time >= sensorLastSendTime[nr] + BOUNCING_PERIOD) {
	    sensorLastSend[nr] = !sensorLastSend[nr];
	    sensorLastSendTime[nr] = _time;
	    sendSensor ( direction, sensor,
			 sensorLastSend[nr], !sensorLastSend[nr] );
	  }
	} else {
	  sensorActivated[nr] = false;
	  sensorLastSend[nr] = true;
	  sendSensor ( direction, sensor, false, true );
	}
    }
  }


  public final int readTime ()
  {
    if (_clientFound) {
      update ();
      if ( !_reset )
	for (int sensor = 0; sensor < 3; sensor++) {
	  bounceSensor ( 1, sensor);
	  bounceSensor (-1, sensor);
	}
      return _time;
    } else {
      if (_time < 0)
	_time = 0;
      else
	_time+=4;
      return _time;
    }
  }


  public final int readLtRSignal () {
    return _ltrSignalState;
  }

  public final int readRtLSignal () {
    return _rtlSignalState;
  }

  public final void toggleLtRSignal () {
      _ltrSignalState = _ltrSignalState == 2 ? 0 : 2;
  }

  public final void toggleRtLSignal () {
      _rtlSignalState = _rtlSignalState == 2 ? 0 : 2;
  }

  // return speed in meter per 1/100 second simtime
  // 70 km/h will be the limit.
  // train distance is 18m and must be min. 0,310 sec
  public final double readTrainSpeed () {
    return ((double)_trainSpeed) / 1400;
  }


  public final boolean needsReset () {
    if (_reset) {
      _reset = false;
      _time = -1;
      return true;
    }
    return false;
  }


  public void close ()
  {
    if (_clientFound) {
      //System.err.println ("closing Communication");
      //_clientFound = false;
      //MobyProtocol.printMobyMessage(os, "quit");

      //try {
      //os.close();
      //is.close();
      //clientSocket.close();
      //} catch (IOException e) { e.printStackTrace(); }
    }
  }



  protected void update ()
  {
    if (_clientFound)
      {
	String input;
	try {
	  while (is.available() > 3) {
	    input = MobyProtocol.readMobyMessage (is);

	    try {
	    TokenScanner tokenScanner = new TokenScanner (input);
	    String keyword = tokenScanner.nextToken ();
	    if (keyword.equals("time")) {
	      _time = Integer.parseInt(tokenScanner.nextToken());
	      //System.err.println ("SimTime " + ((float) _time/100) + " sec");
	    }
	    else if (keyword.equals("signal")) {
	      String direction = tokenScanner.nextToken ();
	      String value = tokenScanner.nextToken ();
	      int newState = 0;
	      if (value.equals ("Stop"))
		newState = 0;
	      else if (value.equals ("StopDemand"))
		newState = 1;
	      else if (value.equals ("Go"))
		newState = 2;

	      if (direction.equals ("1"))
		_ltrSignalState = newState;
	      else if (direction.equals ("2"))
		_rtlSignalState = newState;

	      _time = Integer.parseInt(tokenScanner.nextToken());
	    }
	    else if (keyword.equals("sendAck")) {
	      MobyProtocol.printMobyMessage(os, "ack");
	    }
	    else if (keyword.equals("trainSpeed")) {
	      _trainSpeed = Integer.parseInt(tokenScanner.nextToken());
	    }
	    else if (keyword.equals("restart")) {
	      _reset = true;
	    }
	    else if (keyword.equals("quit")) {
	      close ();
	      return;
	    }
	    } catch (NumberFormatException e) {
	      System.err.println ("#number format error!");
	    }
	  } // while available
	} catch (IOException e) { e.printStackTrace(); }
      }
  }

  private static String sensorName (int direction, int i) {
    char dirChar;
    if (direction == 1)
      dirChar = '1';
    else
      dirChar = '2';
    switch ( i % 3 ) {
    case 0 : return ("ES " + dirChar);
    case 1 : return ("CS " + dirChar);
    case 2 : return ("LS " + dirChar);
    default: return "";
    }
  }

  private static int sensorNr ( int direction, int sensor ) {
    return ( (direction > 0 ) ? sensor : sensor + 3 );
  }


}


