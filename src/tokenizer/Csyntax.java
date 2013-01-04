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
import java.awt.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import main.Cmaininterface;
import main.Ctabinterface;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.Token;
import subwindows.Cerrorinterface;
public class Csyntax {
    /* father's class */
    private Cmaininterface backTrack;
    /* end */
    /* utils */
    private Hashtable languageElements = new Hashtable();
    public Hashtable styleElements = new Hashtable();
    /* end */
    public Csyntax (Cmaininterface backTrack) {
        this.backTrack = backTrack;
    }
    
    public boolean loadSyntax (String inputFile) {
        boolean returnValue = false;
        languageElements.clear();
        styleElements.clear();
        String readerLine = null, readerFields[];
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            while ((readerLine = inputStream.readLine()) != null) {
                if (readerLine.length() > 0) {
                    readerFields = readerLine.split(":");
                    if (!readerFields[0].contains("#")) { // isn't a comment
                        if (readerFields.length >= 4) {
                            ArrayList backupArray = (ArrayList) languageElements.get(readerFields[0]);
                            languageElements.remove(readerFields[0]);
                            for (int index = 4; index < readerFields.length; index++)
                                readerFields[3] = readerFields[3]+":"+readerFields[index];
                            if (backupArray == null)
                                backupArray = new ArrayList();
                            Hashtable backupValue = new Hashtable();
                            backupValue.put("keyword", readerFields[1]);
                            backupValue.put("sdescription", readerFields[2]);
                            backupValue.put("ldescription", readerFields[3]);
                            backupArray.add(backupValue);
                            languageElements.put(readerFields[0], backupArray);
                        } else if (readerFields.length == 2) {
                            styleElements.put(readerFields[0], readerFields[1]);
                        }
                    }
                }
            }
            returnValue = true;
            inputStream.close();
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("CONFIGERR", true);
        }
        return returnValue;
    }
    
    public CompletionProvider createCompletion() {
        DefaultCompletionProvider returnValue = new DefaultCompletionProvider();
        Enumeration keysArray = languageElements.keys();
        while (keysArray.hasMoreElements()) {
            String keyValue = (String) keysArray.nextElement();
            ArrayList backupArray = (ArrayList) languageElements.get(keyValue);
            Iterator valuesArray = backupArray.iterator();
            while (valuesArray.hasNext()) {
                Hashtable valueValue = (Hashtable) valuesArray.next();
                returnValue.addCompletion(new BasicCompletion(returnValue, (String)valueValue.get("keyword"), (String)valueValue.get("sdescription"), (String)valueValue.get("ldescription")));
            }
        }        
        return returnValue;
    }
    
    public void updateStyle (String categoryName, String parameters) {
        Component[] containers = null;
        styleElements.remove(categoryName);
        styleElements.put(categoryName, parameters);
        synchronized (backTrack.tabbedPaneLocker) {
            containers = backTrack.tabbedPane.getComponents();
        }
        for (int index = 0; index < containers.length; index++) 
            updateStyle((Ctabinterface) containers[index]);
    }
    
    public void updateStyle (Ctabinterface singleSource) {
        Enumeration keysArray = styleElements.keys();
        singleSource.setStyle("default:"+styleElements.get("default"));
        while (keysArray.hasMoreElements()) {
            String keyValue = (String) keysArray.nextElement();
            if (!keyValue.equalsIgnoreCase("default"))
                singleSource.setStyle(keyValue+":"+styleElements.get(keyValue));
        }
    }
    
    public String getCategory (int value) {
        String returnValue = "function";
        if (value < 0) returnValue = "default";
        else if (value == Token.RESERVED_WORD) returnValue = "reserved words";
        else if (value == Token.COMMENT_MULTILINE) returnValue = "comment";
        else if (value == Token.COMMENT_EOL) returnValue = "comment EOL";
        else if (value == Token.COMMENT_DOCUMENTATION) returnValue = "comment documentation";
        else if (value == Token.LITERAL_CHAR) returnValue = "characters";
        else if (value == Token.VARIABLE) returnValue = "variables";
        else if (value == Token.PREPROCESSOR) returnValue = "preprocessor";
        else if (value == Token.IDENTIFIER) returnValue = "identifiers";
        else if (value == Token.OPERATOR) returnValue = "operators";
        else if (value == Token.SEPARATOR) returnValue = "separators";
        else if (value == Token.DATA_TYPE) returnValue = "data types";
        else if (value == Token.LITERAL_BOOLEAN) returnValue = "boolean values";
        else if (value == Token.LITERAL_NUMBER_DECIMAL_INT) returnValue = "decimal values";
        else if (value == Token.LITERAL_NUMBER_FLOAT) returnValue = "float values";
        else if (value == Token.LITERAL_NUMBER_HEXADECIMAL) returnValue = "hexadecimal values";
        else if (value == Token.LITERAL_STRING_DOUBLE_QUOTE) returnValue = "string values";
        else if (value == Token.LITERAL_BACKQUOTE) returnValue = "backquotes";
        else if (value == Token.WHITESPACE) returnValue = "white spaces";
        else if (value == Token.MARKUP_TAG_DELIMITER) returnValue = "tag delimiter";
        else if (value == Token.MARKUP_TAG_NAME) returnValue = "tag name";
        else if (value == Token.MARKUP_TAG_ATTRIBUTE) returnValue = "tag attribute";
        return returnValue;
    }
    
    public int getCategory (String defaultName) {
        int returnValue = Token.FUNCTION;
        if (defaultName.equalsIgnoreCase("default")) returnValue = -1;
        else if (defaultName.equalsIgnoreCase("reserved words")) returnValue = Token.RESERVED_WORD;
        else if (defaultName.equalsIgnoreCase("comment")) returnValue = Token.COMMENT_MULTILINE;
        else if (defaultName.equalsIgnoreCase("comment EOL")) returnValue = Token.COMMENT_EOL;
        else if (defaultName.equalsIgnoreCase("comment documentation")) returnValue = Token.COMMENT_DOCUMENTATION;
        else if (defaultName.equalsIgnoreCase("characters")) returnValue = Token.LITERAL_CHAR;
        else if (defaultName.equalsIgnoreCase("variables")) returnValue = Token.VARIABLE;
        else if (defaultName.equalsIgnoreCase("preprocessor")) returnValue = Token.PREPROCESSOR;
        else if (defaultName.equalsIgnoreCase("identifiers")) returnValue = Token.IDENTIFIER;
        else if (defaultName.equalsIgnoreCase("operators")) returnValue = Token.OPERATOR;
        else if (defaultName.equalsIgnoreCase("separators")) returnValue = Token.SEPARATOR;
        else if (defaultName.equalsIgnoreCase("data types")) returnValue = Token.DATA_TYPE;
        else if (defaultName.equalsIgnoreCase("boolean values")) returnValue = Token.LITERAL_BOOLEAN;
        else if (defaultName.equalsIgnoreCase("decimal values")) returnValue = Token.LITERAL_NUMBER_DECIMAL_INT;
        else if (defaultName.equalsIgnoreCase("float values")) returnValue = Token.LITERAL_NUMBER_FLOAT;
        else if (defaultName.equalsIgnoreCase("hexadecimal values")) returnValue = Token.LITERAL_NUMBER_HEXADECIMAL;
        else if (defaultName.equalsIgnoreCase("string values")) returnValue = Token.LITERAL_STRING_DOUBLE_QUOTE;
        else if (defaultName.equalsIgnoreCase("backquotes")) returnValue = Token.LITERAL_BACKQUOTE;
        else if (defaultName.equalsIgnoreCase("white spaces")) returnValue = Token.WHITESPACE;
        else if (defaultName.equalsIgnoreCase("tag delimiter")) returnValue = Token.MARKUP_TAG_DELIMITER;
        else if (defaultName.equalsIgnoreCase("tag name")) returnValue = Token.MARKUP_TAG_NAME;
        else if (defaultName.equalsIgnoreCase("tag attribute")) returnValue = Token.MARKUP_TAG_ATTRIBUTE;
        return returnValue;
    }
    
    public boolean saveSyntax (String outputFile) {
        boolean returnValue = false;
        String outputInformation = "";
        try {
            Enumeration keysArray = languageElements.keys();
            while (keysArray.hasMoreElements()) {
                String keyValue = (String) keysArray.nextElement();
                ArrayList backupArray = (ArrayList) languageElements.get(keyValue);
                Iterator valuesArray = backupArray.iterator();
                while (valuesArray.hasNext()) {
                    Hashtable valueValue = (Hashtable) valuesArray.next();
                    outputInformation += keyValue+":"+valueValue.get("keyword")+":"+valueValue.get("sdescription")+":"+valueValue.get("ldescription")+"\n";
                }
            }
            keysArray = styleElements.keys();
            while (keysArray.hasMoreElements()) {
                String keyValue = (String) keysArray.nextElement();
                outputInformation += keyValue+":"+styleElements.get(keyValue)+"\n";
            }
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
            outputStream.write(outputInformation);
            returnValue = true;
            outputStream.close();
        } catch (Exception exc) {
            Cerrorinterface bck = new Cerrorinterface("CONFIGERR", true);
        }
        return returnValue;
    }
}
