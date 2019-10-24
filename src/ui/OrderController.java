package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cif;

    @FXML
    private TextField base;

    @FXML
    private Button calculate;

    @FXML
    private Label rate;

    @FXML
    private Label orderNumber;

    @FXML
    private TextField md;

    @FXML
    private TextField mod;

    @FXML
    private ChoiceBox<?> status;

    @FXML
    private Button addOrder;

    @FXML
    private Button create;

    @FXML
    private TextField name;

    @FXML
    private TextField period;
    
    private double realRate;

    @FXML
    void addOrder(ActionEvent event) {

    }

    @FXML
    void calculate(ActionEvent event) {
    	if(!name.getText().equals("") || !period.getText().equals("") || !cif.getText().equals("") || !base.getText().equals("")) {
    		int cifAux = Integer.parseInt(cif.getText());
    		int baseAux = Integer.parseInt(base.getText());
    		realRate= cifAux/baseAux;
    		rate.setText(""+ realRate);
    	}
    }

    @FXML
    void create(ActionEvent event) {

    }

    @FXML
    void initialize() {
    	
    }
}
