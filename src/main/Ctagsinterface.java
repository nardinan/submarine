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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
public class Ctagsinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private Czebralist mainView;
    public DefaultListModel mainModel;
    /* end */
    /* semaphores */
    final public Object sourceCoordinatesLocker = new Object();
    final public Object mainModelLocker = new Object();
    /* end */
    /* utilities */
    public ArrayList<String> sourceCoordinates = new ArrayList<String>(); // tags' coordinate into the source code
    /* end */
    public Ctagsinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        mainView = new Czebralist();
        mainModel = new DefaultListModel();
        mainView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    selectingComponents();
                }
            }
        });
        mainView.setModel(mainModel);
        JScrollPane container = new JScrollPane(mainView);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.setMaximumSize(new Dimension(200, 3500));
        container.setMinimumSize(new Dimension(200, 250));
        container.setPreferredSize(new Dimension(200, 250));
        /* blitting */
        this.setLayout(new BorderLayout());
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Nodes"));
        this.add(container);
        /* end */
    }

    public void selectingComponents () {
        Ctabinterface singleSource = null;
        synchronized (backTrack.tabbedPaneLocker) {
            singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
        }
        if (singleSource != null) {
            int selection = mainView.getSelectedIndex();
            String singleton, elements[];
            if (selection >= 0) {
                singleton = (String) sourceCoordinates.get(selection);
                elements = singleton.split(":");
                singleSource.mainView.setSelectionStart(Integer.valueOf(elements[0]));
                singleSource.mainView.setSelectionEnd(Integer.valueOf(elements[1]));
            }
        }
    }
}
