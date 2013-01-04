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
import basics.Cprintsystem;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
public class Cmenuinterface extends JPanel {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JToolBar optionTools, graphicTools;
    private JButton addTabButton, delTabButton, saveAsButton, saveAllButton, saveButton, loadButton, consoleButton;
    private JButton configurationButton, printerButton;
    private JButton previewButton, customPreviewButton;
    private JButton sphereButton, cylinderButton, coneButton, boxButton, polyButton, textButton;
    /* end */
    public Cmenuinterface (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void initializeComponent () {
        optionTools = new JToolBar();
        addTabButton = new JButton();
        delTabButton = new JButton();
        saveAsButton = new JButton();
        saveAllButton = new JButton();
        saveButton = new JButton();
        loadButton = new JButton();
        consoleButton = new JButton();
        previewButton = new JButton();
        customPreviewButton = new JButton();
        configurationButton = new JButton();
        printerButton = new JButton();
        graphicTools = new JToolBar();
        sphereButton = new JButton();
        cylinderButton = new JButton();
        coneButton = new JButton();
        boxButton = new JButton();
        polyButton = new JButton();
        textButton = new JButton();
        optionTools.setFloatable(false);
        graphicTools.setFloatable(false);
        addTabButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/addTab.png"))); // NOI18N
        addTabButton.setToolTipText("Create a new empty X3D document.");
        addTabButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addTabButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTrack.initializeTab(null);
            }
        });
        optionTools.add(addTabButton);
        delTabButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/delTab.png"))); // NOI18N
        delTabButton.setToolTipText("Close the selected X3D document.");
        delTabButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delTabButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        delTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    if (singleSource.documentChanged) {
                        if (!backTrack.closePane.isVisible()) {
                            backTrack.closePane.setAction("close this Tab", false);
                            backTrack.closePane.setVisible(true);
                        }
                   } else backTrack.quitSingleton(true);
                }
            }
        });
        optionTools.add(delTabButton);
        saveAsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/saveAs.png"))); // NOI18N
        saveAsButton.setToolTipText("Save the selected file with a custom name.");
        saveAsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveAsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveAsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    // You have not to check if the source is changed!
                    backTrack.saveFile(true);
                }
            }
        });
        optionTools.add(saveAsButton);
        saveAllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/saveAll.png"))); // NOI18N
        saveAllButton.setToolTipText("Save all opened X3D documents in the project.");
        saveAllButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveAllButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                Component[] containers = null;
                boolean needSave = false;
                synchronized (backTrack.tabbedPaneLocker) {
                    containers = backTrack.tabbedPane.getComponents();
                }
                for (int index = 0; index < containers.length; index++) {
                    singleSource = (Ctabinterface) containers[index]; // obtain selected source
                    if (singleSource.documentChanged)
                        needSave = true;
                }
                if (needSave)
                    backTrack.saveAllFiles();
            }
        });
        optionTools.add(saveAllButton);
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/save.png"))); // NOI18N
        saveButton.setToolTipText("re-save the selected X3D document in the project.");
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null)
                    if (singleSource.documentChanged)
                        backTrack.saveFile(false);
            }
        });
        optionTools.add(saveButton);
        loadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/load.png")));
        loadButton.setToolTipText("load a X3D document.");
        loadButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTrack.loadFile();
            }
        });
        optionTools.add(loadButton);
        consoleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/console.png"))); // NOI18N
        consoleButton.setToolTipText("Open the debug's console.");
        consoleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        consoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTrack.consolePane.getJDialog().setVisible(!backTrack.consolePane.getJDialog().isVisible());
                backTrack.consolePane.resetUnreadMessages();
            }
        });
        optionTools.add(consoleButton);
        optionTools.add(new JToolBar.Separator());
        configurationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/configuration.png"))); // NOI18N
        configurationButton.setToolTipText("Open the configuration's panel.");
        configurationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        configurationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        configurationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTrack.configurationInterface.setVisible(true);
                backTrack.setEnabled(false);
                backTrack.setFocusable(false);
            }
        });
        optionTools.add(configurationButton);
        printerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/printer.png"))); // NOI18N
        printerButton.setToolTipText("Print the current document.");
        printerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printerButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    Cprintsystem printer = new Cprintsystem(backTrack);
                    printer.print();
                }

            }
        });
        optionTools.add(printerButton);
        optionTools.add(new JToolBar.Separator());
        previewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/preview.png"))); // NOI18N
        previewButton.setToolTipText("Preview of the current document.");
        previewButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        previewButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        previewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.saveFile(false);
                    backTrack.previewFile(null);
                }
            }
        });
        optionTools.add(previewButton);
        customPreviewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/customPreview.png"))); // NOI18N
        customPreviewButton.setToolTipText("Customizable preview system of the current document.");
        customPreviewButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        customPreviewButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        customPreviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.setEnabled(false);
                    backTrack.setFocusable(false);
                    backTrack.previewInterface.setVisible(true);
                }
            }
        });
        optionTools.add(customPreviewButton);
        sphereButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/sphere.png"))); // NOI18N
        sphereButton.setToolTipText("Add a sphere into the source code.");
        sphereButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sphereButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sphereButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.setEnabled(false);
                    backTrack.setFocusable(false);
                    backTrack.sphereInterface.setVisible(true);
                    backTrack.sphereInterface.resetComponents();
                }
            }
        });
        graphicTools.add(sphereButton);
        cylinderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/cylinder.png"))); // NOI18N
        cylinderButton.setToolTipText("Add a cylinder into the source code.");
        cylinderButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cylinderButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cylinderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.setEnabled(false);
                    backTrack.setFocusable(false);
                    backTrack.cylinderInterface.setVisible(true);
                    backTrack.cylinderInterface.resetComponents();
                }
            }
        });
        graphicTools.add(cylinderButton);
        coneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/cone.png"))); // NOI18N
        coneButton.setToolTipText("Add a cone into the source code.");
        coneButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        coneButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        coneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.setEnabled(false);
                    backTrack.setFocusable(false);
                    backTrack.coneInterface.setVisible(true);
                    backTrack.coneInterface.resetComponents();
                }
            }
        });
        graphicTools.add(coneButton);
        boxButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/box.png"))); // NOI18N
        boxButton.setToolTipText("Add a box into the source code.");
        boxButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boxButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        boxButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.setEnabled(false);
                    backTrack.setFocusable(false);
                    backTrack.boxInterface.setVisible(true);
                    backTrack.boxInterface.resetComponents();
                }
            }
        });
        graphicTools.add(boxButton);
        polyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/poly.png"))); // NOI18N
        polyButton.setToolTipText("Add an indexedfaceset into the source code.");
        polyButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        polyButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        polyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ctabinterface singleSource = null;
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    backTrack.setEnabled(false);
                    backTrack.setFocusable(false);
                    backTrack.polyInterface.setVisible(true);
                    backTrack.polyInterface.resetComponents();
                }
            }
        });
        graphicTools.add(polyButton);
        textButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arts/text.png"))); // NOI18N
        textButton.setToolTipText("Add text into the source code.");
        textButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        textButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        textButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: add 3D Text into the document
                
            }
        });
        //graphicTools.add(textButton);
        /* blitting */
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(optionTools, BorderLayout.WEST);
        this.add(graphicTools, BorderLayout.EAST);
        /* end */
    }

    public void saveStatus (Boolean active) {
        saveButton.setEnabled(active);
    }
}