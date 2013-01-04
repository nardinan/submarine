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
package subwindows.configuration;
import basics.Cdatafiles;
import basics.Czebralist;
import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.*;
import main.Cmaininterface;
public class Cstyleinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private Czebralist mainView;
    private JLabel fontLabel;
    private JComboBox fontFamily;
    private JButton fontBackground, fontForeground;
    private JSpinner fontSize;
    private JCheckBox fontBold, fontItalic;
    /* end */
    /* utils */
    private String prevSelection = null;
    private int background[] = new int[3], foreground[] = new int[3];
    /* end */
    public Cstyleinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        Hashtable backupCategories = backTrack.X3Dsyntaxizer.styleElements;
        Vector defaultCategories = new Vector();
        int index = 0;
        Enumeration keysArray = backupCategories.keys();
        while (keysArray.hasMoreElements()) {
            defaultCategories.add(keysArray.nextElement());
        }
        mainView = new Czebralist(defaultCategories);
        fontLabel = new JLabel();
        fontFamily = new JComboBox();
        fontBackground = new JButton();
        fontForeground = new JButton();
        fontSize = new JSpinner();
        fontBold = new JCheckBox();
        fontItalic = new JCheckBox();
        fontLabel.setText("Font family");
        fontBold.setText("is Bold");
        fontItalic.setText("is Italic");
        fontSize.setModel(new SpinnerNumberModel(12, 10, 48, 2));
        mainView.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                loadSelectedValue();
            }
        });
        fontFamily.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menlo", "Monospaced", "Lucida Grande", "Lucida Sans", "Lucida Sans Unicode", "Arial", "Verdana", "Geneva", "Georgia", "Helvetica" }));
        fontBackground.setText("select Background color");
        fontBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadColor(background);
            }
        });
        fontForeground.setText("select Foreground color");
        fontForeground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadColor(foreground);
            }
        });
        fontFamily.setEnabled(false);
        fontBackground.setEnabled(false);
        fontForeground.setEnabled(false);
        fontSize.setEnabled(false);
        fontBold.setEnabled(false);
        fontItalic.setEnabled(false);
        JScrollPane mainScroll = new JScrollPane(mainView);
        mainScroll.setBorder(javax.swing.BorderFactory.createTitledBorder("categories"));
        mainScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainScroll.setMaximumSize(new Dimension(200, 3500));
        mainScroll.setMinimumSize(new Dimension(130, 240));
        mainScroll.setPreferredSize(new Dimension(130, 240));
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.loadSelectedValue();
        /* blitting */
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.anchor = GridBagConstraints.NORTH;
        positioner.fill = GridBagConstraints.BOTH;
        this.add(container, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.gridheight = 4;
        positioner.weighty = 1.0;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.fill = GridBagConstraints.VERTICAL;
        container.add(mainScroll, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 0;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(fontLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 0;
        positioner.weightx = 1.0;
        positioner.anchor = GridBagConstraints.EAST;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        container.add(fontFamily, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 3;
        positioner.gridy = 0;
        positioner.weightx = 1.0;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        container.add(fontSize, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.gridwidth = 3;
        positioner.weightx = 1.0;
        positioner.anchor = GridBagConstraints.EAST;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        container.add(fontBackground, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 2;
        positioner.gridwidth = 3;
        positioner.weightx = 1.0;
        positioner.anchor = GridBagConstraints.EAST;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        container.add(fontForeground, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.SOUTHWEST;
        container.add(fontBold, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 3;
        positioner.gridwidth = 2;
        positioner.weightx = 1.0;
        positioner.anchor = GridBagConstraints.SOUTHEAST;
        container.add(fontItalic, positioner);
        /* end */
    }

    public void saveInformations () {
        backTrack.X3Dsyntaxizer.saveSyntax(Cdatafiles.syntax);
    }

    private void loadSelectedValue () {
        String selected = (String) mainView.getSelectedValue(), backupColor = background[0]+","+background[1]+","+background[2]+"|"+foreground[0]+","+foreground[1]+","+foreground[2];
        if (selected != null) {
            if (prevSelection != null) {
                String toUpdate = ((String) fontFamily.getSelectedItem())+"|"+fontSize.getValue()+"|"+(fontBold.isSelected()?"true":"false")+"|"+(fontItalic.isSelected()?"true":"false")+"|"+backupColor;
                backTrack.X3Dsyntaxizer.updateStyle(prevSelection, toUpdate);
            }
            String fromSyntaxizer, parameters[], subColor[];
            fromSyntaxizer = (String) backTrack.X3Dsyntaxizer.styleElements.get(selected);
            parameters = fromSyntaxizer.split("\\|");
            if (parameters.length == 6) {
                fontFamily.setSelectedItem(parameters[0]);
                fontSize.setValue(new Integer(parameters[1]));
                subColor = parameters[4].split(",");
                background[0] = Integer.parseInt(subColor[0]);
                background[1] = Integer.parseInt(subColor[1]);
                background[2] = Integer.parseInt(subColor[2]);
                subColor = parameters[5].split(",");
                foreground[0] = Integer.parseInt(subColor[0]);
                foreground[1] = Integer.parseInt(subColor[1]);
                foreground[2] = Integer.parseInt(subColor[2]);
                fontBold.setSelected((parameters[2].equalsIgnoreCase("true")?true:false));
                fontItalic.setSelected((parameters[3].equalsIgnoreCase("true")?true:false));
            }
            fontFamily.setEnabled(true);
            fontBackground.setEnabled(true);
            fontForeground.setEnabled(true);
            fontSize.setEnabled(true);
            fontBold.setEnabled(true);
            fontItalic.setEnabled(true);
            prevSelection = selected;
        }
    }

    public void loadColor (int[] colorArray) {
        Color newColor = JColorChooser.showDialog(this, "Choose a color", new Color(colorArray[0], colorArray[1], colorArray[2]));
        if (newColor != null) {
            colorArray[0] = newColor.getRed();
            colorArray[1] = newColor.getGreen();
            colorArray[2] = newColor.getBlue();
        }
    }
    
    public void applyValues () {
        this.loadSelectedValue();
        this.saveInformations();
    }
}
