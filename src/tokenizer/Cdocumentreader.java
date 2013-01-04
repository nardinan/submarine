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
import java.io.Reader;
import javax.swing.text.AbstractDocument;
public class Cdocumentreader extends Reader {
    public void update(int position, int adjustment){
        if (position < this.position){
            if (this.position < position - adjustment){
                this.position = position;
            } else {
                this.position += adjustment;
            }
        }
    }
    private int position = 0;
    private int mark = -1;
    private AbstractDocument document;
    public Cdocumentreader(AbstractDocument document){
        this.document = document;
    }

    public void close() {}

    @Override
    public void mark(int readAheadLimit){
        mark = position;
    }

    @Override
    public boolean markSupported(){
        return true;
    }

    public int getPosition () {
        return (int)position;
    }

    @Override
    public int read(){
        if (position < document.getLength()){
            try {
                char chr = document.getText((int)position, 1).charAt(0);
                position++;
                return chr;
            } catch (Exception x){
                return -1;
            }
        } else return -1;
    }

    @Override
    public int read(char[] cbuf){
        return read(cbuf, 0, cbuf.length);
    }

    public int read(char[] cbuf, int off, int len){
        if (position < document.getLength()){
            int length = len;
            if (position + length >= document.getLength()) length = document.getLength() - (int)position;
            if (off + length >= cbuf.length) length = cbuf.length - off;
            try {
                String str = document.getText((int)position, length);
                position += length;
                for (int i=0; i<length; i++){
                    cbuf[off+i] = str.charAt(i);
                }
                return length;
            } catch (Exception x){
                return -1;
            }
        } else return -1;
    }

    @Override
    public boolean ready() {
        return true;
    }

    @Override
    public void reset(){
        if (mark == -1){
            position = 0;
        } else {
            position = mark;
        }
        mark = -1;
    }
    
    @Override
    public long skip(long n){
        if (position + n <= document.getLength()){
            position += n;
            return n;
        } else {
            long oldPos = position;
            position = document.getLength();
            return (document.getLength() - oldPos);
        }
    }

    public void seek(int n){
        if (n <= document.getLength()){
            position = n;
        } else {
            position = document.getLength();
        }
    }
}