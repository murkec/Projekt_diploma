/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import samoojacitvenoucenje.GUI.DnD.TransferableIcon;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import samoojacitvenoucenje.SamoojacitvenoUcenjeView;
import java.io.FileReader;
import javax.swing.border.LineBorder;


/**
 *
 * @author Evgen
 */
public class GridWorldPanel extends JPanel implements DragGestureListener {
    
    private static final int PANEL_WIDTH = 940;
    private static final int PANEL_HEIGHT = 640;
    
    private static final int ROWS = 20;
    private static final int COLUMNS = 30;
    
    private Point characterPointPosition = new Point(0, 0);
    private int characterIndexPosition = 0;
    
    private int[][] gridWorldMatrix;

    
    public void changeCharacterPosition(int position) {     
        GridWorldCell character = (GridWorldCell)this.getComponent(characterIndexPosition);
        int x = 0;
        int y = 0;
            

        if(position == 0) {         // UP
            y = -1;
        } else if(position == 1) {  // RIGHT
            x = 1;
        } else if(position == 2) {  // DOWN
            y = 1;
        } else if(position == 3) {  // LEFT
            x = -1;
        }
        
        if(x != 0 && characterPointPosition.x + x >= 0 && characterPointPosition.x + x < COLUMNS) {         
            // update character position
            characterPointPosition.x += x;
            characterIndexPosition += x;   
            
            // reset current image icon
            character.setIcon(null);
            // get nextPosition component
            GridWorldCell moveCharacter = (GridWorldCell)this.getComponent(characterIndexPosition);                  
            // interaction
            characterInteractWithEnvironment(moveCharacter.getCellValue());
                 
            if(x == 1) {            // RIGHT
               moveCharacter.setIcon(resourceMap.getIcon("characterLabel.icon.right"));          
            } else if(x == -1) {    // LEFT
                moveCharacter.setIcon(resourceMap.getIcon("characterLabel.icon.left"));
            }
            
        }
        
        if(y != 0 && characterPointPosition.y + y >= 0 && characterPointPosition.y + y < ROWS) {
            // update character position
            characterPointPosition.y += y;
            characterIndexPosition += 30 * y;     
                  
            // reset current image icon
            character.setIcon(null);
            // get nextPosition component
            GridWorldCell moveCharacter = (GridWorldCell)this.getComponent(characterIndexPosition);
            // interaction
            characterInteractWithEnvironment(moveCharacter.getCellValue());
            
             if(y == 1) {           // DOWN
               moveCharacter.setIcon(resourceMap.getIcon("characterLabel.icon.down"));          
            } else if(y == -1) {    // UP
                moveCharacter.setIcon(resourceMap.getIcon("characterLabel.icon.up"));
            }
        }
    }
    
    private void characterInteractWithEnvironment(int nextCellValue) {
        GridCharacter character = SamoojacitvenoUcenjeView.getCharacter();
        
        switch(nextCellValue)
        {
            case 0: // Empty
                break;
            case 10: // Health
                character.addHealth(10);
                break;
            case 11: // Food
                character.addHunger(15);
                break;
            case 12: // Time
                break;
            case 13: // Wall
                break;
            case 14: // Bomb
                character.removeHealth(15);
                break;
            case 15: // Water
                character.addHunger(10);
                break;
            case 16: // Fire
                character.removeHealth(10);
                break;
            case 17: // Mushroom
                character.setSpeed(1500);
                break;                          
            case 18: // Chili
                break;
            case 19: // Beer
                character.setSpeed(1000);
                break;   
            case 20: // Poison
                break;
        }
        
        SamoojacitvenoUcenjeView.setCharacter(character);
    }
    
    //<editor-fold defaultstate="collapsed" desc="no vertical and horizontal gap fill">
    private GridLayout gridWorldLayout = new GridLayout(20, 30, 0, 0);
    //</editor-fold>
    private PropertyChangeSupport propertySupport;
    private ResourceMap resourceMap;
    private Color previousState = Color.black;

    public Color getPreviousState() {
        return previousState;
    }

    public void setPreviousState(Color previousState) {
        this.previousState = previousState;
    }
    
    public GridWorldPanel() {   
        //ResourceMap resourceMap = Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class);
        
        this.setLayout(gridWorldLayout);
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.setOpaque(false);
        
        resourceMap = Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class);
        
        gridWorldMatrix = new int[ROWS][COLUMNS];
        
        GridWorldCell currentCell;
        for(int i = 0; i < ROWS * COLUMNS; i++)
        {
            //currentCell = new GridWorldCell(" " + (i));
            currentCell = new GridWorldCell();
            

            //currentCell.setIcon(resourceMap.getIcon("gridcellLabel.icon")); // NOI18N
            

            this.add(currentCell);
            
            int cellWidth = currentCell.getWidth();
            int cellHeight = currentCell.getHeight();
        }
        
