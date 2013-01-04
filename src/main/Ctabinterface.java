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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;
import subwindows.Cerrorinterface;
import tokenizer.Cdocumentreader;
import tokenizer.Ctokenizer;
public class Ctabinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    public RSyntaxTextArea mainView;
    public RTextScrollPane container;
    /* end */
    /* utilities */
    public String sourceName = null;
    public Boolean documentChanged = false;
    /* end */
    /* semaphores */
    final public Object sourceNameLocker = new Object();
    /* end */
    /* syntax lexer and autocompletition */
    public Cdocumentreader reader;
    public Ctokenizer syntaxLexer;
    private AutoCompletion autoCompletion;
    /* end */
    public Ctabinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    /* We didn't need to synchronize; We can't find a thread that use this informations
     * during the execution of this method.
     */
    public void initializeLexer () {
        reader = new Cdocumentreader((AbstractDocument)mainView.getDocument());
        syntaxLexer = new Ctokenizer(reader);
    }

    /* We didn't need to synchronize; We can't find a thread that use this informations
     * during the execution of this method.
     */
    public void initializeComponent (File sourcePath) {
        GridBagConstraints positioner = null;
        BufferedReader inputFile;
        String readerLine, completeText = "";
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
        /* set style */
        mainView.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent ke) {
                backTrack.tagsUpdater.switchUpdate();
                if (!documentChanged) {
                    documentChanged = true;
                    backTrack.tabbedPane.setTitleAt(backTrack.tabbedPane.getSelectedIndex(), backTrack.tabbedPane.getTitleAt(backTrack.tabbedPane.getSelectedIndex())+" [*]");
                }
                
            }
            public void keyPressed(KeyEvent ke) {
                // do nothing
            }
            public void keyReleased(KeyEvent ke) {
                // do nothing
            }
        });
        container = new RTextScrollPane(mainView);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        if (sourcePath != null) {
            sourceName = sourcePath.getAbsolutePath();
            try {
                inputFile = new BufferedReader(new FileReader(sourceName));
                /* read the file */
                if ((readerLine = inputFile.readLine()) != null) completeText = readerLine;
                while ((readerLine = inputFile.readLine()) != null)
                    completeText += '\n'+readerLine;
                /* end */
                inputFile.close();
                mainView.setText(completeText);
                mainView.setCaretPosition(0);
            } catch (Exception ext) {
                // TODO: show error window; Unable to read/load/find file.
            } finally {
                backTrack.tagsUpdater.switchUpdate();
            }
        } else {
            try {
                this.appendText(backTrack.configurationInterface.autosaveInterface.mainView.getDocument().getText(0, backTrack.configurationInterface.autosaveInterface.mainView.getDocument().getLength()));
            } catch (BadLocationException ex) {
                Logger.getLogger(Ctabinterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.initializeLexer();
        backTrack.X3Dsyntaxizer.updateStyle(this);
        backTrack.tagsUpdater.switchUpdate();
        /* blitting */
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        this.add(container, positioner);
        /* end */
    }
    
    public void setStyle (String singleElement) {
        String backupValues[] = singleElement.split(":"), colorValues[], parameters[];
        if (backupValues.length == 2) {
            parameters = backupValues[1].split("\\|");
            if (parameters.length == 6) { // font kind|font size|is bold?|is italic?|background|foreground
                int elementKind = backTrack.X3Dsyntaxizer.getCategory(backupValues[0]), fontKind = Font.PLAIN;
                if ((parameters[2].equalsIgnoreCase("true")) && (parameters[3].equalsIgnoreCase("true"))) fontKind = Font.BOLD + Font.ITALIC;
                else if (parameters[2].equalsIgnoreCase("true")) fontKind = Font.BOLD;
                else if (parameters[3].equalsIgnoreCase("true")) fontKind = Font.ITALIC;
                Font backupFont = new Font(parameters[0], fontKind, Integer.valueOf(parameters[1]));
                if (elementKind < 0) { // default syntax
                    this.setStyle(backupFont, parameters[4], parameters[5]);
                } else {
                    SyntaxScheme backupSyntax = mainView.getSyntaxScheme();
                    colorValues = parameters[4].split(",");
                    backupSyntax.styles[elementKind].background = new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2]));
                    colorValues = parameters[5].split(",");
                    backupSyntax.styles[elementKind].foreground = new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2]));
                    backupSyntax.styles[elementKind].font = backupFont;
                }
            }
        }
    }

    private void setStyle (Font font, String backgroundColor, String foregroundColor) {
        SyntaxScheme backupSyntax = mainView.getDefaultSyntaxScheme();
        String colorValues[];
        backupSyntax = (SyntaxScheme) backupSyntax.clone();
        for (int index = 0; index < backupSyntax.styles.length; index++) {
            if (backupSyntax.styles[index] != null) {
                backupSyntax.styles[index].font = font;
                colorValues = backgroundColor.split(",");
                backupSyntax.styles[index].background = new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2]));
                colorValues = foregroundColor.split(",");
                backupSyntax.styles[index].foreground = new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2]));
            }
        }
        mainView.setSyntaxScheme(backupSyntax);
        mainView.setFont(font);
        
    }

    public void appendText (String text) {
        try {
            mainView.getDocument().insertString(mainView.getCaretPosition(), text, null);
        } catch (Exception ext) {
            Cerrorinterface bck = new Cerrorinterface("DOCUMEERR", true);
        }
    }

    public void setEditable (Boolean editable) {
        mainView.setEditable(editable);
    }
}
