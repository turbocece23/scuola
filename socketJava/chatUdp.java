import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class chatUdp
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
        
        /* Prendo l'ip con cui la scheda di rete si identifica in rete (Serve al thread ricevitore) */
        String ip = getIP(2);
        
        /* Creo i vari oggetti e i rispettivi thread che gestiscono il tutto */
        Inviatore invio =new Inviatore(indirizzo, porta, socket);
        Ricevitore ricevo =new Ricevitore(socket, ip, id);
        Gestore gestisco =new Gestore(indirizzo, porta, socket, id);
        
        System.out.println("Benvenuto nel client di Gusella Michele");
        //System.out.println("L'IP con cui ti presenti in rete e': " + ip.substring(1));
        System.out.println("Sintassi per inviare un messaggio privato -> :iddestinatario messaggio");
        System.out.println("Scrivi q per uscire dal programma");
        System.out.println("---------------- Inizio Chat ----------------");
        Thread tinvio =new Thread(invio);
        Thread tricevo =new Thread(ricevo);
        Thread tgestisco =new Thread(gestisco);
        
        tgestisco.start();
        tinvio.start();
        tricevo.start();
        
        //String timeStamp = new SimpleDateFormat("dd MM yyyy HH mm ss").format(new Date());
        //System.out.println("Data: " + timeStamp);
    }
}

class Gestore implements Runnable
{
    private final int porta;
    private final InetAddress indirizzo;
    /* Millisecondi dopo cui invia l'id per identificarsi */
    private final long attesa               = 10000;
    private final String id;
    private final DatagramSocket socket;
    
    public Gestore(InetAddress host, int porta, DatagramSocket socket, String id)
    {
        this.indirizzo = host;
        this.porta = porta;
        this.socket = socket;
        this.id = "<id>".concat(id.concat("</id>"));
    }
    
    @Override
    public void run()
    {
        try
        {
            /* Creo il buffer che avrà sempre lo stesso messaggio da inviare, ovvero l'id */
            byte[] buffer = id.getBytes("UTF-8");
            DatagramPacket invioid =new DatagramPacket(buffer, buffer.length, indirizzo, porta);
            
            /* Ogni attesa secondi invia l'id */
            while(true)
            {
                socket.send(invioid);
                Thread.sleep(attesa);
            }
        }
        /* Quando l'inviatore chiude il socket, appena il gestore prova a inviare l'id vede che il socket è chiuso
           così viene catturata l'eccezione e il thread termina l'esecuzione essendo uscito dal ciclo while */
        catch(Exception e)
        {
            System.out.println("Gestore: " + e);
        }
    }
}

