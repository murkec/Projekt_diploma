/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.episode;

import java.io.BufferedReader;
import java.io.FileReader;
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
    
    public int GetFirstEpisodeValue() {
        int result = -1;
        
        try {
            FileReader fileReader = new FileReader(resourceMap.getString("episode.path"));
            BufferedReader buffer = new BufferedReader(fileReader);
           
            result = Integer.parseInt(buffer.readLine());
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Episode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public double GetAllEpisodesValue() {
        double result = -1;
        
        try {
            FileReader fileReader = new FileReader(resourceMap.getString("episode.path"));
            BufferedReader buffer = new BufferedReader(fileReader);
           
            int countEpisodes = 0;
            double wholeSum = 0;
            
            String currEpisode = "";
            while((currEpisode = buffer.readLine()) != null)
            {
                wholeSum += Integer.parseInt(currEpisode);
                countEpisodes++;
            }
            
            result = wholeSum / (double)countEpisodes;
            
        } catch (IOException ex) {
            Logger.getLogger(Episode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;    
    }
    
    
}
