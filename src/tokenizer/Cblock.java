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
package tokenizer;
public class Cblock {
    public String tagName = null;
    public String defName = null;
    public int beginPosition = -1;
    public int finalPosition = -1;
    public int beginLine = -1;
    public int finalLine = -1;
    public boolean complete = false;
    public Cblock () { }
    public Cblock (Cblock passiveCopy) {
        this.tagName = passiveCopy.tagName;
        this.defName = passiveCopy.defName;
        this.beginPosition = passiveCopy.beginPosition;
        this.beginLine = passiveCopy.beginLine;
        this.finalLine = passiveCopy.finalLine;
        this.finalPosition = passiveCopy.finalPosition;
        this.complete = passiveCopy.complete; // TRUE only if the block is completely ended;
    }
}
