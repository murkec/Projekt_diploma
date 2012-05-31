/*
 * SamoojacitvenoUcenjeView.java
 */

package samoojacitvenoucenje;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
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
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Random;
import javax.swing.Timer;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import samoojacitvenoucenje.GUI.GridWorldCell;
import samoojacitvenoucenje.GUI.DnD.ImageIconTargetListener;
import samoojacitvenoucenje.GUI.DnD.TransferableIcon;
import samoojacitvenoucenje.GUI.GridCharacter;
import samoojacitvenoucenje.episode.Episode;



/**
 * The application's main frame.
 */
public class SamoojacitvenoUcenjeView extends FrameView implements DragGestureListener, ActionListener {

    private static final int MAX_EPISODE_TIME = 600; // seconds
    private final int HUNGER_TIME = 5; // seconds
    private final int STOP_TIME = 5; // seconds
    
    private boolean isGameStarted = false;
    private boolean isFirstStart = true;
    private boolean hasGameEnded = false;
    private GridWorldCell characterLabel;
    private ResourceMap resourceMap;
    
    private static Episode currentEpisode;
    private static GridCharacter character; 
    private Timer gameTimer = new Timer(1000, this); // 1 sekund
    private int countFiveSecondsWall = 5;
    private int countFiveSecondsTime = 5;
    
    private static int secondsRemaining = MAX_EPISODE_TIME;

    public static void addSecondsRemaining(int addedSeconds) {
        SamoojacitvenoUcenjeView.secondsRemaining += addedSeconds;
    }

    public static void removeSecondsRemaining(int removedSeconds) {
        SamoojacitvenoUcenjeView.secondsRemaining -= removedSeconds;
    }

    public static Episode getCurrentEpisode() {
        return currentEpisode;
    }

    public static void setCurrentEpisode(Episode currentEpisode) {
        SamoojacitvenoUcenjeView.currentEpisode = currentEpisode;
    }

    public SamoojacitvenoUcenjeView(SingleFrameApplication app) {
        super(app);
        initComponents();
        resourceMap = Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class);
        
        
        // <editor-fold defaultstate="collapsed" desc="Health and Hunger Label Array">
        healthBarLabelArray = new JLabel[20];
        healthBarLabelArray[0] = healthBarLabel1;
        healthBarLabelArray[1] = healthBarLabel2;
        healthBarLabelArray[2] = healthBarLabel3;
        healthBarLabelArray[3] = healthBarLabel4;
        healthBarLabelArray[4] = healthBarLabel5;
        
        healthBarLabelArray[5] = healthBarLabel6;
        healthBarLabelArray[6] = healthBarLabel7;
        healthBarLabelArray[7] = healthBarLabel8;
        healthBarLabelArray[8] = healthBarLabel9;
        healthBarLabelArray[9] = healthBarLabel10;
        
        healthBarLabelArray[10] = healthBarLabel11;
        healthBarLabelArray[11] = healthBarLabel12;
        healthBarLabelArray[12] = healthBarLabel13;
        healthBarLabelArray[13] = healthBarLabel14;
        healthBarLabelArray[14] = healthBarLabel15;
        
        healthBarLabelArray[15] = healthBarLabel16;
        healthBarLabelArray[16] = healthBarLabel17;
        healthBarLabelArray[17] = healthBarLabel18;
        healthBarLabelArray[18] = healthBarLabel19;
        healthBarLabelArray[19] = healthBarLabel20;
        
        hungerBarLabelArray = new JLabel[20];
        hungerBarLabelArray[0] = hungerBarLabel1;
        hungerBarLabelArray[1] = hungerBarLabel2;
        hungerBarLabelArray[2] = hungerBarLabel3;
        hungerBarLabelArray[3] = hungerBarLabel4;
        hungerBarLabelArray[4] = hungerBarLabel5;
        
        hungerBarLabelArray[5] = hungerBarLabel6;
        hungerBarLabelArray[6] = hungerBarLabel7;
        hungerBarLabelArray[7] = hungerBarLabel8;
        hungerBarLabelArray[8] = hungerBarLabel9;
        hungerBarLabelArray[9] = hungerBarLabel10;
        
