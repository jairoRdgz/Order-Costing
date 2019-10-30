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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Order;
import model.ResultState;

public class OrderController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    private CheckBox actual;

    @FXML
    private TextField orderNumber;

    @FXML
    private TextField cifReales;
    
    @FXML
    private Button contin;
    
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
    	create.setDisable(false);
    	int id = 0;
    	int mD = 0;
    	int moD = 0;
    	try {
    		id = Integer.parseInt(orderNumber.getText());
        	mD = Integer.parseInt(md.getText());
        	moD = Integer.parseInt(mod.getText());
    	}catch(NumberFormatException e) {
    		create.setDisable(true);
    		Information("Please Enter a number");
    	}
    	String statu = (String) status.getValue();
    	boolean esActual = actual.isSelected();
    	Order newOrder = new Order(id, mD, moD, statu, esActual);
    	if(theEnd==null) {
    		theEnd = new ResultState(name.getText(), period.getText());
    	}
    	theEnd.addOrder(newOrder);
    	orderNumber.setText("" + (id+1));
    	
    	md.setText("");
    	mod.setText("");
    	status.hide();
    	orderNumber.setText("");
    }

    @FXML
    void contin(ActionEvent event){
    	if(!name.getText().equals("") && !period.getText().equals("")) {
    		addOrder.setDisable(false);
        	md.setDisable(false);
        	mod.setDisable(false);
        	status.setDisable(false);
        	orderNumber.setDisable(false);
        	actual.setDisable(false);
        	
        	name.setDisable(true);;
        	period.setDisable(true);;
        	
    	}else {
    		Information("Please enter a value into the active fields");
    	}
    }

    @FXML
    void create(ActionEvent event) {
    	if(!cifReales.getText().contentEquals("")) {
    		double cifAux = theEnd.costoIndirectoDeFabricacionPasado();
    		double baseAux = theEnd.manoDeObraDirectaPasada();
    		realRate= cifAux/baseAux;
    		
    		theEnd.calcularCif(realRate);
        	
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
    	}else {
    		Information("Please enter a value into the real CIF field");
    	}
    	cifReales.setText("");
    }
    
    public String costState() {
    	
    	double consumoMaterialDirecto = theEnd.consumoMaterialDirecto();
    	double manoDeObraDirecta = theEnd.manoDeObraDirecta();
    	double costoIndirectoDeFabricacion = theEnd.costoIndirectoDeFabricacion();
    	double costosAP = manoDeObraDirecta + costoIndirectoDeFabricacion +consumoMaterialDirecto;
    	double inventarioInicialPP = theEnd.inventarioInicialPP();
    	double inventarioFinalPP = theEnd.inventarioFinalPP();
    	double costosPT = costosAP + inventarioInicialPP - inventarioFinalPP;
    	double inventarioInicialPT = theEnd.inventarioInicialPT();
    	double inventarioFinalPT = theEnd.inventarioFinalPT();
    	double costoVenta = costosPT + inventarioInicialPT - inventarioFinalPT;
    	
    	String result = "\t\t\t\t"+theEnd.getName() + "\n";
    	result+="\t\t\t\t Estado de Costos" + "\n\t\t\t\t"+theEnd.getPeriod()+
    			"\n--------------------------------------------------------------------\n--------------------------------------------------------------------\n";
    	result+="Inventario Inicial de MD \t\t\t"+0+"\n";
    	result+="Compra de MD \t\t\t\t" + (consumoMaterialDirecto)+"\n";
    	result+="Inventario Final de MD \t\t\t"+0+"\n--------------------------------------------------------------------"+"\n";
    	result+="Consumo de MD \t\t\t\t"+(consumoMaterialDirecto)+"\n";
    	result+="MOD \t\t\t\t\t\t"+(manoDeObraDirecta)+"\n";
    	result+="CIF \t\t\t\t\t\t\t"+(costoIndirectoDeFabricacion)+"\n";
    	result+="--------------------------------------------------------------------\n";
    	result+="Costos Agregados a produccion \t"+(costosAP)+"\n";
    	result+="Inventario Inicial PP \t\t\t"+(inventarioInicialPP)+"\n";
    	result+="Inventario Final PP \t\t\t\t"+(inventarioFinalPP)+"\n";
    	result+="--------------------------------------------------------------------\n";
    	result+="Costos PT \t\t\t\t\t"+(costosPT)+"\n";
    	result+="Inventario Inicial PT \t\t\t"+(inventarioInicialPT)+"\n";
    	result+="Inventario Final PT \t\t\t\t"+(inventarioFinalPT)+"\n";
    	result+="Costo de Venta \t\t\t\t"+(costoVenta)+"\n";
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
    	actual.setDisable(true);
    	orderNumber.setDisable(true);
    }
}
