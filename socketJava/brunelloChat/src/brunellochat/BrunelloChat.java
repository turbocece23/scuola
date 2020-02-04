/*
Brunello Cesare 5AI 2020

Ogni messaggio inviato avrà una sintassi simile
<id></id> //Identificativo dell'utente
<msg></msg> //Messaggio inviato in broadcast
<msg id=""></msg> //Inviato comunque in broadcast ma viene visualizzato in maniera differente da parte del ricevente

Il programma deve spedire e inviare messaggi allo stesso tempo
è meglio tenere separato l'ambito di comunicazione e di rappresentazione del messaggio

Utilizzerà il protocoloo UDP spedendo i messaggi in 172.30.4.255 (broadcast)
https://www.baeldung.com/udp-in-java
 */
package brunellochat;

import java.io.*;
import java.net.*;

public class BrunelloChat {

    private String[] messaggio;
    //Porta del server in ascolto
    protected static int serverPort = 9090;
    
    public static void main(String[] args) throws Exception
    {
        DatagramSocket ssock = new DatagramSocket(serverPort);
        //Stampa di controllo
        System.out.println("Listening");
        
        String hostname = "172.30.4.9";

        InetAddress address = InetAddress.getByName(hostname);
        DatagramSocket socket = new DatagramSocket();

        InputStream istream;
        istream = ssock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        
        byte[] buffer = new byte[512];
        
        
        for (int i = 0; i < 256; i++) 
        {
            buffer[i]=1;
        }

        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, serverPort);
        socket.send(request);
        
        
    }
}
