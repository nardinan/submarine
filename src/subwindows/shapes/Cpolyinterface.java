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
package subwindows.shapes;
import basics.Cbasewindow;
import basics.Cpaintpanel;
import basics.Cpaintpanel.Csingleton;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import main.Cmaininterface;
import subwindows.shapes.elements.Cfooter;
import subwindows.shapes.elements.Cheader;
import subwindows.shapes.elements.Csubfooter;
public class Cpolyinterface extends Cbasewindow {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public Cheader headerBlock;
    private Cfooter footerBlock;
    private Csubfooter subfooterBlock;
    private JCheckBox convexBox;
    private Cpaintpanel paintPanel;
    private JButton submitButton, cancelButton;
    /* end */
    public Cpolyinterface (Cmaininterface backTrack) {
        super(510, 620, "add a Poly", false, false, true);
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        headerBlock = new Cheader(backTrack);
        footerBlock = new Cfooter(backTrack);
        subfooterBlock = new Csubfooter(backTrack);
        paintPanel = new Cpaintpanel();
        convexBox = new JCheckBox();
        submitButton = new JButton();
        cancelButton = new JButton();
        headerBlock.initializeComponent();
        footerBlock.initializeComponent();
        subfooterBlock.initializeComponent();
        convexBox.setText("convex");
        submitButton.setText("insert");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertComplete();
            }
        });
        cancelButton.setText("cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formWindowClosing();
            }
        });
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel infoContainer = new JPanel(new GridBagLayout());
        infoContainer.setBorder(BorderFactory.createTitledBorder("Draw Area"));
        infoContainer.setMaximumSize(new Dimension(210, 3500));
        infoContainer.setMinimumSize(new Dimension(210, 100));
        infoContainer.setPreferredSize(new Dimension(210, 100));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        positioner.anchor = GridBagConstraints.WEST;
        infoContainer.add(paintPanel, positioner);
        /* blitting */
        this.add(container);
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.gridwidth = 3;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(headerBlock, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.gridwidth = 3;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        positioner.anchor = GridBagConstraints.CENTER;
        container.add(infoContainer, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 2;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(convexBox, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 3;
        positioner.gridwidth = 3;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(footerBlock, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 4;
        positioner.gridwidth = 3;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(subfooterBlock, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 5;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(cancelButton, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 5;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(submitButton, positioner);
        this.pack();
        /* end */
    }

    private void insertComplete () {
        String parameters[], completeString = "<Transform translation='0.0 0.0 0.0' rotation='0.0 0.0 0.0 0.0'>\n\t\t<Shape>\n\t\t\t<IndexedFaceSet";
        Csingleton backupSingleton = null, backtrackSingleton = null, supportSingleton = null;
        int edgesVisited = 0;
        if (headerBlock.nameSelect.isSelected()) {
            completeString += " DEF='"+headerBlock.objectName.getText()+"' coordIndex='";
            if (edgesVisited < paintPanel.points.size()) {
                backupSingleton = (Csingleton) paintPanel.points.get(edgesVisited);
                completeString += paintPanel.points.indexOf(backupSingleton)+" ";
                backtrackSingleton = backupSingleton;
                backupSingleton = backtrackSingleton.aLink;
                edgesVisited++;
            }
            while ((backupSingleton != null) && (edgesVisited <= paintPanel.points.size())) {
                completeString += paintPanel.points.indexOf(backupSingleton)+" ";
                if (backtrackSingleton != backupSingleton.aLink) { // proceed with aLink
                    backtrackSingleton = backupSingleton;
                    backupSingleton = backtrackSingleton.aLink;
                } else if (backtrackSingleton != backupSingleton.bLink) {
                    backtrackSingleton = backupSingleton;
                    backupSingleton = backtrackSingleton.bLink;
                }
                edgesVisited++;
            }   
            completeString += "' convex='"+
                    convexBox.isSelected()+"'>\n\t\t\t<Coordinate point='";
            for (int index = 0; index < paintPanel.points.size(); index++) {
                backupSingleton = (Csingleton) paintPanel.points.get(index);
                completeString += ((float)backupSingleton.coordinateX/100)+" "+((float)backupSingleton.coordinateY/100)+" 0, ";
            }
            completeString += "'/>\n\t\t\t</IndexedFaceSet>\n\t\t\t<Appearance>\n\t\t\t\t<Material ";
            if (footerBlock.colorKind.getSelectedIndex() == 0) completeString += "diffuseColor=";
            else if (footerBlock.colorKind.getSelectedIndex() == 1) completeString += "emissiveColor=";
            else completeString += "specularColor=";
            completeString += "'"+((float)footerBlock.red/255)+" "+((float)footerBlock.green/255)+" "+((float)footerBlock.blue/255)+"'/>\n";
            if ((subfooterBlock.textureBox.isSelected()) && (subfooterBlock.texturePath != null)) {
                completeString += "\t\t\t\t<ImageTexture repeatS='"+
                        subfooterBlock.hBox.isSelected()+"' repeatT='"+
                        subfooterBlock.vBox.isSelected()+"' url='"+
                        subfooterBlock.texturePath+"'/>\n";
            }
            completeString += "\t\t\t</Appearance>\n\t\t</Shape>\n\t</Transform>";
            backTrack.appendString(completeString);
            formWindowClosing();
        } else {
            completeString += " USE='"+
                    headerBlock.useName.getSelectedItem()+"'/>\n\t\t</Shape>\n\t</Transform>";
            backTrack.appendString(completeString);
            formWindowClosing();
        }
        backTrack.tagsUpdater.switchUpdate();
    }

    public void resetComponents () {
        headerBlock.resetComponents();
        footerBlock.resetComponents();
        subfooterBlock.resetComponents();
        paintPanel.points.clear();
        
    }

    /* window's event catched */
    public void formWindowClosing() {
        this.setVisible(false);
        backTrack.setEnabled(true);
        backTrack.setFocusable(true);
        backTrack.toFront();
    }
}