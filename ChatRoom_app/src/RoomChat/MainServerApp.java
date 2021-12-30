/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import RoomChat.ChatHandle;

/**
 *
 * @author ahmedsoliman
 */




public class MainServerApp {
    ServerSocket myServer;
    
    public MainServerApp() {
        try {
            myServer = new ServerSocket(5005);
            while(true)
                {
                    try {
                        Socket s = myServer.accept();
                        new ChatHandle(s);
                    }catch (IOException ex) {
                        Logger.getLogger(MainServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
        } catch (IOException ex) {
            Logger.getLogger(MainServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

       
        
    public static void main (String[] args)
    {
        new MainServerApp();
    }
  }
    
    

