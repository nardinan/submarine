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
import basics.Cbasewindow;
import basics.Cdatafiles;
import basics.Cextension;
import basics.Cprintsystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import subwindows.*;
import subwindows.shapes.*;
import threads.Cautosavethread;
import threads.Ctagsswing;
import threads.Ctagsthread;
import tokenizer.Cblock;
import tokenizer.Csyntax;
public class Cmaininterface extends Cbasewindow {
    /* SWING vars */
    private JPanel mainContainer;
    public Cfileinterface filePane;
    public JTabbedPane tabbedPane;
    public Ctagsinterface tagsPane;
    public Cmenuinterface menuPane;
    public Cremoteinterface remotePane;
    /* end */
    /* SWING external windows */
    public Cconsoleinterface consolePane;
    public Ccloseinterface closePane;
    public Cdeleteinterface deletePane;
    public Cinformationsinterface infosPane;
    public Cconfigurationinterface configurationInterface;
    public Cpreviewinterface previewInterface;
    public Cboxinterface boxInterface;
    public Csphereinterface sphereInterface;
    public Cconeinterface coneInterface;
    public Ccylinderinterface cylinderInterface;
    public Cpolyinterface polyInterface;
    public Cchatinterface chatInterface;
    /* end */
    /* semaphores */
    final public Object tabbedPaneLocker = new Object();
    /* end */
    /* threads */
    public Ctagsthread tagsUpdater = new Ctagsthread(this);
    public Cautosavethread autosaveUpdater = new Cautosavethread(this);
    /* end */
    /* utils */
    static public String version = "2";
    static public String subVersion = "2";
    static public String status = "beta";
    /* remote project's utilities */
    static public String ipAddress = "127.0.0.1";
    static public String port = "5090";
    static public String serial = null;
    static public String username = null;
    static public String password = null;
    /* end */
    public Csyntax X3Dsyntaxizer = new Csyntax(this);
    public Cmaininterface currentTrack = this;
    /* end */
    public Cmaininterface () {
        super("Submarine X3D enterprise ["+version+"."+subVersion+" "+status+"]");
        /* loading X3D language */
        X3Dsyntaxizer.loadSyntax(Cdatafiles.syntax);
        X3Dsyntaxizer.saveSyntax(Cdatafiles.syntax);
        /* end */
        this.setMaximumSize(new Dimension(5000, 5000));
        this.setMinimumSize(new Dimension(1024, 768));
        tabbedPane = new JTabbedPane();
        tagsPane = new Ctagsinterface(this);
        menuPane = new Cmenuinterface(this);
        filePane = new Cfileinterface(this);
        //remotePane = new Cremoteinterface(this);
        consolePane = new Cconsoleinterface(this);
        closePane = new Ccloseinterface(this);
        deletePane = new Cdeleteinterface(this);
        infosPane = new Cinformationsinterface(this);
        configurationInterface = new Cconfigurationinterface(this);
        previewInterface = new Cpreviewinterface(this);
        boxInterface = new Cboxinterface(this);
        sphereInterface = new Csphereinterface(this);
        coneInterface = new Cconeinterface(this);
        cylinderInterface = new Ccylinderinterface(this);
        polyInterface = new Cpolyinterface(this);
        //chatInterface = new Cchatinterface(this);
        tagsPane.initializeComponent();
        menuPane.initializeComponent();
        filePane.initializeComponent();
        //remotePane.initializeComponent();
        consolePane.initializeComponent();
        closePane.initializeComponent();
        deletePane.initializeComponent();
        infosPane.initializeComponent();
        configurationInterface.initializeComponent();
        previewInterface.initializeComponent();
        boxInterface.initializeComponent();
        sphereInterface.initializeComponent();
        coneInterface.initializeComponent();
        cylinderInterface.initializeComponent();
        polyInterface.initializeComponent();
        //chatInterface.initializeComponent();
    }

