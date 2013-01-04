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
import basics.Cbackgroundpanel;
import basics.Cdatafiles;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Cmaininterface;
import subwindows.Cerrorinterface;
public class Caboutinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JLabel submarineLabel, descriptionLabel, freewrlLabel, developerLabel, versionLabel, releasedLabel;
    private JLabel developerName, versionID, releasedDate, supportLabel;
    private JButton updateCheck;
    /* end */
    public Caboutinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        submarineLabel = new JLabel();
        descriptionLabel = new JLabel();
        freewrlLabel = new JLabel();
        developerLabel = new JLabel();
        versionLabel = new JLabel();
        releasedLabel = new JLabel();
        developerName = new JLabel();
        versionID = new JLabel();
        releasedDate = new JLabel();
        supportLabel = new JLabel();
        updateCheck = new JButton();
        submarineLabel.setFont(new java.awt.Font("Marker Felt", 0, 24));
        submarineLabel.setText("Submarine© X3D client edition");
        descriptionLabel.setText("an X3D Editor for Mac, Linux and Windows");
        freewrlLabel.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        freewrlLabel.setText("<html>preview system based on FreeWRL© Application developed by <b>John A. Stewart</b></html>");
        developerLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        developerLabel.setText("Developer");
        versionLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        versionLabel.setText("Version");
        releasedLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        releasedLabel.setText("Released");
        developerName.setText("Andrea Nardinocchi");
        versionID.setText(Cmaininterface.version+"."+Cmaininterface.subVersion+" "+Cmaininterface.status);
        releasedDate.setText("25 May 2011");
        supportLabel.setText("<html>Thanks to <b>Luna Paciucci</b> & <b>Valerio Egidi</b> for graphical support</html>");
        updateCheck.setText("Check for updates");
        updateCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkUpdates();
            }
        });
        JPanel container = new Cbackgroundpanel(new GridBagLayout(), "/arts/aboutBackground.png");
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        positioner.gridwidth = 2;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(submarineLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.gridwidth = 4;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(descriptionLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 2;
        positioner.gridwidth = 4;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(freewrlLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(developerLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 3;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(developerName, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 4;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(versionLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 4;
        positioner.gridwidth = 3;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(versionID, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 5;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(releasedLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 5;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(releasedDate, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 6;
        positioner.gridwidth = 4;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.CENTER;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(supportLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 7;
        positioner.gridwidth = 4;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        positioner.insets = new Insets(5, 5, 5, 5);
        container.add(updateCheck, positioner);
        /* end */
    }

    private void checkUpdates() {
        String pageLink = Cdatafiles.updateWebsite+"submarine."+Cmaininterface.version+"."+Cmaininterface.subVersion+"."+Cmaininterface.status+".html";
        int returnValue = Caboutinterface.checkURL(pageLink);
        if (returnValue < 0) {
            /* new version */
            versionID.setText(Cmaininterface.version+"."+Cmaininterface.subVersion+" "+Cmaininterface.status+" (new distro available)");
            versionID.setForeground(Color.red);
        } else {
            /* current */
            versionID.setText(Cmaininterface.version+"."+Cmaininterface.subVersion+" "+Cmaininterface.status+" (current)");
            versionID.setForeground(Color.blue);
        }
    }

    public static int checkURL(String URLink) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = (HttpURLConnection) new URL(URLink).openConnection();
            connection.setRequestMethod("HEAD");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) return 1;
            else if (connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) return -1;
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("UPDATEERR", false);
        }
        return 0;
    }

    public void applyValues () {
       /* do nothing */
    }
}
