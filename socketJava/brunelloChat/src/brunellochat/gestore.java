/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brunellochat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class gestore implements Runnable
{
    private final int porta;
    private final InetAddress indirizzo;
    private final long attesa               = 10000;
    private final String id                 = "<id>Mikele</id>";
    private boolean attivo                  = true;
    private final DatagramSocket socket;
    
    // Metodo costruttore
    public gestore(InetAddress host, int porta, DatagramSocket socket)
    {
        this.indirizzo = host;
        this.porta = porta;
        this.socket = socket;
    }
    
    @Override
    public void run()
    {
        try
        {
            byte[] buffer = id.getBytes("UTF-8");
            DatagramPacket invioid =new DatagramPacket(buffer, buffer.length, indirizzo, porta);
            
            while(attivo)
            {
                socket.send(invioid);
                Thread.sleep(attesa);
            }   
        } catch(Exception e)
        {
            System.out.println("Gestore: " + e);
            attivo = false;
        }
    }
}
