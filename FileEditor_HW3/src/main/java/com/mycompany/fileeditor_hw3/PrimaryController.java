package com.mycompany.fileeditor_hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController {
    
    @FXML
    TextArea textArea;
    
    Stack<String> undoStack = new Stack<>();
    Stack<String> redoStack = new Stack<>();
    
    public void initialize(){
        textArea.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
            // this will run whenever text is changed
            System.out.println(oldValue);
            
            undoStack.push(oldValue);
        }
    });
    }
    
    /**
     * Will be used to exit javafx platform and exit tictactoe game.
     * @param event
     */
    public void closeGame(ActionEvent event) 
    {
        Platform.exit();
        System.exit(0);
    } // End method.
    
    /**
     * Will allow the user to pick aa text file from the current directory.
     * @param event
     */
    public void openTxtFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        
        // Using a filter, only allow .txt files to be choosen by the user.
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        
        // Set the initiaal sirectory for the user.
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        // Allow the user to select the file they want to open.
        File fileToLoad = fileChooser.showOpenDialog(null);
        
        if (fileToLoad != null){
            loadFileToTextArea(fileToLoad);
        } // End if.
    } // End method.

    /**
     * method that will load a txt file from the user's file system into the text area of the window.
     * @param fileToLoad 
     */
    private void loadFileToTextArea(File fileToLoad) {
        try{
            // Create a scanner object.
            Scanner scan = new Scanner(fileToLoad).useDelimiter("\\s+");
            
            // Clear the text area before the load occurs, to have an empty textArea.
            textArea.clear();
            
            while (scan.hasNext()){
                // If next item in the file is an integer, display that integer.
                if (scan.hasNextLine()){
                    textArea.appendText(scan.nextLine() + "\n");
                } // End if.
            } // End while.
        } // End try.
        catch (FileNotFoundException ex){
            System.err.println(ex);
        } // End catch.
    } // End method.
    
    /**
     * will save what is in the textArea into either a new file in the user's file system or update/overwrite an existing txt file.
     * @param event 
     */
    @FXML
    void save(ActionEvent event) {
        // Creates a fileChooser object.
        FileChooser fileChooser = new FileChooser();
        
        // Opens a save dialog box.
        
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            saveSystem(file, textArea.getText());
        } // End if.
    } // End method.
    
    /**
     * Helper method that will aid in conducting the full save into a file in the user's file system.
     * Will be used in the save method.
     * @param file
     * @param content 
     */
    public void saveSystem(File file, String content){
        try {
            // Creates a printWriter object.
            PrintWriter printWriter = new PrintWriter(file);
            
            // Write what is in the textArea.
            printWriter.write(content);
            
            // Close printWriter.
            printWriter.close();
        } // End try.
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } // End catch.
    } // End method.
            
    public void undo(ActionEvent event)
    {   
        // Stores top element
        // of the stack
        String X = (String)undoStack.pop();
  
        // Push an element to
        // the top of stack
        redoStack.push(X);
        
        textArea.setText(X);
    }
  
    // Function to perform
    // "REDO" operation
    public void redo(ActionEvent event)
    {
      
        // Stores the top element
        // of the stack
        String X = (String)undoStack.pop();
  
  
        // Push an element to
        // the top of the stack
        undoStack.push(X);
        
        textArea.setText(X);
    }
} // End controller.