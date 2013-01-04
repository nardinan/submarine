package subwindows.explorer;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
public class Cdirectorymodel extends AbstractTableModel {
    /* utilities */
    protected File directory;
    protected String[] children;
    protected int rowCount;
    protected Object dirIcon, fileIcon;
    /* end */
    public Cdirectorymodel () {
        initialize();
    }

    public Cdirectorymodel (File dir) {
        initialize();
        FilenameFilter a = new FilenameFilter() {
            public boolean accept(File file, String string) {
                boolean result = true;
                if (string.startsWith(".")) {
                    result = false;
                }
                return result;
            }
        };
        directory = dir;
        children = dir.list();
        rowCount = children.length;
    }

    public final void initialize () {
        dirIcon = UIManager.get("DirectoryPane.directoryIcon");
        fileIcon = UIManager.get("DirectoryPane.fileIcon");
    }

    public void setDirectory (File dir) {
        if (dir != null) {
            directory = dir;
            children = dir.list();
            rowCount = children.length;
        } else {
            directory = null;
            children = null;
            rowCount = 0;
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount () {
        return (children != null)?rowCount:0;
    }

    @Override
    public int getColumnCount () {
        return (children != null)?3:0;
    }

    @Override
    public Object getValueAt (int row, int column){
        if ((directory == null) || (children == null)) return null;
        File fileSysEntity = new File(directory, children[row]);
        switch (column) {
            case 0: return (fileSysEntity.isDirectory())?dirIcon:fileIcon;
            case 1: return fileSysEntity.getName();
            case 2:
                if (fileSysEntity.isDirectory()) return "--";
                else return new Long( fileSysEntity.length() );
            default: return "";
        }
    }

    @Override
    public String getColumnName (int column) {
        switch ( column ) {
            case 0: return "Type";
            case 1: return "Name";
            case 2: return "Bytes";
            default: return "unknown";
        }
    }

    @Override
    public Class getColumnClass (int column) {
        if (column == 0) return getValueAt(0, column).getClass();
        else return super.getColumnClass(column);
    }
}

