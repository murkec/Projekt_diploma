/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

import java.awt.datatransfer.FlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import samoojacitvenoucenje.SamoojacitvenoUcenjeView;

/**
 *
 * @author Evgen
 */
public class ImageIconTargetListener extends DropTargetAdapter {
    private DropTarget dropTarget;
    private JLabel label = null;
    private GridWorldCell gridCell = null;
    SamoojacitvenoUcenjeView outer;

    public ImageIconTargetListener(JLabel label, SamoojacitvenoUcenjeView outer) {
        this.outer = outer;
        this.label = label;
       
        dropTarget = new DropTarget(label, DnDConstants.ACTION_COPY, this, true, null);
    }
    
    public ImageIconTargetListener(GridWorldCell gridCell, SamoojacitvenoUcenjeView outer) {
        this.outer = outer;
        this.gridCell = gridCell;
        
        dropTarget = new DropTarget(gridCell, DnDConstants.ACTION_COPY, this, true, null);
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        try {
            Transferable tr = event.getTransferable();
            ImageIcon imageIcon = (ImageIcon) tr.getTransferData(TransferableIcon.imageIconFlavor);
            if (event.isDataFlavorSupported(TransferableIcon.imageIconFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                
                if(label != null)
                    label.setIcon(imageIcon);
                else if(gridCell != null)
                    gridCell.setIcon(imageIcon);

                event.dropComplete(true);
                
                return;
            }
            event.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            event.rejectDrop();
        }
    }
    
}
