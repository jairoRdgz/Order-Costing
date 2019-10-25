package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Order;
import model.ResultState;

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
    
    private ResultState theEnd;

    @FXML
    void addOrder(ActionEvent event) {
    	int id = Integer.parseInt(orderNumber.getText());
    	int mD = Integer.parseInt(md.getText());
    	int moD = Integer.parseInt(mod.getText());
    	String statu = (String)status.getValue();
    	Order newOrder = new Order(id, mD, moD, statu);
    	theEnd.addOrder(newOrder);
    	orderNumber.setText(id+1);
    }

    @FXML
    void calculate(ActionEvent event) {
    	if(!name.getText().equals("") || !period.getText().equals("") || !cif.getText().equals("") || !base.getText().equals("")) {
    		int cifAux = Integer.parseInt(cif.getText());
    		int baseAux = Integer.parseInt(base.getText());
    		realRate= cifAux/baseAux;
    		rate.setText(""+ realRate);
    		addOrder.setDisable(false);
        	create.setDisable(false);
        	md.setDisable(false);
        	mod.setDisable(false);
        	status.setDisable(false);
    	}else {
    		System.out.println("Deje la recocha menor");
    	}
    }

    @FXML
    void create(ActionEvent event) {

    }

    @FXML
    void initialize() {
    	addOrder.setDisable(true);
    	create.setDisable(true);
    	md.setDisable(true);
    	mod.setDisable(true);
    	status.setDisable(true);
    }
}
