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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import main.Cmaininterface;
import tokenizer.Cblock;
public class Cheader extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public JTextField objectName;
    public JRadioButton nameSelect, useSelect;
    public JComboBox useName;
    /* end */
    /* semaphores */
    final public Object listLocker = new Object();
    /* end */
    public Cheader(Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        objectName = new JTextField();
        nameSelect = new JRadioButton();
        useSelect = new JRadioButton();
        useName = new JComboBox();
        nameSelect.setText("DEF");
        nameSelect.setSelected(true);
        nameSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (useName.getItemCount() > 0) { // it's possible only if there is some elements
                    useSelect.setSelected(!nameSelect.isSelected());
                    useName.setEnabled(!nameSelect.isSelected());
                    objectName.setEnabled(nameSelect.isSelected());
                } else {
                    nameSelect.setSelected(true);
                    useSelect.setSelected(!nameSelect.isSelected());
                    useName.setEnabled(!nameSelect.isSelected());
                    objectName.setEnabled(nameSelect.isSelected());
                }
            }
        });
        useSelect.setText("USE");
        useSelect.setSelected(false);
        useName.setEnabled(false);
        useSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (useName.getItemCount() > 0) { // it's possible only if there is some elements
                    nameSelect.setSelected(!useSelect.isSelected());
                    useName.setEnabled(useSelect.isSelected());
                    objectName.setEnabled(!useSelect.isSelected());
                } else {
                    nameSelect.setSelected(true);
                    useSelect.setSelected(!nameSelect.isSelected());
                    useName.setEnabled(!nameSelect.isSelected());
                    objectName.setEnabled(nameSelect.isSelected());
                }
            }
        });
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(nameSelect, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 0;
        positioner.gridwidth = 3; // take 3 cols
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(objectName, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(useSelect, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.gridwidth = 3; // take 3 cols
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(useName, positioner);
        /* end */
    }

    public void loadTags (ArrayList<Cblock> blockList, String definition) {
        Cblock backupBlock = null;
        int elements = 1;
        synchronized (listLocker) {
            useName.removeAllItems();
            if (blockList != null) {
                Iterator<Cblock> listIterator = blockList.iterator();
                while (listIterator.hasNext()) {
                    backupBlock = listIterator.next();
                    if ((backupBlock.tagName.equalsIgnoreCase(definition)) && (backupBlock.defName != null)) {
                        useName.addItem(backupBlock.defName);
                        elements++;
                    }
                }
                objectName.setText(definition+"-"+elements);
            }
        }
    }

    public void resetComponents () {
        objectName.setEnabled(true);
        nameSelect.setSelected(true);
        useSelect.setSelected(false);
        useName.setEnabled(false);
    }
}
