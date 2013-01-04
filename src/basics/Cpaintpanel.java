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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;
public class Cpaintpanel extends JPanel {
    public ArrayList points = new ArrayList();
    public int maximumRange = 360;
    public class Csingleton {
        public int coordinateX, coordinateY;
        public boolean firstPoint = false;
        public Csingleton aLink = null, bLink = null;
        public Csingleton (int coordinateX, int coordinateY, boolean firstPoint) {
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            this.firstPoint = firstPoint;
        }
    }
    Csingleton backupStack = new Csingleton(-1, -1, false);
    public Cpaintpanel () {
        super();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent evt) {
                int currentMinimum;
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    backupStack.coordinateX = evt.getX();
                    backupStack.coordinateY = evt.getY();
                    points.add(new Csingleton(backupStack.coordinateX, backupStack.coordinateY, false));
                    cleanPoints();
                    buildPoints();
                    repaint();
                } else if (evt.getButton() == MouseEvent.BUTTON3) {
                    currentMinimum = nearNode(evt.getX(), evt.getY(), false, 5, null, null);
                    if (currentMinimum >= 0) {
                        points.remove(currentMinimum);
                        cleanPoints();
                        buildPoints();
                    }
                    repaint();
                }
            }
        });
    }
    
    public int nearNode (int positionX, int positionY, boolean chechEdges, int rangeEdges, Csingleton toExclude, Csingleton softExclude) {
        Csingleton backupSingleton = null;
        double backupDistance = -1, currentDistance;
        int currentMinimum = -1;
        for (int index = 0; index < points.size(); index++) {
            backupSingleton = (Csingleton) points.get(index);
            if ((toExclude == null) || ((backupSingleton != toExclude) && ((toExclude.aLink == null) || (toExclude.aLink != backupSingleton)) && ((toExclude.bLink == null) || (toExclude.bLink != backupSingleton)))) {
                if ((!chechEdges) || ((backupSingleton.aLink == null) || (backupSingleton.bLink == null))) {
                    currentDistance = Math.hypot((positionX-backupSingleton.coordinateX), (positionY-backupSingleton.coordinateY));
                    if (((backupDistance > currentDistance) || (backupDistance < 0)) && (currentDistance <= maximumRange)) {
                        if ((softExclude == null) || (backupSingleton != softExclude) || (backupDistance < 0)) {
                            backupDistance = currentDistance;
                            currentMinimum = index;
                        }
                    }
                }
            }
        }
        return currentMinimum;
    }
    
    public Csingleton getPoints (int index) {
        Csingleton backupSingleton = null;
        if ((index >= 0) && (index < points.size()))
            backupSingleton = (Csingleton) points.get(index);
        return backupSingleton;
    }
    
    public void cleanPoints () {
        Csingleton backupSingleton = null;
        for (int index = 0; index < points.size(); index++) {
           backupSingleton = (Csingleton) points.get(index);
           backupSingleton.aLink = backupSingleton.bLink = null;
        }
    }
    
    public void buildPoints () {
        Csingleton backupSingleton = null;
        for (int index = 0; index < points.size(); index++) {
           backupSingleton = (Csingleton) points.get(index);
           if ((backupSingleton.aLink == null) || (backupSingleton.bLink == null))
               this.buildPoints(backupSingleton, null, 1);
        }
    }
    
    private void buildPoints (Csingleton backupSingleton, Csingleton softExclude, int deep) {
        boolean searchLeft = false, searchRight = false;
        if ((backupSingleton != null) && (deep >= 1)) {
            if (backupSingleton.aLink == null) {
               if ((backupSingleton.aLink = this.getPoints(this.nearNode(backupSingleton.coordinateX, backupSingleton.coordinateY, true, 30, backupSingleton, softExclude))) != null) {
                   if (backupSingleton.aLink.aLink == null) backupSingleton.aLink.aLink = backupSingleton;
                   else if (backupSingleton.aLink.bLink == null) backupSingleton.aLink.bLink = backupSingleton;
                   searchLeft = true;
               }
           }
           if (backupSingleton.bLink == null) {
               if ((backupSingleton.bLink = this.getPoints(this.nearNode(backupSingleton.coordinateX, backupSingleton.coordinateY, true, 30, backupSingleton, softExclude))) != null) {
                   if (backupSingleton.bLink.aLink == null) backupSingleton.bLink.aLink = backupSingleton;
                   else if (backupSingleton.bLink.bLink == null) backupSingleton.bLink.bLink = backupSingleton;
                   searchRight = true;
               }
           }
           if (searchLeft) this.buildPoints(backupSingleton.aLink, backupSingleton.bLink, deep-1);
           if (searchRight) this.buildPoints(backupSingleton.bLink, backupSingleton.aLink, deep-1);
        }
    }
    
    @Override
    public void paintComponent (Graphics g) {
       Graphics2D graph = (Graphics2D)g;
       Csingleton backupSingleton = null;
       graph.clearRect(0, 0, this.getWidth(), this.getHeight());
       for (int index = 0; index < points.size(); index++) {
           backupSingleton = (Csingleton) points.get(index);
           if (backupSingleton.aLink != null) {
               graph.setPaint(Color.BLACK);
               graph.setStroke(new BasicStroke(1));
               graph.draw(new Line2D.Double(backupSingleton.coordinateX, backupSingleton.coordinateY, backupSingleton.aLink.coordinateX, backupSingleton.aLink.coordinateY));
           }
           if (backupSingleton.bLink != null) {
               graph.setPaint(Color.BLACK);
               graph.setStroke(new BasicStroke(1));
               graph.draw(new Line2D.Double(backupSingleton.coordinateX, backupSingleton.coordinateY, backupSingleton.bLink.coordinateX, backupSingleton.bLink.coordinateY));
           }
           
           /* Drawing Points */
           if (backupSingleton.firstPoint) graph.setPaint(Color.GREEN);
           else graph.setPaint(Color.RED);
           graph.setStroke(new BasicStroke(5));
           graph.draw(new Line2D.Double(backupSingleton.coordinateX, backupSingleton.coordinateY, backupSingleton.coordinateX, backupSingleton.coordinateY));
       }
       
    }
}
