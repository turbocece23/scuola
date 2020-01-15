package serverjava;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Cesare Brunello
 */
public class ServerJava {
    
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    
    int porta = 5000;
    
    
    public Socket attendi()
    {
        try
        {
            System.out.println("Server partito in esecuzione\n");
            
            //Crea un server sulla porta specificata
            server = new ServerSocket(porta);
            
            //Rimane in attesa di un client
            client = server.accept();
            
            //Chiudo il server per inibire altri client
            server.close();
            
            //Associo due oggetti al socket del client per effettuare la scrittura e la lettura
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
        return client;
    }
    
    public void comunica()
    {
        try
        {
            //Rimango in attesa della riga trasmessa dal client
            System.out.println("Scrivi una frase e la trasformo in maiuscolo. Attendo.\n");
            stringaRicevuta = inDalClient.readLine();
            System.out.println("Ricevuta la stringa dal client: "+stringaRicevuta);
            
            //La modifico e la rispedisco al client
            stringaModificata=stringaRicevuta.toUpperCase();
            System.out.println("Invio la stringa modificata al client");
            outVersoClient.writeBytes(stringaModificata+'\n');
            
            //Termina elaborazione sul server, chiudo la connessione del client
            System.out.println("Fine elaborazione\n");
            client.close();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
    }
    
    
    public static void main(String[] args) {
        
        /*
        http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
        https://www.tutorialspoint.com/javaexamples/net_multisoc.htm
        https://blog.eduonix.com/java-programming-2/learn-create-multi-threaded-server-java/
        */
        
        ServerJava servente = new ServerJava();
        servente.attendi();
        servente.comunica();
    }
}
