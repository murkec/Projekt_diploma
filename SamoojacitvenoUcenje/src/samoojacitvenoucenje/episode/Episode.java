/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.episode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import samoojacitvenoucenje.SamoojacitvenoUcenjeView;

/**
 *
 * @author Evgen
 */
public class Episode {
    
    private ResourceMap resourceMap;
    private int episodeValue = 0;
    
    
    public Episode() {
        resourceMap = Application.getInstance(samoojacitvenoucenje.SamoojacitvenoUcenjeApp.class).getContext().getResourceMap(SamoojacitvenoUcenjeView.class); 
    }
    
    public int getEpisodeValue() {
        return episodeValue;
    }

    public void addEpisodeValue(int episodeValue) {
        this.episodeValue += episodeValue;
    }
    
    public void removeEpisodeValue(int episodeValue) {
        this.episodeValue -= episodeValue;
    }
    
    public void SaveEpisode() {
        try {
            FileWriter fileWrite = new FileWriter(resourceMap.getString("episode.path"), true);
            PrintWriter printWriter = new PrintWriter(fileWrite);
            
            printWriter.println(episodeValue);
            printWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Episode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
