import java.awt.*;
import java.applet.*;
import java.util.Vector;
import vrml.external.field.*;
import vrml.external.Node;
import vrml.external.Browser;
import vrml.external.exception.*;
import netscape.javascript.JSObject;

/**
 * This Class implements Tic-Tac-Toe VRML 2.0 EAI applet.
 * @author Seongyong Lim
 * @author sylim@sly.kotel.co.kr
 */

public class TicTacToeEAI extends Applet implements EventOutObserver {
  TextArea textArea = null;

  Browser browser = null;
  EventInMFNode rootAddChildren = null;
  EventInMFNode rootRemoveChildren = null;
  EventOutSFTime touchTimes[] = new EventOutSFTime[9];
  EventInSFTime checkDelayIn = null;
  EventOutSFBool checkDelayOut = null;
  EventInSFTime nextDelayIn = null;
  EventOutSFBool nextDelayOut = null;
  boolean error = false;
  Vector addedNodes = new Vector();
  boolean[] isFilled;
  boolean userTurn = true;
  boolean userCanClick = true;

  public void init() {
    add(new Button("New Game"));
    textArea = new TextArea(12, 40);
    textArea.setEditable(false);
    add(textArea);

    JSObject win = JSObject.getWindow(this);
    JSObject doc = (JSObject) win.getMember("document");
    JSObject embeds = (JSObject) doc.getMember("embeds");
    browser = (Browser) embeds.getSlot(0);

    try {
      Node node;

      node = browser.getNode("root");
      rootAddChildren = (EventInMFNode) node.getEventIn("addChildren");
      rootRemoveChildren = (EventInMFNode) node.getEventIn("removeChildren");

      for (int i=0; i< 9; i++) {
	node = browser.getNode("touch" + (i+1));
	touchTimes[i] = (EventOutSFTime) node.getEventOut("touchTime");
	touchTimes[i].advise(this, "touched" + (i+1));
      }

      node = browser.getNode("checkDelay");
      checkDelayIn = (EventInSFTime) node.getEventIn("set_startTime");
      checkDelayOut = (EventOutSFBool) node.getEventOut("isActive");
      checkDelayOut.advise(this, "checkForWin");

      node = browser.getNode("nextDelay");
      nextDelayIn = (EventInSFTime) node.getEventIn("set_startTime");
      nextDelayOut = (EventOutSFBool) node.getEventOut("isActive");
      nextDelayOut.advise(this, "nextTurn");

    } catch (InvalidNodeException ne) {
      ne.printStackTrace();
      textArea.appendText(ne.getMessage());
      error = true;

    } catch (InvalidEventOutException ee) {
      ee.printStackTrace();
      textArea.appendText(ee.getMessage());
      error = true;
    }

    textArea.appendText("Welcome to VRML2.0 Tic-Tac-Toe Game.\n");
    textArea.appendText("Good Luck!\n");
  }

  private double currentTime() {
    return (double) System.currentTimeMillis() / 1000;
  }

  private String moveVrml(int position, boolean userTurn) {
    String result = "Transform { translation ";
    if (position == 0) result += " -1 1 .25 ";
    else if (position == 1) result += " -1 0 .25 ";
    else if (position == 2) result += " -1 -1 .25 ";
    else if (position == 3) result += " 0 1 .25 ";
    else if (position == 4) result += " 0 0 .25 ";
    else if (position == 5) result += " 0 -1 .25 ";
    else if (position == 6) result += " 1 1 .25 ";
    else if (position == 7) result += " 1 0 .25 ";
    else if (position == 8) result += " 1 -1 .25 ";
    else return "";
    if (userTurn && userFirst || !userTurn && !userFirst) // Sphere shape
      result += " children [ Shape { geometry Sphere { radius .25 } appearance Appearance { material Material { diffuseColor 1 0 0 }}}]}";
    else // Cone shape
      result += " rotation 1 0 0 1.57 children [ Shape { geometry Cone { bottomRadius .25 height .5 } appearance Appearance { material Material { diffuseColor 0 1 0 }}}]}";
    return result;
  }

  private void userClicked(int pos, double time) { // 0 <= pos <= 8
    if (!userCanClick) return;
    if (yourMove(pos)) {
      try {
	Node[] nodes = browser.createVrmlFromString(moveVrml(pos, true));
	rootAddChildren.setValue(nodes);
	addedNodes.addElement(nodes[0]);
      }
      catch (InvalidVrmlException ex) {
      }
      userCanClick = false;
      userTurn = false;
      checkDelayIn.setValue(currentTime());
    }
  }

  private void computerClicked(int pos, double time) { // 0 <= pos <= 8
    try {
      Node[] nodes = browser.createVrmlFromString(moveVrml(pos, false));
      rootAddChildren.setValue(nodes);
      addedNodes.addElement(nodes[0]);
    }
    catch (InvalidVrmlException ex) {
    }
    checkDelayIn.setValue(currentTime());
    userTurn = true;
  }

  private void showMessage(String msg) {
    textArea.appendText(msg + "\n");
    try {
      Node[] nodes = browser.createVrmlFromString("Transform { translation 0 0 2.3 children [ Shape { geometry Text { string \"" + msg + "\" fontStyle FontStyle { justify [ \"MIDDLE\" \"MIDDLE\" ] size 1 } maxExtent 3 } appearance Appearance { material Material { diffuseColor 1 1 1 } } } ] }");
      rootAddChildren.setValue(nodes);
      addedNodes.addElement(nodes[0]);
    }
    catch (InvalidVrmlException ex) {
    }
  }

