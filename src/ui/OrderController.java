package ui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private Button contin;
    
    @FXML
    private TextField basePresupuestada;
    
    @FXML
    private TextField cifPresupuestados;
    
    @FXML
    private TextField cifPasados;
    
    @FXML
    private Label rateLabel;
    
    @FXML
    private ChoiceBox<String> baseType;

    @FXML
    private ChoiceBox<String> orderOption;
    
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
    	if(!esActual) {
    		newOrder.setCif(Double.parseDouble(cifPasados.getText()));
    	}else {
    		newOrder.calculateCif(realRate, moD);
    	}
    	if(theEnd==null) {
    		theEnd = new ResultState(name.getText(), period.getText());
    	}
    	theEnd.addOrder(newOrder);
    	orderNumber.setText("" + (id+1));
    	
    	md.setText("");
    	mod.setText("");
    	status.hide();
    	orderNumber.setText("");
    	cifPasados.setText("");
    	orderOption.hide();
    }

    @FXML
    void contin(ActionEvent event){
    	if(!name.getText().equals("") && !period.getText().equals("") && !cifPresupuestados.getText().equals("")&&!basePresupuestada.getText().equals("")) {
    		addOrder.setDisable(false);
        	md.setDisable(false);
        	mod.setDisable(false);
        	status.setDisable(false);
        	orderNumber.setDisable(false);
        	actual.setDisable(false);
        	cifPasados.setDisable(false);
        	orderOption.setDisable(false);
        	
        	double cifAux = Double.parseDouble(cifPresupuestados.getText());
    		double baseAux = Double.parseDouble(basePresupuestada.getText());
    		realRate= cifAux/baseAux;
    		
    		DecimalFormat formato = new DecimalFormat("#.00");
    		
    		
    		rateLabel.setText(rateLabel.getText() + formato.format(realRate));
    		
    		if(theEnd==null) {
        		theEnd = new ResultState(name.getText(), period.getText());
        	}
    		        	
        	name.setDisable(true);
        	period.setDisable(true);
        	cifPresupuestados.setDisable(true);
        	basePresupuestada.setDisable(true);
        	contin.setDisable(true);
        	
    	}else {
    		Information("Please enter a value into the active fields");
    	}
    }
    
    @FXML
    void actualPressed(ActionEvent event) {
    	if(actual.isSelected()) {
    		cifPasados.setDisable(true);
    	}else {
    		cifPasados.setDisable(false);
    	}
    }

    @FXML
    void create(ActionEvent event) {	
    	Information(costState());
    	cifPresupuestados.setText("");
    	basePresupuestada.setText("");
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
    	
    	DecimalFormat formato = new DecimalFormat("#.00");
    	
    	String result = "\t\t\t\t"+theEnd.getName() + "\n";
    	result+="\t\t\t\t Estado de Costos" + "\n\t\t\t\t"+theEnd.getPeriod()+
    			"\n--------------------------------------------------------------------\n--------------------------------------------------------------------\n";
    	result+="Inventario Inicial de MD \t\t\t"+0+"\n";
    	result+="Compra de MD \t\t\t\t" + formato.format(consumoMaterialDirecto)+"$"+"\n";
    	result+="Inventario Final de MD \t\t\t"+0+"\n--------------------------------------------------------------------"+"\n";
    	result+="Consumo de MD \t\t\t\t"+formato.format(consumoMaterialDirecto)+"$"+"\n";
    	result+="MOD \t\t\t\t\t\t"+formato.format(manoDeObraDirecta)+"$"+"\n";
    	result+="CIF \t\t\t\t\t\t\t"+formato.format(costoIndirectoDeFabricacion)+"$"+"\n";
    	result+="--------------------------------------------------------------------\n";
    	result+="Costos Agregados a produccion \t"+formato.format(costosAP)+"$"+"\n";
    	result+="Inventario Inicial PP \t\t\t"+formato.format(inventarioInicialPP)+"$"+"\n";
    	result+="Inventario Final PP \t\t\t\t"+formato.format(inventarioFinalPP)+"$"+"\n";
    	result+="--------------------------------------------------------------------\n";
    	result+="Costos PT \t\t\t\t\t"+formato.format(costosPT)+"$"+"\n";
    	result+="Inventario Inicial PT \t\t\t"+formato.format(inventarioInicialPT)+"$"+"\n";
    	result+="Inventario Final PT \t\t\t\t"+formato.format(inventarioFinalPT)+"$"+"\n";
    	result+="Costo de Venta \t\t\t\t"+formato.format(costoVenta)+"$"+"\n";
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
    	
    	baseType.getItems().add("Mano de obra Directa");
    	baseType.getItems().add("Horas maquina");
    	
    	orderOption.getItems().add("Agregar orden");
    	orderOption.getItems().add("Editar Orden");
    	orderOption.getItems().add("Eliminar Orden");
    	
    	addOrder.setDisable(true);
    	create.setDisable(true);
    	md.setDisable(true);
    	mod.setDisable(true);
    	status.setDisable(true);
    	actual.setDisable(true);
    	orderNumber.setDisable(true);
    	cifPasados.setDisable(true);
    	orderOption.setDisable(true);
    }
}