        hungerBarLabelArray[10] = hungerBarLabel11;
        hungerBarLabelArray[11] = hungerBarLabel12;
        hungerBarLabelArray[12] = hungerBarLabel13;
        hungerBarLabelArray[13] = hungerBarLabel14;
        hungerBarLabelArray[14] = hungerBarLabel15;
        
        hungerBarLabelArray[15] = hungerBarLabel16;
        hungerBarLabelArray[16] = hungerBarLabel17;
        hungerBarLabelArray[17] = hungerBarLabel18;
        hungerBarLabelArray[18] = hungerBarLabel19;
        hungerBarLabelArray[19] = hungerBarLabel20;
        // </editor-fold>
        
        
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
        
        // show value of last episode
        Episode tempEpisode = new Episode();
        int lastEpisodeValue = tempEpisode.GetFirstEpisodeValue();         
        if(lastEpisodeValue == -1) {
            lastEpisodeValueLabel.setText("Ni še vrednosti.");
        }
        else {
            lastEpisodeValueLabel.setText(String.valueOf(lastEpisodeValue));
            allEpisodesValueLabel.setText(String.valueOf(tempEpisode.GetAllEpisodesValue()));
        }

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
        startButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        healthBarLabel1 = new javax.swing.JLabel();
        healthBarLabel2 = new javax.swing.JLabel();
        healthBarLabel3 = new javax.swing.JLabel();
        healthBarLabel4 = new javax.swing.JLabel();
        healthBarLabel5 = new javax.swing.JLabel();
        healthBarLabel6 = new javax.swing.JLabel();
        healthBarLabel7 = new javax.swing.JLabel();
        healthBarLabel8 = new javax.swing.JLabel();
        healthBarLabel9 = new javax.swing.JLabel();
        healthBarLabel10 = new javax.swing.JLabel();
        healthBarLabel11 = new javax.swing.JLabel();
        healthBarLabel12 = new javax.swing.JLabel();
        healthBarLabel13 = new javax.swing.JLabel();
        healthBarLabel14 = new javax.swing.JLabel();
        healthBarLabel15 = new javax.swing.JLabel();
        healthBarLabel16 = new javax.swing.JLabel();
        healthBarLabel17 = new javax.swing.JLabel();
        healthBarLabel18 = new javax.swing.JLabel();
        healthBarLabel19 = new javax.swing.JLabel();
        healthBarLabel20 = new javax.swing.JLabel();
        hungerBarLabel1 = new javax.swing.JLabel();
        hungerBarLabel2 = new javax.swing.JLabel();
        hungerBarLabel3 = new javax.swing.JLabel();
        hungerBarLabel4 = new javax.swing.JLabel();
        hungerBarLabel5 = new javax.swing.JLabel();
        hungerBarLabel6 = new javax.swing.JLabel();
        hungerBarLabel7 = new javax.swing.JLabel();
        hungerBarLabel8 = new javax.swing.JLabel();
        hungerBarLabel9 = new javax.swing.JLabel();
        hungerBarLabel10 = new javax.swing.JLabel();
        hungerBarLabel11 = new javax.swing.JLabel();
        hungerBarLabel12 = new javax.swing.JLabel();
        hungerBarLabel13 = new javax.swing.JLabel();
        hungerBarLabel14 = new javax.swing.JLabel();
        hungerBarLabel15 = new javax.swing.JLabel();
        hungerBarLabel16 = new javax.swing.JLabel();
        hungerBarLabel17 = new javax.swing.JLabel();
        hungerBarLabel18 = new javax.swing.JLabel();
        hungerBarLabel19 = new javax.swing.JLabel();
        hungerBarLabel20 = new javax.swing.JLabel();
        timeBarLabel = new javax.swing.JLabel();
        lastEpisodeLabel = new javax.swing.JLabel();
        lastEpisodeValueLabel = new javax.swing.JLabel();
        allEpisodesLabel = new javax.swing.JLabel();
        allEpisodesValueLabel = new javax.swing.JLabel();
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
        foodLabel.setToolTipText(resourceMap.getString("foodLabel.toolTipText")); // NOI18N
        foodLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        foodLabel.setName("foodLabel"); // NOI18N

