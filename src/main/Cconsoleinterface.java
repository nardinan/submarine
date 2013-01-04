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
import com.explodingpixels.macwidgets.HudWindow;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
public class Cconsoleinterface extends HudWindow {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* SWING vars */
    private JTextPane mainView;
    /* end */
    /* utils */
    private StringBuffer bufferizer = new StringBuffer();
    private int errorsUnread = 0, messagesUnread = 0;
    /* end */
    /* semaphores */
    final public Object mainViewLocker = new Object();
    /* end */
    public Cconsoleinterface (Cmaininterface backTrack) {
        super("Debug's console");
        getJDialog().setPreferredSize(new Dimension(800, 200));
        getJDialog().setLocationRelativeTo(backTrack);
        getJDialog().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getJDialog().setAlwaysOnTop(true);
        this.backTrack = backTrack;
    }
    
    public void initializeComponent () {
        GridBagConstraints positioner = null;
        mainView = new JTextPane();/*{
            private static final long serialVersionUID = 1L;
            @Override
            public void paint(Graphics g) {
                g.clearRect(0,0,getWidth(), getHeight());
                super.paint(g);
            }           
        };*/
        mainView.setContentType("text/html");
        mainView.setEditable(false);
        mainView.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.25f));
        JScrollPane container = new JScrollPane(mainView);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.setMaximumSize(new Dimension(400, 150));
        container.setMinimumSize(new Dimension(400, 150));
        container.setPreferredSize(new Dimension(400, 150));
        container.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        JPanel mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainContainer.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        /* blitting */
        this.getJDialog().add(mainContainer);
        positioner = new GridBagConstraints();
        positioner.gridx = positioner.gridy = 0;
        positioner.weightx = positioner.weighty = 1.0;
        positioner.fill = GridBagConstraints.BOTH;
        positioner.insets = new Insets(2, 2, 2, 2);
        mainContainer.add(container, positioner);
        this.getJDialog().pack();
        /* end */
    }

    public void appendText (String text, Boolean error) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String backupInsertion = "<span style=\"font-family: Menlo; font-size: 11;\"><font color=\"CCCCCC\">"+dateFormat.format(calendar.getTime());
        if (error) {
            if (!this.getJDialog().isVisible())
                errorsUnread++;
            backupInsertion += " - [</font><font color=\"CC0000\">error</font><font color=\"CCCCCC\">] - "+text+"</font></span>";
        } else {
            if (!this.getJDialog().isVisible())
                messagesUnread++;
            backupInsertion += " - [</font><font color=\"0066FF\">output</font><font color=\"CCCCCC\">] - "+text+"</font></span>";
        }
        backTrack.infosPane.updateConsole(messagesUnread, errorsUnread);
        synchronized (mainViewLocker) {
            bufferizer.append(backupInsertion);
            bufferizer.append("<br/>");
            mainView.setText(bufferizer.toString());
        }
    }
    
    public void resetUnreadMessages () {
        messagesUnread = 0;
        errorsUnread = 0;
        backTrack.infosPane.updateConsole(messagesUnread, errorsUnread);
    }
}
