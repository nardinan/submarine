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
package tokenizer;
import subwindows.Cerrorinterface;
public class Ctokenizer {
    public Cdocumentreader reader;
    public int status = 0;
    public int line = 0;
    public Ctoken backup = new Ctoken();
    public Ctokenizer (Cdocumentreader reader) {
        this.reader = reader;
    }

    public void resetReader () {
        this.reader.reset();
        line = 0;
    }

    private Ctoken _getNextToken () {
        Ctoken result = null;
        int search;
        boolean lastsegment = false;
        do {
            search = reader.read();
            if ((search >= 0) && (search <= 127)) {
                result = new Ctoken();
                result.token = String.valueOf((char) search);
                result.beginPosition = reader.getPosition()-1;
                result.finalPosition = reader.getPosition();
                result.line = line;
                result.symbolic = false;
                result.textual = false;
                if (((char)search) == '\n') line++;
            } else lastsegment = true;
        } while ((result == null) && (!lastsegment));
        return result;
    }

    public Ctoken getNextRow () {
        Ctoken result = new Ctoken();
        Ctoken backupToken;
        result.token = null;
        result.symbolic = false;
        result.textual = false;
        try {
            while ((backupToken = _getNextToken()) != null) {
                if (backupToken.token.charAt(0) != '\n') {
                    result.token += backupToken.token.charAt(0);
                    result.line = backupToken.line;
                }
                result.finalPosition = backupToken.finalPosition;
                result.beginPosition = result.finalPosition - result.token.length();
                return result;
            }
        }  catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("TOKENIERR", true);
        }
        return null;
    }

    public Ctoken getNextToken () {
        Ctoken result = null;
        Ctoken backupToken;
        try {
            while ((backupToken = _getNextToken()) != null) { //OMG! returns into an iterative function *shakes his head*
                if ((backupToken.token.charAt(0) != ' ') &&
                        (backupToken.token.charAt(0) != '\n') &&
                            (backupToken.token.charAt(0) != '\t')) {
                    if ((((backupToken.token.charAt(0) >= 33) &&
                            (backupToken.token.charAt(0) <= 47)) ||
                                ((backupToken.token.charAt(0) >= 58) &&
                                    (backupToken.token.charAt(0) <= 64)) ||
                                        ((backupToken.token.charAt(0) >= 91) &&
                                            (backupToken.token.charAt(0) <= 96)) ||
                                                ((backupToken.token.charAt(0) >= 123) &&
                                                    (backupToken.token.charAt(0) <= 126))) &&
                                                        (backupToken.token.charAt(0) != 95) &&
                                                            (backupToken.token.charAt(0) != 45)) {
                        switch (status) {
                            case 0:
                                status = 1;
                                backup.token = backupToken.token;
                                backup.beginPosition = backupToken.beginPosition;
                                backup.finalPosition = backupToken.finalPosition;
                                backup.line = backupToken.line;
                                backup.symbolic = true;
                                backup.textual = false;
                                break;
                            case 1:
                                backup.token += backupToken.token;
                                backup.finalPosition = backupToken.finalPosition;
                                backup.symbolic = true;
                                backup.textual = false;
                                break;
                            case 2:
                                status = 1;
                                result = new Ctoken();
                                result.token = backup.token;
                                result.beginPosition = backup.beginPosition;
                                result.finalPosition = backup.finalPosition;
                                result.line = backup.line;
                                result.symbolic = backup.symbolic;
                                result.textual = backup.textual;
                                backup.token = backupToken.token;
                                backup.beginPosition = backupToken.beginPosition;
                                backup.finalPosition = backupToken.finalPosition;
                                backup.line = backupToken.line;
                                backup.symbolic = true;
                                backup.textual = false;
                                return result;
                        }
                    } else {
                        switch (status) {
                            case 0:
                                status = 2;
                                backup.token = backupToken.token;
                                backup.beginPosition = backupToken.beginPosition;
                                backup.finalPosition = backupToken.finalPosition;
                                backup.line = backupToken.line;
                                backup.symbolic = false;
                                backup.textual = true;
                                break;
                            case 2:
                                backup.token += backupToken.token;
                                backup.finalPosition = backupToken.finalPosition;
                                backup.symbolic = false;
                                backup.textual = true;
                                break;
                            case 1:
                                status = 2;
                                result = new Ctoken();
                                result.token = backup.token;
                                result.beginPosition = backup.beginPosition;
                                result.finalPosition = backup.finalPosition;
                                result.line = backup.line;
                                result.symbolic = backup.symbolic;
                                result.textual = backup.textual;
                                backup.token = backupToken.token;
                                backup.beginPosition = backupToken.beginPosition;
                                backup.finalPosition = backupToken.finalPosition;
                                backup.line = backupToken.line;
                                backup.symbolic = false;
                                backup.textual = true;
                                return result;
                        }
                    }
                } else {
                    status = 0;
                    result = new Ctoken();
                    result.token = backup.token;
                    result.beginPosition = backup.beginPosition;
                    result.finalPosition = backup.finalPosition;
                    result.line = backup.line;
                    result.symbolic = backup.symbolic;
                    result.textual = backup.textual;
                    /* clear everything */
                    backup.token = "";
                    backup.beginPosition = 0;
                    backup.finalPosition = 0;
                    backup.line = 0;
                    backup.symbolic = false;
                    backup.textual = false;
                    /* end */
                    return result;
                }
            }
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("TOKENIERR", true);
        }
        return null;
    }
}