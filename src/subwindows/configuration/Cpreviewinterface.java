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
import basics.Cdatafiles;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.*;
import javax.swing.*;
import main.Cmaininterface;
import subwindows.Cerrorinterface;
public class Cpreviewinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JLabel applicationLabel, descriptionLabel, parametersLabel;
    private JTextField applicationPath;
    private JButton setApplication, resetApplication;
    /* end */
    /* utilities */
    public String completePath = "./";
    /* end */
    public Cpreviewinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        applicationLabel = new JLabel();
        descriptionLabel = new JLabel();
        parametersLabel = new JLabel();
        applicationPath = new JTextField();
        setApplication = new JButton();
        resetApplication = new JButton();
        applicationLabel.setText("Select a Preview software (default FreeWRL)");
        descriptionLabel.setText("Note that the preview application must be parametrical and callable with syntax:");
        descriptionLabel.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        parametersLabel.setText("<preview application> <filepath/file.x3d>");
        applicationPath.setEditable(false);
        setApplication.setText("Select application");
        setApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectApplication();
            }
        });
        resetApplication.setText("Reset application");
        resetApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetApplication();
            }
        });
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.loadInformations();
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
        positioner.anchor = GridBagConstraints.WEST;
        container.add(applicationLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(applicationPath, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(setApplication, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 2;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(resetApplication, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 3;
        positioner.gridwidth = 2;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(descriptionLabel, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 4;
        positioner.gridwidth = 2;
        positioner.anchor = GridBagConstraints.EAST;
        container.add(parametersLabel, positioner);
        /* end */
    }

    public void saveInformations () {
        BufferedWriter outputFile = null;
        try {
            outputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Cdatafiles.application)));
            outputFile.write(completePath);
            outputFile.newLine();
            outputFile.close();
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("CONFIGERR", true);
        }
    }

    public void loadInformations () {
        BufferedReader inputStream;
        String readerLine;
        File abspath = null;
        try {
            inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(Cdatafiles.application)));
            if (((readerLine = inputStream.readLine()) != null) && (readerLine.length() > 0)) {
                abspath = new File(readerLine);
                completePath = abspath.getAbsolutePath();
                applicationPath.setText(abspath.getName());
            } else {
                if (System.getProperty("os.name").toLowerCase().contains("mac")) abspath = new File(Cdatafiles.defaultMacPreview);
                else if (System.getProperty("os.name").toLowerCase().contains("win")) abspath = new File(Cdatafiles.defaultWinPreview);
                else abspath = new File(Cdatafiles.defaultLinPreview);
                completePath = abspath.getAbsolutePath();
                applicationPath.setText(abspath.getName());
                backTrack.consolePane.appendText("First Execution detected on "+System.getProperty("os.name"), false);
                backTrack.consolePane.appendText("FreeWRL is now your default preview application", false);
                this.saveInformations();
            }
            inputStream.close();
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("CONFIGERR", true);
        }
    }

    private void selectApplication () {
        final JFileChooser fileSelect = new JFileChooser();
        String backupDirectory = null;
        fileSelect.setCurrentDirectory(new java.io.File("."));
        fileSelect.setDialogTitle("Select an application");
        fileSelect.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileSelect.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            completePath = fileSelect.getSelectedFile().getAbsolutePath();
            applicationPath.setText(fileSelect.getSelectedFile().getName());
        }
    }

    private void resetApplication () {
        File abspath = null;
        if (System.getProperty("os.name").toLowerCase().contains("mac")) abspath = new File(Cdatafiles.defaultMacPreview);
        else if (System.getProperty("os.name").toLowerCase().contains("win")) abspath = new File(Cdatafiles.defaultWinPreview);
        else abspath = new File(Cdatafiles.defaultLinPreview);
        completePath = abspath.getAbsolutePath();
        applicationPath.setText(abspath.getName());
    }

    public void applyValues () {
        this.saveInformations();
    }
}

