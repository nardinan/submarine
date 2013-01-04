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
import basics.Cnumericdocument;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import main.Cmaininterface;
import subwindows.Cerrorinterface;
import subwindows.shapes.elements.Cfooter;
import subwindows.shapes.elements.Cheader;
import subwindows.shapes.elements.Csubfooter;
public class Cboxinterface extends Cbasewindow {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public Cheader headerBlock;
    private Cfooter footerBlock;
    private Csubfooter subfooterBlock;
    private Cnumericdocument xSize, ySize, zSize;
    private JLabel xPicture, yPicture, zPicture;
    private JCheckBox solidBox;
    private JButton submitButton, cancelButton;
    /* end */
    public Cboxinterface (Cmaininterface backTrack) {
        super(350, 550, "add a Box", false, false, true);
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        headerBlock = new Cheader(backTrack);
        footerBlock = new Cfooter(backTrack);
        subfooterBlock = new Csubfooter(backTrack);
        xSize = new Cnumericdocument();
        ySize = new Cnumericdocument();
        zSize = new Cnumericdocument();
        xPicture = new JLabel();
        yPicture = new JLabel();
        zPicture = new JLabel();
        solidBox = new JCheckBox();
        submitButton = new JButton();
        cancelButton = new JButton();
        headerBlock.initializeComponent();
        footerBlock.initializeComponent();
        subfooterBlock.initializeComponent();
        xSize.setBorder(javax.swing.BorderFactory.createTitledBorder("X"));
        ySize.setBorder(javax.swing.BorderFactory.createTitledBorder("Y"));
        zSize.setBorder(javax.swing.BorderFactory.createTitledBorder("Z"));
        xPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/xbox.png")));
        yPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/ybox.png")));
        zPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/zbox.png")));
        solidBox.setText("solid");
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
        JScrollPane xScroll = new JScrollPane(xSize);
        xScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        xScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        JScrollPane yScroll = new JScrollPane(ySize);
        yScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        yScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        JScrollPane zScroll = new JScrollPane(zSize);
        zScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        zScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel infoContainer = new JPanel(new GridBagLayout());
        infoContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.CENTER;
        infoContainer.add(xScroll, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 0;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.CENTER;
        infoContainer.add(yScroll, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 0;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.CENTER;
        infoContainer.add(zScroll, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.anchor = GridBagConstraints.CENTER;
        infoContainer.add(xPicture, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.anchor = GridBagConstraints.CENTER;
        infoContainer.add(yPicture, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 1;
        positioner.anchor = GridBagConstraints.CENTER;
        infoContainer.add(zPicture, positioner);
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
        positioner.anchor = GridBagConstraints.CENTER;
        container.add(infoContainer, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 2;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(solidBox, positioner);
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
        String completeString = "<Transform translation='0.0 0.0 0.0' rotation='0.0 0.0 0.0 0.0'>\n\t\t<Shape>\n\t\t\t<Box";
        if (headerBlock.nameSelect.isSelected()) {
            if ((!headerBlock.objectName.getText().equals("")) && (!xSize.getText().equals("")) && (!ySize.getText().equals("")) && (!zSize.getText().equals(""))) {
                completeString += " DEF='"+headerBlock.objectName.getText()+"' size='"+
                        xSize.getText()+" "+
                        ySize.getText()+" "+
                        zSize.getText()+"' solid='"+
                        solidBox.isSelected()+"'/>\n\t\t\t<Appearance>\n\t\t\t\t<Material ";
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
                Cerrorinterface bck = new Cerrorinterface("EMPTIEERR", false);
            }
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
        xSize.setText(null);
        ySize.setText(null);
        zSize.setText(null);
    }

    /* window's event catched */
    public void formWindowClosing() {
        this.setVisible(false);
        backTrack.setEnabled(true);
        backTrack.setFocusable(true);
        backTrack.toFront();
    }
}
