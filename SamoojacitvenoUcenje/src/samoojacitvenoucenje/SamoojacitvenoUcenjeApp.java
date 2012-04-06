/*
 * SamoojacitvenoUcenjeApp.java
 */

package samoojacitvenoucenje;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class SamoojacitvenoUcenjeApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new SamoojacitvenoUcenjeView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     * @param root 
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of SamoojacitvenoUcenjeApp
     */
    public static SamoojacitvenoUcenjeApp getApplication() {
        return Application.getInstance(SamoojacitvenoUcenjeApp.class);
    }

    /**
     * Main method launching the application.
     * @param args 
     */
    public static void main(String[] args) {
        launch(SamoojacitvenoUcenjeApp.class, args);
    }
}