  public void callback (EventOut who, double when, Object which) {
    String whichString = (String) which;
    if (whichString.startsWith("touched")) {
      try {
	int num = Integer.parseInt(whichString.substring(7));
	userClicked(num-1, when);
      } catch (NumberFormatException ex) {
      }
    }
    else if (whichString.equals("checkForWin")) {
      EventOutSFBool v = (EventOutSFBool) who;
      if (v.getValue() == false) {
	switch (status()) {
	case WIN:
	  showMessage("You Lose!");
	  break;
	case LOSE:
	  showMessage("You Win!");
	  break;
	case STALEMATE:
	  showMessage("Stalemate!");
	  break;
	default:
	  nextDelayIn.setValue(currentTime());
	}
      }
    }
    else if (whichString.equals("nextTurn")) {
      EventOutSFBool v = (EventOutSFBool) who;
      if (v.getValue() == false) {
	if (userTurn) {
	  userCanClick = true;
	} else {
	  if (black == 0) // if computer first and first move
	    computerClicked((int)(Math.random() * 9), when);
	  else
	    computerClicked(myMove(), when);
	}
      }
    }
  }

  public boolean action(Event event, Object what) {
    if (error) {
      System.err.println("Had an error during initialization");
      return true;
    }
    Node[] nodes = new Node[addedNodes.size()];
    for (int i=0; i< addedNodes.size(); i++)
      nodes[i] = (Node) addedNodes.elementAt(i);
    rootRemoveChildren.setValue(nodes);
    addedNodes = new Vector();
    white = black = 0;
    userFirst = !userFirst;
    userTurn = userFirst;
    nextDelayIn.setValue(currentTime());
    return true;
  }

  // TicTacToe logic from JDK demo applet by Arthur van Hoff
    /**
     * White's current position. The computer is white.
     */
    int white;

    /**
     * Black's current position. The user is black.
     */
    int black;

    /**
     * The squares in order of importance...
     */
    final static int moves[] = {4, 0, 2, 6, 8, 1, 3, 5, 7};

    /**
     * The winning positions.
     */
    static boolean won[] = new boolean[1 << 9];
    static final int DONE = (1 << 9) - 1;
    static final int OK = 0;
    static final int WIN = 1;
    static final int LOSE = 2;
    static final int STALEMATE = 3;

    /**
     * Mark all positions with these bits set as winning.
     */
    static void isWon(int pos) {
	for (int i = 0 ; i < DONE ; i++) {
	    if ((i & pos) == pos) {
		won[i] = true;
	    }
	}
    }

    /**
     * Initialize all winning positions.
     */
    static {
	isWon((1 << 0) | (1 << 1) | (1 << 2));
	isWon((1 << 3) | (1 << 4) | (1 << 5));
	isWon((1 << 6) | (1 << 7) | (1 << 8));
	isWon((1 << 0) | (1 << 3) | (1 << 6));
	isWon((1 << 1) | (1 << 4) | (1 << 7));
	isWon((1 << 2) | (1 << 5) | (1 << 8));
	isWon((1 << 0) | (1 << 4) | (1 << 8));
	isWon((1 << 2) | (1 << 4) | (1 << 6));
    }

    /**
     * Compute the best move for white.
     * @return the square to take
     */
    int bestMove(int white, int black) {
	int bestmove = -1;

      loop:
	for (int i = 0 ; i < 9 ; i++) {
	    int mw = moves[i];
	    if (((white & (1 << mw)) == 0) && ((black & (1 << mw)) == 0)) {
		int pw = white | (1 << mw);
		if (won[pw]) {
		    // white wins, take it!
		    return mw;
		}
		for (int mb = 0 ; mb < 9 ; mb++) {
		    if (((pw & (1 << mb)) == 0) && ((black & (1 << mb)) == 0)) {
			int pb = black | (1 << mb);
			if (won[pb]) {
			    // black wins, take another
			    continue loop;
			}
		    }
		}
		// Neither white nor black can win in one move, this will do.
		if (bestmove == -1) {
		    bestmove = mw;
		}
	    }
	}
	if (bestmove != -1) {
	    return bestmove;
	}

	// No move is totally satisfactory, try the first one that is open
	for (int i = 0 ; i < 9 ; i++) {
	    int mw = moves[i];
	    if (((white & (1 << mw)) == 0) && ((black & (1 << mw)) == 0)) {
		return mw;
	    }
	}

	// No more moves
	return -1;
    }

    /**
     * User move.
     * @return true if legal
     */
    boolean yourMove(int m) {
	if ((m < 0) || (m > 8)) {
	    return false;
	}
	if (((black | white) & (1 << m)) != 0) {
	    return false;
	}
	black |= 1 << m;
	return true;
    }

    /**
     * Computer move.
     * @return true if legal
     */
    int myMove() {
	if ((black | white) == DONE) {
	    return -1;
	}
	int best = bestMove(white, black);
	white |= 1 << best;
	return best;
    }

    /**
     * Figure what the status of the game is.
     */
    int status() {
	if (won[white]) {
	    return WIN;
	}
	if (won[black]) {
	    return LOSE;
	}
	if ((black | white) == DONE) {
	    return STALEMATE;
	}
	return OK;
    }

    /**
     * Who goes first in the next game?
     */
    boolean userFirst = true;

}
