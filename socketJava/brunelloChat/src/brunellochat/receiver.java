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
    private final int dim = 64;
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
    
    // Metodo che associa il valore letto nel messaggio all'id
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
    
    //s è tutto il messaggio completo di tag
    private void controlla(String s, DatagramPacket pacchetto)
    {
        String contenuto;
        boolean esco = false;
        int posLibera = -1;
        
        /* Controlla il tag */
        if(check(s).equals("id"))
        {
            //prende una sottostringa che parte da 4 e prende il nome finché non incontra </id>
            //<id>mematore</id>
            contenuto = s.substring(4, s.indexOf("</id>"));
            //esco è la flag che controlla che l'indirizzo preso in analisi è stato trovato oppure no
            //ip trovato        esco = true
            //ip non trovato    esco = false
            for(int i = 0; i < dim && esco == false; i++)
            {
                //L'ip è gia contenuto nell'array
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)))
                {
                    //controlla l'id vecchio confrontandolo a quello nuovo
                    if(!id[i].equals(contenuto))
                    {
                        //Se è cambiato allora avverti che l'ip ha cambiato nome
                        System.out.println(id[i] + " ha cambiato username in " + contenuto);
                        id[i] = contenuto;
                    }
                    //ha trovato l'id
                    esco = true;
                }
                //Se un client nuovo si connette senza id e senza ip e c'è una posizione libera
                //associa alla posizione libera il valore del contatore i del ciclo for
                if(id[i].equals("") || ip[i].equals("") && posLibera == -1)
                {
                    posLibera = i;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco)
            {
                //Nell'array in posizione libera inserisci il conetnuto dell'id
                //(L'id è stato salvato prima nella stringa contenuto)
                id[posLibera] = contenuto;
                //Inserisci anche l'ip nell'array in posLibera prendendo l'indirizzo
                //da "pacchetto" (di tipo DatagramPacket, attuo a ricevere dati)
                ip[posLibera] = pacchetto.getAddress().toString().substring(1);
                //Siccome l'ip è nuovo e non è stato trovato salvato negli ip, allora avverti
                //che è un nuovo utente collegato ed è ora online
                System.out.println(contenuto + " e' online!");
            }
        }
        //controlla che nel messaggio sia contenuto un messaggio
        else if(check(s).equals("msg"))
        {
            //prendi il contenuto del messaggio, estraendo una sottostringa e prendendo tutto il contentuto del messaggio
            contenuto = s.substring(5, s.indexOf("</msg>"));
            for(int i = 0; i < dim && esco == false; i++)
            {
                //Viene trovato un indirizzo, e viene trovato un id "non vuoto" all'interno dell'array degli id
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)) && !id[i].equals(""))
                {
                    //Stampa id e contenuto
                    System.out.println(id[i] + ": " + contenuto);
                    //Esci dal ciclo
                    esco = true;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco)
            {
                //non ho trovato un ip, quindi qualcuno ha scritto nella chat senza un id valido
                //stampa null come id e il contenuto del messaggio
                System.out.println("null: " + contenuto);
            }
        }
        //nel caso in cui l'utente ha scritto il messaggio non usando i tag
        //noTag è una variabile di debug e all'interno del programma non viene modificata
        //perché serve solo a visualizzare i messaggi che non usano i tag
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
        DatagramPacket ricevopacchetto = new DatagramPacket(ricevo, ricevo.length);
        
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