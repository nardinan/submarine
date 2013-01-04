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
package submarine;
import basics.Cerrormessages;
import basics.Csplashinterface;
import main.Cmaininterface;
import subwindows.Cerrorinterface;
public class Cmain {
    public static void main (String args[]) {
        Cerrormessages.initializeComponent();
        Csplashinterface splashInterface = new Csplashinterface();
        splashInterface.initializeComponent();
        splashInterface.setVisible(true);
        try {
            Thread.sleep(2000); // just take a look at my beautiful splash screen!
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("THREADERR", true);
        }
        splashInterface.setProgressValue(10);
        Cmaininterface mainInterface = new Cmaininterface();
        splashInterface.setProgressValue(40);
        mainInterface.initializeComponent();
        splashInterface.setProgressValue(60);
        mainInterface.initializeThreads();
        splashInterface.setProgressValue(75);
        mainInterface.initializeMenu();
        splashInterface.setProgressValue(90);
        mainInterface.setVisible(true);
        try {
            Thread.sleep(500);
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("THREADERR", true);
        }
        splashInterface.setProgressValue(100);
        splashInterface.setVisible(false);
        
    }
}
