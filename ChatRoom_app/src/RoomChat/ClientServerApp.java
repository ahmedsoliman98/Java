/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ahmedsoliman
 */
public class ClientServerApp extends Application {
    
    Button send;
    Label l;
    TextArea txt;
    TextField msg;
    Socket mySocket;
    DataInputStream ip;
    PrintStream ps;
    MenuBar menu;
    Menu opt;
    MenuItem save;
    MenuItem open;
    MenuItem exit;
    FileChooser saveFileChooser;
    FileChooser openFileChooser;
    
    @Override
    public void init()
    {
        send = new Button("Send");
        send.setDefaultButton(true);
        menu = new MenuBar();
        opt = new Menu("Opetion");
        save = new MenuItem("Save");
        open = new MenuItem("Open");
        exit = new MenuItem("Exit");
        l = new Label ("Enter new massage");
        txt = new TextArea();
        txt.setPromptText("Massage : \n");
        txt.setEditable(false);
        msg = new TextField();
        msg.setPromptText("Enter massage");
        
        
      saveFileChooser = new FileChooser();
      saveFileChooser.setTitle("Save");
      saveFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
      
      openFileChooser = new FileChooser();
      openFileChooser.setTitle("Open");
      openFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
       
      opt.getItems().addAll(save,open,exit);
      menu.getMenus().addAll(opt);
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        try
        {
            mySocket = new Socket("127.0.0.1",5005);
            ip = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            
            send.setOnAction(new EventHandler<ActionEvent>()
            {

                @Override
                public void handle(ActionEvent event) {
                    ps.println(msg.getText());

                    
                    msg.setText("");
                }
                
            });
            
            new Thread(new Runnable ()
            {

                @Override
                public void run() {
                    while(true)
                    {
                        String repmsg;
                        try
                        {
                            repmsg = ip.readLine();
                            txt.appendText("\n"+repmsg);
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                
            }).start();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        save.setOnAction(new EventHandler<ActionEvent>()
              {

            @Override
            public void handle(ActionEvent event) {
                
                FileWriter write = null;
                try {
                    
                    write = new FileWriter(saveFileChooser.showSaveDialog(primaryStage).getPath()) ;
                        try(BufferedWriter buffer = new BufferedWriter(write))
                        {
                            buffer.write(txt.getText());
                        }
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClientServerApp.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try{
                        write.close();
                    }catch (IOException ex) {
                    Logger.getLogger(ClientServerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
            }
                  
              });
        
        open.setOnAction(new EventHandler<ActionEvent>()
              {
                 

            @Override
            public void handle(ActionEvent event) {
                try {
                    FileReader read = new FileReader(openFileChooser.showOpenDialog(primaryStage));
                    BufferedReader br = new BufferedReader(read);
                String line;
                do{
                    line = br.readLine();
                    if(line != null)
                    {
                        txt.setText(line);
                    }
                }while(line != null);
                read.close();
                br.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ClientServerApp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClientServerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
                  
              });
        
        exit.setOnAction(new EventHandler<ActionEvent>()
              {

            @Override
            public void handle(ActionEvent event) {
                Dialog<String>  exDialog = new Dialog<String>();
                exDialog.setTitle("Warnning");
                exDialog.setContentText("Make sure that you save the file !!");
                ButtonType t = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                exDialog.getDialogPane().getButtonTypes().add(t);
                exDialog.showAndWait();
                 Platform.exit();
            }
                  
              });
        
        HBox buttonBox = new HBox(5,l,msg,send);
        VBox root = new VBox(menu,txt,buttonBox);
        Scene scene = new Scene(root, 400, 350);
        
        primaryStage.setTitle("Chat Room");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
