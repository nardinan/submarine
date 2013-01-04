package subwindows.explorer;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
public class Ctreemodelsupport {
   /* utilities */
   private Vector vector = new Vector();
   /* end */
   public void addTreeModelListener (TreeModelListener listener) {
      if ( listener != null && !vector.contains( listener ) ) {
         vector.addElement( listener );
      }
   }

   public void removeTreeModelListener (TreeModelListener listener) {
      if ( listener != null ) {
         vector.removeElement( listener );
      }
   }

   public void fireTreeNodesChanged (TreeModelEvent event) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeNodesChanged(event);
      }
   }

   public void fireTreeNodesInserted (TreeModelEvent event) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeNodesInserted(event);
      }
   }

   public void fireTreeNodesRemoved (TreeModelEvent event) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeNodesRemoved(event);
      }
   }

   public void fireTreeStructureChanged (TreeModelEvent event) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeStructureChanged(event);
      }
   }
}