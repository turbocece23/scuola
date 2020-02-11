/*
Brunello Cesare 5AI 2020

Ogni messaggio inviato avrà una sintassi simile
<id></id> //Identificativo dell'utente
<msg></msg> //Messaggio inviato in broadcast
<msg id=""></msg> //Inviato comunque in broadcast ma viene visualizzato in maniera differente da parte del ricevente

L'id viene lanciato più volte durante l'esecuzione del programma in modo tale da avvertire
tutti i presenti nella chat che il mio client è attivo e sta trasmettendo

Il programma deve spedire e inviare messaggi allo stesso tempo
è meglio tenere separato l'ambito di comunicazione e di rappresentazione del messaggio

Utilizzerà il protocoloo UDP spedendo i messaggi in 172.30.4.255 (broadcast)
https://www.baeldung.com/udp-in-java
 */
package brunellochat;

public class BrunelloChat {

    
    public static void main(String[] args) throws Exception
    {
        sender inviatore = new sender(2345,"172.30.4.255");
        
        Thread tinviatore = new Thread(inviatore);
        tinviatore.start();
        
        /*
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
        */
    }
}
