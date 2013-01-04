

public class TokenScanner {

  protected int _pos = 0;
  protected String _message = "";


  public TokenScanner (String msg) {
    _message = msg;
  }


  public String nextToken () {
    int start = _pos;
    try {
      while ((_pos < _message.length()) && (_message.charAt(_pos) != ' '))
	{ _pos++; }
      _pos++;
      return _message.substring(start, _pos-1);
    } catch (StringIndexOutOfBoundsException e) {
      System.err.println ("## TokenScanner.nextToken out of bounds");
      return "";
    }
  }

}
