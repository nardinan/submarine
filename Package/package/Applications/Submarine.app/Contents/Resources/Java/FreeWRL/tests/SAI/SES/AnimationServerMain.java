
import java.net.*;
import java.io.*;

class AnimationServerMain {

    private boolean _run = true;
    private int simPort  = 4444;
    private int animPort = 4445;

    private AnimationServerThread fromSim, fromAnim;

    private ServerSocket simServerSocket = null;
    private ServerSocket animServerSocket = null;
    private Socket simClientSocket = null;
    private Socket animClientSocket = null;

    private DataInputStream simIS = null;
    private PrintStream simOS = null;
    private DataInputStream animIS = null;
    private PrintStream animOS = null;


    public void run() {

        animServerSocket = setupServer (animPort);
	simServerSocket = setupServer (simPort);
	System.out.println ("Server OK! ...waiting for animation client...");

	if (animServerSocket != null)
	  animClientSocket = setupClient (animServerSocket);
	else
	  System.err.println("animServerSocket == null");

	System.out.println ("Client OK! ...waiting for simulation client...");

	simClientSocket = setupClient (simServerSocket);

	animIS = openIS (animClientSocket);
	animOS = openOS (animClientSocket);
	simIS = openIS (simClientSocket);
	simOS = openOS (simClientSocket);

	System.out.println ("Client OK! ...reading from connections...");

	fromSim = new
	  AnimationServerThread (this, simIS, animOS, "from Sim: ");
	fromAnim = new
	  AnimationServerThread (this, animIS, simOS, "from Anim: ");
	fromSim.start();
	fromAnim.start();
    }


  private ServerSocket setupServer (int port) {
        ServerSocket s = null;
	try {
	  s = new ServerSocket(port);
	} catch (IOException e) {
	  System.out.println("Could not listen on port: " + port
			     + ", " + e);
	  System.exit(1);
	}
	return s;
  }

  private Socket setupClient (ServerSocket server) {
        Socket c = null;
        try {
	  c = server.accept();
        } catch (IOException e) {
	  System.out.println("Accept failed: " + e);
	  System.exit(1);
        }
	return c;
  }

  private DataInputStream openIS (Socket c) {
        DataInputStream is = null;
        try {
	    is = new DataInputStream(
	       new BufferedInputStream(c.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
	return is;
  }

  private PrintStream openOS (Socket c) {
        PrintStream os = null;
        try {
	    os = new PrintStream(
               new BufferedOutputStream(c.getOutputStream(),
					1024), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
	return os;
  }



  private void close (Socket c, ServerSocket s,
		      DataInputStream is, PrintStream os) {
        try {
            os.close();
            is.close();
            c.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
  }


    public void sayBye (AnimationServerThread t) {
      if (t == fromSim) {
	fromSim = null;
	if (fromAnim != null) {
	  fromAnim.setOutStream(null);
	  close (simClientSocket, simServerSocket, simIS, simOS);
	  System.err.println ("restarting Sim-Server...");
	  simServerSocket = setupServer (simPort);
	  System.err.println ("Server OK, waiting for client...");
	  simClientSocket = setupClient (simServerSocket);
	  simIS = openIS (simClientSocket);
	  simOS = openOS (simClientSocket);
	  System.err.println ("Client OK, reading from connections...");
	  fromSim = new
	      AnimationServerThread (this, simIS, animOS, "from Sim: ");
	  fromAnim.setOutStream (simOS);
	  MobyProtocol.printMobyMessage (animOS, "restart");
	  fromSim.start();
	  return;
	}
      }
      if (t == fromAnim) {
	fromAnim = null;
	if (fromSim != null) {
	  fromSim.setOutStream(null);
	  return;
	}
      }
      System.err.println ("Threads done!");
      close (simClientSocket, simServerSocket, simIS, simOS);
      close (animClientSocket, animServerSocket, animIS, simOS);
    }

}

