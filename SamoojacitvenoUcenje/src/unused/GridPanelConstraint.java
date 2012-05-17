/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unused;

/**
 *
 * @author Evgen
 */
import java.awt.Component  ;

public class GridPanelConstraint {
 
   int x, y, width, height, handle;
   Component component;
 
   public GridPanelConstraint(int x, int y, int width, int height, 
                              int handle, Component   component) {
     this.x = x;
     this.y = y;
     this.width = width;
     this.height = height;
     this.handle = handle;
     this.component = component;
   }
}
