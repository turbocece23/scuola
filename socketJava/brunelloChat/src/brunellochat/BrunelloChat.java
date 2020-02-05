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
import java.lang.*;
import java.util.*;

public class BrunelloChat {

    private String[] messaggio;
    //Porta del server in ascolto
    protected static int serverPort = 2345;
    
    public static void main(String[] args) throws Exception
    {
        DatagramSocket sock = new DatagramSocket(serverPort);
        
        //Questo è l'indirizzo di destinazione però sotto forma di stringa
        String stringaIndirizzo = "172.30.4.9";

        //Questa funzione converte la stringa in un indirizzo raggiungibile
        InetAddress indirizzo = InetAddress.getByName(stringaIndirizzo);
        
        StringBuffer bufferOUT = new StringBuffer();
        byte[] bufferIN = new byte[512];
        byte[] bufferOUT2 = new byte[512];
        
        for (int i = 0; i < 256; i++) 
        {
            bufferOUT2[i]='a';
        }
        
        //Invia i dati tramite un buffer
        DatagramPacket invia = new DatagramPacket(bufferOUT2, bufferOUT2.length, indirizzo, serverPort);
        sock.send(invia);
        
        bufferOUT.insert(0,"Saluto generico");

        byte[] bufferbytes = new byte[bufferOUT.length()];
        
        //DatagramPacket invia = new DatagramPacket(bufferbytes, bufferOUT.length(), indirizzo, serverPort);
        sock.send(invia);
        
        DatagramPacket ricevi = new DatagramPacket(bufferIN, bufferIN.length);
        String ricevuto;
        int cont=0;
        
        do
        {
            sock.receive(ricevi);
            ricevuto = new String(ricevi.getData());

            System.out.println("DATI RICEVUTI: "+ricevuto+"\n");
        }while(true);
    }
}
