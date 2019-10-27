package ui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private ChoiceBox<String> status;

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
    
    public void Information(String message) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("CG Costing");
    	alert.setContentText(message);
    	alert.show();
    }

    @FXML
    void addOrder(ActionEvent event) {
    	int id = 0;
    	int mD = 0;
    	int moD = 0;
    	try {
    		id = Integer.parseInt(orderNumber.getText());
        	mD = Integer.parseInt(md.getText());
        	moD = Integer.parseInt(mod.getText());
    	}catch(NumberFormatException e) {
    		Information("Please Enter a number");
    	}
    	String statu = (String) status.getValue();
    	Order newOrder = new Order(id, mD, moD, statu);
    	newOrder.calculateCif(realRate, moD);
    	if(theEnd==null) {
    		theEnd = new ResultState(name.getText(), period.getText());
    	}
    	theEnd.addOrder(newOrder);
    	orderNumber.setText("" + (id+1));
    	
    	md.setText("");
    	mod.setText("");
    	status.hide();
    }

    @FXML
    void calculate(ActionEvent event){
    	if(!name.getText().equals("") && !period.getText().equals("") && !cif.getText().equals("") && !base.getText().equals("")) {
    		double cifAux = Double.parseDouble(cif.getText());
    		double baseAux = Double.parseDouble(base.getText());
    		realRate= cifAux/baseAux;
    		rate.setText(""+ realRate);
    		addOrder.setDisable(false);
        	create.setDisable(false);
        	md.setDisable(false);
        	mod.setDisable(false);
        	status.setDisable(false);
        	
        	name.setDisable(true);;
        	period.setDisable(true);;
        	cif.setDisable(true);;
        	base.setDisable(true);
    	}else {
    		Information("Please enter a value into the active fields");
    	}
    }

    @FXML
    void create(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("CG Costing");
    	alert.setHeaderText("Information managment");
    	alert.setContentText("Do you want to save the information?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		Information("Supuestamente voy a crear un archivo de texto con la informacion de costos");
    		Information(costState());
    	} else {
    		Information(costState());
    	}
    }
    
    public String costState() {
    	String result = "\t\t\t\t Cost State \n";
    	result+= "\t\t\t\t"+theEnd.getName()+"\n\t\t\t\t"+theEnd.getPeriod()+
    			"\n--------------------------------------------------------------------\n--------------------------------------------------------------------\n";
    	result+="Inventario Inicial de MD \t\t\t"+0+"\n";
    	result+="Compra de MD \t\t\t\t" + theEnd.consumoMaterialDirecto()+"\n";
    	result+="Inventario Final de MD \t\t\t"+0+"\n--------------------------------------------------------------------"+"\n";
    	result+="Consumo de MD \t\t\t\t"+theEnd.consumoMaterialDirecto()+"\n";
    	result+="MOD \t\t\t\t\t\t"+theEnd.manoDeObraDirecta()+"\n";
    	result+="CIF \t\t\t\t\t\t\t"+theEnd.costoIndirectoDeFabricacion()+"\n";
    	result+="--------------------------------------------------------------------\n";
    	double costosAP = theEnd.manoDeObraDirecta()+theEnd.costoIndirectoDeFabricacion()+theEnd.consumoMaterialDirecto();
    	result+="Costos Agregados a produccion \t"+costosAP+"\n";
    	result+="Inventario Inicial PP \t\t\t"+0+"\n";
    	result+="Inventario Final PP \t\t\t\t"+theEnd.inventarioFinalPP()+"\n";
    	result+="--------------------------------------------------------------------\n";
    	double costosPT = costosAP-theEnd.inventarioFinalPP();
    	result+="Costos PT \t\t\t\t\t"+costosPT+"\n";
    	result+="Inventario Inicial PT \t\t\t"+0+"\n";
    	result+="Inventario Final PT \t\t\t\t"+theEnd.inventarioFinalPT()+"\n";
    	double costoVenta = costosPT-theEnd.inventarioFinalPT();
    	result+="Costo de Venta \t\t\t\t"+costoVenta+"\n";
    	result+="--------------------------------------------------------------------";
    	return result;
    }
    
    public void createFile(String fileName) {
    	
    }

    @FXML
    void initialize() {
    	status.getItems().add("in Process");
    	status.getItems().add("Finished");
    	status.getItems().add("Sold");
    	
    	addOrder.setDisable(true);
    	create.setDisable(true);
    	md.setDisable(true);
    	mod.setDisable(true);
    	status.setDisable(true);
    	//orderNumber.setText("  " + 1);
    }
}
