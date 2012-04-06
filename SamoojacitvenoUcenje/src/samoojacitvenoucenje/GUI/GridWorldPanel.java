/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import samoojacitvenoucenje.SamoojacitvenoUcenjeView;


/**
 *
 * @author Evgen
 */
public class GridWorldPanel extends JPanel implements Serializable {
    

    
    private static final int PANEL_WIDTH = 960;
    private static final int PANEL_HEIGHT = 640;
    
    private static final int ROWS = PANEL_HEIGHT / 32;
    private static final int COLUMNS = PANEL_WIDTH / 32;
    
    private GridLayout gridWorldLayout = new GridLayout(ROWS, COLUMNS, 0, 0);
    private PropertyChangeSupport propertySupport;

    Image image;

    
    public GridWorldPanel() {   
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class);
        this.setLayout(gridWorldLayout);
        
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        //this.setBackground(Color.WHITE);
  
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        
        GridWorldCell currentCell;
        
        try
        {
          image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("icons/gridBackground.png"), "icons/gridBackground.png"));
        }
        catch (Exception e) { /*handled in paintComponent()*/ }
              
        
        for(int i = 0; i < ROWS * COLUMNS; i++)
        {
            currentCell = new GridWorldCell("Label1");
            currentCell.setIcon(resourceMap.getIcon("gridcellLabel.icon")); // NOI18N
            this.add("Label1", currentCell);
        }
        
        propertySupport = new PropertyChangeSupport(this);   
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); 
        if (image != null)
          g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
    }
}
