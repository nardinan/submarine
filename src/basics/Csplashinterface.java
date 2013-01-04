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
package basics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
public final class Csplashinterface extends Cbasewindow {
    /* SWING vars */
    private JPanel mainContainer;
    private JLabel mainPicture;
    private JProgressBar progress;
    /* end */
    public Csplashinterface () {
        super(526, 280, "You can't see me; wooohhoooo", true, false, true);
        initializeComponent();
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        mainPicture = new JLabel();
        progress = new JProgressBar();
        mainPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/splash.png")));
        progress.setMinimum(0);
        progress.setMaximum(100);
        progress.setValue(0);
        progress.setBorderPainted(false);
        /* blitting */
        mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(mainContainer);
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        mainContainer.add(mainPicture, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.weighty = positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        mainContainer.add(progress, positioner);
        this.pack();
        /* end */
    }

    public void setProgressValue (int value) {
        progress.setValue(value);
    }
}
