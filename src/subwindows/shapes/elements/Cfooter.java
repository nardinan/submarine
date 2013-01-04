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
package subwindows.shapes.elements;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import main.Cmaininterface;
public class Cfooter extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public JButton colorSelect;
    public JComboBox colorKind;
    /* end */
    /* semaphores */
    final public Object listLocker = new Object();
    /* end */
    /* utils */
    public int red = 200, green = 200, blue = 200;
    /* end */
    public Cfooter (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        colorSelect = new JButton();
        colorKind = new JComboBox();
        colorSelect.setText("pick color");
        colorSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadColor();
            }
        });
        colorKind.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "diffuse color", "emissive color", "specular color" }));
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.gridwidth = 2; // take 3 cols
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(colorKind, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 0;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(colorSelect, positioner);
        /* end */
    }

    public void loadColor () {
        Color newColor = JColorChooser.showDialog(this, "Choose a color", new Color(red, green, blue));
        if (newColor != null) {
            red = newColor.getRed();
            green = newColor.getGreen();
            blue = newColor.getBlue();
        }
    }

    public void resetComponents () {
        red = 200;
        green = 200;
        blue = 200;
    }
}
