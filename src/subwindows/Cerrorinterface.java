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
import basics.Cerrormessages;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Cerrorinterface extends Cbasewindow {
    /* SWING vars */
    private JLabel logoImage, errorMessage, informationMessage;
    private JButton killButton;
    private boolean killMe = false;
    /* end */
    public Cerrorinterface (String errorMessage, String informationMessage, boolean killMe) {
        super(430, 170, "Error from Submarine ...", false, false, true);
        /* doing everything here */
        this.killMe = killMe;
        this.initializeComponent();
        this.setAction(errorMessage, informationMessage);
        this.setVisible(true);
        /* end */
    }
    
    public Cerrorinterface (String errorCode, boolean killMe) {
        super(430, 170, "Error from Submarine ...", false, false, true);
        /* doing everything here */
        this.killMe = killMe;
        this.initializeComponent();
        this.setAction(Cerrormessages.getTitle(errorCode), Cerrormessages.getMessage(errorCode));
        this.setVisible(true);
        /* end */
    }

    private void initializeComponent () {
        GridBagConstraints positioner = null;
        logoImage = new JLabel();
        errorMessage = new JLabel();
        informationMessage = new JLabel();
        killButton = new JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        errorMessage.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        logoImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/errorLogo.png"))); // NOI18N
        if (killMe) killButton.setText("Kill application");
        else killButton.setText("Oh! Ok, thanks");
        killButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (killMe) System.exit(1);
                else closeWindow();
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
        container.add(errorMessage, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 1;
        positioner.gridwidth = 4;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(informationMessage, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 5;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.SOUTHEAST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(killButton, positioner);
        this.pack();
        /* end */
    }

    private void setAction (String errorMessage, String informationMessage) {
        this.errorMessage.setText(errorMessage);
        this.informationMessage.setText(informationMessage);
    }

    public void closeWindow () {
        this.setVisible(false);
    }
}

