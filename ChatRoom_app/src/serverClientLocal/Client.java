/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverClientLocal;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author ahmedsoliman
 */
public class Client
{
    Socket mySocket;
    DataInputStream ip;
    PrintStream ps;
    
    public Client()
    {
        try
        {
            mySocket = new Socket("127.0.0.1",5005);
            ip = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            
            ps.println("Welcon from Client");
            String repmsg = ip.readLine();
            System.out.println(repmsg);
            
            ip.close();
            ps.close();
            mySocket.close();
        }
        catch(Exception e)
        {
            
        }
    }
    
    public static void main(String[] args)
    {
        new Client();
    }
}
