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
import java.awt.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import main.Cmaininterface;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import subwindows.Cerrorinterface;
public class Cautosaveinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JLabel destinationMessage;
    private JSlider minutesSetter;
    private JCheckBox autosaveEnabler;
    public JCheckBox textureEnabler;
    public RSyntaxTextArea mainView;
    private RTextScrollPane subContainer;
    /* end */
    /* syntax lexer and autocompletition */
    private AutoCompletion autoCompletion;
    /* end */
    public Cautosaveinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        destinationMessage = new JLabel();
        minutesSetter = new JSlider();
        autosaveEnabler = new JCheckBox();
        textureEnabler = new JCheckBox();
        autosaveEnabler.setText("Autosave");
        autosaveEnabler.setSelected(false);
        autosaveEnabler.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                minutesSetter.setEnabled(autosaveEnabler.isSelected());
            }
        });
        textureEnabler.setText("Local textures");
        textureEnabler.setSelected(true);
        minutesSetter.setMinimum(10);
        minutesSetter.setMaximum(60);
        minutesSetter.setValue(10);
        minutesSetter.setEnabled(false);
        minutesSetter.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                destinationMessage.setText("Autosave every "+minutesSetter.getValue()+" minutes");
            }
        });
        destinationMessage.setText("Autosave every "+minutesSetter.getValue()+" minutes");
        destinationMessage.setEnabled(false);
        mainView = new RSyntaxTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 0, 0, 100));
                g.drawLine(640, 0, 640, this.getHeight());
            }
        };
        mainView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        /* install the code's auto completion module */
        autoCompletion = new AutoCompletion(backTrack.X3Dsyntaxizer.createCompletion());
        autoCompletion.setShowDescWindow(true);
        autoCompletion.setAutoActivationEnabled(true);
        autoCompletion.setAutoCompleteEnabled(true);
        autoCompletion.install(mainView);
        /* end */
        subContainer = new RTextScrollPane(mainView);
        subContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        subContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Header"));
        subPanel.add(subContainer);
        this.loadInformations();
        JPanel container = new JPanel(new GridBagLayout());
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
        positioner.anchor = GridBagConstraints.WEST;
        container.add(autosaveEnabler, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(destinationMessage, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        positioner.anchor = GridBagConstraints.CENTER;
        container.add(minutesSetter, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 2;
        positioner.anchor = GridBagConstraints.WEST;
        container.add(textureEnabler, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 3;
        positioner.gridwidth = 2;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        container.add(subPanel, positioner);
        /* end */
    }

    public void saveInformations () {
        try {
            BufferedWriter outputFile = null;
            String writerFields = null;
            writerFields = ((autosaveEnabler.isSelected())?"T:":"F:")+String.valueOf(minutesSetter.getValue())+"\n"+mainView.getDocument().getText(0, mainView.getDocument().getLength());
            try {
                outputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Cdatafiles.autosave)));
                outputFile.write(writerFields);
                outputFile.newLine();
                outputFile.close();
            } catch (Exception exc) {
                Cerrorinterface bck = new Cerrorinterface("CONFIGERR", true);
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(Cautosaveinterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadInformations () {
        BufferedReader inputStream = null;
        String readerLine = null, completeText = "";
        String[] readerFields;
        try {
            inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(Cdatafiles.autosave)));
            if ((readerLine = inputStream.readLine()) != null) {
                if (readerLine.length() > 0) {
                    readerFields = readerLine.split(":");
                    if (readerFields[0].equalsIgnoreCase("T")) autosaveEnabler.setSelected(true);
                    else autosaveEnabler.setSelected(false);
                    minutesSetter.setEnabled(autosaveEnabler.isSelected());
                    destinationMessage.setEnabled(autosaveEnabler.isSelected());
                    minutesSetter.setValue(Integer.parseInt(readerFields[1]));
                    destinationMessage.setText("Autosave every "+minutesSetter.getValue()+" minutes");
                }
            }
            /* read the configuration file */
            if ((readerLine = inputStream.readLine()) != null) completeText = readerLine;
            while ((readerLine = inputStream.readLine()) != null)
                completeText += '\n'+readerLine;
            /* end */
            this.appendText(completeText);
            inputStream.close();
        } catch (Exception exc) {
            /* create the configuration file if not exists */
            File configurationFile = new File(Cdatafiles.autosave);
            try {
                if (!configurationFile.exists())
                    configurationFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Cautosaveinterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.saveInformations();
        }
    }
    
    public void appendText (String text) {
        try {
            mainView.getDocument().insertString(mainView.getCaretPosition(), text, null);
        } catch (Exception ext) {
            Cerrorinterface bck = new Cerrorinterface("DOCUMEERR", true);
        }
    }

    public void applyValues () {
        this.saveInformations();
        backTrack.autosaveUpdater.switchTimer(minutesSetter.getValue());
        backTrack.autosaveUpdater.switchUpdate(autosaveEnabler.isSelected());
    }
}

