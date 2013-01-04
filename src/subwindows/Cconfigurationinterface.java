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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import main.Cmaininterface;
import subwindows.configuration.Caboutinterface;
import subwindows.configuration.Cautosaveinterface;
import subwindows.configuration.Cpreviewinterface;
import subwindows.configuration.Cstyleinterface;
public class Cconfigurationinterface extends Cbasewindow {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public JTabbedPane tabbedPane;
    private JButton applyButton, cancelButton;
    public Cautosaveinterface autosaveInterface;
    private Cstyleinterface styleInterface;
    public Cpreviewinterface previewInterface;
    private Caboutinterface aboutInterface;
    /* end */
    public Cconfigurationinterface (Cmaininterface backTrack) {
        super(520, 370, "Configuration window", false, false, true);
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        applyButton = new JButton();
        cancelButton = new JButton();
        autosaveInterface = new Cautosaveinterface(backTrack);
        styleInterface = new Cstyleinterface(backTrack);
        previewInterface = new Cpreviewinterface(backTrack);
        aboutInterface = new Caboutinterface(backTrack);
        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autosaveInterface.applyValues();
                styleInterface.applyValues();
                previewInterface.applyValues();
                formWindowClosing();
            }
        });
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formWindowClosing();
            }
        });
        autosaveInterface.initializeComponent();
        styleInterface.initializeComponent();
        previewInterface.initializeComponent();
        aboutInterface.initializeComponent();
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Autosave and Textures", autosaveInterface);
        tabbedPane.addTab("Style", styleInterface);
        tabbedPane.addTab("Preview", previewInterface);
        tabbedPane.addTab("About", aboutInterface);
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        /* blitting */
        this.add(container);
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.gridwidth = 3; // this component take 3 cols
        positioner.weightx = 1.0;
        positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        positioner.anchor = GridBagConstraints.NORTH;
        container.add(tabbedPane, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.anchor = GridBagConstraints.EAST;
        container.add(cancelButton, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 1;
        positioner.anchor = GridBagConstraints.EAST;
        container.add(applyButton, positioner);
        this.pack();
        /* end */
    }

    public void realoadValues () {
        autosaveInterface.loadInformations();
    }

    /* window's event catched */
    public void formWindowClosing() {
        this.setVisible(false);
        backTrack.setEnabled(true);
        backTrack.setFocusable(true);
        backTrack.toFront();
    }
}
