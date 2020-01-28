package multiserver;

import java.util.*;
import java.net.*;
import java.io.*;

public class threadhttp implements Runnable {
    
    private Socket csocket;
    
    public threadhttp(Socket sock)
    {
        this.csocket = sock;
    }
    
    //Funzione run() che vine eseguita da ogni thread
    @Override
    public void run()
    {
        this.processHttp();
    }
    
    public void processHttp()
    {
        try
        {
            //Scrve sullo stream di output, in questo modo comunichiamo con il client connesso
            PrintStream pstream = new PrintStream(csocket.getOutputStream());
            
            //Stampa nel file gli utenti connessi
            InetAddress addr = csocket.getInetAddress();
            String addrstr = addr.getHostAddress();
            
            //Stampa una risposta
            pstream.print("HTTP/1.1 200 OK\nContent: text/html; Connection: close\n\n");
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
