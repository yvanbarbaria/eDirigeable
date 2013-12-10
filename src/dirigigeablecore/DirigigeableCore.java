/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dirigigeablecore;

import com.phidgets.GPSPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.GPSPositionChangeEvent;
import com.phidgets.event.GPSPositionChangeListener;
import com.phidgets.event.GPSPositionFixStatusChangeListener;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

/**
 *
 * @author Manu
 */
public class DirigigeableCore 
{
    public static String dirigeableId = ""; //On prévoit le cas où plus tard le client dise "Oui mais si je veux pouvoir controler plusieur dirigeables..."	
    
    public static double lastAltitude = 0;
    public static double lastLongitude = 0;
    public static double lastLatitude = 0;

    public static double lastAmbiantTemperature = 0; //Facultatif
    public static double lastMeasuredTemperature = 0; //Si le capteur est un peu plus loin, au bout d'un cable...
    
    public static void main(String[] args) throws IOException, PhidgetException 
    {      
        //Initialize Dirigeable Id with mac adress
        System.out.println("Initialisation de l'id du Dirireable...");
        NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        byte[] mac = network.getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
        }
        dirigeableId = sb.toString();
        System.out.println("Initialisation faite avec la valeur : " + dirigeableId);
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080),10);
        server.createContext("/", new dirigigeablecore.HttpServer());
        server.setExecutor(null); // creates a default executor
        System.out.println("Lancement du serveur Http...");
        server.start();
        System.out.println("Serveur Http lancé");
        
        //GPS
        ErrorListener ErrorListener = null;
        GPSPositionFixStatusChangeListener GPSPositionFixStatusChangeListener = null;
        final GPSPhidget gps = new GPSPhidget();
        gps.openAny();
            
        System.out.println("Waiting for the Phidget GPS to be attached...");
        gps.waitForAttachment();
        
        gps.addGPSPositionChangeListener(new GPSPositionChangeListener() {

                public void gpsPositionChanged(GPSPositionChangeEvent gpspce) {
                    try 
                    {
                        System.out.println(gpspce.toString()
                                + ", Velocity: " + gps.getVelocity() + "km/h"
                                + ", Heading: " + gps.getHeading() + " degrees");
                        DirigigeableCore.lastLatitude = gps.getLatitude();
                        DirigigeableCore.lastLongitude = gps.getLongitude();
                        DirigigeableCore.lastAltitude = gps.getAltitude();
                    } 
                    catch (PhidgetException ex) 
                    {
                        System.out.println("\n--->Error: " + ex.getDescription());
                    }

                }

            });
        
        //Temperature sensor
        //TODO
    }
}
