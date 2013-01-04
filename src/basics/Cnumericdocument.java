/* SubmarineX3D (client edition) v 2.1,
 * Copyright (C) 2010-2011 Andrea Nardinocchi [nardinocchi@psychogames.net]

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package basics;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
public class Cnumericdocument extends JTextField {
    final static String bannedChars = " `~!@#$%^&*()_+=\\|\"':;?/><";

    @Override
    public void processKeyEvent(KeyEvent ev) {
        char character = ev.getKeyChar();
        if((Character.isLetter(character) && !ev.isAltDown()) || bannedChars.indexOf(character) > -1) {
            ev.consume();
        } else if ((character == '-') && (getDocument().getLength() > 0)) ev.consume();
        else super.processKeyEvent(ev);
    }
}