package multiserver;

import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

//https://www.tutorialspoint.com/javaexamples/net_multisoc.htm

public class MultiServer implements Runnable{

    //Porta del server in ascolto
    protected static int serverPort = 9090;
    
    Socket csocket;
    MultiServer(Socket csocket)
    {
        this.csocket = csocket;
    }
    
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
            new Thread(new MultiServer(sock)).start();
        }
    }
    
    //Funzione run() che vine eseguita da ogni thread
    public void run()
    {
        try
        {
            //Scrve sullo stream di output, in questo modo comunichiamo con il client connesso
            PrintStream pstream = new PrintStream(csocket.getOutputStream());
            
            //Stampa una risposta
            pstream.print("HTTP/1.1 200 OK\nContent: text;charset=UTF-8\n\n");
            pstream.print("<html>\n\t<head>\n\t\t<title>Pagina prova</title>\n\t</head>\n\t<body>\n\t\n\t");
            pstream.print("\t<h1>Ciao "+ csocket.getInetAddress() +"!</h1>\n");
            pstream.print("\t</body>\n</html>\n");
            
            //Chiude lo stream
            pstream.close();
            //Chiude il socket
            csocket.close();
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
