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
package subwindows;
import basics.Cbasewindow;
import java.awt.GridBagConstraints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import main.Cmaininterface;
public class Cchatinterface extends Cbasewindow {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* utilities */
    boolean exitApplication = false;
    /* end */
    public Cchatinterface (Cmaininterface backTrack) {
        super(430, 170, "Submarine's chat", true, true, true);
        setSpecial(0.50);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent evt) {
                setSpecial(0.50);
            }
            @Override
            public void windowDeactivated(WindowEvent evt) {
                setSpecial(0.10);
            }
            
        });
        this.backTrack = backTrack;
    }
    
    public void initializeComponent () {
        GridBagConstraints positioner = null;
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.pack();
        /* end */
    }
}
