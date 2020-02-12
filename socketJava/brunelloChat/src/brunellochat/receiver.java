/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brunellochat;

import java.net.*;

public class receiver implements Runnable
{
    // Dimensione del buffer
    private final int dim = 32;
    // L'ip di questo pc in rete, non il localhost
    private final String iplocale;
    private boolean attivo = true;
    // Di default false, stampa anche i messaggi senza tag
    private final boolean noTag = true;
    
    private String msg;
    private String[] ip = new String[dim];
    private String[] id = new String[dim];
    private final DatagramSocket server;
    
    // Metodo costruttore
    public receiver(DatagramSocket socket, String ip)
    {
        this.server = socket;
        this.iplocale = ip;
    }
    
    // Metodo che associa il valore letto all'id
    private String check(String s)
    {
        if(s.charAt(0) == '<')
        {
            if(s.charAt(1) == 'i' && s.charAt(2) == 'd' && s.charAt(3) == '>')
                return "id";
            if(s.charAt(1) == 'm' && s.charAt(2) == 's' && s.charAt(3) == 'g' && s.charAt(4) == '>')
                return "msg";
        }
        return "errore";
    }
    
    private void controlla(String s, DatagramPacket pacchetto)
    {
        String contenuto;
        boolean esco = false;
        int posLibera = -1;
        
        /* Controlla il tag */
        if(check(s).equals("id"))
        {
            contenuto = s.substring(4, s.indexOf("</id>"));
            for(int i = 0; i < dim && esco == false; i++)
            {
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)))
                {
                    if(!id[i].equals(contenuto))
                    {
                        System.out.println(id[i] + " ha cambiato username in " + contenuto);
                        id[i] = contenuto;
                    }
                    esco = true;
                }
                if(id[i].equals("") || ip[i].equals("") && posLibera == -1)
                {
                    posLibera = i;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco)
            {
                id[posLibera] = contenuto;
                ip[posLibera] = pacchetto.getAddress().toString().substring(1);
                System.out.println(contenuto + " e' online!");
            }
        }
        else if(check(s).equals("msg"))
        {
            contenuto = s.substring(5, s.indexOf("</msg>"));
            for(int i = 0; i < dim && esco == false; i++)
            {
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)) && !id[i].equals(""))
                {
                    System.out.println(id[i] + ": " + contenuto);
                    esco = true;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco)
            {
                System.out.println("null: " + contenuto);
            }
        }
        else if(noTag)
        {
            for(int i = 0; i < dim && esco == false; i++)
            {
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)) && !id[i].equals(""))
                {
                    System.out.println(id[i] + "->noTag: " + s); 
                    esco = true;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco)
            {
                System.out.println("null->noTag: " + s);
            }
        }
    }
    
    @Override
    public void run()
    {
        byte[] ricevo =new byte[dim];
        DatagramPacket ricevopacchetto =new DatagramPacket(ricevo, ricevo.length);
        
        /* Inizializzo gli array che contengono id e ip */
        for(int i = 0; i < dim; i++)
        {
            ip[i] = "";
            id[i] = "";
        }
        
        try
        {            
            while(attivo)
            {
                /* Pulisco il buffer */
                for (int i = 0; i < dim; i++)
                    ricevo[i] = 0;
                server.receive(ricevopacchetto);
                
                /* Se il pacchetto non è stato inviato da questo host */
                if(!ricevopacchetto.getAddress().toString().equals(iplocale))
                {
                    msg =new String(ricevopacchetto.getData());
                    controlla(msg, ricevopacchetto);
                }
            }
        } catch(Exception e)
        {
            System.out.println("Ricevitore: " + e);
            attivo = false;
        }
    }
}