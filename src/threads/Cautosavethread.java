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
package threads;
import main.Cmaininterface;
import subwindows.Cerrorinterface;
public class Cautosavethread extends Thread {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* semaphores */
    final public Object updateLocker = new Object();
    /* end */
    /* utilities */
    private boolean update = false;
    private int passed = 0;
    private int minutes = 0;
    /* end */
    public Cautosavethread (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void switchUpdate(boolean update) {
        synchronized (updateLocker) {
            this.update = update;
            passed = 0;
        }
    }

    public void switchTimer (int minutes) {
        synchronized (updateLocker) {
            this.minutes = minutes;
        }
    }

    @Override
    public void run () {
        Boolean needUpdate = false;
        while (true) {
            synchronized (updateLocker) {
                needUpdate = update;
            }
            if (needUpdate) {
                if ((passed >= minutes) && (minutes > 0)) {
                    passed = 0;
                    backTrack.saveAllFiles();
                }
                try {
                    Cautosavethread.sleep(1000*60); // it's a minute (1000 millisecs * 60)
                    passed++;
                } catch (Exception exc) {
                    Cerrorinterface bck = new Cerrorinterface("THREADERR", true);
                }
            }
        }
    }
}