    public void initializeComponent () {
        GridBagConstraints positioner = null;
        mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        /* blitting */
        this.add(mainContainer);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 0;
        positioner.gridwidth = 3; // this component takes 3 cols
        positioner.anchor = GridBagConstraints.WEST;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        mainContainer.add(menuPane, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 1;
        positioner.gridheight = 2;
        positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.VERTICAL;
        mainContainer.add(filePane, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 1;
        positioner.gridy = 1;
        positioner.gridheight = 2; // this component takes 2 rows
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        mainContainer.add(tabbedPane, positioner);
        positioner = new GridBagConstraints();
        positioner.gridx = 2;
        positioner.gridy = 1;
        positioner.gridheight = 2;
        positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.VERTICAL;
        mainContainer.add(tagsPane, positioner);
        /*
         * not yet implemented
         */
        /* 
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 2;
        positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.VERTICAL;
        mainContainer.add(remotePane, positioner);
        */
        positioner = new GridBagConstraints();
        positioner.gridx = 0;
        positioner.gridy = 3;
        positioner.gridwidth = 3; // this component takes 3 cols
        positioner.anchor = GridBagConstraints.WEST;
        positioner.weightx = 1.0;
        positioner.fill = GridBagConstraints.HORIZONTAL;
        mainContainer.add(infosPane, positioner);
        this.pack();
        /* end */
        consolePane.appendText("Program started;", false);
    }
    /* initializeMenu create a list of drop down menu;
     * just call this function once as like the initializeComponent.
     */
    public void initializeMenu () {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu previewMenu = new JMenu("Preview");
        JMenu shapeMenu = new JMenu("Shapes");
        /* create a list of actions for fileMenu */
        JMenuItem newAction = new JMenuItem("New", new javax.swing.ImageIcon(getClass().getResource("/arts/addTab_small.png")));
        newAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        newAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                initializeTab(null);
            }
        });
        fileMenu.add(newAction);
        JMenuItem openAction = new JMenuItem("Open", new javax.swing.ImageIcon(getClass().getResource("/arts/load_small.png")));
        openAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        openAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loadFile();
            }
        });
        fileMenu.add(openAction);
        JMenuItem closeAction = new JMenuItem("Close", new javax.swing.ImageIcon(getClass().getResource("/arts/delTab_small.png")));
        closeAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        closeAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    if (singleSource.documentChanged) {
                        if (!closePane.isVisible()) {
                            closePane.setAction("close this Tab", false);
                            closePane.setVisible(true);
                        }
                   } else quitSingleton(true);
                }
            }
        });
        fileMenu.add(closeAction);
        JMenuItem saveAsAction = new JMenuItem("Save As ..", new javax.swing.ImageIcon(getClass().getResource("/arts/saveAs_small.png")));
        saveAsAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    // You have not to check if the source is changed!
                    saveFile(true);
                }
            }
        });
        fileMenu.add(saveAsAction);
        JMenuItem saveAction = new JMenuItem("Save", new javax.swing.ImageIcon(getClass().getResource("/arts/save_small.png")));
        saveAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        saveAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    if (singleSource.documentChanged)
                        saveFile(false);
            }
        });
        fileMenu.add(saveAction);
        JMenuItem saveAllAction = new JMenuItem("Save All", new javax.swing.ImageIcon(getClass().getResource("/arts/saveAll_small.png")));
        saveAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK));
        saveAllAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                Component[] containers = null;
                boolean needSave = false;
                synchronized (tabbedPaneLocker) {
                    containers = tabbedPane.getComponents();
                }
                for (int index = 0; index < containers.length; index++) {
                    singleSource = (Ctabinterface) containers[index]; // obtain selected source
                    if (singleSource.documentChanged)
                        needSave = true;
                }
                if (needSave)
                    saveAllFiles();
            }
        });
        fileMenu.add(saveAllAction);
        fileMenu.addSeparator();
        JMenuItem configurationAction = new JMenuItem("Configuration panel");
        configurationAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                configurationInterface.setVisible(true);
                setEnabled(false);
                setFocusable(false);
            }
        });
        fileMenu.add(configurationAction);
        fileMenu.addSeparator();
        JMenuItem printAction = new JMenuItem("Print ...");
        saveAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        printAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    Cprintsystem printer = new Cprintsystem(currentTrack);
                    printer.print();
                }
            }
        });
        fileMenu.add(printAction);
        fileMenu.addSeparator();
        JMenuItem quitAction = new JMenuItem("Quit", new javax.swing.ImageIcon(getClass().getResource("/arts/delete_small.png")));
        quitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK + Event.SHIFT_MASK));
        quitAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                Component[] containers = null;
                boolean needSave = false;
                synchronized (tabbedPaneLocker) {
                    containers = tabbedPane.getComponents();
                }
                for (int index = 0; index < containers.length; index++) {
                    singleSource = (Ctabinterface) containers[index]; // obtain selected source
                    if (singleSource.documentChanged) needSave = true;
                }
                if (needSave) {
                    if (!closePane.isVisible()) {
                        closePane.setAction("close Submarine", true);
                        closePane.setVisible(true);
                    }
                } else quitApplication(true);
            }
        });
        fileMenu.add(quitAction);
        menuBar.add(fileMenu);
        /* end */
        /* create a list of actions for edit menu */
        JMenuItem undoAction = new JMenuItem("Undo");
        undoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
        undoAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    singleSource.mainView.undoLastAction();
            }
        });
        editMenu.add(undoAction);
        JMenuItem redoAction = new JMenuItem("Undo");
        redoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
        redoAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    singleSource.mainView.redoLastAction();
            }
        });
        editMenu.add(redoAction);
        editMenu.addSeparator();
        JMenuItem cutAction = new JMenuItem("Cut");
        cutAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    singleSource.mainView.cut();
            }
        });
        editMenu.add(cutAction);
        JMenuItem copyAction = new JMenuItem("Copy");
        copyAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    singleSource.mainView.copy();
            }
        });
        editMenu.add(copyAction);
        JMenuItem pasteAction = new JMenuItem("Paste");
        pasteAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    singleSource.mainView.paste();
            }
        });
        editMenu.add(pasteAction);
        menuBar.add(editMenu);
        /* end */
        /* create a list of actions for preview menu */
        JMenuItem previewAction = new JMenuItem("Preview", new javax.swing.ImageIcon(getClass().getResource("/arts/preview_small.png")));
        previewAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
        previewAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    saveFile(false);
                    previewFile(null);
                }
            }
        });
        previewMenu.add(previewAction);
        JMenuItem customPreviewAction = new JMenuItem("Custom preview", new javax.swing.ImageIcon(getClass().getResource("/arts/customPreview_small.png")));
        customPreviewAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK + Event.SHIFT_MASK));
        customPreviewAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    setEnabled(false);
                    setFocusable(false);
                    previewInterface.setVisible(true);
                }
            }
        });
        previewMenu.add(customPreviewAction);
        menuBar.add(previewMenu);
        /* end */
        /* create a list of actions for shapes menu */
        JMenuItem sphereAction = new JMenuItem("add Sphere", new javax.swing.ImageIcon(getClass().getResource("/arts/sphere_small.png")));
        sphereAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    setEnabled(false);
                    setFocusable(false);
                    sphereInterface.setVisible(true);
                    sphereInterface.resetComponents();
                }
            }
        });
        shapeMenu.add(sphereAction);
        JMenuItem cylinderAction = new JMenuItem("add Cylinder", new javax.swing.ImageIcon(getClass().getResource("/arts/cylinder_small.png")));
        cylinderAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    setEnabled(false);
                    setFocusable(false);
                    cylinderInterface.setVisible(true);
                    cylinderInterface.resetComponents();
                }
            }
        });
        shapeMenu.add(cylinderAction);
        JMenuItem coneAction = new JMenuItem("add Cone", new javax.swing.ImageIcon(getClass().getResource("/arts/cone_small.png")));
        coneAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    setEnabled(false);
                    setFocusable(false);
                    coneInterface.setVisible(true);
                    coneInterface.resetComponents();
                }
            }
        });
        shapeMenu.add(coneAction);
        JMenuItem boxAction = new JMenuItem("add Box", new javax.swing.ImageIcon(getClass().getResource("/arts/box_small.png")));
        boxAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    setEnabled(false);
                    setFocusable(false);
                    boxInterface.setVisible(true);
                    boxInterface.resetComponents();
                }
            }
        });
        shapeMenu.add(boxAction);
        JMenuItem polygonAction = new JMenuItem("add Polygon", new javax.swing.ImageIcon(getClass().getResource("/arts/poly_small.png")));
        polygonAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Ctabinterface singleSource = null;
                synchronized (tabbedPaneLocker) {
                    singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    setEnabled(false);
                    setFocusable(false);
                    polyInterface.setVisible(true);
                    polyInterface.resetComponents();
                }
            }
        });
        shapeMenu.add(polygonAction);
        menuBar.add(shapeMenu);
        /* end */
        this.setJMenuBar(menuBar);
    }
    /* initializeTab get sourceFile from JFileChooser;
     * use null for a new empty tab.
     */
    public void initializeTab (File sourcePath) {
        String tabLabel = "new Document";
        Ctabinterface singleSource = new Ctabinterface(this);
        singleSource.initializeComponent(sourcePath);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        synchronized (singleSource.sourceNameLocker) {
            if (singleSource.sourceName != null)
                tabLabel = new File(singleSource.sourceName).getName();
        }
        if (tabLabel.length() > 20) // Truncate extended tab's names
            tabLabel = tabLabel.substring(0, 10)+"..."+tabLabel.substring(tabLabel.length()-10);
        setMinimumSize(new Dimension(800, 600));
        synchronized (tabbedPaneLocker) {
            if (tabbedPane.getTabCount() < 20) {
                tabbedPane.addTab(tabLabel, new javax.swing.ImageIcon(getClass().getResource("/arts/document.png")), singleSource, tabLabel);
                if (sourcePath != null)
                    consolePane.appendText("X3D file "+sourcePath.getName()+" loaded correctly;", false);
            }
        }
        tagsUpdater.switchUpdate();
    }

    private void closeSelectedTab () {
        synchronized (tabbedPaneLocker) {
            Ctabinterface singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
            if (singleSource != null) {
                tabbedPane.remove(tabbedPane.getSelectedIndex());
            }
        }
        tagsUpdater.switchUpdate();
    }

    /* REMEMBER: Kill the application (close it without save anything) only if
     * the user request this action.
     */
    public void quitApplication (Boolean kill) {
        this.formWindowClosing(null);
        if (!kill)
            this.saveAllFiles();
        this.dispose();
        System.exit(0);
    }

    /* REMEMBER: Kill the application (close it without save anything) only if
     * the user request this action.
     */
    public void quitSingleton (Boolean kill) {
        Ctabinterface singleSource = null;
        synchronized (tabbedPaneLocker) {
            singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
        }
        if (singleSource != null) {
        this.formWindowClosing(null);
        if (!kill)
            this.saveFile(false);
        this.closeSelectedTab();
        }
    }

    public void initializeThreads() {
        tagsUpdater.start();
        autosaveUpdater.start();
        //chatInterface.setVisible(true);
    }

    public void saveFile (Boolean withName) {
        this.saveFile(this, withName);
    }

    public void saveFile (Component parent, Boolean withName) {
        final JFileChooser fileSelect = new JFileChooser();
        Ctabinterface singleSource = null;
        BufferedWriter outputFile = null;
        boolean doSavingAction = true;
        String tabLabel = null;
        javax.swing.filechooser.FileFilter superFilter = (javax.swing.filechooser.FileFilter) new Cextension("X3D", "x3d");
        fileSelect.setFileFilter(superFilter);
        synchronized (tabbedPaneLocker) {
            singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
        }
        if (singleSource != null) {
            try {
                synchronized (singleSource.sourceNameLocker) {
                    if (((withName) || (singleSource.sourceName == null)) && (fileSelect.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION))
                        singleSource.sourceName = fileSelect.getSelectedFile().getAbsolutePath()+".x3d";
                    else doSavingAction = false;
                    if ((doSavingAction) || ((singleSource.sourceName != null) && (singleSource.documentChanged))) {
                        outputFile = new BufferedWriter(new FileWriter(singleSource.sourceName));
                        outputFile.write(singleSource.mainView.getDocument().getText(0, singleSource.mainView.getDocument().getLength()));
                        outputFile.newLine();
                        outputFile.close();
                        tabLabel = singleSource.sourceName;
                        if (tabLabel.length() > 10) // Truncate extended tab's names
                            tabLabel = "..."+tabLabel.substring(tabLabel.length()-10);
                        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), tabLabel);
                        singleSource.documentChanged = false;
                        consolePane.appendText("X3D file "+singleSource.sourceName+" saved correctly;", false);
                    }
                }
            } catch (Exception ext) {
                Cerrorinterface bck = new Cerrorinterface("SAVINGERR", false);
            }
        }
    }

    public void saveAllFiles () {
        final JFileChooser fileSelect = new JFileChooser();
        Ctabinterface singleSource = null;
        BufferedWriter outputFile = null;
        boolean doSavingAction = true;
        String tabLabel = null;
        Component[] containers = null;
        javax.swing.filechooser.FileFilter superFilter = (javax.swing.filechooser.FileFilter) new Cextension("X3D", "x3d");
        fileSelect.setFileFilter(superFilter);
        synchronized (tabbedPaneLocker) {
            containers = tabbedPane.getComponents();
        }
        for (int index = 0; index < containers.length; index++) {
            singleSource = (Ctabinterface) containers[index]; // obtain selected source
            doSavingAction = true;
            if (singleSource != null) {
                try {
                    synchronized (singleSource.sourceNameLocker) {
                        if ((singleSource.sourceName == null) && (fileSelect.showSaveDialog(this) == JFileChooser.APPROVE_OPTION))
                            singleSource.sourceName = fileSelect.getSelectedFile().getAbsolutePath()+".x3d";
                        else doSavingAction = false;
                        if ((doSavingAction) || ((singleSource.sourceName != null) && (singleSource.documentChanged))) {
                            outputFile = new BufferedWriter(new FileWriter(singleSource.sourceName));
                            outputFile.write(singleSource.mainView.getDocument().getText(0, singleSource.mainView.getDocument().getLength()));
                            outputFile.newLine();
                            outputFile.close();
                            tabLabel = singleSource.sourceName;
                            if (tabLabel.length() > 10) // Truncate extended tab's names
                                tabLabel = "..."+tabLabel.substring(tabLabel.length()-10);
                            tabbedPane.setTitleAt(tabbedPane.indexOfComponent(singleSource), tabLabel);
                            singleSource.documentChanged = false;
                            consolePane.appendText("X3D file "+singleSource.sourceName+" saved correctly;", false);
                        }
                    }
                } catch (Exception ext) {
                    Cerrorinterface bck = new Cerrorinterface("SAVINGERR", false);
                }
            }
        }
    }
    
    public void loadFile () {
        final JFileChooser fileSelect = new JFileChooser();
        javax.swing.filechooser.FileFilter superFilter = (javax.swing.filechooser.FileFilter) new Cextension("X3D", "x3d");
        fileSelect.setFileFilter((javax.swing.filechooser.FileFilter) superFilter);
        if (fileSelect.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.initializeTab(fileSelect.getSelectedFile());
        }
    }

    public void previewFile (String filePath) {
        Ctabinterface singleSource = null;
        String fileName = null, callableFunction = null;
        if (filePath == null) {
            synchronized (tabbedPaneLocker) {
                singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
            }
            if (singleSource != null) {
                synchronized (singleSource.sourceNameLocker) {
                    if (singleSource.sourceName != null) {
                        fileName = singleSource.sourceName;
                    }
                }
            }
        } else fileName = filePath;
        if (fileName != null) {
            consolePane.appendText("("+System.getProperty("os.name")+" platform detected)", false);
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                fileName = fileName.replace(" ", "\\ ");
                callableFunction = "open -a "+configurationInterface.previewInterface.completePath.replace(" ", "\\ ")+" "+fileName;
            } else callableFunction = "\""+configurationInterface.previewInterface.completePath+"\" \""+fileName+"\"";
            try {
                Process newProcess = Runtime.getRuntime().exec(callableFunction);
                BufferedReader outputSet = new BufferedReader(new InputStreamReader(newProcess.getInputStream()));
                BufferedReader errorSet = new BufferedReader(new InputStreamReader(newProcess.getErrorStream()));
                String singleton;
                int messages = 0;
                while ((singleton = outputSet.readLine()) != null) {
                    consolePane.appendText(singleton, false);
                    messages++;
                }
                while ((singleton = errorSet.readLine()) != null) {
                    consolePane.appendText(singleton, true);
                    messages++;
                }
                if (messages <= 0) 
                    consolePane.appendText("no messages from your Browser", false);
                outputSet.close();
                errorSet.close();
            } catch (Exception ext) {
                Cerrorinterface bck = new Cerrorinterface("PREVIEERR", false);
            }
        }
    }

    /* Called by Ctagsthread.
     * Call Ctagsswing in safe-thread mode; update right (tags interface) component.
     */
    public void modifyBlocks (ArrayList<Cblock> blockList) {
        previewInterface.loadTags(blockList);
        boxInterface.headerBlock.loadTags(blockList, "box");
        sphereInterface.headerBlock.loadTags(blockList, "sphere");
        coneInterface.headerBlock.loadTags(blockList, "cone");
        cylinderInterface.headerBlock.loadTags(blockList, "cylinder");
        polyInterface.headerBlock.loadTags(blockList, "indexedfaceset");
        Ctagsswing updateThread = new Ctagsswing(this, blockList);
        SwingUtilities.invokeLater(updateThread);
    }
    
    public void appendString (String text) {
        Ctabinterface singleSource = null;
        synchronized (tabbedPaneLocker) {
            singleSource = (Ctabinterface) tabbedPane.getSelectedComponent();
        }
        if (singleSource != null) {
            singleSource.documentChanged = true;
            try {
                singleSource.mainView.getDocument().insertString(singleSource.mainView.getCaretPosition(), text, null);
            } catch (Exception exc) {
                Cerrorinterface bck = new Cerrorinterface("DOCUMEERR", true);
            }
        }
    }
    
    /* window's event catched */
    public void formWindowClosing(java.awt.event.WindowEvent evt) {
        if (evt != null) { // that's meaning is a system call
            Ctabinterface singleSource = null;
            Component[] containers = null;
            boolean needSave = false;
            synchronized (tabbedPaneLocker) {
                containers = tabbedPane.getComponents();
            }
            for (int index = 0; index < containers.length; index++) {
                singleSource = (Ctabinterface) containers[index]; // obtain selected source
                if (singleSource.documentChanged) needSave = true;
            }
            if (needSave) {
                if (!closePane.isVisible()) {
                    closePane.setAction("close Submarine", true);
                    closePane.setVisible(true);
                }
            } else quitApplication(true);
        } else { // warning window call this method
            closePane.setVisible(false);
            deletePane.setVisible(false);
        }
    }
}
