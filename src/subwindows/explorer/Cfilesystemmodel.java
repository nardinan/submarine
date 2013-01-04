package subwindows.explorer;
import java.io.File;
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
        String[] children = directory.list();
        return new File (directory, children[index]);
    }

    public int getChildCount (Object parent) {
        File fileSysEntity = (File)parent;
        if ( fileSysEntity.isDirectory() ) {
            String[] children = fileSysEntity.list();
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
        String[] children = directory.list();
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

