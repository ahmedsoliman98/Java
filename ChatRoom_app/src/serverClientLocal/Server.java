/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverClientLocal;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ahmedsoliman
 */
public class Server {
    
    ServerSocket myss;
    Socket s;
    DataInputStream ip;
    PrintStream ps;
    
    public Server() 
            {
               try
               {
                   myss = new ServerSocket(5005);
                   s = myss.accept();
                   ip = new DataInputStream(s.getInputStream());
                   ps = new PrintStream(s.getOutputStream());
                   
                   String msg = ip.readLine();
                   System.out.println(msg);
                   ps.println("Welcom from Server");
                   
                   ip.close();
                   ps.close();
                   s.close();
                   myss.close();
               }
               catch(Exception e)
               {
                   
               }
            }
    public static void main (String[] args)
    {
        new Server();
    }
    
}
