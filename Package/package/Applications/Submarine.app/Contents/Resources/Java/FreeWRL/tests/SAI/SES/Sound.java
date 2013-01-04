

public class Sound {

  static void play (String soundFile) {
    String command = "sh cp " + soundFile + " /dev/audio";
    System.err.println ("EXEC: " + command);
    try {
      Runtime.getRuntime().exec (command);
    } catch (java.io.IOException e) {
      System.err.println ("Couldn't play soundfile \"" + soundFile +"\"");
    }
  }

}
