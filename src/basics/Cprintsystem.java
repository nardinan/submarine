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
/*
 * based on excellent DocumentRender.java, developed by:
 * Kei G. Gauthier (c) 2002
 * Suite 301
 * 77 Winsor Street
 * Ludlow, MA 01056
 * (thank you so much)
 */
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.View;
import main.Cmaininterface;
import main.Ctabinterface;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
public class Cprintsystem implements Printable {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* utilities */
    protected int currentPage = -1;
    protected double coordSY = 0;
    protected double coordEY = 0;
    protected boolean scaleWidth = true;
    protected JEditorPane layer;
    protected PageFormat pformat;
    protected PrinterJob pjob;
    /* end */
    public Cprintsystem (Cmaininterface backTrack) {
        this.backTrack = backTrack;
        pformat = new PageFormat();
        pjob = PrinterJob.getPrinterJob();
    }

    public Document getDocument() {
        return (layer!=null)?layer.getDocument():null;
    }

    public boolean getscaleWidth() {
        return scaleWidth;
    }

    public void pageDialog () {
        pformat = pjob.pageDialog(pformat);
    }
    
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        double scale = 0.8;
        Graphics2D pageInterface;
        View rootView;
        pageInterface = (Graphics2D) graphics;
        pageInterface.setPaint(Color.black);
        layer.setSize((int)pageFormat.getImageableWidth(), Integer.MAX_VALUE);
        layer.validate();
        rootView = layer.getUI().getRootView(layer);
        if ((scaleWidth) && (layer.getMinimumSize().getWidth() > pageFormat.getImageableWidth())) {
            scale = pageFormat.getImageableWidth()/layer.getMinimumSize().getWidth();
            pageInterface.scale(scale, scale);
        }
        pageInterface.setClip((int)(pageFormat.getImageableX()/scale),
                (int)(pageFormat.getImageableY()/scale),
                (int)(pageFormat.getImageableWidth()/scale),
                (int)(pageFormat.getImageableHeight()/scale));
        if (pageIndex > currentPage) {
            currentPage = pageIndex;
            coordSY += coordEY;
            coordEY = pageInterface.getClipBounds().getHeight();
        }
        pageInterface.translate(pageInterface.getClipBounds().getX(), pageInterface.getClipBounds().getY());
        Rectangle allocation = new Rectangle(0, (int)-coordSY, (int)(layer.getMinimumSize().getWidth()), (int)(layer.getPreferredSize().getHeight()));
        if (printView(pageInterface, allocation, rootView)) { return Printable.PAGE_EXISTS; }
        else {
            coordSY = 0;
            coordEY = 0;
            currentPage = -1;
            return Printable.NO_SUCH_PAGE;
        }
        
    }

    public void print () {
        Ctabinterface singleSource = null;
        synchronized (backTrack.tabbedPaneLocker) {
            singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
        }
        if (singleSource != null)
            this.setDocument(singleSource.mainView);
        printDialog();
    }

    protected void printDialog () {
        if (pjob.printDialog()) {
            pjob.setPrintable(this, pformat);
            try {
                pjob.print();
            } catch (Exception exc) {
                coordSY = 0;
                coordEY = 0;
                currentPage = -1;
                backTrack.consolePane.appendText("Unable to contact your printer", true);
            }
        }
    }

    protected boolean printView (Graphics2D pageInterface, Shape allocation, View view) {
        boolean pageExists = false;
        Rectangle clipRectangle = pageInterface.getClipBounds();
        Shape childAllocation;
        View childView;
        if (view.getViewCount() > 0) {
            for (int index = 0; index < view.getViewCount(); index++) {
                   childAllocation = view.getChildAllocation(index, allocation);
                   if (childAllocation != null) {
                       childView = view.getView(index);
                       if (printView(pageInterface, childAllocation, childView)) { pageExists = true; }
                   }
            }
        } else {
            if (allocation.getBounds().getMaxY() >= clipRectangle.getY()) {
                pageExists = true;
                if ((allocation.getBounds().getHeight() > clipRectangle.getHeight()) &&
                        (allocation.intersects(clipRectangle))) {
                    view.paint(pageInterface, allocation);
                } else {
                    if (allocation.getBounds().getY() >= clipRectangle.getY()) {
                        if (allocation.getBounds().getMaxY() <= clipRectangle.getMaxY()) {
                            view.paint(pageInterface, allocation);
                        } else if (allocation.getBounds().getY() < coordEY) {
                            coordEY = allocation.getBounds().getY();
                        }
                    }
                }
            }
        }
        return pageExists;
    }

    protected void setContentType(String type) {
        layer.setContentType(type);
    }

    public void setDocument (RSyntaxTextArea editorpane) {
        layer = new JEditorPane();
        setDocument("text/xml", editorpane.getDocument());
    }

    protected void setDocument (String type, Document document) {
        setContentType(type);
        layer.setDocument(document);
    }

    public void setscaleWidth (boolean scaleWidth) {
        this.scaleWidth = scaleWidth;
    }
}