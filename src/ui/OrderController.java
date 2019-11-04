package ui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import model.Order;
import model.ResultState;

public class OrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField period;

    @FXML
    private TextField cifPresupuestados;

    @FXML
    private TextField basePresupuestada;
    
    @FXML
    private TextField orderNumber;

    @FXML
    private ChoiceBox<String> baseType;

    @FXML
    private Button contin;
    
    @FXML
    private Button delete;

    @FXML
    private TextField md;

    @FXML
    private TextField mod;

    @FXML
    private TextField cifReales;
    
    @FXML
    private TextField horasMaquina;
    
    @FXML
    private Label label;
    
    @FXML
    private Label rateLabel;

    @FXML
    private ChoiceBox<String> status;

    @FXML
    private CheckBox actual;

    @FXML
    private Button addOrder;

    @FXML
    private Button create;
    
    @FXML
    private TableView<Order> table;
    
    @FXML
    private TableColumn<Order, String> number;

    @FXML
    private TableColumn<Order, Double> mOD;

    @FXML
    private TableColumn<Order, Double> mD;

    @FXML
    private TableColumn<Order, Boolean> state;
    
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
    	String id = orderNumber.getText();
    	int mD = 0;
    	int moD = 0;
    	double hours = 0;
    	
    	try {
        	mD = Integer.parseInt(md.getText());
        	moD = Integer.parseInt(mod.getText());
        	if(baseType.getValue().equals("Horas maquina")) {
        		hours = Integer.parseInt(horasMaquina.getText());
        	}
    	}catch(NumberFormatException e) {
    		create.setDisable(true);
    		Information("Please Enter a number");
    	}
    	String statu = (String) status.getValue();
    	boolean esActual = actual.isSelected();
    	Order newOrder = new Order(id, mD, moD, statu, esActual, hours);
    	
    	if(baseType.getValue().equals("Horas maquina")) {
    		newOrder.calculateCif(realRate, hours);
    	}else {
    		newOrder.calculateCif(realRate, moD);
    	}
    	
    	if(theEnd==null) {
    		theEnd = new ResultState(name.getText(), period.getText());
    	}
    	
    	theEnd.addOrder(newOrder);
    	table.getItems().add(newOrder);
    	orderNumber.setText("" + (id+1));
    	
    	md.setText("");
    	mod.setText("");
    	status.hide();
    	orderNumber.setText("");
    	horasMaquina.setText("");
    }

    @FXML
    void contin(ActionEvent event) {
    	if(!name.getText().equals("") && !period.getText().equals("") && !cifPresupuestados.getText().equals("")&&!basePresupuestada.getText().equals("")) {
    		addOrder.setDisable(false);
        	md.setDisable(false);
        	mod.setDisable(false);
        	status.setDisable(false);
        	orderNumber.setDisable(false);
        	actual.setDisable(false);
        	
        	if(baseType.getValue().equals("Horas maquina")) {
        		horasMaquina.setDisable(false);
        		horasMaquina.setVisible(true);
        	}
        	double cifAux = 0.0;
        	double baseAux = 0.0;
        	try {
        		cifAux = Double.parseDouble(cifPresupuestados.getText());
        		baseAux = Double.parseDouble(basePresupuestada.getText());
			} catch (NumberFormatException e) {
				Information("Por Favor Ingrese un Valos numerico en los cif o la base");
			}
    		realRate= cifAux/baseAux;
    		
    		DecimalFormat formato = new DecimalFormat("#.00");
    		
    		
    		rateLabel.setText(rateLabel.getText() + formato.format(realRate));
    		
    		if(theEnd==null) {
        		theEnd = new ResultState(name.getText(), period.getText());
        	}
    		
    		if(baseType.getValue().equals("Mano de obra Directa")) {
    			label.setVisible(false);
    			horasMaquina.setVisible(false);
    		}else {
    			label.setVisible(true);
    			horasMaquina.setVisible(true);
    		}
    		        	
        	name.setDisable(true);
        	period.setDisable(true);
        	cifPresupuestados.setDisable(true);
        	basePresupuestada.setDisable(true);
        	contin.setDisable(true);
        	baseType.setDisable(true);
        	cifReales.setDisable(true);
        	
    	}else {
    		Information("Please enter a value into the active fields");
    	}
    }

    @FXML
    void create(ActionEvent event) {
    	Information(costState());
    	cifPresupuestados.setText("");
    	basePresupuestada.setText("");
    }
    
    @FXML
    void delete(ActionEvent event) {
    	ObservableList<Order> allOrders,oneOrder;
    	allOrders = table.getItems();
    	oneOrder = table.getSelectionModel().getSelectedItems();
    	oneOrder.forEach(allOrders::remove);
    	
    	for (int i = 0; i < theEnd.getOrders().size(); i++) {
			if(theEnd.getOrders().get(i)== oneOrder){
				theEnd.getOrders().remove(i);
			}
		}
    	
    }
    
    @FXML
    void edit(TableColumn.CellEditEvent<Order, String> event) {
    	Order order = table.getSelectionModel().getSelectedItem();
    	order.setId(event.getNewValue());
    }
    
    @FXML
    void editMD(TableColumn.CellEditEvent<Order, String> event) {
    	Order order = table.getSelectionModel().getSelectedItem();
    	order.setMd(Double.parseDouble(event.getNewValue()));
    }
    
    @FXML
    void editMod(TableColumn.CellEditEvent<Order, String> event) {
    	Order order = table.getSelectionModel().getSelectedItem();
    	order.setMod(Double.parseDouble(event.getNewValue()));
    }
    
    @FXML
    void changeState(ActionEvent event) {
    	Order order = table.getSelectionModel().getSelectedItem();    	
    	String value ="";
    	for (int i = 0; i < theEnd.getOrders().size(); i++) {
			if (order == theEnd.getOrders().get(i)) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("CG Costing");
				alert.setHeaderText("Va a realizar el cambio del estado de una orden");
				alert.setContentText("Escoga a que Estado desea cambiar la orden seleccionada");

				ButtonType inProcces = new ButtonType(Order.INPROCESS);
				ButtonType finished = new ButtonType(Order.FINISHED);
				ButtonType sold = new ButtonType(Order.SOLD);
				ButtonType cancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(inProcces, finished, sold, cancel);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == inProcces){
					value =Order.INPROCESS;
				    theEnd.getOrders().get(i).setStatus(Order.INPROCESS);
				} else if (result.get() == finished) {
					value =Order.FINISHED;
					theEnd.getOrders().get(i).setStatus(Order.FINISHED);
				} else if (result.get() == sold) {
					value =Order.SOLD;
					theEnd.getOrders().get(i).setStatus(Order.SOLD);
				} else {
				   System.out.println("Orden cancelada");
				}
			}
		}
    	order.setStatus(value);
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
    	double variacion = costoIndirectoDeFabricacion - Double.parseDouble(cifReales.getText());
    	String sobreOSub = "";
    	if(variacion >= 0) {
    		sobreOSub = "sobreaplicada";
    	}else {
    		sobreOSub = "subaplicada";
    	}
    	
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
    	result+="--------------------------------------------------------------------" + "\n\n";
    	result+="Variacion CIF " + sobreOSub + "\t\t" + formato.format(variacion) + "\n\n";
    	result+="--------------------------------------------------------------------";
    	
    	return result;
    }    
    
    @FXML
    void initialize() {
    	status.getItems().add("in Process");
    	status.getItems().add("Finished");
    	status.getItems().add("Sold");
    	
    	baseType.getItems().add("Mano de obra Directa");
    	baseType.getItems().add("Horas maquina");
    	
    	label.setVisible(false);
		horasMaquina.setVisible(false);
    	
    	addOrder.setDisable(true);
    	create.setDisable(true);
    	md.setDisable(true);
    	mod.setDisable(true);
    	status.setDisable(true);
    	actual.setDisable(true);
    	
    	number.setCellValueFactory(new PropertyValueFactory<>("id"));
    	mOD.setCellValueFactory(new PropertyValueFactory<>("mod"));
    	mD.setCellValueFactory(new PropertyValueFactory<>("md"));
    	state.setCellValueFactory(new PropertyValueFactory<>("status"));
    	
    	table.setEditable(true);
    	number.setCellFactory(TextFieldTableCell.forTableColumn());
    	mD.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    	mOD.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    }
}
/*
 
 */
