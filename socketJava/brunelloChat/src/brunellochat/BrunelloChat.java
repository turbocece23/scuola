/*
Brunello Cesare 5AI 2020

Ogni messaggio inviato avrà una sintassi simile
<id></id> Identificativo dell'utente
<msg></msg> Messaggio inviato in broadcast
<msg id=""></msg> Inviato comunque in broadcast ma viene visualizzato in maniera differente da parte del ricevente

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
    private static final int porta          = 2345;
    private static final String hostname    = "172.30.4.255";
    private static final String id          = "Besare";
    /* Serve per ignorare i messaggi inviati dal processo stesso che tornano indietro */
    private static final String scheda      = "br0";
    
    /* Funzione che a partire dalla lista di schede di rete dell'host, cattura l'ip scelto tramite la posizione */
    public static String getIP(int posizione) throws SocketException
    {
        /* schede è la "lista" di tutte le schede di rete di questo host */
        Enumeration<NetworkInterface> schede = NetworkInterface.getNetworkInterfaces();  
        /* indirizziSchedeRete è la "lista" di tutti gli ip (sia ipv4 sia ipv6) di una scheda di rete */
        Enumeration<InetAddress> indirizziSchedeRete;
        /* Per ogni scheda di rete */
        for(NetworkInterface netint: Collections.list(schede))
        {
            /* Se il nome della scheda della lista equivale alla variabile scheda (variabile della classe) */
           if(netint.getName().equals(scheda))
           {
               /* indirizziSchedeRete prende tutti gli ip della scheda di rete */
               indirizziSchedeRete = netint.getInetAddresses();
               /* Per ogni ip della scheda di rete */
               int i = 0;
               for(InetAddress inetAddress : Collections.list(indirizziSchedeRete))
               {
                   if(i == posizione)
                       return inetAddress.toString();
                   i++;
               }
           }
        }
        /* Non dovrebbe mai arrivare a questo punto */
        return null;
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException
    {
        /* Creo il socket e prendo l'ip partendo da un hostname */
        DatagramSocket socket =new DatagramSocket(porta);
        InetAddress indirizzo = InetAddress.getByName(hostname);
        
        /* Controllo l'ip della scheda di rete così evito di ricevere i pacchetti che hanno come source questo ip */
        String ip = getIP(1);
        System.out.println("L'IP con cui ti presenti in rete e': " + ip.substring(1));
        /* substring serve per togliere il primo carattere che è uno / */
        
        /* Creo i vari oggetti e i rispettivi thread che gestiscono il tutto */
        sender invio = new sender(indirizzo, porta, socket);
        receiver ricevo = new receiver(socket, ip, id);
        gestore gestisco = new gestore(indirizzo, porta, socket, id);
        
        System.out.print("Benvenuto nel client di Gusella Michele\nScrivi q per uscire dal programma\n\n");
        Thread tinvio = new Thread(invio);
        Thread tricevo = new Thread(ricevo);
        Thread tgestisco = new Thread(gestisco);
        
        tgestisco.start();
        tinvio.start();
        tricevo.start();
    }
    
}
