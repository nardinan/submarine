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
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Cmaininterface;
public class Ccloseinterface extends Cbasewindow {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JLabel logoImage, questionMessage, informationMessage;
    private JButton agreeButton, saveButton, cancelButton;
    /* end */
    /* utilities */
    boolean exitApplication = false;
    /* end */
    public Ccloseinterface (Cmaininterface backTrack) {
        super(430, 170, "Closing ...", false, false, true);
        this.backTrack = backTrack;
    }
    
    public void initializeComponent () {
        GridBagConstraints positioner = null;
        logoImage = new JLabel();
        questionMessage = new JLabel();
        informationMessage = new JLabel();
        agreeButton = new JButton();
        saveButton = new JButton();
        cancelButton = new JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        questionMessage.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        questionMessage.setText("Do you want to ... ?");
        informationMessage.setText("<html>Your changes will be lost if you don't save them.</htm>");
        logoImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/warningLogo.png"))); // NOI18N
        agreeButton.setText("Don't save");
        agreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // nothing for now!
            }
        });
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTrack.saveFile(false);
            }
        });
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTrack.formWindowClosing(null);
            }
        });
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        /* blitting */
        this.add(container);
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.gridwidth = 2;
        positioner.gridheight = 4;
        positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.VERTICAL;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(logoImage, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 0;
        positioner.gridwidth = 4;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(questionMessage, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 1;
        positioner.gridwidth = 4;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(informationMessage, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.SOUTH;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(agreeButton, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 3;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.SOUTH;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(saveButton, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 5;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.SOUTHEAST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(cancelButton, positioner);
        this.pack();
        /* end */
    }

    public void setAction (String actionDescription, Boolean exitApplication) {
        ActionListener[] components;
        questionMessage.setText("<html>Do you want to "+actionDescription+"?</html>");
        if (exitApplication) { // closing the application
            saveButton.setText("Save All");
            components = agreeButton.getActionListeners();
            for (int index = 0; index < components.length; index++)
                agreeButton.removeActionListener(components[index]);
            agreeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backTrack.quitApplication(true);
                }
            });
            components = saveButton.getActionListeners();
            for (int index = 0; index < components.length; index++)
                saveButton.removeActionListener(components[index]);
            saveButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backTrack.quitApplication(false);
                }
            });
        } else { // closing a single tab
            saveButton.setText("Save");
            components = agreeButton.getActionListeners();
            for (int index = 0; index < components.length; index++)
                agreeButton.removeActionListener(components[index]);
            agreeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backTrack.quitSingleton(true);
                }
            });
            components = saveButton.getActionListeners();
            for (int index = 0; index < components.length; index++)
                saveButton.removeActionListener(components[index]);
            saveButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backTrack.quitSingleton(false);
                }
            });
        }
    }
}
