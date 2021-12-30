/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.rmi.server.ObjID.read;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.omg.SendingContext.RunTime;

/**
 *
 * @author ahmedsoliman
 */
public class NewFXMain extends Application {
    
    MenuBar bar ;
    Menu file;
    MenuItem fileNew;
    MenuItem fileOpen;
    MenuItem fileSave;
    MenuItem fileExit;
    Menu edit;
    MenuItem editUndo;
    MenuItem editRedo;
    MenuItem editCopy;
    MenuItem editCut;
    MenuItem editDelete;
    MenuItem editpaste;
    MenuItem editSelectAll;
    Menu help;
    MenuItem helpAbout;
    MenuItem helpCompile;
    TextArea txt ;
    SeparatorMenuItem fileSep;
    SeparatorMenuItem editSep;
    SeparatorMenuItem aboutSep;
    FileChooser saveFileChooser;
    FileChooser openFileChooser;
    boolean saveFlag = false;
    
    @Override
    public void init ()
    {
        bar = new MenuBar();
        file = new Menu("File");
        fileNew = new MenuItem("New");
        fileOpen = new MenuItem("Open");
        fileSave = new MenuItem("Save");
        fileExit = new MenuItem("Exit");
        edit = new Menu("Edit");
        editCopy = new MenuItem("Copy");
        editUndo = new MenuItem("Undo");
        editRedo = new MenuItem("Redo");
        editCut = new MenuItem("Cut");
        editpaste = new MenuItem("Paste");
        editDelete = new MenuItem("Delete");
        editSelectAll = new MenuItem("Select All");
        help = new Menu("Help");
        helpAbout = new MenuItem("About");
        helpCompile = new MenuItem("Complile Java");
        fileSep = new SeparatorMenuItem();
        editSep = new SeparatorMenuItem();
        aboutSep = new SeparatorMenuItem();
        
        fileNew.setAccelerator(KeyCombination.keyCombination("Ctrl+f"));
        fileNew.setOnAction(new EventHandler<ActionEvent>()
                {

            @Override
            public void handle(ActionEvent event) {
                txt.clear();
            }
                    
                });
        
       editCopy.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                String copytxt = txt.getSelectedText();
                
                final Clipboard clip = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(copytxt);
                clip.setContent(content);
                
            }
                   
               });
       
       editCut.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                String copytxt = txt.getSelectedText();
                
                final Clipboard clip = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(copytxt);
                clip.setContent(content);
                txt.replaceSelection("");
                
            }    
               });
       
       editpaste.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                
                final Clipboard clip = Clipboard.getSystemClipboard();
                String copytxt = clip.getString();
                txt.appendText(copytxt);
                
            }    
               });
       
       editDelete.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                
                txt.replaceSelection("");
                
            }    
               });
       
       editUndo.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                
                txt.undo();
                
            }    
               });
       
       editRedo.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                
                txt.redo();
                
            }    
               });
       
        editSelectAll.setOnAction(new EventHandler<ActionEvent>()
               {

            @Override
            public void handle(ActionEvent event) {
                
                txt.selectAll();
                
            }    
               });
        
      saveFileChooser = new FileChooser();
      saveFileChooser.setTitle("Save");
      saveFileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
      
      openFileChooser = new FileChooser();
      openFileChooser.setTitle("Open");
      openFileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
              
        
        txt = new TextArea();
        txt.setPrefRowCount(300);
        txt.setPrefHeight(400);
        
        file.getItems().addAll(fileNew, fileOpen, fileSave,fileSep, fileExit);
        edit.getItems().addAll(editUndo, editRedo, editCopy, editCut, editpaste, editDelete,editSep, editSelectAll);
        help.getItems().addAll(helpAbout,aboutSep, helpCompile);
        
        bar.getMenus().addAll(file, edit, help);
        
        
    }
    @Override
    public void start(Stage primaryStage) {
        
        fileSave.setOnAction(new EventHandler<ActionEvent>()
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
                    Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try{
                        write.close();
                    }catch (IOException ex) {
                    Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
            }
                  
              });
        
        fileOpen.setOnAction(new EventHandler<ActionEvent>()
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
                    Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
                  
              });
        
        fileExit.setOnAction(new EventHandler<ActionEvent>()
              {

            @Override
            public void handle(ActionEvent event) {
                Dialog<String>  exDialog = new Dialog<String>();
                exDialog.setTitle("Warnning");
                exDialog.setContentText("Make sure that you save the file !!");
                ButtonType t = new ButtonType("Ok", ButtonData.OK_DONE);
                exDialog.getDialogPane().getButtonTypes().add(t);
                exDialog.showAndWait();
                 Platform.exit();
            }
                  
              });
        
        
        helpAbout.setOnAction(new EventHandler<ActionEvent>()
              {

            @Override
            public void handle(ActionEvent event) {
                Dialog<String>  AbDialog = new Dialog<String>();
                AbDialog.setTitle("About");
                AbDialog.setContentText("Program editor : Ahmed Soliman\nImplement Date&Time : 14/12/2021 2:12am");
                ButtonType t = new ButtonType("Ok", ButtonData.OK_DONE);
                AbDialog.getDialogPane().getButtonTypes().add(t);
                AbDialog.showAndWait();
               
            }
                  
              });
        helpCompile.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event) {
                Process process;
                try {
                    process = Runtime.getRuntime().exec("note.java");
                    Dialog<String>  AbDialog = new Dialog<String>();
                    AbDialog.setTitle("Compiled");
                    AbDialog.setContentText("Code is Compiled ");
                    ButtonType t = new ButtonType("Ok", ButtonData.OK_DONE);
                    AbDialog.getDialogPane().getButtonTypes().add(t);
                    AbDialog.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        BorderPane pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(txt);
        Scene scene = new Scene(pane, 300, 400);
        
        primaryStage.setTitle("NotePade00");
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
