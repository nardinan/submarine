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
import main.Ctabinterface;
import subwindows.Cerrorinterface;
import tokenizer.Cblock;
import tokenizer.Ctoken;
public class Ctagsthread extends Thread {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* semaphores */
    final public Object updateLocker = new Object();
    /* end */
    /* utilities */
    private boolean update = false;
    public ArrayList<Cblock> blockList = new ArrayList<Cblock>(); // Actually not properly used
    /* end */
    public Ctagsthread (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }

    public void switchUpdate () {
        synchronized (updateLocker) {
            update = true;
        }
    }

    @Override
    public void run () {
        Ctoken singleToken = null;
        Cblock backupBlock = null;
        Ctabinterface singleSource = null;
        Boolean isTag = false, isClosing = false, isName = false, needUpdate = false;
        while (true) {
            synchronized (updateLocker) {
                needUpdate = update;
                update = false;
            }
            if (needUpdate) {
                blockList.clear();
                synchronized (backTrack.tabbedPaneLocker) {
                    singleSource = (Ctabinterface) backTrack.tabbedPane.getSelectedComponent();
                }
                if (singleSource != null) {
                    singleSource.syntaxLexer.resetReader();
                    while ((singleToken = singleSource.syntaxLexer.getNextToken()) != null) {
                        if (!singleToken.token.equals(" ")) {
                            if (singleToken.symbolic) {
                                for (int index = 0; index < singleToken.token.length(); index++) {
                                    if (singleToken.token.charAt(index) == '<') {
                                        isTag = true;
                                        isClosing = isName = false;
                                        if ((index+1) < singleToken.token.length())
                                            if (singleToken.token.charAt(index+1) == '/') isClosing = true;
                                        if (!isClosing) {
                                            backupBlock = new Cblock();
                                            backupBlock.beginPosition = singleToken.beginPosition+index;
                                            backupBlock.beginLine = singleToken.line;
                                        } else backupBlock = null;
                                    } else if (singleToken.token.charAt(index) == '>') {
                                        if ((index-1) >= 0)
                                            if (singleToken.token.charAt(index-1) == '/') isClosing = true;
                                        if (isClosing)
                                            if (backupBlock != null) {
                                                backupBlock.finalPosition = singleToken.beginPosition+index+1;
                                                backupBlock.finalLine = singleToken.line;
                                                backupBlock.complete = true;
                                            }
                                        isTag = isClosing = isName = false;
                                        backupBlock = null;
                                    }
                                }
                            }
                            if (singleToken.textual) {
                                if (singleToken.token.toUpperCase().equals("DEF")) isName = true;
                                else if (isName) {
                                    if (backupBlock != null) {
                                        backupBlock.defName = singleToken.token;
                                    }
                                    isName = false;
                                } else if (isTag) {
                                    if (isClosing) {
                                        for (Cblock singleItem : blockList) {
                                            if ((singleItem.tagName.equals(singleToken.token)) && (!singleItem.complete))
                                                backupBlock = singleItem;
                                        }
                                    } else {
                                        if (backupBlock != null) {
                                            backupBlock.tagName = singleToken.token;
                                            backupBlock.complete = false;
                                            backupBlock.finalPosition = -1;
                                            blockList.add(backupBlock);
                                        }
                                    }
                                    isTag = false;
                                }
                            }
                        }
                    }
                    if (!blockList.isEmpty())
                        backTrack.modifyBlocks(blockList); // updating block system
                    else backTrack.modifyBlocks(null);
                } else backTrack.modifyBlocks(null);
            }
            try {
                Ctagsthread.sleep(1024);
            } catch (InterruptedException ex) {
                Cerrorinterface bck = new Cerrorinterface("THREADERR", true);
            }

        }
    }
}
