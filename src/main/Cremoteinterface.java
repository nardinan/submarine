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
import basics.Czebralist;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
public class Cremoteinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private Czebralist mainView;
    public DefaultListModel mainModel;
    private JButton actionButton = null;
    private JScrollPane container;
    /* end */
    /* semaphores */
    final public Object sourceCoordinatesLocker = new Object();
    final public Object mainModelLocker = new Object();
    /* end */
    /* utilities */
    public boolean connected = false;
    /* end */
    public Cremoteinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }
    
    public void initializeComponent () {
        GridBagConstraints positioner = null;
        actionButton = new JButton();
        actionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectingComponents();
            }
        });
        mainView = new Czebralist();
        mainModel = new DefaultListModel();
        mainView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                }
            }
        });
        mainView.setModel(mainModel);
        container = new JScrollPane(mainView);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.setMaximumSize(new Dimension(200, 3500));
        container.setMinimumSize(new Dimension(200, 250));
        container.setPreferredSize(new Dimension(200, 250));
        this.setLayout(new GridBagLayout());
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Remote"));
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 0;
        positioner.gridwidth = 2;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.VERTICAL;
        this.add(container, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.gridwidth = 2;
        positioner.weightx = positioner.weighty = 0.0;
        positioner.fill = GridBagConstraints.BOTH;
        this.add(actionButton, positioner);
        if (connected)
            initializeConnected();
        else
            initializeDisconnected();
    }

    public void initializeDisconnected () {
        connected = false;
        mainView.drawOverside = true;
        mainView.repaint();
        actionButton.setText("connect ...");
        this.revalidate();
    }
    
    public void initializeConnected () {
        connected = true;
        mainView.drawOverside = false;
        mainView.repaint();
        actionButton.setText("disconnect ...");
        this.revalidate();
    }

    public void selectingComponents () {
        if (!connected) {
            
        }
        if (connected)
            initializeDisconnected();
        else
            initializeConnected();
    }
}
