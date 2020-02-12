/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brunellochat;

import java.net.*;
import java.util.Scanner;

public class sender implements Runnable
{
    private final int porta;
    private final InetAddress indirizzo;
    private final DatagramSocket client;
    
    //Metodo costruttore che si occuperà di inviare i miei dati
    public sender(InetAddress host, int porta, DatagramSocket socket)
    {
        this.indirizzo = host;
        this.porta = porta;
        this.client = socket;
    }
    
    @Override
    public void run()
    {
        //Messaggio inizializzato a stringa vuota
        String msg = "";
        
        try
        {
            //Nuovo scanner
            Scanner scan =new Scanner(System.in);
            // Inizializzo il buffer per inizializzare il pacchetto udp
            byte[] buffer = msg.getBytes();
            DatagramPacket inviomessaggio = new DatagramPacket(buffer, 0, indirizzo, porta);
            
            //Ciclo infinito
            do
            {
                //Leggi una stringa in input
                msg = scan.nextLine();
                
                //Se la stringa è diversa da q (comando per uscire)
                if(!msg.equals("q"))
                {
                    //Associa alla stringa msg quello che ho appena scritto
                    msg = "<msg>".concat(msg.concat("</msg>"));   
                    buffer = msg.getBytes("UTF-8");

                    //Imposta i dati da inviare del DatagramPacket e la lunghezza
                    inviomessaggio.setData(buffer);
                    inviomessaggio.setLength(buffer.length);

                    //Usando il DatagramSocket "client" invia i dati
                    client.send(inviomessaggio);
                }
            }
            while(!msg.equals("q"));
            
            //Chiudi il client, una votalche l'utente scrive "q"
            client.close();
            System.out.println("Connessione terminata con successo");
        }
        catch (Exception e)
        {
            System.out.println("Inviatore: " + e);
        }
    }
}