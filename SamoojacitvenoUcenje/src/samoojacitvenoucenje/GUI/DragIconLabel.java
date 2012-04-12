/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Evgen
 */
public class DragIconLabel extends JLabel implements DragGestureListener {

    private static final int LABEL_WIDTH = 32;
    private static final int LABEL_HEIGHT = 32;
    
    public DragIconLabel() {
        this.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
    }

    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        
        JLabel label = (JLabel) event.getComponent();
        ImageIcon icon = (ImageIcon) label.getIcon();

        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        //cursor = label.getToolkit().createCustomCursor(icon.getImage(), new Point(0,0), "usr");
        event.startDrag(cursor, new TransferableIcon(icon));
    }

}
