/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author rdfortes
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXDrawer menu;
    @FXML
    private Label category_label;
    @FXML
    private Label label;
    @FXML
    private JFXButton generate_button;
    @FXML
    private AnchorPane anchorPane;
    
    // CFG variables starts here
    
    //datastruct for productions
    HashMap<String, ArrayList<String>> prod = new HashMap<>(); // note: used diamond inference
    List<String> split_rhs;
    ArrayList<String> lhs_values;

    //datastruct for storing rules
    ArrayList <ArrayList <String>> angProd = new ArrayList <> (); // note: used diamond inference
    ArrayList <String> list = new ArrayList <> (); // note: used diamond inference

    //datastruct for generating random
    List<String> randomProd;
    
    String final_sentence = "You clicked generate!";
    char category = 'N'; // by default
    
    // controls what happens in the application
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// load the splash screen only once
//    	if(!Main.isSplashLoaded) {
//    		loadSplashScreen();
//    	}
        
        // checks the transition of the hamburger
        HamburgerBackArrowBasicTransition trans = new HamburgerBackArrowBasicTransition(ham1);
        trans.setRate(-1); // setting the transition to normal
        ham1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            trans.setRate(trans.getRate()*-1);
            trans.play();

            if(menu.isShown()) {
                menu.close();
//                anchorPane.setStyle("-fx-background-color: #ffffff;");
            } else {
                menu.open();
//                anchorPane.setStyle("-fx-background-color: #000000;");
            }

        });
        
        // put the menu content inside the drawer
        try {
            VBox box = FXMLLoader.load(getClass().getResource("MenuContent.fxml"));
            menu.setSidePane(box);
            
            // add eventhandler to the buttons
            for(Node node : box.getChildren()) {
                if(node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                        this.category = node.getAccessibleText().charAt(0);
                        trans.setRate(trans.getRate()*-1);
                        trans.play();
                        if(menu.isShown()) {
                            menu.close();
                        }
                        switch(node.getAccessibleText()) {
                            case "A":
                                category_label.setText("General & Abstract Terms");
                                break;
                            case "B":
                                category_label.setText("The Body & The Individual");
                                break;
                            case "F":
                                category_label.setText("Food & Farming");
                                break;
                            case "G":
                                category_label.setText("Govt. & The Public Domain");
                                break;
                            case "H":
                                category_label.setText("Architecture, Buildings, Houses & The Home");
                                break;
                            case "I":
                                category_label.setText("Money & Commerce");
                                break;
                            case "K":
                                category_label.setText("Entertainment, Sports & Games");
                                break;
                            case "L":
                                category_label.setText("Life & Living Things");
                                break;
                            case "M":
                                category_label.setText("Movement, Location, Travel & Transport");
                                break;
                            case "O":
                                category_label.setText("Substances, Materials, Objects");
                                break;
                            case "S":
                                category_label.setText("Social Actions, States & Processes");
                                break;
                            case "T":
                                category_label.setText("Time");
                                break;
                            case "W":
                                category_label.setText("The World & Our Environment");
                                break;
                            case "X":
                                category_label.setText("Psychological Actions, States & Processes");
                                break;
                        }
                    });
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // loading splash screen
    private void loadSplashScreen() {
    	try {
    		
    		Main.isSplashLoaded = true;
			StackPane pane = FXMLLoader.load(getClass().getResource("Splash.fxml"));
			anchorPane.getChildren().setAll(pane);
			
			// for transition
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			
			fadeIn.play();
			// after fade in, fade out
			fadeIn.setOnFinished((e)->{
				fadeOut.play();
			});
			// after fade out, display the FXMLDocument.fxml
			fadeOut.setOnFinished((e)->{
				try {
					AnchorPane parentContent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
					anchorPane.getChildren().setAll(parentContent);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    // CFG functions starts here
    public void runcfg() {
        // CFG calls here
            readRules();
            readCorpus();
    }
    
    @FXML
    private void handleGenerateButton(ActionEvent event) {
    	runcfg();
        final_sentence = generateRandom("S");
        label.setText(final_sentence);
        prod.clear();
    }
    
    // read Rules
    public void readRules() {
    	// BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/mytextfile.txt")));
        String rules = "rules.txt";
        BufferedReader input;
        String line;
        int flag = 0;
        int limit = 1;
        
        try {
            input = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(rules)));
            line = input.readLine();
            while(line != null) {
                if(!(line.isEmpty())) {
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    while(stk.hasMoreTokens()) {
                        String token = stk.nextToken();
                        if(flag < limit) {
                            list.add(token);
                        } else {
                            list.add(token);
                            angProd.add(list);
                            list = new ArrayList<>();
                            flag = -1;
                        }
                        flag++;
                    }
                }
                line = input.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        for(int i = 0; i < angProd.size(); i++) {
            ArrayList<String> temp = angProd.get(i);
            String value = temp.get(1);
            value = value.replace(" ", "");
            String key = temp.get(0);
            addProd(key, value);
        }
        
    }
    
    // read corpus
    public void readCorpus() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("corpus.txt")))) {
            String currentLine = "";
            while((currentLine = br.readLine()) != null) {
                String split[] = currentLine.split(",");
                if(split[1].equals("NN")) {
                    if(split[2].charAt(0) == category) {
                        addProd(split[0], split[1]);
                    } else {
                        if(category == 'N') {
                            addProd(split[0], split[1]);
                        }
                    }
                } else {
                    addProd(split[0], split[1]);
                }
            }
        } catch(IOException e) {
            
        }
    }
    
    // generate Random
    public String generateRandom(String symbol) {
        String sentence = "";
        
        Random rand = new Random();
        
        randomProd = prod.get(symbol);
        
        int r = rand.nextInt(randomProd.size());
        
        String str = randomProd.get(r);
        
        if(str.contains(" ")) {
            String split_process[] = str.split("\\s+");
            for (String split_proces : split_process) {
                if (prod.keySet().contains(split_proces)) {
                    sentence += generateRandom(split_proces);
                } else {
                    sentence += split_proces + " ";
                }
            }
        } else {
            if(prod.keySet().contains(str)) {
                sentence += generateRandom(str);
            } else {
                sentence += str + " ";
            }
        }
        return sentence;
    }
    
    // add prod
    public void addProd(String rhs, String lhs) {
        split_rhs = Arrays.asList(rhs.split("\\|"));
        
        // if lhs already exists as a key, get its values and append nalang the new set of rhs
        lhs_values = prod.get(lhs);
        if(prod.containsKey(lhs)) {
            for(int i = 0; i < split_rhs.size(); i++) {
                    lhs_values.add(split_rhs.get(i));
            }
        } else { // put the new key and its values
            lhs_values = new ArrayList<>();
            for(int i = 0; i < split_rhs.size(); i++) {
                    lhs_values.add(split_rhs.get(i));
            }
            prod.put(lhs, lhs_values);
        }
        
//        System.out.println(prod.get("NN"));
        
//        // print to check entry set
//        System.out.println(prod.entrySet());	
//        System.out.println(prod);
    }
    
}