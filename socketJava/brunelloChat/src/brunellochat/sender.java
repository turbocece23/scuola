package brunellochat;

import java.net.*;
import java.util.Scanner;

public class sender implements Runnable
{
    private final int porta;
    private final InetAddress indirizzo;
    private final DatagramSocket client;
    
    public Inviatore(InetAddress host, int porta, DatagramSocket socket)
    {
        this.indirizzo = host;
        this.porta = porta;
        this.client = socket;
    }
    
    @Override
    public void run()
    {
        String msg = "";
        
        try
        {
            Scanner scan =new Scanner(System.in);
            /* Inizializzo il buffer per inizializzare il pacchetto udp */
            byte[] buffer = msg.getBytes();
            DatagramPacket inviomessaggio =new DatagramPacket(buffer, 0, indirizzo, porta);
            
            do
            {
                msg = scan.nextLine();
                
                /* Se il messaggio non è "q", cioè non voglio uscire dal processo */
                if(!msg.equals("q"))
                {
                    /* Aggiungo il tag msg all'inizio e alla fine del messaggio e creo il buffer da spedire */
                    msg = "<msg>".concat(msg.concat("</msg>"));   
                    buffer = msg.getBytes("UTF-8");
                    
                    /* Imposto il pacchetto udp con il buffer contenente il messaggio */
                    inviomessaggio.setData(buffer);
                    inviomessaggio.setLength(buffer.length);
                    /* Invio il pacchetto */
                    client.send(inviomessaggio);
                }
            }
            while(!msg.equals("q"));
            
            /* Quando esco chiudo il socket così gli altri thread catturano l'eccezione del socket chiuso
               e terminano la loro esecuzione */
            client.close();
            System.out.println("Connessione terminata con successo");
        }
        catch (Exception e)
        {
            System.out.println("Inviatore: " + e);
        }
    }
}