        timeLabel.setIcon(resourceMap.getIcon("timeLabel.icon")); // NOI18N
        timeLabel.setText(resourceMap.getString("timeLabel.text")); // NOI18N
        timeLabel.setToolTipText(resourceMap.getString("timeLabel.toolTipText")); // NOI18N
        timeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        timeLabel.setName("timeLabel"); // NOI18N

        wallLabel.setIcon(resourceMap.getIcon("wallLabel.icon")); // NOI18N
        wallLabel.setToolTipText(resourceMap.getString("wallLabel.toolTipText")); // NOI18N
        wallLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        wallLabel.setName("wallLabel"); // NOI18N

        bombLabel.setIcon(resourceMap.getIcon("bombLabel.icon")); // NOI18N
        bombLabel.setText(resourceMap.getString("bombLabel.text")); // NOI18N
        bombLabel.setToolTipText(resourceMap.getString("bombLabel.toolTipText")); // NOI18N
        bombLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bombLabel.setName("bombLabel"); // NOI18N

        waterLabel.setIcon(resourceMap.getIcon("waterLabel.icon")); // NOI18N
        waterLabel.setText(resourceMap.getString("waterLabel.text")); // NOI18N
        waterLabel.setToolTipText(resourceMap.getString("waterLabel.toolTipText")); // NOI18N
        waterLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        waterLabel.setName("waterLabel"); // NOI18N

        fireLabel.setIcon(resourceMap.getIcon("fireLabel.icon")); // NOI18N
        fireLabel.setText(resourceMap.getString("fireLabel.text")); // NOI18N
        fireLabel.setToolTipText(resourceMap.getString("fireLabel.toolTipText")); // NOI18N
        fireLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fireLabel.setName("fireLabel"); // NOI18N

        mushroomLabel.setIcon(resourceMap.getIcon("mushroomLabel.icon")); // NOI18N
        mushroomLabel.setText(resourceMap.getString("mushroomLabel.text")); // NOI18N
        mushroomLabel.setToolTipText(resourceMap.getString("mushroomLabel.toolTipText")); // NOI18N
        mushroomLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mushroomLabel.setName("mushroomLabel"); // NOI18N

        chiliLabel.setIcon(resourceMap.getIcon("chiliLabel.icon")); // NOI18N
        chiliLabel.setText(resourceMap.getString("chiliLabel.text")); // NOI18N
        chiliLabel.setToolTipText(resourceMap.getString("chiliLabel.toolTipText")); // NOI18N
        chiliLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chiliLabel.setName("chiliLabel"); // NOI18N

        beerLabel.setIcon(resourceMap.getIcon("beerLabel.icon")); // NOI18N
        beerLabel.setText(resourceMap.getString("beerLabel.text")); // NOI18N
        beerLabel.setToolTipText(resourceMap.getString("beerLabel.toolTipText")); // NOI18N
        beerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        beerLabel.setName("beerLabel"); // NOI18N

        poisonLabel.setIcon(resourceMap.getIcon("poisonLabel.icon")); // NOI18N
        poisonLabel.setText(resourceMap.getString("poisonLabel.text")); // NOI18N
        poisonLabel.setToolTipText(resourceMap.getString("poisonLabel.toolTipText")); // NOI18N
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

