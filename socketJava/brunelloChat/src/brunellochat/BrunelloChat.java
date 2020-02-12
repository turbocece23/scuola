/*
Brunello Cesare 5AI 2020

Ogni messaggio inviato avrà una sintassi simile
<id></id> //Identificativo dell'utente
<msg></msg> //Messaggio inviato in broadcast
<msg id=""></msg> //Inviato comunque in broadcast ma viene visualizzato in maniera differente da parte del ricevente

L'id viene lanciato più volte durante l'esecuzione del programma in modo tale da avvertire
tutti i presenti nella chat che il mio client è attivo e sta trasmettendo

Il programma deve spedire e inviare messaggi allo stesso tempo
è meglio tenere separato l'ambito di comunicazione e di rappresentazione del messaggio

Utilizzerà il protocoloo UDP spedendo i messaggi in 172.30.4.255 (broadcast)
https://www.baeldung.com/udp-in-java
 */
package brunellochat;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

public class BrunelloChat
{
    private static final int porta = 2345;
    private static final String hostname = "172.30.4.255";
    /* Serve per ignorare i messaggi inviati dal processo stesso che tornano indietro */
    private static final String scheda = "br0";
    
    public static String getIP() throws SocketException
    {
        Enumeration<NetworkInterface> schede = NetworkInterface.getNetworkInterfaces();  
        Enumeration<InetAddress> indirizziSchedeRete;
            
        for(NetworkInterface netint: Collections.list(schede))
        {
           if(netint.getName().equals(scheda))
           {
               indirizziSchedeRete = netint.getInetAddresses();
               int i=0;
               
               for(InetAddress inetAddress : Collections.list(indirizziSchedeRete))
               {
                   if(i==1)
                   {
                      return inetAddress.toString();
                   }
                   i++;
               }
           }
        }
        return null;
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException
    {
        DatagramSocket socket =new DatagramSocket(porta);
        InetAddress indirizzo = InetAddress.getByName(hostname);
        
        /* Controllo l'ip della scheda di rete così evito di ricevere i pacchetti che hanno come source questo ip */
        String ip = getIP();
        System.out.println("L'IP con cui ti presenti in rete e': " + ip.substring(1));
        /* substring serve per togliere il primo carattere che è uno / */
        
        sender invio = new sender(indirizzo, porta, socket);
        receiver ricevo = new receiver(socket, ip);
        gestore gestisco = new gestore(indirizzo, porta, socket);
        
        System.out.print("Benvenuto nel client di Gusella Michele\nScrivi q per uscire dal programma\n\n");
        Thread tinvio = new Thread(invio);
        Thread tricevo = new Thread(ricevo);
        Thread tgestisco = new Thread(gestisco);
        
        tgestisco.start();
        tinvio.start();
        tricevo.start();
    }
    
}
