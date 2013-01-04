package subwindows.explorer;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JTree;
public class Cfilesystem extends JPanel {
    private JTree tree;
    public Cfilesystem () {
        this(new Cfilesystemmodel());
    }

    public Cfilesystem (String startPath) {
        this(new Cfilesystemmodel(startPath));
    }

    public Cfilesystem (Cfilesystemmodel model) {
        tree = new JTree(model) {
            @Override
            public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                return ((File)value).getName();
            }
        };
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        this.setLayout(new BorderLayout());
        add(tree, BorderLayout.CENTER);
    }

    public JTree getTree() {
       return tree;
    }
}