/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author rdfortes
 */
public class FXMLDocumentController implements Initializable {

    // random strings to generate
    ArrayList<String> Interrogatives = new ArrayList<>();
    ArrayList<String> Imperatives = new ArrayList<>();
    ArrayList<String> Meteorologic = new ArrayList<>();
    ArrayList<String> Locative = new ArrayList<>();
    String command = "";
    String gen = "";
    Label label1;
    
    @FXML
    private TextField displayField;
    @FXML
    private Pane paneLoadLabel;
    @FXML
    private Label label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // initialize sentences
        Interrogatives.add("Asa ka padulong?");
        Interrogatives.add("Asa ta molarga?");
        Interrogatives.add("Hain na ang gunting?");
        Interrogatives.add("Unsa imu pangalan?");
        Imperatives.add("Isugba kanang isda.");
        Imperatives.add("Ngari/Ali/Hali diri.");
        Imperatives.add("Ayaw mo panabako diri");
        Meteorologic.add("Tugnaw dinhi sa Baguio City.");
        Meteorologic.add("Init kaayo ang adlaw diri sa Sugbo.");
        Locative.add("Ania ang kwarta.");
        Locative.add("Tua siya sa bukid.");
    }    

    @FXML
    private void handleGenerateAction(ActionEvent event) {
        Random rand = new Random();
        switch (command) {
            case "Interrogatives":
                {
                    int n = rand.nextInt(Interrogatives.size()) + 0;
                    gen = Interrogatives.get(n);
                    label1 = new Label("Not answerable by yes or no");
                    break;
                }
            case "Imperatives":
                {
                    int n = rand.nextInt(Imperatives.size()) + 0;
                    gen = Imperatives.get(n);
                    label1 = new Label("Commands and Requests");
                    break;
                }
            case "Meteorologic":
                {
                    int n = rand.nextInt(Meteorologic.size()) + 0;
                    gen = Meteorologic.get(n);
                    label1 = new Label("Weather conditions, noise level, etc.");
                    break;
                }
            case "Locative":
                {
                    int n = rand.nextInt(Locative.size()) + 0;
                    gen = Locative.get(n);
                    label1 = new Label("Location of a thing");
                    break;
                }
            default:
                label1 = new Label("Choose a Category");
                break;
        }
        displayField.setText(gen);
        displayField.setStyle("-fx-font-family: Georgia; -fx-font-size: 14");
        
    }

    @FXML
    private void handleCategoryAction(ActionEvent event) {
        command = ((Button)event.getSource()).getText();
        System.out.println(command);
        
        switch (command) {
            case "Interrogatives":
                {   
                    label.setText("Not answerable by yes or no");
                    break;
                }
            case "Imperatives":
                {   
                    label.setText("Commands and Requests");
                    break;
                }
            case "Meteorologic":
                {   
                    label.setText("Weather conditions, noise level, etc.");
                    break;
                }
            case "Locative":
                {
                    label.setText("Location of a thing");
                    break;
                }
            default:
                label.setText("Choose a Category");
                break;
        }
        
    }

    
}
