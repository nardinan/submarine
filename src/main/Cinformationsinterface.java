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
package main;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Cinformationsinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JLabel consoleInformations, permissionsInformations;
    /* end */
    public Cinformationsinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        consoleInformations = new JLabel();
        permissionsInformations = new JLabel();
        consoleInformations.setText("<html>[0 unread messages|<font color=\"#FF0000\">0</font> unread errors]</html>"); 
        /* blitting */
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(consoleInformations, BorderLayout.WEST);
        this.add(permissionsInformations, BorderLayout.EAST);
        /* end */
    }

    public void updateConsole (int messages, int errors) {
        consoleInformations.setText("<html>["+messages+" unread messages|<font color=\"#FF0000\">"+errors+"</font> unread errors]</html>"); 
    }
    
    public void updatePermissions (String permissions) {
        permissionsInformations.setText("<html>["+permissions+"]</html>");
    }
}