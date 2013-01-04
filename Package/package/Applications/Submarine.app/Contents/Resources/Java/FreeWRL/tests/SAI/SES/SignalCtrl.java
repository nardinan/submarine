// This class implements a controller for a Signal
// specified in  "Signal.wrl"

import vrml.*;
import vrml.field.*;
import vrml.node.*;

public class SignalCtrl {

    private SFFloat setPanel1;
    private SFFloat setPanel2;
  private int    state = 0;

  public SignalCtrl (SFNode signal)
  {
      Node node = (Node) signal.getValue();
      setPanel1 = (SFFloat) node.getEventIn("setPanel1");
      setPanel2 = (SFFloat) node.getEventIn("setPanel2");
  }

  public void setState (int newState, double time)
  {
    state = newState;
    switch (newState) {
    case 0 : setPanels ( 0, 0 ); break;
    case 1 : setPanels ( 1, 0 ); break;
    case 2 : setPanels ( 1, 1 ); break;
    default: break;
    }
  }
  public int  getState () { return state; }
  public void nextState ( double time )
  {
    setState ((state++) % 3, time);
  }

  private void setPanels ( float panel1, float panel2 )
  {
      setPanel1.setValue (panel1);
      setPanel2.setValue (panel2);
  }

}

