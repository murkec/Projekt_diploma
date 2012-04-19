/*
 * SamoojacitvenoUcenjeView.java
 */

package samoojacitvenoucenje;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import samoojacitvenoucenje.GUI.GridWorldCell;
import samoojacitvenoucenje.GUI.DnD.ImageIconTargetListener;
import samoojacitvenoucenje.GUI.DnD.TransferableIcon;



/**
 * The application's main frame.
 */
public class SamoojacitvenoUcenjeView extends FrameView implements DragGestureListener {
    
    public SamoojacitvenoUcenjeView(SingleFrameApplication app) {
        super(app);
        initComponents();
        ResourceMap resourceMap = Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class);
        
        
        for (int i = 0; i < gridworldFilenames.length; i++) {
            gridworldFilenames[i] = resourceMap.getString("gridworld.world[" + i + "]");
        }
        
        gridWorldPanel.changeGridWorld(gridworldFilenames[0]);
        
        

        getFrame().setUndecorated(true);
        getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
        getFrame().setTitle("Survivor!");
        getFrame().setVisible(true);
        
        for(int i = 0; i < gridWorldPanel.getComponentCount(); i++)  
            new ImageIconTargetListener((GridWorldCell)gridWorldPanel.getComponent(i), this);
        
        

        
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(healthLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(foodLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(timeLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(wallLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(bombLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(waterLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(fireLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(mushroomLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(chiliLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(beerLabel, DnDConstants.ACTION_COPY, this);
        dragSource.createDefaultDragGestureRecognizer(poisonLabel, DnDConstants.ACTION_COPY, this);
 
        mainPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        // DEBUGGING
        int width = gridWorldPanel.getWidth();
        int height = gridWorldPanel.getHeight();
        
        /*
        int cellx = 0;
        int celly = 5;
        GridWorldCell gwc = (GridWorldCell)gridWorldPanel.getComponentAt(cellx, celly);
        gwc.setOpaque(true);
        gwc.setBackground(Color.red);
        */


    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        
        JLabel label = (JLabel) event.getComponent();
        ImageIcon icon = (ImageIcon) label.getIcon();

        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        event.startDrag(cursor, new TransferableIcon(icon));
    }


    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SamoojacitvenoUcenjeApp.getApplication().getMainFrame();
            aboutBox = new SamoojacitvenoUcenjeAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SamoojacitvenoUcenjeApp.getApplication().show(aboutBox);
    }
    
    @Action
    public void showChangeGridworldBox() {
        if (gridworldBox == null) {
            JFrame mainFrame = SamoojacitvenoUcenjeApp.getApplication().getMainFrame();
            gridworldBox = new SamoojacitvenoUcenjeGridworldBox(mainFrame, true);
            gridworldBox.setLocationRelativeTo(mainFrame);

            gridworldBox.addWindowListener(new WindowAdapter() 
            {
                @Override
                public void windowClosed(WindowEvent e)
                {
                    System.out.println("jdialog window closed event received");
                    if(SamoojacitvenoUcenjeGridworldBox.wasConfirmed) {
                        gridWorldPanel.changeGridWorld(gridworldFilenames[SamoojacitvenoUcenjeGridworldBox.getSelectedConfirmedGridworld()]);
                    }
                }

                @Override
                public void windowClosing(WindowEvent e)
                {
                    System.out.println("jdialog window closing event received");
                    //gridworldBox.isco
                }
            });
        }
        SamoojacitvenoUcenjeApp.getApplication().show(gridworldBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        toolbarPanel = new javax.swing.JPanel();
        healthLabel = new javax.swing.JLabel();
        foodLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        wallLabel = new javax.swing.JLabel();
        bombLabel = new javax.swing.JLabel();
        waterLabel = new javax.swing.JLabel();
        fireLabel = new javax.swing.JLabel();
        mushroomLabel = new javax.swing.JLabel();
        chiliLabel = new javax.swing.JLabel();
        beerLabel = new javax.swing.JLabel();
        poisonLabel = new javax.swing.JLabel();
        gridWorldPanel = new samoojacitvenoucenje.GUI.GridWorldPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        gridworldMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1312, 932));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class);
        toolbarPanel.setBackground(resourceMap.getColor("toolbarPanel.background")); // NOI18N
        toolbarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbarPanel.setName("toolbarPanel"); // NOI18N

        healthLabel.setBackground(resourceMap.getColor("healthLabel.background")); // NOI18N
        healthLabel.setIcon(resourceMap.getIcon("healthLabel.icon")); // NOI18N
        healthLabel.setText(resourceMap.getString("healthLabel.text")); // NOI18N
        healthLabel.setToolTipText(resourceMap.getString("healthLabel.toolTipText")); // NOI18N
        healthLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        healthLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        healthLabel.setName("healthLabel"); // NOI18N

        foodLabel.setIcon(resourceMap.getIcon("foodLabel.icon")); // NOI18N
        foodLabel.setText(resourceMap.getString("foodLabel.text")); // NOI18N
        foodLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        foodLabel.setName("foodLabel"); // NOI18N

        timeLabel.setIcon(resourceMap.getIcon("timeLabel.icon")); // NOI18N
        timeLabel.setText(resourceMap.getString("timeLabel.text")); // NOI18N
        timeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        timeLabel.setName("timeLabel"); // NOI18N

        wallLabel.setIcon(resourceMap.getIcon("wallLabel.icon")); // NOI18N
        wallLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        wallLabel.setName("wallLabel"); // NOI18N

        bombLabel.setIcon(resourceMap.getIcon("bombLabel.icon")); // NOI18N
        bombLabel.setText(resourceMap.getString("bombLabel.text")); // NOI18N
        bombLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bombLabel.setName("bombLabel"); // NOI18N

        waterLabel.setIcon(resourceMap.getIcon("waterLabel.icon")); // NOI18N
        waterLabel.setText(resourceMap.getString("waterLabel.text")); // NOI18N
        waterLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        waterLabel.setName("waterLabel"); // NOI18N

        fireLabel.setIcon(resourceMap.getIcon("fireLabel.icon")); // NOI18N
        fireLabel.setText(resourceMap.getString("fireLabel.text")); // NOI18N
        fireLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fireLabel.setName("fireLabel"); // NOI18N

        mushroomLabel.setIcon(resourceMap.getIcon("mushroomLabel.icon")); // NOI18N
        mushroomLabel.setText(resourceMap.getString("mushroomLabel.text")); // NOI18N
        mushroomLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mushroomLabel.setName("mushroomLabel"); // NOI18N

        chiliLabel.setIcon(resourceMap.getIcon("chiliLabel.icon")); // NOI18N
        chiliLabel.setText(resourceMap.getString("chiliLabel.text")); // NOI18N
        chiliLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chiliLabel.setName("chiliLabel"); // NOI18N

        beerLabel.setIcon(resourceMap.getIcon("beerLabel.icon")); // NOI18N
        beerLabel.setText(resourceMap.getString("beerLabel.text")); // NOI18N
        beerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        beerLabel.setName("beerLabel"); // NOI18N

        poisonLabel.setIcon(resourceMap.getIcon("poisonLabel.icon")); // NOI18N
        poisonLabel.setText(resourceMap.getString("poisonLabel.text")); // NOI18N
        poisonLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        poisonLabel.setName("poisonLabel"); // NOI18N

        javax.swing.GroupLayout toolbarPanelLayout = new javax.swing.GroupLayout(toolbarPanel);
        toolbarPanel.setLayout(toolbarPanelLayout);
        toolbarPanelLayout.setHorizontalGroup(
            toolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(healthLabel)
                    .addComponent(foodLabel)
                    .addComponent(timeLabel)
                    .addComponent(wallLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bombLabel)
                    .addComponent(waterLabel)
                    .addComponent(fireLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mushroomLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chiliLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(beerLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(poisonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        toolbarPanelLayout.setVerticalGroup(
            toolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(healthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(foodLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wallLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bombLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fireLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mushroomLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chiliLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(beerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(poisonLabel)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        foodLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("foodLabel.AccessibleContext.accessibleName")); // NOI18N
        timeLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("timeLabel.AccessibleContext.accessibleName")); // NOI18N
        wallLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("wallLabel.AccessibleContext.accessibleName")); // NOI18N
        bombLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("bombLabel.AccessibleContext.accessibleName")); // NOI18N
        waterLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("waterLabel.AccessibleContext.accessibleName")); // NOI18N
        fireLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabel6.AccessibleContext.accessibleName")); // NOI18N
        mushroomLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("mushroomLabel.AccessibleContext.accessibleName")); // NOI18N
        chiliLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabel1.AccessibleContext.accessibleName")); // NOI18N
        beerLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("beerLabel.AccessibleContext.accessibleName")); // NOI18N
        poisonLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("poisonLabel.AccessibleContext.accessibleName")); // NOI18N

        gridWorldPanel.setMaximumSize(new java.awt.Dimension(960, 640));
        gridWorldPanel.setMinimumSize(new java.awt.Dimension(960, 640));
        gridWorldPanel.setName("gridWorldPanel"); // NOI18N
        gridWorldPanel.setOpaque(false);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gridWorldPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(548, 548, 548))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(toolbarPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gridWorldPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(148, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getActionMap(SamoojacitvenoUcenjeView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        settingsMenu.setText(resourceMap.getString("settingsMenu.text")); // NOI18N
        settingsMenu.setActionCommand(resourceMap.getString("settingsMenu.actionCommand")); // NOI18N
        settingsMenu.setName("settingsMenu"); // NOI18N

        gridworldMenuItem.setAction(actionMap.get("showChangeGridworldBox")); // NOI18N
        gridworldMenuItem.setText(resourceMap.getString("gridworldMenuItem.text")); // NOI18N
        gridworldMenuItem.setName("gridworldMenuItem"); // NOI18N
        settingsMenu.add(gridworldMenuItem);

        menuBar.add(settingsMenu);
        settingsMenu.getAccessibleContext().setAccessibleName(resourceMap.getString("settingsMenu.AccessibleContext.accessibleName")); // NOI18N

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1578, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1558, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel beerLabel;
    private javax.swing.JLabel bombLabel;
    private javax.swing.JLabel chiliLabel;
    private javax.swing.JLabel fireLabel;
    private javax.swing.JLabel foodLabel;
    private samoojacitvenoucenje.GUI.GridWorldPanel gridWorldPanel;
    private javax.swing.JMenuItem gridworldMenuItem;
    private javax.swing.JLabel healthLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel mushroomLabel;
    private javax.swing.JLabel poisonLabel;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JLabel wallLabel;
    private javax.swing.JLabel waterLabel;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;
    private JDialog gridworldBox;
    private DragSource dragSource;
    public static final String[] gridworldFilenames = new String[7];
}
