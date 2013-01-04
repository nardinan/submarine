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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/*
 * based on excellent ZebraJList.java which can be found at:
 * http://www.nadeausoftware.com
 */
public class Czebralist extends javax.swing.JList {
    private java.awt.Color rowColors[] = new java.awt.Color[2];
    private boolean drawStripes = false;
    public boolean drawOverside = false;
    public Czebralist() {}
    public Czebralist (javax.swing.ListModel dataModel) { super(dataModel); }
    public Czebralist (Object[] listData) { super(listData); }
    public Czebralist (java.util.Vector<?> listData) { super(listData); }

    @Override
    public void paintComponent (java.awt.Graphics g) {
        final java.awt.Insets insets = getInsets();
        final int w = getWidth()  - insets.left - insets.right, h = getHeight() - insets.top  - insets.bottom, x = insets.left, remainder;
        int y = insets.top, nRows = 0, startRow = 0, rowHeight = getFixedCellHeight();
        drawStripes = ((getLayoutOrientation() == VERTICAL) && (isOpaque()));
        if (drawStripes) {
            updateZebraColors();
            if (rowHeight > 0)  nRows = h / rowHeight;
            else {
                final int nItems = getModel().getSize();
                rowHeight = 17;
                for (int i = 0; i < nItems; i++, y+=rowHeight) {
                    rowHeight = getCellBounds(i, i).height;
                    g.setColor(rowColors[i&1]);
                    g.fillRect(x, y, w, rowHeight);
                }
                nRows = nItems + (insets.top + h - y) / rowHeight;
                startRow = nItems;
            }
            for (int i = startRow; i < nRows; i++, y+=rowHeight) {
                g.setColor(rowColors[i&1]);
                g.fillRect(x, y, w, rowHeight);
            }
            if ((remainder = insets.top + h - y) > 0) {
                g.setColor(rowColors[nRows&1]);
                g.fillRect(x, y, w, remainder);
            }
            setOpaque(false);
            super.paintComponent(g);
            setOpaque(true);
        } else super.paintComponent(g);
    }

    @Override
    public void paint (java.awt.Graphics g) {
        super.paint(g);
        if (drawOverside) {
            Graphics2D hook = (Graphics2D)g;
            hook.setColor(Color.BLACK);
            hook.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            hook.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    private class RendererWrapper  implements javax.swing.ListCellRenderer {
        public javax.swing.ListCellRenderer ren = null;
        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            final java.awt.Component c = ren.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (!isSelected && drawStripes)
                c.setBackground(rowColors[index&1]);

            return c;
        }
    }
    private RendererWrapper wrapper = null;

    @Override
    public javax.swing.ListCellRenderer getCellRenderer() {
        final javax.swing.ListCellRenderer ren = super.getCellRenderer();
        if (ren == null) return null;
        if (wrapper == null) wrapper = new RendererWrapper();
        wrapper.ren = ren;
        return wrapper;
    }

    private void updateZebraColors() {
        final java.awt.Color sel = getSelectionBackground();
        if ((rowColors[0] = getBackground()) == null) rowColors[0] = rowColors[1] = java.awt.Color.white;
        else if (sel == null) rowColors[1] = rowColors[0];
        else {
            final float[] bgHSB = java.awt.Color.RGBtoHSB(rowColors[0].getRed(), rowColors[0].getGreen(), rowColors[0].getBlue(), null);
            final float[] selHSB  = java.awt.Color.RGBtoHSB(sel.getRed(), sel.getGreen(), sel.getBlue(), null);
            rowColors[1] = java.awt.Color.getHSBColor((selHSB[1]==0.0||selHSB[2]==0.0) ? bgHSB[0] : selHSB[0], 0.1f * selHSB[1] + 0.9f * bgHSB[1], bgHSB[2] + ((bgHSB[2]<0.5f) ? 0.05f : -0.05f));
        }
    }
}