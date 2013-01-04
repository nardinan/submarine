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
import java.util.ArrayList;
import main.Cmaininterface;
import tokenizer.Cblock;
public class Ctagsswing implements Runnable {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* utilities */
    ArrayList<Cblock> blockList = new ArrayList<Cblock>(); // tags into the source code
    ArrayList<String> sourceCoordinates = new ArrayList<String>(); // tags' coordinate into the source code
    /* end */
    public Ctagsswing (Cmaininterface backTrack, ArrayList<Cblock> blockList) {
        this.backTrack = backTrack;
        this.blockList = null;
        this.sourceCoordinates = new ArrayList<String>();
        if (blockList != null) {
            this.blockList = new ArrayList<Cblock>();
            for (int index = 0; index < blockList.size(); index++)
                this.blockList.add(blockList.get(index));
        }
    }

    public void run() {
        Cblock backupBlock = null;
        String backupString = null;
        synchronized (backTrack.tagsPane.sourceCoordinatesLocker) {
            synchronized (backTrack.tagsPane.mainModelLocker) {
                backTrack.tagsPane.mainModel.removeAllElements();
                backTrack.tagsPane.sourceCoordinates.clear();
                if ((blockList != null) && (sourceCoordinates != null)) {
                    for (int index = 0; index < blockList.size(); index++) {
                        backupBlock = blockList.get(index);
                        if (backupBlock.defName != null) {
                            backupString = "<html><body>{"+((backupBlock.complete)?"<font color=\"#339900\">Closed</font>":"<font color=\"#660000\">Opened</font>")+"}["+((backupBlock.tagName != null)?backupBlock.tagName:"unnamed")+"]<b>"+backupBlock.defName+"</b></body></html>";
                            backTrack.tagsPane.mainModel.addElement(backupString);
                            if (backupBlock.finalPosition < 0) // unclosed tag
                                backupBlock.finalPosition = backupBlock.beginPosition + 1;
                            backTrack.tagsPane.sourceCoordinates.add(backupBlock.beginPosition+":"+backupBlock.finalPosition);
                        }
                    }
                }
            }
        }
    }
}