class Inviatore implements Runnable
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
                if(!msg.equals("q") && !msg.equals(""))
                {
                    /* Controllo se il messaggio e' privato o pubblico e lo invio */
                    if(msg.charAt(0) == ':')
                    {
                        /* Se il messaggio è privato prendo l'id e il messaggio e li concateno con il tag <msg> */
                        String id = msg.substring(1, msg.indexOf(' '));
                        msg = msg.substring(msg.indexOf(' ') + 1);
                        msg = "<msg id=\"".concat(id.concat("\">".concat(msg.concat("</msg>"))));
                    }
                    else
                    {
                        /* Sennò concateno direttamente il messaggio con il tag */
                        msg = "<msg>".concat(msg.concat("</msg>"));
                    }
                    /* Imposto il pacchetto udp con il buffer contenente il messaggio */
                    buffer = msg.getBytes("UTF-8");
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

class Ricevitore implements Runnable
{
    /* Dimensione del buffer e dell'array che contiene gli id e ip */
    private final int dim                       = 256;
    /* L'ip dell'interfaccia di rete scelta nella classe principale, non il localhost */
    private final String iplocale;
    /* Di default false, se true stampa anche i messaggi senza tag o con tag non riconosciuti (utile per controllare cosa riceve il thread) */
    private final boolean noTag                 = false;
    /* Ciò che riceve */
    private String msg;
    /* L'id che comunica ogni tot tempo */
    private final String idlocale;
    /* ip[] contiene gli ip di tutti gli host che inviano pacchetti */
    private String[] ip =new String[dim];
    /* id[] contiene gli id che gli host inviano. Le posizioni tra ip e id sono collegate (logicamente) ovvero,
       id[i] identifica l'host che ha l'ip equivalente a ip[i] */
    private String[] id =new String[dim];
    private final DatagramSocket server;
    
    public Ricevitore(DatagramSocket socket, String ip, String id)
    {
        this.server = socket;
        this.iplocale = ip;
        this.idlocale = id;
    }
    
    /* Controlla il tag del messaggio ricevuto, i vari return sono gestiti dalla prossima funzione */
    private String controllaTag(String s)
    {
        /* Se il messaggio non parte con < sicuramente non è un tag */
        if(s.charAt(0) == '<')
        {
            /* Se dopo < c'è scritto id> */
            if(s.charAt(1) == 'i' && s.charAt(2) == 'd' && s.charAt(3) == '>')
                return "id";
            /* Se dopo < c'è scritto msg, ci sono due casi */
            if(s.charAt(1) == 'm' && s.charAt(2) == 's' && s.charAt(3) == 'g')
            {
                /* Se il tag si chiude, quindi è <msg>, è un messaggio pubblico */
                if(s.charAt(4) == '>')
                    return "msg";
                /* Se dopo <msg c'è scritto id=" vuol dire che il messaggio è privato */
                if(s.charAt(4) == ' ' && s.charAt(5) == 'i' && s.charAt(6) == 'd' && s.charAt(7) == '=' && s.charAt(8) == '"')
                    return "pvt";
            }
        }
        return "errore";
    }
    
    /* Funzione che in base al tag, esegue certe operazioni */
    private void gestisciInput(String s, DatagramPacket pacchetto)
    {
        String contenuto;
        boolean esco = false;
        int posLibera = -1;
        
        /* Se il tag è <id> */
        if(controllaTag(s).equals("id"))
        {
            /* Legge il contenuto del tag ovvero la stringa dal primo carattere dopo <id> fino a prima di </id> */
            contenuto = s.substring(4, s.indexOf("</id>"));
            /* for che scorre tutto l'array degli ip */
            for(int i = 0; i < dim && esco == false; i++)
            {
                /* Se l'ip sorgente del pacchetto è già nell'array */
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)))
                {
                    /* Se l'id associato a quell'ip è diverso da quello inviato dall'utente */
                    if(!id[i].equals(contenuto))
                    {
                        /* Cambialo ed esci */
                        System.out.println("[" + ip[i] + "] " + id[i] + " ha cambiato username in " + contenuto);
                        id[i] = contenuto;
                    }
                    /* Se l'id è uguale a quello ricevuto, non fare nulla perchè è tutto appost ed esci */
                    esco = true;
                }
                /* Mentre scorro l'array, controllo la prima posizione libera che trovo e me la salvo */
                if(id[i].equals("") || ip[i].equals("") && posLibera == -1)
                    posLibera = i;
            }
            /* Se non sono uscito, quindi non ho trovato l'ip nell'array, vuol dire che è la prima volta che ricevo
               un pacchetto da questo host, quindi lo inserisco nella prima posizione libera. posLibera serve semplicemente
               a non dover scorrere un altra volta l'array in cerca di un posto dove mettere l'ip e l'id */
            if(!esco)
            {
                id[posLibera] = contenuto;
                ip[posLibera] = pacchetto.getAddress().toString().substring(1);
                System.out.println("[" + ip[posLibera] + "] " + contenuto + " e' online!");
            }
        }
        /* Se invece il tag è <msg> */
        else if(controllaTag(s).equals("msg"))
        {
            /* Legge il contenuto del tag ovvero la stringa dal primo carattere dopo <msg> fino a prima di </msg> */
            contenuto = s.substring(5, s.indexOf("</msg>"));
            /* for che scorre tutto l'array degli ip */
            for(int i = 0; i < dim && esco == false; i++)
            {
                /* Se l'ip sorgente del pacchetto è già nell'array e l'id corrispondente non è "" */
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)) && !id[i].equals(""))
                {
                    /* Stampa che questo id ha inviato il messaggio */
                    System.out.println(id[i] + ": " + contenuto);
                    esco = true;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array, stampo il messaggio con mittente
               l'ip sorgente del pacchetto udp */
            if(!esco)
            {
                System.out.println(pacchetto.getAddress().toString().substring(1) + ": " + contenuto);
            }
        }
        /* Se il messaggio è privato, quindi con tag <msg id=" */
        else if(controllaTag(s).equals("pvt"))
        {
            /* Legge il contenuto del sottotag id ovvero la stringa dal primo carattere dopo <msg id=" fino a prima di "> */
            String destinatario = s.substring(9, s.indexOf("\">"));
            contenuto = s.substring(2 + s.indexOf("\">"), s.indexOf("</msg>"));
            /* for che scorre tutto l'array degli ip */
            for(int i = 0; i < dim && esco == false && !destinatario.equals(""); i++)
            {
                /* Se l'ip sorgente del pacchetto è già nell'array e l'id corrispondente non è "" */
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)) && !id[i].equals(""))
                {
                    /* Stampa che questo id ha inviato il messaggio privato, controllando se il destinatario è questo host */
                    if(destinatario.equals(idlocale))
                        System.out.println(id[i] + " verso <<<" + destinatario + ">>>: " + contenuto);
                    else
                        System.out.println(id[i] + " verso " + destinatario + ": " + contenuto);
                    esco = true;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco && !destinatario.equals(""))
            {
                System.out.println(pacchetto.getAddress().toString().substring(1) + " verso " + destinatario + ": " + contenuto);
            }
        }
        /* Se il tag non viene riconosciuto, e il noTag è true */
        else if(noTag && controllaTag(s).equals("errore"))
        {
            /* for che scorre tutto l'array degli ip */
            for(int i = 0; i < dim && esco == false; i++)
            {
                /* Se l'ip sorgente del pacchetto è già nell'array e l'id corrispondente non è "" */
                if(ip[i].equals(pacchetto.getAddress().toString().substring(1)) && !id[i].equals(""))
                {
                    /* Stampo l'intera stringa che ha come mittente l'id */
                    System.out.println(id[i] + "->noTag: " + s); 
                    esco = true;
                }
            }
            /* Se non sono uscito, quindi non ha trovato l'ip nell'array */
            if(!esco)
            {
                System.out.println(pacchetto.getAddress().toString().substring(1) + "->noTag: " + s);
            }
        }
    }
    
    @Override
    public void run()
    {
        /* Creo il buffer di dimensione dim e lo associo al pacchetto udp */
        byte[] ricevo =new byte[dim];
        DatagramPacket ricevopacchetto =new DatagramPacket(ricevo, ricevo.length);
        
        /* Inizializzo gli array che contengono id e ip */
        for(int i = 0; i < ip.length; i++)
        {
            ip[i] = "";
            id[i] = "";
        }
        
        try
        {            
            while(true)
            {
                /* Pulisco il buffer e aspetto di ricevere qualcosa */
                for (int i = 0; i < dim; i++)
                    ricevo[i] = 0;
                server.receive(ricevopacchetto);
                
                /* Controlla che l'ip sorgente non sia quello della scheda di rete scelta nella classe principale.
                   Utile perchè se l'ip di destinazione è il broadcast, il router reindirizza il pacchetto anche
                   a questo host, che però deve ignorarlo dato che è lui stesso il mittente */
                if(!ricevopacchetto.getAddress().toString().equals(iplocale))
                {
                    msg =new String(ricevopacchetto.getData());
                    gestisciInput(msg, ricevopacchetto);
                }
            }
        } catch(Exception e)
        {
            System.out.println("Ricevitore: " + e);
        }
    }
}


