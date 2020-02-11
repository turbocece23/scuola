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
    
    //Porta del server in ascolto
    protected int serverPort;
    //Questo è l'indirizzo di destinazione però sotto forma di stringa
    protected InetAddress indirizzo;
    protected byte[] msginvio;
    protected Scanner input = new Scanner(System.in);
    
    public sender(int port, String addr) throws Exception
    {
        this.serverPort = port;
        //Questa funzione converte la stringa in un indirizzo raggiungibile
        this.indirizzo = InetAddress.getByName(addr);
    }
    
    @Override
    public void run()
    {
        DatagramSocket sock = null;
        try
        {
            sock = new DatagramSocket(serverPort,indirizzo);
        }catch(SocketException e)
        {
            System.out.println("Errore nella creazione del socket: "+e);
        }
        
        String messaggio = new String();
        messaggio = input.nextLine();
        
        try
        {
            msginvio = messaggio.getBytes("UTF-8");
        }catch (Exception e)
        {
            System.out.println("Errore nella creazione del buffer: "+e);
        }
        
        //Invia i dati tramite un buffer, creazione del buffer
        DatagramPacket invia = new DatagramPacket(msginvio, msginvio.length, indirizzo, serverPort);
        
        try
        {
            //Invia i dati
            sock.send(invia);
        }catch(Exception e)
        {
            System.out.println("AAAARRRRHHH C'è un errore! "+e);
        }
        
        sock.close();
    }
}
