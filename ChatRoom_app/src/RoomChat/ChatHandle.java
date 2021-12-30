/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedsoliman
 */
public class ChatHandle extends Thread
{
        DataInputStream ip;
        PrintStream ps;
        static Vector<ChatHandle> clientVector = new Vector<ChatHandle>();
        
        
        public ChatHandle(Socket cs)
        {
          
            try {
                ip = new DataInputStream(cs.getInputStream());
                ps = new PrintStream(cs.getOutputStream());
                clientVector.add(this);
                System.out.println("test1");
                start();
            } catch (IOException ex) {
                Logger.getLogger(ChatHandle.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        @Override
        public void run()
        {
            System.out.println("test2");
            while(true)
            {
                try
                {
                    System.out.println("test3");
                    String str = ip.readLine();
                    System.out.println("test4");
                    System.out.println(str);
                    sendMassageToAll(str);
                    
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
         public void sendMassageToAll(String msg)
        {
            for(ChatHandle ch : clientVector)
            {
                ch.ps.println(msg + " ");
                System.out.println(msg);
            }
        }
}
