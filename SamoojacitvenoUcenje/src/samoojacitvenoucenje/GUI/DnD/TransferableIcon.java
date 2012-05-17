/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI.DnD;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import javax.activation.ActivationDataFlavor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.TransferHandler;

/**
 *
 * @author Evgen
 */
public class TransferableIcon  implements Transferable {
    
    protected static DataFlavor imageIconFlavor = new DataFlavor(ImageIcon.class, "A ImageIcon Object");

    protected static DataFlavor[] supportedFlavors = {
        imageIconFlavor,
        DataFlavor.imageFlavor,
        DataFlavor.stringFlavor,
    };


    Icon icon;
    TransferHandler wrappedHandler;

    public TransferableIcon(Icon icon) { this.icon = icon; }
    
    public TransferableIcon(Icon icon, TransferHandler wrappedHandler) {
        this.icon = icon;
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() { return supportedFlavors; }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
    if (flavor.equals(imageIconFlavor) || 
        flavor.equals(DataFlavor.stringFlavor)) return true;
    return false;
  }


    @Override
   public Object getTransferData(DataFlavor flavor) 
        throws UnsupportedFlavorException
   {
     if (flavor.equals(imageIconFlavor))
         return icon;
     else if (flavor.equals(DataFlavor.stringFlavor)) 
         return icon.toString();
     else 
         throw new UnsupportedFlavorException(flavor);
   }

  public Icon getVisualRepresentation(Transferable t) {
    // This method is not currently (Java 1.4) used by Swing
    return wrappedHandler.getVisualRepresentation(t);
  }
    
}

