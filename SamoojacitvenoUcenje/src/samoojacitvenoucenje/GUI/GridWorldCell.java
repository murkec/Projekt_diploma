/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Evgen
 */
public class GridWorldCell extends JLabel {
    
    private static final int CELL_WIDTH = 32;
    private static final int CELL_HEIGHT = 32;
    
    private int cellValue; // Empty

    public GridWorldCell() {
        super();
        
        
        this.setSize(CELL_WIDTH, CELL_HEIGHT);
        this.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        //this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));

    }

    public GridWorldCell(Icon image) {
        super(image);
        
        this.setSize(CELL_WIDTH, CELL_HEIGHT);
        this.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));

    }

    public GridWorldCell(String text) {
        super(text);
        
        this.setSize(CELL_WIDTH, CELL_HEIGHT);
        this.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    }
    
    public GridWorldCell(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        
        this.setSize(CELL_WIDTH, CELL_HEIGHT);
        this.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
    }

    @Override
    public Icon getIcon() {
        return super.getIcon();
    }

    /**
     * @return the cellValue
     */
    public int getCellValue() {
        return cellValue;
    }

    /**
     * @param cellValue the cellValue to set
     */
    public void setCellValue(int cellValue) {
        this.cellValue = cellValue;
    }
    
    /**
     * @param cellValue the cellValue to set
     */
    public void setCellValue(ImageIcon icon) {
        if(icon.toString().contains("health")) {
            this.cellValue = 10;
        } else if (icon.toString().contains("food")) {
            this.cellValue = 11;
        } else if (icon.toString().contains("time")) {
            this.cellValue = 12;
        } else if (icon.toString().contains("wall")) {
            this.cellValue = 13;
        } else if (icon.toString().contains("bomb")) {
           this.cellValue = 14;
        } else if (icon.toString().contains("water")) {
            this.cellValue = 15;
        } else if (icon.toString().contains("fire")) {
            this.cellValue = 16;
        } else if (icon.toString().contains("mushroom")) {
            this.cellValue = 17;
        } else if (icon.toString().contains("chili")) {
            this.cellValue = 18;
        } else if (icon.toString().contains("beer")) {
            this.cellValue = 19;
        } else if (icon.toString().contains("poison")) {
            this.cellValue = 20;
        } else {
            this.cellValue = 0;
        }
    }



}
