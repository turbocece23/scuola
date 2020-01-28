package multiserver;

import java.util.*;
import java.net.*;
import java.io.*;

//https://www.tutorialspoint.com/javaexamples/net_multisoc.htm

public class MultiServer {

    //Porta del server in ascolto
    protected static int serverPort = 9090;
    
    public static void main(String args[]) throws Exception
    {
        //Inizia ad ascoltare sulla porta del server
        ServerSocket ssock = new ServerSocket(serverPort);
        //Stampa di controllo
        System.out.println("Listening");
        
        //Loop infinito
        while (true)
        {
            //Accetta eventuali connessioni in arrivo
            Socket sock = ssock.accept();
            //Stampa di controllo
            System.out.println("Connected");
            //Crea un nuovo thread passandogli come argomento il socket del client connesso
            (new Thread(new threadhttp(sock))).start();
        }
    }
}