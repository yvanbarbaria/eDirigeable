/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dirigigeablecore;

import java.util.Date;

/**
 *
 * @author Manu
 */
public class DirigeableInfo 
{
    public DirigeableInfo()
    {
        dirigeableId = DirigigeableCore.dirigeableId;
    }
    
    public String dirigeableId = ""; //On prévoit le cas où plus tard le client dise "Oui mais si je veux pouvoir controler plusieur dirigeables..."	
    public Date d = new Date(); //Heure où les mesures ont été faites
	
    public String message = "DefaultMessage"; // Dans le cas ou j'envoie une commande, l'objet de retour me renvoie: OK, ERR#N: Moteur 2 HS, ....

    public double altitude = 0;
    public double longitude = 0;
    public double latitude = 0;
	
    public double ambiantTemperature = 0; //Facultatif
    public double measuredTemperature = 0; //Si le capteur est un peu plus loin, au bout d'un cable...
}
