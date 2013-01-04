import java.io.*;

class MobyProtocol {

    public static String readMobyMessage (DataInputStream is) {
      String lengthStr = "";
      String message = "";
      try {
	for (int i = 0; i < 4; i++) {
	  lengthStr += (char)is.read();
	}
      } catch (IOException e) {
	System.err.println ("##AnimationServer: reading length error!");
	return null;
      }
      try {
	int length = Integer.parseInt(lengthStr);
	try {
	  for (int i = 0; i < length; i++) {
	    message += (char)is.read();
	  }
	  return message;
	} catch (IOException e) {
	  System.err.println ("##AnimationServer: reading message error!");
	  return null;
	}
      } catch (NumberFormatException e) {
	System.err.println ("##AnimationServer: illegal length header!");
	return null;
      }
    }


    public static void printMobyMessage (PrintStream os, String msg) {
      int length = msg.length();
      int pos = 1000;
	for (int i = 0; i < 4; i++) {
	  os.print( (char)('0' + length / pos) );
	  length %= pos;
	  pos /= 10;
	}
	os.print( msg );
	os.flush ();
    }

}
