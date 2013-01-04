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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
final public class Cerrormessages {
    private static Hashtable messages = new Hashtable();
    private static Hashtable titles = new Hashtable();
    static public void initializeComponent () {
        String readerLine, readerFields[];
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(Cdatafiles.errors)));
            while ((readerLine = inputStream.readLine()) != null) {
                if (readerLine.length() > 0) {
                    readerFields = readerLine.split(":");
                    if (!readerFields[0].contains("#")) { // isn't a comment
                        if (readerFields.length >= 3) {
                            for (int index = 3; index < readerFields.length; index++)
                                readerFields[2] = readerFields[2]+":"+readerFields[index];
                            messages.put(readerFields[0], readerFields[2]);
                            titles.put(readerFields[0], readerFields[1]);
                        }
                    }
                }
            }
            inputStream.close();
        } catch (Exception exc) {
            // do nothing!
        }
    }
    
    static public String getMessage (String errorCode) {
        return (String) messages.get(errorCode);
    }
    
    static public String getTitle (String errorCode) {
        return (String) titles.get(errorCode);
    }
}
