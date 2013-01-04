

import java.io.*;


public class AnimationServerThread extends Thread {

  private DataInputStream inStream = null;
  private PrintStream     outStream = null;

  private AnimationServerMain server = null;
  private String direction = "";

  public AnimationServerThread(AnimationServerMain a,
			       DataInputStream is, PrintStream os,
			       String dir)
  {
    super ("AnimationServerThread");
    inStream = is;
    outStream = os;
    server = a;
    direction = dir;
  }


  public void setOutStream (PrintStream os)
  {
    outStream = os;
  }


  public void run() {
    String msg = null;
    while ( (msg = MobyProtocol.readMobyMessage(inStream)) != null) {
      TokenScanner scanner = new TokenScanner(msg);
      if (outStream != null)
	MobyProtocol.printMobyMessage(outStream, msg);
      if (msg.equals("quit"))
	break;
    }
    server.sayBye (this);
  }

}