        propertySupport = new PropertyChangeSupport(this);   
        
 
    }

    
    public void changeGridWorld(String worldFilename) {  
        this.setOpaque(false);  
        
        FileReader fr = null;
        try {
            fr = new FileReader(worldFilename);
            BufferedReader br = new BufferedReader(fr);
            int character;
            String row;
            int cellCounter = 0;
            
            int rowCounter = 0;
            int columnCounter = 0;
            
            //while((character = br.read()))
            while((row = br.readLine()) != null)
            {
                
                String[] cell = row.split(" ");
                for (int i = 0; i < cell.length; i++) {
                    System.out.println(cell[i]);
                    int value = Integer.parseInt(cell[i]);
                    
                    gridWorldMatrix[rowCounter][columnCounter] = value;
                    
                    GridWorldCell currentCell = (GridWorldCell)this.getComponent(cellCounter);

                    currentCell.setIcon(null);
                                   

                    if(cellCounter == 0) { // Character - starting point
                        characterPointPosition = new Point(0, 0);
                        characterIndexPosition = 0;
                        
                        currentCell.setIcon(resourceMap.getIcon("characterLabel.icon.down"));
                        gridWorldMatrix[rowCounter][columnCounter] = 999;
                        currentCell.setCellValue(999);
                    }
                    else {
                        switch(value)
                        {
                            case 0: // Empty
                                break;
                            case 10: // Health
                                currentCell.setIcon(resourceMap.getIcon("healthLabel.icon"));
                                break;
                            case 11: // Food
                                currentCell.setIcon(resourceMap.getIcon("foodLabel.icon"));
                                break;
                            case 12: // Time
                                currentCell.setIcon(resourceMap.getIcon("timeLabel.icon"));
                                break;
                            case 13: // Wall
                                currentCell.setIcon(resourceMap.getIcon("wallLabel.icon"));
                                break;
                            case 14: // Bomb
                                currentCell.setIcon(resourceMap.getIcon("bombLabel.icon"));
                                break;
                            case 15: // Water
                                currentCell.setIcon(resourceMap.getIcon("waterLabel.icon"));
                                break;
                            case 16: // Fire
                                currentCell.setIcon(resourceMap.getIcon("fireLabel.icon"));
                                break;
                            case 17: // Mushroom
                                currentCell.setIcon(resourceMap.getIcon("mushroomLabel.icon"));
                                break;                          
                            case 18: // Chili
                                currentCell.setIcon(resourceMap.getIcon("chiliLabel.icon"));
                                break;
                            case 19: // Beer
                                currentCell.setIcon(resourceMap.getIcon("beerLabel.icon"));
                                break;   
                            case 20: // Poison
                                currentCell.setIcon(resourceMap.getIcon("poisonLabel.icon"));
                                break;


                        }
                        
                    
                        currentCell.setCellValue(value);
                    }
                    cellCounter++;
                    columnCounter++;
                    
                    if(columnCounter == COLUMNS - 1)
                        columnCounter = 0;
                }
                
                rowCounter++;
                
            }
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(GridWorldPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(GridWorldPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void resetGridWorld() {
        
        //this.setOpaque(true);
        //this.setBackground(new Color(240, 240, 240));

        /*
        int count = this.getComponentCount();
        GridWorldCell gwc;
        for (int i = 0; i < count; i++) {
            gwc = (GridWorldCell)this.getComponent(i);
            gwc.setIcon(null);
       }
       */
                      
    }
    
    
    @Override
    public Component getComponentAt(Point p) {
        int x = p.x;
        int y = p.y;
        Component c = getComponent(x * y);
        /*
        
        Component c = locate(30, 30);
        //return locate((x - 1) * 32, (y - 1) * 32);

        if (x == 1) { x = 2; }
        else if (x > 30) { x = 30; }
        
        if (y == 1) { y = 2; }
        else if (x > 20) { y = 20; }
        
        if(x > 0 && y > 0)
            c = locate(Math.abs(x * 32), Math.abs(y * 32));
        else if(x <= 0 && y > 1)
            c = locate(30, (y - 1) * 32);
        else if(x > 1 && y <= 0)
            c = locate((x - 1) * 32, 30);     
        */
        return c;
    }
    
    @Override
    public Component getComponentAt(int x, int y) {
        
        Component c = locate(30, 30);
        //return locate((x - 1) * 32, (y - 1) * 32);

        if (x == 1) { x = 2; }
        else if (x > 30) { x = 30; }
        
        if (y == 1) { y = 2; }
        else if (x > 20) { y = 20; }
        
        if(x > 0 && y > 0)
            c = locate(Math.abs((x - 1) * 32), Math.abs((y - 1) * 32));
        else if(x <= 0 && y > 1)
            c = locate(30, (y - 1) * 32);
        else if(x > 1 && y <= 0)
            c = locate((x - 1) * 32, 30);     
        
        return c;
    }
    
    @Override
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
  
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g); 
        //if (image != null)
        //  g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //GradientPaint gp = new GradientPaint(0, 0, getBackground().darker().darker(), getWidth(), getHeight(), getBackground().brighter().brighter());
        GradientPaint gp = new GradientPaint(0, 0, new Color(89, 159, 237), getWidth(), getHeight(), getBackground().brighter().brighter());
        
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        super.paintComponent(g);
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(border); 
        LineBorder lb = (LineBorder)border;
        
        if(lb != null) this.setPreviousState(lb.getLineColor());
        
    }
    
    
    
}
