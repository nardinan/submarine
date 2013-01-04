package subwindows.explorer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import javax.swing.tree.TreePath;
public class Cfilesystemmodel extends Ctreemodel implements Serializable {
    /* utilities */
    String root;
    /* end */
    public Cfilesystemmodel() {
        this(System.getProperty("user.home"));
    }

    public Cfilesystemmodel( String startPath ) {
        root = startPath;
    }

    public Object getRoot() {
        return new File(root);
    }

    public Object getChild( Object parent, int index ) {
        File directory = (File)parent;
        FilenameFilter hiddenFiles = new FilenameFilter() {
            public boolean accept(File file, String string) {
                boolean result = true;
                if (string.startsWith(".")) {
                    result = false;
                }
                return result;
            }
        };
        String[] children = directory.list(hiddenFiles);
        return new File (directory, children[index]);
    }

    public int getChildCount (Object parent) {
        File fileSysEntity = (File)parent;
        FilenameFilter hiddenFiles = new FilenameFilter() {
            public boolean accept(File file, String string) {
                boolean result = true;
                if (string.startsWith(".")) {
                    result = false;
                }
                return result;
            }
        };
        if ( fileSysEntity.isDirectory() ) {
            String[] children = fileSysEntity.list(hiddenFiles);
            return children.length;
        } else return 0;
    }

    public boolean isLeaf (Object node) {
        return ((File)node).isFile();
    }

    public void valueForPathChanged (TreePath path, Object newValue) {
    }

    public int getIndexOfChild (Object parent, Object child) {
        File directory = (File)parent;
        File fileSysEntity = (File)child;
        FilenameFilter hiddenFiles = new FilenameFilter() {
            public boolean accept(File file, String string) {
                boolean result = true;
                if (string.startsWith(".")) {
                    result = false;
                }
                return result;
            }
        };
        String[] children = directory.list(hiddenFiles);
        int result = -1;
        for (int index = 0; index < children.length; ++index ) {
            if (fileSysEntity.getName().equals(children[index])) {
                result = index;
                break;
            }
        }

        return result;
    }
}