        startButton.setText(resourceMap.getString("startButton.text")); // NOI18N
        startButton.setName("startButton"); // NOI18N
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startButtonMouseClicked(evt);
            }
        });

        resetButton.setText(resourceMap.getString("resetButton.text")); // NOI18N
        resetButton.setEnabled(false);
        resetButton.setMaximumSize(new java.awt.Dimension(57, 23));
        resetButton.setMinimumSize(new java.awt.Dimension(57, 23));
        resetButton.setName("resetButton"); // NOI18N
        resetButton.setPreferredSize(new java.awt.Dimension(57, 23));
        resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetButtonMouseClicked(evt);
            }
        });

        healthBarLabel1.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel1.setText(resourceMap.getString("healthBarLabel1.text")); // NOI18N
        healthBarLabel1.setName("healthBarLabel1"); // NOI18N

        healthBarLabel2.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel2.setName("healthBarLabel2"); // NOI18N

        healthBarLabel3.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel3.setName("healthBarLabel3"); // NOI18N

        healthBarLabel4.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel4.setName("healthBarLabel4"); // NOI18N

        healthBarLabel5.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel5.setName("healthBarLabel5"); // NOI18N

        healthBarLabel6.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel6.setName("healthBarLabel6"); // NOI18N

        healthBarLabel7.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel7.setName("healthBarLabel7"); // NOI18N

        healthBarLabel8.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel8.setName("healthBarLabel8"); // NOI18N

        healthBarLabel9.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel9.setName("healthBarLabel9"); // NOI18N

        healthBarLabel10.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel10.setName("healthBarLabel10"); // NOI18N

        healthBarLabel11.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel11.setName("healthBarLabel11"); // NOI18N

        healthBarLabel12.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel12.setName("healthBarLabel12"); // NOI18N

        healthBarLabel13.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel13.setName("healthBarLabel13"); // NOI18N

        healthBarLabel14.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel14.setName("healthBarLabel14"); // NOI18N

        healthBarLabel15.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel15.setName("healthBarLabel15"); // NOI18N

        healthBarLabel16.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel16.setName("healthBarLabel16"); // NOI18N

        healthBarLabel17.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel17.setName("healthBarLabel17"); // NOI18N

        healthBarLabel18.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel18.setName("healthBarLabel18"); // NOI18N

        healthBarLabel19.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel19.setName("healthBarLabel19"); // NOI18N

        healthBarLabel20.setIcon(resourceMap.getIcon("healthBarLabel1.icon")); // NOI18N
        healthBarLabel20.setName("healthBarLabel20"); // NOI18N

        hungerBarLabel1.setIcon(resourceMap.getIcon("hungerBarLabel1.icon")); // NOI18N
        hungerBarLabel1.setName("hungerBarLabel1"); // NOI18N

        hungerBarLabel2.setIcon(resourceMap.getIcon("hungerBarLabel2.icon")); // NOI18N
        hungerBarLabel2.setName("hungerBarLabel2"); // NOI18N

        hungerBarLabel3.setIcon(resourceMap.getIcon("hungerBarLabel3.icon")); // NOI18N
        hungerBarLabel3.setName("hungerBarLabel3"); // NOI18N

        hungerBarLabel4.setIcon(resourceMap.getIcon("hungerBarLabel4.icon")); // NOI18N
        hungerBarLabel4.setName("hungerBarLabel4"); // NOI18N

        hungerBarLabel5.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel5.setName("hungerBarLabel5"); // NOI18N

        hungerBarLabel6.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel6.setName("hungerBarLabel6"); // NOI18N

        hungerBarLabel7.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel7.setName("hungerBarLabel7"); // NOI18N

        hungerBarLabel8.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel8.setName("hungerBarLabel8"); // NOI18N

        hungerBarLabel9.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel9.setName("hungerBarLabel9"); // NOI18N

        hungerBarLabel10.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel10.setName("hungerBarLabel10"); // NOI18N

        hungerBarLabel11.setIcon(resourceMap.getIcon("hungerBarLabel8.icon")); // NOI18N
        hungerBarLabel11.setName("hungerBarLabel11"); // NOI18N

        hungerBarLabel12.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel12.setName("hungerBarLabel12"); // NOI18N

        hungerBarLabel13.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel13.setName("hungerBarLabel13"); // NOI18N

        hungerBarLabel14.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel14.setName("hungerBarLabel14"); // NOI18N

        hungerBarLabel15.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel15.setName("hungerBarLabel15"); // NOI18N

        hungerBarLabel16.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel16.setName("hungerBarLabel16"); // NOI18N

        hungerBarLabel17.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel17.setName("hungerBarLabel17"); // NOI18N

        hungerBarLabel18.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel18.setName("hungerBarLabel18"); // NOI18N

        hungerBarLabel19.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel19.setName("hungerBarLabel19"); // NOI18N

        hungerBarLabel20.setIcon(resourceMap.getIcon("hungerBarLabel13.icon")); // NOI18N
        hungerBarLabel20.setName("hungerBarLabel20"); // NOI18N

        timeBarLabel.setFont(resourceMap.getFont("timeBarLabel.font")); // NOI18N
        timeBarLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeBarLabel.setText(resourceMap.getString("timeBarLabel.text")); // NOI18N
        timeBarLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        timeBarLabel.setName("timeBarLabel"); // NOI18N

        lastEpisodeLabel.setFont(resourceMap.getFont("lastEpisodeLabel.font")); // NOI18N
        lastEpisodeLabel.setText(resourceMap.getString("lastEpisodeLabel.text")); // NOI18N
        lastEpisodeLabel.setName("lastEpisodeLabel"); // NOI18N

        lastEpisodeValueLabel.setFont(resourceMap.getFont("lastEpisodeLabel.font")); // NOI18N
        lastEpisodeValueLabel.setText(resourceMap.getString("lastEpisodeValueLabel.text")); // NOI18N
        lastEpisodeValueLabel.setName("lastEpisodeValueLabel"); // NOI18N

        allEpisodesLabel.setFont(resourceMap.getFont("allEpisodesLabel.font")); // NOI18N
        allEpisodesLabel.setText(resourceMap.getString("allEpisodesLabel.text")); // NOI18N
        allEpisodesLabel.setName("allEpisodesLabel"); // NOI18N

        allEpisodesValueLabel.setFont(resourceMap.getFont("allEpisodesValueLabel.font")); // NOI18N
        allEpisodesValueLabel.setText(resourceMap.getString("allEpisodesValueLabel.text")); // NOI18N
        allEpisodesValueLabel.setName("allEpisodesValueLabel"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(hungerBarLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBarLabel20)
                        .addGap(36, 36, 36)
                        .addComponent(timeBarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(healthBarLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthBarLabel20))
                    .addComponent(gridWorldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(resetButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(405, 405, 405))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(lastEpisodeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastEpisodeValueLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(allEpisodesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allEpisodesValueLabel)))
                .addGap(405, 405, 405))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hungerBarLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerBarLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(healthBarLabel20)
                            .addComponent(healthBarLabel19)
                            .addComponent(healthBarLabel18)
                            .addComponent(healthBarLabel17)
                            .addComponent(healthBarLabel16)
                            .addComponent(healthBarLabel7)
                            .addComponent(healthBarLabel2)
                            .addComponent(healthBarLabel3)
                            .addComponent(healthBarLabel4)
                            .addComponent(healthBarLabel5)
                            .addComponent(healthBarLabel6)
                            .addComponent(healthBarLabel8)
                            .addComponent(healthBarLabel9)
                            .addComponent(healthBarLabel10)
                            .addComponent(healthBarLabel11)
                            .addComponent(healthBarLabel12)
                            .addComponent(healthBarLabel13)
                            .addComponent(healthBarLabel14)
                            .addComponent(healthBarLabel15)
                            .addComponent(healthBarLabel1)))
                    .addComponent(timeBarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(allEpisodesLabel)
                            .addComponent(allEpisodesValueLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastEpisodeLabel)
                            .addComponent(lastEpisodeValueLabel)))
                    .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gridWorldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(117, Short.MAX_VALUE))
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
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1983, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1963, Short.MAX_VALUE)
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

    private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseClicked
        // TODO add your handling code here:
        gridworldMenuItem.setEnabled(false);
        
        if(hasGameEnded == true) {
            resetButtonMouseClicked(evt);
            hasGameEnded = false;
        }
        
        if(isGameStarted == false) {
            startButton.setText("Pause");
            resetButton.setEnabled(true);        
            isGameStarted = true; 
            
            if(isFirstStart) {
                currentEpisode = new Episode();
                character = new GridCharacter();
                characterLabel = new GridWorldCell(resourceMap.getIcon("charaterLabel.icon"));

                isFirstStart = false;
               
            }

            gameTimer.start();
        }
        else {
            startButton.setText("Start");     
            isGameStarted = false;
            gameTimer.stop();
        }
    }//GEN-LAST:event_startButtonMouseClicked

    
    private void resetButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetButtonMouseClicked
        // TODO add your handling code here:
        gameTimer.stop();
        
        startButton.setText("Start");
        resetButton.setEnabled(false);
        
        isGameStarted = false;
        isFirstStart = true;
        gridworldMenuItem.setEnabled(true);
        

        character.addHealth(GridCharacter.HEALTH_MAX);
        character.addHunger(GridCharacter.HUNGER_MAX);
        
        character.hasHealthChanged = true;
        character.hasHungerChanged = true;
        
        actionPerformed(null);
        
        secondsRemaining = MAX_EPISODE_TIME;
        timeBarLabel.setText("00:00");
        
        gridWorldPanel.resetGridWorld();
        
        
    }//GEN-LAST:event_resetButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel allEpisodesLabel;
    private javax.swing.JLabel allEpisodesValueLabel;
    private javax.swing.JLabel beerLabel;
    private javax.swing.JLabel bombLabel;
    private javax.swing.JLabel chiliLabel;
    private javax.swing.JLabel fireLabel;
    private javax.swing.JLabel foodLabel;
    private samoojacitvenoucenje.GUI.GridWorldPanel gridWorldPanel;
    private javax.swing.JMenuItem gridworldMenuItem;
    private javax.swing.JLabel healthBarLabel1;
    private javax.swing.JLabel healthBarLabel10;
    private javax.swing.JLabel healthBarLabel11;
    private javax.swing.JLabel healthBarLabel12;
    private javax.swing.JLabel healthBarLabel13;
    private javax.swing.JLabel healthBarLabel14;
    private javax.swing.JLabel healthBarLabel15;
    private javax.swing.JLabel healthBarLabel16;
    private javax.swing.JLabel healthBarLabel17;
    private javax.swing.JLabel healthBarLabel18;
    private javax.swing.JLabel healthBarLabel19;
    private javax.swing.JLabel healthBarLabel2;
    private javax.swing.JLabel healthBarLabel20;
    private javax.swing.JLabel healthBarLabel3;
    private javax.swing.JLabel healthBarLabel4;
    private javax.swing.JLabel healthBarLabel5;
    private javax.swing.JLabel healthBarLabel6;
    private javax.swing.JLabel healthBarLabel7;
    private javax.swing.JLabel healthBarLabel8;
    private javax.swing.JLabel healthBarLabel9;
    private javax.swing.JLabel healthLabel;
    private javax.swing.JLabel hungerBarLabel1;
    private javax.swing.JLabel hungerBarLabel10;
    private javax.swing.JLabel hungerBarLabel11;
    private javax.swing.JLabel hungerBarLabel12;
    private javax.swing.JLabel hungerBarLabel13;
    private javax.swing.JLabel hungerBarLabel14;
    private javax.swing.JLabel hungerBarLabel15;
    private javax.swing.JLabel hungerBarLabel16;
    private javax.swing.JLabel hungerBarLabel17;
    private javax.swing.JLabel hungerBarLabel18;
    private javax.swing.JLabel hungerBarLabel19;
    private javax.swing.JLabel hungerBarLabel2;
    private javax.swing.JLabel hungerBarLabel20;
    private javax.swing.JLabel hungerBarLabel3;
    private javax.swing.JLabel hungerBarLabel4;
    private javax.swing.JLabel hungerBarLabel5;
    private javax.swing.JLabel hungerBarLabel6;
    private javax.swing.JLabel hungerBarLabel7;
    private javax.swing.JLabel hungerBarLabel8;
    private javax.swing.JLabel hungerBarLabel9;
    private javax.swing.JLabel lastEpisodeLabel;
    private javax.swing.JLabel lastEpisodeValueLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel mushroomLabel;
    private javax.swing.JLabel poisonLabel;
    private javax.swing.JButton resetButton;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel timeBarLabel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JLabel wallLabel;
    private javax.swing.JLabel waterLabel;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;
    private JDialog gridworldBox;
    private DragSource dragSource;
    public static final String[] gridworldFilenames = new String[7];
    private final JLabel[] healthBarLabelArray; 
    private final JLabel[] hungerBarLabelArray;

    @Override
    public void actionPerformed(ActionEvent e) {
        Random randomMove = new Random(new Date().getTime());
        int rand = randomMove.nextInt(4);
        
        
        // Remove HungerBar every <countFiveSecondsTime> seconds - default is 5 seconds
        if(countFiveSecondsTime != 0) {
            countFiveSecondsTime--;
        }
        else {
            countFiveSecondsTime = HUNGER_TIME - 1;
            character.removeHunger(5);
            character.hasHungerChanged = true;
        }
        
        // Character has to cross the wall - default is 5 seconds
        if(character.isStopped() && countFiveSecondsWall != 1) {
            countFiveSecondsWall--;
        }
        else {
            countFiveSecondsWall = STOP_TIME;
            character.setIsStopped(false);
        }
        
        // Random movement when not stopped
        if( ! character.isStopped()) {
            gridWorldPanel.changeCharacterPosition(rand);
        }
        
        // Showing left time of the current episode
        Date tempDate = new Date(0, 0, 0, 0, 0, secondsRemaining);
        
        String secondsString = (String.valueOf(tempDate.getSeconds()).length() == 1) ? "0" + tempDate.getSeconds() : (String) String.valueOf(tempDate.getSeconds());
        String minutesString = (String.valueOf(tempDate.getMinutes()).length() == 1) ? "0" + tempDate.getMinutes() : (String) String.valueOf(tempDate.getMinutes());
        timeBarLabel.setText(minutesString + ":" + secondsString);
        secondsRemaining--;
        
        // Update HealthBar
        if(character.hasHealthChanged) {
            character.hasHealthChanged = false;
            
            int currentHealth = character.getHealth();
            
            for (int i = 0; i < GridCharacter.HEALTH_MAX / 5; i++) {

                JLabel currentHealthBarLabel = healthBarLabelArray[i];
                
                if(currentHealth / 5 > i) {
                    currentHealthBarLabel.setIcon(resourceMap.getIcon(("healthBarLabel.icon.full")));
                }
                else {
                    currentHealthBarLabel.setIcon(resourceMap.getIcon(("healthBarLabel.icon.empty")));
                }

            }
        }
        
        // Update HungerBar
        if(character.hasHungerChanged) {
            character.hasHungerChanged = false;
            
            int currentHunger = character.getHunger();
            
            for (int i = 0; i < GridCharacter.HUNGER_MAX / 5; i++) {

                if(currentHunger / 5 > i) {
                    hungerBarLabelArray[i].setIcon(resourceMap.getIcon(("hungerBarLabel.icon.full")));
                }
                else {
                    hungerBarLabelArray[i].setIcon(resourceMap.getIcon(("hungerBarLabel.icon.empty")));
                }

            }
        }
        
        // If character runs out of time
        // If character runs out of health
        // If character runs out of hunger
        // The episode ends
        if(secondsRemaining == -1 || character.getHealth() == 0 || character.getHunger() == 0) {
            gameTimer.stop();
            
            isFirstStart = true;
            isGameStarted = false;
            hasGameEnded = true;
            
            startButton.setText("Start");
            resetButton.setEnabled(false);
            
            currentEpisode.SaveEpisode();
            lastEpisodeValueLabel.setText(String.valueOf(currentEpisode.GetFirstEpisodeValue()));
            allEpisodesValueLabel.setText(String.valueOf(currentEpisode.GetAllEpisodesValue()));
        }
        

    }
    
    /**
     * @return the character
     */
    public static GridCharacter getCharacter() {
        return character;
    }
    
    

    /**
     * @param aCharacter the character to set
     */
    public static void setCharacter(GridCharacter aCharacter) {
        character = aCharacter;
    }
}
