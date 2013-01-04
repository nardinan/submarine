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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.*;
import main.Cmaininterface;
import main.Ctabinterface;
import subwindows.Cerrorinterface;
public class Csubfooter extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public JButton textureSelect;
    public JLabel textureImage;
    public JCheckBox textureBox, hBox, vBox;
    /* end */
    /* utils */
    public String texturePath = null;
    /* end */
    public Csubfooter (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        textureSelect = new JButton();
        textureImage = new JLabel();
        textureBox = new JCheckBox();
        hBox = new JCheckBox();
        vBox = new JCheckBox();
        textureSelect.setText("pick texture");
        textureSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTexture();
            }
        });
        textureSelect.setEnabled(false);
        JScrollPane container = new JScrollPane(textureImage);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        container.setMaximumSize(new Dimension(150, 150));
        container.setMinimumSize(new Dimension(150, 150));
        container.setPreferredSize(new Dimension(150, 150));
        textureBox.setText("apply a texture");
        textureBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textureSelect.setEnabled(textureBox.isSelected());
                hBox.setEnabled(textureBox.isSelected());
                vBox.setEnabled(textureBox.isSelected());
            }
        });
        hBox.setText("repeat horizontally");
        hBox.setEnabled(false);
        vBox.setText("repeat vertically");
        vBox.setEnabled(false);
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.gridwidth = 2;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        this.add(textureBox, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.gridheight = 3;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(container, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.NORTH;
        positioner.insets = new Insets(5, 5, 5, 5);
        this.add(textureSelect, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 2;
        positioner.gridwidth = 2;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.SOUTH;
        this.add(hBox, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 3;
        positioner.gridwidth = 2;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.NORTH;
        this.add(vBox, positioner);
        /* end */
    }

    public void loadTexture () {
        Ctabinterface singleSource = null;
        String localDirectory = null, completePath = null;
        File folderPath = null;
        final JFileChooser fileSelect = new JFileChooser();
        if (fileSelect.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            completePath = texturePath = fileSelect.getSelectedFile().getAbsolutePath();
            synchronized (backTrack.tabbedPaneLocker) {
                singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
            }
            if (singleSource != null) {
                if (backTrack.configurationInterface.autosaveInterface.textureEnabler.isSelected()) {
                    synchronized (singleSource.sourceNameLocker) {
                        if (singleSource.sourceName != null) {
                            localDirectory = singleSource.sourceName;
                        }
                    }
                    if (localDirectory == null) {
                        backTrack.saveFile(this, false);
                        synchronized (singleSource.sourceNameLocker) {
                            if (singleSource.sourceName != null) {
                                localDirectory = singleSource.sourceName;
                            }
                        }
                        if (localDirectory == null) {
                            Cerrorinterface bck = new Cerrorinterface("TEXTURERR", false);
                            backTrack.configurationInterface.autosaveInterface.textureEnabler.setSelected(false);
                        }
                    }
                    
                }
                if (localDirectory != null) {
                    folderPath = new File(localDirectory+"Textures");
                    if (!folderPath.exists()) folderPath.mkdir();
                    try {
                        this.copyFiles(texturePath, folderPath.getAbsolutePath() + "/" + (new File(texturePath)).getName());
                    } catch (Exception exc) { }
                    texturePath = "./"+folderPath.getName()+"/"+(new File(texturePath)).getName();
                }
            }
            // load image from completePath
            textureImage.setIcon(new ImageIcon(new ImageIcon(completePath).getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
        }
    }

    public void copyFiles (String source, String destination) throws Exception {
         FileInputStream fis = new FileInputStream(new File(source));
         FileOutputStream fos = new FileOutputStream(new File(destination));
         byte[] buf = new byte[1024];
         int i = 0;
         while((i=fis.read(buf))!=-1) {
             fos.write(buf, 0, i);
         }
         fis.close();
         fos.close();
     }

    public void resetComponents () {
        textureImage.setIcon(null);
        textureBox.setSelected(false);
        hBox.setSelected(false);
        vBox.setSelected(false);
        texturePath = null;
    }
}
