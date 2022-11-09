package app;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import Utilities.ConnectionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
import structures.Stand;
import tables.DaysTable;
import tables.StandsTable;

public class ManagerController implements Initializable {
	
	final static String username = "root";
    final static String password = "";
    final static String dbName = "fairdb";

	//private Stage stage;
	private int randnum;
	private ObservableList<Stand> standsList;
	final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
	final static DaysTable daysTable = new DaysTable(connectionProvider.getMySQLConnection());
	final static StandsTable standsTable = new StandsTable(connectionProvider.getMySQLConnection());
	
	@FXML
	private Spinner<Time> spnApertura;
	@FXML
	private Spinner<Time> spnChiusura;
	@FXML
	private ComboBox<Date> cmbBambini;
	@FXML
    private TableColumn<Stand, Date> colBambini;
    @FXML
    private TableColumn<Stand, Integer> colCodice;
    @FXML
    private TableColumn<Stand, Time> colOrarioApertura;
    @FXML
    private TableColumn<Stand, Time> colOrarioChiusura;
    @FXML
    private TableColumn<Stand, Integer> colSpaziOcc;
    @FXML
    private TableColumn<Stand, Integer> colSpaziTot;
    @FXML
    private TableColumn<Stand, String> colSpecializzazione;
	@FXML
    private TableView<Stand> tblPadiglioni;
	@FXML
	private TextField txtSpaziTot;
	@FXML
	private TextField txtSpaziOcc;
	@FXML
	private TextField txtSpecializzazione;
	@FXML
	private TextField txtCancellaPadiglione;

	@Override
	public void initialize (URL url, ResourceBundle rb) {
		// Sets up the opening/closing spinners.
		ObservableList<Time> hours = FXCollections.observableArrayList(new Time(-2211735600000L), new Time(-2211733800000L), new Time(-2211732000000L), new Time(-2211730200000L), //6 
			new Time(-2211728400000L), new Time(-2211726600000L), new Time(-2211724800000L), new Time(-2211723000000L), //8
			new Time(-2211721200000L), new Time(-2211719400000L), new Time(-2211717600000L), new Time(-2211715800000L), //10
			new Time(-2211714000000L), new Time(-2211712200000L), new Time(-2211710400000L), new Time(-2211708600000L), //12
			new Time(-2211706800000L), new Time(-2211705000000L), new Time(-2211703200000L), new Time(-2211701400000L), //14
			new Time(-2211699600000L), new Time(-2211697800000L), new Time(-2211696000000L), new Time(-2211694200000L), //16
			new Time(-2211692400000L), new Time(-2211690600000L), new Time(-2211688800000L), new Time(-2211687000000L), //18
			new Time(-2211685200000L), new Time(-2211683400000L), new Time(-2211681600000L), new Time(-2211679800000L), //20
			new Time(-2211678000000L));//22
		
		SpinnerValueFactory<Time> valueFactory1 = new SpinnerValueFactory.ListSpinnerValueFactory<Time>(hours);
		SpinnerValueFactory<Time> valueFactory2 = new SpinnerValueFactory.ListSpinnerValueFactory<Time>(hours);
		
		valueFactory1.setValue(new Time(-2211714000000L));
		valueFactory2.setValue(new Time(-2211714000000L));
		       
		spnApertura.setValueFactory(valueFactory1);
		spnChiusura.setValueFactory(valueFactory2);
		
		// Sets up the children places combobox.
		cmbBambini.getItems().clear();
		cmbBambini.getItems().addAll(daysTable.getAllDays());
		
		// Fills the stands table.
		standsList = FXCollections.observableArrayList(standsTable.getAllStands());
		
		colCodice.setCellValueFactory(new PropertyValueFactory<Stand,Integer>("standCod"));
		colSpecializzazione.setCellValueFactory(new PropertyValueFactory<Stand,String>("specialization"));
		colOrarioApertura.setCellValueFactory(new PropertyValueFactory<Stand,Time>("open"));
		colOrarioChiusura.setCellValueFactory(new PropertyValueFactory<Stand,Time>("close"));
		colBambini.setCellValueFactory(new PropertyValueFactory<Stand,Date>("children"));
		colSpaziTot.setCellValueFactory(new PropertyValueFactory<Stand,Integer>("expTot"));
		colSpaziOcc.setCellValueFactory(new PropertyValueFactory<Stand,Integer>("expOcc"));
		 
		tblPadiglioni.setItems(standsList);
	}
	
	public void btnAddStand(ActionEvent event) throws IOException {
	    int totalSpaces;
	    int occupiedSpaces;
		try {
			totalSpaces = Integer.parseInt(txtSpaziTot.getText());
			occupiedSpaces = Integer.parseInt(txtSpaziOcc.getText());
			if (occupiedSpaces > totalSpaces) {
				throw new Exception();
			}
			Random rand = new Random();
	    	boolean assigned = false;
	    	while(assigned == false) {
	    		randnum = rand.nextInt(200)+1;
	    		if (standsTable.checkId(randnum) == false) {
	    			standsTable.addStand(randnum, txtSpecializzazione.getText(), spnApertura.getValue(), spnChiusura.getValue(), cmbBambini.getValue(), totalSpaces, occupiedSpaces);
	    			this.updateStandTable();
	    			assigned = true;
	    		}
	    	}
		} catch (IllegalArgumentException arg) {
			System.out.println("Devi inserire per forza un numero!!");
		}catch (Exception e) {
			System.out.println("Non è possibile occupare più spazi di quelli totali!");
		}
		
		txtSpecializzazione.setText("");
		txtSpaziTot.setText("");
		txtSpaziOcc.setText("");
	}
	
	public void btnDeleteStand(ActionEvent event) throws IOException {
	    try {
			int code = Integer.parseInt(txtCancellaPadiglione.getText());
			standsTable.deleteStand(code);
			this.updateStandTable();
		} catch (IllegalArgumentException arg) {
			System.out.println("Devi inserire per forza un numero!!");
		}
	    
	    txtCancellaPadiglione.setText("");
    }
	
    
    public void updateStandTable(){
    	standsList = FXCollections.observableArrayList(standsTable.getAllStands());
		tblPadiglioni.setItems(standsList);
    }
	
}
