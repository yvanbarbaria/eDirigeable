/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dirigigeablecore;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpServer implements HttpHandler {

    public void handle(HttpExchange t) throws IOException 
    {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        if (t.getRequestURI().toString().startsWith("/netbeans-tomcat-status-test")) 
        {
            return;
        } 
        else 
        {     
            Gson gson = new GsonBuilder().create();
            DirigeableCommande request = gson.fromJson(buf.toString(), DirigeableCommande.class);
            
            if(request != null)
            {
                System.out.println("Reception d'une object JASON 'DirigeableCommande'");
                ProccessRequest(request ,t);
            }            
        }        
    }
    
    private DirigeableInfo UpdateDirigeableInfo(DirigeableInfo response)
    {
        response.altitude = DirigigeableCore.lastAltitude;
        response.longitude = DirigigeableCore.lastLongitude;
        response.latitude = DirigigeableCore.lastLatitude;
        
        response.ambiantTemperature = DirigigeableCore.lastAmbiantTemperature;
        response.measuredTemperature = DirigigeableCore.lastMeasuredTemperature;
        
        return response;
    }

    private void ProccessRequest(DirigeableCommande request, HttpExchange t) throws IOException 
    {
        DirigeableInfo response = new DirigeableInfo();   
        response = this.UpdateDirigeableInfo(response);
        
        if(request.cmdType.equalsIgnoreCase("GoToGPSPosition"))
        {            
            System.out.println("Reception de la commande 'GoToGPSPosition'");
            //TODO
        }
        else if(request.cmdType.equalsIgnoreCase("Forward"))
        {
            System.out.println("Reception de la commande 'Forward'");
            //TODO
        }
        else if(request.cmdType.equalsIgnoreCase("Left"))
        {
            System.out.println("Reception de la commande 'Left'");
            //TODO
        }
        else if(request.cmdType.equalsIgnoreCase("Right"))
        {
            System.out.println("Reception de la commande 'Right'");
            //TODO
        }
        else if(request.cmdType.equalsIgnoreCase("Back"))
        {
            System.out.println("Reception de la commande 'Back'");
            //TODO
        }
        else if(request.cmdType.equalsIgnoreCase("Up"))
        {
            System.out.println("Reception de la commande 'Up'");
            //TODO
        }
        else if(request.cmdType.equalsIgnoreCase("Down"))
        {
            System.out.println("Reception de la commande 'Down'");
            //TODO
        }
        else
        {
            System.out.println("Commande inconnue");
            response.message = "unknown command";
        }       
        
        Gson gson = new GsonBuilder().create();  
        String jsonResponse = gson.toJson(response);
        
        t.sendResponseHeaders(200, jsonResponse.length());
        OutputStream os = t.getResponseBody();
        System.out.println("Envoie de la réponse JASON");
        os.write(jsonResponse.getBytes());
        os.close();
    }
}