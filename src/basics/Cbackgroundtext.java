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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JTextPane;
public class Cbackgroundtext extends JTextPane {
    /* utils */
    private String backgroundImage = null;
    /* end */
    public Cbackgroundtext(String backgroundImage) {
        super();
        setOpaque(false);
        this.backgroundImage = backgroundImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(backgroundImage));
            g.drawImage(image, 0, this.getVisibleRect().y, image.getWidth(), image.getHeight(), this);
        } catch (Exception exc) {
            // no background image, or malformed path
        }
        super.paintComponent(g);
    }
}
