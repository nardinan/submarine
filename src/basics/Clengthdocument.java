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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
public class Clengthdocument extends PlainDocument {
    private int limit;
    public Clengthdocument (int limit) {
        super();
        this.limit = limit;
    }
    @Override
    public void insertString (int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str != null) {
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}
