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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
public class Cbasewindow extends JFrame {
    public Cbasewindow (String title) {
        super();
        //Cbasewindow.setDefaultLookAndFeelDecorated(true);
        Dimension mainSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(mainSize.width, mainSize.height));
        /* centering */
        this.setLocation(0, 0);
        /* end */
        /* flags */
        this.setAlwaysOnTop(false);
        this.setUndecorated(false);
        this.setResizable(true);
        /* end */
    }

    public Cbasewindow (Integer width, Integer height, String title, Boolean bordless, Boolean resizable, Boolean onTop) {
        super();
        //Cbasewindow.setDefaultLookAndFeelDecorated(true);
        Dimension mainSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(new Dimension(width, height));
        /* centering */
        int x = (mainSize.width - width)/2;
        int y = (mainSize.height - height)/2;
        this.setLocation(x, y);
        /* end */
        /* flags */
        this.setAlwaysOnTop(onTop);
        this.setUndecorated(bordless);
        this.setResizable(resizable);
        /* end */
        
    }
    
    public void setSpecial (double trasparency) {
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, (float)trasparency));
    }
}